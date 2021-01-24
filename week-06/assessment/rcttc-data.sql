use tiny_theaters;

select * from import_ticket_sales;

insert into person (first_name, last_name)
	select distinct
		customer_first,
		customer_last
	from import_ticket_sales;

select * from person;

insert into contact (email, phone, address, person_id)
	select distinct
		its.customer_email,
		its.customer_phone,
		its.customer_address,
        p.person_id
	from import_ticket_sales its
    left outer join person p on concat(its.customer_first, ' ', its.customer_last) = concat(p.first_name, ' ', p.last_name);

select * from contact where person_id is not null;

insert into customer (person_id)
	select person_id from person;

select * from customer cu inner join person p on cu.person_id = p.person_id;

insert into theater (theater_name)
	select distinct
		theater
	from import_ticket_sales;

select * from theater;

insert into contact (email, phone, address, theater_id)
    select distinct
		its.theater_email,
		concat(SUBSTRING(its.theater_phone, 2, 3), '-', SUBSTRING(its.theater_phone, 7, 3), '-', SUBSTRING(its.theater_phone, 11, 4)),
		its.theater_address,
        t.theater_id
	from import_ticket_sales its
    left outer join theater t on its.theater = t.theater_name;

select * from contact where theater_id is not null;

insert into seat (seat_name, theater_id)
	select distinct
		seat,
		(select theater_id from theater t where theater_name = theater)
	from import_ticket_sales;

select * from seat;

insert into `show` (show_name, theater_id)
	select distinct
		`show`,
        (select theater_id from theater where theater_name = theater)
	from import_ticket_sales;

select * from `show`;

-- set sql_safe_updates = 0;

-- update `show` set -- removed, but keeping to ask James whether this would have been better practice (start/end date)
-- 	start_date = (select min(`date`) from import_ticket_sales where `show` = show_name),
-- 	end_date = (select max(`date`) from import_ticket_sales where `show` = show_name)
-- where start_date is null and end_date is null;

-- set sql_safe_updates = 1;

-- select * from `show`;

insert into performance (ticket_price, performance_date, theater_id, show_id)
    select distinct
		ticket_price,
        `date`,
        (select theater_id from `show` sh where `show` = sh.show_name),
        (select show_id from `show` sh where `show` = sh.show_name)
	from import_ticket_sales;

select * from performance;

insert into reservation (customer_id, seat_id, performance_id)
	select
		(select distinct
				cu.customer_id 
                from customer cu
                inner join person p on cu.person_id = p.person_id 
                where p.first_name = its.customer_first and p.last_name = its.customer_last),
        (select distinct seat_id from seat where seat_name = its.seat and theater_id = (select distinct theater_id from theater where theater_name = its.theater)),
		(select distinct performance_id from performance where show_id = (select show_id from `show` where show_name = its.`show`) and performance_date = its.`date`)
	from import_ticket_sales its;

select * from reservation;

drop table import_ticket_sales;

select distinct performance_id from performance where 
	show_id = (select show_id from `show` where show_name = 'The Sky Lit Up') and
    performance_date = '2021-03-01' and
    ticket_price = '20.00';

update performance set
	ticket_price = '22.25'
	where performance_id = '5';

select * from performance;

select distinct
	cu.customer_id,
	concat(p.first_name, ' ', p.last_name) as customer_name,
	r.reservation_id,
    s.seat_id,
    s.seat_name,
    sh.show_name,
    pe.performance_id,
    s.theater_id
from reservation r
left outer join customer cu on r.customer_id = cu.customer_id
left outer join person p on cu.person_id = p.person_id
left outer join seat s on r.seat_id = s.seat_id
left outer join performance pe on r.performance_id = pe.performance_id
left outer join `show` sh on pe.show_id = sh.show_id
where r.performance_id = 
	(select distinct performance_id from performance where 
		show_id = (select show_id from `show` where show_name = 'The Sky Lit Up') and
		performance_date = '2021-03-01');

insert into seat values
	('0', 'temp_seat_admin', '2');
    
update reservation set seat_id = (select seat_id from seat where seat_name = 'temp_seat_admin' limit 1) where reservation_id = 91;
update reservation set seat_id = 29 where reservation_id = 97;
update reservation set seat_id = 35 where reservation_id = 95;
update reservation set seat_id = 33 where reservation_id = 91;

set sql_safe_updates = 0;
delete from seat where seat_name = 'temp_seat_admin';
set sql_safe_updates = 1;

select
	p.person_id,
	concat(p.first_name, ' ', p.last_name) as full_name,
	c.phone
from person p
left outer join contact c on p.person_id = c.person_id
where concat(p.first_name, ' ', p.last_name) = 'Jammie Swindles';

update contact set
	phone = '801-EAT-CAKE'
where person_id = '48';

select
	count(reservation_id),
    r.customer_id,
    p.performance_id
from reservation r
left outer join performance p on r.performance_id = p.performance_id
left outer join `show` s on p.show_id = s.show_id
left outer join theater t on s.theater_id = t.theater_id
where t.theater_name = '10 Pin'
group by r.customer_id, p.performance_id;

