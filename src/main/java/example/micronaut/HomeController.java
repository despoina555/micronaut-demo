package example.micronaut;

import example.micronaut.domain.GreekCars;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Slf4j
@Controller("/cars")
public class HomeController {

    @Inject
    private GreekCarsRepository greekCarsRepository;

    /** curl http://localhost:8080/cars/model?taxhp=tax-hp*/
    @Get("/{model}")
    public Optional<GreekCars> getLatestModel(@NotBlank String model, @Nullable @QueryValue String taxhp ) {
        log.info("get Latest Model {} , taxhp {}",model,taxhp);
        return greekCarsRepository.findByModelAndTaxhpOrderByEndYearDesc(model,taxhp);
    }

    @Get(value = "/list")
    public Iterable<GreekCars> getAll() {
        return greekCarsRepository.findAll();
    }
}
