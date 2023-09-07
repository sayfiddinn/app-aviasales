package uz.pdp.appaviauz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appaviauz.entity.FlightSchedule;
import uz.pdp.appaviauz.entity.enums.FlightStatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, UUID> {

    List<FlightSchedule> findAllByFrom_IdOrTo_Id(UUID from_id,
                                                 UUID to_id,
                                                 Pageable flightTime);

    Long countByFrom_IdOrTo_Id(UUID from_id, UUID to_id);

    @Query(nativeQuery = true, value =
            "select * from flight_schedule " +
                    "where (to_id =?1 or from_id =?1) and DATE(flight_time)=DATE(?2) " +
                    "order by flight_time desc offset ?3 *?4 limit ?4")
    List<FlightSchedule> findAllByAeroIdAndFlightTime(UUID from_id,
                                                      Date time,
                                                      Integer page,
                                                      Integer size);

    @Query(nativeQuery = true, value =
            "SELECT count(*) FROM flight_schedule " +
                    "WHERE (to_id = ?1 OR from_id = ?1) " +
                    "AND DATE(flight_time) = DATE(?2)")
    Long countByAeroIdAndFlightTime(UUID aeroId, Date time);

    @Query(nativeQuery = true, value =
            "select * from flight_schedule " +
                    "where (to_id =?1 or from_id =?1) and DATE(flight_time) " +
                    "between DATE(?2) and DATE(?3) " +
                    "order by flight_time desc offset ?4 *?5 limit ?5")
    List<FlightSchedule> findAllByAeroIdAndFlightTimeBetween(UUID aero_id, Date start,
                                                             Date end, Integer page,
                                                             Integer size);

    @Query(nativeQuery = true, value =
            "SELECT count(*) FROM flight_schedule " +
                    "WHERE (to_id = ?1 OR from_id = ?1) " +
                    "AND DATE(flight_time) BETWEEN DATE(?2) and DATE(?3)")
    Long countByAeroIdAndFlightTimeBetween(UUID aero_id,
                                           Date start,
                                           Date end);

    List<FlightSchedule> findAllByFlightStatus(FlightStatus flightStatus,
                                               Pageable pageable);

    Long countByFlightStatus(FlightStatus flightStatus);

    List<FlightSchedule> findAllByPlane_Id(UUID plane_id, Pageable flightTime);

    Long countByPlane_Id(UUID plane_id);

    @Query(nativeQuery = true, value =
            "select * from flight_schedule " +
                    "where plane_id=?1 and DATE(flight_time)=DATE(?2) " +
                    "order by flight_time desc offset ?3 *?4 limit ?4")
    List<FlightSchedule> findAllByPlaneIdAndFlightTime(UUID plane_id, Date flightTime,
                                                       Integer page, Integer size);

    @Query(nativeQuery = true, value =
            "SELECT count(*) FROM flight_schedule " +
                    "WHERE plane_id=?1 " +
                    "AND DATE(flight_time) = DATE(?2)")
    Long countByPlane_IdAndFlightTime(UUID plane_id, Date flightTime);

    @Query(nativeQuery = true, value =
            "select * from flight_schedule " +
                    "where plane_id=?1 and DATE(flight_time) " +
                    "between DATE(?2) and DATE(?3) " +
                    "order by flight_time desc offset ?4 *?5 limit ?5")
    List<FlightSchedule> findAllByPlaneIdAndFlightTimeBetween(UUID plane_id,
                                                              Date start, Date end,
                                                              Integer page,
                                                              Integer size);

    @Query(nativeQuery = true, value =
            "SELECT count(*) FROM flight_schedule " +
                    "WHERE plane_id=?1 AND DATE(flight_time) " +
                    "BETWEEN DATE(?2) and DATE(?3)")
    Long countByPlaneIdAndFlightTimeBetween(UUID plane_id,
                                            Date start,
                                            Date end);

    Optional<FlightSchedule> findByPlaneIdAndFlightTimeAndFlightStatus(UUID plane_id,
                                                                       Timestamp flightTime,
                                                                       FlightStatus flightStatus);

    default boolean findByNew(UUID plane_id, Timestamp flightTime) {
        FlightSchedule flightSchedule = findByPlaneIdAndFlightTimeAndFlightStatus(
                plane_id, flightTime, FlightStatus.NEW)
                .orElse(null);
        return flightSchedule != null;
    }
}
