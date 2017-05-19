package pl.bTech.carsappII.model.service;

import org.springframework.stereotype.Service;
import pl.bTech.carsappII.brands.entity.BrandEntity;
import pl.bTech.carsappII.brands.repository.BrandRepository;
import pl.bTech.carsappII.model.entity.ModelEntity;
import pl.bTech.carsappII.model.repository.ModelRepository;
import pl.bTech.carsappII.model.validator.ModelValidator;
import pl.bTech.carsappII.utilities.service.AbstractService;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Service
public class ModelService extends AbstractService {

    protected BrandRepository brandRepository;

    public ModelService(BrandRepository brandRepository, ModelRepository modelRepository) {
        super(modelRepository);
        this.brandRepository = brandRepository;
    }

    public ModelEntity getOneById(int id) {
        return ((ModelRepository) this.repository).findOneById(id);
    }

    public ModelEntity transformFromJson(ModelEntity modelEntity, String json, int id) {
        modelEntity.fromJson(json);
        modelEntity.setId(id);
        return modelEntity;
    }

    public ModelEntity add(String json) {
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.fromJson(json);
        int brandId = modelEntity.getBrand().getId();
        BrandEntity brandEntity = this.brandRepository.findById(brandId);
        modelEntity.setBrand(brandEntity);
        if (ModelValidator.isValid(modelEntity)) {
            super.add(modelEntity);
            return modelEntity;
        }
        return null;
    }

    public ModelEntity edit(int id, String json) {
        ModelEntity modelEntity = ((ModelRepository) this.repository).findOneById(id);
        modelEntity.fromJson(json);
        int brandId = modelEntity.getBrand().getId();
        BrandEntity brandEntity = this.brandRepository.findById(brandId);
        modelEntity.setBrand(brandEntity);
        if (ModelValidator.isValid(modelEntity)) {
            super.edit(modelEntity);
            return modelEntity;
        }
        return null;
    }
}
