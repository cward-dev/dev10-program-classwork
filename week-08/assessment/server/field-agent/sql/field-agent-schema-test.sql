drop database if exists field_agent_test;
create database field_agent_test;
use field_agent_test;

-- create tables and relationships
create table agency (
    agency_id int primary key auto_increment,
    short_name varchar(25) not null,
    long_name varchar(250) not null
);

create table location (
    location_id int primary key auto_increment,
    `name` varchar(25) not null,
    address varchar(125) not null,
    city varchar(50) not null,
    region varchar(25) null,
    country_code varchar(5) not null,
    postal_code varchar(15) not null,
    agency_id int not null,
    constraint fk_location_agency_id
        foreign key (agency_id)
        references agency(agency_id)
);

create table agent (
    agent_id int primary key auto_increment,
    first_name varchar(50) not null,
    middle_name varchar(50) null,
    last_name varchar(50) not null,
    dob date null,
    height_in_inches int not null
);

create table security_clearance (
    security_clearance_id int primary key auto_increment,
    `name` varchar(50) not null
);

create table mission (
    mission_id int primary key auto_increment,
    code_name varchar(50) not null,
    notes text,
    start_date date not null,
    projected_end_date date not null,
    actual_end_date date null,
    operational_cost decimal(10,2) not null,
    agency_id int not null,
    constraint fk_mission_agency_id
        foreign key(agency_id)
        references agency(agency_id)
);

create table agency_agent (
    agency_id int not null,
    agent_id int not null,
    identifier varchar(50) not null,
    security_clearance_id int not null,
    activation_date date not null,
    is_active bit not null default 1,
    constraint pk_agency_agent
        primary key (agency_id, agent_id),
    constraint fk_agency_agent_agency_id
        foreign key (agency_id)
        references agency(agency_id),
    constraint fk_agency_agent_agent_id
        foreign key (agent_id)
        references agent(agent_id),
    constraint fk_agency_agent_security_clearance_id
        foreign key (security_clearance_id)
        references security_clearance(security_clearance_id),
    constraint uq_agency_agent_identifier_agency_id
        unique (identifier, agency_id)
);

create table mission_agent (
    mission_id int not null,
    agent_id int not null,
    constraint pk_mission_agent
        primary key(mission_id, agent_id),
    constraint fk_mission_agent_mission_id
        foreign key (mission_id)
        references mission(mission_id),
    constraint fk_mission_agent_agent_id
        foreign key (agent_id)
        references agent(agent_id)
);

create table alias (
    alias_id int primary key auto_increment,
    `name` varchar(125) not null,
    persona varchar(2048) null,
    agent_id int not null,
    constraint fk_alias_agent_id
        foreign key (agent_id)
        references agent(agent_id)
);

create table logged_exception (
	logged_exception_id int primary key auto_increment,
	status_code int not null,
    original_message varchar(2048) not null,
    displayed_message varchar(2048) not null,
    exception_timestamp datetime not null
);

delimiter //
create procedure set_known_good_state()
begin

    delete from mission;
    alter table mission auto_increment = 1;
	delete from location;
    alter table location auto_increment = 1;
    delete from agency_agent;
	delete from agency;
	alter table agency auto_increment = 1;
	delete from alias;
    alter table alias auto_increment = 1;
    delete from agent;
    alter table agent auto_increment = 1;
	delete from security_clearance;
    alter table security_clearance auto_increment = 1;
    delete from logged_exception;
    alter table logged_exception auto_increment = 1;
    
    insert into agency(agency_id, short_name, long_name) values
        (1, 'ACME', 'Agency to Classify & Monitor Evildoers'),
        (2, 'MASK', 'Mobile Armored Strike Kommand'),
        (3, 'ODIN', 'Organization of Democratic Intelligence Networks');
        
	insert into location (location_id, name, address, city, region, country_code, postal_code, agency_id)
		values
	(1, 'HQ', '123 Elm', 'Des Moines', 'IA', 'USA', '55555', 1),
    (2, 'Safe House #1', 'A One Ave.', 'Walla Walla', 'WA', 'USA', '54321-1234', 1),
    (3, 'HQ', '123 Elm', 'Test', 'WI', 'USA', '55555', 2),
	(4, 'Remote', '999 Nine St.', 'Test', 'WI', 'USA', '55555', 2),
	(5, 'HQ', '123 Elm', 'Test', 'WI', 'USA', '55555', 3), -- for delete tests
	(6, 'Remote', '999 Nine St.', 'Test', 'WI', 'USA', '55555', 3);
        
	insert into agent 
		(first_name, middle_name, last_name, dob, height_in_inches) 
	values
		('Hazel','C','Sauven','1954-09-16',76),
		('Claudian','C','O''Lynn','1956-11-09',41),
		('Winn','V','Puckrin','1999-10-21',60),
		('Kiab','U','Whitham','1960-07-29',52),
		('Min','E','Dearle','1967-04-18',44),
		('Urban','H','Carwithen',null,58),
		('Ulises','B','Muhammad','2008-04-01',80),
		('Phylys','Y','Howitt','1979-03-28',68);
        
	insert into alias(`name`, persona, agent_id) values
		('Red Noodle', null, 1),
		('Watcher', null, 1),
		('007', null, 2);
        
    insert into security_clearance values
		(1, 'Secret'),
		(2, 'Top Secret'),
        (3, 'Ultra Top Secret');    
        
	insert into agency_agent 
		(agency_id, agent_id, identifier, security_clearance_id, activation_date)
    select
        agency.agency_id,                              -- agency_id
        agent.agent_id,                                -- agent_id
        concat(agency.agency_id, '-', agent.agent_id), -- identifier
        1,                                             -- security_clearance_id
        date_add(agent.dob, interval 10 year)          -- activation_date
    from agency
    inner join agent
    where agent.agent_id not in (6, 8)
    and agency.agency_id != 2;
    
	insert into mission values
		(1, 'Operation Piggy', 'Oink oink.', '1970-01-15', '1970-02-15', '1970-02-24', 150000.00, 1),
		(2, 'Jump the Gun', 'They''ll never see it coming.', '1970-01-15', '1970-02-15', '1970-02-24', 100500.50, 1),
		(3, 'Taco Tuesday', 'This may get messy.', '1970-01-15', '1970-02-15', '1970-02-24', 4506.22, 2);
    
	insert into logged_exception values
		(1, 499, 'Original Message', 'Handled Message', '1995-01-15 07:30:32'),
		(2, 499, 'Original Message', 'Handled Message', '1995-02-03 17:04:53'),
		(3, 499, 'Original Message', 'Handled Message', '1995-02-13 12:09:16');   

end //
delimiter ;

-- for manual setting of known good state
set SQL_SAFE_UPDATES = 0;
call set_known_good_state;
set SQL_SAFE_UPDATES = 1;