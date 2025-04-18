package com.reservation.reservationsalle.controller;

import com.reservation.reservationsalle.model.Salle;
import com.reservation.reservationsalle.repository.SalleRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {

    private SalleRepository salleRepository;

    public SalleController(SalleRepository salleRepository) {
            this.salleRepository = salleRepository;
    }

    @GetMapping("allSalles")
    public List<Salle> getAllSalles(){
        return salleRepository.findAll();
    }

    @PostMapping("createSalle")
    Salle createSalle(@RequestBody Salle salle){
        return salleRepository.save(salle);
    }

    @PutMapping("updateSalle/{id}")
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

    @DeleteMapping("deleteSalle/{id}")
    public void deleteSalle(@PathVariable Long id) {
        if (!salleRepository.existsById(id)) {
            throw new RuntimeException("Salle non trouvée avec l'id : " + id);
        }
        salleRepository.deleteById(id);
    }

    @GetMapping("/available")
    public List<Salle> getAvailableSalles(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime end) {

        return salleRepository.findAvailableSalles(date, start, end);
    }

}
