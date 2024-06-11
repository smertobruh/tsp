package com.aliexpress.tsp.controllers;

import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.repositories.CarRepository;
import com.aliexpress.tsp.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CarService carService;

    @PostMapping("/admin/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("LOX");
        carService.deleteCar(id);
        return "redirect:/";
    }

    @GetMapping("/admin/add")
    public String addPage(Model model) throws IOException {
        model.addAttribute("car", new Car());
        return "admin/add";
    }

    @PostMapping("/admin/add")
    public String addCar(@ModelAttribute("car") Car car,
                         @RequestParam("files") MultipartFile[] files) throws IOException {
        System.out.println("ADD CAR");
        carService.saveCar(car, files);
        return "redirect:/";
    }
}
