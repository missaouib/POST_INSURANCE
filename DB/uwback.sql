drop table t_uw_back;

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

CREATE UNIQUE INDEX idx_uwbp ON t_uw_back(policy_no); 

alter table t_under_write add column organ_code varchar(8);
alter table t_under_write add column organ_name varchar(64);

update t_under_write set sys_date=check_date where sys_date<"1900-01-01";
update t_under_write set client_receive_date=null,bill_back_date=null where bill_back_date<"1900-01-01";
update t_under_write set body_check_date1=null,body_check_date2=null,deal_check_date1=null,deal_check_date2=null where holder is null;

update t_under_write uw, t_policy_dtl pd set uw.holder=cast(aes_decrypt(unhex(pd.holder), 'GDPost') as char(100)),uw.insured=cast(aes_decrypt(unhex(pd.insured), 'GDPost') as char(100)),uw.relation=pd.relation where uw.policy_no=pd.policy_no and uw.holder is null;