package com.backend.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "states")
@AttributeOverride(name = "id", column = @Column(name = "state_id"))
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = "cities")
public class State extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities = new ArrayList<>();

    public State(String name) {
        this.name = name;
    }

    // Helper method to add city
    public void addCity(City city) {
        cities.add(city);
        city.setState(this);
    }

    // Helper method to remove city
    public void removeCity(City city) {
        cities.remove(city);
        city.setState(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
