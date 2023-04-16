INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000008', '2000008', 'PayType', '支付方式', NULL, 0, '/base-data/pay-type/index', NULL, '2000', '/pay-type', 0, 1, 0, 'base-data:pay-type:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000008001', '2000008001', '', '新增支付方式', NULL, 0, '', NULL, '2000008', '', 0, 2, 0, 'base-data:pay-type:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000008002', '2000008002', '', '修改支付方式', NULL, 0, '', NULL, '2000008', '', 0, 2, 0, 'base-data:pay-type:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_data_pay_type
-- ----------------------------
DROP TABLE IF EXISTS `base_data_pay_type`;
CREATE TABLE `base_data_pay_type`  (
    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
    `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `rec_text` tinyint(1) NOT NULL COMMENT '是否记录内容',
    `available` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态',
    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人ID',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付方式' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_data_pay_type
-- ----------------------------
INSERT INTO `base_data_pay_type` VALUES ('1', '001', '现金', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` VALUES ('2', '002', '微信支付', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` VALUES ('3', '003', '支付宝支付', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` VALUES ('4', '004', '移动支付', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` VALUES ('5', '005', '优惠券', 1, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` VALUES ('6', '006', '积分', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE `base_data_store_center`
    ADD COLUMN `zip_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编' AFTER `people_num`,
    ADD COLUMN `receiver` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人' AFTER `zip_code`,
    ADD COLUMN `receive_telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货手机号' AFTER `receiver`,
    ADD COLUMN `receive_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货地址' AFTER `receive_telephone`,
    ADD COLUMN `sender` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货人' AFTER `receive_address`,
    ADD COLUMN `send_telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货手机号' AFTER `sender`,
    ADD COLUMN `send_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货地址' AFTER `send_telephone`;

ALTER TABLE `base_data_supplier`
    CHANGE COLUMN `delivery_address` `send_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货地址' AFTER `address`,
    ADD COLUMN `sender` varchar(200) NULL COMMENT '发货人' AFTER `address`,
    ADD COLUMN `send_telephone` varchar(20) NULL COMMENT '发货手机号' AFTER `sender`;

ALTER TABLE `base_data_member`
    ADD COLUMN `city_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区ID' AFTER `guider_id`,
    ADD COLUMN `zip_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编' AFTER `city_id`,
    ADD COLUMN `receiver` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人' AFTER `zip_code`,
    ADD COLUMN `receive_telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货手机号' AFTER `receiver`,
    ADD COLUMN `receive_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货地址' AFTER `receive_telephone`;

DROP TABLE IF EXISTS `base_data_address`;
CREATE TABLE `base_data_address`  (
    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `entity_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实体ID',
    `entity_type` tinyint(3) NOT NULL COMMENT '实体类型',
    `address_type` tinyint(3) NOT NULL COMMENT '地址类型',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
    `telephone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
    `province_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '省',
    `city_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '市',
    `district_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区',
    `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细地址',
    `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认地址',
    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '修改人ID',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `entity_id`(`entity_id`, `entity_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '地址库' ROW_FORMAT = Dynamic;
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009', '2000009', 'Address', '地址库', NULL, 0, '/base-data/address/index', NULL, '2000', '/address', 0, 1, 0, 'base-data:address:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009001', '2000009001', '', '新增地址', NULL, 0, '', NULL, '2000009', '', 0, 2, 0, 'base-data:address:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009002', '2000009002', '', '修改地址', NULL, 0, '', NULL, '2000009', '', 0, 2, 0, 'base-data:address:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009003', '2000009003', '', '导出地址', NULL, 0, '', NULL, '2000009', '', 0, 2, 0, 'base-data:address:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009004', '2000009004', '', '导入地址', NULL, 0, '', NULL, '2000009', '', 0, 2, 0, 'base-data:address:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');

INSERT INTO base_data_address (
    id,
    entity_id,
    entity_type,
    address_type,
    `name`,
    telephone,
    province_id,
    city_id,
    district_id,
    address,
    is_default,
    create_by,
    create_by_id,
    create_time,
    update_by,
    update_by_id,
    update_time
) SELECT
      CONCAT(tb.id, 2) AS id,
      tb.id AS entity_id,
      1 AS entity_type,
      2 AS address_type,
      tb.receiver,
      tb.receive_telephone,
      p.id AS province_id,
      c.id AS city_id,
      d.id AS district_id,
      tb.receive_address,
      1 AS is_default,
      tb.create_by,
      tb.create_by_id,
      tb.create_time,
      tb.update_by,
      tb.update_by_id,
      tb.update_time
FROM
    base_data_store_center AS tb
        INNER JOIN dic_city AS d ON d.id = tb.city_id
        INNER JOIN dic_city as c ON c.id = d.parent_id
        INNER JOIN dic_city as p ON p.id = c.parent_id
WHERE
    tb.receiver IS NOT NULL
  AND tb.receiver != ''
	AND tb.receive_telephone IS NOT NULL
	AND tb.receive_telephone != ''
	AND tb.receive_address IS NOT NULL
	AND tb.receive_address != '';

INSERT INTO base_data_address (
    id,
    entity_id,
    entity_type,
    address_type,
    `name`,
    telephone,
    province_id,
    city_id,
    district_id,
    address,
    is_default,
    create_by,
    create_by_id,
    create_time,
    update_by,
    update_by_id,
    update_time
) SELECT
      CONCAT(tb.id, 2) AS id,
      tb.id AS entity_id,
      2 AS entity_type,
      2 AS address_type,
      tb.receiver,
      tb.receive_telephone,
      p.id AS province_id,
      c.id AS city_id,
      d.id AS district_id,
      tb.receive_address,
      1 AS is_default,
      tb.create_by,
      tb.create_by_id,
      tb.create_time,
      tb.update_by,
      tb.update_by_id,
      tb.update_time
FROM
    base_data_customer AS tb
        INNER JOIN dic_city AS d ON d.id = tb.city_id
        INNER JOIN dic_city as c ON c.id = d.parent_id
        INNER JOIN dic_city as p ON p.id = c.parent_id
WHERE
    tb.receiver IS NOT NULL
  AND tb.receiver != ''
	AND tb.receive_telephone IS NOT NULL
	AND tb.receive_telephone != ''
	AND tb.receive_address IS NOT NULL
	AND tb.receive_address != '';

INSERT INTO base_data_address (
    id,
    entity_id,
    entity_type,
    address_type,
    `name`,
    telephone,
    province_id,
    city_id,
    district_id,
    address,
    is_default,
    create_by,
    create_by_id,
    create_time,
    update_by,
    update_by_id,
    update_time
) SELECT
      CONCAT(tb.id, 2) AS id,
      tb.id AS entity_id,
      4 AS entity_type,
      2 AS address_type,
      tb.receiver,
      tb.receive_telephone,
      p.id AS province_id,
      c.id AS city_id,
      d.id AS district_id,
      tb.receive_address,
      1 AS is_default,
      tb.create_by,
      tb.create_by_id,
      tb.create_time,
      tb.update_by,
      tb.update_by_id,
      tb.update_time
FROM
    base_data_member AS tb
        INNER JOIN dic_city AS d ON d.id = tb.city_id
        INNER JOIN dic_city as c ON c.id = d.parent_id
        INNER JOIN dic_city as p ON p.id = c.parent_id
WHERE
    tb.receiver IS NOT NULL
  AND tb.receiver != ''
	AND tb.receive_telephone IS NOT NULL
	AND tb.receive_telephone != ''
	AND tb.receive_address IS NOT NULL
	AND tb.receive_address != '';

INSERT INTO base_data_address (
    id,
    entity_id,
    entity_type,
    address_type,
    `name`,
    telephone,
    province_id,
    city_id,
    district_id,
    address,
    is_default,
    create_by,
    create_by_id,
    create_time,
    update_by,
    update_by_id,
    update_time
) SELECT
      CONCAT(tb.id, 1) AS id,
      tb.id AS entity_id,
      1 AS entity_type,
      1 AS address_type,
      tb.sender,
      tb.send_telephone,
      p.id AS province_id,
      c.id AS city_id,
      d.id AS district_id,
      tb.send_address,
      1 AS is_default,
      tb.create_by,
      tb.create_by_id,
      tb.create_time,
      tb.update_by,
      tb.update_by_id,
      tb.update_time
FROM
    base_data_store_center AS tb
        INNER JOIN dic_city AS d ON d.id = tb.city_id
        INNER JOIN dic_city as c ON c.id = d.parent_id
        INNER JOIN dic_city as p ON p.id = c.parent_id
WHERE
    tb.sender IS NOT NULL
  AND tb.sender != ''
	AND tb.send_telephone IS NOT NULL
	AND tb.send_telephone != ''
	AND tb.send_address IS NOT NULL
	AND tb.send_address != '';

INSERT INTO base_data_address (
    id,
    entity_id,
    entity_type,
    address_type,
    `name`,
    telephone,
    province_id,
    city_id,
    district_id,
    address,
    is_default,
    create_by,
    create_by_id,
    create_time,
    update_by,
    update_by_id,
    update_time
) SELECT
      CONCAT(tb.id, 1) AS id,
      tb.id AS entity_id,
      3 AS entity_type,
      1 AS address_type,
      tb.sender,
      tb.send_telephone,
      p.id AS province_id,
      c.id AS city_id,
      d.id AS district_id,
      tb.send_address,
      1 AS is_default,
      tb.create_by,
      tb.create_by_id,
      tb.create_time,
      tb.update_by,
      tb.update_by_id,
      tb.update_time
FROM
    base_data_supplier AS tb
        INNER JOIN dic_city AS d ON d.id = tb.city_id
        INNER JOIN dic_city as c ON c.id = d.parent_id
        INNER JOIN dic_city as p ON p.id = c.parent_id
WHERE
    tb.sender IS NOT NULL
  AND tb.sender != ''
	AND tb.send_telephone IS NOT NULL
	AND tb.send_telephone != ''
	AND tb.send_address IS NOT NULL
	AND tb.send_address != '';

ALTER TABLE `base_data_customer`
DROP COLUMN `receiver`,
DROP COLUMN `receive_telephone`,
DROP COLUMN `receive_address`;

ALTER TABLE `base_data_member`
DROP COLUMN `zip_code`,
DROP COLUMN `receiver`,
DROP COLUMN `receive_telephone`,
DROP COLUMN `receive_address`;

ALTER TABLE `base_data_supplier`
DROP COLUMN `sender`,
DROP COLUMN `send_telephone`,
DROP COLUMN `send_address`;

ALTER TABLE `base_data_store_center`
DROP COLUMN `zip_code`,
DROP COLUMN `receiver`,
DROP COLUMN `receive_telephone`,
DROP COLUMN `receive_address`,
DROP COLUMN `sender`,
DROP COLUMN `send_telephone`,
DROP COLUMN `send_address`;

DROP TABLE IF EXISTS `tbl_order_pay_type`;
CREATE TABLE `tbl_order_pay_type`  (
   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
   `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单ID',
   `pay_type_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付方式ID',
   `pay_amount` decimal(24, 2) NOT NULL COMMENT '支付金额',
   `text` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付内容',
   PRIMARY KEY (`id`) USING BTREE,
   INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单支付方式' ROW_FORMAT = Dynamic;

insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_purchase_order where total_amount > 0;
insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_receive_sheet where total_amount > 0;
insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_purchase_return where total_amount > 0;
insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_retail_out_sheet where total_amount > 0;
insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_retail_return where total_amount > 0;
insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_sale_order where total_amount > 0;
insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_sale_out_sheet where total_amount > 0;
insert into tbl_order_pay_type (id, order_id, pay_type_id, pay_amount, text) select id, id, 1, total_amount, NULL from tbl_sale_return where total_amount > 0;

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000002003', '1000002003', '', '部门权限', NULL, 0, '', NULL, '1000002', '', 0, 2, 0, 'system:dept:permission', 1, 1, '', '系统管理员', '1', '2021-06-27 01:33:47', '系统管理员', '1', '2021-07-04 00:34:23');
DROP TABLE IF EXISTS `sys_data_permission_data`;
CREATE TABLE `sys_data_permission_data` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `biz_id` varchar(32) NOT NULL COMMENT '业务ID',
    `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
    `permission_type` tinyint(3) NOT NULL COMMENT '权限类型',
    `permission` longtext NOT NULL COMMENT '数据权限',
    PRIMARY KEY (`id`),
    UNIQUE KEY `biz_id` (`biz_id`,`biz_type`,`permission_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据权限数据';
DROP TABLE IF EXISTS `sys_data_permission_model_detail`;
CREATE TABLE `sys_data_permission_model_detail`  (
     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
     `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
     `model_id` int(11) NOT NULL COMMENT '模型ID',
     `condition_type` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '条件',
     `input_type` tinyint(3) NOT NULL COMMENT '输入类型',
     `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
     `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名',
     `enum_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端枚举名',
     `sql_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'SQL',
     PRIMARY KEY (`id`) USING BTREE,
     INDEX `model_id`(`model_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限模型明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_data_permission_model_detail
-- ----------------------------
INSERT INTO `sys_data_permission_model_detail` VALUES (1, '编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (2, '名称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (3, '简称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'short_name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (4, 'SKU', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'sku_code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (5, '外部编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'external_code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (6, '品类编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'category', 'code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (7, '品类名称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'category', 'name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (8, '品牌编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'brand', 'code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (9, '品牌名称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'brand', 'name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (10, '规格', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'spec', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (11, '单位', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'unit', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (12, '状态', 1, '0,5,6,7', 1, 'product', 'available', 'AVAILABLE', NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (13, '创建时间', 1, '0,1,2,3,4,5', 2, 'product', 'create_time', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` VALUES (14, '创建人部门与本人相同', 2, '6', 99, 'order', 'create_by_id', NULL, 'SELECT __ud.user_id FROM sys_user_dept AS __ud WHERE __ud.dept_id IN ({__var#curDeptIds})');
INSERT INTO `sys_data_permission_model_detail` VALUES (15, '创建人部门是本人的上级部门', 2, '6', 99, 'order', 'create_by_id', NULL, 'SELECT DISTINCT __ud.user_id FROM sys_user_dept AS __ud WHERE FIND_IN_SET(__ud.dept_id,(SELECT GROUP_CONCAT(__mp.path) FROM sys_dept AS __d INNER JOIN recursion_mapping AS __mp ON __mp.node_id=__d.id AND __mp.node_type=1 WHERE __d.id IN ({__var#curDeptIds}) AND __mp.level> 1))');
INSERT INTO `sys_data_permission_model_detail` VALUES (16, '创建人部门是本人的下级部门', 2, '6', 99, 'order', 'create_by_id', NULL, 'SELECT DISTINCT __ud.user_id FROM sys_user_dept AS __ud WHERE __ud.dept_id IN (SELECT __mp.node_id FROM recursion_mapping AS __mp WHERE FIND_IN_SET((SELECT __d.id FROM sys_dept AS __d WHERE __d.id IN ({__var#curDeptIds})),__mp.path) AND __mp.node_type=1)');
INSERT INTO `sys_data_permission_model_detail` VALUES (17, '创建时间', 2, '0,1,2,3,4,5', 2, 'order', 'create_time', NULL, NULL);
