/*==============================================================*/
/* Table: t_settlement                                          */
/*==============================================================*/
create table t_settle_task
(
   id                   bigint not null auto_increment,
   org_id               bigint,
   policy_no			VARCHAR(18),
   insured              varchar(32),
   check_start_date     date,
   check_end_date      	date,
   limitation           int,
   check_req      		varchar(254),
   checker            	varchar(16),
   checker_addr         varchar(16),
   check_fee			double,
   attr_link			varchar(128),
   check_status			varchar(16),	
   operate_id           bigint,
   create_time         	date,
   primary key (id)
);

/*==============================================================*/
/* Table: t_settlement_log                                      */
/*==============================================================*/
create table t_settle_task_log
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   settle_task_id       bigint,
   deal_date            datetime,
   info                 varchar(1024),
   is_key_info          boolean,
   ip                   varchar(24),
   primary key (id)
);

alter table t_settle_task_log add constraint fk_sett_task_32 foreign key (settle_task_id)
      references t_settle_task (id) on delete restrict on update restrict;
