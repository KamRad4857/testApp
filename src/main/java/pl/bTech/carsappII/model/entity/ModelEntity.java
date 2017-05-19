package pl.bTech.carsappII.model.entity;

import org.hibernate.validator.constraints.NotEmpty;
import pl.bTech.carsappII.brands.entity.BrandEntity;
import pl.bTech.carsappII.car.entity.CarEntity;
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
@Table(name = "model")
public class ModelEntity extends SerializableEntityAbstract implements SerializableEntityInterface{

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    protected int id;

    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    protected String name;

    @NotNull
    @ManyToOne(targetEntity = BrandEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    protected BrandEntity brand;

    @OneToMany(targetEntity = CarEntity.class, mappedBy = "model", fetch = FetchType.LAZY)
    protected List<CarEntity> carEntities = new ArrayList<>();

    public List<CarEntity> getCarEntities() {
        return carEntities;
    }

    public void setCarEntities(List<CarEntity> carEntities) {
        this.carEntities = carEntities;
        for (CarEntity car : carEntities) {
            car.setModel(this);
        }
    }

    public ModelEntity() {
    }

    public ModelEntity(String name, BrandEntity brand) {
        this.name = name;
        this.brand = brand;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
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
        return new String[]{"model","brandEntities","modelEntities","carEntities"};
    }

    @Override
    public String toString() {
        return String.format("ModelEntity: id= %s, name= %s, brand= %s", id, name, brand.getName());
    }
}
