package com.dcorn.api.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private int mileage;

    @PastOrPresent
    private Date year;

    @NotNull
    private String fuel;

    @NotNull
    private String transmission;

    @NotNull
    private String engine;

    @NotNull
    private String color;

//    private Boolean repaired;

    @NotNull
    private int totalOwners;
}
