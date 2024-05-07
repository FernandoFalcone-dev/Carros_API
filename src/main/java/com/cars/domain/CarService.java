package com.cars.domain;

import com.cars.domain.dto.CarDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    CarRepository carRepository;

    public List<CarDTO> getCars() {

        return carRepository.findAll().stream().map(CarDTO::create)
                .collect(Collectors.toList());
    }

    public CarDTO getCarById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        return car.map(CarDTO::create).orElseThrow(
                () -> new EntityNotFoundException("Car with id " + id + " was not found."));
    }

    public List<CarDTO> getCarsByType(String type) {

        return carRepository.findByType(type).stream().map(CarDTO::create)
                .collect(Collectors.toList());
    }

    public CarDTO insert(Car car) {
        return CarDTO.create(carRepository.save(car));
    }

    public CarDTO update(Car car, Long id) {
        Assert.notNull(id,"Unable to update record");

        return carRepository.findById(id).map(db -> {
            db.setName(car.getName());
            db.setType(car.getType());

            carRepository.save(db);

            return CarDTO.create(db);
        }).orElseThrow(() -> new RuntimeException("Unable to update record"));
    }

    public void delete(Long id) {
        carRepository.deleteById(id);
    }

}
