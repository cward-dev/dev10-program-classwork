use tiny_theaters;

select
	p.first_name as customer_first,
    p.last_name as customer_last,
    pc.email as customer_email,
    pc.phone as customer_phone,
    pc.address as customer_address,
    s.seat_name as customer_seat,
    sh.show_name as `show`,
    perf.ticket_price as ticket_price,
    perf.performance_date as `date`,
    t.theater_name,
    tc.address,
    tc.phone,
    tc.email
from reservation r
left outer join person p on r.person_id = p.person_id
left outer join contact pc on p.contact_id = pc.contact_id
left outer join seat s on r.seat_id = s.seat_id
left outer join performance perf on r.performance_id = perf.performance_id
left outer join `show` sh on perf.show_id = sh.show_id
left outer join theater t on sh.theater_id = t.theater_id
left outer join contact tc on t.contact_id = tc.contact_id;