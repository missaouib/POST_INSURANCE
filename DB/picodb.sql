CREATE DATABASE picodb DEFAULT CHARACTER SET UTF8 COLLATE utf8_general_ci;
FLUSH PRIVILEGES;
CREATE user pico@'localhost' identified by 'db@pico.com';
-- CREATE user pico@'%' identified by 'db@pico.com';
grant all privileges on picodb.* to pico@'localhost';
FLUSH PRIVILEGES;
use picodb;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_component_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_component_resource`;
CREATE TABLE `t_component_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `file` longblob,
  `name` varchar(128) DEFAULT NULL,
  `size` varchar(32) DEFAULT NULL,
  `store_type` varchar(16) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `uuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;


-- ----------------------------
-- Table structure for t_data_control
-- ----------------------------
DROP TABLE IF EXISTS `t_data_control`;
CREATE TABLE `t_data_control` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `control` varchar(10240) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_data_control
-- ----------------------------
INSERT INTO `t_data_control` VALUES ('1', '{\"EQ_user.id\":\"#user.id\"}', null, 'User关联的资源');

-- ----------------------------
-- Table structure for t_log_info
-- ----------------------------
DROP TABLE IF EXISTS `t_log_info`;
CREATE TABLE `t_log_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_time` datetime DEFAULT NULL,
  `ip_address` varchar(16) DEFAULT NULL,
  `log_level` varchar(16) DEFAULT NULL,
  `message` varchar(256) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_log_info
-- ----------------------------
INSERT INTO `t_log_info` VALUES ('1', '2014-09-06 16:57:42', '127.0.0.1', 'TRACE', 'admin登录了系统。', 'admin');


-- ----------------------------
-- Table structure for t_module
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `priority` int(11) NOT NULL,
  `sn` varchar(32) NOT NULL,
  `url` varchar(256) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_sn` (`sn`),
  KEY `FK89A04864953FE581` (`parent_id`),
  CONSTRAINT `FK89A04864953FE581` FOREIGN KEY (`parent_id`) REFERENCES `t_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES ('1', null, '所有模块的根节点，不能删除。', '根模块', '1', 'Root', '#', null);
INSERT INTO `t_module` VALUES ('2', null, '安全管理:包含权限管理、模块管理等。', '安全管理', '999', 'Security', '#', '1');
INSERT INTO `t_module` VALUES ('3', null, null, '用户管理', '999', 'User', '/management/security/user/list', '2');
INSERT INTO `t_module` VALUES ('4', null, null, '角色管理', '999', 'Role', '/management/security/role/list', '2');
INSERT INTO `t_module` VALUES ('5', null, null, '数据权限', '999', 'DataControl', '/management/security/dataControl/list', '2');
INSERT INTO `t_module` VALUES ('6', null, null, '模块管理', '999', 'Module', '/management/security/module/tree_list', '2');
INSERT INTO `t_module` VALUES ('7', null, null, '组织管理', '999', 'Organization', '/management/security/organization/tree_list', '2');
INSERT INTO `t_module` VALUES ('8', null, null, '缓存管理', '998', 'Cache', '/management/security/cache/index', '2');
INSERT INTO `t_module` VALUES ('9', '', '', '日志管理', '999', 'LogInfo', '/management/security/logInfo/list', '2');

-- ----------------------------
-- Table structure for t_organization
-- ----------------------------
DROP TABLE IF EXISTS `t_organization`;
CREATE TABLE `t_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(256) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `org_code` varchar(12) NOT NULL,
  `level` int(1) NOT NULL,
  `priority` int(11) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`),
  KEY `FKCAE6352BF464A488` (`parent_id`),
  CONSTRAINT `FKCAE6352BF464A488` FOREIGN KEY (`parent_id`) REFERENCES `t_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_organization
