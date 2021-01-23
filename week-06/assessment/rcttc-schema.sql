drop database if exists tiny_theaters;
create database tiny_theaters;
use tiny_theaters;

create table contact (
	contact_id int primary key auto_increment,
    email varchar(48) not null,
    phone char(12) null,
	address varchar(48) null
);

create table person (
	person_id int primary key auto_increment,
    first_name varchar(48) not null,
    last_name varchar(48) not null,
    contact_id int null,
    constraint fk_person_contact_id
		foreign key (contact_id)
		references contact(contact_id)
);

create table customer_login (
	user_name varchar(48) primary key,
    `password` varchar(24) not null,
    person_id int not null,
    constraint fk_customer_login_person_id
		foreign key (person_id)
		references person(person_id)
);

create table theater (
	theater_id int primary key auto_increment,
    theater_name varchar(48) not null,
    contact_id int not null,
    constraint fk_theater_contact_id
		foreign key (contact_id)
		references contact(contact_id)
);

create table seat (
	seat_id int primary key auto_increment,
    seat_name varchar(24) not null,
    theater_id int not null,
    constraint fk_seat_theater_id
		foreign key (theater_id)
		references theater(theater_id)
);

create table `show` (
	show_id int primary key auto_increment,
    show_name varchar(36) not null,
    start_date date null,
    end_date date null,
    theater_id int not null,
    constraint fk_show_theater_id
		foreign key (theater_id)
		references theater(theater_id)
);

create table performance (
	performance_id int primary key auto_increment,
    ticket_price decimal(7,2) not null,
    performance_date date not null,
    show_id int not null,
    constraint fk_performance_show_id
		foreign key (show_id)
		references `show`(show_id),
	constraint uq_performance_performance_date_show_id
        unique (performance_date, show_id)
);

create table reservation (
	reservation_id int primary key auto_increment,
    person_id int not null,
    seat_id int not null,
    performance_id int not null,
    constraint fk_reservation_person_id
		foreign key (person_id)
		references person(person_id),
    constraint fk_reservation_seat_id
		foreign key (seat_id)
		references seat(seat_id),
    constraint fk_reservation_performance_id
		foreign key (performance_id)
		references performance(performance_id),
	constraint uq_reservation_seat_id_performance_id
        unique (seat_id, performance_id)
);

create table show_member_type (
	show_member_type_id int primary key auto_increment,
    type_name varchar(24) not null
);

create table show_member_show (
	person_id int not null,
    show_id int not null,
    show_member_type_id int not null,
    constraint pk_show_member_show
		primary key (person_id, show_id),
	constraint fk_show_member_show_person_id
		foreign key (person_id)
        references person(person_id),
	constraint fk_show_member_show_show_id
		foreign key (show_id)
        references `show`(show_id),
	constraint fk_show_member_show_show_member_type_id
		foreign key (show_member_type_id)
        references show_member_type(show_member_type_id)
);