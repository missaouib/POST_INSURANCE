/*==============================================================*/
/* Database name:  picodb                                       */
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/23 星期四 下午 22:07:00                    */
/*==============================================================*/


drop database if exists picodb;

/*==============================================================*/
/* Database: picodb                                             */
/*==============================================================*/
create database picodb;

use picodb;

/*==============================================================*/
/* User: pico@                                                  */
/*==============================================================*/
create user pico@;

/*==============================================================*/
/* Table: t_prd                                                 */
/*==============================================================*/
create table t_prd
(
   id                   bigint not null auto_increment,
   prd_code             varchar(8),
   prd_name             varchar(128),
   prd_status           int comment '0:invalid
            1:valid',
   prd_per_money        int,
   prd_perm             int,
   duration             int,
   primary key (id)
);

/*==============================================================*/
/* Table: t_conservation_error                                  */
/*==============================================================*/
create table t_conservation_error
(
   id                   bigint not null auto_increment,
   error_code           varchar(8),
   error_desc           varchar(256),
   primary key (id)
);

/*==============================================================*/
/* Table: t_area                                                */
/*==============================================================*/
create table t_area
(
   id                   bigint(20) not null auto_increment,
   area_code            varchar(6) not null,
   area_name            varchar(50) not null,
   city_code            varchar(6) not null,
   primary key (id)
)
engine=innodb auto_increment=1 default charset=utf8;

/*==============================================================*/
/* Table: t_city                                                */
/*==============================================================*/
create table t_city
(
   id                   bigint(20) not null auto_increment,
   city_code            varchar(6) not null,
   city_name            varchar(50) not null,
   province_code        varchar(6) not null,
   primary key (id)
)
engine=innodb auto_increment=1 default charset=utf8;

/*==============================================================*/
/* Table: t_policy                                              */
/*==============================================================*/
create table t_policy
(
   id                   bigint not null,
   org_id               bigint(20),
   city_name            varchar(12),
   area_name            varchar(12),
   prod_id              bigint,
   prod_name            varchar(128),
   bank_id              bigint,
   bank_name            varchar(128),
   sales_name           varchar(32),
   sales_id             varchar(18),
   status               int,
   perm                 int,
   policy_fee           double,
   copies               int,
   insured_amount       double,
   holder               varchar(32),
   holder_addr          varchar(256),
   holder_phone         varchar(20),
   holder_mobile        varchar(20),
   holder_card_type     int,
   holder_card_num      varchar(32),
   relation             int comment '0-本人
            1-配偶
            2-父母
            3-子女
            4-其他',
   insured              varchar(32),
   insured_card_type    int,
   insured_card_num     varchar(32),
   insured_mobile       varchar(20),
   insured_phone        varchar(20),
   insured_addr         varchar(256),
   policy_date          date,
   renewal_sucess_flag  int,
   reset_valid_flag     int,
   primary key (id)
);

/*==============================================================*/
/* Table: t_province                                            */
/*==============================================================*/
create table t_province
(
   id                   bigint(20) not null auto_increment,
   province_code        varchar(6) not null,
   province_name        varchar(50) not null,
   primary key (id)
)
engine=innodb auto_increment=1 default charset=utf8;

/*==============================================================*/
/* Table: t_renewal_dtl                                         */
/*==============================================================*/
create table t_renewal_dtl
(
   id                   bigint not null,
   type_id              bigint comment '0：电话
            1：现场',
   policy_id            bigint,
   type                 int,
   reset_valid_flag     int,
   renewal_info         varchar(256),
   renewal_date         date,
   renewal_man          varchar(32),
   is_success           boolean,
   primary key (id)
);

