package com.reservation.reservationsalle.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDTO {
    public String nomPersonne;
    public LocalDate dateReservation;
    public LocalTime heureDebut;
    public LocalTime heureFin;
    public Long salleId;
    public Long userId;
}
