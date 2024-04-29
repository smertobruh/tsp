package com.aliexpress.tsp.services;

import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.enums.Role;
import com.aliexpress.tsp.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CarService {
    private final CarRepository carRepository;

    public List<Car> listCars(String model) {
        if (model != null && !model.equals("")) return carRepository.findByModel(model);
        return carRepository.findAll();
    }

    public void createCar(Car car) {
        carRepository.save(car);
        log.info("Saved new user with car: {}", car.getModel());
    }

    public void editCar(Car car){
        Car old = carRepository.findById(car.getId()).orElse(new Car());
        old.setClazz(car.getClazz());
        old.setEnginePower(car.getEnginePower());
        old.setModel(car.getModel());
        old.setTrademark(car.getTrademark());
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }

    public Optional<Car> findCarByID(Long id){
        return carRepository.findById(id);
    }

    public List<Car> findCarByModel(String model){
        return carRepository.findByModel(model);
    }


}
