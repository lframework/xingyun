-- ----------------------------
-- CRM 模块：线索、跟进记录、商机
-- 菜单 7000 段，sys_module_id=18（CRM客户关系管理）
-- ----------------------------

-- 线索表
DROP TABLE IF EXISTS `crm_clue`;
CREATE TABLE `crm_clue` (
  `id`           varchar(32)    NOT NULL COMMENT 'ID',
  `code`         varchar(32)    NOT NULL COMMENT '线索编号',
  `contact_name` varchar(64)    NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(32)   NOT NULL COMMENT '联系电话',
  `company_name` varchar(128)   DEFAULT NULL COMMENT '公司名称',
  `source`       tinyint(3)     DEFAULT 0 COMMENT '线索来源（0-手动录入 1-电话咨询 2-网络推广 3-展会 4-转介绍 5-其他）',
  `owner_id`     varchar(32)    DEFAULT NULL COMMENT '负责销售员ID',
  `status`       tinyint(3)     NOT NULL DEFAULT 0 COMMENT '状态（0-待跟进 1-跟进中 2-已转化 3-无效）',
  `description`  varchar(500)   DEFAULT NULL COMMENT '备注',
  `create_by`    varchar(32)    NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32)    NOT NULL COMMENT '创建人ID',
  `create_time`  datetime       NOT NULL COMMENT '创建时间',
  `update_by`    varchar(32)    NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32)    NOT NULL COMMENT '修改人ID',
  `update_time`  datetime       NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_owner_id` (`owner_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售线索';

-- 跟进记录表
DROP TABLE IF EXISTS `crm_follow_up`;
CREATE TABLE `crm_follow_up` (
  `id`               varchar(32)  NOT NULL COMMENT 'ID',
  `clue_id`          varchar(32)  DEFAULT NULL COMMENT '关联线索ID',
  `opportunity_id`   varchar(32)  DEFAULT NULL COMMENT '关联商机ID',
  `follow_type`      tinyint(3)   DEFAULT 0 COMMENT '跟进方式（0-电话 1-邮件 2-上门拜访 3-微信/企业微信 4-其他）',
  `content`          varchar(2000) NOT NULL COMMENT '跟进内容',
  `next_follow_date` date         DEFAULT NULL COMMENT '下次跟进日期',
  `create_by`        varchar(32)  NOT NULL COMMENT '跟进人',
  `create_by_id`     varchar(32)  NOT NULL COMMENT '跟进人ID',
  `create_time`      datetime     NOT NULL COMMENT '跟进时间',
  `update_by`        varchar(32)  NOT NULL COMMENT '修改人',
  `update_by_id`     varchar(32)  NOT NULL COMMENT '修改人ID',
  `update_time`      datetime     NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_clue_id` (`clue_id`),
  KEY `idx_opportunity_id` (`opportunity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='跟进记录';

-- 商机表
DROP TABLE IF EXISTS `crm_opportunity`;
CREATE TABLE `crm_opportunity` (
  `id`                  varchar(32)    NOT NULL COMMENT 'ID',
  `code`                varchar(32)    NOT NULL COMMENT '商机编号',
  `name`                varchar(128)   NOT NULL COMMENT '商机名称',
  `clue_id`             varchar(32)    DEFAULT NULL COMMENT '来源线索ID',
  `customer_id`         varchar(32)    DEFAULT NULL COMMENT '关联客户ID',
  `owner_id`            varchar(32)    DEFAULT NULL COMMENT '负责销售员ID',
  `estimated_amount`    decimal(16,2)  DEFAULT NULL COMMENT '预计金额',
  `expected_close_date` date           DEFAULT NULL COMMENT '预计成交日期',
  `stage`               tinyint(3)     NOT NULL DEFAULT 0 COMMENT '阶段（0-初步接触 1-需求确认 2-方案报价 3-商务谈判 4-赢单 5-输单）',
  `status`              tinyint(3)     NOT NULL DEFAULT 0 COMMENT '状态（0-进行中 1-赢单 2-输单）',
  `description`         varchar(500)   DEFAULT NULL COMMENT '备注',
  `create_by`           varchar(32)    NOT NULL COMMENT '创建人',
  `create_by_id`        varchar(32)    NOT NULL COMMENT '创建人ID',
  `create_time`         datetime       NOT NULL COMMENT '创建时间',
  `update_by`           varchar(32)    NOT NULL COMMENT '修改人',
  `update_by_id`        varchar(32)    NOT NULL COMMENT '修改人ID',
  `update_time`         datetime       NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_owner_id` (`owner_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售商机';

-- 系统模块（CRM）
INSERT INTO `sys_module` (`id`, `name`, `icon`, `description`, `available`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`)
VALUES (18, 'CRM客户关系管理', 'crm', 'CRM客户关系管理模块', 1, '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- 菜单：CRM 顶级目录
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('7000', '7000', 'Crm', 'CRM管理', 'crm', 0, '', NULL, '0', '18', '/crm', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- 菜单：线索管理
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('7000001', '7000001', 'CrmClue', '线索管理', NULL, 0, '/crm/clue/index', NULL, '7000', '18', '/clue', 0, 1, 0, 'crm:clue:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000001001', '7000001001', '', '新增线索', NULL, 0, '', NULL, '7000001', '18', '', 0, 2, 0, 'crm:clue:add', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000001002', '7000001002', '', '修改线索', NULL, 0, '', NULL, '7000001', '18', '', 0, 2, 0, 'crm:clue:modify', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000001003', '7000001003', '', '删除线索', NULL, 0, '', NULL, '7000001', '18', '', 0, 2, 0, 'crm:clue:delete', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000001004', '7000001004', '', '转化线索', NULL, 0, '', NULL, '7000001', '18', '', 0, 2, 0, 'crm:clue:convert', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- 菜单：商机管理
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('7000002', '7000002', 'CrmOpportunity', '商机管理', NULL, 0, '/crm/opportunity/index', NULL, '7000', '18', '/opportunity', 0, 1, 0, 'crm:opportunity:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000002001', '7000002001', '', '新增商机', NULL, 0, '', NULL, '7000002', '18', '', 0, 2, 0, 'crm:opportunity:add', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000002002', '7000002002', '', '修改商机', NULL, 0, '', NULL, '7000002', '18', '', 0, 2, 0, 'crm:opportunity:modify', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000002003', '7000002003', '', '删除商机', NULL, 0, '', NULL, '7000002', '18', '', 0, 2, 0, 'crm:opportunity:delete', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000002004', '7000002004', '', '关闭商机', NULL, 0, '', NULL, '7000002', '18', '', 0, 2, 0, 'crm:opportunity:close', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('7000002005', '7000002005', '', '转化销售订单', NULL, 0, '', NULL, '7000002', '18', '', 0, 2, 0, 'crm:opportunity:convert', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- 单据编号规则
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES
(500, '线索编号', '[{"type":6,"val":"XS"},{"type":1,"pattern":"yyyyMMdd"},{"type":3,"key":"crm_clue_code_seq_key_20240101","len":5,"step":1,"expireSeconds":86400}]'),
(501, '商机编号', '[{"type":6,"val":"SJ"},{"type":1,"pattern":"yyyyMMdd"},{"type":3,"key":"crm_opp_code_seq_key_20240101","len":5,"step":1,"expireSeconds":86400}]');

COMMIT;
