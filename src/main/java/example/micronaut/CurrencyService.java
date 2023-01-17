package example.micronaut;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.DeserializationFeature;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CurrencyService {
    //currencies

    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();
    private static final ObjectMapper oMapper = new ObjectMapper();

    static {
        oMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        oMapper.enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);
    }

    @Value("${api.currencies.rates}")
    private String GET_RATES_ENDPOINT;
    @Value("${api.key}")
    private String CREDENTIALS;


    String getBestRate(int coin) {

        log.info("Finds best buy rate for coin-id={}", coin);

        int EURO = 17;
        int CHF = 4;

        double basicBTCtoEURO = 0;
        double basicBTCtoCHF = 0;

        final CompletableFuture<Double> futureBTCtoEURO = getRate(coin, EURO);
        final CompletableFuture<Double> futureBTCtoCHF = getRate(coin, CHF);

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureBTCtoEURO, futureBTCtoCHF);
        try {
            combinedFuture.get();

            if (!futureBTCtoEURO.isCompletedExceptionally() && futureBTCtoEURO.isDone()) {
                basicBTCtoEURO = futureBTCtoEURO.get();
            }
            if (!futureBTCtoCHF.isCompletedExceptionally() && futureBTCtoCHF.isDone()) {
                basicBTCtoCHF = futureBTCtoCHF.get();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return findMin(basicBTCtoEURO, basicBTCtoCHF);
    }

    /**
     * Compare the 2 rates, rateInEURO and rateInCHF
     * returns the currency , with the best rate, "EURO" or "CHF"
     */
    private String findMin(double rateInEURO, double rateInCHF) {
        if (rateInEURO == 0 && rateInCHF == 0)
            return "API non responding";
        return (Double.compare(rateInEURO, rateInCHF) < 0) ? "EURO" : "CHF";
    }

    /**
     * Returns a CompletableFuture, to find the rate to buy fromCurrency, toCurrency
     * In case of exception , returns 0
     */
    private CompletableFuture<Double> getRate(int fromCurrency, int toCurrency) {
        log.info(" getRate :fromCurrency  " + fromCurrency + " to currency " + toCurrency);
        return CompletableFuture.supplyAsync(() -> sendRequest(fromCurrency, toCurrency))
                .exceptionally(exception -> {
                    System.err.println(exception);
                    return 0d;
                });
    }


    /**
     * Send the API request to Get the Rate
     */
    private Double sendRequest(int fromCurrency, int toCurrency) {
        System.out.println("sending");
        try {
            final Request request = createRequest(fromCurrency, toCurrency);
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            Result result = oMapper.readValue(body, Result.class);
            log.info(" getRate :fromCurrency={} to currency={} ,response ={}", fromCurrency, toCurrency, body);

            if (result == null)
                return 0d;

            String basic = (result.data != null) ? result.data.get(0)[0].basic : null;
            return (basic != null) ? Double.parseDouble(basic) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }

    /**
     * Creates the Request to  investing-cryptocurrency-markets API
     */
    private Request createRequest(int fromCurrency, int toCurrency) {
        String url = GET_RATES_ENDPOINT + "?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency + "&lang_ID=1&time_utc_offset=28800";
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("x-rapidapi-host", "investing-cryptocurrency-markets.p.rapidapi.com")
                .addHeader("x-rapidapi-key", CREDENTIALS)
                .build();
        return request;
    }


    static class Result {
        @JsonProperty("data")
        List<Rate[]> data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Rate {
        @JsonProperty("currency_ID")
        int currency_ID;
        @JsonProperty("basic")
        String basic;
        @JsonProperty("reverse")
        String reverse;
        @JsonProperty("digits")
        int digits;
    }
}