-- ----------------------------
INSERT INTO `t_organization` VALUES ('1', '不能删除。', '根节点', '999', null);
INSERT INTO `t_organization` VALUES ('2', null, '开发部', '999', '1');
INSERT INTO `t_organization` VALUES ('3', null, '测试部', '999', '1');
INSERT INTO `t_organization` VALUES ('4', null, '运维部', '999', '1');
INSERT INTO `t_organization` VALUES ('5', null, '销售部', '999', '1');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(256) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员。', '管理员');
INSERT INTO `t_role` VALUES ('2', '测试1。', '测试员');
INSERT INTO `t_role` VALUES ('3', '测试员2。', '测试员2');
INSERT INTO `t_role` VALUES ('4', '测试员3。', '测试员3');
INSERT INTO `t_role` VALUES ('5', '测试员4。', '测试员4');

-- ----------------------------
-- Table structure for t_organization_role
-- ----------------------------
DROP TABLE IF EXISTS `t_organization_role`;
CREATE TABLE `t_organization_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD483870AC80E875F` (`organization_id`),
  KEY `FKD483870A3FFD717F` (`role_id`),
  CONSTRAINT `FKD483870A3FFD717F` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKD483870AC80E875F` FOREIGN KEY (`organization_id`) REFERENCES `t_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_organization_role
