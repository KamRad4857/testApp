package pl.bTech.carsappII.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.bTech.carsappII.car.entity.CarEntity;
import pl.bTech.carsappII.car.service.CarService;

import java.io.IOException;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@RestController
@RequestMapping(path = "car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @Transactional
    @GetMapping(path = "")
    public String getAllCarsAction() {
        return CarService.serializableEntityListToJsonArray(carService.getAll());
    }

    @Transactional
    @PostMapping(path = "/add")
    public String addCarAction(@RequestBody String json) throws IOException {
        CarEntity carEntity = carService.add(json);
        return carEntity.toJson();
    }

    @Transactional
    @DeleteMapping(path = "/delete/{id}")
    public String deleteCarByIdAction(@PathVariable int id) throws IOException {
        carService.deleteById(id);
        return "{\"status\":\"OK\"}";
    }

    @Transactional
    @PutMapping(path = "/edit/{id}")
    public String editCarAction(@PathVariable int id, @RequestBody String json) throws IOException {
        CarEntity carEntity = carService.edit(id, json);
        return carEntity.toJson();
    }
}
