use musical_recording_artists;

insert into person (first_name, last_name) values
	('Beyoncé', 'Knowles'),
    ('Shawn', 'Carter'),
    ('Jack', 'White'),
    ('Joshua', 'Coleman'),
    ('Sia', 'Furler'),
    ('Brian', 'Soko'),
    ('Jerome', 'Harmon'),
    ('Andre', 'Proctor'),
    ('Rasool', 'Diaz'),
    ('Timothy', 'Mosley'),
    ('Noel', 'Fisher'),
    ('Pharrell', 'Williams'),
    ('Diana', 'Gordon'),
    ('Wayne', 'Rhoden'),
    ('Jonathan', 'Coffer'),
    ('Carla', 'Williams'),
    ('Arrow', 'Benjamin'),
    ('Kendrick', 'Duckworth'),
    ('Christopher', 'Breaux'),
    ('Kanye', 'West'),
    ('Malay', 'Ho');
    
insert into writer (pen_name, person_id) values
	('Beyoncé Knowles', (select person_id from person where first_name = 'Beyoncé' and last_name = 'Knowles')),
    ('Joshua Coleman', (select person_id from person where first_name = 'Joshua' and last_name = 'Coleman')),
    ('Sia Furler', (select person_id from person where first_name = 'Sia' and last_name = 'Furler')),
    ('Brian Soko', (select person_id from person where first_name = 'Brian' and last_name = 'Soko')),
    ('Jerome Harmon', (select person_id from person where first_name = 'Jerome' and last_name = 'Harmon')),
    ('Shawn Carter', (select person_id from person where first_name = 'Shawn' and last_name = 'Carter')),
    ('Andre Eric Proctor', (select person_id from person where first_name = 'Andre' and last_name = 'Proctor')),
    ('Rasool Diaz', (select person_id from person where first_name = 'Rasool' and last_name = 'Diaz')),
    ('Timothy Mosley', (select person_id from person where first_name = 'Timothy' and last_name = 'Mosley')),
    ('Noel Fisher', (select person_id from person where first_name = 'Noel' and last_name = 'Fisher')),
	('Pharrell Williams', (select person_id from person where first_name = 'Pharrell' and last_name = 'Williams')),
	('Christopher Breaux', (select person_id from person where first_name = 'Christopher' and last_name = 'Breaux')),
	('Jack White', (select person_id from person where first_name = 'Jack' and last_name = 'White')),
	('Diana Gordon', (select person_id from person where first_name = 'Diana' and last_name = 'Gordon')),
	('Rhoden', (select person_id from person where first_name = 'Wayne' and last_name = 'Rhoden')),
	('Jonathan Coffer', (select person_id from person where first_name = 'Jonathan' and last_name = 'Coffer')),
	('Carla Williams', (select person_id from person where first_name = 'Carla' and last_name = 'Williams')),
	('Arrow Benjamin', (select person_id from person where first_name = 'Arrow' and last_name = 'Benjamin')),
	('Kendrick Duckworth', (select person_id from person where first_name = 'Kendrick' and last_name = 'Duckworth')),
	('Kanye West', (select person_id from person where first_name = 'Kanye' and last_name = 'West')),
	('Malay Ho', (select person_id from person where first_name = 'Malay' and last_name = 'Ho'));

insert into musician (stage_name, person_id) values
	('Beyoncé', (select person_id from person where first_name = 'Beyoncé' and last_name = 'Knowles')),
    ('Jay-Z', (select person_id from person where first_name = 'Shawn' and last_name = 'Carter')),
    ('Frank Ocean', (select person_id from person where first_name = 'Christopher' and last_name = 'Breaux')),
    ('Jack White', (select person_id from person where first_name = 'Jack' and last_name = 'White')),
    ('Kendrick Lamar', (select person_id from person where first_name = 'Kendrick' and last_name = 'Duckworth'));
    
insert into artist (name) values
	('Beyoncé'),
    ('Jay-Z'),
    ('Frank Ocean'),
    ('Jack White'),
    ('Kendrick Lamar');

insert into artist_musician (artist_id, musician_id) values
	((select artist_id from artist where name = 'Beyoncé'), (select musician_id from musician where stage_name = 'Beyoncé')),
    ((select artist_id from artist where name = 'Jay-Z'), (select musician_id from musician where stage_name = 'Jay-Z')),
    ((select artist_id from artist where name = 'Frank Ocean'), (select musician_id from musician where stage_name = 'Frank Ocean')),
    ((select artist_id from artist where name = 'Jack White'), (select musician_id from musician where stage_name = 'Jack White')),
    ((select artist_id from artist where name = 'Kendrick Lamar'), (select musician_id from musician where stage_name = 'Kendrick Lamar'));
        
insert into album (name, release_date, artist_id) values
	('Beyoncé', '2013-12-13', 1),
    ('Lemonade', '2016-08-23', 1),
    ('Blonde', '2016-08-20', 3);

