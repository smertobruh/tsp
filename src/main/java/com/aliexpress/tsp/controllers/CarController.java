package com.aliexpress.tsp.controllers;


import com.aliexpress.tsp.Test;
import com.aliexpress.tsp.services.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    private final Test test;

    @GetMapping("/")
    public String products(@RequestParam(required = false) String carModel, Model model) {
        try {
            model.addAttribute("cars", carService.listCars(carModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "cars";
    }

    @GetMapping("/test")
    public void test(){
        test.test();
    }
}
