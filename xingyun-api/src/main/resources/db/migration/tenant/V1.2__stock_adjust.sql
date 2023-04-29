INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005002', '3000005002', 'StockAdjustReason', '库存调整原因', NULL, 0, '/sc/stock/adjust/stock/reason/index', NULL, '3000005', '/stock/reason', 0, 1, 0, 'stock:adjust:reason:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005002001', '3000005002001', '', '新增库存成本调整单', NULL, 0, '', NULL, '3000005002', '', 0, 2, 0, 'stock:adjust:reason:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005002002', '3000005002002', '', '修改库存成本调整单', NULL, 0, '', NULL, '3000005002', '', 0, 2, 0, 'stock:adjust:reason:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');

DROP TABLE IF EXISTS `tbl_stock_adjust_reason`;
CREATE TABLE `tbl_stock_adjust_reason`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存调整原因' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_stock_adjust_reason
-- ----------------------------
INSERT INTO `tbl_stock_adjust_reason` VALUES ('1', '001', '初始化数据', 1, '系统内置', '系统管理员', '1', '2023-04-18 14:04:34', '系统管理员', '1', '2023-04-18 14:04:53');
INSERT INTO `tbl_stock_adjust_reason` VALUES ('2', '002', '损溢', 1, '系统内置', '系统管理员', '1', '2023-04-18 14:04:34', '系统管理员', '1', '2023-04-18 14:04:53');
INSERT INTO `tbl_stock_adjust_reason` VALUES ('3', '003', '其他', 1, '系统内置', '系统管理员', '1', '2023-04-18 14:04:34', '系统管理员', '1', '2023-04-18 14:04:53');