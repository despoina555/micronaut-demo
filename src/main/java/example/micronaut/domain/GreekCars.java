package example.micronaut.domain;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.*;


import java.math.BigInteger;

@Getter
@Setter
@Introspected
@MappedEntity
public class GreekCars{
    @Id
    BigInteger id;
    String uniqueIdentity;
    String vehicleType;
    String make;
    String model;
    String versionName;
    String taxhp;
    String cc;
    String doors;
    String fuelType;
    String bodyType;
    String seats;
    Integer startYear;
    Integer endYear;
    String acceleration;
    Integer emissions;
    String iagMake;
    String iagModel;
    Integer tonnage;
}
