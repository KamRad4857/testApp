package pl.bTech.carsappII.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bTech.carsappII.model.entity.ModelEntity;

import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Integer> {

    List<ModelEntity> findAll();

    ModelEntity findOneById(int id);
}
