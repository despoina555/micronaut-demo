package example.micronaut;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Introspected
public class Result {

    //total count of entries in db table greek_cars
    long count;
    //current date
    String date;
}
