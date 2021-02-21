drop table if exists user;
create table user as select * from CSVREAD('classpath:user.csv');