use student_registrations;

select * from student;

--  Print students from Argentina.
select * from student where country = 'Argentina';

-- 	Print students whose last names starts with "T".
select * from student where last_name like 'T%';

-- 	Print students from Argentina, ordered by GPA.
select * from student where country = 'Argentina' order by gpa asc;

-- 	Print the bottom 10% (100 students) ranked by GPA.
select * from student order by gpa asc limit 100;

-- 	Print the 4th - 6th ranked students by GPA from Argentina.
select * from student where country = 'Argentina' order by gpa desc limit 3, 3;

-- 	Is anyone from Maldives?
select * from student where country = 'Maldives';

-- 	Does everyone have a non-null, non-empty email address?
select * from student where nullif(email_address, '') is null;

-- 	Print students who are currently registered for 5 courses.
select
	count(r.course_id) as count,
	concat(s.first_name, ' ', s.last_name) as student_name
from student s
inner join registration r on s.student_id = r.student_id
group by s.student_id
having count = 5;

-- 	Print students who are registered for the course "Literary Genres".
select
	c.title as course_title,
	concat(s.first_name, ' ', s.last_name) as student_name
from student s
inner join registration r on s.student_id = r.student_id
inner join course c on r.course_id = c.course_id
where c.title = 'Literary Genres';

-- 	Who has the latest birthday? Who is the youngest?
select
	first_name,
    last_name,
    birth_date
from student
order by MONTH(birth_date) desc, DAY(birth_date) desc
limit 1;

-- 	Who has the highest GPA? There may be a tie.
select
	first_name,
    last_name,
    gpa
from student
where gpa = (select max(gpa) from student);

-- 	Print every course students are registered for, including repeats.
select
	c.title,
    concat(s.first_name, ' ', s.last_name) as student_name
from course c
inner join registration r on c.course_id = r.course_id
inner join student s on r.student_id = s.student_id;

-- 	Print a distinct list of courses students are registered for.
select distinct
	c.title
from course c
inner join registration r on c.course_id = r.course_id
inner join student s on r.student_id = s.student_id;

-- 	Print a distinct list of courses students are registered for, ordered by name.
select distinct
	c.title
from course c
inner join registration r on c.course_id = r.course_id
inner join student s on r.student_id = s.student_id
order by c.title asc;

-- 	Count students per country.
select
	count(student_id),
    country
from student
group by country;

-- 	Count students per country. Order by most to fewest students.
select
	count(student_id) as count,
    country
from student
group by country
order by count desc;

-- 	Count registrations per course.
select
	count(student_id) as count,
    course_id
from registration
group by course_id
order by count desc;

-- 	How many registrations are not graded (GradeType = "Audit")?
select
	count(r.grade_type_id)
from registration r
inner join grade_type gt on r.grade_type_id = gt.grade_type_id
where gt.name = 'Audit';

-- 	Create a student summary by selecting each student's country, major, and IQ; sort and limit by IQ (your choice of low or high).

-- 	What is the average GPA per country (remember, it's random fictional data).

-- 	What is the maximum GPA per country?

-- 	Print average IQ per Major ordered by IQ ascending.

-- 	Who has the highest pointPercent in "Sacred Writing"?