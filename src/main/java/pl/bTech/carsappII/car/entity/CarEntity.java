package pl.bTech.carsappII.car.entity;

import org.hibernate.validator.constraints.NotEmpty;
import pl.bTech.carsappII.color.entity.ColorEntity;
import pl.bTech.carsappII.model.entity.ModelEntity;
import pl.bTech.carsappII.utilities.entity.SerializableEntityAbstract;
import pl.bTech.carsappII.utilities.entity.SerializableEntityInterface;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@XmlRootElement
@Entity
@Table(name = "car")
public class CarEntity extends SerializableEntityAbstract implements SerializableEntityInterface {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    protected int id;

    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    protected String name;

    @NotNull
    @Column(precision = 10, scale = 2)
    protected double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NotNull
    @ManyToOne(targetEntity = ModelEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    protected ModelEntity model;

    @NotNull
    @ManyToOne(targetEntity = ColorEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    protected ColorEntity color;

    public CarEntity(String name, ModelEntity model, ColorEntity color) {
        this.name = name;
        this.model = model;
        this.color = color;
    }

    public ModelEntity getModel() {
        return model;
    }

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
    }

    public CarEntity() {
    }

    public void setModel(ModelEntity model) {
        this.model = model;
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
        return new String[]{"car","colorEntities","carEntities","modelEntities"};
    }

    @Override
    public String toString() {
        return String.format("CarEntity: id= %s, name= %s, price= %s, model= %s, color= %s", id, name, price, model.getName(), color);
    }
}
