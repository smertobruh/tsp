package com.aliexpress.tsp.dto;

import com.aliexpress.tsp.model.Booking;
import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.Image;
import com.aliexpress.tsp.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@ToString
public class CarDTO {
    private Long id;

    private String trademark;

    private String model;

    private String clazz;

    private int enginePower;

    private int price;

    private Set<User> users = new HashSet<>();

    private Set<Booking> bookings = new HashSet<>();

    private List<Image> images = new ArrayList<>();

    private Long previewImageID;

    private boolean isLiked;





    public CarDTO(Car car, boolean isLiked) {
        this.id = car.getId();
        this.trademark = car.getTrademark();
        this.model = car.getModel();
        this.clazz = car.getClazz();
        this.enginePower = car.getEnginePower();
        this.price = car.getPrice();
        this.users = car.getUsers();
        this.bookings = car.getBookings();
        this.images = car.getImages();
        this.previewImageID = car.getPreviewImageID();
        this.isLiked = isLiked;
    }
}
