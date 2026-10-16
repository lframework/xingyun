SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 供应商-商品关联表
-- ----------------------------
DROP TABLE IF EXISTS `base_data_supplier_product`;
CREATE TABLE `base_data_supplier_product` (
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
-- 菜单
-- ----------------------------
-- 供应商商品关联管理菜单（归入基础数据模块）
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('2000009', '2000009', 'SupplierProductManage', '供应商商品关联', NULL, 0, '', NULL, '2000', '3', '/supplier/product', 0, 2, 0, 'base:supplier:product:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('2000009001', '2000009001', '', '新增供应商商品', NULL, 0, '', NULL, '2000009', '3', '', 0, 2, 0, 'base:supplier:product:add', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('2000009002', '2000009002', '', '修改供应商商品', NULL, 0, '', NULL, '2000009', '3', '', 0, 2, 0, 'base:supplier:product:modify', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('2000009003', '2000009003', '', '删除供应商商品', NULL, 0, '', NULL, '2000009', '3', '', 0, 2, 0, 'base:supplier:product:delete', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

COMMIT;
SET FOREIGN_KEY_CHECKS = 1;