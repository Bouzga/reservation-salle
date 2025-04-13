package com.reservation.reservationsalle.repository;

import com.reservation.reservationsalle.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSalle extends JpaRepository<Reservation, Long> {
}
