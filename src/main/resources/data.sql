INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME, USER_ID)
VALUES ('Domino Pizza', 1),
       ('Pizza Hut', 2);

INSERT INTO MENU (MENU_DATE, RESTAURANT_ID)
VALUES (current_date(), 1),
       ('2023-02-10', 2);

INSERT INTO MEAL (name, PRICE, MENU_ID)
VALUES ('Филе Миньон', 300, 1),
       ('Бефстроганов', 100, 1),
       ('Каре ягненка', 200, 1),
       ('Тирамису', 50, 2),
       ('Пана кота', 50, 2),
       ('Минестроне', 70, 2),
       ('Борщ', 70, 2);

INSERT INTO VOTE (VOTING_DATE, RESTAURANT_ID, USER_ID)
VALUES (current_date(), 1, 1),
       ('2023-02-10', 1, 2);