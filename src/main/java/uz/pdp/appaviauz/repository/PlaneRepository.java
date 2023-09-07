package uz.pdp.appaviauz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appaviauz.entity.Plane;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, UUID> {
    Optional<Plane> findByPlaneCompanyAndPlaneNumber(String planeCompany, String planeNumber);
}
