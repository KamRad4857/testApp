package pl.bTech.carsappII.brands.sevices;

import org.springframework.stereotype.Service;
import pl.bTech.carsappII.brands.repository.BrandRepository;
import pl.bTech.carsappII.utilities.service.AbstractService;

/**
 * @author Kamil Radek<kamil.radek@btech.pl>
 */
@Service
public class BrandService extends AbstractService {

    protected BrandRepository repository;

    public BrandService(BrandRepository repository) {
        super(repository);
    }
}
