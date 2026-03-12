package com.backend.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.BookingDetailsResponse;
import com.backend.dtos.BookingRequest;
import com.backend.dtos.BookingResponse;
import com.backend.entities.Address;
import com.backend.entities.Booking;
import com.backend.entities.BookingStatus;
import com.backend.entities.Customer;
import com.backend.entities.ServiceType;
import com.backend.repository.AddressRepository;
import com.backend.repository.BookingRepository;
import com.backend.repository.CustomerRepository;
import com.backend.repository.ServiceTypeRepository;

import com.backend.exception.ApiException;
import com.backend.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final CustomerRepository customerRepo;
    private final AddressRepository addressRepo;
    private final ServiceTypeRepository serviceTypeRepo;
    private final LocationService locationService;

    @Override
    public BookingDetailsResponse createBooking(BookingRequest request, String userEmailOrId) {
        // 1. Find customer by email or ID
        Customer customer = findCustomerByIdOrEmail(userEmailOrId);

        // 2. Validate pickup location: city belongs to state
        locationService.validateCityInState(request.getPickupCity(), request.getPickupState());
        
        // 3. Validate drop location: city belongs to state
        locationService.validateCityInState(request.getDropCity(), request.getDropState());

        // 4. Find service type by category
        ServiceType serviceType = serviceTypeRepo.findByCategory(request.getServiceCategory())
                .stream()
                .filter(ServiceType::isActive)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No active service found for category: " + request.getServiceCategory()));

        // 5. Get pickup state and city entities
        com.backend.entities.State pickupState = locationService.getStateByName(request.getPickupState());
        com.backend.entities.City pickupCity = locationService.getCityByNameAndState(request.getPickupCity(), pickupState.getId());

        // 6. Create and save pickup address
        Address pickupAddress = new Address();
        pickupAddress.setLabel(request.getPickupLabel());
        pickupAddress.setAddressLine(request.getPickupAddressLine());
        pickupAddress.setState(pickupState);
        pickupAddress.setCity(pickupCity);
        pickupAddress.setPincode(request.getPickupPincode());
        pickupAddress = addressRepo.save(pickupAddress);

        // 7. Get drop state and city entities
        com.backend.entities.State dropState = locationService.getStateByName(request.getDropState());
        com.backend.entities.City dropCity = locationService.getCityByNameAndState(request.getDropCity(), dropState.getId());

        // 8. Create and save drop address
        Address dropAddress = new Address();
        dropAddress.setLabel(request.getDropLabel());
        dropAddress.setAddressLine(request.getDropAddressLine());
        dropAddress.setState(dropState);
        dropAddress.setCity(dropCity);
        dropAddress.setPincode(request.getDropPincode());
        dropAddress = addressRepo.save(dropAddress);

        // 9. Create booking with PENDING status
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setService(serviceType);
        booking.setPickupAddress(pickupAddress);
        booking.setDropAddress(dropAddress);
        booking.setMoveDate(request.getMoveDate());
        booking.setEstimatedPrice(request.getEstimatedPrice());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepo.save(booking);

        // 10. Return response
        return mapToDetailsResponse(savedBooking);
    }

    @Override
    public BookingDetailsResponse updateBooking(Long bookingId, BookingRequest request, String userEmailOrId) {
        // 1. Find customer
        Customer customer = findCustomerByIdOrEmail(userEmailOrId);

        // 2. Find booking
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // 3. Verify ownership
        if (!booking.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("Unauthorized: This booking does not belong to you");
        }

        // 4. Verify status is PENDING
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new ApiException("Cannot modify booking. Only PENDING bookings can be modified. " +
                "Current status: " + booking.getStatus());
        }

        // 5. Validate locations
        locationService.validateCityInState(request.getPickupCity(), request.getPickupState());
        locationService.validateCityInState(request.getDropCity(), request.getDropState());

        // 6. Update Service if changed
        ServiceType serviceType = serviceTypeRepo.findByCategory(request.getServiceCategory())
                .stream()
                .filter(ServiceType::isActive)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No active service found for category: " + request.getServiceCategory()));
        booking.setService(serviceType);

        // 7. Update Addresses
        Address pickup = booking.getPickupAddress();
        pickup.setLabel(request.getPickupLabel());
        pickup.setAddressLine(request.getPickupAddressLine());
        pickup.setState(locationService.getStateByName(request.getPickupState()));
        pickup.setCity(locationService.getCityByNameAndState(request.getPickupCity(), pickup.getState().getId()));
        pickup.setPincode(request.getPickupPincode());
        addressRepo.save(pickup);

        Address drop = booking.getDropAddress();
        drop.setLabel(request.getDropLabel());
        drop.setAddressLine(request.getDropAddressLine());
        drop.setState(locationService.getStateByName(request.getDropState()));
        drop.setCity(locationService.getCityByNameAndState(request.getDropCity(), drop.getState().getId()));
        drop.setPincode(request.getDropPincode());
        addressRepo.save(drop);

        // 8. Update other fields
        booking.setMoveDate(request.getMoveDate());
        booking.setEstimatedPrice(request.getEstimatedPrice());

        Booking updatedBooking = bookingRepo.save(booking);

        return mapToDetailsResponse(updatedBooking);
    }

    @Override
    public List<BookingDetailsResponse> getCurrentBookings(String userEmailOrId) {
        // Find customer
        Customer customer = findCustomerByIdOrEmail(userEmailOrId);

        // Get bookings excluding DELIVERED and CANCELLED
        List<BookingStatus> excludedStatuses = Arrays.asList(
            BookingStatus.DELIVERED, 
            BookingStatus.CANCELLED
        );
        
        List<Booking> bookings = bookingRepo.findByCustomerAndStatusNotIn(customer, excludedStatuses);
        
        return bookings.stream()
                .map(this::mapToDetailsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDetailsResponse> getBookingHistory(String userEmailOrId) {
        // Find customer
        Customer customer = findCustomerByIdOrEmail(userEmailOrId);

        // Get only DELIVERED and CANCELLED bookings
        List<BookingStatus> historyStatuses = Arrays.asList(
            BookingStatus.DELIVERED, 
            BookingStatus.CANCELLED
        );
        
        List<Booking> bookings = bookingRepo.findByCustomerAndStatusIn(customer, historyStatuses);
        
        return bookings.stream()
                .map(this::mapToDetailsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse cancelBooking(Long bookingId, String userEmailOrId) {
        // Find customer
        Customer customer = findCustomerByIdOrEmail(userEmailOrId);

        // Find booking
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Verify booking belongs to this customer
        if (!booking.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("Unauthorized: This booking does not belong to you");
        }

        // Check if status is PENDING
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new ApiException(
                "Cannot cancel booking. Only bookings with PENDING status can be cancelled. " +
                "Current status: " + booking.getStatus() + ". " +
                "Please use the request cancellation endpoint to send a cancellation request to admin."
            );
        }

        // Update status to CANCELLED
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepo.save(booking);

        return new BookingResponse(
            booking.getId(),
            BookingStatus.CANCELLED.toString(),
            booking.getEstimatedPrice(),
            booking.getMoveDate(),
            "Booking cancelled successfully"
        );
    }

    @Override
    public String requestCancellation(Long bookingId, String userEmailOrId, String reason) {
        // Find customer
        Customer customer = findCustomerByIdOrEmail(userEmailOrId);

        // Find booking
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Verify booking belongs to this customer
        if (!booking.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("Unauthorized: This booking does not belong to you");
        }

        // Check if already cancelled or delivered
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new ApiException("Booking is already cancelled");
        }
        if (booking.getStatus() == BookingStatus.DELIVERED) {
            throw new ApiException("Cannot cancel a delivered booking");
        }

        // TODO: Send email to admin with cancellation request
        // Placeholder for email functionality
        String emailContent = String.format(
            "Cancellation Request:\n" +
            "Customer: %s (%s)\n" +
            "Booking ID: %d\n" +
            "Current Status: %s\n" +
            "Reason: %s\n" +
            "Move Date: %s",
            customer.getName(),
            customer.getEmail(),
            booking.getId(),
            booking.getStatus(),
            reason,
            booking.getMoveDate()
        );

        // Log the request (in production, this would send an email)
        System.out.println("===== CANCELLATION REQUEST =====");
        System.out.println(emailContent);
        System.out.println("================================");

        return "Cancellation request submitted successfully. Admin will review your request and contact you soon.";
    }

    /**
     * Helper to find customer by ID (if identifier is numeric) or Email
     */
    private Customer findCustomerByIdOrEmail(String identifier) {
        try {
            // Try to parse as Long (ID)
            Long id = Long.parseLong(identifier);
            return customerRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
        } catch (NumberFormatException e) {
            // Not a number, try to find by email
            return customerRepo.findByEmail(identifier)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + identifier));
        }
    }

    // Helper method to map Booking entity to BookingDetailsResponse DTO
    private BookingDetailsResponse mapToDetailsResponse(Booking booking) {
        BookingDetailsResponse response = new BookingDetailsResponse();
        response.setBookingId(booking.getId());
        response.setServiceId(booking.getService().getId());
        response.setServiceName(booking.getService().getName());
        
        // Format pickup address using State and City entities
        Address pickup = booking.getPickupAddress();
        response.setPickupAddress(String.format("%s, %s, %s, %s - %s",
            pickup.getLabel(),
            pickup.getAddressLine(),
            pickup.getCity() != null ? pickup.getCity().getName() : "",
            pickup.getState() != null ? pickup.getState().getName() : "",
            pickup.getPincode()
        ));
        
        // Format drop address using State and City entities
        Address drop = booking.getDropAddress();
        response.setDropAddress(String.format("%s, %s, %s, %s - %s",
            drop.getLabel(),
            drop.getAddressLine(),
            drop.getCity() != null ? drop.getCity().getName() : "",
            drop.getState() != null ? drop.getState().getName() : "",
            drop.getPincode()
        ));
        
        response.setMoveDate(booking.getMoveDate());
        response.setStatus(booking.getStatus().toString());
        response.setEstimatedPrice(booking.getEstimatedPrice());
        response.setCreatedAt(booking.getCreatedAt());
        
        return response;
    }
}
