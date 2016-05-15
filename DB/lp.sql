/*==============================================================*/
/* Table: t_settlement                                          */
/*==============================================================*/
create table t_settlement
(
   id                   bigint not null auto_increment,
   org_id               bigint,
   insured              varchar(32),
   reporter             varchar(32),
   reporter_phone       varchar(32),
   case_date            date,
   case_type            varchar(16),
   reporte_date         date,
   record_date          date,
   close_date           date,
   pay_fee              double,
   case_status          varchar(16),
   remark               varchar(1024),
   primary key (id)
);

/*==============================================================*/
/* Table: t_settlement_log                                      */
/*==============================================================*/
create table t_settlement_log
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   settlement_id        bigint,
   deal_date            datetime,
   info                 varchar(1024),
   is_key_info          boolean,
   ip                   varchar(24),
   primary key (id)
);

alter table t_settlement_log add constraint fk_reference_32 foreign key (settlement_id)
      references t_settlement (id) on delete restrict on update restrict;
