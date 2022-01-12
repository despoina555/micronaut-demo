package example.micronaut;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.micronaut.domain.GreekCars;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;


@MicronautTest
class MicronautguideTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    private HttpClient client;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    public void testHello() {
        HttpRequest<String> request = HttpRequest.GET("/cars/500?taxhp=10");
        String body = client.toBlocking().retrieve(request);

        GreekCars car = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            car = objectMapper.readValue(body, GreekCars.class);
        } catch (Exception e) {

        }
        assertNotNull(car);
        assertEquals("10", car.getTaxhp());
    }


}
