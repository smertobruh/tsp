package com.aliexpress.tsp.services;

import com.aliexpress.tsp.dto.CarDTO;
import com.aliexpress.tsp.model.Car;
import com.aliexpress.tsp.model.Image;
import com.aliexpress.tsp.model.User;
import com.aliexpress.tsp.model.enums.Role;
import com.aliexpress.tsp.repositories.CarRepository;
import com.aliexpress.tsp.repositories.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
@PropertySource("classpath:application.properties")
public class CarService {
    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final FileSystemStorageService storageService;

    @Value("${upload.path}")
    private String uploadPath;

    public List<CarDTO> listCars(String model, User user) throws IOException {
        if (model != null && !model.equals("") ) return carRepository.findByModel(model, user);

        return findAllCar(user);
    }


    public void saveCar(Car car, MultipartFile... files) throws IOException {
        List<Image> imageList = new ArrayList<>();
        for(var file : files) {
            if(file.getSize() != 0) {
                Image image = toImageEntity(file, car);
                car.addImageToCar(image);
            }
        }
        log.info("Saving new Car. Model: {};", car.getModel());
        Car carFromDb = carRepository.save(car);
        carFromDb.setPreviewImageID(car.getImages().get(0).getId());
        carRepository.save(carFromDb);
    }

    public void editCar(Car car){
        Car old = carRepository.findById(car.getId()).orElse(new Car());
        old.setClazz(car.getClazz());
        old.setEnginePower(car.getEnginePower());
        old.setModel(car.getModel());
        old.setTrademark(car.getTrademark());
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();
        return userRepository.findByEmail(principal.getName()).orElse(null);
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }

    public Optional<Car> findCarByID(Long id){
        return carRepository.findById(id);
    }

    public List<CarDTO> findCarByModel(String model, User user){
        return carRepository.findByModel(model, user);
    }

    public List<CarDTO> findAllCar(User user) {
        if(user.getEmail() == null) return carRepository.findAll().stream().map(o -> new CarDTO(o, false)).toList();
        else return carRepository.findAllCar(user);
    }

    private Image toImageEntity(MultipartFile file, Car car) {
        Image image = new Image();
        String filename = storageService.store(file).getFileName().toString();
        image.setFilename(filename);
        System.out.println(image.getFilename());;
        image.setContentType(MediaType.IMAGE_JPEG.getType());
        return image;
    }

    public boolean like(Long id, User user) {
        Car car = findCarByID(id).orElse(null);
        if(car.getUsers().contains(user)) {
           return car.getUsers().remove(user);
        }
        else return car.getUsers().add(user);
    }

    public Car getCarById(@NonNull Long carId) {
        return carRepository.getCarById(carId);
    }

    public User getUserById(@NonNull Long userId) {
        return userRepository.getUserById(userId);
    }
}
