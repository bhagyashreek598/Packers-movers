package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "vehicles")
@AttributeOverride(name = "id", column = @Column(name = "vehicle_id"))
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Vehicle extends BaseEntity {

    private String numberPlate;
    private String type;
    private String capacity;
    private boolean active = true;
}
