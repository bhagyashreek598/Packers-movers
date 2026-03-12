package com.backend.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingAssignmentRequest {
    private Long bookingId;
    private Long staffId;
    private Long vehicleId;
}