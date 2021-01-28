drop database if exists pets_test;
create database pets_test;
use pets_test;

create table pet (
    pet_id int primary key auto_increment,
    `name` varchar(50) not null,
    `type` varchar(50) null
);

-- 1. MySQL's default statement terminator is `;`.
-- Since we include `;` inside our procedure, we temporarily change
-- the statement terminator to `//`.
-- That way, the SQL inside is treated as text.
delimiter //
create procedure set_known_good_state()
begin
    -- 2. Throws out all records without executing deletes.
    -- Resets the auto_increment value.
    truncate table pet;

    -- 3. Add test data.
    insert into pet
        (`name`, `type`)
    values
        ('Meep','Mouse'),
        ('Slithpers','Snake'),
        ('Noodles','Dog');
end //
-- 4. Change the statement terminator back to the original.
delimiter ;

call set_known_good_state();
select * from pet;