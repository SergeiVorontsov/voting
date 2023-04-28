INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest'),
       ('AnotherAdmin', 'another@admin.com', '{noop}another');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('ADMIN', 4),
       ('USER', 4);

INSERT INTO RESTAURANT (NAME, USER_ID)
VALUES ('Domino Pizza', 2),
       ('Pizza Hut', 2),
       ('Alfa', 4);

INSERT INTO MENU (MENU_DATE, RESTAURANT_ID)
VALUES (current_date(), 1),
       ('2023-02-10', 1),
       (current_date(), 2);

INSERT INTO MEAL (name, PRICE, MENU_ID)
VALUES ('Filet', 80, 1),
       ('Steak', 90, 1),
       ('Pizza', 50, 2),
       ('Tiramisu', 30, 2),
       ('Burger', 50, 3),
       ('Minestrone', 30, 3),
       ('Club Sandwich', 40, 3);

INSERT INTO VOTE (VOTING_DATE, RESTAURANT_ID, USER_ID)
VALUES (current_date(), 1, 1),
       ('2023-02-10', 2, 2);