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
    tracking bit not null
);