package uz.pdp.appaviauz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appaviauz.entity.Aerodrome;
import uz.pdp.appaviauz.entity.City;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AerodromeRepository extends JpaRepository<Aerodrome, UUID> {

    @Query(nativeQuery = true,
            value = "select * from aerodrom where id in " +
                    "(select aerodrom_id from aerodrom_planes where planes_id = :plane_id)")
    List<Aerodrome> findByPlanesById(UUID plane_id);

    Optional<Aerodrome> findByNameAndCity(String name, City city);
}
