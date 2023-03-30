INSERT INTO USERS (EMAIL, NAME, PASSWORD)
VALUES ('user@yandex.ru', 'User', '{noop}password'),
       ('admin@gmail.com', 'Admin', '{noop}admin');


INSERT INTO USER_ROLES (ROLE, USER_ID)
VALUES ('USER', 1),
       ('USER', 2),
       ('ADMIN', 2);


INSERT INTO RESTAURANT (NAME, USER_ID)
VALUES ('Domino"s Pizza', 1),
       ('Pizza Hut', 2);

INSERT INTO MEAL (NAME, PUBLICATION_DATE, PRICE, RESTAURANT_ID)
VALUES ('Филе Миньон', '2023-02-10', 300, 1),
       ('Бефстроганов', '2023-02-10', 100, 1),
       ('Каре ягненка', '2023-02-10', 200, 1),
       ('Тирамису', '2023-02-10', 50, 2),
       ('Пана кота', current_date(), 50, 2),
       ('Минестроне', current_date(), 70, 2),
       ('Борщ', '2023-02-10', 70, 2);

INSERT INTO VOTE (VOTING_DATE, RESTAURANT_ID, USER_ID)
VALUES (current_date(), 1, 1),
       (current_date(), 1, 2);