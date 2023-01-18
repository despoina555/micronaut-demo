package example.micronaut;


import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller("/")
public class HomeController {

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

}
