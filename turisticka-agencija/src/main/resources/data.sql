INSERT INTO destination (id, name, country, description)
VALUES
    (1, 'Krf', 'Grcka', 'Krf je jedno od najlepših grčkih ostrva, poznato po plažama, zelenilu i starom gradu pod zaštitom UNESCO-a.'),
    (2, 'Rim', 'Italija', 'Rim je istorijski grad sa bogatom kulturom, antičkim spomenicima, trgovima i autentičnom italijanskom atmosferom.'),
    (3, 'Kopaonik', 'Srbija', 'Kopaonik je najpoznatiji ski centar u Srbiji, pogodan za zimovanje, odmor u prirodi i aktivan turizam.'),
    (4, 'Hurgada', 'Egipat', 'Hurgada je popularno letovalište na Crvenom moru, poznato po all inclusive hotelima, ronjenju i toploj klimi.'),
    (5, 'Pariz', 'Francuska', 'Pariz je grad umetnosti, mode, romantike i znamenitosti kao što su Ajfelov toranj, Luvr i Jelisejska polja.');

INSERT INTO accommodation (id, name, category)
VALUES
    (1, 'Hotel Ionian View', 'HOTEL_4'),
    (2, 'Hotel Roma Centro', 'HOTEL_3'),
    (3, 'Grand Hotel Kopaonik', 'HOTEL_5'),
    (4, 'Hurgada Resort', 'HOTEL_5'),
    (5, 'Paris Apartment', 'APARTMENT');

INSERT INTO transport (id, type, company)
VALUES
    (1, 'BUS', 'LuxTravel Bus'),
    (2, 'PLANE', 'Air Serbia'),
    (3, 'OWN_TRANSPORT', 'Bez prevoza');

INSERT INTO additional_service (id, name)
VALUES
    (1, 'Doručak'),
    (2, 'All inclusive'),
    (3, 'Vodič'),
    (4, 'Spa');

INSERT INTO arrangement
(id, name, description, base_price, image_url, number_of_nights, accommodation_id, transport_id, destination_id)
VALUES
    (1, 'Letovanje Krf', 'Letovanje na Krfu sa autobuskim prevozom i smeštajem blizu plaže.', 450, '/arrangements/krf1.jpg', 10, 1, 1, 1),
    (2, 'Rim city break', 'Putovanje u Rim, obilazak znamenitosti i slobodno vreme za kupovinu.', 380, '/arrangements/rimjpg.jpg', 4, 2, 2, 2),
    (3, 'Zimovanje Kopaonik', 'Zimski aranžman na Kopaoniku sa hotelskim smeštajem.', 300, '/arrangements/zimovanje.jpg', 7, 3, 3, 3),
    (4, 'Letovanje Hurgada', 'All inclusive letovanje u Hurgadi sa avionskim prevozom.', 700, '/arrangements/hurgada1.jpg', 10, 4, 2, 4),
    (5, 'Pariz romantično putovanje', 'Putovanje u Pariz sa obilaskom najpoznatijih znamenitosti.', 620, '/arrangements/pariz.jpg', 7, 5, 2, 5);

INSERT INTO term (id, start_date, end_date)
VALUES
    (1, '2026-07-10', '2026-07-20'),
    (2, '2026-08-01', '2026-08-10'),
    (3, '2026-09-05', '2026-09-09'),
    (4, '2026-12-20', '2026-12-27'),
    (5, '2026-06-15', '2026-06-22'),
    (6, '2025-07-10', '2025-07-20'),
    (7, '2025-08-01', '2025-08-10'),
    (8, '2025-12-20', '2025-12-27'),

    (9, '2024-07-15', '2024-07-25'),
    (10, '2024-12-18', '2024-12-26');

INSERT INTO arrangement_term
(id, arrangement_id, term_id, capacity, reserved_spots)
VALUES
    (1, 1, 1, 40, 0),
    (2, 1, 2, 35, 0),
    (3, 2, 3, 25, 0),
    (4, 3, 4, 30, 0),
    (5, 4, 2, 20, 0),
    (6, 5, 5, 15, 0),
    (7, 1, 6, 40, 2),
    (8, 1, 7, 35, 0),
    (9, 3, 8, 30, 1),

    (10, 4, 9, 20, 3),
    (11, 3, 10, 30, 1);

