use solar_panels;

insert into solar_panel (panel_id, section, `row`, `column`, year_installed, material, is_tracking)
	values
    (1,'Bluegrass',3,15,1994,'MULTICRYSTALLINE_SILICON',1),
	(2,'Jazz',54,22,1983,'AMORPHOUS_SILICON',0),
	(3,'Gospel',13,15,2005,'COPPER_INDIUM_GALLIUM_SELENIDE',1),
	(4,'Jazz',54,10,2016,'AMORPHOUS_SILICON',0)
    


	select
		i.panelId,
        i.section,
        i.`row`,
        i.`column`,
        i.yearInstalled,
        i.material,
        case when i.isTracking = 'true' then 1 else 0 end
	from import_solar_panel i;
    
select * from solar_panel;