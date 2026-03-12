package com.backend.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingResponse {
    private Long bookingId;
    private String status;
    private BigDecimal estimatedPrice;
    private LocalDate moveDate;
    private String message;

    public BookingResponse() {}

    public BookingResponse(Long bookingId, String status, BigDecimal estimatedPrice, LocalDate moveDate, String message) {
        this.bookingId = bookingId;
        this.status = status;
        this.estimatedPrice = estimatedPrice;
        this.moveDate = moveDate;
        this.message = message;
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getEstimatedPrice() { return estimatedPrice; }
    public void setEstimatedPrice(BigDecimal estimatedPrice) { this.estimatedPrice = estimatedPrice; }

    public LocalDate getMoveDate() { return moveDate; }
    public void setMoveDate(LocalDate moveDate) { this.moveDate = moveDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
