-- Database: cs309a

create table if not exists rooms
(
    room_id  integer not null
        constraint room_pkey
            primary key,
    name     varchar(255),
    type     varchar(255),
    intro    varchar(255),
    status   integer,
    floor_id integer
);

alter table rooms
    owner to postgres;

create table if not exists floors
(
    floor_id    integer not null
        primary key,
    name        varchar(255),
    intro       varchar(255),
    building_id integer
);

alter table floors
    owner to postgres;

create table if not exists buildings
(
    building_id integer not null
        primary key,
    name        varchar(255),
    intro       varchar(255),
    region_id   integer
);

alter table buildings
    owner to postgres;

create table if not exists regions
(
    region_id integer not null
        primary key,
    name      varchar(255),
    intro     varchar(255)
);

alter table regions
    owner to postgres;

create table if not exists students
(
    student_id integer not null
        primary key,
    name       varchar(255),
    intro      varchar(255),
    gender     smallint,
    group_id   integer,
    type       varchar(255),
    tag1       varchar(255),
    tag2       varchar(255)
);

alter table students
    owner to postgres;

create table if not exists teachers
(
    teacher_id integer not null
        primary key,
    name       varchar(255)
);

alter table teachers
    owner to postgres;

create table if not exists comments
(
    comment_id    integer not null
        constraint commets_pkey
            primary key,
    title         varchar(255),
    body          varchar(255),
    account_id    integer,
    post_id       integer,
    creation_date date
);

alter table comments
    owner to postgres;

create table if not exists groups
(
    group_id   integer not null
        primary key,
    name       varchar(255),
    member1_id integer,
    member2_id integer,
    member3_id integer,
    member4_id integer,
    status     varchar(255),
    leader     varchar(255)
);

alter table groups
    owner to postgres;

create table if not exists group_likes
(
    group_id integer not null,
    room_id  integer not null
);

alter table group_likes
    owner to postgres;

