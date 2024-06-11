package com.aliexpress.tsp.controllers;


import com.aliexpress.tsp.dto.BookingDTO;
import com.aliexpress.tsp.dto.CarDTO;
import com.aliexpress.tsp.exceptions.InvalidDataException;
import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.User;
import com.aliexpress.tsp.services.BookingService;
import com.aliexpress.tsp.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    private final BookingService bookingService;


    @GetMapping("/")
    public String cars(@RequestParam(required = false) String carModel, Model model, Principal principal) {
        User user = carService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        try {
            model.addAttribute("cars", carService.listCars(carModel, user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cars";
    }

    @GetMapping("/car/{id}")
    public String carInfo(@PathVariable Long id, Model model, Principal principal) {
        Car car = carService.findCarByID(id).orElse(new Car());
        model.addAttribute("car", car);
        model.addAttribute("images", car.getImages());
        model.addAttribute("bookingDTO", new BookingDTO());
        model.addAttribute("user", carService.getUserByPrincipal(principal));
        return "info";
    }

    @PostMapping("/car/{id}/booking")
    public String booking(@ModelAttribute("bookingDTO") BookingDTO bookingDTO, Model model) {

        System.out.println(bookingDTO);
        try {
            bookingService.saveBooking(bookingDTO);
        } catch ( InvalidDataException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }


        return "redirect:/car/{id}";
    }

    @GetMapping("/favourites")
    public String favourites(Model model, Principal principal){
        User user = carService.getUserByPrincipal(principal);
        model.addAttribute("cars", carService.findAllCar(user).stream().filter(CarDTO::isLiked));
        return "favourites";
    }

    @GetMapping("/car/{id}/like")
    public String like(@PathVariable Long id, Principal principal) {
        carService.like(id, carService.getUserByPrincipal(principal));
        return "redirect:/";
    }
}
