package pl.bTech.carsappII.color.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.color.sevices.ColorService;

import java.io.IOException;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@RestController
@RequestMapping(path = "color")
public class ColorController {

    private final ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping(path = "")
    public String getAllColorsAction() {
        return ColorService.serializableEntityListToJsonArray(colorService.getAll());
    }

    @PostMapping(path = "/add")
    public String addColorAction(@RequestBody String json) throws IOException {
        ColorEntity colorEntity = colorService.add(json);
        return colorEntity.toJson();
    }

    @Transactional
    @DeleteMapping(path = "/delete/{id}")
    public String deleteColorByIdAction(@PathVariable int id) throws IOException {
        colorService.deleteById(id);
        return "{\"status\":\"OK\"}";
    }

    @PutMapping(path = "/edit/{id}")
    public String editColorAction(@PathVariable int id, @RequestBody String json) throws IOException {
        ColorEntity colorEntity = colorService.edit(id, json);
        return colorEntity.toJson();
    }
}
