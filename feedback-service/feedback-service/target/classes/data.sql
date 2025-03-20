-- Ubacivanje volontera
INSERT INTO volunteer (volunteer_id, name, contact_info) VALUES
(1, 'Marko Petrović', 'marko.petro@example.com | 061-123-456'),
(2, 'Jasmina Hasić', 'jasmina.hasic@example.com | 062-654-321');

-- Ubacivanje aktivnosti
INSERT INTO activity (activity_id, description, date, location, volunteers_needed) VALUES
(1, 'Čišćenje parka', '2025-04-10', 'Centar grada', 10),
(2, 'Sadnja drveća', '2025-05-15', 'Gradski park', 5);

-- Ubacivanje povratnih informacija
INSERT INTO feedback (feedback_id, volunteer_id, activity_id, rating, comment, timestamp) VALUES
(1, 1, 1, 5, 'Odlična organizacija!', '2025-04-11 10:30:00'),
(2, 2, 1, 4, 'Lijepo iskustvo, ali nedostajalo alata.', '2025-04-11 12:00:00'),
(3, 1, 2, 5, 'Volio bih opet učestvovati!', '2025-05-16 14:00:00');

-- Ubacivanje statistike aktivnosti
INSERT INTO activity_statistics (id, activity_id, average_rating, total_ratings, total_comments) VALUES
(1, 1, 4.5, 2, 2),
(2, 2, 5.0, 1, 1);