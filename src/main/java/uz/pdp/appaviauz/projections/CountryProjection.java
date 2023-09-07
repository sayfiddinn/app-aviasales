package uz.pdp.appaviauz.projections;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appaviauz.entity.Country;


@Projection(types = Country.class)
public interface CountryProjection {
    Integer getId();

    String getName();

}
