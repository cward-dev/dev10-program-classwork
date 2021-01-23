use tiny_theaters;

--  1 - Find all performances in the last quarter of 2021 (Oct. 1, 2021 - Dec. 31 2021).
select
	sh.show_name,
    p.performance_date
from performance p
left outer join `show` sh on p.show_id = sh.show_id
where performance_date between '2021-10-01' and '2021-12-31';

--  2 - List customers without duplication.
select distinct 
	p.person_id,
    concat(p.first_name, ' ', p.last_name) as customer_name
from person p
left outer join reservation r on p.person_id = r.person_id;

--  3 - Find all customers without a .com email address.
select distinct 
	p.person_id,
    concat(p.first_name, ' ', p.last_name) as customer_name,
    c.email
from person p
left outer join contact c on p.contact_id = c.contact_id
left outer join reservation r on p.person_id = r.person_id
where c.email not like '%.com'; -- 32 if strict with the .com, 29 if counting variations

--  4 - Find the three cheapest shows.
select distinct
	sh.show_name,
    min(p.ticket_price) as lowest_price_performance,
    max(p.ticket_price) as highest_price_performance
from `show` sh
left outer join performance p on sh.show_id = p.show_id
group by sh.show_name
order by min(p.ticket_price) asc
limit 3;
    
--  5 - List customers and the show they're attending with no duplication.
select distinct
    concat(p.first_name, ' ', p.last_name) as customer_name,
    sh.show_name
from person p
inner join reservation r on p.person_id = r.person_id
inner join performance pe on r.performance_id = pe.performance_id
inner join `show` sh on pe.show_id = sh.show_id;

--  6 - List customer, show, theater, and seat number in one query.
select
    concat(p.first_name, ' ', p.last_name) as customer,
    sh.show_name as `show`,
    t.theater_name as theater,
    s.seat_name as seat_number
from person p
inner join reservation r on p.person_id = r.person_id
inner join seat s on r.seat_id = s.seat_id
inner join performance pe on r.performance_id = pe.performance_id
inner join `show` sh on pe.show_id = sh.show_id
inner join theater t on sh.theater_id = t.theater_id;

--  7 - Find customers without an address.
select 
    concat(p.first_name, ' ', p.last_name) as customer,
    c.address
from person p
left outer join contact c on p.contact_id = c.contact_id
where p.contact_id is null or nullif(c.address, '') is null;

--  8 - Recreate the spreadsheet data with a single query.
select
	p.first_name as customer_first,
    p.last_name as customer_last,
    pc.email as customer_email,
    pc.phone as customer_phone,
    pc.address as customer_address,
    s.seat_name as seat,
    sh.show_name as `show`,
    perf.ticket_price as ticket_price,
    perf.performance_date as `date`,
    t.theater_name as theater,
    tc.address as theater_address,
    tc.phone as theater_phone,
    tc.email as theater_email
from reservation r
left outer join person p on r.person_id = p.person_id
left outer join contact pc on p.contact_id = pc.contact_id
left outer join seat s on r.seat_id = s.seat_id
left outer join performance perf on r.performance_id = perf.performance_id
left outer join `show` sh on perf.show_id = sh.show_id
left outer join theater t on sh.theater_id = t.theater_id
left outer join contact tc on t.contact_id = tc.contact_id;

--  9 - Count total tickets purchased per customer.
select
	count(r.reservation_id) as tickets_purchased,
	concat(p.first_name, ' ', p.last_name) as customer
from person p
left outer join reservation r on p.person_id = r.person_id
group by p.person_id;

-- 10 - Calculate the total revenue per show based on tickets sold.
select
	sh.show_name,
	sum(p.ticket_price) as total_revenue
from `show` sh
left outer join performance p on sh.show_id = p.show_id
left outer join reservation r on p.performance_id = r.performance_id
group by sh.show_id;

-- 11 - Calculate the total revenue per theater based on tickets sold.
select
	t.theater_name,
	sum(p.ticket_price) as total_revenue
from `show` sh
left outer join performance p on sh.show_id = p.show_id
left outer join reservation r on p.performance_id = r.performance_id
inner join theater t on sh.theater_id = t.theater_id
group by t.theater_id;

-- 12 - Who is the biggest supporter of RCTTC? Who spent the most in 2021?
select
	concat(p.first_name, ' ', p.last_name) as customer,
    ifnull(sum(pe.ticket_price),'0.00') as total_spent
from person p
left outer join reservation r on p.person_id = r.person_id
left outer join performance pe on r.performance_id = pe.performance_id
group by p.person_id;