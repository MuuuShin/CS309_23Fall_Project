drop table if exists public.teachers;

drop table if exists public.comments;

drop table if exists public.students;

drop table if exists public.group_stars;

drop table if exists public.groups;

drop table if exists public.rooms;

drop table if exists public.floors;

drop table if exists public.buildings;

drop table if exists public.regions;

drop table if exists public.timelines;

drop table if exists public.msgs;

drop table if exists public.passwords;

--drop function if exists public.insert_room_trigger();

drop function if exists public.insert_room_data(name_list text[], type_list integer[], intro_list text[], floor_name_list text[], building_name_list text[], region_name_list text[]);

drop sequence if exists public.your_sequence_name;