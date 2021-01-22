use gravel_family;

-- Solve each task by writing a query below the task description.
-- Each task describes the expected result.
-- Unfortunately, tasks must be verified manually. :(

-- Example: 
-- Count the number of customer in Toronto
-- Expected: 14
select count(*)
from customer
where city = 'Toronto';

-- How many employees have the last_name 'Soyle'?
-- Expected: 12
select count(*) from employee where last_name = 'Soyle';

-- How many projects are there in the database?
-- Expected: 1121
select count(*) from project;

-- What's the earliest project start_date?
-- Expected: 2017-09-23
select min(start_date) from project;

-- What's the latest employee start_date?
-- Expected: 2020-08-25
select max(start_date) from employee;

-- Count customers per city.
-- Expected: 88 Rows
select count(*), city from customer group by city;

-- Count customers per postal_code.
-- Expected: 84 Rows
select count(*), postal_code from customer group by postal_code;

-- Count employees per last_name.
-- Expected: 3 Rows
select count(*) from employee group by last_name;

-- Count the number of projects per city.
-- Expected: 88 Rows
select count(*), c.city from project p
inner join customer c on p.customer_id = c.customer_id
group by c.city;

-- Count the number of projects per city.
-- Sort by the count descending and select the top 10 rows.
-- Expected: 10 Rows
select count(*), c.city from project p
inner join customer c on p.customer_id = c.customer_id
group by c.city
order by count(*) desc
limit 10;

-- Which postal_code has the most projects?
-- Expected: M3H
select count(*), c.postal_code from project p
inner join customer c on p.customer_id = c.customer_id
group by c.postal_code
order by count(*) desc
limit 1;

-- Count the number of projects per start_date year.
-- Expected: 4 Rows
select count(*), year(start_date) from project
group by year(start_date);

-- Count the number of employees per project in the M3H postal_code.
-- Group by project_id, sort by count descending.
-- Expected: 39 Rows
select count(*), p.project_id from employee e
left outer join project_employee pe on e.employee_id = pe.employee_id
left outer join project p on pe.project_id = p.project_id
left outer join customer c on p.customer_id = c.customer_id
where c.postal_code = 'M3H'
group by p.project_id
order by count(*) desc, project_id asc;

-- Calculate the total cost per project in the 'M3H' postal_code.
-- (Hint: sum a calculation)
-- Expected: 39 Rows
select
	sum(pi.quantity * i.price_per_unit) as total_cost,
    p.project_id,
    p.description
from project p
left outer join customer c on p.customer_id = c.customer_id
left outer join project_item pi on p.project_id = pi.project_id
left outer join item i on pi.item_id = i.item_id
where c.postal_code = 'M3H'
group by p.project_id;

-- What's the most expensive project in the 'M3H' postal_code?
-- Expected: 18828.00
select
	sum(pi.quantity * i.price_per_unit) as total_cost
from project p
left outer join customer c on p.customer_id = c.customer_id
left outer join project_item pi on p.project_id = pi.project_id
left outer join item i on pi.item_id = i.item_id
where c.postal_code = 'M3H'
group by p.project_id
order by total_cost desc
limit 1;


-- How many projects did each employee work on?
-- Expected: 33 Rows
select
	count(p.project_id),
    e.last_name,
    e.first_name
from employee e
left outer join project_employee pe on e.employee_id = pe.employee_id
left outer join project p on pe.project_id = p.project_id
group by e.employee_id;

-- How many employees worked on more than 140 projects?
-- Expected: 10 Rows
select
	e.employee_id,
    count(p.project_id) as num_of_projects
from employee e
left outer join project_employee pe on e.employee_id = pe.employee_id
left outer join project p on pe.project_id = p.project_id
group by e.employee_id
having num_of_projects > 140;


-- How many projects cost more than $20,000?
-- Expected: 55 Rows
select
	p.project_id,
    sum(pi.quantity * i.price_per_unit) as total_cost
from project p
left outer join project_item pi on p.project_id = pi.project_id
left outer join item i on pi.item_id = i.item_id
group by p.project_id
having total_cost > 20000;

-- Across all projects, what are the total costs per item?
-- Select the item name and sum.
-- Sort by the sum desc;
-- Expected: 18 Rows
select
	i.name as item_name,
	sum(pi.quantity * i.price_per_unit) as total_cost
from item i
left outer join project_item pi on i.item_id = pi.item_id
left outer join project p on pi.project_id = p.project_id
group by item_name
having total_cost is not null
order by total_cost desc;

-- Across all projects, what are the total costs per item category?
-- Select the category name and sum.
-- Sort by the sum desc;
-- Expected: 7 Rows
select
	c.name as category_name,
	sum(pi.quantity * i.price_per_unit) as total_cost
from category c
left outer join item i on c.category_id = i.category_id
left outer join project_item pi on i.item_id = pi.item_id
left outer join project p on pi.project_id = p.project_id
group by category_name
having total_cost is not null
order by total_cost desc;

-- What's the average 'Standard Labor' cost per city?
-- Expected: 88 Rows
select
	c.city,
	avg(pi.quantity * i.price_per_unit) as avg_cost
from category ca
left outer join item i on ca.category_id = i.category_id
left outer join project_item pi on i.item_id = pi.item_id
left outer join project p on pi.project_id = p.project_id
left outer join customer c on p.customer_id = c.customer_id
group by c.city
having avg_cost is not null
order by avg_cost desc;

-- Challenge: Which customer has the first project of 2019?
-- (Requires a subquery.)
-- Expected: Starkie 2019-01-01
select
	c.last_name,
    p.start_date
from customer c
inner join project p on c.customer_id = p.customer_id
where year(p.start_date) = 2019
order by p.start_date asc
limit 1;