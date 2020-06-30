SET GLOBAL event_scheduler = ON;
SET GLOBAL time_zone = '-3:00';
-- drop database tpfinal;
create database tpfinal;
use tpfinal;

-- TABLAS

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
password varchar(500),
user_type enum('AERIAL', 'EMPLOYEE', 'CUSTOMER'),
is_active boolean default true,
id_city int,
constraint fk_city_user foreign key (id_city) references cities(id)
);

create table fees(
id int auto_increment primary key,
price_per_minute float default 0,
cost_per_minute float default 0,
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
price_per_minute float default 0,
duration_secs int default 0,
total_cost float default 0,
total_price float default 0,
source_number varchar(50),
id_source_number int,
destination_number varchar(50),
id_destination_number int,
date_call timestamp,
id_source_city int,
id_destination_city int,
id_invoice int,
constraint fk_id_source_number_call foreign key (id_source_number) references telephone_lines(id),
constraint fk_id_destination_number_call foreign key (id_destination_number) references telephone_lines(id),
constraint fk_id_source_city_call foreign key (id_source_city) references cities(id),
constraint fk_id_destination_city_call foreign key (id_destination_city) references cities(id),
constraint fk_id_invoice_call foreign key (id_invoice) references invoices(id) 
);
