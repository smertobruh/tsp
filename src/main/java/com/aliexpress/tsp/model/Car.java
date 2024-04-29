package com.aliexpress.tsp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CARS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "cars_gen", sequenceName = "CARS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_gen")
    private Long id;

    @Column(name = "TRADEMARK")
    private String trademark;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "CLAZZ")
    private String clazz;

    @Column(name = "ENGINEPOWER")
    private int enginePower;

    @ManyToMany(mappedBy = "cars")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "car")
    private Set<Booking> bookings = new HashSet<>();

}
