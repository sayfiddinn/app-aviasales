package uz.pdp.appaviauz.repository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appaviauz.entity.Country;
import uz.pdp.appaviauz.projections.CountryProjection;

@RepositoryRestResource(path = "country",excerptProjection = CountryProjection.class)
@SecurityRequirement(name = "bearerAuth")
public interface CountryRepository extends JpaRepository<Country, Integer> {


}
