ALTER TABLE `tbl_purchase_order_detail`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `receive_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`,
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '含税总金额' AFTER `receive_num`;

ALTER TABLE `tbl_purchase_order_detail_form`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `receive_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已收货数量' AFTER `order_no`,
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '含税总金额' AFTER `receive_num`;

ALTER TABLE `tbl_purchase_order`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `expect_arrive_date`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `total_num`;

ALTER TABLE `tbl_purchase_order_form`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `expect_arrive_date`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `total_num`;

update tbl_purchase_order_detail set tax_amount = order_num * tax_price;
update tbl_purchase_order_detail_form set tax_amount = order_num * tax_price;

ALTER TABLE `tbl_receive_sheet`
    MODIFY COLUMN `total_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '商品数量' AFTER `purchase_order_id`,
    MODIFY COLUMN `total_gift_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '赠品数量' AFTER `total_num`;

ALTER TABLE `tbl_receive_sheet_detail`
    MODIFY COLUMN `order_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '采购数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '采购价' AFTER `order_num`,
    MODIFY COLUMN `return_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '已退货数量' AFTER `order_no`,
    ADD COLUMN `tax_amount` decimal(24, 2) NOT NULL DEFAULT 0 COMMENT '采购总金额' AFTER `purchase_order_detail_id`;

update tbl_receive_sheet_detail set tax_amount = order_num * tax_price;

ALTER TABLE `tbl_product_stock`
    MODIFY COLUMN `stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '库存数量' AFTER `product_id`,
    MODIFY COLUMN `tax_price` decimal(24, 6) NOT NULL DEFAULT 0 COMMENT '含税价格' AFTER `stock_num`;

ALTER TABLE `tbl_product_stock_log`
    MODIFY COLUMN `ori_stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '变动前库存数量' AFTER `cur_tax_price`,
    MODIFY COLUMN `cur_stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '变动后库存数量' AFTER `ori_stock_num`,
    MODIFY COLUMN `stock_num` decimal(24, 8) NOT NULL DEFAULT 0 COMMENT '库存数量' AFTER `cur_stock_num`;