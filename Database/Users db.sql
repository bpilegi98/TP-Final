-- ////////////
-- ///USERS////
-- ////////////

CREATE USER 'backoffice'@'localhost'identified by 'back123';

CREATE USER 'aerial'@'localhost' identified by 'aerial123';

create user 'customer'@'localhost' identified by 'customer123';

grant insert on tpfinal.calls to 'aerial'@'localhost';

-- /// PERMISOS AERIAL ///
grant  execute on procedure add_call_aerial to 'aerial'@'localhost';

-- /// PERMISOS BACKOFFICE ///

grant execute on procedure backoffice_request_fee_by_id to 'backoffice'@'localhost';
grant execute on procedure backoffice_request_fee to 'backoffice'@'localhost';
grant execute on procedure backoffice_request_calls_user_simple to 'backoffice'@'localhost';
grant execute on procedure backoffice_request_calls_user to 'backoffice'@'localhost';
grant execute on procedure backoffice_invoices_from_user to 'backoffice'@'localhost';
grant execute on procedure backoffice_invoices_from_user_paid to 'backoffice'@'localhost';
grant execute on procedure backoffice_invoices_from_user_not_paid to 'backoffice'@'localhost';
grant execute on procedure backoffice_invoices_from_month to 'backoffice'@'localhost';
grant execute on procedure backoffice_invoices_from_year to 'backoffice'@'localhost';
grant execute on procedure backoffice_invoices_between_dates to 'backoffice'@'localhost';
grant execute on procedure backoffice_check_income to 'backoffice'@'localhost';
grant execute on procedure backoffice_check_income_month to 'backoffice'@'localhost';
grant execute on procedure backoffice_check_income_year to 'backoffice'@'localhost';

grant insert on tpfinal.users to 'backoffice'@'localhost';
grant update on tpfinal.users to 'backoffice'@'localhost';

-- /// PERMISOS CLIENTE/API ////

grant execute on procedure user_calls_between_dates to 'customer'@'localhost';
grant execute on procedure user_invoices_between_dates to 'customer'@'localhost';
grant execute on procedure user_top_most_called to 'customer'@'localhost';
grant execute on procedure user_top_most_called_lines to 'customer'@'localhost';
