-- ------------------------------
-- 设定AES加密的key
-- ------------------------------
SET @AESKey='pico';

SET FOREIGN_KEY_CHECKS=0;

/*==============================================================*/
/* table: t_policy                                            */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy`;
create table t_policy
(
   id                   bigint(20) not null auto_increment,
   policy_name           varchar(150) not null,
   description          varchar(250),
   contact              varchar(100),
   phone                varchar(100),
   address              varchar(500),
   email                varchar(100),
   deliver              varchar(100),
   deliver_address       varchar(500),
   deliver_email         varchar(100),
   deliver_phone         varchar(100),
   payee                varchar(100),
   account              varchar(100),
   status               int,
   priority             int,
   parent_id             bigint(20),
   primary key (id),
   unique key `unique_name` (`policy_name`),
  key `fk_m_parentid` (`parent_id`),
  constraint `fk_tm_parentid` foreign key (`parent_id`) references `t_policy` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;

INSERT INTO t_policy(id, description, policy_name, priority, contact, status, parent_id) VALUES (1, hex(aes_encrypt('不能删除。',@AESKey)), hex(aes_encrypt('根节点',@AESKey)), 999, '',1, null);

/*==============================================================*/
/* index: index_policy                                          */
/*==============================================================*/
create index index_policy on t_policy
(
   policy_name
);

/*==============================================================*/
/* table: t_policy_category                                    */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_category`;
create table t_policy_category
(
   id                   bigint(20) not null auto_increment,
   policy_id             bigint(20),
   pl                   varchar(100) not null,
   create_date           datetime not null,
   created_by            int not null,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policycategory                                  */
/*==============================================================*/
create index index_policycategory on t_policy_category
(
   policy_id,
   pl
);

/*==============================================================*/
/* table: t_policy_data                                        */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_data`;
create table t_policy_data
(
   id                   bigint(20) not null auto_increment,
   policy_id            bigint(20),
   ny                   int,
   did                  bigint(20),
   dm                   varchar(100),
   plid                 bigint(20),
   pl                   varchar(100),
   pm                   varchar(100),
   cj                   varchar(100),
   gg                   varchar(100),
   dj                   decimal(12,4),
   cbj                  decimal(12,4),
   mlv                  decimal(12,4),
   xsl                  decimal(12,4),
   xse                  decimal(12,4),
   jhl                  decimal(12,4),
   yckc                 decimal(12,4),
   ymkc                 decimal(12,4),
   create_date          datetime,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policydata                                      */
/*==============================================================*/
create index index_policydata on t_policy_data
(
   policy_id,
   ny,
   did,
   plid,
   pm
);

/*==============================================================*/
/* table: t_policy_data_control                                 */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_data_control`;
create table t_policy_data_control
(
   id                   bigint(20) not null auto_increment,
   control              varchar(10240) default null,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   primary key (id),
   unique key unique_name (name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* table: t_policy_data_status                                  */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_data_status`;
create table t_policy_data_status
(
   id                   bigint(20) not null auto_increment,
   policy_id             bigint(20),
   ny                   int not null,
   status               int not null,
   create_date           datetime not null,
   operator             bigint(20) not null,
   operator_name         varchar(20) not null,
   operate_type          int not null,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policydatastatus                                */
/*==============================================================*/
create index index_policydatastatus on t_policy_data_status
(
   policy_id,
   ny,
   create_date
);

/*==============================================================*/
/* table: t_policy_data_template                                */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_data_template`;
create table t_policy_data_template
(
   id                   bigint(20) not null auto_increment,
   policy_id             bigint(20),
   template_name         varchar(100) not null,
   template_type         int not null,
   status               int not null,
   create_date           datetime not null,
   created_by            int not null,
   modify_date           datetime,
   modify_by             bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policydatatemplate                              */
/*==============================================================*/
create index index_policydatatemplate on t_policy_data_template
(
   policy_id,
   template_name
);

/*==============================================================*/
/* table: t_policy_data_template_field                           */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_data_template_field`;
create table t_policy_data_template_field
(
   id                   bigint(20) not null auto_increment,
   template_id           bigint(20),
   field_name            varchar(100) not null,
   field                int not null,
   is_using_column        int not null,
   data_column           varchar(100),
   map_column            varchar(100),
   is_using_filename      int not null,
   is_using_sheetname     int not null,
   is_using_multicolumn   int not null,
   multicolumn          varchar(200),
   create_date           datetime not null,
   created_by            bigint(20) not null,
   modify_date           datetime,
   modify_by             bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policydatatemplatefield                         */
/*==============================================================*/
create index index_policydatatemplatefield on t_policy_data_template_field
(
   template_id,
   field_name,
   field
);

/*==============================================================*/
/* table: t_policy_data_template_field_rule                       */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_data_template_field_rule`;
create table t_policy_data_template_field_rule
(
   id                   bigint(20) not null auto_increment,
   field_id              bigint(20),
   rule_name             varchar(100) not null,
   split_char            varchar(20) not null,
   value_index           int not null,
   create_date           datetime not null,
   created_by            int not null,
   modify_date           datetime,
   modify_by             bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policydatatemplatefieldrule                     */
/*==============================================================*/
create index index_policydatatemplatefieldrule on t_policy_data_template_field_rule
(
   field_id,
   rule_name
);

/*==============================================================*/
/* table: t_policy_message                                     */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_message`;
create table t_policy_message
(
   id                   bigint(20) not null auto_increment,
   category_id           bigint(20),
   policy_user_id         bigint(20),
   user_id               bigint(20),
   title                varchar(200) not null,
   content              varchar(1000) not null,
   create_date           datetime not null,
   status								int not null,
   parent_id             bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policymessage                                   */
/*==============================================================*/
create index index_policymessage on t_policy_message
(
   category_id,
   policy_user_id,
   user_id,
   title,
   create_date,
   parent_id
);

/*==============================================================*/
/* table: t_policy_message_assign                               */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_message_assign`;
create table t_policy_message_assign
(
   id                   bigint(20) not null auto_increment,
   category_id           bigint(20),
   user_id               bigint(20),
   message_id            bigint(20),
   create_date           datetime not null,
   created_by            bigint(20) not null,
   modify_date           datetime,
   modify_by             bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policymessageassign                             */
/*==============================================================*/
create index index_policymessageassign on t_policy_message_assign
(
   category_id,
   user_id,
   message_id
);

/*==============================================================*/
/* table: t_policy_message_category                             */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_message_category`;
create table t_policy_message_category
(
   id                   bigint(20) not null auto_increment,
   category_name         varchar(100) not null,
   description          varchar(200),
   status               int not null,
   create_date           datetime not null,
   created_by            bigint(20) not null,
   modify_date           datetime,
   modify_by             bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policymessagecategory                           */
/*==============================================================*/
create index index_policymessagecategory on t_policy_message_category
(
   category_name
);

/*==============================================================*/
/* table: t_policy_module                                      */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_module`;
create table t_policy_module
(
   id                   bigint(20) not null auto_increment,
   class_name            varchar(256) default null,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   priority             int(11) not null,
   sn                   varchar(32) not null,
   url                  varchar(256) not null,
   parent_id             bigint(20) default null,
   primary key (id),
   unique key unique_sn (sn),
   key policymodulekey (parent_id)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=UTF8;

INSERT INTO `t_policy_module` VALUES ('1', null, '所有模块的根节点，不能删除。', '根模块', '1', 'Root', '#', null);
INSERT INTO `t_policy_module` VALUES ('2', null, '连锁管理:包含连锁管理、连锁管理员管理等。', '连锁管理', '999', 'policys', '#', '1');
INSERT INTO `t_policy_module` VALUES ('3', null, null, '连锁管理', '999', 'Tblpolicy', '/policys/policy/tree_list', '2');
INSERT INTO `t_policy_module` VALUES ('4', null, null, '连锁管理员', '999', 'TblpolicyUser', '/policys/user/list', '2');
INSERT INTO `t_policy_module` VALUES ('5', null, null, '连锁用户角色管理', '999', 'TblpolicyRole', '/policys/role/list', '2');
-- INSERT INTO `t_policy_module` VALUES ('6', null, null, '连锁数据权限', '999', 'TblpolicyDataControl', '/policys/dataControl/list', '2');
INSERT INTO `t_policy_module` VALUES ('6', null, null, '连锁模块管理', '999', 'TblpolicyModule', '/policys/module/tree_list', '2');

/*==============================================================*/
/* table: t_policy_permission                                  */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_permission`;
create table t_policy_permission
(
   id                   bigint(20) not null auto_increment,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   sn                   varchar(16) not null,
   module_id             bigint(20) default null,
   primary key (id),
   key policypermissionkey (module_id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=UTF8;

INSERT INTO `t_policy_permission` VALUES ('1', null, '看', 'show', '2');
INSERT INTO `t_policy_permission` VALUES ('2', null, '看', 'show', '3');
INSERT INTO `t_policy_permission` VALUES ('3', null, '查', 'view', '3');
INSERT INTO `t_policy_permission` VALUES ('4', null, '改', 'edit', '3');
INSERT INTO `t_policy_permission` VALUES ('5', null, '看', 'show', '4');
INSERT INTO `t_policy_permission` VALUES ('6', null, '增', 'save', '4');
INSERT INTO `t_policy_permission` VALUES ('7', null, '删', 'delete', '4');
INSERT INTO `t_policy_permission` VALUES ('8', null, '查', 'view', '4');
INSERT INTO `t_policy_permission` VALUES ('9', null, '改', 'edit', '4');
INSERT INTO `t_policy_permission` VALUES ('10', '重置密码、更新状态', '重置', 'reset', '4');
INSERT INTO `t_policy_permission` VALUES ('11', '分配、撤销角色', '授权', 'assign', '4');
INSERT INTO `t_policy_permission` VALUES ('12', null, '看', 'show', '5');
INSERT INTO `t_policy_permission` VALUES ('13', null, '增', 'save', '5');
INSERT INTO `t_policy_permission` VALUES ('14', null, '删', 'delete', '5');
INSERT INTO `t_policy_permission` VALUES ('15', null, '查', 'view', '5');
INSERT INTO `t_policy_permission` VALUES ('16', null, '改', 'edit', '5');
INSERT INTO `t_policy_permission` VALUES ('17', null, '授权', 'assign', '5');
-- INSERT INTO `t_policy_permission` VALUES ('18', null, '看', 'show', '6');
-- INSERT INTO `t_policy_permission` VALUES ('19', null, '增', 'save', '6');
-- INSERT INTO `t_policy_permission` VALUES ('20', null, '删', 'delete', '6');
-- INSERT INTO `t_policy_permission` VALUES ('21', null, '查', 'view', '6');
-- INSERT INTO `t_policy_permission` VALUES ('22', null, '改', 'edit', '6');

/*==============================================================*/
/* table: t_policy_resource                                    */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_resource`;
create table t_policy_resource
(
   id                   bigint(20) not null auto_increment,
   policy_id             bigint(20) not null,
   resource_id           bigint(20),
   status               int,
   read_date             datetime,
   create_date           datetime,
   created_by            bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policyresource                                  */
/*==============================================================*/
create index index_policyresource on t_policy_resource
(
   policy_id,
   resource_id,
   create_date
);

/*==============================================================*/
/* table: t_policy_role                                        */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_role`;
create table t_policy_role
(
   id                   bigint(20) not null auto_increment,
   description          varchar(256) default null,
   name                 varchar(64) not null,
   policy_id							bigint(20),
   primary key (id),
   unique key unique_name (name,policy_id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;

INSERT INTO `t_policy_role` VALUES ('1', '管理员。', '管理员', -1);

/*==============================================================*/
/* table: t_policy_role_permission                             */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_role_permission`;
create table t_policy_role_permission
(
   id                   bigint(20) not null auto_increment,
   permission_id         bigint(20) default null,
   role_id               bigint(20) default null,
   primary key (id),
   key mrppid (permission_id),
   key mrprid (role_id)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=UTF8;

INSERT INTO `t_policy_role_permission` VALUES ('1', '1', '1');
INSERT INTO `t_policy_role_permission` VALUES ('2', '2', '1');
INSERT INTO `t_policy_role_permission` VALUES ('3', '3', '1');
INSERT INTO `t_policy_role_permission` VALUES ('4', '4', '1');
INSERT INTO `t_policy_role_permission` VALUES ('5', '5', '1');
INSERT INTO `t_policy_role_permission` VALUES ('6', '6', '1');
INSERT INTO `t_policy_role_permission` VALUES ('7', '7', '1');
INSERT INTO `t_policy_role_permission` VALUES ('8', '8', '1');
INSERT INTO `t_policy_role_permission` VALUES ('9', '9', '1');
INSERT INTO `t_policy_role_permission` VALUES ('10', '10', '1');
INSERT INTO `t_policy_role_permission` VALUES ('11', '11', '1');
INSERT INTO `t_policy_role_permission` VALUES ('12', '12', '1');
INSERT INTO `t_policy_role_permission` VALUES ('13', '13', '1');
INSERT INTO `t_policy_role_permission` VALUES ('14', '14', '1');
INSERT INTO `t_policy_role_permission` VALUES ('15', '15', '1');
INSERT INTO `t_policy_role_permission` VALUES ('16', '16', '1');
INSERT INTO `t_policy_role_permission` VALUES ('17', '17', '1');
-- INSERT INTO `t_policy_role_permission` VALUES ('18', '18', '1');
-- INSERT INTO `t_policy_role_permission` VALUES ('19', '19', '1');
-- INSERT INTO `t_policy_role_permission` VALUES ('20', '20', '1');
-- INSERT INTO `t_policy_role_permission` VALUES ('21', '21', '1');
-- INSERT INTO `t_policy_role_permission` VALUES ('22', '22', '1');

/*==============================================================*/
/* table: t_policy_role_permission_data_control                 */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_role_permission_data_control`;
create table t_policy_role_permission_data_control
(
   id                   bigint(20) not null auto_increment,
   data_control_id        bigint(20) default null,
   role_permission_id     bigint(20) default null,
   primary key (id),
   key mpddcidkey (data_control_id),
   key mpdrpidkey (role_permission_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* table: t_policy_shop                                        */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_shop`;
create table t_policy_shop
(
   id                   bigint(20) not null auto_increment,
   policy_id             bigint(20),
   dm                   varchar(100) not null,
   create_date           datetime not null,
   created_by            int not null,
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policyshop                                      */
/*==============================================================*/
create index index_policyshop on t_policy_shop
(
   policy_id,
   dm
);

/*==============================================================*/
/* table: t_policy_user                                        */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_user`;
create table t_policy_user
(
   id                   bigint(20) not null auto_increment,
   policy_id             bigint(20),
   user_name             varchar(100) not null,
   real_name             varchar(100),
   password             varchar(100) not null,
   salt                 varchar(50),
   phone                varchar(100),
   email                varchar(100),
   status               varchar(16) NOT NULL,
   description          varchar(1024),
   is_admin              int,
   create_date           datetime,
   created_by            bigint(20),
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* index: index_policyuser                                      */
/*==============================================================*/
create index index_policyuser on t_policy_user
(
   policy_id,
   user_name
);

/*==============================================================*/
/* table: t_policy_user_role                                   */
/*==============================================================*/
DROP TABLE IF EXISTS `t_policy_user_role`;
create table t_policy_user_role
(
   id                   bigint(20) not null auto_increment,
   priority							int(11),
   role_id               bigint(20) default null,
   user_id               bigint(20) default null,
   primary key (id),
   key murridkey (role_id),
   key muruidkey (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists t_category_org;

create table t_category_org
(
   id                   bigint(20) not null auto_increment,
   org_id               bigint(20),
   category_id          bigint(20), 
   primary key (id),
   unique key unique_name (org_id,category_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_policy_category add constraint fk_reference_28 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_policy_data add constraint fk_reference_29 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_policy_data add constraint fk_reference_30 foreign key (did)
      references t_policy_shop (id) on delete restrict on update restrict;

alter table t_policy_data add constraint fk_reference_31 foreign key (plid)
      references t_policy_category (id) on delete restrict on update restrict;

alter table t_policy_data_status add constraint fk_reference_33 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_policy_data_template add constraint fk_reference_34 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_policy_data_template_field add constraint fk_reference_14 foreign key (template_id)
      references t_policy_data_template (id) on delete restrict on update restrict;

alter table t_policy_data_template_field_rule add constraint fk_reference_15 foreign key (field_id)
      references t_policy_data_template_field (id) on delete restrict on update restrict;

alter table t_policy_message add constraint fk_reference_16 foreign key (category_id)
      references t_policy_message_category (id) on delete restrict on update restrict;

alter table t_policy_message add constraint fk_reference_35 foreign key (policy_user_id)
      references t_policy_user (id) on delete restrict on update restrict;

alter table t_policy_message add constraint fk_reference_37 foreign key (user_id)
      references t_user (id) on delete restrict on update restrict;

alter table t_policy_message_assign add constraint fk_reference_17 foreign key (category_id)
      references t_policy_message_category (id) on delete restrict on update restrict;

alter table t_policy_module add constraint policymodulekey foreign key (parent_id)
      references t_policy_module (id);

alter table t_policy_permission add constraint fk_reference_19 foreign key (module_id)
      references t_policy_module (id) on delete restrict on update restrict;

alter table t_policy_resource add constraint fk_reference_13 foreign key (resource_id)
      references t_component_resource (id) on delete restrict on update restrict;

alter table t_policy_resource add constraint fk_reference_36 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_policy_role_permission add constraint fk_reference_20 foreign key (role_id)
      references t_policy_role (id) on delete restrict on update restrict;

alter table t_policy_role_permission add constraint fk_reference_21 foreign key (permission_id)
      references t_policy_permission (id) on delete restrict on update restrict;

alter table t_policy_role_permission_data_control add constraint fk_reference_24 foreign key (data_control_id)
      references t_policy_data_control (id) on delete restrict on update restrict;

alter table t_policy_role_permission_data_control add constraint fk_reference_25 foreign key (role_permission_id)
      references t_policy_role_permission (id) on delete restrict on update restrict;

alter table t_policy_shop add constraint fk_reference_27 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_policy_user add constraint fk_reference_26 foreign key (policy_id)
      references t_policy (id) on delete restrict on update restrict;

alter table t_policy_user_role add constraint fk_reference_22 foreign key (role_id)
      references t_policy_role (id) on delete restrict on update restrict;

alter table t_policy_user_role add constraint fk_reference_23 foreign key (user_id)
      references t_policy_user (id) on delete restrict on update restrict;

