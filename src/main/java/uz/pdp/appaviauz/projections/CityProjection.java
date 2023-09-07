package uz.pdp.appaviauz.projections;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appaviauz.entity.City;
import uz.pdp.appaviauz.entity.Country;


@Projection(types = City.class)
public interface CityProjection {
    Integer getId();

    String getName();

    Country getCountry();
}

