DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统模块' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES (1, '平台管理', '系统平台的核心模块。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (2, '系统管理', '维护企业、系统基础数据，包括部门、岗位、角色、系统菜单和参数等。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (3, '基础信息管理', '维护基础业务数据，包括仓库、客户、供应商、会员等。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (4, '商品中心', '管理商品核心数据。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (5, '采购业务', '管理供应商与企业的采购业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (6, '销售业务', '管理企业与TOB端大客户的销售业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (7, '零售业务', '管理企业与TOC端会员的零售业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (8, '库存管理及调拨', '查询商品库存和进行商品调拨。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (9, '库存盘点', '库存商品盘点。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (10, '库存调整', '商品库存数量和成本调整。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (11, '结算管理', '企业与供应商、客户进行结算。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (12, '开发管理', '系统快捷开发，如管理定时器、数据实体、数据对象、表单生成等。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (13, '便捷办公', '支持文件收纳和在线Excel。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (14, '开放平台', '系统开放平台管理。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');
INSERT INTO `sys_module` VALUES (15, '物流发货', '管理企业物流业务。', '系统管理员', '1', '2023-06-06 10:26:30', '系统管理员', '1', '2023-06-06 10:26:41');

DROP TABLE IF EXISTS `sys_module_tenant`;
CREATE TABLE `sys_module_tenant`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `module_id` int(11) NOT NULL COMMENT '模块ID',
  `tenant_id` int(11) NOT NULL COMMENT '租户ID',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`tenant_id`, `module_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户和系统模块关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_module_tenant
-- ----------------------------
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875135', 1, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875136', 2, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875137', 3, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875138', 4, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875139', 5, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875140', 6, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875141', 7, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875142', 8, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875143', 9, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875144', 10, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875145', 11, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875146', 12, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875147', 13, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875148', 14, 1000, '2099-01-01 00:00:00');
INSERT INTO `sys_module_tenant` VALUES ('1666286094246875149', 15, 1000, '2099-01-01 00:00:00');