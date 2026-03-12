package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "booking_goods")
@AttributeOverride(name = "id", column = @Column(name = "booking_goods_id"))
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class BookingGoods extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false)
    private GoodsMaster goods;

    private Integer quantity;
}
