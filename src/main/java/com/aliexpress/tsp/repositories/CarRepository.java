package com.aliexpress.tsp.repositories;

import com.aliexpress.tsp.dto.CarDTO;
import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select new com.aliexpress.tsp.dto.CarDTO(" +
            "c," +
//            "false" +
            "sum(case when cl = :user then 1 else 0 end) > 0" +
            ")" +
            "from Car c left join c.users cl " +
            "where c.model = :model " +
            "group by c")
    List<CarDTO> findByModel(@Param("model") String model, @Param("user") User user);


    @Query("select new com.aliexpress.tsp.dto.CarDTO(" +
            "c," +
//            "false" +
            "sum(case when cl = :user then 1 else 0 end) > 0" +
            ")" +
            "from Car c left join c.users cl " +
            "group by c")
    List<CarDTO> findAllCar(@Param("user") User user);

    @Query("from Car c where c.id = :id")
    Car getCarById(@Param("id") Long id);
}
