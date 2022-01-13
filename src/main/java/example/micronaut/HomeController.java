package example.micronaut;

import example.micronaut.domain.GreekCars;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Controller("/cars")
public class HomeController {

    @Inject
    private GreekCarsRepository greekCarsRepository;

    /** Run on Browser:  http://localhost:8080/cars/modelAndView
     *
     * Method :
     * - renders the View = name : index.html
     * - pass model data:  Result result (Annotation @Introspected) */
    @Get("/modelAndView")
    ModelAndView modelAndView() {
        Result result = new Result();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        result.date = date.format(formatter);

        long count = greekCarsRepository.count();
        result.count = count;

        return new ModelAndView("index", result);
    }


    /**
     * curl http://localhost:8080/cars/params?model=500&taxhp=10
     */
    @Get("/params")
    public Optional<GreekCars> getLatestModel(@NotNull @QueryValue String model, @NotNull @QueryValue String taxhp) {
        log.info("get Latest Model {} , taxhp {}", model, taxhp);
        return greekCarsRepository.findByModelAndTaxhpOrderByEndYearDesc(model, taxhp);
    }

    /** curl http://localhost:8080/cars/list */
    @Get(value = "/list")
    public Iterable<GreekCars> getAll() {
        return greekCarsRepository.findAll();
    }
}
