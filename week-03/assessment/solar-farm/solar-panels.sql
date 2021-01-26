drop database if exists solar_panels;
create database solar_panels;
use solar_panels;

create table solar_panel (
	panel_id int primary key auto_increment,
    section varchar(48) not null,
    `row` int not null,
    `column` int not null,
    year_installed int not null,
    material varchar(48) not null,
    is_tracking bit not null
);

insert into solar_panel (section, `row`, `column`, year_installed, material, is_tracking)
	values
    ('Bluegrass',2,13,1994,'AMORPHOUS_SILICON',1),
    ('Jazz',54,22,1983,'AMORPHOUS_SILICON',0),
	('Bluegrass',54,10,2005,'COPPER_INDIUM_GALLIUM_SELENIDE',1),
	('Jazz',54,10,2016,'AMORPHOUS_SILICON',1),
	('Ska',1,1,2020,'MONOCRYSTALLINE_SILICON',1),
	('Soul',45,23,2021,'MULTICRYSTALLINE_SILICON',0),
	('Jazz',32,43,2021,'CADMIUM_TELLURIDE',1),
	('Jazz',43,23,2017,'COPPER_INDIUM_GALLIUM_SELENIDE',1),
	('Ska',23,15,2005,'MONOCRYSTALLINE_SILICON',0),
	('Funk',213,64,1954,'COPPER_INDIUM_GALLIUM_SELENIDE',0),
	('Ska',3,3,1999,'MONOCRYSTALLINE_SILICON',0);
    
select * from solar_panel;