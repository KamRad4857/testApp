package pl.bTech.carsappII.model.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.bTech.carsappII.brands.entity.BrandEntity;
import pl.bTech.carsappII.brands.sevices.BrandService;
import pl.bTech.carsappII.brands.validator.BrandValidator;
import pl.bTech.carsappII.model.entity.ModelEntity;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public class ModelValidator {

    @Autowired
    private static BrandService service;

    public static boolean isValid(ModelEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getName().isEmpty()) {
            return false;
        }
        BrandEntity brand = entity.getBrand();
        if ((brand == null) || (!BrandValidator.isValid(brand))) {
            return false;
        }
        return true;
    }
}
