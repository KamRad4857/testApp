package pl.bTech.carsappII.car.service;

import org.springframework.stereotype.Service;
import pl.bTech.carsappII.brands.repository.BrandRepository;
import pl.bTech.carsappII.car.entity.CarEntity;
import pl.bTech.carsappII.car.repository.CarRepository;
import pl.bTech.carsappII.car.validator.CarValidator;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.color.repository.ColorRepository;
import pl.bTech.carsappII.model.entity.ModelEntity;
import pl.bTech.carsappII.model.repository.ModelRepository;
import pl.bTech.carsappII.utilities.service.AbstractService;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Service
public class CarService extends AbstractService {

    protected ModelRepository modelRepository;
    protected ColorRepository colorRepository;
    protected BrandRepository brandRepository;

    public CarService(BrandRepository brandRepository, ModelRepository modelRepository, ColorRepository colorRepository, CarRepository carRepository) {
        super(carRepository);
        this.colorRepository = colorRepository;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
    }

    public CarEntity getOneById(int id) {
        return ((CarRepository) this.repository).findOneById(id);
    }

    public CarEntity transformFromJson(CarEntity carEntity, String json, int id) {
        carEntity.fromJson(json);
        carEntity.setId(id);
        return carEntity;
    }

    public CarEntity add(String json) {
        CarEntity carEntity = new CarEntity();
        carEntity.fromJson(json);
        int modelId = carEntity.getModel().getId();
        int colorId = carEntity.getColor().getId();
        ModelEntity modelEntity = this.modelRepository.findOneById(modelId);
        ColorEntity colorEntity = this.colorRepository.findOneById(colorId);
        carEntity.setColor(colorEntity);
        carEntity.setModel(modelEntity);

        if (CarValidator.isValid(carEntity)) {
            super.add(carEntity);
            return carEntity;
        }
        return null;
    }

    public CarEntity edit(int id, String json) {
        CarEntity carEntity = ((CarRepository) this.repository).findOneById(id);
        carEntity.fromJson(json);
        int modelId = carEntity.getModel().getId();
        int colorId = carEntity.getColor().getId();
        ModelEntity modelEntity = this.modelRepository.findOneById(modelId);
        ColorEntity colorEntity = this.colorRepository.findOneById(colorId);

        carEntity.setColor(colorEntity);
        carEntity.setModel(modelEntity);
        if (CarValidator.isValid(carEntity)) {
            super.edit(carEntity);
            return carEntity;
        }
        return null;
    }
}
