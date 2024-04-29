package com.aliexpress.tsp;

import com.aliexpress.tsp.model.Booking;
import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.User;
import com.aliexpress.tsp.services.BookingService;
import com.aliexpress.tsp.services.CarService;
import com.aliexpress.tsp.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Test {
    private final ApplicationContext applicationContext;

    private final BookingService bookingService;

    private final CarService carService;

    private final UserService userService;

    public void test(){
        Booking booking = new Booking();
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(LocalDateTime.now().plusHours(2));
        Car car = carService.findCarByModel("m5").get(0);
        carService.deleteCar(car.getId());
        carService.createCar(car);
        car = carService.findCarByModel("m5").get(0);
        User user = userService.findUserByID(2L).orElse(null);
        user.getCars().add(car);
        car = carService.findCarByModel("Kalina").get(0);
        user.getCars().add(car);
        System.out.println(car);
        System.out.println(user);
        bookingService.createBooking(booking, car.getId(),  user.getId());
    }

}
