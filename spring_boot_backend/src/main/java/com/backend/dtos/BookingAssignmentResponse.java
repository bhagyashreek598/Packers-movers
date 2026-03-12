package com.backend.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingAssignmentResponse {
    private Long assignmentId;
    private String message; // "Assignment Successful!"
}