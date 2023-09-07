package uz.pdp.appaviauz.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appaviauz.entity.PurchasedTicket;
import uz.pdp.appaviauz.entity.User;
import uz.pdp.appaviauz.entity.enums.TicketStatus;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PurchasedTicketRepo extends JpaRepository<PurchasedTicket, UUID> {
    List<PurchasedTicket> findAllByFrom_IdOrBack_IdAndTicketStatus(UUID from_id, UUID back_id, TicketStatus ticketStatus, Pageable pageable);

    Long countByFrom_IdOrBack_IdAndTicketStatus(UUID from_id, UUID back_id, TicketStatus ticketStatus);

    default List<PurchasedTicket> findByFlightId(UUID flightId, Pageable pageable) {
        return findAllByFrom_IdOrBack_IdAndTicketStatus(flightId, flightId, TicketStatus.ACTIVE, pageable);
    }

    default Long countByFlightId(UUID flightId) {
        return countByFrom_IdOrBack_IdAndTicketStatus(flightId, flightId, TicketStatus.ACTIVE);
    }

    @Query(nativeQuery = true, value =
            "select * from purchased_ticket where " +
                    "Date(creation_timestamp)=Date(?1) " +
                    "order by creation_timestamp desc offset ?2*?3 limit ?3")
    List<PurchasedTicket> findByTime(Date time, Integer page, Integer size);

    @Query(nativeQuery = true, value =
            "select count(*) from purchased_ticket " +
                    "where Date(creation_timestamp)=Date(?1)")
    Long countByTime(Date time);

    @Query(nativeQuery = true, value =
            "select * from purchased_ticket where " +
                    "Date(creation_timestamp) between Date(?1) and Date(?2) " +
                    "order by creation_timestamp desc offset ?3*?4 limit ?4")
    List<PurchasedTicket> findByTimeBetween(Date start, Date end, Integer page, Integer size);

    @Query(nativeQuery = true, value =
            "select count(*) from purchased_ticket where " +
                    "Date(creation_timestamp) between Date(?1) and Date(?2)")
    Long countByTimeBetween(Date start, Date end);

    //aerodrome-by
    @Query(nativeQuery = true, value =
            "select * from purchased_ticket where (from_id " +
                    "in (select f.id from flight_schedule f where f.to_id=?1 or f.from_id=?1) or back_id " +
                    "in (select f1.id from flight_schedule f1 where f1.to_id=?1 or f1.from_id=?1)) and " +
                    "Date(creation_timestamp)=Date(?2) " +
                    "order by creation_timestamp desc offset ?3*?4 limit ?4")
    List<PurchasedTicket> findByTimeAndAero(UUID aerodromeId, Date time, Integer page, Integer size);

    @Query(nativeQuery = true, value =
            "select count(*) from purchased_ticket where (from_id " +
                    "in (select f.id from flight_schedule f where f.to_id=?1 or f.from_id=?1) or back_id " +
                    "in (select f1.id from flight_schedule f1 where f1.to_id=?1 or f1.from_id=?1)) and " +
                    "Date(creation_timestamp)=Date(?2)")
    Long countByTimeAndAero(UUID aerodromeId, Date time);

    @Query(nativeQuery = true, value =
            "select * from purchased_ticket where (from_id " +
                    "in (select f.id from flight_schedule f where f.to_id=?1 or f.from_id=?1) or back_id " +
                    "in (select f1.id from flight_schedule f1 where f1.to_id=?1 or f1.from_id=?1)) and " +
                    "Date(creation_timestamp) between Date(?2) and Date(?3) " +
                    "order by creation_timestamp desc offset ?4*?5 limit ?5")
    List<PurchasedTicket> findByTimeBetweenAndAero(UUID aerodromeId, Date start, Date end,
                                                   Integer page, Integer size);

    @Query(nativeQuery = true, value =
            "select count(*) from purchased_ticket where (from_id " +
                    "in (select f.id from flight_schedule f where f.to_id=?1 or f.from_id=?1) or back_id " +
                    "in (select f1.id from flight_schedule f1 where f1.to_id=?1 or f1.from_id=?1)) and " +
                    "Date(creation_timestamp) between Date(?2) and Date(?3)")
    Long countByTimeBetweenAndAero(UUID aerodromeId, Date start, Date end);


    List<PurchasedTicket> findByUser(User user);
}
