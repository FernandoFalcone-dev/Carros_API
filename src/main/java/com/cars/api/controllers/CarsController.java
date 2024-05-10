package com.cars.api.controllers;

import com.cars.domain.entities.Car;
import com.cars.domain.services.CarService;
import com.cars.domain.dto.CarDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Cars", description = "Cars management APIs")
@RestController
@RequestMapping("/api/v1/cars")
public class CarsController {
    @Autowired
    private CarService carService;

    @Operation(
            summary = "Retrieve all Cars as a list",
            description = "Get a list of Car objects by calling the method. The response is a List of CarDTO objects with id, name and type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping()
    public ResponseEntity<List<CarDTO>> getCars() {

        return ResponseEntity.ok(carService.getCars());
    }

    @Operation(
            summary = "Retrieve a Car by Id",
            description = "Get a Car object by specifying its id. The response is CarDTO object with id, name and type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("id") Long id) {
        CarDTO car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @Operation(
            summary = "Retrieve all Cars by type",
            description = "Get a list of Car objects by specifying their type. The response is a list of CarDTO objects with id, name and type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<CarDTO>> getCarsByType(@PathVariable("type") String type) {
        List<CarDTO> cars = carService.getCarsByType(type);
        return cars.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(cars);
    }

    @Operation(
            summary = "Insert a Car into database",
            description = "Insert a Car object by providing its properties. The response is the CarDTO object that was inserted with id, name and type.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping
    @Secured({ "ROLE_ADMIN" })
    public ResponseEntity<CarDTO> post(@RequestBody Car car){
            CarDTO c = carService.insert(car);
            URI location = getUri(car.getId());
            return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @Operation(
            summary = "Update a Car from database",
            description = "Update a Car object by providing its id and properties. The response is the CarDTO object that was updated with id, name and type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> put(@PathVariable("id") Long id, @RequestBody Car car) {
            car.setId(id);
            CarDTO c = carService.update(car, id);
            return ResponseEntity.ok(c);
    }

    @Operation(
            summary = "Delete a Car from database",
            description = "Delete a Car object by specifying its id. The response is the Http status code + headers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
            carService.delete(id);
            return ResponseEntity.ok().build();
    }
}
