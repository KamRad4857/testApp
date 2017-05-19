package pl.bTech.carsappII.utilities.service;

import pl.bTech.carsappII.utilities.entity.SerializableEntityInterface;

import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public interface ServiceInterface {

    List<SerializableEntityInterface> getAll();

    void add(SerializableEntityInterface entitiesInterface);

    void edit(SerializableEntityInterface entitiesInterface);

    void delete(SerializableEntityInterface serializableEntity);

    void deleteById(int id);
}
