-- Users
INSERT INTO "user" (id, name, email, password, role) VALUES (1, 'Admin User', 'admin@example.com', 'admin123', 'ADMIN');
INSERT INTO "user" (id, name, email, password, role) VALUES (2, 'Youssef', 'youssef@example.com', 'pass123', 'CLIENT');
INSERT INTO "user" (id, name, email, password, role) VALUES (3, 'Amine', 'amine@example.com', 'pass456', 'CLIENT');

-- Salles
INSERT INTO salle (nom, capacite, emplacement) VALUES ('Salle Alpha', 30, 'Étage 1');
INSERT INTO salle (nom, capacite, emplacement) VALUES ('Salle Bêta', 50, 'Étage 2');
INSERT INTO salle (nom, capacite, emplacement) VALUES ('Salle Conférence', 100, 'Rez-de-chaussée');

-- Réservations
INSERT INTO reservation (nom_personne, date_reservation, heure_debut, heure_fin, salle_id, user_id, statut)
VALUES ('Youssef', '2025-04-15', '09:00:00', '11:00:00', 1, 2, 'EN_ATTENTE');

INSERT INTO reservation (nom_personne, date_reservation, heure_debut, heure_fin, salle_id, user_id, statut)
VALUES ('Amine', '2025-04-15', '14:00:00', '16:00:00', 2, 3, 'VALIDEE');