-- ----------------------------
INSERT INTO `t_organization_role` VALUES ('1', '999', '2', '2');
INSERT INTO `t_organization_role` VALUES ('2', '999', '3', '3');
INSERT INTO `t_organization_role` VALUES ('3', '999', '4', '4');

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(256) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `sn` varchar(16) NOT NULL,
  `module_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCAE8ABC7BD56587F` (`module_id`),
  CONSTRAINT `FKCAE8ABC7BD56587F` FOREIGN KEY (`module_id`) REFERENCES `t_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', null, '看', 'show', '2');
INSERT INTO `t_permission` VALUES ('2', null, '看', 'show', '3');
INSERT INTO `t_permission` VALUES ('3', null, '增', 'save', '3');
INSERT INTO `t_permission` VALUES ('4', null, '删', 'delete', '3');
INSERT INTO `t_permission` VALUES ('5', null, '查', 'view', '3');
INSERT INTO `t_permission` VALUES ('6', null, '改', 'edit', '3');
INSERT INTO `t_permission` VALUES ('7', '重置密码、更新状态', '重置', 'reset', '3');
INSERT INTO `t_permission` VALUES ('8', '分配、撤销角色', '授权', 'assign', '3');
INSERT INTO `t_permission` VALUES ('9', null, '看', 'show', '4');
INSERT INTO `t_permission` VALUES ('10', null, '增', 'save', '4');
INSERT INTO `t_permission` VALUES ('11', null, '删', 'delete', '4');
INSERT INTO `t_permission` VALUES ('12', null, '查', 'view', '4');
INSERT INTO `t_permission` VALUES ('13', null, '改', 'edit', '4');
INSERT INTO `t_permission` VALUES ('14', null, '授权', 'assign', '4');
INSERT INTO `t_permission` VALUES ('15', null, '看', 'show', '5');
INSERT INTO `t_permission` VALUES ('16', null, '增', 'save', '5');
INSERT INTO `t_permission` VALUES ('17', null, '删', 'delete', '5');
INSERT INTO `t_permission` VALUES ('18', null, '查', 'view', '5');
INSERT INTO `t_permission` VALUES ('19', null, '改', 'edit', '5');
INSERT INTO `t_permission` VALUES ('20', null, '看', 'show', '6');
INSERT INTO `t_permission` VALUES ('21', null, '增', 'save', '6');
INSERT INTO `t_permission` VALUES ('22', null, '删', 'delete', '6');
INSERT INTO `t_permission` VALUES ('23', null, '查', 'view', '6');
INSERT INTO `t_permission` VALUES ('24', null, '改', 'edit', '6');
INSERT INTO `t_permission` VALUES ('25', null, '看', 'show', '7');
INSERT INTO `t_permission` VALUES ('26', null, '增', 'save', '7');
INSERT INTO `t_permission` VALUES ('27', null, '删', 'delete', '7');
INSERT INTO `t_permission` VALUES ('28', null, '查', 'view', '7');
INSERT INTO `t_permission` VALUES ('29', null, '改', 'edit', '7');
INSERT INTO `t_permission` VALUES ('30', null, '授权', 'assign', '7');
INSERT INTO `t_permission` VALUES ('31', null, '看', 'show', '8');
INSERT INTO `t_permission` VALUES ('32', null, '删', 'delete', '8');
INSERT INTO `t_permission` VALUES ('33', null, '看', 'show', '9');
INSERT INTO `t_permission` VALUES ('34', null, '删', 'delete', '9');
INSERT INTO `t_permission` VALUES ('35', null, '查', 'view', '9');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9B6EA402BB04F1F` (`permission_id`),
  KEY `FK9B6EA403FFD717F` (`role_id`),
  CONSTRAINT `FK9B6EA402BB04F1F` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`),
  CONSTRAINT `FK9B6EA403FFD717F` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1', '1');
INSERT INTO `t_role_permission` VALUES ('2', '2', '1');
INSERT INTO `t_role_permission` VALUES ('3', '3', '1');
INSERT INTO `t_role_permission` VALUES ('4', '4', '1');
INSERT INTO `t_role_permission` VALUES ('5', '5', '1');
INSERT INTO `t_role_permission` VALUES ('6', '6', '1');
INSERT INTO `t_role_permission` VALUES ('7', '7', '1');
INSERT INTO `t_role_permission` VALUES ('8', '8', '1');
INSERT INTO `t_role_permission` VALUES ('9', '9', '1');
INSERT INTO `t_role_permission` VALUES ('10', '10', '1');
INSERT INTO `t_role_permission` VALUES ('11', '11', '1');
INSERT INTO `t_role_permission` VALUES ('12', '12', '1');
INSERT INTO `t_role_permission` VALUES ('13', '13', '1');
INSERT INTO `t_role_permission` VALUES ('14', '14', '1');
INSERT INTO `t_role_permission` VALUES ('15', '15', '1');
INSERT INTO `t_role_permission` VALUES ('16', '16', '1');
INSERT INTO `t_role_permission` VALUES ('17', '17', '1');
INSERT INTO `t_role_permission` VALUES ('18', '18', '1');
INSERT INTO `t_role_permission` VALUES ('19', '19', '1');
INSERT INTO `t_role_permission` VALUES ('20', '20', '1');
INSERT INTO `t_role_permission` VALUES ('21', '21', '1');
INSERT INTO `t_role_permission` VALUES ('22', '22', '1');
INSERT INTO `t_role_permission` VALUES ('23', '23', '1');
INSERT INTO `t_role_permission` VALUES ('24', '24', '1');
INSERT INTO `t_role_permission` VALUES ('25', '25', '1');
INSERT INTO `t_role_permission` VALUES ('26', '26', '1');
INSERT INTO `t_role_permission` VALUES ('27', '27', '1');
INSERT INTO `t_role_permission` VALUES ('28', '28', '1');
INSERT INTO `t_role_permission` VALUES ('29', '29', '1');
INSERT INTO `t_role_permission` VALUES ('30', '30', '1');
INSERT INTO `t_role_permission` VALUES ('31', '31', '1');
INSERT INTO `t_role_permission` VALUES ('32', '32', '1');
INSERT INTO `t_role_permission` VALUES ('33', '33', '1');
INSERT INTO `t_role_permission` VALUES ('34', '34', '1');
INSERT INTO `t_role_permission` VALUES ('35', '35', '1');
INSERT INTO `t_role_permission` VALUES ('36', '1', '2');
INSERT INTO `t_role_permission` VALUES ('37', '2', '2');
INSERT INTO `t_role_permission` VALUES ('38', '3', '2');
INSERT INTO `t_role_permission` VALUES ('39', '4', '2');
INSERT INTO `t_role_permission` VALUES ('40', '5', '2');
INSERT INTO `t_role_permission` VALUES ('41', '6', '2');
INSERT INTO `t_role_permission` VALUES ('42', '7', '2');
INSERT INTO `t_role_permission` VALUES ('43', '8', '2');
INSERT INTO `t_role_permission` VALUES ('44', '9', '2');
INSERT INTO `t_role_permission` VALUES ('45', '10', '2');
INSERT INTO `t_role_permission` VALUES ('46', '11', '2');
INSERT INTO `t_role_permission` VALUES ('47', '12', '2');
INSERT INTO `t_role_permission` VALUES ('48', '13', '2');
INSERT INTO `t_role_permission` VALUES ('49', '14', '2');
INSERT INTO `t_role_permission` VALUES ('50', '15', '2');
INSERT INTO `t_role_permission` VALUES ('51', '16', '2');
INSERT INTO `t_role_permission` VALUES ('52', '17', '2');
INSERT INTO `t_role_permission` VALUES ('53', '18', '2');
INSERT INTO `t_role_permission` VALUES ('54', '19', '2');
INSERT INTO `t_role_permission` VALUES ('55', '20', '2');
INSERT INTO `t_role_permission` VALUES ('56', '21', '2');
INSERT INTO `t_role_permission` VALUES ('57', '22', '2');
INSERT INTO `t_role_permission` VALUES ('58', '23', '2');
INSERT INTO `t_role_permission` VALUES ('59', '24', '2');
INSERT INTO `t_role_permission` VALUES ('60', '25', '2');
INSERT INTO `t_role_permission` VALUES ('61', '26', '2');
INSERT INTO `t_role_permission` VALUES ('62', '27', '2');
INSERT INTO `t_role_permission` VALUES ('63', '28', '2');
INSERT INTO `t_role_permission` VALUES ('64', '29', '2');
INSERT INTO `t_role_permission` VALUES ('65', '30', '2');
INSERT INTO `t_role_permission` VALUES ('66', '31', '2');
INSERT INTO `t_role_permission` VALUES ('67', '32', '2');
INSERT INTO `t_role_permission` VALUES ('68', '33', '2');
INSERT INTO `t_role_permission` VALUES ('69', '34', '2');
INSERT INTO `t_role_permission` VALUES ('70', '35', '2');

-- ----------------------------
-- Table structure for t_role_permission_data_control
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission_data_control`;
CREATE TABLE `t_role_permission_data_control` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_control_id` bigint(20) DEFAULT NULL,
  `role_permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB2EFD96750319A20` (`data_control_id`),
  KEY `FKB2EFD96758F6F1AC` (`role_permission_id`),
  CONSTRAINT `FKB2EFD96750319A20` FOREIGN KEY (`data_control_id`) REFERENCES `t_data_control` (`id`),
  CONSTRAINT `FKB2EFD96758F6F1AC` FOREIGN KEY (`role_permission_id`) REFERENCES `t_role_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_role_permission_data_control
