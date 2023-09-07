package uz.pdp.appaviauz.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appaviauz.entity.Ticket;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query(nativeQuery = true, value =
            "select * from ticket t where t.from_id  " +
                    "in (select f.id from flight_schedule f where " +
                    "    from_id  in (select a1.id from aerodrome a1 where a1.city_id=?1) and " +
                    "    to_id  in (select a2.id from aerodrome a2 where a2.city_id=?2) and  " +
                    "    DATE(flight_time)=DATE(?3) " +
                    "   ) and t.is_deleted=false offset ?4 *?5 limit ?5")
    List<Ticket> fromBy(Integer fromCity, Integer toCity, Date when,
                        Integer page, Integer size);

    @Query(nativeQuery = true, value =
            "select * from ticket t where t.from_id in " +
                    " (select f1.id from flight_schedule f1 where DATE(f1.flight_time)=DATE(?3) and" +
                    "    f1.from_id  in (select a1.id from aerodrome a1 where a1.city_id=?1) and" +
                    "    f1.to_id  in (select a2.id from aerodrome a2 where a2.city_id=?2)" +
                    " ) and t.back_id in" +
                    " (select f2.id from flight_schedule f2 where DATE(f2.flight_time)=DATE(?4) and" +
                    "    f2.from_id  in (select a1.id from aerodrome a1 where a1.city_id=?2) and" +
                    "    f2.to_id  in (select a2.id from aerodrome a2 where a2.city_id=?1)" +
                    " ) and t.is_deleted=false")
    List<Ticket> fromAndBackBy(Integer fromCity, Integer backCity, Date when, Date back, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select * from ticket where is_deleted=false and (from_id=?1 or back_id=?1)")
    List<Ticket> findByFlight(UUID flightId, Pageable pageable);

    @Query(nativeQuery = true,
            value = "select count(*) from ticket where is_deleted=false and (from_id=?1 or back_id=?1)")
    Long countByFlight(UUID flightId);

    long countAllByCount(Integer count);

    List<Ticket> findAllByCount(Integer count);


}
