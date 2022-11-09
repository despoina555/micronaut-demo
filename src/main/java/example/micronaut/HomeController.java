package example.micronaut;


import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller("/")
public class HomeController {

    @Inject
    CurrencyService currencyService;

    /**
     * Run on Browser:  http://localhost:8080/
     * Method :
     * - renders the View = name : index.html
     */
    @Get("/")
    ModelAndView index() {

        final String os = System.getProperty("os.name").toLowerCase();
        final Map<String, Object> model = new HashMap<>();
        model.put("os", os);

        return new ModelAndView("index", model);
    }

    @Get("/currencies")
    ModelAndView compareCurrenciesView() {
        return new ModelAndView("compare-currencies", null);
    }

    @Get("/currencies/best-rate")
    public ModelAndView getRates(@NotNull @QueryValue int coin) {
        String currencyToBuy = currencyService.getBestRate(coin);

        final Map<String, Object> model = new HashMap<>();
        model.put("currency", currencyToBuy);

        return new ModelAndView("compare-currencies", model);
    }

}
