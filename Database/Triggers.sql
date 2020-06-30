-- TRIGGER de calls
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
(NEW.duration_secs <= 0) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'Los duracion debe ser mayor que cero', mysql_errno = 1000;
END IF;

IF (NEW.date_call > now()) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'La fecha no puede ser futura', mysql_errno = 1000;
END IF;

IF (NEW.date_call < (NOW() - INTERVAL 10 DAY)) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'La fecha tiene no puede ser anterior a 10 dias', mysql_errno = 1000;
END IF;

-- Este metodo busca la ciudad por el prefijo, pero como hay prefijos repetidos entre ciudades no se si esta del todo bien
SET id_source = (SELECT c.id FROM cities c WHERE new.source_number LIKE CONCAT(c.prefix_number, '%') ORDER BY LENGTH(c.prefix_number) DESC LIMIT 1);
SET id_dest = (SELECT c.id FROM cities c WHERE new.destination_number LIKE CONCAT(c.prefix_number, '%') ORDER BY LENGTH(c.prefix_number) DESC LIMIT 1);
select IFNULL(f.price_per_minute,2), IFNULL(f.cost_per_minute,1)
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

-- crearlo despues de correr el script
DELIMITER // 
CREATE TRIGGER tbi_new_city BEFORE INSERT ON cities FOR EACH ROW
BEGIN 
IF EXISTS (select * 
FROM cities c
WHERE NEW.name = c.name) THEN SIGNAL SQLSTATE '45000' set MESSAGE_TEXT = 'La ciudad que desea ingresar ya existe', mysql_errno = 1000;
END if;
END//