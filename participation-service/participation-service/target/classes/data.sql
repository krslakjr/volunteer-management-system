-- Ubacivanje podataka u tabelu 'activity'
INSERT INTO activity (activity_id, description, date, location, volunteers_needed) VALUES
(1, 'Clean the park', '2025-04-10', 'City Park', 10),
(2, 'Help at the shelter', '2025-04-15', 'Animal Shelter', 5);

-- Ubacivanje podataka u tabelu 'volunteer'
INSERT INTO volunteer (volunteer_id, name, contact_info) VALUES
(1, 'John Doe', 'john.doe@email.com'),
(2, 'Jane Smith', 'jane.smith@email.com');

-- Ubacivanje podataka u tabelu 'participation'
INSERT INTO participation (participation_id, volunteer_id, activity_id, registration_date, attendance_status) VALUES
(1, 1, 1, '2025-04-01', 'PRESENT'),
(2, 2, 2, '2025-04-02', 'PRESENT');

-- Ubacivanje podataka u tabelu 'recommendation'
INSERT INTO recommendation (recommendation_id, volunteer_id, recommendation_activity_id, date_generated) VALUES
(1, 1, 2, '2025-04-05'),
(2, 2, 1, '2025-04-06');

-- Ubacivanje podataka u tabelu 'certificate'
INSERT INTO certificate (certificate_id, volunteer_id, activity_id, issue_date, certificate_status) VALUES
(1, 1, 1, '2025-04-11', 'APPROVED'),
(2, 2, 2, '2025-04-16', 'PENDING');
