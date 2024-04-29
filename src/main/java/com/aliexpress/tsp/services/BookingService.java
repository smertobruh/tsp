package com.aliexpress.tsp.services;

import com.aliexpress.tsp.model.Booking;
import com.aliexpress.tsp.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserService userService;

    private final CarService carService;

    public void createBooking(Booking booking, Long carID, Long userID) {
        booking.setCar(carService.findCarByID(carID).orElse(null));
        booking.setUser(userService.findUserByID(userID).orElse(null));
        bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public void editBooking(Long id, Booking booking) {
        Booking old = getBooking(id).orElse(null);
        old.setStartTime(booking.getStartTime());
        old.setEndTime(booking.getEndTime());
    }

    public Optional<Booking> getBooking(Long id) {
        return bookingRepository.findById(id);
    }

}
