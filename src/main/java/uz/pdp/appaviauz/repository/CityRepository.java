package uz.pdp.appaviauz.repository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appaviauz.entity.City;
import uz.pdp.appaviauz.projections.CityProjection;

@RepositoryRestResource(path = "city",excerptProjection = CityProjection.class)
@SecurityRequirement(name = "bearerAuth")
public interface CityRepository extends JpaRepository<City, Integer> {

}
