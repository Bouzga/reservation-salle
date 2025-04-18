package com.reservation.reservationsalle.repository;

import com.reservation.reservationsalle.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.salle.id = :salleId " +
            "AND r.dateReservation = :date " +
            "AND r.heureDebut < :end " +
            "AND r.heureFin > :start")
    boolean isSalleReservee(@Param("salleId") Long salleId,
                            @Param("date") LocalDate date,
                            @Param("start") LocalTime start,
                            @Param("end") LocalTime end);

}
