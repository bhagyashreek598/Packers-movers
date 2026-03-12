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
@Table(name = "goods_master")
@AttributeOverride(name = "id", column = @Column(name = "goods_id"))
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class GoodsMaster extends BaseEntity {

    private String name;
    private String category;
    private boolean active = true;
}
