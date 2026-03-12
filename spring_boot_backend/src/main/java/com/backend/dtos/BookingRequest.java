package com.backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.backend.entities.ServiceCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    
    @NotNull(message = "Service Category is required")
    private ServiceCategory serviceCategory;
    
    // Pickup Address fields
    @NotNull(message = "Pickup address label is required")
    private String pickupLabel;
    
    @NotNull(message = "Pickup address line is required")
    private String pickupAddressLine;
    
    @NotNull(message = "Pickup state name is required")
    private String pickupState;
    
    @NotNull(message = "Pickup city name is required")
    private String pickupCity;
    
    @NotNull(message = "Pickup pincode is required")
    private String pickupPincode;
    
    // Drop Address fields
    @NotNull(message = "Drop address label is required")
    private String dropLabel;
    
    @NotNull(message = "Drop address line is required")
    private String dropAddressLine;
    
    @NotNull(message = "Drop state name is required")
    private String dropState;
    
    @NotNull(message = "Drop city name is required")
    private String dropCity;
    
    @NotNull(message = "Drop pincode is required")
    private String dropPincode;
    
    @NotNull(message = "Move date is required")
    @Future(message = "Move date must be in the future")
    private LocalDate moveDate;
    
    @Positive(message = "Estimated price must be positive")
    private BigDecimal estimatedPrice;

    public ServiceCategory getServiceCategory() { return serviceCategory; }
    public void setServiceCategory(ServiceCategory serviceCategory) { this.serviceCategory = serviceCategory; }

    public String getPickupLabel() { return pickupLabel; }
    public void setPickupLabel(String pickupLabel) { this.pickupLabel = pickupLabel; }

    public String getPickupAddressLine() { return pickupAddressLine; }
    public void setPickupAddressLine(String pickupAddressLine) { this.pickupAddressLine = pickupAddressLine; }

    public String getPickupState() { return pickupState; }
    public void setPickupState(String pickupState) { this.pickupState = pickupState; }

    public String getPickupCity() { return pickupCity; }
    public void setPickupCity(String pickupCity) { this.pickupCity = pickupCity; }

    public String getPickupPincode() { return pickupPincode; }
    public void setPickupPincode(String pickupPincode) { this.pickupPincode = pickupPincode; }

    public String getDropLabel() { return dropLabel; }
    public void setDropLabel(String dropLabel) { this.dropLabel = dropLabel; }

    public String getDropAddressLine() { return dropAddressLine; }
    public void setDropAddressLine(String dropAddressLine) { this.dropAddressLine = dropAddressLine; }

    public String getDropState() { return dropState; }
    public void setDropState(String dropState) { this.dropState = dropState; }

    public String getDropCity() { return dropCity; }
    public void setDropCity(String dropCity) { this.dropCity = dropCity; }

    public String getDropPincode() { return dropPincode; }
    public void setDropPincode(String dropPincode) { this.dropPincode = dropPincode; }

    public LocalDate getMoveDate() { return moveDate; }
    public void setMoveDate(LocalDate moveDate) { this.moveDate = moveDate; }

    public BigDecimal getEstimatedPrice() { return estimatedPrice; }
    public void setEstimatedPrice(BigDecimal estimatedPrice) { this.estimatedPrice = estimatedPrice; }
}
