ALTER TABLE `tbl_receive_sheet`
ADD COLUMN `ori_settle_status` tinyint(3) NULL COMMENT '结算状态中间态' AFTER `settle_status`;
ALTER TABLE `tbl_receive_sheet`
ADD COLUMN `tx_id` varchar(100) NULL COMMENT '事务ID' AFTER `settle_status`;

ALTER TABLE `tbl_purchase_return`
ADD COLUMN `ori_settle_status` tinyint(3) NULL COMMENT '结算状态中间态' AFTER `settle_status`;
ALTER TABLE `tbl_purchase_return`
ADD COLUMN `tx_id` varchar(100) NULL COMMENT '事务ID' AFTER `settle_status`;

ALTER TABLE `tbl_sale_out_sheet`
ADD COLUMN `ori_settle_status` tinyint(3) NULL COMMENT '结算状态中间态' AFTER `settle_status`;
ALTER TABLE `tbl_sale_out_sheet`
ADD COLUMN `tx_id` varchar(100) NULL COMMENT '事务ID' AFTER `settle_status`;

ALTER TABLE `tbl_sale_return`
ADD COLUMN `ori_settle_status` tinyint(3) NULL COMMENT '结算状态中间态' AFTER `settle_status`;
ALTER TABLE `tbl_sale_return`
ADD COLUMN `tx_id` varchar(100) NULL COMMENT '事务ID' AFTER `settle_status`;