/*==============================================================*/
/* Table: t_renewal_type                                        */
/*==============================================================*/
create table t_renewal_type
(
   id                   bigint not null auto_increment,
   type_name            varchar(32),
   type_desc            varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: t_bank_code                                           */
/*==============================================================*/
create table t_bank_code
(
   id                   bigint not null auto_increment,
   cpi_code             varchar(18),
   bank_code            varchar(20),
   name                 varchar(60),
   status               int default 1 comment '状态',
   primary key (id)
);

/*==============================================================*/
/* Table: t_call_deal_type                                      */
/*==============================================================*/
create table t_call_deal_type
(
   id                   bigint not null auto_increment,
   type_name            varchar(32),
   type_desc            varchar(255),
   primary key (id)
);

/*==============================================================*/
/* Table: t_call_fail                                           */
/*==============================================================*/
create table t_call_fail
(
   id                   bigint not null,
   policy_id            bigint,
   fail_type_id         bigint,
   status               int,
   is_reset             boolean,
   reset_time           date,
   issue_date           date,
   success_fiag         int,
   success_date         date,
   letter_flag          int,
   primary key (id)
);

/*==============================================================*/
/* Table: t_call_feedback                                       */
/*==============================================================*/
create table t_call_feedback
(
   id                   bigint not null,
   call_fail_id         bigint,
   feedback             varchar(255),
   type                 int,
   woker                varchar(32),
   is_signed            int,
   feedback_time        datetime,
   is_success           boolean,
   primary key (id)
);

/*==============================================================*/
/* Table: t_category_org                                        */
/*==============================================================*/
create table t_category_org
(
   id                   bigint(20) not null auto_increment,
   org_id               bigint(20),
   category_id          bigint(20),
   primary key (id),
   unique key unique_name (org_id, category_id)
);

/*==============================================================*/
/* Table: t_check_record_dtl                                    */
/*==============================================================*/
create table t_check_record_dtl
(
   id                   bigint not null,
   checker              varchar(32),
   check_date           date,
   need_fix             boolean,
   policy_id            bigint,
   scan_error           boolean default false,
   holder_name          boolean,
   holder_birthday      boolean,
   holder_sexy          boolean,
   holder_card_id       boolean,
   holder_card_valid_date boolean,
   holder_married       boolean,
   holder_hight         boolean,
   holder_weight        boolean,
   holder_job           boolean,
   holder_job_code      boolean,
   holder_nation        boolean,
   holder_household     boolean,
   holder_receipt       boolean,
   holder_phone         boolean,
   holder_mobile        boolean,
   holder_addr          boolean,
   holder_postcode      boolean,
   relationship         boolean,
   insured_name         boolean,
   insured_birthday     boolean,
   insured_sexy         boolean,
   insured_card_id      boolean,
   insured_card_valid_date boolean,
   insured_hight        boolean,
   insured_weight       boolean,
   insured_job          boolean,
   insured_job_code     boolean,
   insured_nation       boolean,
   insured_household    boolean,
   insured_married      boolean,
   insured_phone        boolean,
   insured_mobile       boolean,
   insured_addr         boolean,
   insured_postcode     boolean,
   miss_file            boolean,
   prd_name             boolean,
   copies               boolean,
   pay_period           boolean,
   duration             boolean,
   premiums             boolean,
   pay_frequency        boolean,
   pay_method           boolean,
   pay_name             boolean,
   pay_card_id          boolean,
   bonus_flag           boolean,
   beneficiary          boolean,
   dispute_type         boolean comment 'dispute',
   notify               boolean,
   risk_sentence        boolean,
   holder_signed        boolean,
   insured_signed       boolean,
   insure_date          boolean,
   bank_signed          boolean,
   return_receipt       boolean,
   bank_else_info       boolean,
   auto_bank_account_transfer boolean,
   primary key (id)
);

/*==============================================================*/
/* Table: t_check_write_dtl                                     */
/*==============================================================*/
create table t_check_write_dtl
(
   id                   bigint not null,
   checker              varchar(32),
   check_date           date,
   need_fix             boolean,
   policy_id            bigint,
   scan_error           boolean default false,
   holder_name          boolean,
   holder_birthday      boolean,
   holder_sexy          boolean,
   holder_card_id       boolean,
   holder_card_valid_date boolean,
   holder_married       boolean,
   holder_hight         boolean,
   holder_weight        boolean,
   holder_job           boolean,
   holder_job_code      boolean,
   holder_nation        boolean,
   holder_household     boolean,
   holder_receipt       boolean,
   holder_phone         boolean,
   holder_mobile        boolean,
   holder_addr          boolean,
   holder_postcode      boolean,
   relationship         boolean,
   insured_name         boolean,
   insured_birthday     boolean,
   insured_sexy         boolean,
   insured_card_id      boolean,
   insured_card_valid_date boolean,
   insured_hight        boolean,
   insured_weight       boolean,
   insured_job          boolean,
   insured_job_code     boolean,
   insured_nation       boolean,
   insured_household    boolean,
   insured_married      boolean,
   insured_phone        boolean,
   insured_mobile       boolean,
   insured_addr         boolean,
   insured_postcode     boolean,
   miss_file            boolean,
   prd_name             boolean,
   copies               boolean,
   pay_period           boolean,
   duration             boolean,
   premiums             boolean,
   pay_frequency        boolean,
   pay_method           boolean,
   pay_name             boolean,
   pay_card_id          boolean,
   bonus_flag           boolean,
   beneficiary          boolean,
   dispute_type         boolean comment 'dispute',
   notify               boolean,
   risk_sentence        boolean,
   holder_signed        boolean,
   insured_signed       boolean,
   insure_date          boolean,
   bank_signed          boolean,
   return_receipt       boolean,
   bank_else_info       boolean,
   auto_bank_account_transfer boolean,
   primary key (id)
);

/*==============================================================*/
/* Table: t_component_resource                                  */
/*==============================================================*/
create table t_component_resource
(
   id                   bigint(20) not null auto_increment,
   file                 longblob,
   name                 varchar(128) default null,
   size                 varchar(32) default null,
   store_type           varchar(16) default null,
   upload_time          datetime default null,
   uuid                 varchar(32) default null,
   primary key (id),
   unique key unique_uuid (uuid)
)
engine=innodb auto_increment=1 default charset=utf8;

/*==============================================================*/
/* Table: t_conservation_dtl                                    */
/*==============================================================*/
create table t_conservation_dtl
(
   id                   bigint not null auto_increment,
   type                 int,
   deal_num             varchar(32),
   info                 varchar(256),
   cs_date              date,
   cs_user_id           bigint,
   cs_rst               varchar(64),
   deal_user_id         bigint,
   status               int,
   policy_id            bigint,
   cancel_flag          boolean,
   cancel_man           bigint,
   cancel_date          datetime,
   remark               varchar(256),
   primary key (id)
);

/*==============================================================*/
/* Table: t_convicted_info                                      */
/*==============================================================*/
create table t_convicted_info
(
   id                   bigint not null,
   call_fail_id         bigint,
   type                 int,
   convicted_info       varchar(255),
   convicted_time       date,
   is_signed            boolean,
   sign_date            date,
   primary key (id)
);

/*==============================================================*/
/* Table: t_data_control                                        */
/*==============================================================*/
create table t_data_control
(
   id                   bigint(20) not null auto_increment,
   control              varchar(10240) default null,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   primary key (id),
   unique key unique_name (name)
)
engine=innodb auto_increment=2 default charset=utf8;

/*==============================================================*/
/* Table: t_issue                                               */
/*==============================================================*/
create table t_issue
(
   id                   bigint not null,
   type_id              bigint,
   policy_id            bigint,
   issue_time           date,
   status               int,
   result               varchar(256),
   deal_man             varchar(32),
   deal_time            date,
   reopen_reason        varchar(256),
   primary key (id)
);

/*==============================================================*/
/* Table: t_issue_type                                          */
/*==============================================================*/
create table t_issue_type
(
   id                   bigint not null auto_increment,
   type_name            varchar(32),
   type_desc            varchar(255) comment '处理描述',
   primary key (id)
);

/*==============================================================*/
/* Table: t_log_info                                            */
/*==============================================================*/
create table t_log_info
(
   id                   bigint(20) not null auto_increment,
   create_time          datetime default null,
   ip_address           varchar(16) default null,
   log_level            varchar(16) default null,
   message              varchar(256) default null,
   username             varchar(32) default null,
   primary key (id)
)
engine=innodb auto_increment=2 default charset=utf8;

/*==============================================================*/
/* Table: t_module                                              */
/*==============================================================*/
create table t_module
(
   id                   bigint(20) not null auto_increment,
   class_name           varchar(256) default null,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   priority             int(11) not null,
   sn                   varchar(32) not null,
   url                  varchar(256) not null,
   parent_id            bigint(20) default null,
   primary key (id),
   unique key unique_sn (sn),
   key fk89a04864953fe581 (parent_id)
)
engine=innodb auto_increment=16 default charset=utf8;

/*==============================================================*/
/* Table: t_organization                                        */
/*==============================================================*/
create table t_organization
(
   id                   bigint(20) not null auto_increment,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   org_code             varchar(12) not null,
   level                int(1) not null,
   priority             int(11) not null,
   parent_id            bigint(20) default null,
   primary key (id),
   unique key unique_name (name),
   key fkcae6352bf464a488 (parent_id)
)
engine=innodb auto_increment=6 default charset=utf8;

/*==============================================================*/
/* Table: t_organization_role                                   */
/*==============================================================*/
create table t_organization_role
(
   id                   bigint(20) not null auto_increment,
   priority             int(11) not null,
   organization_id      bigint(20) default null,
   role_id              bigint(20) default null,
   primary key (id),
   key fkd483870ac80e875f (organization_id),
   key fkd483870a3ffd717f (role_id)
)
engine=innodb auto_increment=4 default charset=utf8;

/*==============================================================*/
/* Table: t_permission                                          */
/*==============================================================*/
create table t_permission
(
   id                   bigint(20) not null auto_increment,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   sn                   varchar(16) not null,
   module_id            bigint(20) default null,
   primary key (id),
   key fkcae8abc7bd56587f (module_id)
)
engine=innodb auto_increment=66 default charset=utf8;

/*==============================================================*/
/* Table: t_renewal_fee_dtl                                     */
/*==============================================================*/
create table t_renewal_fee_dtl
(
   id                   bigint not null,
   policy_id            bigint,
   fee_dtl              varchar(64),
   fee_date             date,
   primary key (id)
);

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   id                   bigint(20) not null auto_increment,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   primary key (id),
   unique key unique_name (name)
)
engine=innodb auto_increment=6 default charset=utf8;

