package pl.bTech.carsappII.brands.validator;

import pl.bTech.carsappII.brands.entity.BrandEntity;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public class BrandValidator {

    public static boolean isValid(BrandEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getName().length() < 2) {
            return false;
        }
        return true;
    }
}
