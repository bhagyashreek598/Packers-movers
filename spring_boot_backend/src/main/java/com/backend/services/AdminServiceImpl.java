package com.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.BookingDetailsResponse;
import com.backend.dtos.UserDTO;
import com.backend.dtos.UserRegisterRequest;
import com.backend.dtos.UserUpdateDTO;
import com.backend.entities.Address;
import com.backend.entities.Booking;
import com.backend.entities.BookingStatus;
import com.backend.entities.Customer;
import com.backend.entities.Staff;
import com.backend.entities.StaffRole;
import com.backend.entities.UserRole;
import com.backend.entities.State;
import com.backend.entities.City;
import com.backend.entities.ServiceType;
import com.backend.repository.BookingRepository;
import com.backend.repository.CustomerRepository;
import com.backend.repository.StaffRepository;
import com.backend.repository.UserRepository;
import com.backend.repository.ServiceTypeRepository;

import com.backend.exception.ApiException;
import com.backend.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final BookingRepository bookingRepo;
    private final StaffRepository staffRepo;
    private final CustomerRepository customerRepo;
    private final UserRepository userRepo;
    private final ServiceTypeRepository serviceTypeRepo;
    private final LocationService locationService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<BookingDetailsResponse> getAllBookings() {
        return bookingRepo.findAll().stream()
                .map(this::mapToDetailsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDetailsResponse getBookingById(Long id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + id));
        return mapToDetailsResponse(booking);
    }

    @Override
    public BookingDetailsResponse updateBooking(Long bookingId, com.backend.dtos.BookingRequest request) {
        // 1. Find booking
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2. Resolve pickup address entities
        State pState = locationService.getStateByName(request.getPickupState());
        City pCity = locationService.getCityByNameAndState(request.getPickupCity(), pState.getId());
        
        Address pickup = booking.getPickupAddress();
        pickup.setLabel(request.getPickupLabel());
        pickup.setAddressLine(request.getPickupAddressLine());
        pickup.setState(pState);
        pickup.setCity(pCity);
        pickup.setPincode(request.getPickupPincode());

        // 3. Resolve drop address entities
        State dState = locationService.getStateByName(request.getDropState());
        City dCity = locationService.getCityByNameAndState(request.getDropCity(), dState.getId());
        
        Address drop = booking.getDropAddress();
        drop.setLabel(request.getDropLabel());
        drop.setAddressLine(request.getDropAddressLine());
        drop.setState(dState);
        drop.setCity(dCity);
        drop.setPincode(request.getDropPincode());

        // 4. Update service category
        ServiceType serviceType = serviceTypeRepo.findByCategory(request.getServiceCategory())
            .stream().filter(ServiceType::isActive).findFirst()
            .orElseThrow(() -> new RuntimeException("Service category not found"));
        
        booking.setService(serviceType);
        booking.setMoveDate(request.getMoveDate());
        booking.setEstimatedPrice(request.getEstimatedPrice());

        Booking saved = bookingRepo.save(booking);
        return mapToDetailsResponse(saved);
    }

    @Override
    public void updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(status);
        bookingRepo.save(booking);
    }

    @Override
    public List<UserDTO> getAllStaff() {
        return staffRepo.findByActiveTrue().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getInactiveStaff() {
        return staffRepo.findByActiveFalse().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getStaffById(Long staffId) {
        Staff staff = staffRepo.findById(staffId)
                .filter(Staff::isActive)
                .orElseThrow(() -> new RuntimeException("Active staff member not found"));
        return convertToUserDTO(staff);
    }

    @Override
    public UserDTO addStaff(UserRegisterRequest request) {
        // 1. Check if email already exists
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already exists");
        }

        if (request.getStaffType() == null) {
            throw new ApiException("Staff type is required for staff registration");
        }

        Staff staff = mapper.map(request, Staff.class);
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staff.setUserRole(UserRole.STAFF);
        staff.setActive(true);
        staff.setStaffRole(StaffRole.valueOf(request.getStaffType().toUpperCase()));

        Staff savedStaff = staffRepo.save(staff);
        return convertToUserDTO(savedStaff); 
    }

    @Override
    public UserDTO updateStaff(Long staffId, UserUpdateDTO request) {
        Staff staff = staffRepo.findById(staffId)
                .filter(Staff::isActive)
                .orElseThrow(() -> new RuntimeException("Active staff member not found"));
        
        staff.setName(request.getName());
        staff.setPhone(request.getPhone());
        
        if (request.getStaffType() != null) {
            staff.setStaffRole(StaffRole.valueOf(request.getStaffType().toUpperCase()));
        }

        Staff updatedStaff = staffRepo.save(staff);
        return convertToUserDTO(updatedStaff);
    }


    @Override
    public void deleteStaff(Long staffId) {
        Staff staff = staffRepo.findById(staffId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff member not found")); // Changed exception
        
        staff.setActive(false); // Soft delete
        staffRepo.save(staff);
    }

    @Override
    public UserDTO addCustomer(UserRegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already exists!");
        }

        Customer customer = mapper.map(request, Customer.class);
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setUserRole(UserRole.CUSTOMER);
        
        Customer savedCustomer = customerRepo.save(customer);
        return convertToUserDTO(savedCustomer); 
    }

    @Override
    public List<UserDTO> getAllCustomers() {
        return customerRepo.findAll().stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getCustomerById(Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return convertToUserDTO(customer);
    }    
    
    @Override
    public UserDTO updateCustomer(Long id, UserUpdateDTO request) { // Changed parameter name
        Customer customer = customerRepo.findById(id) // Changed parameter name
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found")); // Changed exception
        
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());

        Customer updatedCustomer = customerRepo.save(customer);
        return convertToUserDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepo.delete(customer);
    }

    private UserDTO convertToUserDTO(com.backend.entities.User user) {
        UserDTO dto = mapper.map(user, UserDTO.class);
        dto.setId(user.getId());
        dto.setUserRole(user.getUserRole().name());
        
        if (user instanceof Staff) {
            Staff staff = (Staff) user;
            if (staff.getStaffRole() != null) {
                dto.setStaffRole(staff.getStaffRole().name());
            }
        }
        
        return dto;
    }

    private BookingDetailsResponse mapToDetailsResponse(Booking booking) {
        BookingDetailsResponse response = new BookingDetailsResponse();
        response.setBookingId(booking.getId());
        response.setServiceId(booking.getService().getId());
        response.setServiceName(booking.getService().getName());
        
        Address pickup = booking.getPickupAddress();
        response.setPickupAddress(String.format("%s, %s, %s, %s - %s",
            pickup.getLabel(),
            pickup.getAddressLine(),
            pickup.getCity() != null ? pickup.getCity().getName() : "",
            pickup.getState() != null ? pickup.getState().getName() : "",
            pickup.getPincode()
        ));
        
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
