package example.micronaut;

import example.micronaut.domain.GreekCars;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import java.util.Optional;



@JdbcRepository(dialect = Dialect.POSTGRES)
public interface GreekCarsRepository extends PageableRepository<GreekCars, Long> {
    Optional<GreekCars> findByModel(String model);

    Optional<GreekCars> findByModelAndTaxhpOrderByEndYearDesc(String model,String taxhp);
}
