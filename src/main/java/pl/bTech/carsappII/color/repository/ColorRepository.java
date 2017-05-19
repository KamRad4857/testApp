package pl.bTech.carsappII.color.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bTech.carsappII.color.entity.ColorEntity;

import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Integer> {

    List<ColorEntity> findAll();

    ColorEntity findOneById(int id);
}
