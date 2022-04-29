ALTER TABLE `base_data_customer` COMMENT = '客户';
ALTER TABLE `base_data_member` COMMENT = '会员';
ALTER TABLE `base_data_product` COMMENT = '商品';
ALTER TABLE `base_data_product_brand` COMMENT = '商品品牌';
ALTER TABLE `base_data_product_category` COMMENT = '商品类目';
ALTER TABLE `base_data_product_category_property` COMMENT = '商品类目和商品属性关系表';
ALTER TABLE `base_data_product_poly` COMMENT = '商品SPU';
ALTER TABLE `base_data_product_poly_property` COMMENT = '商品SPU和商品属性关系表';
ALTER TABLE `base_data_product_property` COMMENT = '商品属性';
ALTER TABLE `base_data_product_property_item` COMMENT = '商品属性值';
ALTER TABLE `base_data_product_purchase` COMMENT = '商品采购价';
ALTER TABLE `base_data_product_retail` COMMENT = '商品零售价';
ALTER TABLE `base_data_product_sale` COMMENT = '商品销售价';
ALTER TABLE `base_data_product_saleprop_group` COMMENT = '商品销售属性组';
ALTER TABLE `base_data_product_saleprop_item` COMMENT = '商品销售属性值';
ALTER TABLE `base_data_product_saleprop_item_relation` COMMENT = '商品和销售属性值关系表';
ALTER TABLE `base_data_store_center` COMMENT = '仓库';
ALTER TABLE `base_data_supplier` COMMENT = '供应商';
ALTER TABLE `dic_city` COMMENT = '地区字典表';
ALTER TABLE `op_logs` COMMENT = '操作日志';
ALTER TABLE `recursion_mapping` COMMENT = '树形数据节点关系表';
ALTER TABLE `settle_check_sheet` COMMENT = '供应商对账单';
ALTER TABLE `settle_check_sheet_detail` COMMENT = '供应商对账单明细';
ALTER TABLE `settle_fee_sheet` COMMENT = '供应商费用单';
ALTER TABLE `settle_fee_sheet_detail` COMMENT = '供应商费用单明细';
ALTER TABLE `settle_in_item` COMMENT = '收入项目';
ALTER TABLE `settle_out_item` COMMENT = '支出项目';
ALTER TABLE `settle_pre_sheet` COMMENT = '供应商预付款单';
ALTER TABLE `settle_pre_sheet_detail` COMMENT = '供应商预付款单明细';
ALTER TABLE `settle_sheet` COMMENT = '供应商结算单';
ALTER TABLE `settle_sheet_detail` COMMENT = '供应商结算单明细';
ALTER TABLE `sys_dept` COMMENT = '部门';
ALTER TABLE `sys_menu` COMMENT = '菜单';
ALTER TABLE `sys_menu_collect` COMMENT = '菜单收藏';
ALTER TABLE `sys_position` COMMENT = '岗位';
ALTER TABLE `sys_role` COMMENT = '角色';
ALTER TABLE `sys_role_menu` COMMENT = '角色与菜单关系表';
ALTER TABLE `sys_user` COMMENT = '用户';
ALTER TABLE `sys_user_dept` COMMENT = '用户与部门关系表';
ALTER TABLE `sys_user_position` COMMENT = '用户与岗位关系表';
ALTER TABLE `sys_user_role` COMMENT = '用户与角色关系表';
ALTER TABLE `tbl_order_chart` COMMENT = '订单图表数据';
ALTER TABLE `tbl_product_lot` COMMENT = '商品批次';
ALTER TABLE `tbl_product_lot_stock` COMMENT = '商品批次库存';
ALTER TABLE `tbl_product_stock` COMMENT = '商品库存';
ALTER TABLE `tbl_product_stock_log` COMMENT = '商品库存变动记录';
ALTER TABLE `tbl_purchase_config` COMMENT = '采购参数设置';
ALTER TABLE `tbl_purchase_order` COMMENT = '采购订单';
ALTER TABLE `tbl_purchase_order_detail` COMMENT = '采购订单明细';
ALTER TABLE `tbl_purchase_return` COMMENT = '采购退单';
ALTER TABLE `tbl_purchase_return_detail` COMMENT = '采购退单明细';
ALTER TABLE `tbl_receive_sheet` COMMENT = '采购收货单';
ALTER TABLE `tbl_receive_sheet_detail` COMMENT = '采购收货单明细';
ALTER TABLE `tbl_retail_config` COMMENT = '零售参数设置';
ALTER TABLE `tbl_retail_out_sheet` COMMENT = '零售出库单';
ALTER TABLE `tbl_retail_out_sheet_detail` COMMENT = '零售出库单明细';
ALTER TABLE `tbl_retail_out_sheet_detail_lot` COMMENT = '零售出库单批次明细';
ALTER TABLE `tbl_retail_return` COMMENT = '零售退单';
ALTER TABLE `tbl_retail_return_detail` COMMENT = '零售退单明细';
ALTER TABLE `tbl_sale_config` COMMENT = '销售参数设置';
ALTER TABLE `tbl_sale_order` COMMENT = '销售订单';
ALTER TABLE `tbl_sale_order_detail` COMMENT = '销售订单明细';
ALTER TABLE `tbl_sale_out_sheet` COMMENT = '销售出库单';
ALTER TABLE `tbl_sale_out_sheet_detail` COMMENT = '销售出库单明细';
ALTER TABLE `tbl_sale_out_sheet_detail_lot` COMMENT = '销售出库单批次明细';
ALTER TABLE `tbl_sale_return` COMMENT = '销售退单';
ALTER TABLE `tbl_sale_return_detail` COMMENT = '销售退单明细';
ALTER TABLE `gen_create_column_config` COMMENT = '新增功能代码生成配置';
ALTER TABLE `gen_data_object` COMMENT = '数据对象';
ALTER TABLE `gen_data_object_column` COMMENT = '数据对象列信息';
ALTER TABLE `gen_detail_column_config` COMMENT = '详情功能代码生成配置';
ALTER TABLE `gen_generate_info` COMMENT = '代码生成基本信息';
ALTER TABLE `gen_query_column_config` COMMENT = '查询功能代码生成配置';
ALTER TABLE `gen_query_params_column_config` COMMENT = '查询参数功能代码生成配置';
ALTER TABLE `gen_simple_table` COMMENT = '数据库单表';
ALTER TABLE `gen_simple_table_column` COMMENT = '数据库单表列信息';
ALTER TABLE `gen_update_column_config` COMMENT = '修改功能代码生成配置';
ALTER TABLE `tbl_pre_take_stock_sheet` COMMENT = '库存预先盘点单';
ALTER TABLE `tbl_pre_take_stock_sheet_detail` COMMENT = '库存预先盘点单明细';
ALTER TABLE `tbl_take_stock_config` COMMENT = '库存盘点参数设置';
ALTER TABLE `tbl_take_stock_plan` COMMENT = '库存盘点任务';
ALTER TABLE `tbl_take_stock_plan_detail` COMMENT = '库存盘点任务明细';
ALTER TABLE `tbl_take_stock_sheet` COMMENT = '库存盘点单';
ALTER TABLE `tbl_take_stock_sheet_detail` COMMENT = '库存盘点单明细';
ALTER TABLE `tbl_stock_cost_adjust_sheet` COMMENT = '库存成本调整单';
ALTER TABLE `tbl_stock_cost_adjust_sheet_detail` COMMENT = '库存成本调整单明细';
ALTER TABLE `sys_config` COMMENT = '系统设置';
DROP TABLE IF EXISTS `sys_user_telephone`;
CREATE TABLE `sys_user_telephone`
(
    `id`        varchar(32) NOT NULL COMMENT 'ID',
    `telephone` varchar(11) NOT NULL COMMENT '手机号',
    `user_id`   varchar(32) DEFAULT NULL COMMENT '用户ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `telephone` (`telephone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户绑定手机号表';

SET
FOREIGN_KEY_CHECKS = 1;