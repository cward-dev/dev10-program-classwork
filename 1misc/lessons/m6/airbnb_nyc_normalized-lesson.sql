use airbnb_nyc_normalized;

-- insert into room_type (`name`)
-- 	select distinct room_type from temp_listing;
--     
-- select * from room_type;

-- insert into `host` (host_id, `name`)
-- 	select distinct host_id, host_name from temp_listing;
--     
-- select * from `host`;

-- insert into neighborhood_group (`name`)
-- 	select distinct neighbourhood_group from temp_listing;
--     
-- select * from neighborhood_group;

-- insert into neighborhood (neighborhood_group_id, `name`)
-- 	select distinct ng.neighborhood_group_id, l.neighbourhood
-- 	from temp_listing l
-- 	inner join neighborhood_group ng on l.neighbourhood_group = ng.`name`;

-- select * from neighborhood;

-- insert into listing
-- 	select
--         l.listing_id,
--         l.name,
--         l.host_id,
--         n.neighborhood_id,
--         l.latitude,                                        -- implicit conversion
--         l.longitude,                                       -- implicit
--         rt.room_type_id,
--         l.price,                                           -- implicit
--         l.minimum_nights,
--         l.number_of_reviews,
--         str_to_date(nullif(l.last_review,''), '%Y-%m-%d'), -- explicit
--         l.reviews_per_month,
--         l.calculated_host_listings_count,
--         l.availability_365
--     from temp_listing l
--     inner join neighborhood_group ng on l.neighbourhood_group = ng.`name`
--     inner join neighborhood n on l.neighbourhood = n.`name`
--         -- Prevents a duplicate neighborhood name in different groups.
--         and ng.neighborhood_group_id = n.neighborhood_group_id
--     inner join room_type rt on l.room_type = rt.`name`; 
--     
-- select * from listing;

select
    l.listing_id,
    l.`name`,
    l.host_id,
    h.`name` host_name,
    ng.`name` neighbourhood_group,
    n.`name` neighbourhood,
    l.latitude,
    l.longitude,
    rt.`name` room_type,
    l.price,
    l.minimum_nights,
    l.number_of_reviews,
    l.last_review,
    l.reviews_per_month,
    l.calculated_host_listings_count,
    l.availability_365
from listing l
inner join `host` h on l.host_id = h.host_id
inner join neighborhood n on l.neighborhood_id = n.neighborhood_id
inner join neighborhood_group ng on n.neighborhood_group_id = ng.neighborhood_group_id
inner join room_type rt on l.room_type_id = rt.room_type_id
order by l.listing_id;

-- drop table temp_listing;