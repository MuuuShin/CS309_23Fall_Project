create table if not exists public.regions
(
    region_id bigint generated always as identity
        primary key,
    name      varchar(255),
    intro     varchar(255)
);

-- alter table public.regions
--     owner to postgres;

create table if not exists public.buildings
(
    building_id bigint generated always as identity
        primary key,
    name        varchar(255),
    intro       varchar(255),
    region_id   bigint
        constraint buildings_regions_null_fk
            references public.regions
);

-- alter table public.buildings
--     owner to postgres;

create table if not exists public.floors
(
    floor_id    bigint generated always as identity
        primary key,
    name        varchar(255),
    intro       varchar(255),
    building_id bigint
        constraint floors_buildings_null_fk
            references public.buildings
);

-- alter table public.floors
--     owner to postgres;

create table if not exists public.rooms
(
    room_id         bigint generated always as identity
        constraint room_pkey
            primary key,
    name            varchar(255),
    type            integer,
    intro           varchar(255),
    status          integer,
    floor_id        bigint
        constraint rooms_floors_null_fk
            references public.floors,
    comment_base_id bigint,
    img_url         varchar(255)
);

-- alter table public.rooms
--     owner to postgres;

create table if not exists public.teachers
(
    teacher_id bigint generated always as identity (start with 100000001)
        primary key,
    name       varchar(255),
    permission varchar(255),
    account    varchar(255)
);

-- alter table public.teachers
--     owner to postgres;

create table if not exists public.comments
(
    comment_id    bigint generated always as identity
        constraint commets_pkey
            primary key,
    title         varchar(255),
    body          varchar(255),
    user_id       bigint,
    post_id       bigint,
    creation_date timestamp(6),
    disabled      boolean
);

-- alter table public.comments
--     owner to postgres;

create table if not exists public.groups
(
    group_id bigint generated always as identity
        primary key,
    name     varchar(255),
    leader   bigint,
    intro    varchar(255),
    room_id  bigint
        constraint groups_rooms_null_fk
            references public.rooms
            ON DELETE SET NULL
);

-- alter table public.groups
--     owner to postgres;

create table if not exists public.students
(
    student_id bigint generated by default as identity (start with 200000001)
        primary key,
    name       varchar(255),
    intro      varchar(255),
    gender     smallint,
    group_id   bigint
        constraint students_groups_null_fk
            references public.groups
            ON DELETE SET NULL,
    type       integer,
    awake_time time(6),
    sleep_time time(6),
    account    varchar(255),
    img_url    varchar(255)
);

-- alter table public.students
--     owner to postgres;

create table if not exists public.group_stars
(
    group_id bigint not null
        constraint group_likes_groups_null_fk
            references public.groups,
    room_id  bigint not null
        constraint group_likes_rooms_null_fk
            references public.rooms,
    constraint group_likes_pkey
        primary key (group_id, room_id)
);

-- alter table public.group_stars
--     owner to postgres;

create table if not exists public.timelines
(
    timeline_id bigint generated always as identity
        primary key,
    type        integer,
    begin_time1 timestamp(0),
    end_time1   timestamp(0),
    begin_time2 timestamp(0),
    end_time2   timestamp(0),
    begin_time3 timestamp(0),
    end_time3   timestamp(0),
    begin_time4 timestamp(0),
    end_time4   timestamp(0)
);

-- alter table public.timelines
--     owner to postgres;

create table if not exists public.msgs
(
    msg_id    bigint generated always as identity
        primary key,
    type    integer,
    src_id    bigint,
    dst_id    bigint,
    body      varchar(255),
    timestamp timestamp,
    status    integer
);

-- alter table public.msgs
--     owner to postgres;

create table if not exists public.passwords
(
    account  varchar(255) not null
        primary key,
    password varchar(255)
);

-- alter table public.passwords
--     owner to postgres;


CREATE OR REPLACE FUNCTION insert_room_data(
    name_list text[],
    type_list integer[],
    intro_list text[],
    floor_name_list text[],
    building_name_list text[],
    region_name_list text[]
)
    RETURNS int AS $$
DECLARE
    region1_id INT;
    building1_id INT;
    floor1_id INT;
    room1_id INT;
    comment1_id INT;
BEGIN
    FOR i IN 1..array_length(name_list, 1) LOOP
            -- Check if region already exists
            SELECT region_id INTO region1_id FROM regions WHERE name = region_name_list[i] LIMIT 1;

            IF region1_id IS NULL THEN
                -- Insert new region
                INSERT INTO regions (name, intro)
                VALUES (region_name_list[i], '')
                RETURNING region_id INTO region1_id;
            END IF;

            -- Check if building already exists
            SELECT building_id INTO building1_id FROM buildings WHERE name = building_name_list[i] AND region_id = region1_id LIMIT 1;

            IF building1_id IS NULL THEN
                -- Insert new building
                INSERT INTO buildings (name, intro, region_id)
                VALUES (building_name_list[i], '', region1_id)
                RETURNING building_id INTO building1_id;
            END IF;

            -- Check if floor already exists
            SELECT floor_id INTO floor1_id FROM floors WHERE name = floor_name_list[i] AND building_id = building1_id LIMIT 1;

            IF floor1_id IS NULL THEN
                -- Insert new floor
                INSERT INTO floors (name, intro, building_id)
                VALUES (floor_name_list[i], '', building1_id)
                RETURNING floor_id INTO floor1_id;
            END IF;

            -- Check if room already exists
            SELECT room_id INTO room1_id FROM rooms WHERE name = name_list[i] AND floor_id = floor1_id LIMIT 1;

            IF room1_id IS NULL THEN
                -- Insert new room
                INSERT INTO rooms (name, type, intro, status, floor_id)
                VALUES (name_list[i], type_list[i], intro_list[i], 0, floor1_id)
                RETURNING room_id INTO room1_id;
            ELSE
                -- Update existing room
                UPDATE rooms SET type = type_list[i], intro = intro_list[i], status = 0 WHERE room_id = room1_id;
            END IF;

            -- Insert Comment
            INSERT INTO comments (user_id, post_id, title, creation_date, disabled)
            VALUES (room1_id, 0, name_list[i], NOW(), false)
            RETURNING comment_id INTO comment1_id;

            -- Insert CommentId to Room
            UPDATE rooms SET comment_base_id = comment1_id WHERE room_id = room1_id;

        END LOOP;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;



-- CREATE OR REPLACE FUNCTION insert_room_trigger()
--     RETURNS TRIGGER AS $$
-- BEGIN
--     PERFORM insert_room_data(
--             NEW.name::text[],
--             NEW.type::integer[],
--             NEW.intro::text[],
--             NEW.floor_name::text[],
--             NEW.building_name::text[],
--             NEW.region_name::text[]
--         );
--
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;

-- CREATE TRIGGER insert_room_trigger
--     BEFORE INSERT ON rooms
--     FOR EACH ROW EXECUTE FUNCTION insert_room_trigger();
