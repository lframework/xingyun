-- ----------------------------
-- 生产管理：组装单
-- 菜单 6000002 段，sys_module_id=17（生产管理）
-- ----------------------------

-- 组装单主表
DROP TABLE IF EXISTS `tbl_assemble_sheet`;
CREATE TABLE `tbl_assemble_sheet` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '业务单据号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID（子件出库仓、成品入库仓）',
  `bom_id` varchar(32) NOT NULL COMMENT 'BOM ID',
  `product_id` varchar(32) NOT NULL COMMENT '成品商品ID',
  `sku_id` varchar(32) NOT NULL COMMENT '成品SKU ID',
  `total_num` decimal(16, 8) NOT NULL COMMENT '组装数量（成品数量）',
  `status` tinyint(3) NOT NULL COMMENT '状态（0-待审核 3-审核通过 6-审核拒绝）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `sc_id` (`sc_id`),
  KEY `bom_id` (`bom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组装单';

-- 组装单明细（子件）
DROP TABLE IF EXISTS `tbl_assemble_sheet_detail`;
CREATE TABLE `tbl_assemble_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '单据ID',
  `product_id` varchar(32) NOT NULL COMMENT '子件商品ID',
  `sku_id` varchar(32) NOT NULL COMMENT '子件SKU ID',
  `num` decimal(16, 8) NOT NULL COMMENT '单位用量（自BOM带出）',
  `stock_num` decimal(16, 8) NOT NULL COMMENT '本次消耗数量（= 单位用量 × 组装数量）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sheet_id_sku_id` (`sheet_id`,`sku_id`),
  KEY `sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组装单明细';

-- 菜单
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('6000002', '6000002', 'AssembleSheet', '组装单', NULL, 0, '/produce/assemble/index', NULL, '6000', '17', '/assemble', 0, 1, 0, 'produce:assemble:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('6000002001', '6000002001', '', '新增组装单', NULL, 0, '', NULL, '6000002', '17', '', 0, 2, 0, 'produce:assemble:add', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('6000002002', '6000002002', '', '修改组装单', NULL, 0, '', NULL, '6000002', '17', '', 0, 2, 0, 'produce:assemble:modify', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('6000002003', '6000002003', '', '删除组装单', NULL, 0, '', NULL, '6000002', '17', '', 0, 2, 0, 'produce:assemble:delete', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('6000002004', '6000002004', '', '审核组装单', NULL, 0, '', NULL, '6000002', '17', '', 0, 2, 0, 'produce:assemble:approve', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('6000002005', '6000002005', '', '导出组装单', NULL, 0, '', NULL, '6000002', '17', '', 0, 2, 0, 'produce:assemble:export', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- 单据编号规则（组装单）
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES
(215, '组装单号', '[{\"type\":6,\"val\":\"ZD\"},{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"7a6b5c4d3e2f1a0b9c8d7e6f5a4b3c2d\",\"len\":5,\"step\":1,\"expireSeconds\":86400}]');

COMMIT;
