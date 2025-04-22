package com.reservation.reservationsalle.controller;

import com.reservation.reservationsalle.dto.ReservationDTO;
import com.reservation.reservationsalle.model.Reservation;
import com.reservation.reservationsalle.model.Salle;
import com.reservation.reservationsalle.model.StatutReservation;
import com.reservation.reservationsalle.model.User;
import com.reservation.reservationsalle.repository.ReservationRepository;
import com.reservation.reservationsalle.repository.SalleRepository;
import com.reservation.reservationsalle.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/reservations")
public class ReservationController {

    private ReservationRepository reservationRepository;
    private SalleRepository salleRepository;
    private UserRepository userRepository;

    public ReservationController(
            ReservationRepository reservationRepository,
            SalleRepository salleRepository,
            UserRepository userRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.salleRepository = salleRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("allReservations")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @PostMapping("createReservation")
    public Reservation createReservation(@RequestBody ReservationDTO dto) {
        // Vérifie si la salle est disponible
        boolean salleOccupee = reservationRepository.isSalleReservee(
                dto.salleId, dto.dateReservation, dto.heureFin, dto.heureDebut);

        if (salleOccupee) {
            throw new RuntimeException("Salle déjà réservée à ce créneau");
        }

        // Récupérer la salle et l’utilisateur
        Salle salle = salleRepository.findById(dto.salleId)
                .orElseThrow(() -> new RuntimeException("Salle non trouvée"));
        User user = userRepository.findById(dto.userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Créer la réservation
        Reservation reservation = Reservation.builder()
                .nomPersonne(dto.nomPersonne)
                .dateReservation(dto.dateReservation)
                .heureDebut(dto.heureDebut)
                .heureFin(dto.heureFin)
                .salle(salle)
                .user(user)
                .statut(StatutReservation.EN_ATTENTE)
                .build();

        return reservationRepository.save(reservation);
    }


    @PutMapping("updateReservation/{id}")
    public Reservation updateReservation(@RequestBody Reservation newReservation, @PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setNomPersonne(newReservation.getNomPersonne());
                    reservation.setHeureDebut(newReservation.getHeureDebut());
                    reservation.setHeureFin(newReservation.getHeureFin());
                    reservation.setDateReservation(newReservation.getDateReservation());
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new RuntimeException("Reservation non trouvée avec l'id : " + id));
    }


    @DeleteMapping("deleteReservation/{id}")
    public void deleteReservation(@PathVariable Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation non trouvéz avec id " + id);
        }
        reservationRepository.deleteById(id);
    }
}
