-- SQL-dialect for PostgreSQL

-- start Docker PostgreSQL in container: just copy-paste in terminal text below
-- docker run -d -p 5432:5432 --rm -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=sensor_rest_db --name postgeSQL -it postgres

create table sensors (
    id int primary key generated by default as identity,
    name varchar(30) not null unique,
    registration_time timestamp not null
);

create table measurements (
    measure_id int primary key generated by default as identity,
    sensor_id int references sensors(id) on delete set null,
    update_time timestamp not null ,
    temperature_celsius float check ( temperature_celsius >= -100.0) check ( temperature_celsius <= 100.0 ),
    raining boolean not null
);

insert into sensors(name, registration_time) VALUES ('sensor1', '2022-12-29 19:10:25');
insert into sensors(name, registration_time) VALUES ('sensor2', '2023-01-25 19:10:25');
insert into sensors(name, registration_time) VALUES ('sensor3', '2023-01-28 19:10:25');
insert into sensors(name, registration_time) VALUES ('sensor4', '2023-01-30 19:10:25');


insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (1, '2023-01-30 19:10:25', 18.0, false);
insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (2, '2023-01-30 19:10:25', 16.4, false);
insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (3, '2023-01-30 19:10:25', -2.3, false);
insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (2, '2023-01-30 19:10:25', 20, false);
insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (2, '2023-01-30 19:10:25', 25.7, true);
insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (2, '2023-01-30 19:10:25', 27, true);
insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (2, '2023-01-30 19:10:25', 21.55, false);
insert into measurements(sensor_id, update_time, temperature_celsius, raining) VALUES (4, '2023-01-30 19:10:25', 0.13, true);