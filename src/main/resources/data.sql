INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('user1@gmail.com', 'User1_First', 'User1_Last', '{noop}password1'),
       ('user2@gmail.com', 'User2_First', 'User2_Last', '{noop}password2'),
       ('user3@gmail.com', 'User3_First', 'User3_Last', '{noop}password3'),
       ('user4@gmail.com', 'User4_First', 'User4_Last', '{noop}password4'),
       ('admin@mail.ru', 'Admin_First', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5),
       ('ADMIN', 6),
       ('USER', 6);

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
       (current_date(), 1, 2),
       (current_date(), 1, 3),
       (current_date(), 2, 4),
       (current_date(), 2, 5),
       (current_date(), 2, 6);