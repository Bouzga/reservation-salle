package com.reservation.reservationsalle.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "\"user\"") // ⚠️ "user" est un mot réservé en PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String role; // "ADMIN" or "CLIENT"

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
}
