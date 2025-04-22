-- Users
INSERT INTO "user" (name, email, password, role)
VALUES ('Amine', 'amine@example.com', '$2a$10$7SnzNivZxKtBq1qUpebgOOG8UoHxaBkBXLu6k2B3FdggNBbgYgEZC', 'CLIENT');

-- Salles
INSERT INTO salle (nom, capacite, emplacement) VALUES ('Salle Alpha', 30, 'Étage 1');
INSERT INTO salle (nom, capacite, emplacement) VALUES ('Salle Bêta', 50, 'Étage 2');
INSERT INTO salle (nom, capacite, emplacement) VALUES ('Salle Conférence', 100, 'Rez-de-chaussée');

-- Réservations
INSERT INTO reservation (nom_personne, date_reservation, heure_debut, heure_fin, salle_id, user_id, statut)
VALUES ('Youssef', '2025-04-15', '09:00:00', '11:00:00', 1, 1, 'EN_ATTENTE');

INSERT INTO reservation (nom_personne, date_reservation, heure_debut, heure_fin, salle_id, user_id, statut)
VALUES ('Amine', '2025-04-15', '14:00:00', '16:00:00', 2, 1, 'VALIDEE');
