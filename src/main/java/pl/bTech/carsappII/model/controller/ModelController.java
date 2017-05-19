package pl.bTech.carsappII.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.bTech.carsappII.model.entity.ModelEntity;
import pl.bTech.carsappII.model.service.ModelService;

import java.io.IOException;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@RestController
@RequestMapping(path = "model")
public class ModelController {

    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @Transactional
    @GetMapping(path = "")
    public String getAllModelsAction() {
        return ModelService.serializableEntityListToJsonArray(modelService.getAll());
    }

    @PostMapping(path = "/add")
    public String addModelAction(@RequestBody String json) throws IOException {
        ModelEntity modelEntity = modelService.add(json);
        return modelEntity.toJson();
    }

    @Transactional
    @DeleteMapping(path = "/delete/{id}")
    public String deleteModelByIdAction(@PathVariable int id) throws IOException {
        modelService.deleteById(id);
        return "{\"status\":\"OK\"}";
    }

    @PutMapping(path = "/edit/{id}")
    public String editModelAction(@PathVariable int id, @RequestBody String json) throws IOException {
        ModelEntity modelEntity = modelService.edit(id, json);
        return modelEntity.toJson();
    }
}
