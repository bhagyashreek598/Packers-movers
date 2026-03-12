package com.backend.dtos;

import java.math.BigDecimal;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private String status; // SUCCESS, PENDING
    private BigDecimal amount;
    private String message; // "Payment Successful!"
}