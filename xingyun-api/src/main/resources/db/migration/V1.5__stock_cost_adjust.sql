INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000005', '3000005', 'StockAdjust', '库存调整', '', NULL, '/take-adjust', 0, 0, 0, '', 1, 1, '', '1', '2021-07-05 01:21:35', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000005001', '3000005001', 'StockCostAdjustSheet', '库存成本调整', '/sc/stock/adjust/cost/index', '3000005', '/cost', 0, 1, 0, 'stock:adjust:cost:query', 1, 1, '', '1', '2021-07-05 21:59:35', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000005001001', '3000005001001', '', '新增库存成本调整单', '', '3000005001', '', 0, 2, 0, 'stock:adjust:cost:add', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000005001002', '3000005001002', '', '修改库存成本调整单', '', '3000005001', '', 0, 2, 0, 'stock:adjust:cost:modify', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000005001003', '3000005001003', '', '删除库存成本调整单', '', '3000005001', '', 0, 2, 0, 'stock:adjust:cost:delete', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000005001004', '3000005001004', '', '导出库存成本调整单', '', '3000005001', '', 0, 2, 0, 'stock:adjust:cost:export', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000005001005', '3000005001005', '', '审核库存成本调整单', '', '3000005001', '', 0, 2, 0, 'stock:adjust:cost:approve', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');

-- ----------------------------
-- Table structure for tbl_stock_cost_adjust_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_stock_cost_adjust_sheet`;
CREATE TABLE `tbl_stock_cost_adjust_sheet` (
                                               `id` varchar(32) NOT NULL COMMENT 'ID',
                                               `code` varchar(32) NOT NULL COMMENT '业务单据号',
                                               `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
                                               `product_num` int(11) NOT NULL COMMENT '调价品种数',
                                               `diff_amount` decimal(24,2) NOT NULL COMMENT '库存调价差额',
                                               `status` tinyint(3) NOT NULL COMMENT '状态',
                                               `description` varchar(200) DEFAULT NULL COMMENT '备注',
                                               `create_by` varchar(32) NOT NULL COMMENT '创建人',
                                               `create_time` datetime NOT NULL COMMENT '创建时间',
                                               `update_by` varchar(32) NOT NULL COMMENT '修改人',
                                               `update_time` datetime NOT NULL COMMENT '修改时间',
                                               `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
                                               `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
                                               `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
                                               PRIMARY KEY (`id`),
                                               UNIQUE KEY `code` (`code`),
                                               KEY `sc_id` (`sc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_stock_cost_adjust_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_stock_cost_adjust_sheet_detail`;
CREATE TABLE `tbl_stock_cost_adjust_sheet_detail` (
                                                      `id` varchar(32) NOT NULL COMMENT 'ID',
                                                      `sheet_id` varchar(32) NOT NULL COMMENT '单据ID',
                                                      `product_id` varchar(32) NOT NULL COMMENT '商品ID',
                                                      `stock_num` int(11) NOT NULL COMMENT '库存数量',
                                                      `purchase_price` decimal(16,2) NOT NULL COMMENT '档案采购价',
                                                      `ori_price` decimal(16,2) NOT NULL COMMENT '调整前成本价',
                                                      `price` decimal(16,2) NOT NULL COMMENT '调整后成本价',
                                                      `diff_amount` decimal(24,2) NOT NULL COMMENT '库存调价差额',
                                                      `description` varchar(200) DEFAULT NULL COMMENT '备注',
                                                      `order_no` int(11) NOT NULL COMMENT '排序',
                                                      PRIMARY KEY (`id`),
                                                      UNIQUE KEY `sheet_id` (`sheet_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
