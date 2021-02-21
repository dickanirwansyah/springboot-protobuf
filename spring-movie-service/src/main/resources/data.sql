drop table if exists movie;
create table movie as select * from CSVREAD('classpath:movie.csv');