DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin'),
       ('User', 'user@yandex.ru', 'password');

INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 100000),
       ('USER', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2018-09-01 09:00:00', 'Админ завтрак', 500),
       (100000, '2018-09-01 19:00:00', 'Админ ужин', 1000),
       (100001, '2018-08-31 09:00:00', 'Завтрак', 500),
       (100001, '2018-08-31 14:00:00', 'Обед', 1000),
       (100001, '2018-08-31 19:00:00', 'Ужин', 500),
       (100001, '2018-09-01 10:00:00', 'Завтрак', 500),
       (100001, '2018-09-01 13:00:00', 'Обед', 1000),
       (100001, '2018-09-01 20:00:00', 'Ужин', 510);