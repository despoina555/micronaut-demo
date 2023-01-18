package example.micronaut.car;


import example.micronaut.car.domain.GreekCars;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Slf4j
public class CarService {
    @Inject
    private GreekCarsRepository greekCarsRepository;


    public Optional<GreekCars> getLatestModel(String make, String model) {
        log.debug("get Latest make={} , model={}", make, model);
        return greekCarsRepository.findByMakeAndModelOrderByEndYearDesc(make, model);
    }


    public Long count() {
        return greekCarsRepository.count();
    }


    public Page<GreekCars> list(int page, int size) {

        Pageable paging = Pageable.from(page, size);

        Page<GreekCars> r = greekCarsRepository.list(paging);
        return r;
    }
}
