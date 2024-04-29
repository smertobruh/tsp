package com.aliexpress.tsp.repositories;

import com.aliexpress.tsp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByModel(String model);


}
