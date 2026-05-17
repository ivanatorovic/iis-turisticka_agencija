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
    (5, '2026-06-15', '2026-06-22');

INSERT INTO arrangement_term
(id, arrangement_id, term_id, capacity, reserved_spots)
VALUES
    (1, 1, 1, 40, 0),
    (2, 1, 2, 35, 0),
    (3, 2, 3, 25, 0),
    (4, 3, 4, 30, 0),
    (5, 4, 2, 20, 0),
    (6, 5, 5, 15, 0);

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
);