-- ----------------------------
INSERT INTO `t_role_permission_data_control` VALUES ('1', '1', '68');
INSERT INTO `t_role_permission_data_control` VALUES ('2', '1', '69');
INSERT INTO `t_role_permission_data_control` VALUES ('3', '1', '70');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATE_time` datetime DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `pwd_time` datetime DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `realname` varchar(32) NOT NULL,
  `salt` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL,
  `username` varchar(32) NOT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`username`),
  KEY `FK97BBABC3C80E875F` (`organization_id`),
  CONSTRAINT `FK97BBABC3C80E875F` FOREIGN KEY (`organization_id`) REFERENCES `t_organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '2014-08-03 14:58:38', 'test@gmail.com', 'f0850817aee6fcd981ec4578314ee3bc8afdc61c', '110', '为人民服务', 'f40eaf1c6ec3efaf', 'enabled', 'admin', '2');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24086F723FFD717F` (`role_id`),
  KEY `FK24086F72E528355F` (`user_id`),
  CONSTRAINT `FK24086F723FFD717F` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK24086F72E528355F` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=UTF8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '999', '1', '1');

drop table if exists t_category_org;

create table t_category_org
(
   id                   bigint(20) not null auto_increment,
   org_id               bigint(20),
   category_id          bigint(20), 
   primary key (id),
   unique key unique_name (org_id,category_id)
);

DROP TABLE IF EXISTS `t_Province`;
CREATE TABLE t_Province(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	province_code varchar(6) NOT NULL,
	province_name varchar(50) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `t_City`;
CREATE TABLE t_City(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	city_code varchar(6) NOT NULL,
	city_name varchar(50) NOT NULL,
	province_code varchar(6) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `t_Area`;
CREATE TABLE t_Area(
	id bigint(20) NOT NULL AUTO_INCREMENT,
	area_code varchar(6) NOT NULL,
	area_name varchar(50) NOT NULL,
	city_code varchar(6) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8;