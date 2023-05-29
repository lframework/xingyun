ALTER TABLE `base_data_product` 
ADD COLUMN `product_type` tinyint(3) NOT NULL DEFAULT 1 COMMENT '商品类型' AFTER `brand_id`;

ALTER TABLE `tbl_sale_out_sheet_detail`
ADD COLUMN `ori_bundle_detail_id` varchar(32) NULL COMMENT '组合商品原始明细ID' AFTER `sale_order_detail_id`;
ALTER TABLE `tbl_sale_order_detail`
ADD COLUMN `ori_bundle_detail_id` varchar(32) NULL COMMENT '组合商品原始明细ID' AFTER `out_num`;
ALTER TABLE `tbl_retail_out_sheet_detail`
ADD COLUMN `ori_bundle_detail_id` varchar(32) NULL COMMENT '组合商品原始明细ID' AFTER `return_num`;

DROP TABLE IF EXISTS `base_data_product_bundle`;
CREATE TABLE `base_data_product_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '主商品ID',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `bundle_num` int(11) NOT NULL COMMENT '包含数量',
  `sale_price` decimal(24,2) NOT NULL COMMENT '销售价',
  `retail_price` decimal(24,2) NOT NULL COMMENT '零售价',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `main_product_id` (`main_product_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组合商品';

DROP TABLE IF EXISTS `tbl_sale_out_sheet_detail_bundle`;
CREATE TABLE `tbl_sale_out_sheet_detail_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '出库单ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '组合商品数量',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `product_order_num` int(11) NOT NULL COMMENT '单品数量',
  `product_ori_price` decimal(16,2) NOT NULL COMMENT '单品原价',
  `product_tax_price` decimal(16,2) NOT NULL COMMENT '单品含税价格',
  `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
  `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_detail_id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售出库单组合商品明细';

DROP TABLE IF EXISTS `tbl_sale_order_detail_bundle`;
CREATE TABLE `tbl_sale_order_detail_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '销售单ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '组合商品数量',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `product_order_num` int(11) NOT NULL COMMENT '单品数量',
  `product_ori_price` decimal(16,2) NOT NULL COMMENT '单品原价',
  `product_tax_price` decimal(16,2) NOT NULL COMMENT '单品含税价格',
  `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
  `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`,`product_detail_id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售单组合商品明细';

DROP TABLE IF EXISTS `tbl_retail_out_sheet_detail_bundle`;
CREATE TABLE `tbl_retail_out_sheet_detail_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '出库单ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '组合商品数量',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `product_order_num` int(11) NOT NULL COMMENT '单品数量',
  `product_ori_price` decimal(16,2) NOT NULL COMMENT '单品原价',
  `product_tax_price` decimal(16,2) NOT NULL COMMENT '单品含税价格',
  `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
  `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_detail_id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='零售出库单组合商品明细';