delete from reservation where customer_id = '7' and performance_id = '1';
delete from reservation where customer_id = '8' and performance_id = '2';
delete from reservation where customer_id = '10' and performance_id = '2';
delete from reservation where customer_id = '15' and performance_id = '2';
delete from reservation where customer_id = '18' and performance_id = '3';
delete from reservation where customer_id = '19' and performance_id = '3';
delete from reservation where customer_id = '22' and performance_id = '3';
delete from reservation where customer_id = '25' and performance_id = '3';
delete from reservation where customer_id = '26' and performance_id = '4';

select
	p.person_id,
	cu.customer_id,
    c.contact_id,
    cl.user_name,
    r.reservation_id
from person p
inner join customer cu on p.person_id = cu.customer_id
inner join customer_login cl on cu.customer_id = cl.customer_id
inner join contact c on p.person_id = c.person_id
inner join reservation r on cu.customer_id = r.customer_id
where concat(first_name, ' ', last_name) = 'Liv Egle of Germany';

delete from reservation where customer_id = (select customer_id from customer where person_id = '65');
delete from customer_login where customer_id = (select customer_id from customer where person_id = '65');
delete from customer where person_id = '65';
delete from show_member_performance where person_id = '65';
delete from contact where person_id = '65'; -- not sure if it was worth keeping contact info together for theaters and people, a little clunky in this regard (contact is the last removed)
delete from person where person_id = '65';

-- Other Stretch Goals

-- login, so customers can make and check reservations online
insert into customer_login (user_name, `password`, customer_id)
	select distinct
		concat(
			LOWER(SUBSTRING(p.first_name,1,1)), 
            LOWER(SUBSTRING(p.last_name,1,1)), 
            cu.customer_id),
        'password',
        cu.customer_id
	from customer cu
    inner join person p on cu.person_id = p.person_id;

select * from customer_login;

-- cast and crew for each performance
insert into person (first_name, last_name)
	values
    ('Jimmy', 'Johnson'),
    ('Helga', 'Havarti'),
    ('Ronald', 'Reggae'),
    ('Leslie', 'Lawter'),
    ('Michael', 'Marston'),
    ('Amy', 'Almers'),
    ('Jeffrey', 'Jeffton'),
    ('Zachary', 'Zane'),
    ('Penny', 'Polish'),
    ('Tina', 'Tarsley');
    
select * from `show`;

select * from person p left outer join customer cu on p.person_id = cu.person_id where customer_id is null;

insert into show_member_type (type_name)
	values
    ('cast'),
    ('crew');

select * from show_member_type;

insert into show_member_performance (person_id, performance_id, show_member_type_id)
	values
    ('128', '1', '1'),
	('129', '1', '1'),
    ('130', '1', '2'),
    ('131', '1', '2'),
    ('132', '2', '1'),
    ('133', '2', '1'),
    ('134', '2', '2'),
    ('135', '3', '1'),
    ('136', '3', '1'),
	('137', '3', '2');

select * from show_member_performance;

select
	p.person_id,
	concat(p.first_name, ' ', p.last_name) as person_name,
    smt.type_name as `role`,
    sh.show_name,
    t.theater_name,
    pe.performance_date
from person p
inner join show_member_performance smp on p.person_id = smp.person_id
inner join show_member_type smt on smp.show_member_type_id = smt.show_member_type_id
inner join performance pe on smp.performance_id = pe.performance_id
inner join `show` sh on pe.show_id = sh.show_id
inner join theater t on sh.theater_id = t.theater_id;

-- payments, payment types
insert into payment_type (type_name)
	values
    ('Cash'),
	('Check'),
    ('Credit'),
    ('Debit');
    
select * from payment_type;

insert into balance (total_amount, reservation_id)
	select
		pe.ticket_price,
        r.reservation_id
	from reservation r
    inner join performance pe on r.performance_id = pe.performance_id;
    
select * from balance;

insert into payment (amount_due, amount_paid, balance_id, payment_type_id)
	select
		b.remaining_amount,
        b.remaining_amount,
        b.balance_id,
        (Select payment_type_id from payment_type pt where type_name = 'Credit')
	from balance b
    inner join reservation r on b.reservation_id = r.reservation_id;

select * from payment;

delete from payment where balance_id = '1';

insert into payment (amount_due, amount_paid, balance_id, payment_type_id)
	values
    ((select remaining_amount from balance where balance_id = '1'), '10.00', '1', '3');

set sql_safe_updates = 0;
update balance b set
	remaining_amount = ifnull(total_amount - (select sum(amount_paid) from payment p where p.balance_id = b.balance_id), b.total_amount);
set sql_safe_updates = 1;    
    
select
	r.reservation_id,
    b.balance_id,
    b.total_amount,
    b.remaining_amount
from reservation r
inner join balance b on r.reservation_id = b.reservation_id
order by b.remaining_amount desc, r.reservation_id asc;