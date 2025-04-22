package com.reservation.reservationsalle.controller;

import com.reservation.reservationsalle.model.User;
import com.reservation.reservationsalle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Get All Users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //  Get User by ID
    @GetMapping("getUserById/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID : " + id));
    }

    //  Create User
    @PostMapping("createUser")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Vérification de l'unicité de l'email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(Map.of("error", "Email déjà utilisé"));
        }

        // Hash du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Sauvegarde de l'utilisateur
        User savedUser = userRepository.save(user);

        // Réponse avec données utiles
        return ResponseEntity
                .status(HttpStatus.CREATED) //
                .body(Map.of(
                        "id", savedUser.getId(),
                        "email", savedUser.getEmail(),
                        "role", savedUser.getRole(),
                        "message", "Utilisateur créé avec succès"
                ));
    }



    // Update User
    @PutMapping("updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setRole(newUser.getRole());

                    // Ne pas oublier de re-hasher le mot de passe s'il est modifié
                    user.setPassword(passwordEncoder.encode(newUser.getPassword()));

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ID : " + id));
    }

    // Delete User
    @DeleteMapping("deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé avec ID : " + id);
        }
        userRepository.deleteById(id);
    }
}
