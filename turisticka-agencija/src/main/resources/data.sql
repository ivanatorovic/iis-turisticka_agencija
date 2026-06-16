INSERT INTO destination (id, category, name, country, description)
VALUES
    (1, 'LETOVANJE', 'Krf', 'Grcka', 'Krf je jedno od najlepših grčkih ostrva, poznato po plažama, zelenilu i starom gradu pod zaštitom UNESCO-a.'),
    (2, 'IZLET', 'Rim', 'Italija', 'Rim je istorijski grad sa bogatom kulturom, antičkim spomenicima, trgovima i autentičnom italijanskom atmosferom.'),
    (3, 'ZIMOVANJE', 'Kopaonik', 'Srbija', 'Kopaonik je najpoznatiji ski centar u Srbiji, pogodan za zimovanje, odmor u prirodi i aktivan turizam.'),
    (4, 'EGZOTIKA', 'Hurgada', 'Egipat', 'Hurgada je popularno letovalište na Crvenom moru, poznato po all inclusive hotelima, ronjenju i toploj klimi.'),
    (5, 'IZLET', 'Pariz', 'Francuska', 'Pariz je grad umetnosti, mode, romantike i znamenitosti kao što su Ajfelov toranj, Luvr i Jelisejska polja.');

INSERT INTO destination_calendar (id, name, start_date, end_date, destination_id)
VALUES
    (1, 'Krf dostupni termini 2026', '2026-05-01', '2026-09-01', 1),
    (2, 'Rim dostupni termini 2026', '2026-09-01', '2026-11-30', 2),
    (3, 'Kopaonik dostupni termini 2026', '2026-12-01', '2027-03-15', 3),
    (4, 'Hurgada dostupni termini 2026', '2026-05-01', '2026-10-31', 4),
    (5, 'Pariz dostupni termini 2026', '2026-03-01', '2026-06-30', 5);

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
(id, name, description, base_price, image_url, number_of_nights,
 accommodation_id, transport_id, destination_id)
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
    (1, 1, 1, 40, 10),
    (2, 1, 2, 35, 7),
    (3, 2, 3, 25, 3),
    (4, 3, 4, 30, 4),
    (5, 4, 2, 20, 8),
    (6, 5, 5, 15, 5),
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
    (1, 'Ivana', 'Torovic', 'ivana', 'ivana@gmail.com', '$2a$12$vwKOu6LpRjAsYDDUCi2b/eeuZfn6Vdok4UY8aP3Xk.uHDpUedpdiG', '0641111111', 'CUSTOMER'),
    (2, 'Tijana', 'Barudzija', 'tijana', 'tijana@gmail.com', '$2a$12$8a.6wRX01hEc4/cy.KvDAO.jG6n6hhVbv9uh65bmVDb6Yl/vSh4.i', '0641111111', 'SALES_AGENT'),
    (3, 'Srdjan', 'Jovic', 'srdjan', 'srdjan@gmail.com', '$2a$12$8t9.26FFCQvLb2M2ZIuE3Ovfjary6v//q2MkeNQP1/bYmldLd64ym', '0641111111', 'MANAGER'),
    (4, 'Pera', 'Peric', 'pera', 'pera@gmail.com', '$2a$12$i.mgoLN5DPZoA4uyN3GRDe9EE6BF9MUEkVEhMZboFEd0hYQ8vGBNW', '0641111111', 'ADMIN'),
    (5, 'Zika', 'Zikic', 'zika', 'zika@gmail.com', '$2a$12$3sz5kLVM6c6C89MLHFKwVeCDh7IKUkZxsGaqV19JAtih4/VX0/oGW', '0641111111', 'DIRECTOR'),
    (6, 'Mika', 'Mikic', 'mika', 'mika@gmail.com', '$2a$12$I2G9oURzwaIngC4AW226CetjqAzs3dOrZk4/0i5psVCI3KGOAyLHq', '0641111111', 'MANAGER'),
    (7, 'Nikola', 'Markovic', 'nikola', 'nikola.guide@gmail.com', '$2a$12$1X8PJslZsVrKs.o7hEK.7uQimcV6/mz.t0tnRjhalgEWYWxbqvQWe', '0642222222', 'GUIDE'),
    (8, 'Marija', 'Jovanovic', 'marija', 'marija.guide@gmail.com', '$2a$12$TaxPHIG4YEuzC8WBuGWcMuEEwCYxFf2G7f593oCuXVNQwrUYfEZyy', '0643333333', 'GUIDE'),
    (9, 'Stefan', 'Petrovic', 'stefan', 'stefan.guide@gmail.com', '$2a$12$c3zZDfkrfYfCADqzt8rZseyCExHqdqOw9JfDUovNe7d3NMlZIQxFm', '0644444444', 'GUIDE'),
    (10, 'Marko', 'Nikolic', 'marko', 'marko@gmail.com', '$2a$12$z3yf/VvoZkdKS2q5LZ9LK.P9zHbSMTaFMcQ70ds8bJHaSPuCxlQgu', '0645555555', 'CUSTOMER'),
    (11, 'Ana', 'Stankovic', 'ana', 'ana@gmail.com', '$2a$12$YdtjVF5HLxZMppg3agP2e.PgMKIQ2ZNFSTQk6K75QyifgQXYn6zqK', '0646666666', 'CUSTOMER'),
    (12, 'Luka', 'Pavlovic', 'luka', 'luka@gmail.com', '$2a$12$raKxWepdCv2r/C/48Yp9d.X7ccyX96IdOEjdKmQPSD3bj66diCV7u', '0647777777', 'CUSTOMER'),
    (13, 'Milica', 'Ilic', 'milica', 'milica@gmail.com', '$2a$12$c9e0LO16Tk94VRQ23Y4RsuAHPCcuhXSbZsIcYZkdKRyGRey5edpFu', '0648888888', 'CUSTOMER');