INSERT INTO arrangement_additional_services (arrangement_id, additional_service_id)
VALUES
    (1, 1),
    (1, 3),
    (2, 1),
    (2, 3),
    (3, 1),
    (3, 4),
    (4, 2),
    (5, 1),
    (5, 3);

INSERT INTO users
(id, first_name, last_name, username, email, password, contact, role)
VALUES
    (
        1,
        'Ivana',
        'Torovic',
        'ivana',
        'ivana@gmail.com',
        '$2a$12$vwKOu6LpRjAsYDDUCi2b/eeuZfn6Vdok4UY8aP3Xk.uHDpUedpdiG',
        '0641111111',
        'CUSTOMER'
    ),(
    2,
    'Tijana',
    'Barudzija',
    'tijana',
    'tijana@gmail.com',
    '$2a$12$8a.6wRX01hEc4/cy.KvDAO.jG6n6hhVbv9uh65bmVDb6Yl/vSh4.i',
    '0641111111',
    'SALES_AGENT'
),(
    3,
    'Srdjan',
    'Jovic',
    'srdjan',
    'srdjan@gmail.com',
    '$2a$12$8t9.26FFCQvLb2M2ZIuE3Ovfjary6v//q2MkeNQP1/bYmldLd64ym',
    '0641111111',
    'MANAGER'
);

INSERT INTO reservation
(id, user_id, arrangement_id, arrangement_term_id, number_of_passengers, total_price, reservation_date, status, payment_type, number_of_installments, installment_amount)
VALUES
    (1, 1, 1, 1, 2, 900, '2026-02-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 900),
    (2, 1, 1, 2, 3, 1350, '2026-03-15 11:30:00', 'CONFIRMED', 'INSTALLMENTS', 3, 450),
    (3, 1, 4, 5, 2, 1400, '2026-04-01 09:15:00', 'CONFIRMED', 'INSTALLMENTS', 4, 350),
    (4, 1, 2, 3, 1, 380, '2026-05-12 14:20:00', 'CANCELLED', 'ONE_TIME', 1, 380),
    (5, 1, 3, 4, 2, 600, '2026-10-05 16:45:00', 'CONFIRMED', 'ONE_TIME', 1, 600),

-- 2025
    (6, 1, 1, 7, 2, 900, '2025-02-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 900),
    (7, 1, 1, 8, 3, 1350, '2025-03-10 10:00:00', 'CONFIRMED', 'INSTALLMENTS', 3, 450),
    (8, 1, 3, 9, 1, 300, '2025-10-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 300),

-- 2024
    (9, 1, 4, 10, 3, 2100, '2024-04-10 10:00:00', 'CONFIRMED', 'INSTALLMENTS', 6, 350),
    (10, 1, 3, 11, 1, 300, '2024-11-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 300);
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('destination_id_seq', (SELECT MAX(id) FROM destination));
SELECT setval('accommodation_id_seq', (SELECT MAX(id) FROM accommodation));
SELECT setval('transport_id_seq', (SELECT MAX(id) FROM transport));
SELECT setval('additional_service_id_seq', (SELECT MAX(id) FROM additional_service));
SELECT setval('arrangement_id_seq', (SELECT MAX(id) FROM arrangement));
SELECT setval('term_id_seq', (SELECT MAX(id) FROM term));
SELECT setval('arrangement_term_id_seq', (SELECT MAX(id) FROM arrangement_term));
SELECT setval('reservation_id_seq', (SELECT MAX(id) FROM reservation));

UPDATE arrangement_term SET reserved_spots = 4 WHERE id = 1;
UPDATE arrangement_term SET reserved_spots = 3 WHERE id = 2;
UPDATE arrangement_term SET reserved_spots = 3 WHERE id = 3;
UPDATE arrangement_term SET reserved_spots = 3 WHERE id = 4;
UPDATE arrangement_term SET reserved_spots = 5 WHERE id = 5;
UPDATE arrangement_term SET reserved_spots = 2 WHERE id = 6;