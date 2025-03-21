
INSERT INTO organizer (name, email, phone_number) VALUES
('Organizacija A', 'orgA@email.com', '0651234567'),
('Organizacija B', 'orgB@email.com', '0632345678'),
('Organizacija C', 'orgC@email.com', '0623456789'),
('Organizacija D', 'orgD@email.com', '0619876543');

INSERT INTO activity (title, description, date, location, organizer_id) VALUES
('Aktivnost 1', 'Opis aktivnosti 1', '2025-04-15', 'Lokacija 1', 1),
('Aktivnost 2', 'Opis aktivnosti 2', '2025-04-20', 'Lokacija 2', 2),
('Aktivnost 3', 'Opis aktivnosti 3', '2025-05-10', 'Lokacija 3', 1),
('Aktivnost 4', 'Opis aktivnosti 4', '2025-06-01', 'Lokacija 4', 3);

INSERT INTO volunteer (name, email, phone_number) VALUES
('Marko Marković', 'marko@email.com', '0612345678'),
('Ana Anić', 'ana@email.com', '0623456789'),
('John Doe', 'john.doe@example.com', '1234567890'),
('Jane Smith', 'jane.smith@example.com', '0987654321');

INSERT INTO engagement_statistics (volunteer_id, total_activities, messages_sent, forum_posts_made, notifications_received) VALUES
(1, 5, 10, 3, 4),
(2, 3, 7, 2, 5),
(3, 8, 15, 6, 7),
(4, 4, 12, 5, 6);


INSERT INTO message (sender_id, receiver_id, organizer_id, content, timestamp) VALUES
(1, 2, 1, 'Pozdrav, Ana! Kako si?', '2025-03-20 10:30:00'),
(2, 1, 2, 'Zdravo, Marko! Dobro sam, hvala.', '2025-03-20 11:00:00'),
(3, 4, 1, 'Ćao, Jane! Kako ide?', '2025-03-21 09:00:00'),
(4, 3, 2, 'Zdravo, John! Imaš li novih informacija?', '2025-03-21 10:00:00');

INSERT INTO forum_post (author_id, activity_id, organizer_id, content, timestamp) VALUES
(1, 1, 1, 'Ovo je moj post na forumu o aktivnostima!', '2025-03-20 12:00:00'),
(2, 2, 2, 'Čekam sve učesnike na ovoj aktivnosti!', '2025-03-20 12:30:00'),
(3, 1, 1, 'Pitanje za sve: Koja je najbolja opcija za ovu aktivnost?', '2025-03-22 15:00:00'),
(4, 2, 2, 'Svi ste dobrodošli na aktivnost, prijavite se!', '2025-03-22 16:00:00');

INSERT INTO notification (volunteer_id, activity_id, organizer_id, message, type, timestamp, is_read) VALUES
(1, 1, 1, 'Nova aktivnost je postavljena!', 'INFO', '2025-03-20 13:00:00', false),
(2, 2, 2, 'Imate novu poruku!', 'ALERT', '2025-03-20 13:30:00', true),
(3, 1, 1, 'Poslana vam je nova poruka!', 'ALERT', '2025-03-21 09:30:00', false),
(4, 2, 2, 'Nova aktivnost je postavljena!', 'INFO', '2025-03-21 10:30:00', true);
