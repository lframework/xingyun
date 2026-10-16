SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 供应商-商品关联表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `base_data_supplier_product` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `sku_id` varchar(32) DEFAULT NULL COMMENT 'SKU ID（null表示商品下全部SKU）',
  `supplier_product_code` varchar(100) DEFAULT '' COMMENT '供应商商品编号',
  `supplier_product_name` varchar(200) DEFAULT '' COMMENT '供应商商品名称',
  `default_purchase_price` decimal(16,6) DEFAULT NULL COMMENT '默认采购价',
  `min_order_qty` decimal(16,4) DEFAULT NULL COMMENT '最小起订量',
  `lead_time_days` int(11) DEFAULT NULL COMMENT '供货周期（天）',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认供应商',
  `available` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 1-启用 0-停用',
  `description` varchar(500) DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_supplier` (`supplier_id`, `available`),
  KEY `idx_product` (`product_id`, `available`),
  UNIQUE KEY `uk_supplier_sku` (`supplier_id`, `product_id`, `sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商商品关联';

-- ----------------------------
-- 菜单（2000012 系列，挂在基础信息管理 2000 下）
-- ----------------------------
INSERT IGNORE INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('2000012', '2000012', 'SupplierProductManage', '供应商商品关联', NULL, 0, '/base-data/supplier/product/index', NULL, '2000', '3', '/supplier/product', 0, 1, 0, 'base-data:supplier:product:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('2000012001', '2000012001', '', '新增供应商商品', NULL, 0, '', NULL, '2000012', '3', '', 0, 2, 0, 'base-data:supplier:product:add', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('2000012002', '2000012002', '', '修改供应商商品', NULL, 0, '', NULL, '2000012', '3', '', 0, 2, 0, 'base-data:supplier:product:modify', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('2000012003', '2000012003', '', '删除供应商商品', NULL, 0, '', NULL, '2000012', '3', '', 0, 2, 0, 'base-data:supplier:product:delete', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- ----------------------------
-- 角色授权（采购员 / 采购审批人）
-- ----------------------------
INSERT IGNORE INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
('SP21106285172585963521', '2110628517258596352', '2000012'),
('SP21106285172585963522', '2110628517258596352', '2000012001'),
('SP21106285172585963523', '2110628517258596352', '2000012002'),
('SP21106285172585963524', '2110628517258596352', '2000012003'),
('SP21106305897900318721', '2110630589790031872', '2000012'),
('SP21106305897900318722', '2110630589790031872', '2000012001'),
('SP21106305897900318723', '2110630589790031872', '2000012002'),
('SP21106305897900318724', '2110630589790031872', '2000012003');

COMMIT;
SET FOREIGN_KEY_CHECKS = 1;
