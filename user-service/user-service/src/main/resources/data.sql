-- Unos u tabelu role
INSERT INTO role (role_name)
VALUES
('Admin'),
('Volunteer'),
('Organizer');

-- Unos u tabelu permission
INSERT INTO permission (permission_name)
VALUES
('CREATE_ACTIVITY'),
('EDIT_ACTIVITY'),
('DELETE_ACTIVITY'),
('VIEW_ACTIVITY');

-- Unos u tabelu users
INSERT INTO users (first_name, last_name, email, password_hash, profile_picture, role_id, created_at, updated_at)
VALUES
('Marko', 'Marković', 'marko@example.com', 'hashedpassword123', 'profile_pic1.jpg', 1, '2025-03-20 10:00:00', '2025-03-20 10:00:00'),
('Jelena', 'Jovanović', 'jelena@example.com', 'hashedpassword456', 'profile_pic2.jpg', 2, '2025-03-20 10:30:00', '2025-03-20 10:30:00');

-- Unos u tabelu activity
INSERT INTO activity (activity_name, activity_date, description, organizer_id, created_at, updated_at)
VALUES
('Volontiranje u parku', '2025-03-22 09:00:00', 'Čišćenje parka u centru grada', 1, '2025-03-20 10:00:00', '2025-03-20 10:00:00'),
('Prikupljanje donacija', '2025-03-25 14:00:00', 'Donacije za lokalnu školu', 2, '2025-03-20 10:30:00', '2025-03-20 10:30:00');

-- Unos u tabelu social_share
INSERT INTO social_share (user_id, activity_id, platform, shared_at)
VALUES
(1, 1, 'Facebook', '2025-03-20 12:00:00'),
(2, 2, 'Twitter', '2025-03-20 12:30:00');

-- Unos u tabelu user_permission
INSERT INTO user_permission (user_id, permission_id)
VALUES
(1, 1),  -- Korisnik 1 dobija permisiju 1 (npr. CREATE_ACTIVITY)
(1, 2),  -- Korisnik 1 dobija permisiju 2 (npr. EDIT_ACTIVITY)
(2, 3),  -- Korisnik 2 dobija permisiju 3 (npr. DELETE_ACTIVITY)
(2, 4);  -- Korisnik 2 dobija permisiju 4 (npr. VIEW_ACTIVITY)

-- Unos u tabelu volunteer_certificate
INSERT INTO volunteer_certificate (user_id, activity_id, certificate_date, certificate_pdf_link, issued_at)
VALUES
(1, 1, '2025-03-22', 'http://example.com/certificate1.pdf', '2025-03-20 12:00:00'),
(2, 2, '2025-03-25', 'http://example.com/certificate2.pdf', '2025-03-20 12:30:00');
