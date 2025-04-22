package com.reservation.reservationsalle.controller;

import com.reservation.reservationsalle.model.Salle;
import com.reservation.reservationsalle.repository.SalleRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    private final SalleRepository salleRepository;

    public SalleController(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    // 👥 Accessible par ADMIN et CLIENT
    @GetMapping("allSalles")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    // 👥 Accessible par ADMIN uniquement
    @PostMapping("createSalle")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Salle createSalle(@RequestBody Salle salle) {
        return salleRepository.save(salle);
    }

    // 👥 Accessible par ADMIN uniquement
    @PutMapping("updateSalle/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Salle updateSalle(@RequestBody Salle updatedSalle, @PathVariable long id) {
        return salleRepository.findById(id)
                .map(salle -> {
                    salle.setNom(updatedSalle.getNom());
                    salle.setCapacite(updatedSalle.getCapacite());
                    salle.setEmplacement(updatedSalle.getEmplacement());
                    return salleRepository.save(salle);
                })
                .orElseThrow(() -> new RuntimeException("Salle non trouvée avec l'id : " + id));
    }

    // 👥 Accessible par ADMIN uniquement
    @DeleteMapping("deleteSalle/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSalle(@PathVariable Long id) {
        if (!salleRepository.existsById(id)) {
            throw new RuntimeException("Salle non trouvée avec l'id : " + id);
        }
        salleRepository.deleteById(id);
    }

    // 👥 Accessible par ADMIN et CLIENT
    @GetMapping("/available")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public List<Salle> getAvailableSalles(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime end) {
        return salleRepository.findAvailableSalles(date, start, end);
    }
}

