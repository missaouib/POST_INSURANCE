SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Records of t_data_control
-- ----------------------------
truncate t_data_control;
INSERT INTO `t_data_control` VALUES ('1', '{\"EQ_user.id\":\"#user.id\"}', null, 'User关联的资源');

-- ----------------------------
-- Records of t_log_info
-- ----------------------------
truncate t_log_info;
INSERT INTO `t_log_info` VALUES ('1', '2015-04-06 16:57:42', '127.0.0.1', 'TRACE', 'admin登录了系统。', 'admin');

-- ----------------------------
-- Records of t_module
-- ----------------------------
truncate t_module;
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
-- Records of t_organization
-- ----------------------------
truncate t_organization;
INSERT INTO `t_organization` VALUES ('1', '不能删除。', '中邮人寿保险股份有限公司', '86', 1, '999', null);
INSERT INTO `t_organization` VALUES ('2', '省分公司。', '中邮人寿保险股份有限公司广东分公司', '8644', 2, '999', null);
INSERT INTO `t_organization` VALUES ('3', null, '广州市中邮保险局', '864401', 3, '999', '2');

-- ----------------------------
-- Records of t_role
-- ----------------------------
truncate t_role;
INSERT INTO `t_role` VALUES ('1', '管理员。', '管理员');

-- ----------------------------
-- Records of t_organization_role
-- ----------------------------
truncate t_organization_role;
INSERT INTO `t_organization_role` VALUES ('1', '999', '1', '1');
INSERT INTO `t_organization_role` VALUES ('2', '999', '2', '1');
INSERT INTO `t_organization_role` VALUES ('3', '999', '3', '1');

-- ----------------------------
-- Records of t_permission
-- ----------------------------
truncate t_permission;
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
-- Records of t_role_permission
-- ----------------------------
truncate t_role_permission;
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

-- ----------------------------
-- Records of t_role_permission_data_control
-- ----------------------------
truncate t_role_permission_data_control;
INSERT INTO `t_role_permission_data_control` VALUES ('1', '1', '2');
INSERT INTO `t_role_permission_data_control` VALUES ('2', '1', '3');
INSERT INTO `t_role_permission_data_control` VALUES ('3', '1', '4');
INSERT INTO `t_role_permission_data_control` VALUES ('4', '1', '5');
INSERT INTO `t_role_permission_data_control` VALUES ('5', '1', '6');
INSERT INTO `t_role_permission_data_control` VALUES ('6', '1', '7');
INSERT INTO `t_role_permission_data_control` VALUES ('7', '1', '8');

-- ----------------------------
-- Records of t_user
-- ----------------------------
truncate t_user;
INSERT INTO `t_user` VALUES ('1', '2014-08-03 14:58:38', 'test@gmail.com', 'f0850817aee6fcd981ec4578314ee3bc8afdc61c', null, '02038181638', '为邮政保险服务', 'f40eaf1c6ec3efaf', 'enabled', 'admin', '2');

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
truncate t_user_role;
INSERT INTO `t_user_role` VALUES ('1', '999', '1', '1');
