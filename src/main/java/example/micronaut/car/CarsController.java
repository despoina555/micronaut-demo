package example.micronaut.car;

import example.micronaut.car.domain.GreekCars;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller("/cars")
public class CarsController {

    @Inject
    private CarService carService;

    /**
     * Run on Browser:  http://localhost:8080/cars/
     * Method :
     * - renders the View = name : cars-view.html
     */
    @Get("/")
    ModelAndView mainView() {

        long count = carService.count();

        final Map<String, Object> m = new HashMap<>();
        m.put("count", count);

        return new ModelAndView("cars-view", m);
    }


    /**
     * curl http://localhost:8080/cars/params?make=FIAT&model=500
     * Method :
     * - renders the View = name : cars-view.html
     * Search for car with params = {make, model}
     * with the result data
     */
    @Get("/params")
    ModelAndView getLatestModel(@NotNull @QueryValue String make, @NotNull @QueryValue String model) {

        final Optional<GreekCars> opt = carService.getLatestModel(make, model);

        if (!opt.isPresent())
            return new ModelAndView("not-found.html", null);

        final Map<String, Object> m = new HashMap<>();
        m.put("car", opt.get());

        return new ModelAndView("cars-view", m);
    }


}
