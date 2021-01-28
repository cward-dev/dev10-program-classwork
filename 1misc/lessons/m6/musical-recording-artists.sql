drop database if exists musical_recording_artists;
create database musical_recording_artists;
use musical_recording_artists;

create table label (
	label_id int primary key auto_increment,
    name varchar(124) not null
);

create table person (
	person_id int primary key auto_increment,
    first_name varchar(124) not null,
    last_name varchar(124) not null
);

create table musician (
	musician_id int primary key auto_increment,
    stage_name varchar(124) null,
    person_id int not null,
	constraint fk_musician_person_id
		foreign key(person_id)
        references person(person_id)
);

create table writer (
	writer_id int primary key auto_increment,
    pen_name varchar(124) null,
	person_id int not null,
	constraint fk_writer_person_id
		foreign key(person_id)
        references person(person_id)
);

create table artist (
	artist_id int primary key auto_increment,
	name varchar(124) not null
);

create table album (
	album_id int primary key auto_increment,
    name varchar(124) not null,
    release_date date null,
    label_id int null,
    artist_id int not null,
    constraint fk_album_label_id
		foreign key(label_id)
        references label(label_id),
	constraint fk_album_artist_id
		foreign key(artist_id)
        references artist(artist_id)
);

create table track (
	track_id int primary key auto_increment,
    num int null,
    title varchar(124) not null,
    length time not null,
    artist_id int not null,
	album_id int null,
    constraint fk_track_artist_id
		foreign key(artist_id)
		references artist(artist_id),
    constraint fk_track_album_id
		foreign key(album_id)
        references album(album_id)
);

create table featured_artist_track (
	featured_artist_id int not null,
    track_id int not null,
    constraint pk_featured_artist_track
		primary key (featured_artist_id, track_id),
	constraint fk_featured_artist_track_featured_artist_id
		foreign key(featured_artist_id)
        references artist(artist_id),
	constraint fk_featured_artist_track_track_id
		foreign key(track_id)
        references track(track_id)
);

create table artist_musician (
	artist_id int not null,
    musician_id int not null,
    constraint pk_artist_musician
		primary key (artist_id, musician_id),
	constraint fk_artist_musician_artist_id
		foreign key(artist_id)
        references artist(artist_id),
	constraint fk_artist_musician_musician_id
		foreign key(musician_id)
        references musician(musician_id)
);

create table writer_track (
	writer_id int not null,
    track_id int not null,
    constraint pk_writer_musician
		primary key (writer_id, track_id),
	constraint fk_writer_track_artist_id
		foreign key(writer_id)
        references writer(writer_id),
	constraint fk_writer_track_track_id
		foreign key(track_id)
        references track(track_id)
);
