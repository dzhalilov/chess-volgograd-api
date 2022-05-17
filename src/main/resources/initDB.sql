create table players
(
    id                  integer not null
        primary key,
    age                 integer not null,
    blitz_rating        integer not null,
    classic_rating      integer not null,
    fide_classic_rating integer not null,
    fide_id             integer not null,
    name                varchar(255),
    rapid_rating        integer not null,
    region_code         integer not null,
    sex                 varchar(255)
);