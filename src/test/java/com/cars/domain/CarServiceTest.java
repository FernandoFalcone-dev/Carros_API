package com.cars.domain;

import com.cars.domain.dto.CarDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void testGetCars() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Test Car");
        car.setType("Sedan");

        when(carRepository.findAll()).thenReturn(List.of(car));

        List<CarDTO> cars = carService.getCars();

        assertEquals(1, cars.size());
        assertEquals(car.getName(), cars.getFirst().getName());
        assertEquals(car.getType(), cars.getFirst().getType());
    }

    @Test
    public void testGetCarById() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Test Car");
        car.setType("Sedan");

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Optional<CarDTO> carDto = carService.getCarById(1L);

        assertTrue(carDto.isPresent());
        assertEquals(car.getName(), carDto.get().getName());
        assertEquals(car.getType(), carDto.get().getType());
    }

    @Test
    public void testGetCarsByType() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Test Car");
        car.setType("Sedan");

        when(carRepository.findByType("Sedan")).thenReturn(List.of(car));

        List<CarDTO> cars = carService.getCarsByType("Sedan");

        assertEquals(1, cars.size());
        assertEquals(car.getName(), cars.getFirst().getName());
        assertEquals(car.getType(), cars.getFirst().getType());
    }

    @Test
    public void testInsert() {
        Car car = new Car();
        car.setName("Test Car");
        car.setType("Sedan");

        when(carRepository.save(car)).thenReturn(car);

        CarDTO carDto = carService.insert(car);

        assertEquals(car.getName(), carDto.getName());
        assertEquals(car.getType(), carDto.getType());
    }

    @Test
    public void testUpdate() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Test Car");
        car.setType("Sedan");

        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setName("Updated Car");
        updatedCar.setType("SUV");

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(updatedCar);

        CarDTO carDto = carService.update(updatedCar, 1L);

        assertEquals(updatedCar.getName(), carDto.getName());
        assertEquals(updatedCar.getType(), carDto.getType());
    }

    @Test
    public void testDelete() {
        Car car = new Car();
        car.setId(1L);
        car.setName("Test Car");
        car.setType("Sedan");

        when(carRepository.existsById(1L)).thenReturn(true);

        carService.delete(1L);

        verify(carRepository, times(1)).deleteById(1L);

        when(carRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> carService.delete(1L));
    }
}