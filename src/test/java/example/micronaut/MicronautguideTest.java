package example.micronaut;

import example.micronaut.car.CarService;
import example.micronaut.car.domain.GreekCars;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class MicronautguideTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    private CarService carService;
    @Inject
    private CurrencyService currencyService;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    /**
     * Tests  CarService
     */
    @Test
    public void testGetLatestModel() {
        final String make = "FIAT";
        final String model = "500";

        Optional<GreekCars> c = carService.getLatestModel(make, model);
        GreekCars car = c.get();
        System.out.println("Car" + c.toString());

        assertNotNull(car);
        assertEquals(model, car.getModel());
    }

    @Test
    public void testGetRates() {
        int coin = 189;
        String currencyToBuy = currencyService.getBestRate(coin);
        System.out.println("Buy currency=" + currencyToBuy);

        assertNotNull(currencyToBuy);

    }


}