/*==============================================================*/
/* Table: t_role_permission                                     */
/*==============================================================*/
create table t_role_permission
(
   id                   bigint(20) not null auto_increment,
   permission_id        bigint(20) default null,
   role_id              bigint(20) default null,
   primary key (id),
   key fk9b6ea402bb04f1f (permission_id),
   key fk9b6ea403ffd717f (role_id)
)
engine=innodb auto_increment=71 default charset=utf8;

/*==============================================================*/
/* Table: t_role_permission_data_control                        */
/*==============================================================*/
create table t_role_permission_data_control
(
   id                   bigint(20) not null auto_increment,
   data_control_id      bigint(20) default null,
   role_permission_id   bigint(20) default null,
   primary key (id),
   key fkb2efd96750319a20 (data_control_id),
   key fkb2efd96758f6f1ac (role_permission_id)
)
engine=innodb auto_increment=4 default charset=utf8;

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   bigint(20) not null auto_increment,
   create_time          datetime default null,
   email                varchar(128) default null,
   password             varchar(64) not null,
   pwd_time             datetime default null,
   phone                varchar(32) default null,
   realname             varchar(32) not null,
   salt                 varchar(32) not null,
   status               varchar(16) not null,
   username             varchar(32) not null,
   organization_id      bigint(20) default null,
   primary key (id),
   unique key unique_name (username),
   key fk97bbabc3c80e875f (organization_id)
)
engine=innodb auto_increment=2 default charset=utf8;

