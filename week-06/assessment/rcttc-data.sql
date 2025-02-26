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
		nullif(its.customer_phone, ''),
		nullif(its.customer_address, ''),
        p.person_id
	from import_ticket_sales its
    left outer join person p on concat(its.customer_first, ' ', its.customer_last) = concat(p.first_name, ' ', p.last_name);

select * from contact where person_id is not null;

insert into customer (person_id)
	select person_id from person;

select * from customer cu inner join person p on cu.person_id = p.person_id;

insert into theater (theater_name, theater_description)
	values
    ('Little Fitz', 'Previously a storage room in the historic Fitzgerald Theater.'),
    ('10 Pin', 'A cozy little spot tucked behind the lanes of a hipster bowling alley. It''s a little noisy, but the atmosphere is one-of-a-kind.'),
    ('Horizon', 'A rain-or-shine, summer-to-winter backyard theater hosted by the Thao family.');

select * from theater;

insert into contact (email, phone, address, theater_id)
    select distinct
		its.theater_email,
		nullif(concat(SUBSTRING(its.theater_phone, 2, 3), '-', SUBSTRING(its.theater_phone, 7, 3), '-', SUBSTRING(its.theater_phone, 11, 4)), ''),
		nullif(its.theater_address, ''),
        t.theater_id
	from import_ticket_sales its
    left outer join theater t on its.theater = t.theater_name;

select * from contact where theater_id is not null;

insert into seat (seat_name, theater_id)
	values
    ('A1', 1), ('A2', 1), ('A3', 1), ('A4', 1),
    ('B1', 1), ('B2', 1), ('B3', 1), ('B4', 1),
    ('C1', 1), ('C2', 1), ('C3', 1), ('C4', 1),
    ('A1', 2), ('A2', 2), ('A3', 2), ('A4', 2), ('A5', 2),
    ('B1', 2), ('B2', 2), ('B3', 2), ('B4', 2), ('B5', 2),
    ('C1', 2), ('C2', 2), ('C3', 2), ('C4', 2), ('C5', 2),
    ('D1', 2), ('D2', 2), ('D3', 2), ('D4', 2), ('D5', 2),
    ('E1', 2), ('E2', 2), ('E3', 2), ('E4', 2), ('E5', 2),
    ('A1', 3), ('A2', 3), ('A3', 3), ('A4', 3), ('A5', 3), ('A6', 3), ('A7', 3), ('A8', 3),
    ('B1', 3), ('B2', 3), ('B3', 3), ('B4', 3), ('B5', 3), ('B6', 3), ('B7', 3), ('B8', 3);

select * from seat;

insert into `show` (show_name, show_description, theater_id)
	values
    ('High School Musical', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'High School Musical'))),
    ('Hair', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Hair'))),
    ('Dance Dance Vertical', 'Dance performed on a vertical surface using climbing equipment', (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Dance Dance Vertical'))),
    ('Caddyshack', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Caddyshack'))),
    ('Burr', 'The real dirt on Alexander Hamilton', (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Burr'))),
    ('Send in the Clowns', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Send in the Clowns'))),
    ('The Dress', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'The Dress'))),
    ('Tell Me What To Think', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Tell Me What To Think'))),
    ('The Sky Lit Up', 'Cosmic horror', (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'The Sky Lit Up'))),
    ('Ocean', 'The life of Frank Ocean as performed by Frank Ocean', (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Ocean'))),
    ('Stop. Just Stop.', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Stop. Just Stop.'))),
    ('Wen', null, (select distinct theater_id from theater where theater_name = (select distinct its.theater from import_ticket_sales its where its.`show` = 'Wen')));

select * from `show`;

set sql_safe_updates = 0;

update `show` set
	start_date = (select min(`date`) from import_ticket_sales where `show` = show_name),
	end_date = (select max(`date`) from import_ticket_sales where `show` = show_name)
where start_date is null and end_date is null;

set sql_safe_updates = 1;

select * from `show`;

insert into performance (ticket_price, performance_date, show_id)
    select distinct
		ticket_price,
        `date`,
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
    ticket_price = 20.00;

update performance set
	ticket_price = 22.25
	where performance_id = 5;

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
inner join customer cu on r.customer_id = cu.customer_id
inner join person p on cu.person_id = p.person_id
inner join seat s on r.seat_id = s.seat_id
inner join performance pe on r.performance_id = pe.performance_id
inner join `show` sh on pe.show_id = sh.show_id
where r.performance_id = 
	(select distinct performance_id from performance where 
		show_id = (select show_id from `show` where show_name = 'The Sky Lit Up') and
		performance_date = '2021-03-01');

insert into seat values
	(0, 'temp_seat_admin', 2);
    
update reservation set seat_id = (select seat_id from seat where seat_name = 'temp_seat_admin' limit 1) where reservation_id = 91;
update reservation set seat_id = (select seat_id from seat where seat_name = 'A4' and theater_id = 1) where reservation_id = 97;
update reservation set seat_id = (select seat_id from seat where seat_name = 'C2' and theater_id = 1) where reservation_id = 95;
update reservation set seat_id = (select seat_id from seat where seat_name = 'B4' and theater_id = 1) where reservation_id = 91;

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
where person_id = 48;

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

delete from reservation where customer_id = 7 and performance_id = 1;
delete from reservation where customer_id = 8 and performance_id = 2;
delete from reservation where customer_id = 10 and performance_id = 2;
delete from reservation where customer_id = 15 and performance_id = 2;
delete from reservation where customer_id = 18 and performance_id = 3;
delete from reservation where customer_id = 19 and performance_id = 3;
delete from reservation where customer_id = 22 and performance_id = 3;
delete from reservation where customer_id = 25 and performance_id = 3;
delete from reservation where customer_id = 26 and performance_id = 4;

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

delete from reservation where customer_id = (select customer_id from customer where person_id = 65);
delete from customer_login where customer_id = (select customer_id from customer where person_id = 65);
delete from customer where person_id = 65;
delete from show_member_performance where person_id = 65;
delete from contact where person_id = 65; -- not sure if it was worth keeping contact info together for theaters and people, a little clunky in this regard (contact is the last removed)
delete from person where person_id = 65;

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

select -- check results
	concat(p.first_name, ' ', p.last_name) as customer_name,
    cl.user_name,
    cl.`password`
from customer_login cl
inner join customer cu on cl.customer_id = cu.customer_id
inner join person p on cu.person_id = p.person_id;

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
    (128, 1, 1),
	(129, 1, 1),
    (130, 1, 2),
    (131, 1, 2),
    (132, 2, 1),
    (133, 2, 1),
    (134, 2, 2),
    (135, 3, 1),
    (136, 3, 1),
	(137, 3, 2);

select * from show_member_performance;

select -- check results
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

delete from payment where balance_id = 1;

set sql_safe_updates = 0;
update balance b set
	remaining_amount = ifnull(total_amount - (select sum(amount_paid) from payment p where p.balance_id = b.balance_id), b.total_amount);
set sql_safe_updates = 1;    

insert into payment (amount_due, amount_paid, balance_id, payment_type_id) values 
    ((select remaining_amount from balance where balance_id = 1), 10.00, 1, 3);

set sql_safe_updates = 0;
update balance b set
	remaining_amount = ifnull(total_amount - (select sum(amount_paid) from payment p where p.balance_id = b.balance_id), b.total_amount);
set sql_safe_updates = 1;    
    
select -- check results
	r.reservation_id,
    b.balance_id,
    b.total_amount,
    b.remaining_amount
from reservation r
inner join balance b on r.reservation_id = b.reservation_id
order by b.remaining_amount desc, r.reservation_id asc;