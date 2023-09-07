package uz.pdp.appaviauz.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appaviauz.entity.AerodromeFlightsHistory;

import java.util.List;
import java.util.UUID;

@Repository
//@ApiKeyAuthDefinition(key = "Authorization", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, name = "bearerAuth")
public interface HistoryFlightAeroRepo extends JpaRepository<AerodromeFlightsHistory, UUID> {
    List<AerodromeFlightsHistory> findAllByAerodrome_Id(UUID aerodrome_id, Pageable pageable);

    Long countByAerodrome_Id(UUID aerodrome_id);

    List<AerodromeFlightsHistory> findAllByPlane_Id(UUID plane_id, Pageable pageable);

    Long countByPlane_Id(UUID plane_id);
}
