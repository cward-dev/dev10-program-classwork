drop database if exists tiny_theaters;
create database tiny_theaters;
use tiny_theaters;

create table person (
	person_id int primary key auto_increment,
    first_name varchar(48) not null,
    last_name varchar(48) not null
);

create table customer(
	customer_id int primary key auto_increment,
    person_id int not null,
    constraint fk_customer_person_id
		foreign key (person_id)
		references person(person_id),
    constraint uq_customer_person_id
		unique (person_id)
);

create table customer_login (
	user_name varchar(48) primary key,
    `password` varchar(24) not null,
    customer_id int not null,
    constraint fk_customer_login_customer_id
		foreign key (customer_id)
		references customer(customer_id),
	constraint uq_customer_login_customer_id
		unique (customer_id)
);

create table theater (
	theater_id int primary key auto_increment,
    theater_name varchar(48) not null
);

create table contact (
	contact_id int primary key auto_increment,
    email varchar(48) not null,
    phone char(12) null,
	address varchar(48) null,
	person_id int null,
	theater_id int null,
    constraint fk_contact_person_id
		foreign key (person_id)
		references person(person_id),
	constraint fk_contact_theater_id
		foreign key (theater_id)
		references theater(theater_id),
	constraint ck_one_fk
		check ((person_id is not null and theater_id is null) 
		    or (person_id is null and theater_id is not null)),
	constraint uq_contact_person_id
		unique (person_id),
	constraint uq_contact_theater_id
		unique (theater_id)
);

create table seat (
	seat_id int primary key auto_increment,
    seat_name varchar(24) not null,
    theater_id int not null,
    constraint fk_seat_theater_id
		foreign key (theater_id)
		references theater(theater_id),
	constraint uq_seat_seat_name_theater_id
		unique (seat_name, theater_id)
);

create table `show` (
	show_id int primary key auto_increment,
    show_name varchar(36) not null,
    show_description varchar(100) null,
--     date_start date null, -- removed, but keeping to ask James whether this would have been better practice (start/end date, how to validate no date range overlap at a theater?)
--     date_end date null,
    theater_id int null,
    constraint fk_show_theater_id
		foreign key (theater_id)
		references theater(theater_id)
--     constraint ck_show_both_dates_null_or_neither
-- 		check ((date_start is null and date_end is null) or 
-- 			   (date_start is not null and date_end is not null)),
-- 	constraint ck_show_start_date_is_not_after_end_date
-- 		check (date_start <= date_end)
);

create table performance (
	performance_id int primary key auto_increment,
    ticket_price decimal(7, 2) not null,
    performance_date date not null,
    theater_id int not null,
    show_id int not null,
    constraint fk_performance_show_id
		foreign key (show_id)
		references `show`(show_id),
	constraint fk_performance_theater_id
		foreign key (theater_id)
		references theater(theater_id),
	constraint uq_performance_performance_date_theater_id
        unique (performance_date, theater_id)
);

create table reservation (
	reservation_id int primary key auto_increment,
    customer_id int not null,
    seat_id int not null,
    performance_id int not null,
    constraint fk_reservation_customer_id
		foreign key (customer_id)
		references customer(customer_id),
    constraint fk_reservation_seat_id
		foreign key (seat_id)
		references seat(seat_id),
    constraint fk_reservation_performance_id
		foreign key (performance_id)
		references performance(performance_id),
	constraint uq_reservation_seat_id_performance_id
        unique (seat_id, performance_id)
);

create table balance (
	balance_id int primary key auto_increment,
    total_amount decimal(7, 2) not null,
    remaining_amount decimal(7, 2) not null default (total_amount),
    reservation_id int not null,
    constraint fk_balance_reservation_id
		foreign key (reservation_id)
        references reservation(reservation_id),
	constraint ck_balance_remaining_amount_not_larger_than_total_amount
		check (remaining_amount <= total_amount),
	constraint ck_balance_remaining_amount_not_negative
		check (remaining_amount >= '0.00')
);

create table payment_type (
	payment_type_id int primary key auto_increment,
    type_name varchar(24) not null
);

create table payment (
	payment_id int primary key auto_increment,
    amount_due decimal(7, 2) not null,
    amount_paid decimal(7, 2) not null,
    payment_type_id int not null,
    balance_id int not null,
	constraint fk_payment_payment_type_id
		foreign key (payment_type_id)
        references payment_type(payment_type_id),
    constraint fk_payment_balance_id
		foreign key (balance_id)
        references balance(balance_id),
	constraint ck_payment_amount_paid_not_more_than_amount_due
		check (amount_paid <= amount_due)
);

create table show_member_type (
	show_member_type_id int primary key auto_increment,
    type_name varchar(24) not null
);

create table show_member_performance (
	person_id int not null,
    performance_id int not null,
    show_member_type_id int not null,
    constraint pk_show_member_performance
		primary key (person_id, performance_id),
	constraint fk_show_member_performance_person_id
		foreign key (person_id)
        references person(person_id),
	constraint fk_show_member_performance_performance_id
		foreign key (performance_id)
        references performance(performance_id),
	constraint fk_show_member_performance_show_member_type_id
		foreign key (show_member_type_id)
        references show_member_type(show_member_type_id)
);