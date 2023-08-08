DELETE FROM hits CASCADE;

INSERT INTO hits (id, app, uri, ip, time_stamp)
VALUES (1000, 'ewm-main-service', '/events/1', '192.163.0.1', '2022-09-06 11:00:23'),
       (1001, 'ewm-main-service', '/events/1', '192.163.0.1', '2022-09-06 11:00:23'),
       (1002, 'ewm-main-service', '/feeds/1', '192.163.0.1', '2022-09-06 11:00:23');