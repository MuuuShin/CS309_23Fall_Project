-- Database: cs309a

create table if not exists regions
(
    region_id bigint generated always as identity
        primary key,
    name      varchar(255),
    intro     varchar(255)
);

alter table regions
    owner to postgres;

create table if not exists buildings
(
    building_id bigint generated always as identity
        primary key,
    name        varchar(255),
    intro       varchar(255),
    region_id   bigint
        constraint buildings_regions_null_fk
            references regions
);

alter table buildings
    owner to postgres;

create table if not exists floors
(
    floor_id    bigint generated always as identity
        primary key,
    name        varchar(255),
    intro       varchar(255),
    building_id bigint
        constraint floors_buildings_null_fk
            references buildings
);

alter table floors
    owner to postgres;

create table if not exists rooms
(
    room_id         bigint generated always as identity
        constraint room_pkey
            primary key,
    name            varchar(255),
    type            varchar(255),
    intro           varchar(255),
    status          integer,
    floor_id        bigint
        constraint rooms_floors_null_fk
            references floors,
    comment_base_id bigint
);

alter table rooms
    owner to postgres;

create table if not exists teachers
(
    teacher_id bigint generated always as identity
        primary key,
    name       varchar(255),
    permission varchar(255),
    account    varchar(255),
    password   varchar(255)
);

alter table teachers
    owner to postgres;

create table if not exists comments
(
    comment_id    bigint generated always as identity
        constraint commets_pkey
            primary key,
    title         varchar(255),
    body          varchar(255),
    account_id    bigint,
    post_id       bigint,
    creation_date timestamp(6)
);

alter table comments
    owner to postgres;

create table if not exists groups
(
    group_id   bigint generated always as identity
        primary key,
    name       varchar(255),
    member1_id bigint,
    member2_id bigint,
    member3_id bigint,
    member4_id bigint,
    status     varchar(255),
    leader     varchar(255),
    room_id    bigint
        constraint groups_rooms_null_fk
            references rooms
);

alter table groups
    owner to postgres;

create table if not exists students
(
    student_id bigint generated always as identity
        primary key,
    name       varchar(255),
    intro      varchar(255),
    gender     smallint,
    group_id   bigint
        constraint students_groups_null_fk
            references groups,
    type       varchar(255),
    awake_time time(6),
    sleep_time varchar(255),
    account    varchar(255),
    password   varchar(255)
);

alter table students
    owner to postgres;

create table if not exists group_stars
(
    group_id bigint not null
        constraint group_likes_groups_null_fk
            references groups,
    room_id  bigint not null
        constraint group_likes_rooms_null_fk
            references rooms,
    constraint group_likes_pkey
        primary key (group_id, room_id)
);

alter table group_stars
    owner to postgres;

create table if not exists timelines
(
    timeline_id bigint generated always as identity
        primary key,
    type        varchar(255),
    begin_time1 timestamp(0),
    end_time1   timestamp(0),
    begin_time2 timestamp(0),
    end_time2   timestamp(0),
    begin_time3 timestamp(0),
    end_time3   timestamp(0),
    begin_time4 timestamp(0),
    end_time4   timestamp(0)
);

alter table timelines
    owner to postgres;

