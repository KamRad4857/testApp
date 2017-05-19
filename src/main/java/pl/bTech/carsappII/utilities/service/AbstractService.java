package pl.bTech.carsappII.utilities.service;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bTech.carsappII.utilities.entity.SerializableEntityInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public abstract class AbstractService implements ServiceInterface {

    protected JpaRepository repository;


    public AbstractService(JpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SerializableEntityInterface> getAll() {
        return repository.findAll();
    }

    public static String serializableEntityListToJsonArray(List<SerializableEntityInterface> serializableEntityList) {
        List<String> jsonStringList = new ArrayList<>();
        for (SerializableEntityInterface serializableEntity : serializableEntityList) {
            jsonStringList.add(serializableEntity.toJson());
        }
        return String.format("[%s]", String.join(",", jsonStringList));
    }

    @Override
    public void add(SerializableEntityInterface entitiesInterface) {
        repository.save(entitiesInterface);
        repository.flush();
    }

    @Override
    public void edit(SerializableEntityInterface entitity) {
        repository.save(entitity);
        repository.flush();
    }

    @Override
    public void delete(SerializableEntityInterface serializableEntity) {
        repository.delete(serializableEntity);
    }

    @Override
    public void deleteById(int id) {
        repository.delete(id);
    }
}
