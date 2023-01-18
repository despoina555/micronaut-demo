package example.micronaut.currencies;


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
@Controller("/currencies")
public class CurrenciesController {


    @Inject
    CurrencyService currencyService;


    @Get("/")
    ModelAndView index() {
        return new ModelAndView("compare-currencies", null);
    }


    @Get("/best-rate")
    public ModelAndView getRates(@NotNull @QueryValue int coin) {
        String currencyToBuy = currencyService.getBestRate(coin);

        final Map<String, Object> model = new HashMap<>();
        model.put("currency", currencyToBuy);

        return new ModelAndView("compare-currencies", model);
    }

}
