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
id_source_number int,
destination_number varchar(50),
id_destination_number int,
date_call date,
id_source_city int,
id_destination_city int,
constraint fk_id_source_number_call foreign key (id_source_number) references telephone_lines(id),
constraint fk_id_destination_number_call foreign key (id_destination_number) references telephone_lines(id),
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
create procedure create_provinces()
begin
insert into provinces (name,id_country) 
values 
("Buenos Aires",1),
("Catamarca",1) ,
("Chaco",1) , 
("Chubut",1) ,
("Cordoba",1) ,
("Corrientes",1) ,
("Entre Rios",1) , 
("Formosa",1) , 
("Jujuy",1) ,   
("La Pampa",1) , 
("La Rioja",1) ,
("Mendoza",1) ,    
("Misiones",1) ,
("Neuquen",1) , 
("Rio Negro",1) ,
("Salta",1) ,  
("San Juan",1) , 
("San Luis",1) ,
("Santa Cruz",1) ,   
("Santa Fe",1) , 
("Santiago del Estero",1) ,      
("Tierra del Fuego",1) , 
("Tucuman",1);              
end //     

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
drop procedure add_fees
delimiter //
create procedure add_fees()
begin

declare i int;
declare j int;
declare total_cities int;
declare aux_ppm float;
declare aux_cpm float;

-- SET total_cities = (select count(id) from cities);
SET total_cities = 250;
SET i = 1;
set j=1;
set aux_ppm = 0;
set aux_cpm = 0;

truncate table fees;
    start transaction;

while i < total_cities do
		while j < total_cities do
			set aux_ppm = (select TRUNCATE (((50 + (rand() * 250))/100), 2));	
			set aux_cpm = (select TRUNCATE (aux_ppm * TRUNCATE (((20 + (rand() * 80))/100), 2),2));
			insert into fees (price_per_minute,cost_per_minute,id_source_city,id_destination_city) values ( aux_ppm, aux_cpm,i,j);
			set j=j+1;
		end while;

	set i=i+1;
	set j=1;
end while;
	commit;
end //
call add_fees()

select * from fees
50 250
select count(id) from cities

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
select * from telephone_lines
select * from cities where id = 190
select * from calls
delimiter //
create procedure add_calls()
begin
insert into calls (source_number, destination_number, duration_secs, date_call) values ('2236784509', '2235436785', 160,'2020-06-23');
insert into calls (source_number, destination_number, duration_secs, date_call) values ('2236784509', '2215908654', 60,'2020-06-20');
insert into calls (source_number, destination_number, duration_secs, date_call) values ('2236784509', '2235436785', 60,'2020-06-20');
insert into calls (source_number, destination_number, duration_secs, date_call) values ('2236784509', '115098521', 120,'2020-06-21');
insert into calls (source_number, destination_number, duration_secs, date_call) values ('2236784509', '115098521', 180,'2020-06-22');
insert into calls (source_number, destination_number, duration_secs, date_call) values ('2236784509', '115098521', 30,'2020-06-22');
end //
183 146 1
delimiter //
create procedure add_invoices()
begin
insert into invoices (total_price, total_cost, date_creation, date_expiration, id_telephone_line, id_user, paid) values (23.3, 7.68, '2020-05-30', '2020-06-15', 1, 1, false);
end //

delimiter // 
create procedure add_country_provinces_cities()
begin
insert into countries (name) values ('Argentina');
end //


-- CALL STORED PROCEDURES

call add_country_provinces_cities();
call create_provinces();

call add_users();
call add_telephone_lines();
call add_fees();
call add_invoices();
call add_calls();

select * from cities where name = "mar del plata"



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

delimiter //
create procedure backoffice_request_fee_by_id(IN idCityFrom int, IN idCityTo int)
begin
select cf.name as cityFrom, ct.name as cityTo, f.price_per_minute as fee 
from fees f 
inner join cities cf
on f.id_source_city = cf.id 
inner join cities ct
on f.id_destination_city = ct.id
where f.id_source_city = idCityFrom and f.id_destination_city = idCityTo;
end // 

drop procedure backoffice_request_fee_by_id;
call backoffice_request_fee_by_id(1, 2);

