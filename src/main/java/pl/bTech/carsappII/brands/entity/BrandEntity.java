package pl.bTech.carsappII.brands.entity;

import pl.bTech.carsappII.model.entity.ModelEntity;
import pl.bTech.carsappII.utilities.entity.SerializableEntityAbstract;
import pl.bTech.carsappII.utilities.entity.SerializableEntityInterface;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Entity
@Table(name = "brand")
public class BrandEntity extends SerializableEntityAbstract implements SerializableEntityInterface {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    protected int id;

    @NotNull
    @Column(name = "name", unique = true, nullable = false)
    protected String name;

    @OneToMany(targetEntity = ModelEntity.class, mappedBy = "brand", fetch = FetchType.LAZY)
    protected List<ModelEntity> modelEntities = new ArrayList<>();

    public List<ModelEntity> getModelEntities() {
        return modelEntities;
    }

    public void addModelToModelList(ModelEntity model) {
        this.modelEntities.add(model);
        model.setBrand(this);
    }

    public void setModelEntities(List<ModelEntity> modelEntities) {
        for (ModelEntity model : modelEntities) {
            model.setBrand(this);
        }
        this.modelEntities = modelEntities;
    }

    public BrandEntity() {
    }

    public BrandEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected String[] getFieldsToSkip() {
        return new String[]{"brand","modelEntities","BrantEntities"};
    }

    @Override
    public String toString() {
        return String.format("BrandEntity: id= %s, name= %s", id, name);
    }
}
