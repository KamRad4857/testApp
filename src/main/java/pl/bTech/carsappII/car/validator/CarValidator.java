package pl.bTech.carsappII.car.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.bTech.carsappII.brands.sevices.BrandService;
import pl.bTech.carsappII.car.entity.CarEntity;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.color.validator.ColorValidator;
import pl.bTech.carsappII.model.entity.ModelEntity;
import pl.bTech.carsappII.model.validator.ModelValidator;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public class CarValidator {

    @Autowired
    private static BrandService service;

    public static boolean isValid(CarEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getName().isEmpty()) {
            return false;
        }
        if (entity.getPrice() < 0f) {
            return false;
        }

        ModelEntity model = entity.getModel();
        if (!ModelValidator.isValid(model)) {
            return false;
        }
        ColorEntity color = entity.getColor();
        if (!ColorValidator.isValid(color)) {
            return false;
        }
        return true;
    }
}
