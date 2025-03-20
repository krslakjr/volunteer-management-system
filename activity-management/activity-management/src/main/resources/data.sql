-- Dodavanje timova
INSERT INTO team (team_name) VALUES 
('Tim A'),
('Tim B'),
('Tim C');

-- Dodavanje aktivnosti
INSERT INTO activity (description, date, location, volunteers_needed) VALUES 
('Čišćenje parka', '2025-04-10', 'Centar grada', 10),
('Pomoć u azilu za pse', '2025-04-15', 'Azil za pse Sarajevo', 5),
('Sakupljanje hrane za socijalno ugrožene', '2025-04-20', 'Crveni križ', 8);

-- Dodavanje volontera
INSERT INTO volunteer (name, contact_info) VALUES 
('Marko Petrović', 'marko.petro@example.com | 061-123-456'),
('Jasmina Hasić', 'jasmina.hasic@example.com | 062-654-321'),
('Dino Kovač', 'dino.kovac@example.com | 063-987-654');


-- Dodavanje volontera u aktivnosti
INSERT INTO activity_volunteer (activity_id, volunteer_id) VALUES 
(1, 1), -- Marko učestvuje u čišćenju parka
(1, 2), -- Jasmina učestvuje u čišćenju parka
(2, 3), -- Dino pomaže u azilu za pse
(3, 1), -- Marko učestvuje u sakupljanju hrane
(3, 2); -- Jasmina učestvuje u sakupljanju hrane


-- Dodavanje aktivnosti za timove
INSERT INTO team_activity (team_id, activity_id) VALUES 
(1, 1),  -- Tim A učestvuje u čišćenju parka
(1, 2),  -- Tim A učestvuje u pomoći u azilu
(2, 2),  -- Tim B učestvuje u pomoći u azilu
(2, 3),  -- Tim B učestvuje u sakupljanju hrane
(3, 1),  -- Tim C učestvuje u čišćenju parka
(3, 3);  -- Tim C učestvuje u sakupljanju hrane
