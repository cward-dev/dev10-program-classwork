-- Note: database structure requires everyone in the system (even cast/crew) to be listed as "customers"

use tiny_theaters;

--  1 - Find all performances in the last quarter of 2021 (Oct. 1, 2021 - Dec. 31 2021).
select
	sh.show_name,
    p.performance_date
from performance p
inner join `show` sh on p.show_id = sh.show_id
where performance_date between '2021-10-01' and '2021-12-31'
order by p.performance_date asc, sh.show_name asc;

--  2 - List customers without duplication.
select distinct 
	cu.customer_id,
    concat(p.first_name, ' ', p.last_name) as customer_name
from person p
inner join customer cu on p.person_id = cu.person_id
order by customer_id;

--  3 - Find all customers without a .com email address.
select distinct 
	cu.customer_id,
    concat(p.first_name, ' ', p.last_name) as customer_name,
    c.email
from person p
inner join customer cu on p.person_id = cu.person_id
left outer join contact c on p.person_id = c.person_id
left outer join reservation r on cu.customer_id = r.customer_id
where c.email not like '%.com'
order by customer_id asc; -- 32 if strict with the .com, 29 if counting variations ie like '%.com%'

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
inner join customer cu on p.person_id = cu.person_id
inner join reservation r on cu.customer_id = r.customer_id
inner join performance pe on r.performance_id = pe.performance_id
inner join `show` sh on pe.show_id = sh.show_id
order by customer_name asc, sh.show_name asc;

--  6 - List customer, show, theater, and seat number in one query.
select
    concat(p.first_name, ' ', p.last_name) as customer,
    sh.show_name as `show`,
    t.theater_name as theater,
    s.seat_name as seat_number
from person p
inner join customer cu on p.person_id = cu.person_id
inner join reservation r on cu.customer_id = r.customer_id
inner join seat s on r.seat_id = s.seat_id
inner join performance pe on r.performance_id = pe.performance_id
inner join `show` sh on pe.show_id = sh.show_id
inner join theater t on sh.theater_id = t.theater_id
order by theater asc, `show` asc, seat_number asc, customer asc;

--  7 - Find customers without an address.
select 
    concat(p.first_name, ' ', p.last_name) as customer_name,
    c.address
from person p
inner join customer cu on p.person_id = cu.person_id
left outer join contact c on p.person_id = c.person_id
where nullif(c.address, '') is null
order by customer_name asc;

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
inner join customer cu on r.customer_id = cu.customer_id
inner join person p on cu.person_id = p.person_id
left outer join contact pc on p.person_id = pc.person_id
left outer join seat s on r.seat_id = s.seat_id
left outer join performance perf on r.performance_id = perf.performance_id
left outer join `show` sh on perf.show_id = sh.show_id
left outer join theater t on sh.theater_id = t.theater_id
left outer join contact tc on t.theater_id = tc.theater_id;

--  9 - Count total tickets purchased per customer.
select
	count(r.reservation_id) as tickets_purchased,
	concat(p.first_name, ' ', p.last_name) as customer_name
from person p
inner join customer cu on p.person_id = cu.person_id
left outer join reservation r on cu.customer_id = r.customer_id
group by cu.customer_id
order by tickets_purchased desc, customer_name asc;

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
-- 12a - Simple (assumes one result)
select
	concat(p.first_name, ' ', p.last_name) as customer,
    sum(pe.ticket_price) as total_spent
from customer cu
inner join person p on cu.person_id = p.person_id
left outer join reservation r on cu.customer_id = r.customer_id
left outer join performance pe on r.performance_id = pe.performance_id
group by cu.customer_id
order by total_spent desc
limit 1;

-- 12b - Works if multiple results
select
	concat(p.first_name, ' ', p.last_name) as customer,
    sum(pe.ticket_price) as total_spent
from customer cu
inner join person p on cu.person_id = p.person_id
left outer join reservation r on cu.customer_id = r.customer_id
left outer join performance pe on r.performance_id = pe.performance_id
group by cu.customer_id
having total_spent = (
	select
    sum(pe.ticket_price) as total_spent
	from reservation r
	left outer join performance pe on r.performance_id = pe.performance_id
	group by r.customer_id
	order by total_spent desc
    limit 1
)
order by p.last_name asc, p.first_name asc;