insert into track (title, num, length, artist_id, album_id) values
	('Pretty Hurts', 1, '4:17', (select artist_id from artist where name = 'Beyoncé'), (select album_id from album where name = 'Beyoncé')),
	('Drunk in Love', 3, '5:23', (select artist_id from artist where name = 'Beyoncé'), (select album_id from album where name = 'Beyoncé')),
	('Super-Power', 12, '4:36', (select artist_id from artist where name = 'Beyoncé'), (select album_id from album where name = 'Beyoncé')),
	('Don''t Hurt Yourself', 3, '3:53', (select artist_id from artist where name = 'Beyoncé'), (select album_id from album where name = 'Lemonade')),
	('Sorry', 4, '3:52', (select artist_id from artist where name = 'Beyoncé'), (select album_id from album where name = 'Lemonade')),
	('Freedom', 10, '5:53', (select artist_id from artist where name = 'Beyoncé'), (select album_id from album where name = 'Lemonade')),
	('Nikes', 2, '5:14', (select artist_id from artist where name = 'Frank Ocean'), (select album_id from album where name = 'Blonde')),
	('White Ferrari', 15, '4:08', (select artist_id from artist where name = 'Frank Ocean'), (select album_id from album where name = 'Blonde')),
	('Godspeed', 17, '2:57', (select artist_id from artist where name = 'Frank Ocean'), (select album_id from album where name = 'Blonde'));
    
insert into featured_artist_track (featured_artist_id, track_id) values
	((select artist_id from artist where name = 'Jay-Z'), (select track_id from track where title = 'Drunk in Love')),
   	((select artist_id from artist where name = 'Frank Ocean'), (select track_id from track where title = 'Super-Power')),
   	((select artist_id from artist where name = 'Jack White'), (select track_id from track where title = 'Don''t Hurt Yourself')),
   	((select artist_id from artist where name = 'Kendrick Lamar'), (select track_id from track where title = 'Freedom'));
    
insert into writer_track (writer_id, track_id) values
	((select writer_id from writer where pen_name = 'Beyoncé Knowles'), (select track_id from track where title = 'Pretty Hurts')),
	((select writer_id from writer where pen_name = 'Joshua Coleman'), (select track_id from track where title = 'Pretty Hurts')),
	((select writer_id from writer where pen_name = 'Sia Furler'), (select track_id from track where title = 'Pretty Hurts')),
	((select writer_id from writer where pen_name = 'Beyoncé Knowles'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Brian Soko'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Jerome Harmon'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Shawn Carter'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Andre Eric Proctor'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Rasool Díaz'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Timothy Mosley'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Noel Fisher'), (select track_id from track where title = 'Drunk in Love')),
	((select writer_id from writer where pen_name = 'Beyoncé Knowles'), (select track_id from track where title = 'Super-Power')),
	((select writer_id from writer where pen_name = 'Pharrell Williams'), (select track_id from track where title = 'Super-Power')),
	((select writer_id from writer where pen_name = 'Christopher Breaux'), (select track_id from track where title = 'Super-Power')),
	((select writer_id from writer where pen_name = 'Beyoncé Knowles'), (select track_id from track where title = 'Don''t Hurt Yourself')),
	((select writer_id from writer where pen_name = 'Jack White'), (select track_id from track where title = 'Don''t Hurt Yourself')),
	((select writer_id from writer where pen_name = 'Diana Gordon'), (select track_id from track where title = 'Don''t Hurt Yourself')),
	((select writer_id from writer where pen_name = 'Beyoncé Knowles'), (select track_id from track where title = 'Sorry')),
	((select writer_id from writer where pen_name = 'Diana Gordon'), (select track_id from track where title = 'Sorry')),
	((select writer_id from writer where pen_name = 'Rhoden'), (select track_id from track where title = 'Sorry')),
	((select writer_id from writer where pen_name = 'Beyoncé Knowles'), (select track_id from track where title = 'Freedom')),
	((select writer_id from writer where pen_name = 'Jonathan Coffer'), (select track_id from track where title = 'Freedom')),
	((select writer_id from writer where pen_name = 'Carla Williams'), (select track_id from track where title = 'Freedom')),
	((select writer_id from writer where pen_name = 'Arrow Benjamin'), (select track_id from track where title = 'Freedom')),
	((select writer_id from writer where pen_name = 'Kendrick Duckworth'), (select track_id from track where title = 'Freedom')),
	((select writer_id from writer where pen_name = 'Christopher Breaux'), (select track_id from track where title = 'Nikes')),
	((select writer_id from writer where pen_name = 'Christopher Breaux'), (select track_id from track where title = 'White Ferrari')),
	((select writer_id from writer where pen_name = 'Kanye West'), (select track_id from track where title = 'White Ferrari')),
	((select writer_id from writer where pen_name = 'Malay Ho'), (select track_id from track where title = 'White Ferrari')),
	((select writer_id from writer where pen_name = 'Christopher Breaux'), (select track_id from track where title = 'Godspeed')),
	((select writer_id from writer where pen_name = 'Malay Ho'), (select track_id from track where title = 'Godspeed'));

set sql_safe_updates = 0;

update album set
	release_date = '2016-04-23'
where name = 'Lemonade';

update track set
	title = 'Superpower'
where num = 12 and album_id = (select album_id from album where name = 'Beyoncé');

update track set
	length = '4:49'
where num = 10 and album_id = (select album_id from album where name = 'Lemonade');

update track set
	num = num - 1
where album_id = (select album_id from album where name = 'Blonde');

delete from featured_artist_track
where track_id in (select track_id from track where album_id = (select album_id from album where name = 'Beyoncé'));

delete from writer_track 
where track_id in (select track_id from track where album_id = (select album_id from album where name = 'Beyoncé'));

delete from track 
where album_id = (select album_id from album where name = 'Beyoncé');

delete from album 
where name = 'Beyoncé';
	
set sql_safe_updates = 1;

select * from track t;