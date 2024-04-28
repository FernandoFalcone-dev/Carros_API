package com.cars.domain;

import com.cars.domain.dto.CarDTO;
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

    public Optional<CarDTO> getCarById(Long id) {

        return carRepository.findById(id).map(CarDTO::create);
    }

    public List<CarDTO> getCarsByType(String type) {

        return carRepository.findByType(type).stream().map(CarDTO::create)
                .collect(Collectors.toList());
    }

    public CarDTO insert(Car car) {
        Assert.isNull(car.getId(), "Unable to insert record.");

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
        if(carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new RuntimeException("Unable to delete record");
        }
    }

}
