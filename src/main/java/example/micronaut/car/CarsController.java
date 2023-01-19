package example.micronaut.car;


import example.micronaut.car.domain.GreekCars;
import io.micronaut.data.model.Page;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Controller("/cars")
public class CarsController {

    private static final int RESULTS_PER_PAGE = 50;

    private CarService carService;

    /**
     * use constructor injection
     */
    @Inject
    void populateCarService(CarService carService) {
        this.carService = carService;
    }

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
        m.put("result", false);


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

        log.info("Search car with make={}, model={}", make, model);

        final Optional<GreekCars> opt = carService.getLatestModel(make, model);
        final List<GreekCars> l = new ArrayList<>();

        if (!opt.isPresent())
            return new ModelAndView("not-found.html", null);

        final Map<String, Object> m = new HashMap<>();
        m.put("cars", opt.get());
        m.put("result", true);

        return new ModelAndView("cars-view", m);
    }


    /**
     * http://localhost:8080/cars/list?page=8
     */
    @Get("/list")
    ModelAndView getCars(@QueryValue(defaultValue = "1") Integer page) {

        final Page<GreekCars> r = carService.list(page, RESULTS_PER_PAGE);

        final List<Integer> pager = new ArrayList<Integer>();
        for (int i = 1; i <= r.getTotalPages(); ++i) {
            pager.add(i);
        }

        log.info(" total-pages={}, total-size={}, current={}", r.getTotalPages(), r.getTotalSize(), page);

        final Map<String, Object> m = new HashMap<>();
        m.put("cars", r.getContent());
        m.put("pager", pager);
        m.put("current", page);
        m.put("result", true);

        log.info("list cars, pager-size={} current-page={}", pager.size(), page);

        return new ModelAndView("cars-view", m);
    }

}
