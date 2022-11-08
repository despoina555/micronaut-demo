package example.micronaut;

import example.micronaut.car.CarService;
import example.micronaut.car.domain.GreekCars;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
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
    @Client("/")
    private HttpClient client;

    @Inject
    private CarService carService;

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

        assertNotNull(car);
        assertEquals(model, car.getModel());
    }

    @Test
    public void testGetRates() {
        try {
            HttpRequest<String> request = HttpRequest.GET("/currencies/best-rate?coin=189");
            String body = client.toBlocking().retrieve(request);

            String rate = body;
            System.out.println("Rate=" + rate);

            assertNotNull(rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
