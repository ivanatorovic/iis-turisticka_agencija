INSERT INTO arrangement (id, name, destination, description, base_price, image_url)
VALUES
    (1, 'Letovanje Krf', 'Grcka', 'Letovanje na Krfu sa autobuskim prevozom i smeštajem blizu plaže.', 450, '/arrangements/krf.jpg'),
    (2, 'Rim city break', 'Italija', 'Putovanje u Rim, obilazak znamenitosti i slobodno vreme za kupovinu.', 380, '/arrangements/rimjpg.jpg'),
    (3, 'Zimovanje Kopaonik', 'Srbija', 'Zimski aranžman na Kopaoniku sa hotelskim smeštajem.', 300, '/arrangements/zimovanje.jpg'),
    (4, 'Letovanje Hurgada', 'Egipat', 'All inclusive letovanje u Hurgadi sa avionskim prevozom.', 700, '/arrangements/hurgada.jpg'),
    (5, 'Pariz romantično putovanje', 'Francuska', 'Putovanje u Pariz sa obilaskom najpoznatijih znamenitosti.', 620, '/arrangements/pariz.jpg');

INSERT INTO term (id, start_date, end_date)
VALUES
    (1, '2026-07-10', '2026-07-20'),
    (2, '2026-08-01', '2026-08-10'),
    (3, '2026-09-05', '2026-09-09'),
    (4, '2026-12-20', '2026-12-27'),
    (5, '2026-06-15', '2026-06-22');

INSERT INTO arrangement_terms (arrangement_id, term_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 2),
    (5, 5);

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
    );