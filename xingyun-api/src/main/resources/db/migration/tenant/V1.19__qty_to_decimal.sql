ALTER TABLE `tbl_purchase_order_detail`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `receive_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`,
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '含税总金额' AFTER `receive_num`;

ALTER TABLE `tbl_purchase_order_detail_form`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `receive_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`,
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '含税总金额' AFTER `receive_num`;

ALTER TABLE `tbl_purchase_order`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `expect_arrive_date`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `total_num`;

ALTER TABLE `tbl_purchase_order_form`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `expect_arrive_date`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `total_num`;

ALTER TABLE `tbl_receive_sheet`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `purchase_order_id`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_receive_sheet_detail`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `order_no`,
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '采购总金额' AFTER `purchase_order_detail_id`;

ALTER TABLE `tbl_product_stock`
    MODIFY COLUMN `stock_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '库存数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL DEFAULT 0 COMMENT '含税价格' AFTER `stock_num`;

ALTER TABLE `tbl_product_stock_log`
    MODIFY COLUMN `ori_stock_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '变动前库存数量' AFTER `cur_tax_price`,
    MODIFY COLUMN `cur_stock_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '变动后库存数量' AFTER `ori_stock_num`,
    MODIFY COLUMN `stock_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '变动库存数量' AFTER `cur_stock_num`;

ALTER TABLE `base_data_product_purchase`
    MODIFY COLUMN `price` decimal(16, 6) NOT NULL COMMENT '采购价' AFTER `id`;

ALTER TABLE `base_data_product_sale`
    MODIFY COLUMN `price` decimal(16, 6) NOT NULL COMMENT '销售价' AFTER `id`;

ALTER TABLE `base_data_product_retail`
    MODIFY COLUMN `price` decimal(16, 6) NOT NULL COMMENT '零售价' AFTER `id`;

ALTER TABLE `base_data_product_bundle`
    MODIFY COLUMN `sale_price` decimal(16, 6) NOT NULL COMMENT '销售价' AFTER `bundle_num`,
    MODIFY COLUMN `retail_price` decimal(16, 6) NOT NULL COMMENT '零售价' AFTER `sale_price`;

ALTER TABLE `tbl_purchase_return`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `receive_sheet_id`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_purchase_return_detail`
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL COMMENT '退货数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL COMMENT '采购价' AFTER `return_num`;

ALTER TABLE `tbl_purchase_return_detail`
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '退货总金额' AFTER `receive_sheet_detail_id`;

ALTER TABLE `tbl_sc_transfer_order`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '调拨数量' AFTER `target_sc_id`;
ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `transfer_num` decimal(16, 8) NOT NULL COMMENT '调拨数量' AFTER `product_id`,
    MODIFY COLUMN `receive_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`;

ALTER TABLE `tbl_sc_transfer_order_detail_receive`
    MODIFY COLUMN `receive_num` decimal(16, 8) NOT NULL COMMENT '收货数量' AFTER `detail_id`;

ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `tax_price` decimal(16, 6) NULL DEFAULT NULL COMMENT '成本价' AFTER `transfer_num`;

ALTER TABLE `tbl_sc_transfer_order_detail`
    ADD COLUMN `transfer_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '调拨金额' AFTER `transfer_num`,
ADD COLUMN `receive_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '已收货金额' AFTER `receive_num`;
ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `tax_price` decimal(16, 6) NULL COMMENT '成本价' AFTER `transfer_amount`;

ALTER TABLE `tbl_sc_transfer_order_detail_receive`
    ADD COLUMN `receive_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '收货金额' AFTER `receive_num`;

update tbl_sc_transfer_order_detail_receive r
    join tbl_sc_transfer_order_detail d on d.id = r.detail_id
    set r.receive_amount = r.receive_num * d.transfer_amount / d.transfer_num
where d.tax_price is not null;

ALTER TABLE `tbl_product_stock_warning`
    MODIFY COLUMN `max_limit` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '预警上限' AFTER `product_id`,
    MODIFY COLUMN `min_limit` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '预警下限' AFTER `max_limit`;

ALTER TABLE `tbl_stock_adjust_sheet_detail`
    MODIFY COLUMN `stock_num` decimal(16, 8) NOT NULL COMMENT '调整库存数量' AFTER `product_id`;

ALTER TABLE `tbl_sale_order`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '销售数量' AFTER `saler_id`,
    MODIFY COLUMN `total_gift_num` decimal(24) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_sale_order_detail`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL COMMENT '销售数量' AFTER `product_id`,
    MODIFY COLUMN `out_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已出库数量' AFTER `order_no`;

ALTER TABLE `tbl_sale_order_detail_bundle`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_order_num` decimal(16, 8) NOT NULL COMMENT '单品数量' AFTER `product_id`;

