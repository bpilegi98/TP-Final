SET GLOBAL time_zone = '-3:00';
drop database tpfinal;
create database tpfinal;
use tpfinal;


create table countries(
id int auto_increment primary key,
name varchar(50) unique
);


create table provinces(
id int auto_increment primary key,
name varchar(50),
id_country int,
constraint fk_country foreign key (id_country) references countries(id)
);

create table cities(
id int auto_increment primary key,
name varchar(50),
prefix_number varchar(4),
id_province int,
constraint fk_province foreign key (id_province) references provinces(id)
);

create table users(
id int auto_increment primary key,
firstname varchar(50),
lastname varchar(50),
dni varchar(50) unique,
username varchar(50) UNIQUE,
password varchar(50),
user_type enum('AERIAL', 'EMPLOYEE', 'CUSTOMER'),
is_active boolean,
id_city int,
constraint fk_city_user foreign key (id_city) references cities(id)
);

create table fees(
id int auto_increment primary key,
price_per_minute float,
cost_per_minute float,
id_source_city int,
id_destination_city int,
constraint fk_source_city_fee foreign key (id_source_city) references cities(id),
constraint fk_destination_city_fee foreign key (id_destination_city) references cities(id)
);

create table telephone_lines(
id int auto_increment primary key,
line_number varchar(50) unique,
line_type enum('MOBILE', 'RESIDENTIAL'),
status enum('SUSPENDED', 'ACTIVE', 'DELETED'),
id_user int,
constraint fk_person_telephone_line foreign key (id_user) references users(id)
);


create table invoices(
id int auto_increment primary key,
total_price float,
total_cost float,
date_creation date,
date_expiration date,
paid boolean,
id_telephone_line int,
id_user int,
constraint fk_telephone_line foreign key (id_telephone_line) references telephone_lines(id),
constraint fk_person_invoice foreign key (id_user) references users(id)
);

create table calls(
id int auto_increment primary key,
price_per_minute float,
duration_secs int,
total_cost float,
total_price float,
source_number varchar(50),
destination_number varchar(50),
date_call date,
id_source_city int,
id_destination_city int,
constraint fk_source_number_call foreign key (source_number) references telephone_lines(line_number),
constraint fk_destination_number_call foreign key (destination_number) references telephone_lines(line_number),
constraint fk_id_source_city_call foreign key (id_source_city) references cities(id),
constraint fk_id_destination_city_call foreign key (id_destination_city) references cities(id)
);

create table invoice_calls(
id int auto_increment primary key,
id_invoice int,
id_call int,
constraint fk_invoice_ic foreign key(id_invoice) references invoices(id),
constraint fk_call_ic foreign key(id_call) references calls(id)
)





-- STORED PROCEDURES

delimiter //
create procedure add_users()
begin
insert into users(firstname, lastname, dni, username, password, id_city, user_type,is_active) values ('bianca', 'pilegi', '41307541', 'bpilegi98','1234', 1, 'CUSTOMER',true);
insert into users(firstname, lastname, dni, username, password, id_city, user_type,is_active) values ('juan martin', 'ludueña', '41306543', 'juanludu', '4321', 1, 'EMPLOYEE',true);
insert into users(firstname, lastname, dni, username, password, id_city, user_type,is_active) values ('fisrtname1', 'lastname1', '1111111', 'username1', 'password1', 3, 'CUSTOMER',true);
insert into users(firstname, lastname, dni, username, password, id_city, user_type,is_active) values ('fisrtname2', 'lastname2', '2222222', 'username2', 'password2', 4, 'CUSTOMER',true);
insert into users(firstname, lastname, dni, username, password, id_city, user_type,is_active) values ('fisrtname3', 'lastname3', '3333333', 'username3', 'password3', 4, 'CUSTOMER',true);
insert into users(firstname, lastname, dni, username, password, id_city, user_type,is_active) values ('fisrtname4', 'lastname4', '4444444', 'username4', 'password4', 2, 'CUSTOMER',true);
end //

delimiter //
create procedure add_fees()
begin
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (1, 2, 3, 1);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (2, 1, 3, 1);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (1, 3, 5, 2);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (3, 1, 5, 2);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (1, 4, 1, 0.25);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (4, 1, 1, 0.25);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (2, 3, 2, 1);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (3, 2, 2, 1);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (2, 4, 4.5, 2);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (4, 2, 4.5, 2);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (3, 4, 6, 2.5);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (4, 3, 6, 2.5);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (1, 1, 0.5, 0.05);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (2, 2, 0.5, 0.05);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (3, 3, 0.5, 0.05);
insert into fees (id_source_city, id_destination_city, price_per_minute, cost_per_minute) values (4, 4, 0.5, 0.05);
end //


delimiter //
create procedure add_telephone_lines()
begin
insert into telephone_lines (line_number, line_type, id_user, status) values ('2236784509', 'MOBILE', 1, 'ACTIVE');
insert into telephone_lines (line_number, line_type, id_user, status) values ('2235436785', 'MOBILE', 2, 'ACTIVE');
insert into telephone_lines (line_number, line_type, id_user, status) values ('2215908654', 'MOBILE', 3, 'ACTIVE');
insert into telephone_lines (line_number, line_type, id_user, status) values ('2914127384', 'MOBILE', 4, 'ACTIVE');
insert into telephone_lines (line_number, line_type, id_user, status) values ('2916987653', 'MOBILE', 5, 'ACTIVE');
insert into telephone_lines (line_number, line_type, id_user, status) values ('115098521', 'MOBILE', 6, 'ACTIVE');
insert into telephone_lines (line_number, line_type, id_user, status) values ('2234678564', 'RESIDENTIAL', 1, 'ACTIVE');
insert into telephone_lines (line_number, line_type, id_user, status) values ('2914738495', 'RESIDENTIAL', 4, 'ACTIVE');
end //

