SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for magic_api_file
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_file`;
CREATE TABLE `magic_api_file` (
  `file_path` varchar(512) NOT NULL,
  `file_content` mediumtext,
  PRIMARY KEY (`file_path`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of magic_api_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for magic_api_file_backup
-- ----------------------------
DROP TABLE IF EXISTS `magic_api_file_backup`;
CREATE TABLE `magic_api_file_backup` (
  `id` varchar(32) NOT NULL COMMENT '原对象ID',
  `create_date` bigint(13) NOT NULL COMMENT '备份时间',
  `tag` varchar(32) DEFAULT NULL COMMENT '标签',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `name` varchar(64) DEFAULT NULL COMMENT '原名称',
  `content` blob COMMENT '备份内容',
  `create_by` varchar(64) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`,`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of magic_api_file_backup
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE,
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统模块';

-- ----------------------------
-- Records of sys_module
-- ----------------------------
BEGIN;
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (1, '平台管理', '系统平台的核心模块。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (2, '系统管理', '维护企业、系统基础数据，包括部门、岗位、角色、系统菜单和参数等。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (3, '基础信息管理', '维护基础业务数据，包括仓库、客户、供应商、会员等。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (4, '商品中心', '管理商品核心数据。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (5, '采购业务', '管理供应商与企业的采购业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (6, '销售业务', '管理企业与TOB端大客户的销售业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (7, '零售业务', '管理企业与TOC端会员的零售业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (8, '库存管理及调拨', '查询商品库存和进行商品调拨。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (9, '库存盘点', '库存商品盘点。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (10, '库存调整', '商品库存调整。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (11, '结算管理', '企业与供应商、客户进行结算。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (12, '开发管理', '系统快捷开发，如管理定时器、数据实体、数据对象、表单生成等。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (13, '便捷办公', '支持文件收纳和在线Excel。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (14, '开放平台', '系统开放平台管理。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` (`id`, `name`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (15, '物流发货', '管理企业物流业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
COMMIT;

-- ----------------------------
-- Table structure for sys_module_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_module_tenant`;
CREATE TABLE `sys_module_tenant` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `module_id` int(11) NOT NULL COMMENT '模块ID',
  `tenant_id` int(11) NOT NULL COMMENT '租户ID',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`tenant_id`,`module_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户和系统模块关系表';

-- ----------------------------
-- Records of sys_module_tenant
-- ----------------------------
BEGIN;
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875135', 1, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875136', 2, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875137', 3, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875138', 4, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875139', 5, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875140', 6, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875141', 7, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875142', 8, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875143', 9, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875144', 10, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875145', 11, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875146', 12, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875147', 13, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875148', 14, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` (`id`, `module_id`, `tenant_id`, `expire_time`) VALUES ('1666286094246875149', 15, 1000, '2099-01-01 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_open_domain
-- ----------------------------
DROP TABLE IF EXISTS `sys_open_domain`;
CREATE TABLE `sys_open_domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `api_secret` varchar(100) NOT NULL COMMENT 'API秘钥',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `tenant_id` int(11) DEFAULT NULL COMMENT '租户ID',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='开放域';

-- ----------------------------
-- Records of sys_open_domain
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `jdbc_url` varchar(400) NOT NULL COMMENT 'JDBC连接地址',
  `jdbc_username` varchar(200) NOT NULL COMMENT 'JDBC用户名',
  `jdbc_password` varchar(200) NOT NULL COMMENT 'JDBC密码',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='租户';

-- ----------------------------
-- Records of tenant
-- ----------------------------
BEGIN;
INSERT INTO `tenant` (`id`, `name`, `jdbc_url`, `jdbc_username`, `jdbc_password`, `available`, `create_time`, `update_time`) VALUES (1000, '测试租户', 'jdbc:mysql://127.0.0.1:3306/xingyun', 'root', '24590d72bfb4d71ccbc533570aa9627b', 1, '2022-12-31 22:59:21', '2023-03-13 22:14:07');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;