ALTER TABLE `tbl_sale_order_detail_bundle`
    MODIFY COLUMN `product_ori_price` decimal(16, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(16, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`;

ALTER TABLE `tbl_sale_order_detail`
    MODIFY COLUMN `ori_price` decimal(16, 6) NOT NULL COMMENT '原价' AFTER `order_num`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_sale_out_sheet`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `sale_order_id`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_sale_out_sheet_detail`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL COMMENT '出库数量' AFTER `product_id`,
    MODIFY COLUMN `ori_price` decimal(16, 6) NOT NULL COMMENT '原价' AFTER `order_num`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL COMMENT '现价' AFTER `ori_price`,
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `ori_bundle_detail_id`;

ALTER TABLE `tbl_sale_out_sheet_detail_bundle`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_order_num` decimal(16, 8) NOT NULL COMMENT '单品数量' AFTER `product_id`,
    MODIFY COLUMN `product_ori_price` decimal(16, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(16, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`;

ALTER TABLE `tbl_sale_out_sheet_detail_lot`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL COMMENT '出库数量' AFTER `detail_id`,
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `order_num`;

ALTER TABLE `tbl_sale_return`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `out_sheet_id`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_sale_return_detail`
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL COMMENT '退货数量' AFTER `product_id`,
    MODIFY COLUMN `ori_price` decimal(16, 6) NOT NULL COMMENT '原价' AFTER `return_num`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_retail_out_sheet`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `payment_date`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_retail_out_sheet_detail`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL COMMENT '出库数量' AFTER `product_id`,
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `settle_status`;

ALTER TABLE `tbl_retail_out_sheet_detail`
    MODIFY COLUMN `ori_price` decimal(16, 6) NOT NULL COMMENT '原价' AFTER `order_num`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_retail_out_sheet_detail_bundle`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_ori_price` decimal(16, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(16, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`;

ALTER TABLE `tbl_retail_out_sheet_detail_lot`
    MODIFY COLUMN `order_num` decimal(16, 8) NOT NULL COMMENT '出库数量' AFTER `detail_id`,
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `order_num`;

ALTER TABLE `tbl_retail_return`
    MODIFY COLUMN `total_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `out_sheet_id`,
    MODIFY COLUMN `total_gift_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_retail_return_detail`
    MODIFY COLUMN `return_num` decimal(16, 8) NOT NULL COMMENT '退货数量' AFTER `product_id`,
    MODIFY COLUMN `ori_price` decimal(16, 6) NOT NULL COMMENT '原价' AFTER `return_num`,
    MODIFY COLUMN `tax_price` decimal(16, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_pre_take_stock_sheet_detail`
    MODIFY COLUMN `first_num` decimal(16, 8) NULL DEFAULT NULL COMMENT '初盘数量' AFTER `product_id`,
    MODIFY COLUMN `second_num` decimal(16, 8) NULL DEFAULT NULL COMMENT '复盘数量' AFTER `first_num`,
    MODIFY COLUMN `rand_num` decimal(16, 8) NULL DEFAULT NULL COMMENT '抽盘数量' AFTER `second_num`;

ALTER TABLE `tbl_take_stock_plan_detail`
    MODIFY COLUMN `stock_num` decimal(16, 8) NOT NULL COMMENT '初始库存数量' AFTER `product_id`,
    MODIFY COLUMN `ori_take_num` decimal(16, 8) NULL DEFAULT NULL COMMENT '盘点数量' AFTER `stock_num`,
    MODIFY COLUMN `take_num` decimal(16, 8) NULL DEFAULT NULL COMMENT '修改后的盘点数量' AFTER `ori_take_num`,
    MODIFY COLUMN `total_out_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '出项数量' AFTER `take_num`,
    MODIFY COLUMN `total_in_num` decimal(16, 8) NOT NULL DEFAULT 0 COMMENT '入项数量' AFTER `total_out_num`;

ALTER TABLE `tbl_take_stock_sheet_detail`
    MODIFY COLUMN `take_num` decimal(16, 8) NOT NULL COMMENT '盘点数量' AFTER `product_id`;

ALTER TABLE `base_data_product_bundle`
    ADD COLUMN `purchase_price` decimal(16, 6) NOT NULL COMMENT '采购价' AFTER `bundle_num`;

CREATE TABLE `tbl_purchase_order_detail_bundle` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `order_id` varchar(32) NOT NULL COMMENT '采购单ID',
    `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
    `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
    `order_num` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '组合商品数量',
    `product_id` varchar(32) NOT NULL COMMENT '单品ID',
    `product_order_num` decimal(16,8) NOT NULL COMMENT '单品数量',
    `product_ori_price` decimal(16,6) NOT NULL COMMENT '单品原价',
    `product_tax_price` decimal(16,6) NOT NULL COMMENT '单品含税价格',
    `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
    `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `order_id` (`order_id`,`product_detail_id`) USING BTREE,
    KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购单组合商品明细';

CREATE TABLE `tbl_purchase_order_detail_bundle_form` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `order_id` varchar(32) NOT NULL COMMENT '采购单ID',
    `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
    `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
    `order_num` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '组合商品数量',
    `product_id` varchar(32) NOT NULL COMMENT '单品ID',
    `product_order_num` decimal(16,8) NOT NULL COMMENT '单品数量',
    `product_ori_price` decimal(16,6) NOT NULL COMMENT '单品原价',
    `product_tax_price` decimal(16,6) NOT NULL COMMENT '单品含税价格',
    `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
    `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `order_id` (`order_id`,`product_detail_id`) USING BTREE,
    KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购单组合商品明细';

CREATE TABLE `tbl_receive_sheet_detail_bundle` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `sheet_id` varchar(32) NOT NULL COMMENT '收货单ID',
    `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
    `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
    `order_num` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '组合商品数量',
    `product_id` varchar(32) NOT NULL COMMENT '单品ID',
    `product_order_num` decimal(16,8) NOT NULL COMMENT '单品数量',
    `product_ori_price` decimal(16,6) NOT NULL COMMENT '单品原价',
    `product_tax_price` decimal(16,6) NOT NULL COMMENT '单品含税价格',
    `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
    `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sheet_id` (`sheet_id`,`product_detail_id`) USING BTREE,
    KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购收货单组合商品明细';

ALTER TABLE `tbl_sale_order_detail`
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL COMMENT '总金额' AFTER `ori_bundle_detail_id`;

ALTER TABLE `tbl_sale_out_sheet_detail`
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL COMMENT '总金额' AFTER `return_num`;

ALTER TABLE `tbl_sale_return_detail`
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL COMMENT '总金额' AFTER `out_sheet_detail_id`;

ALTER TABLE `tbl_retail_out_sheet_detail`
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL COMMENT '总金额' AFTER `ori_bundle_detail_id`;

ALTER TABLE `tbl_retail_return_detail`
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL COMMENT '总金额' AFTER `out_sheet_detail_id`;

ALTER TABLE `tbl_purchase_order_detail_bundle`
    ADD COLUMN `product_tax_amount` decimal(24, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_purchase_order_detail_bundle_form`
    ADD COLUMN `product_tax_amount` decimal(24, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;


ALTER TABLE `tbl_receive_sheet_detail_bundle`
    ADD COLUMN `product_tax_amount` decimal(24, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_retail_out_sheet_detail_bundle`
    ADD COLUMN `product_tax_amount` decimal(24, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_sale_order_detail_bundle`
    ADD COLUMN `product_tax_amount` decimal(24, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_sale_out_sheet_detail_bundle`
    ADD COLUMN `product_tax_amount` decimal(24, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_purchase_order_detail`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `receive_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`;
ALTER TABLE `tbl_purchase_order_detail_form`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `receive_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`;
ALTER TABLE `tbl_purchase_order`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `expect_arrive_date`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `total_num`;
ALTER TABLE `tbl_purchase_order_form`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `expect_arrive_date`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `total_num`;
ALTER TABLE `tbl_receive_sheet`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `purchase_order_id`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_receive_sheet_detail`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `order_no`,
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0 COMMENT '采购总金额' AFTER `purchase_order_detail_id`;

ALTER TABLE `tbl_product_stock`
    MODIFY COLUMN `stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '库存数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '含税价格' AFTER `stock_num`;
ALTER TABLE `tbl_product_stock`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL COMMENT '含税金额' AFTER `tax_price`;

ALTER TABLE `tbl_product_stock_log`
    MODIFY COLUMN `ori_stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '变动前库存数量' AFTER `cur_tax_price`,
    MODIFY COLUMN `cur_stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '变动后库存数量' AFTER `ori_stock_num`,
    MODIFY COLUMN `stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '变动库存数量' AFTER `cur_stock_num`;

ALTER TABLE `base_data_product_purchase`
    MODIFY COLUMN `price` decimal(24, 6) NOT NULL COMMENT '采购价' AFTER `id`;

ALTER TABLE `base_data_product_sale`
    MODIFY COLUMN `price` decimal(24, 6) NOT NULL COMMENT '销售价' AFTER `id`;

ALTER TABLE `base_data_product_retail`
    MODIFY COLUMN `price` decimal(24, 6) NOT NULL COMMENT '零售价' AFTER `id`;

ALTER TABLE `base_data_product_bundle`
    MODIFY COLUMN `sale_price` decimal(24, 6) NOT NULL COMMENT '销售价' AFTER `bundle_num`,
    MODIFY COLUMN `retail_price` decimal(24, 6) NOT NULL COMMENT '零售价' AFTER `sale_price`;

ALTER TABLE `tbl_purchase_return`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `receive_sheet_id`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_purchase_return_detail`
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL COMMENT '退货数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL COMMENT '采购价' AFTER `return_num`;

ALTER TABLE `tbl_purchase_return_detail`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0 COMMENT '退货总金额' AFTER `receive_sheet_detail_id`;

ALTER TABLE `tbl_sc_transfer_order`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '调拨数量' AFTER `target_sc_id`;
ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `transfer_num` decimal(24, 8) NOT NULL COMMENT '调拨数量' AFTER `product_id`,
    MODIFY COLUMN `receive_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`;

ALTER TABLE `tbl_sc_transfer_order_detail_receive`
    MODIFY COLUMN `receive_num` decimal(24, 8) NOT NULL COMMENT '收货数量' AFTER `detail_id`;

ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `tax_price` decimal(24, 6) NULL DEFAULT NULL COMMENT '成本价' AFTER `transfer_num`;
ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `tax_price` decimal(24, 6) NULL COMMENT '成本价' AFTER `transfer_amount`;


ALTER TABLE `tbl_product_stock_warning`
    MODIFY COLUMN `max_limit` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '预警上限' AFTER `product_id`,
    MODIFY COLUMN `min_limit` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '预警下限' AFTER `max_limit`;

ALTER TABLE `tbl_stock_adjust_sheet_detail`
    MODIFY COLUMN `stock_num` decimal(24, 8) NOT NULL COMMENT '调整库存数量' AFTER `product_id`;

ALTER TABLE `tbl_sale_order`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '销售数量' AFTER `saler_id`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_sale_order_detail`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL COMMENT '销售数量' AFTER `product_id`,
    MODIFY COLUMN `out_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已出库数量' AFTER `order_no`;

ALTER TABLE `tbl_sale_order_detail_bundle`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_order_num` decimal(24, 8) NOT NULL COMMENT '单品数量' AFTER `product_id`;

ALTER TABLE `tbl_sale_order_detail_bundle`
    MODIFY COLUMN `product_ori_price` decimal(24, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(24, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`;

ALTER TABLE `tbl_sale_order_detail`
    MODIFY COLUMN `ori_price` decimal(24, 6) NOT NULL COMMENT '原价' AFTER `order_num`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_sale_out_sheet`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `sale_order_id`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_sale_out_sheet_detail`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL COMMENT '出库数量' AFTER `product_id`,
    MODIFY COLUMN `ori_price` decimal(24, 6) NOT NULL COMMENT '原价' AFTER `order_num`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL COMMENT '现价' AFTER `ori_price`,
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `ori_bundle_detail_id`;

ALTER TABLE `tbl_sale_out_sheet_detail_bundle`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_order_num` decimal(24, 8) NOT NULL COMMENT '单品数量' AFTER `product_id`,
    MODIFY COLUMN `product_ori_price` decimal(24, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(24, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`;

ALTER TABLE `tbl_sale_out_sheet_detail_lot`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL COMMENT '出库数量' AFTER `detail_id`,
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `order_num`;

ALTER TABLE `tbl_sale_return`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `out_sheet_id`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_sale_return_detail`
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL COMMENT '退货数量' AFTER `product_id`,
    MODIFY COLUMN `ori_price` decimal(24, 6) NOT NULL COMMENT '原价' AFTER `return_num`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_retail_out_sheet`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `payment_date`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_retail_out_sheet_detail`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL COMMENT '出库数量' AFTER `product_id`,
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `settle_status`;

ALTER TABLE `tbl_retail_out_sheet_detail`
    MODIFY COLUMN `ori_price` decimal(24, 6) NOT NULL COMMENT '原价' AFTER `order_num`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_retail_out_sheet_detail_bundle`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_ori_price` decimal(24, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(24, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`;

ALTER TABLE `tbl_retail_out_sheet_detail_lot`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL COMMENT '出库数量' AFTER `detail_id`,
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `order_num`;

ALTER TABLE `tbl_retail_return`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `out_sheet_id`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_retail_return_detail`
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL COMMENT '退货数量' AFTER `product_id`,
    MODIFY COLUMN `ori_price` decimal(24, 6) NOT NULL COMMENT '原价' AFTER `return_num`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL COMMENT '现价' AFTER `ori_price`;

ALTER TABLE `tbl_pre_take_stock_sheet_detail`
    MODIFY COLUMN `first_num` decimal(24, 8) NULL DEFAULT NULL COMMENT '初盘数量' AFTER `product_id`,
    MODIFY COLUMN `second_num` decimal(24, 8) NULL DEFAULT NULL COMMENT '复盘数量' AFTER `first_num`,
    MODIFY COLUMN `rand_num` decimal(24, 8) NULL DEFAULT NULL COMMENT '抽盘数量' AFTER `second_num`;

ALTER TABLE `tbl_take_stock_plan_detail`
    MODIFY COLUMN `stock_num` decimal(24, 8) NOT NULL COMMENT '初始库存数量' AFTER `product_id`,
    MODIFY COLUMN `ori_take_num` decimal(24, 8) NULL DEFAULT NULL COMMENT '盘点数量' AFTER `stock_num`,
    MODIFY COLUMN `take_num` decimal(24, 8) NULL DEFAULT NULL COMMENT '修改后的盘点数量' AFTER `ori_take_num`,
    MODIFY COLUMN `total_out_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '出项数量' AFTER `take_num`,
    MODIFY COLUMN `total_in_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '入项数量' AFTER `total_out_num`;

ALTER TABLE `tbl_take_stock_sheet_detail`
    MODIFY COLUMN `take_num` decimal(24, 8) NOT NULL COMMENT '盘点数量' AFTER `product_id`;

ALTER TABLE `tbl_purchase_order_detail_bundle`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_ori_price` decimal(24, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(24, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`,
    MODIFY COLUMN `product_tax_rate` decimal(16, 2) NOT NULL COMMENT '单品税率' AFTER `product_tax_price`,
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_purchase_order_detail_bundle_form`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_ori_price` decimal(24, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(24, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`,
    MODIFY COLUMN `product_tax_rate` decimal(16, 2) NOT NULL COMMENT '单品税率' AFTER `product_tax_price`,
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_receive_sheet_detail_bundle`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT '组合商品数量' AFTER `main_product_id`,
    MODIFY COLUMN `product_order_num` decimal(24, 8) NOT NULL COMMENT '单品数量' AFTER `product_id`,
    MODIFY COLUMN `product_ori_price` decimal(24, 6) NOT NULL COMMENT '单品原价' AFTER `product_order_num`,
    MODIFY COLUMN `product_tax_price` decimal(24, 6) NOT NULL COMMENT '单品含税价格' AFTER `product_ori_price`,
    MODIFY COLUMN `product_tax_rate` decimal(16, 2) NOT NULL COMMENT '单品税率' AFTER `product_tax_price`,
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;


ALTER TABLE `tbl_purchase_order_detail`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT '含税总金额' AFTER `receive_num`;

ALTER TABLE `tbl_purchase_order_detail_form`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT '含税总金额' AFTER `receive_num`;

ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `transfer_amount` decimal(32, 2) NOT NULL DEFAULT 0 COMMENT '调拨金额' AFTER `transfer_num`,
    MODIFY COLUMN `receive_amount` decimal(32, 2) NOT NULL DEFAULT 0 COMMENT '已收货金额' AFTER `receive_num`;

ALTER TABLE `tbl_sc_transfer_order_detail_receive`
    MODIFY COLUMN `receive_amount` decimal(32, 2) NOT NULL DEFAULT 0 COMMENT '收货金额' AFTER `receive_num`;

ALTER TABLE `base_data_product_bundle`
    MODIFY COLUMN `purchase_price` decimal(24, 6) NOT NULL COMMENT '采购价' AFTER `bundle_num`;



ALTER TABLE `tbl_sale_order_detail`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL COMMENT '总金额' AFTER `ori_bundle_detail_id`;

ALTER TABLE `tbl_sale_out_sheet_detail`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL COMMENT '总金额' AFTER `return_num`;

ALTER TABLE `tbl_sale_return_detail`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL COMMENT '总金额' AFTER `out_sheet_detail_id`;

ALTER TABLE `tbl_retail_out_sheet_detail`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL COMMENT '总金额' AFTER `ori_bundle_detail_id`;

ALTER TABLE `tbl_retail_return_detail`
    MODIFY COLUMN `tax_amount` decimal(32, 2) NOT NULL COMMENT '总金额' AFTER `out_sheet_detail_id`;

ALTER TABLE `tbl_purchase_order_detail_bundle`
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_purchase_order_detail_bundle_form`
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;


ALTER TABLE `tbl_receive_sheet_detail_bundle`
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_retail_out_sheet_detail_bundle`
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_sale_order_detail_bundle`
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_sale_out_sheet_detail_bundle`
    MODIFY COLUMN `product_tax_amount` decimal(32, 2) NOT NULL COMMENT '单品含税金额' AFTER `product_detail_id`;

ALTER TABLE `tbl_retail_out_sheet_detail_bundle`
    MODIFY COLUMN `product_tax_rate` decimal(16, 2) NOT NULL COMMENT '单品税率' AFTER `product_tax_price`;
ALTER TABLE `tbl_sale_order_detail_bundle`
    MODIFY COLUMN `product_tax_rate` decimal(16, 2) NOT NULL COMMENT '单品税率' AFTER `product_tax_price`;
ALTER TABLE `tbl_sale_out_sheet_detail_bundle`
    MODIFY COLUMN `product_tax_rate` decimal(16, 2) NOT NULL COMMENT '单品税率' AFTER `product_tax_price`;

alter table `customer_settle_check_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `customer_settle_check_sheet` modify column `total_pay_amount` decimal(32, 2) NOT NULL COMMENT "应付金额";
alter table `customer_settle_check_sheet` modify column `total_payed_amount` decimal(32, 2) NOT NULL COMMENT "已付金额";
alter table `customer_settle_check_sheet` modify column `total_discount_amount` decimal(32, 2) NOT NULL COMMENT "已优惠金额";
alter table `customer_settle_check_sheet_detail` modify column `pay_amount` decimal(32, 2) NOT NULL COMMENT "应付金额";
alter table `customer_settle_fee_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `customer_settle_fee_sheet_detail` modify column `amount` decimal(32, 2) NOT NULL COMMENT "金额";
alter table `customer_settle_pre_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `customer_settle_pre_sheet_detail` modify column `amount` decimal(32, 2) NOT NULL COMMENT "金额";
alter table `customer_settle_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `customer_settle_sheet` modify column `total_discount_amount` decimal(32, 2) NOT NULL COMMENT "已优惠金额";
alter table `customer_settle_sheet_detail` modify column `pay_amount` decimal(32, 2) NOT NULL COMMENT "实付金额";
alter table `customer_settle_sheet_detail` modify column `discount_amount` decimal(32, 2) NOT NULL COMMENT "优惠金额";
alter table `settle_check_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `settle_check_sheet` modify column `total_pay_amount` decimal(32, 2) NOT NULL COMMENT "应付金额";
alter table `settle_check_sheet` modify column `total_payed_amount` decimal(32, 2) NOT NULL COMMENT "已付金额";
alter table `settle_check_sheet` modify column `total_discount_amount` decimal(32, 2) NOT NULL COMMENT "已优惠金额";
alter table `settle_check_sheet_detail` modify column `pay_amount` decimal(32, 2) NOT NULL COMMENT "应付金额";
alter table `settle_fee_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `settle_fee_sheet_detail` modify column `amount` decimal(32, 2) NOT NULL COMMENT "金额";
alter table `settle_pre_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `settle_pre_sheet_detail` modify column `amount` decimal(32, 2) NOT NULL COMMENT "金额";
alter table `settle_sheet` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `settle_sheet` modify column `total_discount_amount` decimal(32, 2) NOT NULL COMMENT "已优惠金额";
alter table `settle_sheet_detail` modify column `pay_amount` decimal(32, 2) NOT NULL COMMENT "实付金额";
alter table `settle_sheet_detail` modify column `discount_amount` decimal(32, 2) NOT NULL COMMENT "优惠金额";
alter table `tbl_logistics_sheet` modify column `total_amount` decimal(32, 2) NULL COMMENT "物流费";
alter table `tbl_order_chart` modify column `total_amount` decimal(32, 2) NOT NULL COMMENT "单据总金额";
alter table `tbl_order_pay_type` modify column `pay_amount` decimal(32, 2) NOT NULL COMMENT "支付金额";
alter table `tbl_product_stock` modify column `tax_amount` decimal(32, 2) NOT NULL COMMENT "含税金额";
alter table `tbl_product_stock_log` modify column `tax_amount` decimal(32, 2) NOT NULL COMMENT "含税金额";
alter table `tbl_purchase_order` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "采购金额";
alter table `tbl_purchase_order_detail` modify column `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "含税总金额";
alter table `tbl_purchase_order_detail_bundle` modify column `product_tax_amount` decimal(32, 2) NOT NULL COMMENT "单品含税金额";
alter table `tbl_purchase_order_detail_bundle_form` modify column `product_tax_amount` decimal(32, 2) NOT NULL COMMENT "单品含税金额";
alter table `tbl_purchase_order_detail_form` modify column `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "含税总金额";
alter table `tbl_purchase_order_form` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "采购金额";
alter table `tbl_purchase_return` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "退货金额";
alter table `tbl_purchase_return_detail` modify column `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "退货总金额";
alter table `tbl_receive_sheet` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "收货金额";
alter table `tbl_receive_sheet_detail` modify column `tax_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "采购总金额";
alter table `tbl_receive_sheet_detail_bundle` modify column `product_tax_amount` decimal(32, 2) NOT NULL COMMENT "单品含税金额";
alter table `tbl_retail_out_sheet` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "出库金额";
alter table `tbl_retail_out_sheet_detail` modify column `tax_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `tbl_retail_out_sheet_detail_bundle` modify column `product_tax_amount` decimal(32, 2) NOT NULL COMMENT "单品含税金额";
alter table `tbl_retail_out_sheet_detail_lot` modify column `cost_tax_amount` decimal(32, 2) NOT NULL COMMENT "含税成本金额";
alter table `tbl_retail_return` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "退货金额";
alter table `tbl_retail_return_detail` modify column `tax_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `tbl_sale_order` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "销售金额";
alter table `tbl_sale_order_detail` modify column `tax_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `tbl_sale_order_detail_bundle` modify column `product_tax_amount` decimal(32, 2) NOT NULL COMMENT "单品含税金额";
alter table `tbl_sale_out_sheet` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "出库金额";
alter table `tbl_sale_out_sheet_detail` modify column `tax_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `tbl_sale_out_sheet_detail_bundle` modify column `product_tax_amount` decimal(32, 2) NOT NULL COMMENT "单品含税金额";
alter table `tbl_sale_out_sheet_detail_lot` modify column `cost_tax_amount` decimal(32, 2) NOT NULL COMMENT "含税成本金额";
alter table `tbl_sale_return` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "退货金额";
alter table `tbl_sale_return_detail` modify column `tax_amount` decimal(32, 2) NOT NULL COMMENT "总金额";
alter table `tbl_sc_transfer_order` modify column `total_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "调拨成本金额";
alter table `tbl_sc_transfer_order_detail` modify column `transfer_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "调拨金额";
alter table `tbl_sc_transfer_order_detail` modify column `receive_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "已收货金额";
alter table `tbl_sc_transfer_order_detail_receive` modify column `receive_amount` decimal(32, 2) NOT NULL DEFAULT 0.00 COMMENT "收货金额";


alter table `base_data_product_bundle` modify column `purchase_price` decimal(24, 6) NOT NULL COMMENT "采购价";
alter table `base_data_product_bundle` modify column `sale_price` decimal(24, 6) NOT NULL COMMENT "销售价";
alter table `base_data_product_bundle` modify column `retail_price` decimal(24, 6) NOT NULL COMMENT "零售价";
alter table `base_data_product_purchase` modify column `price` decimal(24, 6) NOT NULL COMMENT "采购价";
alter table `base_data_product_retail` modify column `price` decimal(24, 6) NOT NULL COMMENT "零售价";
alter table `base_data_product_sale` modify column `price` decimal(24, 6) NOT NULL COMMENT "销售价";
alter table `tbl_product_stock` modify column `tax_price` decimal(24, 6) NOT NULL DEFAULT 0.000000 COMMENT "含税价格";
alter table `tbl_product_stock_log` modify column `ori_tax_price` decimal(24, 6) NOT NULL COMMENT "原含税成本价";
alter table `tbl_product_stock_log` modify column `cur_tax_price` decimal(24, 6) NOT NULL COMMENT "现含税成本价";
alter table `tbl_purchase_order_detail` modify column `tax_price` decimal(24, 6) NOT NULL DEFAULT 0.000000 COMMENT "采购价";
alter table `tbl_purchase_order_detail_bundle` modify column `product_ori_price` decimal(24, 6) NOT NULL COMMENT "单品原价";
alter table `tbl_purchase_order_detail_bundle` modify column `product_tax_price` decimal(24, 6) NOT NULL COMMENT "单品含税价格";
alter table `tbl_purchase_order_detail_bundle_form` modify column `product_ori_price` decimal(24, 6) NOT NULL COMMENT "单品原价";
alter table `tbl_purchase_order_detail_bundle_form` modify column `product_tax_price` decimal(24, 6) NOT NULL COMMENT "单品含税价格";
alter table `tbl_purchase_order_detail_form` modify column `tax_price` decimal(24, 6) NOT NULL DEFAULT 0.000000 COMMENT "采购价";
alter table `tbl_purchase_return_detail` modify column `tax_price` decimal(24, 6) NOT NULL COMMENT "采购价";
alter table `tbl_receive_sheet_detail` modify column `tax_price` decimal(24, 6) NOT NULL DEFAULT 0.000000 COMMENT "采购价";
alter table `tbl_receive_sheet_detail_bundle` modify column `product_ori_price` decimal(24, 6) NOT NULL COMMENT "单品原价";
alter table `tbl_receive_sheet_detail_bundle` modify column `product_tax_price` decimal(24, 6) NOT NULL COMMENT "单品含税价格";
alter table `tbl_retail_out_sheet_detail` modify column `ori_price` decimal(24, 6) NOT NULL COMMENT "原价";
alter table `tbl_retail_out_sheet_detail` modify column `tax_price` decimal(24, 6) NOT NULL COMMENT "现价";
alter table `tbl_retail_out_sheet_detail_bundle` modify column `product_ori_price` decimal(24, 6) NOT NULL COMMENT "单品原价";
alter table `tbl_retail_out_sheet_detail_bundle` modify column `product_tax_price` decimal(24, 6) NOT NULL COMMENT "单品含税价格";
alter table `tbl_retail_return_detail` modify column `ori_price` decimal(24, 6) NOT NULL COMMENT "原价";
alter table `tbl_retail_return_detail` modify column `tax_price` decimal(24, 6) NOT NULL COMMENT "现价";
alter table `tbl_sale_order_detail` modify column `ori_price` decimal(24, 6) NOT NULL COMMENT "原价";
alter table `tbl_sale_order_detail` modify column `tax_price` decimal(24, 6) NOT NULL COMMENT "现价";
alter table `tbl_sale_order_detail_bundle` modify column `product_ori_price` decimal(24, 6) NOT NULL COMMENT "单品原价";
alter table `tbl_sale_order_detail_bundle` modify column `product_tax_price` decimal(24, 6) NOT NULL COMMENT "单品含税价格";
alter table `tbl_sale_out_sheet_detail` modify column `ori_price` decimal(24, 6) NOT NULL COMMENT "原价";
alter table `tbl_sale_out_sheet_detail` modify column `tax_price` decimal(24, 6) NOT NULL COMMENT "现价";
alter table `tbl_sale_out_sheet_detail_bundle` modify column `product_ori_price` decimal(24, 6) NOT NULL COMMENT "单品原价";
alter table `tbl_sale_out_sheet_detail_bundle` modify column `product_tax_price` decimal(24, 6) NOT NULL COMMENT "单品含税价格";
alter table `tbl_sale_return_detail` modify column `ori_price` decimal(24, 6) NOT NULL COMMENT "原价";
alter table `tbl_sale_return_detail` modify column `tax_price` decimal(24, 6) NOT NULL COMMENT "现价";
alter table `tbl_sc_transfer_order_detail` modify column `tax_price` decimal(24, 6) NULL COMMENT "成本价";

alter table `tbl_pre_take_stock_sheet_detail` modify column `first_num` decimal(24, 8) NULL COMMENT "初盘数量";
alter table `tbl_pre_take_stock_sheet_detail` modify column `second_num` decimal(24, 8) NULL COMMENT "复盘数量";
alter table `tbl_pre_take_stock_sheet_detail` modify column `rand_num` decimal(24, 8) NULL COMMENT "抽盘数量";
alter table `tbl_product_stock` modify column `stock_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "库存数量";
alter table `tbl_product_stock_log` modify column `ori_stock_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "变动前库存数量";
alter table `tbl_product_stock_log` modify column `cur_stock_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "变动后库存数量";
alter table `tbl_product_stock_log` modify column `stock_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "变动库存数量";
alter table `tbl_purchase_order` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "采购数量";
alter table `tbl_purchase_order` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "采购价";
alter table `tbl_purchase_order_detail` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "采购数量";
alter table `tbl_purchase_order_detail` modify column `receive_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已收货数量";
alter table `tbl_purchase_order_detail_bundle` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "组合商品数量";
alter table `tbl_purchase_order_detail_bundle` modify column `product_order_num` decimal(24, 8) NOT NULL COMMENT "单品数量";
alter table `tbl_purchase_order_detail_bundle_form` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "组合商品数量";
alter table `tbl_purchase_order_detail_bundle_form` modify column `product_order_num` decimal(24, 8) NOT NULL COMMENT "单品数量";
alter table `tbl_purchase_order_detail_form` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "采购数量";
alter table `tbl_purchase_order_detail_form` modify column `receive_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已收货数量";
alter table `tbl_purchase_order_form` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "采购数量";
alter table `tbl_purchase_order_form` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "采购价";
alter table `tbl_purchase_return` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "商品数量";
alter table `tbl_purchase_return` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "赠品数量";
alter table `tbl_purchase_return_detail` modify column `return_num` decimal(24, 8) NOT NULL COMMENT "退货数量";
alter table `tbl_receive_sheet` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "商品数量";
alter table `tbl_receive_sheet` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "赠品数量";
alter table `tbl_receive_sheet_detail` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "采购数量";
alter table `tbl_receive_sheet_detail` modify column `return_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已退货数量";
alter table `tbl_receive_sheet_detail_bundle` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "组合商品数量";
alter table `tbl_receive_sheet_detail_bundle` modify column `product_order_num` decimal(24, 8) NOT NULL COMMENT "单品数量";
alter table `tbl_retail_out_sheet` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "商品数量";
alter table `tbl_retail_out_sheet` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "赠品数量";
alter table `tbl_retail_out_sheet_detail` modify column `order_num` decimal(24, 8) NOT NULL COMMENT "出库数量";
alter table `tbl_retail_out_sheet_detail` modify column `return_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已退货数量";
alter table `tbl_retail_out_sheet_detail_bundle` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "组合商品数量";
alter table `tbl_retail_out_sheet_detail_lot` modify column `order_num` decimal(24, 8) NOT NULL COMMENT "出库数量";
alter table `tbl_retail_out_sheet_detail_lot` modify column `return_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已退货数量";
alter table `tbl_retail_return` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "商品数量";
alter table `tbl_retail_return` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "赠品数量";
alter table `tbl_retail_return_detail` modify column `return_num` decimal(24, 8) NOT NULL COMMENT "退货数量";
alter table `tbl_sale_order` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "销售数量";
alter table `tbl_sale_order` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "赠品数量";
alter table `tbl_sale_order_detail` modify column `order_num` decimal(24, 8) NOT NULL COMMENT "销售数量";
alter table `tbl_sale_order_detail` modify column `out_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已出库数量";
alter table `tbl_sale_order_detail_bundle` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "组合商品数量";
alter table `tbl_sale_order_detail_bundle` modify column `product_order_num` decimal(24, 8) NOT NULL COMMENT "单品数量";
alter table `tbl_sale_out_sheet` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "商品数量";
alter table `tbl_sale_out_sheet` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "赠品数量";
alter table `tbl_sale_out_sheet_detail` modify column `order_num` decimal(24, 8) NOT NULL COMMENT "出库数量";
alter table `tbl_sale_out_sheet_detail` modify column `return_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已退货数量";
alter table `tbl_sale_out_sheet_detail_bundle` modify column `order_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "组合商品数量";
alter table `tbl_sale_out_sheet_detail_bundle` modify column `product_order_num` decimal(24, 8) NOT NULL COMMENT "单品数量";
alter table `tbl_sale_out_sheet_detail_lot` modify column `order_num` decimal(24, 8) NOT NULL COMMENT "出库数量";
alter table `tbl_sale_out_sheet_detail_lot` modify column `return_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已退货数量";
alter table `tbl_sale_return` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "商品数量";
alter table `tbl_sale_return` modify column `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "赠品数量";
alter table `tbl_sale_return_detail` modify column `return_num` decimal(24, 8) NOT NULL COMMENT "退货数量";
alter table `tbl_sc_transfer_order` modify column `total_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "调拨数量";
alter table `tbl_sc_transfer_order_detail` modify column `transfer_num` decimal(24, 8) NOT NULL COMMENT "调拨数量";
alter table `tbl_sc_transfer_order_detail` modify column `receive_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "已收货数量";
alter table `tbl_sc_transfer_order_detail_receive` modify column `receive_num` decimal(24, 8) NOT NULL COMMENT "收货数量";
alter table `tbl_stock_adjust_sheet_detail` modify column `stock_num` decimal(24, 8) NOT NULL COMMENT "调整库存数量";
alter table `tbl_take_stock_plan_detail` modify column `stock_num` decimal(24, 8) NOT NULL COMMENT "初始库存数量";
alter table `tbl_take_stock_plan_detail` modify column `ori_take_num` decimal(24, 8) NULL COMMENT "盘点数量";
alter table `tbl_take_stock_plan_detail` modify column `take_num` decimal(24, 8) NULL COMMENT "修改后的盘点数量";
alter table `tbl_take_stock_plan_detail` modify column `total_out_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "出项数量";
alter table `tbl_take_stock_plan_detail` modify column `total_in_num` decimal(24, 8) NOT NULL DEFAULT 0.00000000 COMMENT "入项数量";
alter table `tbl_take_stock_sheet_detail` modify column `take_num` decimal(24, 8) NOT NULL COMMENT "盘点数量";

update tbl_purchase_order_detail set tax_amount = order_num * tax_price;
update tbl_purchase_order_detail_form set tax_amount = order_num * tax_price;
update tbl_receive_sheet_detail set tax_amount = order_num * tax_price;
update tbl_purchase_return_detail set tax_amount = return_num * tax_price;
UPDATE tbl_sc_transfer_order_detail SET transfer_amount =transfer_num * tax_price, receive_amount = receive_num * tax_price WHERE tax_price IS NOT NULL;
UPDATE tbl_sale_order_detail
SET tax_amount = tax_price * order_num;
UPDATE tbl_sale_out_sheet_detail
SET tax_amount = tax_price * order_num;
UPDATE tbl_sale_return_detail
SET tax_amount = tax_price * return_num;
UPDATE tbl_retail_out_sheet_detail
SET tax_amount = tax_price * order_num;
UPDATE tbl_retail_return_detail
SET tax_amount = tax_price * return_num;
UPDATE tbl_purchase_order_detail_bundle
SET product_tax_amount = product_tax_price * order_num;
UPDATE tbl_purchase_order_detail_bundle_form
SET product_tax_amount = product_tax_price * order_num;
UPDATE tbl_receive_sheet_detail_bundle
SET product_tax_amount = product_tax_price * order_num;
UPDATE tbl_retail_out_sheet_detail_bundle
SET product_tax_amount = product_tax_price * order_num;
UPDATE tbl_sale_order_detail_bundle
SET product_tax_amount = product_tax_price * order_num;
UPDATE tbl_sale_out_sheet_detail_bundle
SET product_tax_amount = product_tax_price * order_num;