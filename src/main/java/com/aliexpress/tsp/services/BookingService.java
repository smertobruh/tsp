package com.aliexpress.tsp.services;

import com.aliexpress.tsp.dto.BookingDTO;
import com.aliexpress.tsp.exceptions.InvalidDataException;
import com.aliexpress.tsp.model.Booking;
import com.aliexpress.tsp.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;

    private final CarService carService;




    public void saveBooking(BookingDTO bookingDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        Booking booking = new Booking();
        System.out.println("#########");
        System.out.println(bookingDTO);
        booking.setEndTime(LocalDate.parse(bookingDTO.getEndTime(), formatter).atStartOfDay());
        booking.setStartTime(LocalDate.parse(bookingDTO.getStartTime(), formatter).atStartOfDay());
        booking.setCar(carService.getCarById(bookingDTO.getBookCarId()));
        booking.setUser(carService.getUserById(bookingDTO.getBookUserId()));

        if (bookingOverlaps(booking)) {
            throw new InvalidDataException("Пересечение с другой бронью");
        }
        booking = bookingRepository.save(booking);
    }

    public boolean bookingOverlaps(Booking booking) {
        return bookingRepository.findByCarID(booking.getCar().getId())
                .stream().anyMatch(dataBaseRes -> {
            if (dataBaseRes.getCar().getId().equals(booking.getCar().getId())) {
                int checkInInDbCheckOut = booking.getStartTime().compareTo(dataBaseRes.getEndTime());
                int checkOutInDbCheckIn = booking.getStartTime().compareTo(dataBaseRes.getEndTime());
                int checkInBeforeDbCheckOut = booking.getStartTime().compareTo(dataBaseRes.getEndTime()) > 0 ? 1 : -1;
                int checkOutBeforeDbCheckIn = booking.getEndTime().compareTo(dataBaseRes.getStartTime()) > 0 ? 1 : -1;
                System.out.println("check in int " + checkInBeforeDbCheckOut);
                System.out.println("check out int " + checkOutBeforeDbCheckIn);
                if (checkInInDbCheckOut == 0 || checkOutInDbCheckIn == 0) {
                    return true;
                } else {
                    return (checkInBeforeDbCheckOut != checkOutBeforeDbCheckIn);
                }
            } else {
                return false;
            }

        });
    }

    public Optional<Booking> getBooking(Long id) {
        return bookingRepository.findById(id);
    }

}