/*==============================================================*/
/* Table: t_user_role                                           */
/*==============================================================*/
create table t_user_role
(
   id                   bigint(20) not null auto_increment,
   priority             int(11) not null,
   role_id              bigint(20) default null,
   user_id              bigint(20) default null,
   primary key (id),
   key fk24086f723ffd717f (role_id),
   key fk24086f72e528355f (user_id)
)
engine=innodb auto_increment=2 default charset=utf8;

alter table t_policy add constraint fk_reference_13 foreign key (org_id)
      references t_organization (id) on delete restrict on update restrict;

alter table t_policy add constraint fk_reference_23 foreign key (prod_id)
      references t_prd (id) on delete restrict on update restrict;

alter table t_renewal_dtl add constraint fk_reference_16 foreign key (type_id)
      references t_renewal_type (id) on delete restrict on update restrict;

alter table t_renewal_dtl add constraint fk_reference_17 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_call_fail add constraint fk_reference_19 foreign key (fail_type_id)
      references t_call_deal_type (id) on delete restrict on update restrict;

alter table t_call_fail add constraint fk_reference_22 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_call_feedback add constraint fk_reference_20 foreign key (call_fail_id)
      references t_call_fail (id) on delete restrict on update restrict;

alter table t_check_record_dtl add constraint fk_reference_27 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_check_write_dtl add constraint fk_reference_26 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_conservation_dtl add constraint fk_reference_25 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_convicted_info add constraint fk_reference_21 foreign key (call_fail_id)
      references t_call_fail (id) on delete restrict on update restrict;

alter table t_issue add constraint fk_reference_14 foreign key (type_id)
      references t_issue_type (id) on delete restrict on update restrict;

alter table t_issue add constraint fk_reference_15 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_module add constraint fk89a04864953fe581 foreign key (parent_id)
      references t_module (id);

alter table t_organization add constraint fkcae6352bf464a488 foreign key (parent_id)
      references t_organization (id);

alter table t_organization_role add constraint fkd483870a3ffd717f foreign key (role_id)
      references t_role (id);

alter table t_organization_role add constraint fkd483870ac80e875f foreign key (organization_id)
      references t_organization (id);

alter table t_permission add constraint fkcae8abc7bd56587f foreign key (module_id)
      references t_module (id);

alter table t_renewal_fee_dtl add constraint fk_reference_24 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_role_permission add constraint fk9b6ea402bb04f1f foreign key (permission_id)
      references t_permission (id);

alter table t_role_permission add constraint fk9b6ea403ffd717f foreign key (role_id)
      references t_role (id);

alter table t_role_permission_data_control add constraint fkb2efd96750319a20 foreign key (data_control_id)
      references t_data_control (id);

alter table t_role_permission_data_control add constraint fkb2efd96758f6f1ac foreign key (role_permission_id)
      references t_role_permission (id);

alter table t_user add constraint fk97bbabc3c80e875f foreign key (organization_id)
      references t_organization (id);

alter table t_user_role add constraint fk24086f723ffd717f foreign key (role_id)
      references t_role (id);

alter table t_user_role add constraint fk24086f72e528355f foreign key (user_id)
      references t_user (id);

