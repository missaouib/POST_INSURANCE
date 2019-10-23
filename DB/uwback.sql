drop table t_uw_back;

drop table t_uw_notback;

create table t_uw_back (
 id bigint not null auto_increment,
 policy_no varchar(18) not null,
 prod_code varchar(6) not null,
 bill_back_date date not null,
 client_receive_date date not null,
 operate_id  bigint,
 operate_time timestamp default current_timestamp,
 primary key (id)
)
engine=innodb auto_increment=1 default charset=utf8;

create table t_uw_notback (
 id bigint not null auto_increment,
 policy_no varchar(18) not null,
 prod_code varchar(6) default null,
 bill_back_date date default null,
 client_receive_date date default null,
 operate_id  bigint,
 operate_time timestamp default current_timestamp,
 primary key (id)
)
engine=innodb auto_increment=1 default charset=utf8;

CREATE UNIQUE INDEX idx_uwbp ON t_uw_back(policy_no); 
CREATE UNIQUE INDEX idx_uwnbp ON t_uw_notback(policy_no); 

alter table t_under_write add column organ_code varchar(8);
alter table t_under_write add column organ_name varchar(64);