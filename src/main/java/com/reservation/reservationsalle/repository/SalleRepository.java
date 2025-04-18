package com.reservation.reservationsalle.repository;

import com.reservation.reservationsalle.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Long> {

    @Query("SELECT s FROM Salle s WHERE s.id NOT IN (" +
            "SELECT r.salle.id FROM Reservation r " +
            "WHERE r.dateReservation = :date " +
            "AND ((r.heureDebut < :endTime) AND (r.heureFin > :startTime))" +
            ")")
    List<Salle> findAvailableSalles(@Param("date") LocalDate date,
                                    @Param("startTime") LocalTime startTime,
                                    @Param("endTime") LocalTime endTime);

}
