package com.sanvalero.ejemplofxml.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private int id;
    private String license;
    private String brand;
    private String model;
    private String type;

    public Car(String license, String brand, String model, String type) {
        this.license = license;
        this.brand = brand;
        this.model = model;
        this.type = type;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + brand + " " + model;
    }
}
