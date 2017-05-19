package pl.bTech.carsappII.brands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bTech.carsappII.brands.entity.BrandEntity;

import java.util.List;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

    List<BrandEntity> findAll();

    BrandEntity findById(int id);
}
