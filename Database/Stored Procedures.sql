-- ///STORED PROCEDURES///

delimiter // 
create procedure add_country()
begin
insert into countries (name) values ('Argentina');
end //

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

-- Script de fees (tarda 15 minutos)
delimiter //
create procedure add_fees()
begin

declare i int;
declare j int;
declare total_cities int;
declare aux_ppm float;
declare aux_cpm float;

SET total_cities = (select count(id) from cities);
-- SET total_cities = 750;
SET i = 1;
set j=1;
set aux_ppm = 0;
set aux_cpm = 0;

truncate table fees;
    start transaction;

while i < total_cities do
		while j < total_cities do
			set aux_ppm = TRUNCATE (((50 + (rand() * 250))/100), 2);	
			set aux_cpm =TRUNCATE (aux_ppm * TRUNCATE (((20 + (rand() * 80))/100), 2),2);
			insert into fees (price_per_minute,cost_per_minute,id_source_city,id_destination_city) values ( aux_ppm, aux_cpm,i,j);
			set j=j+1;
		end while;

	set i=i+1;
	set j=1;
end while;
	commit;
end //

delimiter //
create procedure add_telephone_lines()
begin
insert into telephone_lines (line_number, line_type, id_user, status) values  ('116784509', 'MOBILE', 1, 'ACTIVE'),
('116784777', 'MOBILE', 1, 'ACTIVE'), ('2201234509', 'MOBILE', 2, 'ACTIVE'), ('2203214509', 'MOBILE', 2, 'ACTIVE'), ('2205554509', 'MOBILE', 3, 'ACTIVE'),
('2215141639', 'MOBILE', 4, 'ACTIVE'), ('221893147', 'MOBILE', 4, 'ACTIVE'), ('2236784509', 'MOBILE', 5, 'ACTIVE'), ('2306982659', 'MOBILE', 6, 'ACTIVE'),
('2306554409', 'MOBILE', 6, 'ACTIVE'), ('236921209', 'RESIDENTIAL', 7, 'ACTIVE'), ('2377788869', 'MOBILE', 8, 'ACTIVE'), ('35476784509', 'RESIDENTIAL', 1, 'ACTIVE');
end //

-- Script de cargar llamadas 
-- drop procedure add_x_calls
delimiter //
create procedure add_x_calls(in x_calls int)
begin
declare total_lines int;
declare i int default 0;
declare total_cities int;
declare date_var timestamp;
declare source_number_var varchar(30);
declare destination_number_var varchar(30) default 0;
declare duration_var int default 0;

SET total_lines = (select count(id) from telephone_lines);

start transaction;
	while i < x_calls do
		set source_number_var = (SELECT line_number from telephone_lines order by rand() limit 1);
	
		while(source_number_var = destination_number_var or destination_number_var = 0 ) DO
			set destination_number_var = (SELECT line_number from telephone_lines order by rand() limit 1);
		end while;
        -- un mes tiene 2628000 segundos, 10 dias 864000
		set date_var = FROM_UNIXTIME(UNIX_TIMESTAMP(now()- interval 10 day) + FLOOR(0 + (RAND() * 863000)));
		set duration_var = ROUND((50+ rand()*1000),0);
		insert into calls (source_number, destination_number, duration_secs, date_call) values (source_number_var, destination_number_var, duration_var, date_var);
		set i=i+1;
      set destination_number_var = 0;
	end while;
commit;
end //

-- CALL STORED PROCEDURES

call add_country();
call create_provinces();
-- /////////////////////////////////////////////////
-- STOP STOP STOP //////////////////////////////////
-- /////////////////////////////////////////////////
-- tenemos un script en java que carga las ciudades, aca deberias ir y ejecutarlo
-- agregar usuarios por java ya que si los agregas por sql no tendran sus passwords hasheadas y no podran logear
 call add_fees();
 call add_x_calls(5000);
 
-- ///FACTURACION///
DELIMITER //
CREATE PROCEDURE facturation()
begin
DECLARE total_price_var float;
DECLARE total_price_sum float DEFAULT 0;
DECLARE total_cost_var float;
DECLARE total_cost_sum float DEFAULT 0;
DECLARE date_creation_var date;
DECLARE date_expiration_var date;
DECLARE id_telephone_line_var int;
DECLARE id_user_var int;
DECLARE id_source_number_call int;
DECLARE id_invoice_var int;
DECLARE id_call_var int DEFAULT 0;
DECLARE vFinished INTEGER DEFAULT 0;

DECLARE cur_telephone_lines CURSOR FOR 
SELECT id, id_user
FROM telephone_lines;

DECLARE cur_calls CURSOR FOR 
SELECT id, total_cost, total_price,id_source_number
FROM calls
WHERE date_call > (DATE(now()) - INTERVAL 1 MONTH) and ISNULL(id_invoice);

