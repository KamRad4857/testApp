package pl.bTech.carsappII.color.validator;

        import pl.bTech.carsappII.color.entity.ColorEntity;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public class ColorValidator {

    public static boolean isValid(ColorEntity color) {
        if (color == null) {
            return false;
        }
        if (color.getName().length() < 2) {
            return false;
        }
        return true;
    }
}
