DELETE FROM categories CASCADE;
DELETE FROM locations CASCADE;
DELETE FROM users CASCADE;
DELETE FROM events CASCADE;
DELETE FROM locations CASCADE;

INSERT INTO categories (id, name)
VALUES (1, 'drama'),
       (2, 'comedy');

INSERT INTO locations (id, lat, lon, radius, name, address, type)
VALUES (1, 1, 2, 1, 'Памятник святому', 'Улица свободы 21', 'Памятник'),
       (2, 3, 2, 1,  '', '', '');

INSERT INTO users (id, email, name)
VALUES (1, 'mail@mail.ru', 'name name'),
       (2, 'gmail@gmail.ru', 'jhon bon');

INSERT INTO events (annotation, category_id, confirmed_requests, created_on, description, event_date, initiator_id, location_id,
 paid, participant_limit, published_on, request_moderation, state, title, views)
VALUES ('annotation', 1, 0, '2023-08-14 19:13:51', 'description', '2023-08-14 19:13:51', 1, 1, 'true', 0, '2023-08-14 19:13:51',
'false', 'PUBLISHED', 'title', 0);