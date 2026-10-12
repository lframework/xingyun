SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 生产管理模块
-- ----------------------------

-- BOM 主表
DROP TABLE IF EXISTS `bom_bom`;
CREATE TABLE `bom_bom` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT 'BOM编号',
  `name` varchar(100) NOT NULL COMMENT 'BOM名称',
  `main_product_id` varchar(32) NOT NULL COMMENT '成品/半成品商品ID',
  `main_sku_id` varchar(32) NOT NULL COMMENT '成品SKU ID',
  `output_num` decimal(16,4) NOT NULL DEFAULT 1 COMMENT '产出数量',
  `scrap_rate` decimal(5,2) NOT NULL DEFAULT 0 COMMENT '废品率%',
  `status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '状态 0-草稿 1-启用 2-停用',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认版本',
  `version` varchar(20) NOT NULL DEFAULT '1.0' COMMENT '版本',
  `description` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `main_sku` (`main_sku_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM清单';

-- BOM 明细表
DROP TABLE IF EXISTS `bom_bom_detail`;
CREATE TABLE `bom_bom_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `bom_id` varchar(32) NOT NULL COMMENT 'BOM ID',
  `product_id` varchar(32) NOT NULL COMMENT '子件商品ID',
  `sku_id` varchar(32) NOT NULL COMMENT '子件SKU ID',
  `unit_num` decimal(16,6) NOT NULL COMMENT '单位用量',
  `scrap_rate` decimal(5,2) NOT NULL DEFAULT 0 COMMENT '损耗率%',
  `is_key_material` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否关键物料',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `description` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bom_sku` (`bom_id`, `sku_id`),
  KEY `sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM明细';

-- ----------------------------
-- 菜单
-- ----------------------------
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('6000', '6000', 'Produce', '生产管理', 'a-menu', NULL, '', NULL, '0001', '17', '/produce', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39'),
('6000001', '6000001', 'BomManage', 'BOM管理', NULL, 0, '/produce/bom/index', NULL, '6000', '17', '/bom', 0, 1, 0, 'produce:bom:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42'),
('6000001001', '6000001001', '', '新增BOM', NULL, 0, '', NULL, '6000001', '17', '', 0, 2, 0, 'produce:bom:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-12-09 17:54:42'),
('6000001002', '6000001002', '', '修改BOM', NULL, 0, '', NULL, '6000001', '17', '', 0, 2, 0, 'produce:bom:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42'),
('6000001003', '6000001003', '', '删除BOM', NULL, 0, '', NULL, '6000001', '17', '', 0, 2, 0, 'produce:bom:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23'),
('6000001004', '6000001004', '', '审核BOM', NULL, 0, '', NULL, '6000001', '17', '', 0, 2, 0, 'produce:bom:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23'),
('6000001005', '6000001005', '', '停用BOM', NULL, 0, '', NULL, '6000001', '17', '', 0, 2, 0, 'produce:bom:disable', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23'),
('6000001006', '6000001006', '', '导出BOM', NULL, 0, '', NULL, '6000001', '17', '', 0, 2, 0, 'produce:bom:export', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');

-- ----------------------------
-- 单号规则
-- ----------------------------
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES
(400, 'BOM编号', '[{\"type\":6,\"val\":\"B\"},{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"8f3c9a2b1d4e5f6a7b8c9d0e1f2a3b4c\",\"len\":5,\"step\":1,\"expireSeconds\":86400}]');

COMMIT;
SET FOREIGN_KEY_CHECKS = 1;
