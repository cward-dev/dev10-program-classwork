drop database if exists solar_panels_test;
create database solar_panels_test;
use solar_panels_test;

create table solar_panel (
	panel_id int primary key auto_increment,
    section varchar(48) not null,
    `row` int not null,
    `column` int not null,
    year_installed int not null,
    material varchar(48) not null,
    is_tracking bit not null
);

delimiter //
create procedure set_known_good_state()
begin
    truncate table solar_panel;
	insert into solar_panel (section, `row`, `column`, year_installed, material, is_tracking)
		values
		('Bluegrass',3,15,1994,'MULTICRYSTALLINE_SILICON',1),
		('Jazz',54,22,1983,'AMORPHOUS_SILICON',0),
		('Gospel',13,15,2005,'COPPER_INDIUM_GALLIUM_SELENIDE',1),
		('Jazz',54,10,2016,'AMORPHOUS_SILICON',0);
end //
delimiter ;
    
select * from solar_panel;