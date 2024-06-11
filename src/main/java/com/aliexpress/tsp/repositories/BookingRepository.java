package com.aliexpress.tsp.repositories;

import com.aliexpress.tsp.model.Booking;
import com.aliexpress.tsp.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b cross join Car c where c.id = :carID")
    List<Booking> findByCarID(@Param("carID") Long carID);

}