delimiter //
create procedure backoffice_request_fee(IN cityFrom varchar(50), IN cityTo varchar(50))
begin
DECLARE idCityFrom int;
DECLARE idCityTo int;
SELECT id FROM cities c WHERE c.name = cityFrom INTO idCityFrom;
SELECT id FROM cities c WHERE c.name = cityTo INTO idCityTo;
select cityFrom, cityTo, f.price_per_minute as fee 
from fees f 
where f.id_source_city = idCityFrom and f.id_destination_city = idCityTo;
end // 

drop procedure backoffice_request_fee;
call backoffice_request_fee("Mar del Plata", "Buenos Aires");


-- 5) Consulta de llamadas por usuario.

delimiter //
create procedure backoffice_request_calls_user_simple(IN dni int)
begin
select u.firstname, u.lastname, u.dni, count(ca.id) as callsMade
from users u
inner join telephone_lines t 
on u.id = t.id_user
inner join calls ca
on t.line_number = ca.source_number
where u.dni = dni
group by u.id;
end //

delimiter //
create procedure backoffice_request_calls_user(IN dni int)
begin
select u.firstname, u.lastname, u.dni, ca.id as idCall, ca.total_cost as totalCost, ca.total_price as totalPrice, ca.date_call as dateCall,
ca.source_number as sourceNumber, ca.destination_number as destinationNumber
from calls ca
inner join telephone_lines t
on ca.source_number = t.line_number
inner join users u
on t.id_user = u.id
where u.dni = dni;
end //

drop procedure backoffice_request_calls_user;
call backoffice_request_calls_user('41307541');

-- 6) Consulta de facturación. La facturación se hará directamente por un proceso interno en la base datos.

-- ver facturas de un usuario
delimiter //
create procedure backoffice_invoices_from_user(IN dni varchar(50))
begin
select u.dni, u.lastname, u.firstname, t.line_number, i.date_creation, i.date_expiration, i.total_cost, i.total_price, i.paid
from users u
inner join telephone_lines t
on u.id = t.id_user
inner join invoices i
on t.id = i.id_telephone_line
where u.dni = dni;
end //


-- ver facturas pagadas/sin pagar de un usuario
delimiter //
create procedure backoffice_invoices_from_user_paid(IN dni varchar(50))
begin
select u.dni, u.lastname, u.firstname, t.line_number, i.date_creation, i.date_expiration, i.total_cost, i.total_price, i.paid
from users u
inner join telephone_lines t
on u.id = t.id_user
inner join invoices i
on t.id = i.id_telephone_line
where u.dni = dni and i.paid = 1;
end //


delimiter //
create procedure backoffice_invoices_from_user_not_paid(IN dni varchar(50))
begin
select u.dni, u.lastname, u.firstname, t.line_number, i.date_creation, i.date_expiration, i.total_cost, i.total_price, i.paid
from users u
inner join telephone_lines t
on u.id = t.id_user
inner join invoices i
on t.id = i.id_telephone_line
where u.dni = dni and i.paid = 0;
end //


-- ver facturas de un mes
delimiter //
create procedure backoffice_invoices_from_month(IN monthI varchar(50))
begin
select t.line_number, i.date_creation, i.date_expiration, i.total_cost, i.total_price, i.paid
from telephone_lines t
inner join invoices i
on t.id = i.id_telephone_line
where month(i.date_creation) = monthI;
end //


-- ver facturas de un año

delimiter //
create procedure backoffice_invoices_from_year(IN yearI varchar(50))
begin
select t.line_number, i.date_creation, i.date_expiration, i.total_cost, i.total_price, i.paid
from telephone_lines t
inner join invoices i
on t.id = i.id_telephone_line
where year(i.date_creation) = yearI;
end //


-- ver facturas de un periodo

delimiter //
create procedure backoffice_invoices_between_dates(IN fromI varchar(50), IN toI varchar(50))
begin
select t.line_number, i.date_creation, i.date_expiration, i.total_cost, i.total_price, i.paid
from telephone_lines t
inner join invoices i
on t.id = i.id_telephone_line
where i.date_creation between fromI and toI;
end //


-- ver ganancias 

