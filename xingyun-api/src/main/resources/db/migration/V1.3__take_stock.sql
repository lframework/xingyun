-- ----------------------------
-- Table structure for tbl_pre_take_stock_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_pre_take_stock_sheet`;
CREATE TABLE `tbl_pre_take_stock_sheet` (
                                            `id` varchar(32) NOT NULL COMMENT 'ID',
                                            `code` varchar(32) NOT NULL COMMENT '业务单据号',
                                            `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
                                            `take_status` tinyint(3) NOT NULL COMMENT '盘点状态',
                                            `description` varchar(200) DEFAULT NULL COMMENT '备注',
                                            `create_by` varchar(32) NOT NULL COMMENT '创建人',
                                            `create_time` datetime NOT NULL COMMENT '创建时间',
                                            `update_by` varchar(32) NOT NULL COMMENT '修改人',
                                            `update_time` datetime NOT NULL COMMENT '修改时间',
                                            PRIMARY KEY (`id`),
                                            UNIQUE KEY `code` (`code`),
                                            KEY `sc_id` (`sc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_pre_take_stock_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_pre_take_stock_sheet_detail`;
CREATE TABLE `tbl_pre_take_stock_sheet_detail` (
                                                   `id` varchar(32) NOT NULL COMMENT 'ID',
                                                   `sheet_id` varchar(32) NOT NULL COMMENT '预先盘点单ID',
                                                   `product_id` varchar(32) NOT NULL COMMENT '商品ID',
                                                   `first_num` int(11) DEFAULT NULL COMMENT '初盘数量',
                                                   `second_num` int(11) DEFAULT NULL COMMENT '复盘数量',
                                                   `rand_num` int(11) DEFAULT NULL COMMENT '抽盘数量',
                                                   `order_no` int(11) NOT NULL COMMENT '排序',
                                                   PRIMARY KEY (`id`),
                                                   UNIQUE KEY `sheet_id` (`sheet_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_take_stock_config
-- ----------------------------
DROP TABLE IF EXISTS `tbl_take_stock_config`;
CREATE TABLE `tbl_take_stock_config` (
                                         `id` varchar(32) NOT NULL COMMENT 'ID',
                                         `show_product` tinyint(1) NOT NULL DEFAULT '0' COMMENT '库存盘点单关联盘点任务后，是否显示盘点任务中的商品数据',
                                         `show_stock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '库存盘点单是否显示盘点任务创建时商品的系统库存数量',
                                         `auto_change_stock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '盘点差异生成时是否自动调整盘点任务中商品的系统库存数量',
                                         `allow_change_num` tinyint(1) NOT NULL DEFAULT '0' COMMENT '盘点差异单中的盘点数量是否允许手动修改',
                                         `cancel_hours` int(11) NOT NULL DEFAULT '24' COMMENT '盘点任务创建后多少小时内内未完成，则自动作废',
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_take_stock_plan
-- ----------------------------
DROP TABLE IF EXISTS `tbl_take_stock_plan`;
CREATE TABLE `tbl_take_stock_plan` (
                                       `id` varchar(32) NOT NULL COMMENT 'ID',
                                       `code` varchar(32) NOT NULL COMMENT '业务单据号',
                                       `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
                                       `take_type` tinyint(3) NOT NULL COMMENT '盘点类别',
                                       `biz_id` longtext COMMENT '业务ID',
                                       `take_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '盘点状态',
                                       `description` varchar(200) DEFAULT NULL COMMENT '备注',
                                       `create_by` varchar(32) NOT NULL COMMENT '创建人',
                                       `create_time` datetime NOT NULL COMMENT '创建时间',
                                       `update_by` varchar(32) NOT NULL COMMENT '修改人',
                                       `update_time` datetime NOT NULL COMMENT '修改时间',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `code` (`code`),
                                       KEY `sc_id` (`sc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_take_stock_plan_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_take_stock_plan_detail`;
CREATE TABLE `tbl_take_stock_plan_detail` (
                                              `id` varchar(32) NOT NULL COMMENT 'ID',
                                              `plan_id` varchar(32) NOT NULL COMMENT '盘点任务ID',
                                              `product_id` varchar(32) NOT NULL COMMENT '商品ID',
                                              `stock_num` int(11) NOT NULL COMMENT '初始库存数量',
                                              `ori_take_num` int(11) DEFAULT NULL COMMENT '盘点数量',
                                              `take_num` int(11) DEFAULT NULL COMMENT '修改后的盘点数量',
                                              `total_out_num` int(11) NOT NULL DEFAULT '0' COMMENT '出项数量',
                                              `total_in_num` int(11) NOT NULL DEFAULT '0' COMMENT '入项数量',
                                              `description` varchar(200) DEFAULT NULL COMMENT '备注',
                                              `order_no` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
                                              PRIMARY KEY (`id`),
                                              UNIQUE KEY `plan_id` (`plan_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_take_stock_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_take_stock_sheet`;
CREATE TABLE `tbl_take_stock_sheet` (
                                        `id` varchar(32) NOT NULL COMMENT 'ID',
                                        `code` varchar(32) NOT NULL COMMENT '业务单据号',
                                        `plan_id` varchar(32) NOT NULL COMMENT '盘点任务ID',
                                        `pre_sheet_id` varchar(32) DEFAULT NULL COMMENT '预先盘点单ID',
                                        `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
                                        `status` tinyint(3) NOT NULL COMMENT '状态',
                                        `description` varchar(200) DEFAULT NULL COMMENT '备注',
                                        `create_by` varchar(32) NOT NULL COMMENT '创建人',
                                        `create_time` datetime NOT NULL COMMENT '创建时间',
                                        `update_by` varchar(32) NOT NULL COMMENT '修改人',
                                        `update_time` datetime NOT NULL COMMENT '修改时间',
                                        `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
                                        `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
                                        `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        UNIQUE KEY `code` (`code`),
                                        KEY `plan_id` (`plan_id`),
                                        KEY `sc_id` (`sc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_take_stock_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_take_stock_sheet_detail`;
CREATE TABLE `tbl_take_stock_sheet_detail` (
                                               `id` varchar(32) NOT NULL COMMENT 'ID',
                                               `sheet_id` varchar(32) NOT NULL COMMENT '盘点单ID',
                                               `product_id` varchar(32) NOT NULL COMMENT '商品ID',
                                               `take_num` int(11) NOT NULL COMMENT '盘点数量',
                                               `description` varchar(200) DEFAULT NULL COMMENT '备注',
                                               `order_no` int(11) NOT NULL COMMENT '排序',
                                               PRIMARY KEY (`id`),
                                               UNIQUE KEY `sheet_id` (`sheet_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `tbl_take_stock_config` (`id`, `show_product`, `show_stock`, `auto_change_stock`, `allow_change_num`, `cancel_hours`) VALUES ('1', 0, 0, 0, 0, 168);

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004', '3000004', 'TakeStock', '库存盘点', '', '3000', '/take', 0, 0, 0, '', 1, 1, '', '1', '2021-07-05 01:21:35', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004001', '3000004001', 'TakeStockConfig', '盘点参数设置', '/sc/stock/take/config/index', '3000004', '/config', 1, 1, 0, 'stock:take:config:modify', 1, 1, '', '1', '2021-07-05 21:59:35', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004002', '3000004002', 'PreTakeStockSheet', '预先盘点单管理', '/sc/stock/take/pre/index', '3000004', '/pre', 0, 1, 0, 'stock:take:pre:query', 1, 1, '', '1', '2021-07-05 21:59:35', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004002001', '3000004002001', '', '新增预先盘点单', '', '3000004002', '', 0, 2, 0, 'stock:take:pre:add', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004002002', '3000004002002', '', '修改预先盘点单', '', '3000004002', '', 0, 2, 0, 'stock:take:pre:modify', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004002003', '3000004002003', '', '删除预先盘点单', '', '3000004002', '', 0, 2, 0, 'stock:take:pre:delete', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004002004', '3000004002004', '', '导出预先盘点单', '', '3000004002', '', 0, 2, 0, 'stock:take:pre:export', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003', '3000004003', 'TakeStockPlan', '盘点任务管理', '/sc/stock/take/plan/index', '3000004', '/plan', 0, 1, 0, 'stock:take:plan:query', 1, 1, '', '1', '2021-07-05 21:59:35', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003001', '3000004003001', '', '新增盘点任务', '', '3000004003', '', 0, 2, 0, 'stock:take:plan:add', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003002', '3000004003002', '', '修改盘点任务', '', '3000004003', '', 0, 2, 0, 'stock:take:plan:modify', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003003', '3000004003003', '', '删除盘点任务', '', '3000004003', '', 0, 2, 0, 'stock:take:plan:delete', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003004', '3000004003004', '', '导出盘点任务', '', '3000004003', '', 0, 2, 0, 'stock:take:plan:export', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003005', '3000004003005', '', '差异生成', '', '3000004003', '', 0, 2, 0, 'stock:take:plan:create-diff', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003006', '3000004003006', '', '差异处理', '', '3000004003', '', 0, 2, 0, 'stock:take:plan:handle-diff', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('3000004003007', '3000004003007', '', '作废盘点任务', '', '3000004003', '', 0, 2, 0, 'stock:take:plan:cancel', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-07-04 00:34:23');

UPDATE sys_menu` SET `path` = '/menu' WHERE `id` = '1000001';