/*==============================================================*/
/* Database name:  picodb                                       */
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/2/1 ÐÇÆÚÒ» ÏÂÎç 23:08:30                     */
/*==============================================================*/


drop database if exists picodb;

/*==============================================================*/
/* Database: picodb                                             */
/*==============================================================*/
create database picodb;

use picodb;

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
/* Table: t_settlement_check                                    */
/*==============================================================*/
create table t_settlement_check
(
   id                   bigint not null auto_increment,
   settlement_id        bigint,
   check_date           date,
   checker              varchar(32),
   check_done_date      date,
   check_done_man       varchar(32),
   primary key (id)
);

/*==============================================================*/
/* Table: t_settlement_policy                                   */
/*==============================================================*/
create table t_settlement_policy
(
   id                   bigint not null auto_increment,
   settlement_id        bigint,
   policy_no            varchar(18),
   prod                 varchar(64),
   policy_fee           double,
   valid_date           date,
   primary key (id)
);

/*==============================================================*/
/* Table: t_settlement_report                                   */
/*==============================================================*/
create table t_settlement_report
(
   id                   bigint not null auto_increment,
   settlement_id        bigint,
   first_case_time      date,
   case_man             char(10),
   first_file_date      date,
   first_sign_man       varchar(32),
   all_file_date        date,
   all_sign_man         varchar(32),
   primary key (id)
);

alter table t_settlement_check add constraint fk_reference_31 foreign key (settlement_id)
      references t_settlement (id) on delete restrict on update restrict;

alter table t_settlement_policy add constraint fk_reference_29 foreign key (settlement_id)
      references t_settlement (id) on delete restrict on update restrict;

alter table t_settlement_report add constraint fk_reference_30 foreign key (settlement_id)
      references t_settlement (id) on delete restrict on update restrict;

