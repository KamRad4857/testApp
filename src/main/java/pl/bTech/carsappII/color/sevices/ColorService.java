package pl.bTech.carsappII.color.sevices;

import org.springframework.stereotype.Service;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.color.repository.ColorRepository;
import pl.bTech.carsappII.color.validator.ColorValidator;
import pl.bTech.carsappII.utilities.service.AbstractService;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Service
public class ColorService extends AbstractService {

    public ColorService(ColorRepository repository) {
        super(repository);
    }

    public ColorEntity add(String json) {
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.fromJson(json);
        if (ColorValidator.isValid(colorEntity)) {
            super.add(colorEntity);
            return colorEntity;
        }
        return null;
    }

    public ColorEntity edit(int id, String json) {
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.fromJson(json);
        colorEntity.setId(id);
        if (ColorValidator.isValid(colorEntity)) {
            super.edit(colorEntity);
            return colorEntity;
        }
        return null;
    }
}
