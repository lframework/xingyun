ALTER TABLE `tbl_retail_config`
    ADD COLUMN `retail_out_sheet_require_member` tinyint(1) NOT NULL DEFAULT 1 COMMENT '零售出库单上的会员是否必填' AFTER `id`,
ADD COLUMN `retail_return_require_member` tinyint(1) NOT NULL DEFAULT 1 COMMENT '零售退货单上的会员是否必填' AFTER `retail_return_multiple_relate_out_stock`;
ALTER TABLE `tbl_retail_out_sheet`
    MODIFY COLUMN `member_id` varchar (32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '会员ID' AFTER `sc_id`;
ALTER TABLE `tbl_retail_return`
    MODIFY COLUMN `member_id` varchar (32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '会员ID' AFTER `sc_id`;