DECLARE CONTINUE HANDLER FOR NOT FOUND 
SET vfinished=1;
SET date_creation_var = (select DATE(now()));
SET date_expiration_var =(select (DATE(now()) + INTERVAL 15 DAY));
start transaction;
OPEN cur_telephone_lines;
  LOOP1: LOOP
    FETCH cur_telephone_lines INTO id_telephone_line_var, id_user_var;
    IF vfinished =1 THEN
      LEAVE LOOP1;
    END IF;
    OPEN cur_calls;
    LOOP2: LOOP
      FETCH cur_calls INTO id_call_var, total_cost_var, total_price_var,id_source_number_call;
        IF vfinished = 1 THEN
			SET vfinished =0;
          LEAVE LOOP2;
		ELSE 
			IF (id_source_number_call = id_telephone_line_var) THEN
				IF(total_cost_sum = 0) THEN 
					INSERT INTO invoices (total_price, total_cost, date_creation, date_expiration, paid, id_telephone_line, id_user)
					values (0, 0, date_creation_var, date_expiration_var, 0, id_telephone_line_var, id_user_var);
                    SET id_invoice_var = last_insert_id();
                END IF;
                IF(id_invoice_var > 0) THEN
                    UPDATE calls
                    SET id_invoice = id_invoice_var
                    WHERE id=id_call_var;
				END IF;
				SET total_cost_sum = total_cost_sum + total_cost_var;
                SET total_price_sum = total_price_sum + total_price_var;
			END IF;
        END IF;
	SET vfinished =0;
    END LOOP LOOP2;
    IF (total_price_sum >0) THEN
		UPDATE invoices
        SET total_price = total_price_sum , total_cost = total_cost_sum
        where id = id_invoice_var;
        SET total_price_sum =0;
		SET total_cost_sum =0;
        SET id_invoice_var =0;
	END IF;
	CLOSE cur_calls;
  END LOOP LOOP1;
  CLOSE cur_telephone_lines;
  commit;
end //

-- Y su pequeño evento
CREATE EVENT facturation_event
ON SCHEDULE EVERY 1 MONTH STARTS '2020-07-01 00:00:00'
DO CALL facturation();

select * from invoices


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
-- drop procedure user_top_most_called
delimiter //
create procedure user_top_most_called(IN idLoggedUser int)
begin
select ct.name as city, count(c.id_destination_city) as times_called
from calls c 
join telephone_lines t
on c.source_number = t.line_number
join users u
on t.id_user = u.id
join cities ct
on c.id_destination_city = ct.id
where u.id = idLoggedUser 
group by city
order by times_called DESC
limit 10;
end //

delimiter //
create procedure user_top_most_called_lines(IN idLoggedUser int)
begin
select c.destination_number as number_called, count(c.destination_number) as times_called
from calls c 
join telephone_lines t
on c.source_number = t.line_number
join users u
on t.id_user = u.id
where u.id = idLoggedUser
group by number_called
order by times_called DESC
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

-- drop procedure backoffice_request_calls_user
delimiter //
create procedure backoffice_request_calls_user(IN dni int)
begin
select ca.source_number as sourceNumber , (select name from cities where id=ca.id_source_number) as sourceCity , 
ca.destination_number as destinationNumber , (select name from cities where id=ca.id_destination_number) as destinationCity , 
ca.total_price as totalPrice, ca.date_call as dateCall 
from calls ca
inner join telephone_lines t
on ca.source_number = t.line_number
inner join users u
on t.id_user = u.id
where u.dni = dni;
end //


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
create procedure backoffice_invoices_from_month(IN monthI varchar(50),IN yearI varchar(50))
begin
select t.line_number, i.date_creation, i.date_expiration, i.total_cost, i.total_price, i.paid
from telephone_lines t
inner join invoices i
on t.id = i.id_telephone_line
where month(i.date_creation) = monthI and year(i.date_creation)=yearI;
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
select truncate((sum(i.total_price)-sum(i.total_cost)),2) as income
from invoices i;
end //

-- ver ganancias de un mes

delimiter //
create procedure backoffice_check_income_month(IN monthI varchar(50), IN yearI varchar(50))
begin
select truncate((sum(i.total_price)-sum(i.total_cost)),2) as income
from invoices i
where month(i.date_creation) = monthI and year(i.date_creation)=yearI;
end //

-- ver ganancias de un año
delimiter //
create procedure backoffice_check_income_year(IN yearI varchar(50))
begin
select truncate((sum(i.total_price)-sum(i.total_cost)),2) as income
from invoices i
where year(i.date_creation) = yearI;
end //

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


-- STORED PROCEDURE ADD CALL

delimiter //
create procedure add_call_aerial(IN sourceNumber varchar(50), IN destinationNumber varchar(50), IN duration int, IN dateCall date)
begin 
insert into calls(source_number, destination_number, duration_secs, date_call) values (sourceNumber, destinationNumber, duration, dateCall);
end //
