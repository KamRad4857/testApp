package pl.bTech.carsappII.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bTech.carsappII.car.entity.CarEntity;

import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

    List<CarEntity> findAll();

    CarEntity findOneById(int id);
}
