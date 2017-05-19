package pl.bTech.carsappII.brands.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.bTech.carsappII.brands.entity.BrandEntity;
import pl.bTech.carsappII.brands.sevices.BrandService;

import java.io.IOException;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@RestController
@RequestMapping(path = "brand")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Transactional
    @GetMapping(path = "")
    public String getAllBrandsAction() {
        return BrandService.serializableEntityListToJsonArray(brandService.getAll());
    }

    @PostMapping(path = "/add")
    public String addBrandAction(@RequestBody String json) throws IOException {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.fromJson(json);
        brandService.add(brandEntity);
        return brandEntity.toJson();
    }

    @Transactional
    @DeleteMapping(path = "/delete/{id}")
    public String deleteBrandByIdAction(@PathVariable int id) throws IOException {
        brandService.deleteById(id);
        return "{\"status\":\"OK\"}";
    }

    @PutMapping(path = "/edit/{id}")
    public String editBrandAction(@PathVariable int id, @RequestBody String json) throws IOException {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.fromJson(json);
        brandEntity.setId(id);
        brandService.edit(brandEntity);
        return brandEntity.toJson();
    }
}