delimiter //
create procedure add_calls()
begin
insert into calls (source_number, destination_number, price_per_minute, duration_secs, total_cost, total_price, date_call, id_source_city, id_destination_city) values ('2236784509', '2235436785', 0.5, 160, 0.13, 1.3,'2020-06-01' , 1, 1);
insert into calls (source_number, destination_number, price_per_minute, duration_secs, total_cost, total_price, date_call, id_source_city, id_destination_city) values ('2236784509', '2215908654', 5, 60, 2, 5, '2020-06-05', 1, 3);
insert into calls (source_number, destination_number, price_per_minute, duration_secs, total_cost, total_price, date_call, id_source_city, id_destination_city) values ('2236784509', '2235436785', 0.5, 60, 0.05, 0.5, '2020-06-09', 1, 1);
insert into calls (source_number, destination_number, price_per_minute, duration_secs, total_cost, total_price, date_call, id_source_city, id_destination_city) values ('2236784509', '115098521', 3, 120, 2, 6, '2020-06-09', 1, 2);
insert into calls (source_number, destination_number, price_per_minute, duration_secs, total_cost, total_price, date_call, id_source_city, id_destination_city) values ('2236784509', '115098521', 3, 180, 3, 9, '2020-06-11', 1, 2);
insert into calls (source_number, destination_number, price_per_minute, duration_secs, total_cost, total_price, date_call, id_source_city, id_destination_city) values ('2236784509', '115098521', 3, 30, 0.5, 1.5, '2020-06-13', 1, 2);
end //


delimiter //
create procedure add_invoices()
begin
insert into invoices (total_price, total_cost, date_creation, date_expiration, id_telephone_line, id_user, paid) values (23.3, 7.68, '2020-05-30', '2020-06-15', 1, 1, false);
end //

delimiter // 
create procedure add_country_provinces_cities()
begin
insert into countries (name) values ('Argentina');

insert into provinces(name,id_country) values('Buenos Aires',1);

insert into cities(name, prefix_number, id_province) values ('Mar del plata', '223',1),('Buenos Aires','11',1),('La Plata','221',1),('Bahia Blanca','291',1);
end //


-- CALL STORED PROCEDURES

call add_country_provinces_cities();
call add_users();
call add_telephone_lines();
call add_fees();
call add_invoices();
call add_calls();





-- Devolver el numero al que más llamó un usuario

select u.firstname, u.lastname, c.destination_number as dest from users u 
inner join telephone_lines t 
on t.id_user = u.id 
inner join calls c 
on c.source_number = t.line_number 
where u.id = 1 
group by c.destination_number 
order by  count(c.destination_number) desc 
limit 1;


-- Endpoint que devuelva la cantidad de llamados que recibio la linea X

select t.line_number as LineNumber, count(c.id) as CallsReceived from calls c
inner join telephone_lines t
on c.destination_number = t.line_number
where c.destination_number = '115098521';

select * from calls 

-- API 
-- 2) Consulta de llamadas del usuario logueado por rango de fechas

delimiter //
create procedure user_calls_between_dates(IN fromD date, IN toD date, IN idLoggedUser int)
begin
select c.destination_number as calledNumber, c.duration_secs as callDuration, c.total_price as totalPrice
from calls c 
join telephone_lines t
on c.source_number = t.line_number
join users u
on t.id_user = u.id
where u.id = idLoggedUser and c.date_call between fromD and toD;
end //

-- 3) Consulta de facturas del usuario logueado por rango de fechas.

delimiter //
create procedure user_invoices_between_dates(IN fromD date, IN toD date, IN idLoggedUser int)
begin
select i.date_creation as period_from, i.date_expiration as period_to, t.line_number, i.total_price, i.paid
from invoices i 
join telephone_lines t
on i.id_telephone_line = t.id
join users u
on t.id_user = u.id
where u.id = idLoggedUser and i.date_creation between fromD and toD;
end //


-- 4) Consulta de TOP 10 destinos más llamados por el usuario

delimiter //
create procedure user_top_most_called(IN idLoggedUser int)
begin
select c.destination_number as number_called, count(c.destination_number) as times_called
from calls c 
join telephone_lines t
on c.source_number = t.line_number
join users u
on t.id_user = u.id
where u.id = idLoggedUser
group by number_called
limit 10;
end //


-- BACK OFFICE
-- 2) Manejo de clientes
-- 3) Alta , baja y suspensión de líneas.
-- 4) Consulta de tarifas
-- 5) Consulta de llamadas por usuario.
-- 6) Consulta de facturación. La facturación se hará directamente por un proceso interno en la base datos.

-- AERIAL
-- Se debe permitir también el agregado de llamadas, con un login especial, ya que
-- este método de nuestra API será llamado nada más que por el área de
-- infraestructura cada vez que se produzca una llamada. El área de infraestructura
-- sólo enviará la siguiente información de llamadas :
-- ○ Número de origen
-- ○ Número de destino
-- ○ Duración de la llamada
-- ○ Fecha y hora de la llamada
-- La tarifa y las localidades de destino deberán calcularse al momento de guardar la
-- llamada y no será recibido por la API REST.


-- HOLA JUAN Q TAL
