package com.aliexpress.tsp.dto;

import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BookingDTO {

    private Long bookCarId;

    private Long bookUserId;

    private String startTime;

    private  String endTime;

    public BookingDTO(Long bookCarId, Long bookUserId, String startTime, String endTime) {
        this.bookCarId = bookCarId;
        this.bookUserId = bookUserId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BookingDTO(){
    }
}
