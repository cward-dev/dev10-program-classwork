use tiny_theaters;

-- select * from import_ticket_sales;

-- insert into contact (email, phone, address)
-- 	select distinct
-- 		customer_email,
-- 		customer_phone,
-- 		customer_address
-- 	from import_ticket_sales;

-- select * from contact;

-- insert into contact (email, phone, address)
--     select distinct
-- 		theater_email,
-- 		concat(SUBSTRING(theater_phone, 2, 3), '-', SUBSTRING(theater_phone, 7, 3), '-', SUBSTRING(theater_phone, 11, 4)),
-- 		theater_address
-- 	from import_ticket_sales;

-- select * from contact;

-- insert into person (first_name, last_name, contact_id)
-- 	select distinct
-- 		customer_first,
-- 		customer_last,
--         (select distinct contact_id from contact where email = customer_email)
-- 	from import_ticket_sales;
--     
-- select * from person;

-- insert into customer_login (user_name, `password`, person_id)
-- 	select distinct
-- 		customer_email,
--         'password',
--         (select distinct person_id from person where contact_id = (select distinct contact_id from contact where email = customer_email))
-- 	from import_ticket_sales;

-- select * from customer_login;

-- insert into theater (theater_name, contact_id)
-- 	select distinct
-- 		theater,
--         (select distinct contact_id from contact where email = theater_email)
-- 	from import_ticket_sales;

-- select * from theater;

-- insert into seat (seat_name, theater_id)
-- 	select distinct
-- 		seat,
-- 		(select theater_id from theater t where theater_name = theater)
-- 	from import_ticket_sales;
--     
-- select * from seat;

-- insert into `show` (show_name, theater_id)
-- 	select distinct
-- 		`show`,
--         (select theater_id from theater where theater_name = theater)
-- 	from import_ticket_sales;

-- select * from `show`;

-- set sql_safe_updates = 0;

-- update `show` set
-- 	start_date = (select min(`date`) from import_ticket_sales where `show` = show_name),
-- 	end_date = (select max(`date`) from import_ticket_sales where `show` = show_name)
-- where start_date is null and end_date is null;

-- set sql_safe_updates = 1;

-- select * from `show`;

-- insert into performance (ticket_price, performance_date, show_id)
--     select distinct
-- 		ticket_price,
--         `date`,
--         (select show_id from `show` s where `show` = s.show_name)
-- 	from import_ticket_sales;

-- select * from performance;

-- insert into reservation (person_id, seat_id, performance_id)
-- 	select
-- 		(select distinct person_id from person where first_name = customer_first and last_name = customer_last),
--         (select distinct seat_id from seat where seat_name = its.seat and theater_id = (select distinct theater_id from theater where theater_name = its.theater)),
-- 		(select distinct performance_id from performance where show_id = (select show_id from `show` where show_name = its.`show`) and performance_date = its.`date`)
-- 	from import_ticket_sales its;

-- select * from reservation;