package pl.bTech.carsappII.color.entity;

import org.hibernate.validator.constraints.NotEmpty;
import pl.bTech.carsappII.car.entity.CarEntity;
import pl.bTech.carsappII.utilities.entity.SerializableEntityAbstract;
import pl.bTech.carsappII.utilities.entity.SerializableEntityInterface;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Entity
@Table(name = "color")
public class ColorEntity extends SerializableEntityAbstract implements SerializableEntityInterface {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    protected int id;

    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    protected String name;

    @OneToMany(targetEntity = CarEntity.class, mappedBy = "color", fetch = FetchType.LAZY)
    protected List<CarEntity> carEntities = new ArrayList<>();

    public List<CarEntity> getCarEntities() {
        return carEntities;
    }

    public void setCarEntities(List<CarEntity> carEntities) {
        this.carEntities = carEntities;
        for (CarEntity car : carEntities) {
            car.setColor(this);
        }
    }

    public ColorEntity() {
    }

    public ColorEntity(String name) {
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
        return new String[]{"color","carEntities"};
    }

    @Override
    public String toString() {
        return String.format("ColorEntity: id= %s, name= %s", id, name);
    }
}
