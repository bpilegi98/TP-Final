-- /// INDICES ///
explain select * from cities where id_province = 1;

-- CALLS
-- user_calls_between_dates
-- La performance es 44.5 veces mas rapida 
-- Con medio millon de llamadas sin indice 1.38s CON indice 0.031s
create index idx_user_calls_between_date on calls (date_call, id_source_number) using btree;

-- backoffice_request_calls_user // backoffice_request_calls_user_simple
-- La performance pasa de 0.078 a 0.0 (pocos users)
create index idx_backoffice_request_calls_user on calls (id_source_number) using btree;

-- INVOICES
-- backoffice_invoices_between_dates
-- la performance pasa de 0.016 a 0.0 (40 invoices)
create index idx_backoffice_invoices_between_dates on invoices (date_creation) using btree;

-- user_invoices_between_dates
-- la performance pasa de 0.016 a 0.0 (40 invoices)
create index idx_user_invoices_between_dates on invoices (date_creation, id_user) using btree;

call backoffice_request_calls_user(7555);
call backoffice_request_calls_user_simple(7555);
call backoffice_invoices_between_dates("2020-06-20","2020-06-27");
call user_invoices_between_dates("2020-06-20","2020-06-27",3);

call user_calls_between_date("20-06-2020","27-06-2020",1);
call add_x_calls(500000);
select * from calls order by id DESC limit 500;

call user_invoices_between_dates;
call user_top_most_called(1);
select * from telephone_lines;
call user_calls_between_dates("2020-06-20","2020-06-27",3);

select * from users