INSERT INTO reservation (
    id,
    user_id,
    arrangement_id,
    arrangement_term_id,
    number_of_passengers,
    total_price,
    reservation_date,
    status,
    payment_type,
    number_of_installments,
    installment_amount,
    passenger_first_name,
    passenger_last_name,
    passenger_email,
    base_price_per_person,
    dynamic_price_per_person,
    insurance_selected,
    insurance_price,
    arrangement_total_price
)
VALUES
    (1, 1, 1, 1, 2, 900, '2026-02-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 900, 'Petar', 'Petrović', 'petar@gmail.com', 450, 450, false, 0, 900),
    (2, 1, 1, 2, 3, 1350, '2026-03-15 11:30:00', 'CONFIRMED', 'INSTALLMENTS', 3, 450, 'Ana', 'Anić', 'ana@gmail.com', 450, 450, false, 0, 1350),
    (3, 1, 4, 5, 2, 1400, '2026-04-01 09:15:00', 'CONFIRMED', 'INSTALLMENTS', 4, 350, 'Marko', 'Marković', 'marko@gmail.com', 700, 700, false, 0, 1400),
    (4, 1, 2, 3, 1, 380, '2026-05-12 14:20:00', 'CANCELLED', 'ONE_TIME', 1, 380, 'Jovana', 'Jovanović', 'jovana@gmail.com', 380, 380, false, 0, 380),
    (5, 1, 3, 4, 2, 600, '2026-10-05 16:45:00', 'CONFIRMED', 'ONE_TIME', 1, 600, 'Nikola', 'Nikolić', 'nikola@gmail.com', 300, 300, false, 0, 600),
    (6, 1, 1, 7, 2, 900, '2025-02-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 900, 'Milica', 'Milić', 'milica@gmail.com', 450, 450, false, 0, 900),
    (7, 1, 1, 8, 3, 1350, '2025-03-10 10:00:00', 'CONFIRMED', 'INSTALLMENTS', 3, 450, 'Stefan', 'Stefanović', 'stefan@gmail.com', 450, 450, false, 0, 1350),
    (8, 1, 3, 9, 1, 300, '2025-10-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 300, 'Mina', 'Mitić', 'mina@gmail.com', 300, 300, false, 0, 300),
    (9, 1, 4, 10, 3, 2100, '2024-04-10 10:00:00', 'CONFIRMED', 'INSTALLMENTS', 6, 350, 'Luka', 'Lukić', 'luka@gmail.com', 700, 700, false, 0, 2100),
    (10, 1, 3, 11, 1, 300, '2024-11-10 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 300, 'Sara', 'Sarić', 'sara@gmail.com', 300, 300, false, 0, 300),
    (11, 10, 1, 1, 2, 900, '2026-03-01 12:15:00', 'CONFIRMED', 'ONE_TIME', 1, 900, 'Milan', 'Milanović', 'milan@gmail.com', 450, 450, false, 0, 900),
    (12, 11, 1, 1, 3, 1350, '2026-03-03 09:40:00', 'CONFIRMED', 'INSTALLMENTS', 3, 450, 'Ivana', 'Ivić', 'ivana@gmail.com', 450, 450, false, 0, 1350),
    (13, 12, 1, 2, 2, 900, '2026-03-06 15:25:00', 'CONFIRMED', 'ONE_TIME', 1, 900, 'Tamara', 'Tomić', 'tamara@gmail.com', 450, 450, false, 0, 900),
    (14, 13, 4, 5, 2, 1400, '2026-04-02 10:30:00', 'CONFIRMED', 'INSTALLMENTS', 4, 350, 'Bojan', 'Bojić', 'bojan@gmail.com', 700, 700, false, 0, 1400),
    (15, 12, 1, 1, 2, 900, '2026-03-08 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 900, 'Nina', 'Ninić', 'nina@gmail.com', 450, 450, false, 0, 900),
    (16, 13, 1, 1, 2, 900, '2026-03-09 10:00:00', 'CONFIRMED', 'ONE_TIME', 1, 900, 'Vuk', 'Vukić', 'vuk@gmail.com', 450, 450, false, 0, 900);

INSERT INTO workflow (id, name, created_at, admin_id)
VALUES
    (1, 'Letovanje Evropa', NOW(), 4);

INSERT INTO workflow_phase (id, name, type, workflow_id)
VALUES
    (1, 'DESTINATION', 'RECOMMENDED', 1),
    (2, 'ACCOMMODATION', 'RECOMMENDED', 1),
    (3, 'TRANSPORT', 'RECOMMENDED', 1);

INSERT INTO additional_activity
(id, name, description, location, image_url, created_by_id)
VALUES
    (1, 'Izlet do ostrva Vido', 'Organizovan izlet brodom do ostrva Vido tokom letovanja na Krfu.', 'Krf, Grcka', '/uploads/additional-activities/vido.jpg', 3),
    (2, 'Obilazak Koloseuma', 'Obilazak Koloseuma i Rimskog foruma uz lokalnog turističkog vodiča.', 'Rim, Italija', '/uploads/additional-activities/koloseum.jpg', 3),
    (3, 'Ski škola za početnike', 'Osnovna ski obuka za početnike tokom zimovanja na Kopaoniku.', 'Kopaonik, Srbija', '/uploads/additional-activities/ski-skola.jpg', 6),
    (4, 'Ronjenje u Crvenom moru', 'Ronjenje sa instruktorom i obilazak koralnih grebena tokom boravka u Hurgadi.', 'Hurgada, Egipat', '/uploads/additional-activities/ronjenje.jpg', 6),
    (5, 'Krstarenje Senom', 'Večernje panoramsko krstarenje Senom tokom putovanja u Pariz.', 'Pariz, Francuska', '/uploads/additional-activities/sena.jpg', 3),
    (6, 'Grčko veče na Krfu', 'Večera uz tradicionalnu grčku muziku, ples i lokalne specijalitete.', 'Krf, Grcka', '/uploads/additional-activities/grcko-vece.jpg', 3),
    (7, 'Obilazak Vatikana', 'Organizovan obilazak Vatikanskih muzeja i Trga Svetog Petra.', 'Rim, Italija', '/uploads/additional-activities/vatikan.jpg', 3),
    (8, 'Motorne sanke', 'Vožnja motornim sankama uz pratnju instruktora na Kopaoniku.', 'Kopaonik, Srbija', '/uploads/additional-activities/motorne-sanke.jpg', 6),
    (9, 'Safari pustinjom', 'Vožnja džipovima kroz pustinju i večera u beduinskom kampu.', 'Hurgada, Egipat', '/uploads/additional-activities/safari.jpg', 6),
    (10, 'Obilazak Luvra', 'Vođeni obilazak najpoznatijih dela u muzeju Luvr.', 'Pariz, Francuska', '/uploads/additional-activities/luvr.jpg', 3),
    (13, 'Izlet brodom', 'Poseban izlet brodom oko Krfa.', 'Krf, Grcka', '/uploads/additional-activities/brod.jpg', 3),
    (14, 'Kajak avantura', 'Vožnja kajakom uz obalu Krfa.', 'Krf, Grcka', '/uploads/additional-activities/kajak.jpg', 3),
    (15, 'Planinarenje do vidikovca', 'Pešačka tura do vidikovca.', 'Krf, Grcka', '/uploads/additional-activities/planinarenje.jpg', 3),
    (16, 'Plažni odbojkaški turnir', 'Sportsko takmičenje na plaži.', 'Krf, Grcka', '/uploads/additional-activities/odbojka.jpg', 3),
    (17, 'Degustacija grčke hrane', 'Degustacija lokalnih specijaliteta.', 'Krf, Grcka', '/uploads/additional-activities/hrana.jpg', 3),
    (18, 'Foto tura starim gradom', 'Obilazak i fotografisanje starog grada.', 'Krf, Grcka', '/uploads/additional-activities/foto.jpg', 3),
    (19, 'Porodični kviz', 'Zabavna porodična aktivnost.', 'Krf, Grcka', '/uploads/additional-activities/kviz.jpg', 3),
    (20, 'Relax masaža', 'Kratka relaks masaža u hotelu.', 'Krf, Grcka', '/uploads/additional-activities/masaza.jpg', 3);

INSERT INTO activity_term (id, date, start_time)
VALUES
    (1, '2026-07-12', '10:00'),
    (2, '2026-07-15', '20:00'),
    (3, '2026-08-03', '09:30'),
    (4, '2026-08-06', '20:30'),
    (5, '2026-09-06', '11:00'),
    (6, '2026-09-07', '10:00'),
    (7, '2026-12-21', '12:00'),
    (8, '2026-12-23', '14:00'),
    (9, '2026-08-03', '09:00'),
    (10, '2026-08-05', '15:00'),
    (11, '2026-06-17', '19:30'),
    (12, '2026-06-18', '10:00'),
    (13, '2026-07-13', '09:00'),
    (14, '2026-07-14', '11:00'),
    (15, '2026-07-15', '08:30'),
    (16, '2026-07-16', '17:00'),
    (17, '2026-07-17', '19:00'),
    (18, '2026-07-18', '10:00'),
    (19, '2026-07-18', '16:00'),
    (20, '2026-07-19', '12:00');

INSERT INTO additional_activity_execution
(id, additional_activity_id, arrangement_term_id, activity_term_id,
 guide_id, duration_minutes, capacity, reserved_spots, status, prior)
VALUES
    (1, 1, 1, 1, 7, 180, 30, 10, 'UPCOMING', TRUE),
    (2, 6, 1, 2, 8, 150, 35, 9, 'UPCOMING', FALSE),
    (3, 1, 2, 3, 9, 180, 28, 5, 'UPCOMING', FALSE),
    (4, 6, 2, 4, 7, 150, 30, 7, 'UPCOMING', FALSE),
    (5, 2, 3, 5, 8, 120, 20, 0, 'UPCOMING', TRUE),
    (6, 7, 3, 6, 9, 180, 20, 0, 'UPCOMING', FALSE),
    (7, 3, 4, 7, 7, 120, 18, 3, 'UPCOMING', FALSE),
    (8, 8, 4, 8, 8, 90, 12, 0, 'UPCOMING', FALSE),
    (9, 4, 5, 9, 9, 180, 14, 0, 'UPCOMING', TRUE),
    (10, 9, 5, 10, 7, 240, 16, 8, 'UPCOMING', TRUE),
    (11, 5, 6, 11, 8, 90, 12, 3, 'UPCOMING', FALSE),
    (12, 10, 6, 12, 9, 150, 12, 0, 'UPCOMING', FALSE),
    (13, 13, 1, 13, 7, 120, 20, 5, 'UPCOMING', TRUE),
    (14, 14, 1, 14, 8, 150, 20, 14, 'UPCOMING', FALSE),
    (15, 15, 1, 15, 9, 180, 25, 18, 'UPCOMING', FALSE),
    (16, 16, 1, 16, 7, 90, 16, 12, 'UPCOMING', FALSE),
    (17, 17, 1, 17, 8, 120, 30, 8, 'UPCOMING', FALSE),
    (18, 18, 1, 18, 9, 100, 25, 4, 'UPCOMING', FALSE),
    (19, 19, 1, 19, 7, 80, 20, 2, 'UPCOMING', FALSE),
    (20, 20, 1, 20, 8, 60, 15, 1, 'UPCOMING', FALSE);

INSERT INTO additional_activity_price_list
(id, additional_activity_execution_id, price, valid_from, valid_to)
VALUES
    (1, 1, 35, '2026-07-10', '2026-07-20'),
    (2, 2, 45, '2026-07-10', '2026-07-20'),
    (3, 3, 35, '2026-08-01', '2026-08-10'),
    (4, 4, 45, '2026-08-01', '2026-08-10'),
    (5, 5, 30, '2026-09-05', '2026-09-09'),
    (6, 6, 40, '2026-09-05', '2026-09-09'),
    (7, 7, 25, '2026-12-20', '2026-12-27'),
    (8, 8, 60, '2026-12-20', '2026-12-27'),
    (9, 9, 55, '2026-08-01', '2026-08-10'),
    (10, 10, 70, '2026-08-01', '2026-08-10'),
    (11, 11, 35, '2026-06-15', '2026-06-22'),
    (12, 12, 45, '2026-06-15', '2026-06-22'),
    (13, 13, 50, '2026-07-10', '2026-07-20'),
    (14, 14, 35, '2026-07-10', '2026-07-20'),
    (15, 15, 25, '2026-07-10', '2026-07-20'),
    (16, 16, 20, '2026-07-10', '2026-07-20'),
    (17, 17, 30, '2026-07-10', '2026-07-20'),
    (18, 18, 15, '2026-07-10', '2026-07-20'),
    (19, 19, 10, '2026-07-10', '2026-07-20'),
    (20, 20, 18, '2026-07-10', '2026-07-20');

INSERT INTO additional_activity_registration
(id, user_id, additional_activity_execution_id,
 number_of_participants, registration_date, status)
VALUES
    (1, 1, 1, 5, '2026-05-25 12:35:00', 'ACTIVE'),
    (2, 10, 1, 2, '2026-05-26 09:15:00', 'ACTIVE'),
    (3, 11, 1, 3, '2026-05-27 14:20:00', 'ACTIVE'),

    (4, 1, 2, 2, '2026-05-28 18:10:00', 'ACTIVE'),
    (5, 10, 2, 2, '2026-05-29 11:45:00', 'ACTIVE'),
    (6, 11, 2, 5, '2026-05-30 16:00:00', 'ACTIVE'),

    (7, 12, 3, 2, '2026-06-01 10:00:00', 'ACTIVE'),
    (8, 1, 3, 3, '2026-06-02 12:30:00', 'ACTIVE'),
    (9, 10, 3, 1, '2026-06-03 09:10:00', 'CANCELLED'),

    (10, 12, 4, 2, '2026-06-04 20:10:00', 'ACTIVE'),
    (11, 1, 4, 5, '2026-06-05 13:30:00', 'ACTIVE'),

    (12, 1, 7, 3, '2026-11-02 15:45:00', 'ACTIVE'),

    (13, 13, 10, 2, '2026-05-03 11:15:00', 'ACTIVE'),
    (14, 1, 10, 2, '2026-05-04 12:00:00', 'ACTIVE'),
    (15, 11, 10, 4, '2026-05-05 16:35:00', 'ACTIVE'),

    (16, 1, 11, 3, '2026-04-21 10:25:00', 'ACTIVE'),
    (17, 1, 14, 2, '2026-06-10 10:00:00', 'ACTIVE'),
    (18, 11, 14, 3, '2026-06-10 11:00:00', 'ACTIVE'),
    (19, 12, 14, 2, '2026-06-10 12:00:00', 'ACTIVE'),

    (20, 1, 15, 2, '2026-06-11 10:00:00', 'ACTIVE'),
    (21, 11, 15, 2, '2026-06-11 11:00:00', 'ACTIVE'),

    (22, 1, 16, 2, '2026-06-12 10:00:00', 'ACTIVE'),

    (23, 11, 17, 2, '2026-06-13 10:00:00', 'ACTIVE'),

    (24, 1, 18, 1, '2026-06-14 10:00:00', 'ACTIVE');

INSERT INTO category (id, name)
VALUES
    (1, 'Avantura'),
    (2, 'Kultura'),
    (3, 'Gastronomija'),
    (4, 'Priroda'),
    (5, 'Sport'),
    (6, 'Istorija'),
    (7, 'Nocni zivot'),
    (8, 'Porodicne aktivnosti'),
    (9, 'Relax'),
    (10, 'Luksuz');

INSERT INTO additional_activity_category
(additional_activity_id, category_id)
VALUES

(1, 4),
(2, 2),
(2, 6),
(3, 5),
(3, 1),
(4, 1),
(4, 5),
(5, 2),
(5, 9),
(6, 3),
(6, 7),
(7, 2),
(7, 6),
(8, 1),
(8, 5),
(9, 1),
(9, 4),
(10, 2),
(10, 6),
(13, 4),
(14, 1),
(14, 5),
(15, 4),
(15, 1),
(16, 5),
(17, 3),
(18, 2),
(19, 8),
(20, 9);


INSERT INTO user_liked_category
(user_id, category_id)
VALUES

-- Ivana
(1, 4),   -- Priroda
(1, 9),   -- Relax
(1, 2),   -- Kultura

-- Marko
(10, 1),  -- Avantura
(10, 5),  -- Sport
(10, 4),  -- Priroda

-- Ana
(11, 2),  -- Kultura
(11, 6),  -- Istorija
(11, 3),  -- Gastronomija

-- Luka
(12, 1),  -- Avantura
(12, 7),  -- Nocni zivot
(12, 3),  -- Gastronomija

-- Milica
(13, 10), -- Luksuz
(13, 9),  -- Relax
(13, 8);  -- Porodicne aktivnosti


INSERT INTO pricing_rule
(id, name, type, percentage, arrangement_id, season_start, season_end,
 min_occupancy_percent, max_days_before_start, min_days_before_start, active)
VALUES
    (1, 'Letnja sezona za Krf', 'SEASON', 25, 1, '2026-07-01', '2026-08-31',
     null, null, null, true),

    (2, 'Zimska sezona za Kopaonik', 'SEASON', 30, 3, '2026-12-01', '2027-03-15',
     null, null, null, true),

    (3, 'Letnja sezona za Hurgadu', 'SEASON', 20, 4, '2026-06-01', '2026-09-30',
     null, null, null, true),

    (4, 'Popust za early booking', 'EARLY_BOOKING', -10, null, null, null,
     null, null, 60, true),

    (5, 'Last minute popust', 'LAST_MINUTE', -20, null, null, null,
     null, 7, null, true),

    (6, 'Doplata za veliku popunjenost', 'OCCUPANCY', 15, null, null, null,
     80, null, null, true);

INSERT INTO reservation_passenger
(id, reservation_id, first_name, last_name, age, price, discount_description)
VALUES
    (1, 1, 'Petar', 'Petrović', 34, 450, 'Puna cena'),
    (2, 1, 'Mila', 'Petrović', 8, 225, 'Dečiji popust 50%'),

    (3, 2, 'Ana', 'Anić', 30, 450, 'Puna cena'),
    (4, 2, 'Lena', 'Anić', 4, 0, 'Dete do 5 godina - gratis'),
    (5, 2, 'Marko', 'Anić', 10, 225, 'Dečiji popust 50%');

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('destination_id_seq', (SELECT MAX(id) FROM destination));
SELECT setval('accommodation_id_seq', (SELECT MAX(id) FROM accommodation));
SELECT setval('transport_id_seq', (SELECT MAX(id) FROM transport));
SELECT setval('additional_service_id_seq', (SELECT MAX(id) FROM additional_service));
SELECT setval('arrangement_id_seq', (SELECT MAX(id) FROM arrangement));
SELECT setval('term_id_seq', (SELECT MAX(id) FROM term));
SELECT setval('arrangement_term_id_seq', (SELECT MAX(id) FROM arrangement_term));
SELECT setval('reservation_id_seq', (SELECT MAX(id) FROM reservation));
SELECT setval('workflow_id_seq', (SELECT MAX(id) FROM workflow));
SELECT setval('workflow_phase_id_seq', (SELECT MAX(id) FROM workflow_phase));
SELECT setval('destination_calendar_id_seq', (SELECT MAX(id) FROM destination_calendar));
SELECT setval('additional_activity_id_seq', (SELECT MAX(id) FROM additional_activity));
SELECT setval('activity_term_id_seq', (SELECT MAX(id) FROM activity_term));
SELECT setval('additional_activity_execution_id_seq', (SELECT MAX(id) FROM additional_activity_execution));
SELECT setval('additional_activity_price_list_id_seq', (SELECT MAX(id) FROM additional_activity_price_list));
SELECT setval('additional_activity_registration_id_seq', (SELECT MAX(id) FROM additional_activity_registration));
SELECT setval('category_id_seq', (SELECT MAX(id) FROM category));
SELECT setval('pricing_rule_id_seq', (SELECT MAX(id) FROM pricing_rule));
SELECT setval('reservation_passenger_id_seq', (SELECT MAX(id) FROM reservation_passenger));