delimiter //
create procedure backoffice_check_income()
begin
select (sum(i.total_price)-sum(i.total_cost)) as income
from invoices i;
end //

-- ver ganancias de un mes

delimiter //
create procedure backoffice_check_income_month(IN monthI varchar(50))
begin
select (sum(i.total_price)-sum(i.total_cost)) as income
from invoices i
where month(i.date_creation) = monthI;
end //

-- ver ganancias de un año

delimiter //
create procedure backoffice_check_income_year(IN yearI varchar(50))
begin
select (sum(i.total_price)-sum(i.total_cost)) as income
from invoices i
where year(i.date_creation) = yearI;
end //

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


-- TRIGGER de calls
drop trigger tbi_call_complete_and_check
DELIMITER // 
CREATE TRIGGER tbi_call_complete_and_check BEFORE INSERT ON calls FOR EACH ROW
BEGIN 
declare ppp float;
declare cpp float;
declare cost float;
declare price float;
declare id_source int;
declare id_dest int;
declare duration_minutes int;

IF not EXISTS (select * 
FROM telephone_lines t
WHERE NEW.source_number = t.line_number) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'El numero origen no existe', mysql_errno = 1000;
END if;

if not exists (select * 
FROM telephone_lines t
WHERE NEW.destination_number = t.line_number) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'El numero destino no existe', mysql_errno = 1000;
END IF;

if (NEW.destination_number = NEW.source_number) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'Los numeros son iguales', mysql_errno = 1000;
END IF;

IF
(NEW.duration_secs <= 0) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'Los duracion debe ser mayor q cero', mysql_errno = 1000;
END IF;

IF (NEW.date_call > now()) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'La fecha no puede ser futura', mysql_errno = 1000;
END IF;

IF (NEW.date_call < (NOW() - INTERVAL 10 DAY)) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'La fecha tiene no puede ser anterior a 10 dias', mysql_errno = 1000;
END IF;

select u.id_city from users u join telephone_lines t on u.id=t.id_user  where t.line_number = NEW.source_number into id_source;
select u.id_city from users u join telephone_lines t on u.id=t.id_user  where t.line_number = NEW.destination_number into id_source;

select f.price_per_minute, f.cost_per_minute
from fees f
where (f.id_source_city = id_source) and (f.id_destination_city = id_dest) into ppp,cpp;

select CEIL(new.duration_secs / 60) into duration_minutes;

select ppp * duration_minutes , cpp * duration_minutes into price,cost;

SET NEW.id_source_number = (select t.id from telephone_lines t where t.line_number = NEW.source_number  limit 1);
SET NEW.id_destination_number = (select t.id from telephone_lines t where t.line_number = NEW.destination_number  limit 1);
SET NEW.price_per_minute = ppp;
SET NEW.total_cost = cost;
SET NEW.total_price = price;
SET NEW.id_source_city = id_source;
SET NEW.id_destination_city = id_dest;
END//

insert into calls (source_number, destination_number,duration_secs,date_call) value ("2236784509","2235436785",120,"2020-06-20");
insert into calls (total_price) value (200);

-- STORED PROCEDURE ADD CALL

delimiter //
create procedure add_call_aerial(IN sourceNumber varchar(50), IN destinationNumber varchar(50), IN duration int, IN dateCall date)
begin 
insert into calls(source_number, destination_number, duration_secs, date_call) values (sourceNumber, destinationNumber, duration, dateCall);
end //

call add_call_aerial("2236784509", "2235436785", 120, "2020-06-20");

drop trigger tbi_new_city
DELIMITER // 
CREATE TRIGGER tbi_new_city BEFORE INSERT ON cities FOR EACH ROW
BEGIN 
IF EXISTS (select * 
FROM cities c
WHERE NEW.name = c.name) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'La ciudad que desea ingresar ya existe', mysql_errno = 1000;
END if;
END//

delimiter // 
create procedure insert_city(IN name_var varchar(50),IN prefix_var varchar (10), IN province_name_var varchar (50))
begin
declare id_province int;
SELECT p.id FROM provinces p WHERE p.name = province_name_var   LIMIT 1 into id_province;
insert into cities(name, prefix_number,id_province) values (name_var,prefix_var, id_province);
end // 

