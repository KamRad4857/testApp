package pl.bTech.carsappII.utilities.entity;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
public interface SerializableEntityInterface extends AutoIdInterface{
    String toJson();

    boolean fromJson(String jsonString);
}
