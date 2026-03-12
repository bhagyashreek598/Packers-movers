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
@Table(name = "customers")
//@AttributeOverride(name = "id", column = @Column(name = "customer_id"))
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Customer extends User {
	

}
