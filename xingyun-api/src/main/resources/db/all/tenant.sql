SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_data_address
-- ----------------------------
DROP TABLE IF EXISTS `base_data_address`;
CREATE TABLE `base_data_address` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `entity_id` varchar(32) NOT NULL COMMENT '实体ID',
  `entity_type` tinyint(3) NOT NULL COMMENT '实体类型',
  `address_type` tinyint(3) NOT NULL COMMENT '地址类型',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `telephone` varchar(20) NOT NULL COMMENT '手机号',
  `province_id` varchar(32) NOT NULL COMMENT '省',
  `city_id` varchar(32) NOT NULL COMMENT '市',
  `district_id` varchar(32) NOT NULL COMMENT '区',
  `address` varchar(200) NOT NULL COMMENT '详细地址',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认地址',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `entity_id` (`entity_id`,`entity_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='地址库';

-- ----------------------------
-- Records of base_data_address
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_customer
-- ----------------------------
DROP TABLE IF EXISTS `base_data_customer`;
CREATE TABLE `base_data_customer` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `mnemonic_code` varchar(20) NOT NULL COMMENT '助记码',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `zip_code` varchar(20) DEFAULT NULL COMMENT '邮编',
  `fax` varchar(100) DEFAULT NULL COMMENT '传真',
  `city_id` varchar(32) DEFAULT NULL COMMENT '地区ID',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `settle_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '结算方式',
  `credit_code` varchar(100) DEFAULT NULL COMMENT '统一社会信用代码',
  `tax_identify_no` varchar(100) DEFAULT NULL COMMENT '纳税人识别号',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `account_name` varchar(100) DEFAULT NULL COMMENT '户名',
  `account_no` varchar(100) DEFAULT NULL COMMENT '银行账号',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户';

-- ----------------------------
-- Records of base_data_customer
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_logistics_company
-- ----------------------------
DROP TABLE IF EXISTS `base_data_logistics_company`;
CREATE TABLE `base_data_logistics_company` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `city_id` varchar(32) DEFAULT NULL COMMENT '地区ID',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流公司';

-- ----------------------------
-- Records of base_data_logistics_company
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_member
-- ----------------------------
DROP TABLE IF EXISTS `base_data_member`;
CREATE TABLE `base_data_member` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `gender` tinyint(3) NOT NULL DEFAULT '0' COMMENT '性别',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `join_day` date NOT NULL COMMENT '入会日期',
  `shop_id` varchar(32) DEFAULT NULL COMMENT '所属门店',
  `guider_id` varchar(32) DEFAULT NULL COMMENT '所属导购',
  `city_id` varchar(32) DEFAULT NULL COMMENT '地区ID',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  UNIQUE KEY `telephone` (`telephone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='会员';

-- ----------------------------
-- Records of base_data_member
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_pay_type
-- ----------------------------
DROP TABLE IF EXISTS `base_data_pay_type`;
CREATE TABLE `base_data_pay_type` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `rec_text` tinyint(1) NOT NULL COMMENT '是否记录内容',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='支付方式';

-- ----------------------------
-- Records of base_data_pay_type
-- ----------------------------
BEGIN;
INSERT INTO `base_data_pay_type` (`id`, `code`, `name`, `rec_text`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1', '001', '现金', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` (`id`, `code`, `name`, `rec_text`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2', '002', '微信支付', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` (`id`, `code`, `name`, `rec_text`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3', '003', '支付宝支付', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` (`id`, `code`, `name`, `rec_text`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4', '004', '移动支付', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` (`id`, `code`, `name`, `rec_text`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5', '005', '优惠券', 1, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
INSERT INTO `base_data_pay_type` (`id`, `code`, `name`, `rec_text`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('6', '006', '积分', 0, 1, '', '系统管理员', '1', '2023-03-21 10:09:44', '系统管理员', '1', '2023-03-21 10:12:30');
COMMIT;

-- ----------------------------
-- Table structure for base_data_product
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product`;
CREATE TABLE `base_data_product` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `short_name` varchar(100) DEFAULT NULL COMMENT '简称',
  `sku_code` varchar(100) NOT NULL COMMENT 'SKU',
  `external_code` varchar(100) DEFAULT NULL COMMENT '外部编号',
  `category_id` varchar(32) NOT NULL COMMENT '类目ID',
  `brand_id` varchar(32) NOT NULL COMMENT '品牌ID',
  `product_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '商品类型',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '进项税率（%）',
  `sale_tax_rate` decimal(16,2) NOT NULL COMMENT '销项税率',
  `spec` varchar(20) DEFAULT NULL COMMENT '规格',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `weight` decimal(16,2) DEFAULT NULL COMMENT '重量（kg）',
  `volume` decimal(16,2) DEFAULT NULL COMMENT '体积（cm3）',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  UNIQUE KEY `sku_code` (`sku_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品';

-- ----------------------------
-- Records of base_data_product
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_brand
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_brand`;
CREATE TABLE `base_data_product_brand` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `short_name` varchar(20) DEFAULT '' COMMENT '简称',
  `logo` longtext COMMENT 'logo',
  `introduction` varchar(400) NOT NULL DEFAULT '' COMMENT '简介',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品品牌';

-- ----------------------------
-- Records of base_data_product_brand
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_bundle
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_bundle`;
CREATE TABLE `base_data_product_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '主商品ID',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `bundle_num` int(11) NOT NULL COMMENT '包含数量',
  `sale_price` decimal(24,2) NOT NULL COMMENT '销售价',
  `retail_price` decimal(24,2) NOT NULL COMMENT '零售价',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `main_product_id` (`main_product_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='组合商品';

-- ----------------------------
-- Records of base_data_product_bundle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_category
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_category`;
CREATE TABLE `base_data_product_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品类目';

-- ----------------------------
-- Records of base_data_product_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_category_property
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_category_property`;
CREATE TABLE `base_data_product_category_property` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `property_id` varchar(32) NOT NULL COMMENT '商品属性ID',
  `category_id` varchar(32) NOT NULL COMMENT '商品类目ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `property_id` (`property_id`,`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品类目和商品属性关系表';

-- ----------------------------
-- Records of base_data_product_category_property
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_property
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_property`;
CREATE TABLE `base_data_product_property` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `is_required` tinyint(1) NOT NULL COMMENT '是否必填',
  `column_type` tinyint(3) NOT NULL COMMENT '录入类型',
  `column_data_type` tinyint(3) DEFAULT NULL COMMENT '数据类型',
  `property_type` tinyint(3) NOT NULL COMMENT '属性类别',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品属性';

-- ----------------------------
-- Records of base_data_product_property
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_property_item
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_property_item`;
CREATE TABLE `base_data_product_property_item` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `property_id` varchar(32) NOT NULL COMMENT '属性ID',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `property_id` (`property_id`,`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品属性值';

-- ----------------------------
-- Records of base_data_product_property_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_property_relation
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_property_relation`;
CREATE TABLE `base_data_product_property_relation` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `property_id` varchar(32) NOT NULL COMMENT '商品属性ID',
  `property_item_id` varchar(32) DEFAULT NULL COMMENT '属性值ID',
  `property_text` varchar(100) DEFAULT NULL COMMENT '商品属性值',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `product_id` (`product_id`,`property_id`,`property_item_id`) USING BTREE,
  KEY `property_id` (`property_id`,`property_item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品和商品属性关系表';

-- ----------------------------
-- Records of base_data_product_property_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_purchase
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_purchase`;
CREATE TABLE `base_data_product_purchase` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `price` decimal(24,2) NOT NULL COMMENT '采购价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品采购价';

-- ----------------------------
-- Records of base_data_product_purchase
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_retail
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_retail`;
CREATE TABLE `base_data_product_retail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `price` decimal(24,2) NOT NULL COMMENT '零售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品零售价';

-- ----------------------------
-- Records of base_data_product_retail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_product_sale
-- ----------------------------
DROP TABLE IF EXISTS `base_data_product_sale`;
CREATE TABLE `base_data_product_sale` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `price` decimal(24,2) NOT NULL COMMENT '销售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品销售价';

-- ----------------------------
-- Records of base_data_product_sale
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_store_center
-- ----------------------------
DROP TABLE IF EXISTS `base_data_store_center`;
CREATE TABLE `base_data_store_center` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系人手机号码',
  `city_id` varchar(32) DEFAULT NULL COMMENT '地区ID',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `people_num` int(11) DEFAULT NULL COMMENT '仓库人数',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='仓库';

-- ----------------------------
-- Records of base_data_store_center
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for base_data_supplier
-- ----------------------------
DROP TABLE IF EXISTS `base_data_supplier`;
CREATE TABLE `base_data_supplier` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `mnemonic_code` varchar(20) NOT NULL COMMENT '助记码',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `zip_code` varchar(20) DEFAULT NULL COMMENT '邮编',
  `fax` varchar(100) DEFAULT NULL COMMENT '传真',
  `city_id` varchar(32) DEFAULT NULL COMMENT '地区ID',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `delivery_cycle` int(11) DEFAULT NULL COMMENT '发货周期（天）',
  `manage_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '经营方式',
  `settle_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '结算方式',
  `credit_code` varchar(100) DEFAULT NULL COMMENT '统一社会信用代码',
  `tax_identify_no` varchar(100) DEFAULT NULL COMMENT '纳税人识别号',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `account_name` varchar(100) DEFAULT NULL COMMENT '户名',
  `account_no` varchar(100) DEFAULT NULL COMMENT '银行账号',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商';

-- ----------------------------
-- Records of base_data_supplier
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_check_sheet
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_check_sheet`;
CREATE TABLE `customer_settle_check_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `total_pay_amount` decimal(24,2) NOT NULL COMMENT '应付金额',
  `total_payed_amount` decimal(24,2) NOT NULL COMMENT '已付金额',
  `total_discount_amount` decimal(24,2) NOT NULL COMMENT '已优惠金额',
  `start_date` date NOT NULL COMMENT '起始日期',
  `end_date` date NOT NULL COMMENT '截止日期',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户对账单';

-- ----------------------------
-- Records of customer_settle_check_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_check_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_check_sheet_detail`;
CREATE TABLE `customer_settle_check_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '对账单ID',
  `biz_id` varchar(32) NOT NULL COMMENT '单据ID',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  `calc_type` tinyint(3) NOT NULL COMMENT '计算类型',
  `pay_amount` decimal(24,2) NOT NULL COMMENT '应付金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`biz_id`) USING BTREE,
  KEY `biz_id` (`biz_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户对账单明细';

-- ----------------------------
-- Records of customer_settle_check_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_fee_sheet
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_fee_sheet`;
CREATE TABLE `customer_settle_fee_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `sheet_type` tinyint(3) NOT NULL COMMENT '单据类型',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户费用单';

-- ----------------------------
-- Records of customer_settle_fee_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_fee_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_fee_sheet_detail`;
CREATE TABLE `customer_settle_fee_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '费用单ID',
  `item_id` varchar(32) NOT NULL COMMENT '项目ID',
  `amount` decimal(24,2) NOT NULL COMMENT '金额',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`item_id`) USING BTREE,
  KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户费用单明细';

-- ----------------------------
-- Records of customer_settle_fee_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_pre_sheet
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_pre_sheet`;
CREATE TABLE `customer_settle_pre_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户预付款单';

-- ----------------------------
-- Records of customer_settle_pre_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_pre_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_pre_sheet_detail`;
CREATE TABLE `customer_settle_pre_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '预付款单ID',
  `item_id` varchar(32) NOT NULL COMMENT '项目ID',
  `amount` decimal(24,2) NOT NULL COMMENT '金额',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`item_id`) USING BTREE,
  KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户预付款单明细';

-- ----------------------------
-- Records of customer_settle_pre_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_sheet
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_sheet`;
CREATE TABLE `customer_settle_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `total_discount_amount` decimal(24,2) NOT NULL COMMENT '已优惠金额',
  `start_date` date NOT NULL COMMENT '起始日期',
  `end_date` date NOT NULL COMMENT '截止日期',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户结算单';

-- ----------------------------
-- Records of customer_settle_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for customer_settle_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `customer_settle_sheet_detail`;
CREATE TABLE `customer_settle_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '结算单ID',
  `biz_id` varchar(32) NOT NULL COMMENT '单据ID',
  `pay_amount` decimal(24,2) NOT NULL COMMENT '实付金额',
  `discount_amount` decimal(24,2) NOT NULL COMMENT '优惠金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`biz_id`) USING BTREE,
  KEY `biz_id` (`biz_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='客户结算单明细';

-- ----------------------------
-- Records of customer_settle_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for dic_city
-- ----------------------------
DROP TABLE IF EXISTS `dic_city`;
CREATE TABLE `dic_city` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `level` int(11) NOT NULL COMMENT '层级',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='地区字典表';

-- ----------------------------
-- Records of dic_city
-- ----------------------------
BEGIN;
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('11', '11', '北京市', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1101', '1101', '市辖区', '11', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110101', '110101', '东城区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110102', '110102', '西城区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110105', '110105', '朝阳区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110106', '110106', '丰台区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110107', '110107', '石景山区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110108', '110108', '海淀区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110109', '110109', '门头沟区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110111', '110111', '房山区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110112', '110112', '通州区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110113', '110113', '顺义区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110114', '110114', '昌平区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110115', '110115', '大兴区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110116', '110116', '怀柔区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110117', '110117', '平谷区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110118', '110118', '密云区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('110119', '110119', '延庆区', '1101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('12', '12', '天津市', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1201', '1201', '市辖区', '12', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120101', '120101', '和平区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120102', '120102', '河东区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120103', '120103', '河西区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120104', '120104', '南开区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120105', '120105', '河北区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120106', '120106', '红桥区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120110', '120110', '东丽区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120111', '120111', '西青区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120112', '120112', '津南区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120113', '120113', '北辰区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120114', '120114', '武清区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120115', '120115', '宝坻区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120116', '120116', '滨海新区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120117', '120117', '宁河区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120118', '120118', '静海区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('120119', '120119', '蓟州区', '1201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('13', '13', '河北省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1301', '1301', '石家庄市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130102', '130102', '长安区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130104', '130104', '桥西区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130105', '130105', '新华区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130107', '130107', '井陉矿区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130108', '130108', '裕华区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130109', '130109', '藁城区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130110', '130110', '鹿泉区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130111', '130111', '栾城区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130121', '130121', '井陉县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130123', '130123', '正定县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130125', '130125', '行唐县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130126', '130126', '灵寿县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130127', '130127', '高邑县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130128', '130128', '深泽县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130129', '130129', '赞皇县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130130', '130130', '无极县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130131', '130131', '平山县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130132', '130132', '元氏县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130133', '130133', '赵县', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130171', '130171', '石家庄高新技术产业开发区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130172', '130172', '石家庄循环化工园区', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130181', '130181', '辛集市', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130183', '130183', '晋州市', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130184', '130184', '新乐市', '1301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1302', '1302', '唐山市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130202', '130202', '路南区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130203', '130203', '路北区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130204', '130204', '古冶区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130205', '130205', '开平区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130207', '130207', '丰南区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130208', '130208', '丰润区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130209', '130209', '曹妃甸区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130224', '130224', '滦南县', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130225', '130225', '乐亭县', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130227', '130227', '迁西县', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130229', '130229', '玉田县', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130271', '130271', '河北唐山芦台经济开发区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130272', '130272', '唐山市汉沽管理区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130273', '130273', '唐山高新技术产业开发区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130274', '130274', '河北唐山海港经济开发区', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130281', '130281', '遵化市', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130283', '130283', '迁安市', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130284', '130284', '滦州市', '1302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1303', '1303', '秦皇岛市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130302', '130302', '海港区', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130303', '130303', '山海关区', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130304', '130304', '北戴河区', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130306', '130306', '抚宁区', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130321', '130321', '青龙满族自治县', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130322', '130322', '昌黎县', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130324', '130324', '卢龙县', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130371', '130371', '秦皇岛市经济技术开发区', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130372', '130372', '北戴河新区', '1303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1304', '1304', '邯郸市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130402', '130402', '邯山区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130403', '130403', '丛台区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130404', '130404', '复兴区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130406', '130406', '峰峰矿区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130407', '130407', '肥乡区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130408', '130408', '永年区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130423', '130423', '临漳县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130424', '130424', '成安县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130425', '130425', '大名县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130426', '130426', '涉县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130427', '130427', '磁县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130430', '130430', '邱县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130431', '130431', '鸡泽县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130432', '130432', '广平县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130433', '130433', '馆陶县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130434', '130434', '魏县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130435', '130435', '曲周县', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130471', '130471', '邯郸经济技术开发区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130473', '130473', '邯郸冀南新区', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130481', '130481', '武安市', '1304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1305', '1305', '邢台市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130502', '130502', '襄都区', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130503', '130503', '信都区', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130505', '130505', '任泽区', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130506', '130506', '南和区', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130522', '130522', '临城县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130523', '130523', '内丘县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130524', '130524', '柏乡县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130525', '130525', '隆尧县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130528', '130528', '宁晋县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130529', '130529', '巨鹿县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130530', '130530', '新河县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130531', '130531', '广宗县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130532', '130532', '平乡县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130533', '130533', '威县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130534', '130534', '清河县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130535', '130535', '临西县', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130571', '130571', '河北邢台经济开发区', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130581', '130581', '南宫市', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130582', '130582', '沙河市', '1305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1306', '1306', '保定市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130602', '130602', '竞秀区', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130606', '130606', '莲池区', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130607', '130607', '满城区', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130608', '130608', '清苑区', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130609', '130609', '徐水区', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130623', '130623', '涞水县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130624', '130624', '阜平县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130626', '130626', '定兴县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130627', '130627', '唐县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130628', '130628', '高阳县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130629', '130629', '容城县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130630', '130630', '涞源县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130631', '130631', '望都县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130632', '130632', '安新县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130633', '130633', '易县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130634', '130634', '曲阳县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130635', '130635', '蠡县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130636', '130636', '顺平县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130637', '130637', '博野县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130638', '130638', '雄县', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130671', '130671', '保定高新技术产业开发区', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130672', '130672', '保定白沟新城', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130681', '130681', '涿州市', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130682', '130682', '定州市', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130683', '130683', '安国市', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130684', '130684', '高碑店市', '1306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1307', '1307', '张家口市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130702', '130702', '桥东区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130703', '130703', '桥西区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130705', '130705', '宣化区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130706', '130706', '下花园区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130708', '130708', '万全区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130709', '130709', '崇礼区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130722', '130722', '张北县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130723', '130723', '康保县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130724', '130724', '沽源县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130725', '130725', '尚义县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130726', '130726', '蔚县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130727', '130727', '阳原县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130728', '130728', '怀安县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130730', '130730', '怀来县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130731', '130731', '涿鹿县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130732', '130732', '赤城县', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130771', '130771', '张家口经济开发区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130772', '130772', '张家口市察北管理区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130773', '130773', '张家口市塞北管理区', '1307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1308', '1308', '承德市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130802', '130802', '双桥区', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130803', '130803', '双滦区', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130804', '130804', '鹰手营子矿区', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130821', '130821', '承德县', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130822', '130822', '兴隆县', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130824', '130824', '滦平县', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130825', '130825', '隆化县', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130826', '130826', '丰宁满族自治县', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130827', '130827', '宽城满族自治县', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130828', '130828', '围场满族蒙古族自治县', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130871', '130871', '承德高新技术产业开发区', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130881', '130881', '平泉市', '1308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1309', '1309', '沧州市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130902', '130902', '新华区', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130903', '130903', '运河区', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130921', '130921', '沧县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130922', '130922', '青县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130923', '130923', '东光县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130924', '130924', '海兴县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130925', '130925', '盐山县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130926', '130926', '肃宁县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130927', '130927', '南皮县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130928', '130928', '吴桥县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130929', '130929', '献县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130930', '130930', '孟村回族自治县', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130971', '130971', '河北沧州经济开发区', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130972', '130972', '沧州高新技术产业开发区', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130973', '130973', '沧州渤海新区', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130981', '130981', '泊头市', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130982', '130982', '任丘市', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130983', '130983', '黄骅市', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('130984', '130984', '河间市', '1309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1310', '1310', '廊坊市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131002', '131002', '安次区', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131003', '131003', '广阳区', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131022', '131022', '固安县', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131023', '131023', '永清县', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131024', '131024', '香河县', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131025', '131025', '大城县', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131026', '131026', '文安县', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131028', '131028', '大厂回族自治县', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131071', '131071', '廊坊经济技术开发区', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131081', '131081', '霸州市', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131082', '131082', '三河市', '1310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1311', '1311', '衡水市', '13', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131102', '131102', '桃城区', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131103', '131103', '冀州区', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131121', '131121', '枣强县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131122', '131122', '武邑县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131123', '131123', '武强县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131124', '131124', '饶阳县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131125', '131125', '安平县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131126', '131126', '故城县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131127', '131127', '景县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131128', '131128', '阜城县', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131171', '131171', '河北衡水高新技术产业开发区', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131172', '131172', '衡水滨湖新区', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('131182', '131182', '深州市', '1311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('14', '14', '山西省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1401', '1401', '太原市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140105', '140105', '小店区', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140106', '140106', '迎泽区', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140107', '140107', '杏花岭区', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140108', '140108', '尖草坪区', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140109', '140109', '万柏林区', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140110', '140110', '晋源区', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140121', '140121', '清徐县', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140122', '140122', '阳曲县', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140123', '140123', '娄烦县', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140171', '140171', '山西转型综合改革示范区', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140181', '140181', '古交市', '1401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1402', '1402', '大同市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140212', '140212', '新荣区', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140213', '140213', '平城区', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140214', '140214', '云冈区', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140215', '140215', '云州区', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140221', '140221', '阳高县', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140222', '140222', '天镇县', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140223', '140223', '广灵县', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140224', '140224', '灵丘县', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140225', '140225', '浑源县', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140226', '140226', '左云县', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140271', '140271', '山西大同经济开发区', '1402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1403', '1403', '阳泉市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140302', '140302', '城区', '1403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140303', '140303', '矿区', '1403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140311', '140311', '郊区', '1403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140321', '140321', '平定县', '1403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140322', '140322', '盂县', '1403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1404', '1404', '长治市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140403', '140403', '潞州区', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140404', '140404', '上党区', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140405', '140405', '屯留区', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140406', '140406', '潞城区', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140423', '140423', '襄垣县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140425', '140425', '平顺县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140426', '140426', '黎城县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140427', '140427', '壶关县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140428', '140428', '长子县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140429', '140429', '武乡县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140430', '140430', '沁县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140431', '140431', '沁源县', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140471', '140471', '山西长治高新技术产业园区', '1404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1405', '1405', '晋城市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140502', '140502', '城区', '1405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140521', '140521', '沁水县', '1405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140522', '140522', '阳城县', '1405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140524', '140524', '陵川县', '1405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140525', '140525', '泽州县', '1405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140581', '140581', '高平市', '1405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1406', '1406', '朔州市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140602', '140602', '朔城区', '1406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140603', '140603', '平鲁区', '1406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140621', '140621', '山阴县', '1406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140622', '140622', '应县', '1406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140623', '140623', '右玉县', '1406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140671', '140671', '山西朔州经济开发区', '1406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140681', '140681', '怀仁市', '1406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1407', '1407', '晋中市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140702', '140702', '榆次区', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140703', '140703', '太谷区', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140721', '140721', '榆社县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140722', '140722', '左权县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140723', '140723', '和顺县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140724', '140724', '昔阳县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140725', '140725', '寿阳县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140727', '140727', '祁县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140728', '140728', '平遥县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140729', '140729', '灵石县', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140781', '140781', '介休市', '1407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1408', '1408', '运城市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140802', '140802', '盐湖区', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140821', '140821', '临猗县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140822', '140822', '万荣县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140823', '140823', '闻喜县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140824', '140824', '稷山县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140825', '140825', '新绛县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140826', '140826', '绛县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140827', '140827', '垣曲县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140828', '140828', '夏县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140829', '140829', '平陆县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140830', '140830', '芮城县', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140881', '140881', '永济市', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140882', '140882', '河津市', '1408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1409', '1409', '忻州市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140902', '140902', '忻府区', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140921', '140921', '定襄县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140922', '140922', '五台县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140923', '140923', '代县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140924', '140924', '繁峙县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140925', '140925', '宁武县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140926', '140926', '静乐县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140927', '140927', '神池县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140928', '140928', '五寨县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140929', '140929', '岢岚县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140930', '140930', '河曲县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140931', '140931', '保德县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140932', '140932', '偏关县', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140971', '140971', '五台山风景名胜区', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('140981', '140981', '原平市', '1409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1410', '1410', '临汾市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141002', '141002', '尧都区', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141021', '141021', '曲沃县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141022', '141022', '翼城县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141023', '141023', '襄汾县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141024', '141024', '洪洞县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141025', '141025', '古县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141026', '141026', '安泽县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141027', '141027', '浮山县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141028', '141028', '吉县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141029', '141029', '乡宁县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141030', '141030', '大宁县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141031', '141031', '隰县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141032', '141032', '永和县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141033', '141033', '蒲县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141034', '141034', '汾西县', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141081', '141081', '侯马市', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141082', '141082', '霍州市', '1410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1411', '1411', '吕梁市', '14', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141102', '141102', '离石区', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141121', '141121', '文水县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141122', '141122', '交城县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141123', '141123', '兴县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141124', '141124', '临县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141125', '141125', '柳林县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141126', '141126', '石楼县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141127', '141127', '岚县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141128', '141128', '方山县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141129', '141129', '中阳县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141130', '141130', '交口县', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141181', '141181', '孝义市', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('141182', '141182', '汾阳市', '1411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('15', '15', '内蒙古自治区', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1501', '1501', '呼和浩特市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150102', '150102', '新城区', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150103', '150103', '回民区', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150104', '150104', '玉泉区', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150105', '150105', '赛罕区', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150121', '150121', '土默特左旗', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150122', '150122', '托克托县', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150123', '150123', '和林格尔县', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150124', '150124', '清水河县', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150125', '150125', '武川县', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150172', '150172', '呼和浩特经济技术开发区', '1501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1502', '1502', '包头市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150202', '150202', '东河区', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150203', '150203', '昆都仑区', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150204', '150204', '青山区', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150205', '150205', '石拐区', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150206', '150206', '白云鄂博矿区', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150207', '150207', '九原区', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150221', '150221', '土默特右旗', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150222', '150222', '固阳县', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150223', '150223', '达尔罕茂明安联合旗', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150271', '150271', '包头稀土高新技术产业开发区', '1502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1503', '1503', '乌海市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150302', '150302', '海勃湾区', '1503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150303', '150303', '海南区', '1503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150304', '150304', '乌达区', '1503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1504', '1504', '赤峰市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150402', '150402', '红山区', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150403', '150403', '元宝山区', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150404', '150404', '松山区', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150421', '150421', '阿鲁科尔沁旗', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150422', '150422', '巴林左旗', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150423', '150423', '巴林右旗', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150424', '150424', '林西县', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150425', '150425', '克什克腾旗', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150426', '150426', '翁牛特旗', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150428', '150428', '喀喇沁旗', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150429', '150429', '宁城县', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150430', '150430', '敖汉旗', '1504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1505', '1505', '通辽市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150502', '150502', '科尔沁区', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150521', '150521', '科尔沁左翼中旗', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150522', '150522', '科尔沁左翼后旗', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150523', '150523', '开鲁县', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150524', '150524', '库伦旗', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150525', '150525', '奈曼旗', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150526', '150526', '扎鲁特旗', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150571', '150571', '通辽经济技术开发区', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150581', '150581', '霍林郭勒市', '1505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1506', '1506', '鄂尔多斯市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150602', '150602', '东胜区', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150603', '150603', '康巴什区', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150621', '150621', '达拉特旗', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150622', '150622', '准格尔旗', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150623', '150623', '鄂托克前旗', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150624', '150624', '鄂托克旗', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150625', '150625', '杭锦旗', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150626', '150626', '乌审旗', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150627', '150627', '伊金霍洛旗', '1506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1507', '1507', '呼伦贝尔市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150702', '150702', '海拉尔区', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150703', '150703', '扎赉诺尔区', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150721', '150721', '阿荣旗', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150722', '150722', '莫力达瓦达斡尔族自治旗', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150723', '150723', '鄂伦春自治旗', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150724', '150724', '鄂温克族自治旗', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150725', '150725', '陈巴尔虎旗', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150726', '150726', '新巴尔虎左旗', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150727', '150727', '新巴尔虎右旗', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150781', '150781', '满洲里市', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150782', '150782', '牙克石市', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150783', '150783', '扎兰屯市', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150784', '150784', '额尔古纳市', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150785', '150785', '根河市', '1507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1508', '1508', '巴彦淖尔市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150802', '150802', '临河区', '1508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150821', '150821', '五原县', '1508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150822', '150822', '磴口县', '1508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150823', '150823', '乌拉特前旗', '1508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150824', '150824', '乌拉特中旗', '1508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150825', '150825', '乌拉特后旗', '1508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150826', '150826', '杭锦后旗', '1508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1509', '1509', '乌兰察布市', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150902', '150902', '集宁区', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150921', '150921', '卓资县', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150922', '150922', '化德县', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150923', '150923', '商都县', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150924', '150924', '兴和县', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150925', '150925', '凉城县', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150926', '150926', '察哈尔右翼前旗', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150927', '150927', '察哈尔右翼中旗', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150928', '150928', '察哈尔右翼后旗', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150929', '150929', '四子王旗', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('150981', '150981', '丰镇市', '1509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1522', '1522', '兴安盟', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152201', '152201', '乌兰浩特市', '1522', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152202', '152202', '阿尔山市', '1522', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152221', '152221', '科尔沁右翼前旗', '1522', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152222', '152222', '科尔沁右翼中旗', '1522', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152223', '152223', '扎赉特旗', '1522', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152224', '152224', '突泉县', '1522', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1525', '1525', '锡林郭勒盟', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152501', '152501', '二连浩特市', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152502', '152502', '锡林浩特市', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152522', '152522', '阿巴嘎旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152523', '152523', '苏尼特左旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152524', '152524', '苏尼特右旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152525', '152525', '东乌珠穆沁旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152526', '152526', '西乌珠穆沁旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152527', '152527', '太仆寺旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152528', '152528', '镶黄旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152529', '152529', '正镶白旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152530', '152530', '正蓝旗', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152531', '152531', '多伦县', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152571', '152571', '乌拉盖管委会', '1525', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('1529', '1529', '阿拉善盟', '15', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152921', '152921', '阿拉善左旗', '1529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152922', '152922', '阿拉善右旗', '1529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152923', '152923', '额济纳旗', '1529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('152971', '152971', '内蒙古阿拉善经济开发区', '1529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('21', '21', '辽宁省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2101', '2101', '沈阳市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210102', '210102', '和平区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210103', '210103', '沈河区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210104', '210104', '大东区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210105', '210105', '皇姑区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210106', '210106', '铁西区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210111', '210111', '苏家屯区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210112', '210112', '浑南区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210113', '210113', '沈北新区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210114', '210114', '于洪区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210115', '210115', '辽中区', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210123', '210123', '康平县', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210124', '210124', '法库县', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210181', '210181', '新民市', '2101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2102', '2102', '大连市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210202', '210202', '中山区', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210203', '210203', '西岗区', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210204', '210204', '沙河口区', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210211', '210211', '甘井子区', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210212', '210212', '旅顺口区', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210213', '210213', '金州区', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210214', '210214', '普兰店区', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210224', '210224', '长海县', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210281', '210281', '瓦房店市', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210283', '210283', '庄河市', '2102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2103', '2103', '鞍山市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210302', '210302', '铁东区', '2103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210303', '210303', '铁西区', '2103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210304', '210304', '立山区', '2103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210311', '210311', '千山区', '2103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210321', '210321', '台安县', '2103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210323', '210323', '岫岩满族自治县', '2103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210381', '210381', '海城市', '2103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2104', '2104', '抚顺市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210402', '210402', '新抚区', '2104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210403', '210403', '东洲区', '2104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210404', '210404', '望花区', '2104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210411', '210411', '顺城区', '2104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210421', '210421', '抚顺县', '2104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210422', '210422', '新宾满族自治县', '2104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210423', '210423', '清原满族自治县', '2104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2105', '2105', '本溪市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210502', '210502', '平山区', '2105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210503', '210503', '溪湖区', '2105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210504', '210504', '明山区', '2105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210505', '210505', '南芬区', '2105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210521', '210521', '本溪满族自治县', '2105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210522', '210522', '桓仁满族自治县', '2105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2106', '2106', '丹东市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210602', '210602', '元宝区', '2106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210603', '210603', '振兴区', '2106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210604', '210604', '振安区', '2106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210624', '210624', '宽甸满族自治县', '2106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210681', '210681', '东港市', '2106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210682', '210682', '凤城市', '2106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2107', '2107', '锦州市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210702', '210702', '古塔区', '2107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210703', '210703', '凌河区', '2107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210711', '210711', '太和区', '2107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210726', '210726', '黑山县', '2107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210727', '210727', '义县', '2107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210781', '210781', '凌海市', '2107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210782', '210782', '北镇市', '2107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2108', '2108', '营口市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210802', '210802', '站前区', '2108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210803', '210803', '西市区', '2108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210804', '210804', '鲅鱼圈区', '2108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210811', '210811', '老边区', '2108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210881', '210881', '盖州市', '2108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210882', '210882', '大石桥市', '2108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2109', '2109', '阜新市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210902', '210902', '海州区', '2109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210903', '210903', '新邱区', '2109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210904', '210904', '太平区', '2109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210905', '210905', '清河门区', '2109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210911', '210911', '细河区', '2109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210921', '210921', '阜新蒙古族自治县', '2109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('210922', '210922', '彰武县', '2109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2110', '2110', '辽阳市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211002', '211002', '白塔区', '2110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211003', '211003', '文圣区', '2110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211004', '211004', '宏伟区', '2110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211005', '211005', '弓长岭区', '2110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211011', '211011', '太子河区', '2110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211021', '211021', '辽阳县', '2110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211081', '211081', '灯塔市', '2110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2111', '2111', '盘锦市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211102', '211102', '双台子区', '2111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211103', '211103', '兴隆台区', '2111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211104', '211104', '大洼区', '2111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211122', '211122', '盘山县', '2111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2112', '2112', '铁岭市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211202', '211202', '银州区', '2112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211204', '211204', '清河区', '2112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211221', '211221', '铁岭县', '2112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211223', '211223', '西丰县', '2112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211224', '211224', '昌图县', '2112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211281', '211281', '调兵山市', '2112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211282', '211282', '开原市', '2112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2113', '2113', '朝阳市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211302', '211302', '双塔区', '2113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211303', '211303', '龙城区', '2113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211321', '211321', '朝阳县', '2113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211322', '211322', '建平县', '2113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211324', '211324', '喀喇沁左翼蒙古族自治县', '2113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211381', '211381', '北票市', '2113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211382', '211382', '凌源市', '2113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2114', '2114', '葫芦岛市', '21', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211402', '211402', '连山区', '2114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211403', '211403', '龙港区', '2114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211404', '211404', '南票区', '2114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211421', '211421', '绥中县', '2114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211422', '211422', '建昌县', '2114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('211481', '211481', '兴城市', '2114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('22', '22', '吉林省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2201', '2201', '长春市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220102', '220102', '南关区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220103', '220103', '宽城区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220104', '220104', '朝阳区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220105', '220105', '二道区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220106', '220106', '绿园区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220112', '220112', '双阳区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220113', '220113', '九台区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220122', '220122', '农安县', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220171', '220171', '长春经济技术开发区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220172', '220172', '长春净月高新技术产业开发区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220173', '220173', '长春高新技术产业开发区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220174', '220174', '长春汽车经济技术开发区', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220182', '220182', '榆树市', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220183', '220183', '德惠市', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220184', '220184', '公主岭市', '2201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2202', '2202', '吉林市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220202', '220202', '昌邑区', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220203', '220203', '龙潭区', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220204', '220204', '船营区', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220211', '220211', '丰满区', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220221', '220221', '永吉县', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220271', '220271', '吉林经济开发区', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220272', '220272', '吉林高新技术产业开发区', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220273', '220273', '吉林中国新加坡食品区', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220281', '220281', '蛟河市', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220282', '220282', '桦甸市', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220283', '220283', '舒兰市', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220284', '220284', '磐石市', '2202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2203', '2203', '四平市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220302', '220302', '铁西区', '2203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220303', '220303', '铁东区', '2203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220322', '220322', '梨树县', '2203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220323', '220323', '伊通满族自治县', '2203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220382', '220382', '双辽市', '2203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2204', '2204', '辽源市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220402', '220402', '龙山区', '2204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220403', '220403', '西安区', '2204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220421', '220421', '东丰县', '2204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220422', '220422', '东辽县', '2204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2205', '2205', '通化市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220502', '220502', '东昌区', '2205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220503', '220503', '二道江区', '2205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220521', '220521', '通化县', '2205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220523', '220523', '辉南县', '2205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220524', '220524', '柳河县', '2205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220581', '220581', '梅河口市', '2205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220582', '220582', '集安市', '2205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2206', '2206', '白山市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220602', '220602', '浑江区', '2206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220605', '220605', '江源区', '2206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220621', '220621', '抚松县', '2206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220622', '220622', '靖宇县', '2206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220623', '220623', '长白朝鲜族自治县', '2206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220681', '220681', '临江市', '2206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2207', '2207', '松原市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220702', '220702', '宁江区', '2207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220721', '220721', '前郭尔罗斯蒙古族自治县', '2207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220722', '220722', '长岭县', '2207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220723', '220723', '乾安县', '2207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220771', '220771', '吉林松原经济开发区', '2207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220781', '220781', '扶余市', '2207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2208', '2208', '白城市', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220802', '220802', '洮北区', '2208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220821', '220821', '镇赉县', '2208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220822', '220822', '通榆县', '2208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220871', '220871', '吉林白城经济开发区', '2208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220881', '220881', '洮南市', '2208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('220882', '220882', '大安市', '2208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2224', '2224', '延边朝鲜族自治州', '22', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222401', '222401', '延吉市', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222402', '222402', '图们市', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222403', '222403', '敦化市', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222404', '222404', '珲春市', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222405', '222405', '龙井市', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222406', '222406', '和龙市', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222424', '222424', '汪清县', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('222426', '222426', '安图县', '2224', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('23', '23', '黑龙江省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2301', '2301', '哈尔滨市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230102', '230102', '道里区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230103', '230103', '南岗区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230104', '230104', '道外区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230108', '230108', '平房区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230109', '230109', '松北区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230110', '230110', '香坊区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230111', '230111', '呼兰区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230112', '230112', '阿城区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230113', '230113', '双城区', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230123', '230123', '依兰县', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230124', '230124', '方正县', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230125', '230125', '宾县', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230126', '230126', '巴彦县', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230127', '230127', '木兰县', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230128', '230128', '通河县', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230129', '230129', '延寿县', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230183', '230183', '尚志市', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230184', '230184', '五常市', '2301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2302', '2302', '齐齐哈尔市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230202', '230202', '龙沙区', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230203', '230203', '建华区', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230204', '230204', '铁锋区', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230205', '230205', '昂昂溪区', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230206', '230206', '富拉尔基区', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230207', '230207', '碾子山区', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230208', '230208', '梅里斯达斡尔族区', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230221', '230221', '龙江县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230223', '230223', '依安县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230224', '230224', '泰来县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230225', '230225', '甘南县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230227', '230227', '富裕县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230229', '230229', '克山县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230230', '230230', '克东县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230231', '230231', '拜泉县', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230281', '230281', '讷河市', '2302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2303', '2303', '鸡西市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230302', '230302', '鸡冠区', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230303', '230303', '恒山区', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230304', '230304', '滴道区', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230305', '230305', '梨树区', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230306', '230306', '城子河区', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230307', '230307', '麻山区', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230321', '230321', '鸡东县', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230381', '230381', '虎林市', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230382', '230382', '密山市', '2303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2304', '2304', '鹤岗市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230402', '230402', '向阳区', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230403', '230403', '工农区', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230404', '230404', '南山区', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230405', '230405', '兴安区', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230406', '230406', '东山区', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230407', '230407', '兴山区', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230421', '230421', '萝北县', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230422', '230422', '绥滨县', '2304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2305', '2305', '双鸭山市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230502', '230502', '尖山区', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230503', '230503', '岭东区', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230505', '230505', '四方台区', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230506', '230506', '宝山区', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230521', '230521', '集贤县', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230522', '230522', '友谊县', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230523', '230523', '宝清县', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230524', '230524', '饶河县', '2305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2306', '2306', '大庆市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230602', '230602', '萨尔图区', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230603', '230603', '龙凤区', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230604', '230604', '让胡路区', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230605', '230605', '红岗区', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230606', '230606', '大同区', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230621', '230621', '肇州县', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230622', '230622', '肇源县', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230623', '230623', '林甸县', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230624', '230624', '杜尔伯特蒙古族自治县', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230671', '230671', '大庆高新技术产业开发区', '2306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2307', '2307', '伊春市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230717', '230717', '伊美区', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230718', '230718', '乌翠区', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230719', '230719', '友好区', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230722', '230722', '嘉荫县', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230723', '230723', '汤旺县', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230724', '230724', '丰林县', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230725', '230725', '大箐山县', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230726', '230726', '南岔县', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230751', '230751', '金林区', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230781', '230781', '铁力市', '2307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2308', '2308', '佳木斯市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230803', '230803', '向阳区', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230804', '230804', '前进区', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230805', '230805', '东风区', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230811', '230811', '郊区', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230822', '230822', '桦南县', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230826', '230826', '桦川县', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230828', '230828', '汤原县', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230881', '230881', '同江市', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230882', '230882', '富锦市', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230883', '230883', '抚远市', '2308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2309', '2309', '七台河市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230902', '230902', '新兴区', '2309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230903', '230903', '桃山区', '2309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230904', '230904', '茄子河区', '2309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('230921', '230921', '勃利县', '2309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2310', '2310', '牡丹江市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231002', '231002', '东安区', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231003', '231003', '阳明区', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231004', '231004', '爱民区', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231005', '231005', '西安区', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231025', '231025', '林口县', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231071', '231071', '牡丹江经济技术开发区', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231081', '231081', '绥芬河市', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231083', '231083', '海林市', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231084', '231084', '宁安市', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231085', '231085', '穆棱市', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231086', '231086', '东宁市', '2310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2311', '2311', '黑河市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231102', '231102', '爱辉区', '2311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231123', '231123', '逊克县', '2311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231124', '231124', '孙吴县', '2311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231181', '231181', '北安市', '2311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231182', '231182', '五大连池市', '2311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231183', '231183', '嫩江市', '2311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2312', '2312', '绥化市', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231202', '231202', '北林区', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231221', '231221', '望奎县', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231222', '231222', '兰西县', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231223', '231223', '青冈县', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231224', '231224', '庆安县', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231225', '231225', '明水县', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231226', '231226', '绥棱县', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231281', '231281', '安达市', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231282', '231282', '肇东市', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('231283', '231283', '海伦市', '2312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('2327', '2327', '大兴安岭地区', '23', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('232701', '232701', '漠河市', '2327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('232721', '232721', '呼玛县', '2327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('232722', '232722', '塔河县', '2327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('232761', '232761', '加格达奇区', '2327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('232762', '232762', '松岭区', '2327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('232763', '232763', '新林区', '2327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('232764', '232764', '呼中区', '2327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('31', '31', '上海市', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3101', '3101', '市辖区', '31', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310101', '310101', '黄浦区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310104', '310104', '徐汇区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310105', '310105', '长宁区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310106', '310106', '静安区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310107', '310107', '普陀区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310109', '310109', '虹口区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310110', '310110', '杨浦区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310112', '310112', '闵行区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310113', '310113', '宝山区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310114', '310114', '嘉定区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310115', '310115', '浦东新区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310116', '310116', '金山区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310117', '310117', '松江区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310118', '310118', '青浦区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310120', '310120', '奉贤区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('310151', '310151', '崇明区', '3101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('32', '32', '江苏省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3201', '3201', '南京市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320102', '320102', '玄武区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320104', '320104', '秦淮区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320105', '320105', '建邺区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320106', '320106', '鼓楼区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320111', '320111', '浦口区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320113', '320113', '栖霞区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320114', '320114', '雨花台区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320115', '320115', '江宁区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320116', '320116', '六合区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320117', '320117', '溧水区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320118', '320118', '高淳区', '3201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3202', '3202', '无锡市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320205', '320205', '锡山区', '3202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320206', '320206', '惠山区', '3202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320211', '320211', '滨湖区', '3202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320213', '320213', '梁溪区', '3202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320214', '320214', '新吴区', '3202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320281', '320281', '江阴市', '3202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320282', '320282', '宜兴市', '3202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3203', '3203', '徐州市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320302', '320302', '鼓楼区', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320303', '320303', '云龙区', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320305', '320305', '贾汪区', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320311', '320311', '泉山区', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320312', '320312', '铜山区', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320321', '320321', '丰县', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320322', '320322', '沛县', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320324', '320324', '睢宁县', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320371', '320371', '徐州经济技术开发区', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320381', '320381', '新沂市', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320382', '320382', '邳州市', '3203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3204', '3204', '常州市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320402', '320402', '天宁区', '3204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320404', '320404', '钟楼区', '3204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320411', '320411', '新北区', '3204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320412', '320412', '武进区', '3204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320413', '320413', '金坛区', '3204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320481', '320481', '溧阳市', '3204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3205', '3205', '苏州市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320505', '320505', '虎丘区', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320506', '320506', '吴中区', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320507', '320507', '相城区', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320508', '320508', '姑苏区', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320509', '320509', '吴江区', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320571', '320571', '苏州工业园区', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320581', '320581', '常熟市', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320582', '320582', '张家港市', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320583', '320583', '昆山市', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320585', '320585', '太仓市', '3205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3206', '3206', '南通市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320602', '320602', '崇川区', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320611', '320611', '港闸区', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320612', '320612', '通州区', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320623', '320623', '如东县', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320671', '320671', '南通经济技术开发区', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320681', '320681', '启东市', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320682', '320682', '如皋市', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320684', '320684', '海门市', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320685', '320685', '海安市', '3206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3207', '3207', '连云港市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320703', '320703', '连云区', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320706', '320706', '海州区', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320707', '320707', '赣榆区', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320722', '320722', '东海县', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320723', '320723', '灌云县', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320724', '320724', '灌南县', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320771', '320771', '连云港经济技术开发区', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320772', '320772', '连云港高新技术产业开发区', '3207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3208', '3208', '淮安市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320803', '320803', '淮安区', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320804', '320804', '淮阴区', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320812', '320812', '清江浦区', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320813', '320813', '洪泽区', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320826', '320826', '涟水县', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320830', '320830', '盱眙县', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320831', '320831', '金湖县', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320871', '320871', '淮安经济技术开发区', '3208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3209', '3209', '盐城市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320902', '320902', '亭湖区', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320903', '320903', '盐都区', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320904', '320904', '大丰区', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320921', '320921', '响水县', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320922', '320922', '滨海县', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320923', '320923', '阜宁县', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320924', '320924', '射阳县', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320925', '320925', '建湖县', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320971', '320971', '盐城经济技术开发区', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('320981', '320981', '东台市', '3209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3210', '3210', '扬州市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321002', '321002', '广陵区', '3210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321003', '321003', '邗江区', '3210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321012', '321012', '江都区', '3210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321023', '321023', '宝应县', '3210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321071', '321071', '扬州经济技术开发区', '3210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321081', '321081', '仪征市', '3210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321084', '321084', '高邮市', '3210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3211', '3211', '镇江市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321102', '321102', '京口区', '3211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321111', '321111', '润州区', '3211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321112', '321112', '丹徒区', '3211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321171', '321171', '镇江新区', '3211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321181', '321181', '丹阳市', '3211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321182', '321182', '扬中市', '3211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321183', '321183', '句容市', '3211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3212', '3212', '泰州市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321202', '321202', '海陵区', '3212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321203', '321203', '高港区', '3212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321204', '321204', '姜堰区', '3212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321271', '321271', '泰州医药高新技术产业开发区', '3212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321281', '321281', '兴化市', '3212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321282', '321282', '靖江市', '3212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321283', '321283', '泰兴市', '3212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3213', '3213', '宿迁市', '32', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321302', '321302', '宿城区', '3213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321311', '321311', '宿豫区', '3213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321322', '321322', '沭阳县', '3213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321323', '321323', '泗阳县', '3213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321324', '321324', '泗洪县', '3213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('321371', '321371', '宿迁经济技术开发区', '3213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('33', '33', '浙江省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3301', '3301', '杭州市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330102', '330102', '上城区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330103', '330103', '下城区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330104', '330104', '江干区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330105', '330105', '拱墅区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330106', '330106', '西湖区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330108', '330108', '滨江区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330109', '330109', '萧山区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330110', '330110', '余杭区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330111', '330111', '富阳区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330112', '330112', '临安区', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330122', '330122', '桐庐县', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330127', '330127', '淳安县', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330182', '330182', '建德市', '3301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3302', '3302', '宁波市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330203', '330203', '海曙区', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330205', '330205', '江北区', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330206', '330206', '北仑区', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330211', '330211', '镇海区', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330212', '330212', '鄞州区', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330213', '330213', '奉化区', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330225', '330225', '象山县', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330226', '330226', '宁海县', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330281', '330281', '余姚市', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330282', '330282', '慈溪市', '3302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3303', '3303', '温州市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330302', '330302', '鹿城区', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330303', '330303', '龙湾区', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330304', '330304', '瓯海区', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330305', '330305', '洞头区', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330324', '330324', '永嘉县', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330326', '330326', '平阳县', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330327', '330327', '苍南县', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330328', '330328', '文成县', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330329', '330329', '泰顺县', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330371', '330371', '温州经济技术开发区', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330381', '330381', '瑞安市', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330382', '330382', '乐清市', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330383', '330383', '龙港市', '3303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3304', '3304', '嘉兴市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330402', '330402', '南湖区', '3304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330411', '330411', '秀洲区', '3304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330421', '330421', '嘉善县', '3304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330424', '330424', '海盐县', '3304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330481', '330481', '海宁市', '3304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330482', '330482', '平湖市', '3304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330483', '330483', '桐乡市', '3304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3305', '3305', '湖州市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330502', '330502', '吴兴区', '3305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330503', '330503', '南浔区', '3305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330521', '330521', '德清县', '3305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330522', '330522', '长兴县', '3305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330523', '330523', '安吉县', '3305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3306', '3306', '绍兴市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330602', '330602', '越城区', '3306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330603', '330603', '柯桥区', '3306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330604', '330604', '上虞区', '3306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330624', '330624', '新昌县', '3306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330681', '330681', '诸暨市', '3306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330683', '330683', '嵊州市', '3306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3307', '3307', '金华市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330702', '330702', '婺城区', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330703', '330703', '金东区', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330723', '330723', '武义县', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330726', '330726', '浦江县', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330727', '330727', '磐安县', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330781', '330781', '兰溪市', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330782', '330782', '义乌市', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330783', '330783', '东阳市', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330784', '330784', '永康市', '3307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3308', '3308', '衢州市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330802', '330802', '柯城区', '3308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330803', '330803', '衢江区', '3308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330822', '330822', '常山县', '3308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330824', '330824', '开化县', '3308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330825', '330825', '龙游县', '3308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330881', '330881', '江山市', '3308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3309', '3309', '舟山市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330902', '330902', '定海区', '3309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330903', '330903', '普陀区', '3309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330921', '330921', '岱山县', '3309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('330922', '330922', '嵊泗县', '3309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3310', '3310', '台州市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331002', '331002', '椒江区', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331003', '331003', '黄岩区', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331004', '331004', '路桥区', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331022', '331022', '三门县', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331023', '331023', '天台县', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331024', '331024', '仙居县', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331081', '331081', '温岭市', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331082', '331082', '临海市', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331083', '331083', '玉环市', '3310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3311', '3311', '丽水市', '33', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331102', '331102', '莲都区', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331121', '331121', '青田县', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331122', '331122', '缙云县', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331123', '331123', '遂昌县', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331124', '331124', '松阳县', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331125', '331125', '云和县', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331126', '331126', '庆元县', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331127', '331127', '景宁畲族自治县', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('331181', '331181', '龙泉市', '3311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('34', '34', '安徽省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3401', '3401', '合肥市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340102', '340102', '瑶海区', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340103', '340103', '庐阳区', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340104', '340104', '蜀山区', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340111', '340111', '包河区', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340121', '340121', '长丰县', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340122', '340122', '肥东县', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340123', '340123', '肥西县', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340124', '340124', '庐江县', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340171', '340171', '合肥高新技术产业开发区', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340172', '340172', '合肥经济技术开发区', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340173', '340173', '合肥新站高新技术产业开发区', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340181', '340181', '巢湖市', '3401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3402', '3402', '芜湖市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340202', '340202', '镜湖区', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340203', '340203', '弋江区', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340207', '340207', '鸠江区', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340208', '340208', '三山区', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340221', '340221', '芜湖县', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340222', '340222', '繁昌县', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340223', '340223', '南陵县', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340271', '340271', '芜湖经济技术开发区', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340272', '340272', '安徽芜湖长江大桥经济开发区', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340281', '340281', '无为市', '3402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3403', '3403', '蚌埠市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340302', '340302', '龙子湖区', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340303', '340303', '蚌山区', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340304', '340304', '禹会区', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340311', '340311', '淮上区', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340321', '340321', '怀远县', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340322', '340322', '五河县', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340323', '340323', '固镇县', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340371', '340371', '蚌埠市高新技术开发区', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340372', '340372', '蚌埠市经济开发区', '3403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3404', '3404', '淮南市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340402', '340402', '大通区', '3404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340403', '340403', '田家庵区', '3404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340404', '340404', '谢家集区', '3404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340405', '340405', '八公山区', '3404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340406', '340406', '潘集区', '3404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340421', '340421', '凤台县', '3404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340422', '340422', '寿县', '3404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3405', '3405', '马鞍山市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340503', '340503', '花山区', '3405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340504', '340504', '雨山区', '3405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340506', '340506', '博望区', '3405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340521', '340521', '当涂县', '3405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340522', '340522', '含山县', '3405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340523', '340523', '和县', '3405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3406', '3406', '淮北市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340602', '340602', '杜集区', '3406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340603', '340603', '相山区', '3406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340604', '340604', '烈山区', '3406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340621', '340621', '濉溪县', '3406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3407', '3407', '铜陵市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340705', '340705', '铜官区', '3407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340706', '340706', '义安区', '3407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340711', '340711', '郊区', '3407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340722', '340722', '枞阳县', '3407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3408', '3408', '安庆市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340802', '340802', '迎江区', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340803', '340803', '大观区', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340811', '340811', '宜秀区', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340822', '340822', '怀宁县', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340825', '340825', '太湖县', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340826', '340826', '宿松县', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340827', '340827', '望江县', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340828', '340828', '岳西县', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340871', '340871', '安徽安庆经济开发区', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340881', '340881', '桐城市', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('340882', '340882', '潜山市', '3408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3410', '3410', '黄山市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341002', '341002', '屯溪区', '3410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341003', '341003', '黄山区', '3410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341004', '341004', '徽州区', '3410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341021', '341021', '歙县', '3410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341022', '341022', '休宁县', '3410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341023', '341023', '黟县', '3410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341024', '341024', '祁门县', '3410', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3411', '3411', '滁州市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341102', '341102', '琅琊区', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341103', '341103', '南谯区', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341122', '341122', '来安县', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341124', '341124', '全椒县', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341125', '341125', '定远县', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341126', '341126', '凤阳县', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341171', '341171', '苏滁现代产业园', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341172', '341172', '滁州经济技术开发区', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341181', '341181', '天长市', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341182', '341182', '明光市', '3411', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3412', '3412', '阜阳市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341202', '341202', '颍州区', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341203', '341203', '颍东区', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341204', '341204', '颍泉区', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341221', '341221', '临泉县', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341222', '341222', '太和县', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341225', '341225', '阜南县', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341226', '341226', '颍上县', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341271', '341271', '阜阳合肥现代产业园区', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341272', '341272', '阜阳经济技术开发区', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341282', '341282', '界首市', '3412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3413', '3413', '宿州市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341302', '341302', '埇桥区', '3413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341321', '341321', '砀山县', '3413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341322', '341322', '萧县', '3413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341323', '341323', '灵璧县', '3413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341324', '341324', '泗县', '3413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341371', '341371', '宿州马鞍山现代产业园区', '3413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341372', '341372', '宿州经济技术开发区', '3413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3415', '3415', '六安市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341502', '341502', '金安区', '3415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341503', '341503', '裕安区', '3415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341504', '341504', '叶集区', '3415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341522', '341522', '霍邱县', '3415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341523', '341523', '舒城县', '3415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341524', '341524', '金寨县', '3415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341525', '341525', '霍山县', '3415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3416', '3416', '亳州市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341602', '341602', '谯城区', '3416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341621', '341621', '涡阳县', '3416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341622', '341622', '蒙城县', '3416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341623', '341623', '利辛县', '3416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3417', '3417', '池州市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341702', '341702', '贵池区', '3417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341721', '341721', '东至县', '3417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341722', '341722', '石台县', '3417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341723', '341723', '青阳县', '3417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3418', '3418', '宣城市', '34', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341802', '341802', '宣州区', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341821', '341821', '郎溪县', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341823', '341823', '泾县', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341824', '341824', '绩溪县', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341825', '341825', '旌德县', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341871', '341871', '宣城市经济开发区', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341881', '341881', '宁国市', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('341882', '341882', '广德市', '3418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('35', '35', '福建省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3501', '3501', '福州市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350102', '350102', '鼓楼区', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350103', '350103', '台江区', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350104', '350104', '仓山区', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350105', '350105', '马尾区', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350111', '350111', '晋安区', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350112', '350112', '长乐区', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350121', '350121', '闽侯县', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350122', '350122', '连江县', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350123', '350123', '罗源县', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350124', '350124', '闽清县', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350125', '350125', '永泰县', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350128', '350128', '平潭县', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350181', '350181', '福清市', '3501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3502', '3502', '厦门市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350203', '350203', '思明区', '3502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350205', '350205', '海沧区', '3502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350206', '350206', '湖里区', '3502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350211', '350211', '集美区', '3502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350212', '350212', '同安区', '3502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350213', '350213', '翔安区', '3502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3503', '3503', '莆田市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350302', '350302', '城厢区', '3503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350303', '350303', '涵江区', '3503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350304', '350304', '荔城区', '3503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350305', '350305', '秀屿区', '3503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350322', '350322', '仙游县', '3503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3504', '3504', '三明市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350402', '350402', '梅列区', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350403', '350403', '三元区', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350421', '350421', '明溪县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350423', '350423', '清流县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350424', '350424', '宁化县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350425', '350425', '大田县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350426', '350426', '尤溪县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350427', '350427', '沙县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350428', '350428', '将乐县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350429', '350429', '泰宁县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350430', '350430', '建宁县', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350481', '350481', '永安市', '3504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3505', '3505', '泉州市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350502', '350502', '鲤城区', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350503', '350503', '丰泽区', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350504', '350504', '洛江区', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350505', '350505', '泉港区', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350521', '350521', '惠安县', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350524', '350524', '安溪县', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350525', '350525', '永春县', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350526', '350526', '德化县', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350527', '350527', '金门县', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350581', '350581', '石狮市', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350582', '350582', '晋江市', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350583', '350583', '南安市', '3505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3506', '3506', '漳州市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350602', '350602', '芗城区', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350603', '350603', '龙文区', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350622', '350622', '云霄县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350623', '350623', '漳浦县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350624', '350624', '诏安县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350625', '350625', '长泰县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350626', '350626', '东山县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350627', '350627', '南靖县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350628', '350628', '平和县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350629', '350629', '华安县', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350681', '350681', '龙海市', '3506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3507', '3507', '南平市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350702', '350702', '延平区', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350703', '350703', '建阳区', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350721', '350721', '顺昌县', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350722', '350722', '浦城县', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350723', '350723', '光泽县', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350724', '350724', '松溪县', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350725', '350725', '政和县', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350781', '350781', '邵武市', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350782', '350782', '武夷山市', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350783', '350783', '建瓯市', '3507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3508', '3508', '龙岩市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350802', '350802', '新罗区', '3508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350803', '350803', '永定区', '3508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350821', '350821', '长汀县', '3508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350823', '350823', '上杭县', '3508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350824', '350824', '武平县', '3508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350825', '350825', '连城县', '3508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350881', '350881', '漳平市', '3508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3509', '3509', '宁德市', '35', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350902', '350902', '蕉城区', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350921', '350921', '霞浦县', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350922', '350922', '古田县', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350923', '350923', '屏南县', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350924', '350924', '寿宁县', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350925', '350925', '周宁县', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350926', '350926', '柘荣县', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350981', '350981', '福安市', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('350982', '350982', '福鼎市', '3509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('36', '36', '江西省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3601', '3601', '南昌市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360102', '360102', '东湖区', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360103', '360103', '西湖区', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360104', '360104', '青云谱区', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360111', '360111', '青山湖区', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360112', '360112', '新建区', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360113', '360113', '红谷滩区', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360121', '360121', '南昌县', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360123', '360123', '安义县', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360124', '360124', '进贤县', '3601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3602', '3602', '景德镇市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360202', '360202', '昌江区', '3602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360203', '360203', '珠山区', '3602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360222', '360222', '浮梁县', '3602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360281', '360281', '乐平市', '3602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3603', '3603', '萍乡市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360302', '360302', '安源区', '3603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360313', '360313', '湘东区', '3603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360321', '360321', '莲花县', '3603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360322', '360322', '上栗县', '3603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360323', '360323', '芦溪县', '3603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3604', '3604', '九江市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360402', '360402', '濂溪区', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360403', '360403', '浔阳区', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360404', '360404', '柴桑区', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360423', '360423', '武宁县', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360424', '360424', '修水县', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360425', '360425', '永修县', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360426', '360426', '德安县', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360428', '360428', '都昌县', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360429', '360429', '湖口县', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360430', '360430', '彭泽县', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360481', '360481', '瑞昌市', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360482', '360482', '共青城市', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360483', '360483', '庐山市', '3604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3605', '3605', '新余市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360502', '360502', '渝水区', '3605', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360521', '360521', '分宜县', '3605', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3606', '3606', '鹰潭市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360602', '360602', '月湖区', '3606', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360603', '360603', '余江区', '3606', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360681', '360681', '贵溪市', '3606', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3607', '3607', '赣州市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360702', '360702', '章贡区', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360703', '360703', '南康区', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360704', '360704', '赣县区', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360722', '360722', '信丰县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360723', '360723', '大余县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360724', '360724', '上犹县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360725', '360725', '崇义县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360726', '360726', '安远县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360728', '360728', '定南县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360729', '360729', '全南县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360730', '360730', '宁都县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360731', '360731', '于都县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360732', '360732', '兴国县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360733', '360733', '会昌县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360734', '360734', '寻乌县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360735', '360735', '石城县', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360781', '360781', '瑞金市', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360783', '360783', '龙南市', '3607', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3608', '3608', '吉安市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360802', '360802', '吉州区', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360803', '360803', '青原区', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360821', '360821', '吉安县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360822', '360822', '吉水县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360823', '360823', '峡江县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360824', '360824', '新干县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360825', '360825', '永丰县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360826', '360826', '泰和县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360827', '360827', '遂川县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360828', '360828', '万安县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360829', '360829', '安福县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360830', '360830', '永新县', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360881', '360881', '井冈山市', '3608', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3609', '3609', '宜春市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360902', '360902', '袁州区', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360921', '360921', '奉新县', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360922', '360922', '万载县', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360923', '360923', '上高县', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360924', '360924', '宜丰县', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360925', '360925', '靖安县', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360926', '360926', '铜鼓县', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360981', '360981', '丰城市', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360982', '360982', '樟树市', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('360983', '360983', '高安市', '3609', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3610', '3610', '抚州市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361002', '361002', '临川区', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361003', '361003', '东乡区', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361021', '361021', '南城县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361022', '361022', '黎川县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361023', '361023', '南丰县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361024', '361024', '崇仁县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361025', '361025', '乐安县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361026', '361026', '宜黄县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361027', '361027', '金溪县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361028', '361028', '资溪县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361030', '361030', '广昌县', '3610', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3611', '3611', '上饶市', '36', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361102', '361102', '信州区', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361103', '361103', '广丰区', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361104', '361104', '广信区', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361123', '361123', '玉山县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361124', '361124', '铅山县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361125', '361125', '横峰县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361126', '361126', '弋阳县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361127', '361127', '余干县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361128', '361128', '鄱阳县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361129', '361129', '万年县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361130', '361130', '婺源县', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('361181', '361181', '德兴市', '3611', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('37', '37', '山东省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3701', '3701', '济南市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370102', '370102', '历下区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370103', '370103', '市中区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370104', '370104', '槐荫区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370105', '370105', '天桥区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370112', '370112', '历城区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370113', '370113', '长清区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370114', '370114', '章丘区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370115', '370115', '济阳区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370116', '370116', '莱芜区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370117', '370117', '钢城区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370124', '370124', '平阴县', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370126', '370126', '商河县', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370171', '370171', '济南高新技术产业开发区', '3701', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3702', '3702', '青岛市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370202', '370202', '市南区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370203', '370203', '市北区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370211', '370211', '黄岛区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370212', '370212', '崂山区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370213', '370213', '李沧区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370214', '370214', '城阳区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370215', '370215', '即墨区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370271', '370271', '青岛高新技术产业开发区', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370281', '370281', '胶州市', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370283', '370283', '平度市', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370285', '370285', '莱西市', '3702', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3703', '3703', '淄博市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370302', '370302', '淄川区', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370303', '370303', '张店区', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370304', '370304', '博山区', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370305', '370305', '临淄区', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370306', '370306', '周村区', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370321', '370321', '桓台县', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370322', '370322', '高青县', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370323', '370323', '沂源县', '3703', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3704', '3704', '枣庄市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370402', '370402', '市中区', '3704', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370403', '370403', '薛城区', '3704', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370404', '370404', '峄城区', '3704', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370405', '370405', '台儿庄区', '3704', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370406', '370406', '山亭区', '3704', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370481', '370481', '滕州市', '3704', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3705', '3705', '东营市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370502', '370502', '东营区', '3705', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370503', '370503', '河口区', '3705', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370505', '370505', '垦利区', '3705', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370522', '370522', '利津县', '3705', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370523', '370523', '广饶县', '3705', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370571', '370571', '东营经济技术开发区', '3705', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370572', '370572', '东营港经济开发区', '3705', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3706', '3706', '烟台市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370602', '370602', '芝罘区', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370611', '370611', '福山区', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370612', '370612', '牟平区', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370613', '370613', '莱山区', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370614', '370614', '蓬莱区', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370671', '370671', '烟台高新技术产业开发区', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370672', '370672', '烟台经济技术开发区', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370681', '370681', '龙口市', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370682', '370682', '莱阳市', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370683', '370683', '莱州市', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370685', '370685', '招远市', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370686', '370686', '栖霞市', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370687', '370687', '海阳市', '3706', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3707', '3707', '潍坊市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370702', '370702', '潍城区', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370703', '370703', '寒亭区', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370704', '370704', '坊子区', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370705', '370705', '奎文区', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370724', '370724', '临朐县', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370725', '370725', '昌乐县', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370772', '370772', '潍坊滨海经济技术开发区', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370781', '370781', '青州市', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370782', '370782', '诸城市', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370783', '370783', '寿光市', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370784', '370784', '安丘市', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370785', '370785', '高密市', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370786', '370786', '昌邑市', '3707', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3708', '3708', '济宁市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370811', '370811', '任城区', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370812', '370812', '兖州区', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370826', '370826', '微山县', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370827', '370827', '鱼台县', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370828', '370828', '金乡县', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370829', '370829', '嘉祥县', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370830', '370830', '汶上县', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370831', '370831', '泗水县', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370832', '370832', '梁山县', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370871', '370871', '济宁高新技术产业开发区', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370881', '370881', '曲阜市', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370883', '370883', '邹城市', '3708', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3709', '3709', '泰安市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370902', '370902', '泰山区', '3709', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370911', '370911', '岱岳区', '3709', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370921', '370921', '宁阳县', '3709', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370923', '370923', '东平县', '3709', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370982', '370982', '新泰市', '3709', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('370983', '370983', '肥城市', '3709', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3710', '3710', '威海市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371002', '371002', '环翠区', '3710', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371003', '371003', '文登区', '3710', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371071', '371071', '威海火炬高技术产业开发区', '3710', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371072', '371072', '威海经济技术开发区', '3710', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371073', '371073', '威海临港经济技术开发区', '3710', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371082', '371082', '荣成市', '3710', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371083', '371083', '乳山市', '3710', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3711', '3711', '日照市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371102', '371102', '东港区', '3711', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371103', '371103', '岚山区', '3711', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371121', '371121', '五莲县', '3711', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371122', '371122', '莒县', '3711', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371171', '371171', '日照经济技术开发区', '3711', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3713', '3713', '临沂市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371302', '371302', '兰山区', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371311', '371311', '罗庄区', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371312', '371312', '河东区', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371321', '371321', '沂南县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371322', '371322', '郯城县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371323', '371323', '沂水县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371324', '371324', '兰陵县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371325', '371325', '费县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371326', '371326', '平邑县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371327', '371327', '莒南县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371328', '371328', '蒙阴县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371329', '371329', '临沭县', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371371', '371371', '临沂高新技术产业开发区', '3713', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3714', '3714', '德州市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371402', '371402', '德城区', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371403', '371403', '陵城区', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371422', '371422', '宁津县', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371423', '371423', '庆云县', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371424', '371424', '临邑县', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371425', '371425', '齐河县', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371426', '371426', '平原县', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371427', '371427', '夏津县', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371428', '371428', '武城县', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371471', '371471', '德州经济技术开发区', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371472', '371472', '德州运河经济开发区', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371481', '371481', '乐陵市', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371482', '371482', '禹城市', '3714', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3715', '3715', '聊城市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371502', '371502', '东昌府区', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371503', '371503', '茌平区', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371521', '371521', '阳谷县', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371522', '371522', '莘县', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371524', '371524', '东阿县', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371525', '371525', '冠县', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371526', '371526', '高唐县', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371581', '371581', '临清市', '3715', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3716', '3716', '滨州市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371602', '371602', '滨城区', '3716', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371603', '371603', '沾化区', '3716', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371621', '371621', '惠民县', '3716', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371622', '371622', '阳信县', '3716', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371623', '371623', '无棣县', '3716', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371625', '371625', '博兴县', '3716', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371681', '371681', '邹平市', '3716', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('3717', '3717', '菏泽市', '37', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371702', '371702', '牡丹区', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371703', '371703', '定陶区', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371721', '371721', '曹县', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371722', '371722', '单县', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371723', '371723', '成武县', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371724', '371724', '巨野县', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371725', '371725', '郓城县', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371726', '371726', '鄄城县', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371728', '371728', '东明县', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371771', '371771', '菏泽经济技术开发区', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('371772', '371772', '菏泽高新技术开发区', '3717', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('41', '41', '河南省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4101', '4101', '郑州市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410102', '410102', '中原区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410103', '410103', '二七区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410104', '410104', '管城回族区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410105', '410105', '金水区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410106', '410106', '上街区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410108', '410108', '惠济区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410122', '410122', '中牟县', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410171', '410171', '郑州经济技术开发区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410172', '410172', '郑州高新技术产业开发区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410173', '410173', '郑州航空港经济综合实验区', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410181', '410181', '巩义市', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410182', '410182', '荥阳市', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410183', '410183', '新密市', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410184', '410184', '新郑市', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410185', '410185', '登封市', '4101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4102', '4102', '开封市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410202', '410202', '龙亭区', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410203', '410203', '顺河回族区', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410204', '410204', '鼓楼区', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410205', '410205', '禹王台区', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410212', '410212', '祥符区', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410221', '410221', '杞县', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410222', '410222', '通许县', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410223', '410223', '尉氏县', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410225', '410225', '兰考县', '4102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4103', '4103', '洛阳市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410302', '410302', '老城区', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410303', '410303', '西工区', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410304', '410304', '瀍河回族区', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410305', '410305', '涧西区', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410306', '410306', '吉利区', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410311', '410311', '洛龙区', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410322', '410322', '孟津县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410323', '410323', '新安县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410324', '410324', '栾川县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410325', '410325', '嵩县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410326', '410326', '汝阳县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410327', '410327', '宜阳县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410328', '410328', '洛宁县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410329', '410329', '伊川县', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410371', '410371', '洛阳高新技术产业开发区', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410381', '410381', '偃师市', '4103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4104', '4104', '平顶山市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410402', '410402', '新华区', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410403', '410403', '卫东区', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410404', '410404', '石龙区', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410411', '410411', '湛河区', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410421', '410421', '宝丰县', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410422', '410422', '叶县', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410423', '410423', '鲁山县', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410425', '410425', '郏县', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410471', '410471', '平顶山高新技术产业开发区', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410472', '410472', '平顶山市城乡一体化示范区', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410481', '410481', '舞钢市', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410482', '410482', '汝州市', '4104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4105', '4105', '安阳市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410502', '410502', '文峰区', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410503', '410503', '北关区', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410505', '410505', '殷都区', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410506', '410506', '龙安区', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410522', '410522', '安阳县', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410523', '410523', '汤阴县', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410526', '410526', '滑县', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410527', '410527', '内黄县', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410571', '410571', '安阳高新技术产业开发区', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410581', '410581', '林州市', '4105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4106', '4106', '鹤壁市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410602', '410602', '鹤山区', '4106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410603', '410603', '山城区', '4106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410611', '410611', '淇滨区', '4106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410621', '410621', '浚县', '4106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410622', '410622', '淇县', '4106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410671', '410671', '鹤壁经济技术开发区', '4106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4107', '4107', '新乡市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410702', '410702', '红旗区', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410703', '410703', '卫滨区', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410704', '410704', '凤泉区', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410711', '410711', '牧野区', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410721', '410721', '新乡县', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410724', '410724', '获嘉县', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410725', '410725', '原阳县', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410726', '410726', '延津县', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410727', '410727', '封丘县', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410771', '410771', '新乡高新技术产业开发区', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410772', '410772', '新乡经济技术开发区', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410773', '410773', '新乡市平原城乡一体化示范区', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410781', '410781', '卫辉市', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410782', '410782', '辉县市', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410783', '410783', '长垣市', '4107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4108', '4108', '焦作市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410802', '410802', '解放区', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410803', '410803', '中站区', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410804', '410804', '马村区', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410811', '410811', '山阳区', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410821', '410821', '修武县', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410822', '410822', '博爱县', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410823', '410823', '武陟县', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410825', '410825', '温县', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410871', '410871', '焦作城乡一体化示范区', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410882', '410882', '沁阳市', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410883', '410883', '孟州市', '4108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4109', '4109', '濮阳市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410902', '410902', '华龙区', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410922', '410922', '清丰县', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410923', '410923', '南乐县', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410926', '410926', '范县', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410927', '410927', '台前县', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410928', '410928', '濮阳县', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410971', '410971', '河南濮阳工业园区', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('410972', '410972', '濮阳经济技术开发区', '4109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4110', '4110', '许昌市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411002', '411002', '魏都区', '4110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411003', '411003', '建安区', '4110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411024', '411024', '鄢陵县', '4110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411025', '411025', '襄城县', '4110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411071', '411071', '许昌经济技术开发区', '4110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411081', '411081', '禹州市', '4110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411082', '411082', '长葛市', '4110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4111', '4111', '漯河市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411102', '411102', '源汇区', '4111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411103', '411103', '郾城区', '4111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411104', '411104', '召陵区', '4111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411121', '411121', '舞阳县', '4111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411122', '411122', '临颍县', '4111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411171', '411171', '漯河经济技术开发区', '4111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4112', '4112', '三门峡市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411202', '411202', '湖滨区', '4112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411203', '411203', '陕州区', '4112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411221', '411221', '渑池县', '4112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411224', '411224', '卢氏县', '4112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411271', '411271', '河南三门峡经济开发区', '4112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411281', '411281', '义马市', '4112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411282', '411282', '灵宝市', '4112', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4113', '4113', '南阳市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411302', '411302', '宛城区', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411303', '411303', '卧龙区', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411321', '411321', '南召县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411322', '411322', '方城县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411323', '411323', '西峡县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411324', '411324', '镇平县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411325', '411325', '内乡县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411326', '411326', '淅川县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411327', '411327', '社旗县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411328', '411328', '唐河县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411329', '411329', '新野县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411330', '411330', '桐柏县', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411371', '411371', '南阳高新技术产业开发区', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411372', '411372', '南阳市城乡一体化示范区', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411381', '411381', '邓州市', '4113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4114', '4114', '商丘市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411402', '411402', '梁园区', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411403', '411403', '睢阳区', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411421', '411421', '民权县', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411422', '411422', '睢县', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411423', '411423', '宁陵县', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411424', '411424', '柘城县', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411425', '411425', '虞城县', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411426', '411426', '夏邑县', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411471', '411471', '豫东综合物流产业聚集区', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411472', '411472', '河南商丘经济开发区', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411481', '411481', '永城市', '4114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4115', '4115', '信阳市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411502', '411502', '浉河区', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411503', '411503', '平桥区', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411521', '411521', '罗山县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411522', '411522', '光山县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411523', '411523', '新县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411524', '411524', '商城县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411525', '411525', '固始县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411526', '411526', '潢川县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411527', '411527', '淮滨县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411528', '411528', '息县', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411571', '411571', '信阳高新技术产业开发区', '4115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4116', '4116', '周口市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411602', '411602', '川汇区', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411603', '411603', '淮阳区', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411621', '411621', '扶沟县', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411622', '411622', '西华县', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411623', '411623', '商水县', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411624', '411624', '沈丘县', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411625', '411625', '郸城县', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411627', '411627', '太康县', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411628', '411628', '鹿邑县', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411671', '411671', '河南周口经济开发区', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411681', '411681', '项城市', '4116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4117', '4117', '驻马店市', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411702', '411702', '驿城区', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411721', '411721', '西平县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411722', '411722', '上蔡县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411723', '411723', '平舆县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411724', '411724', '正阳县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411725', '411725', '确山县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411726', '411726', '泌阳县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411727', '411727', '汝南县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411728', '411728', '遂平县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411729', '411729', '新蔡县', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('411771', '411771', '河南驻马店经济开发区', '4117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4190', '4190', '省直辖县级行政区划', '41', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('419001', '419001', '济源市', '4190', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('42', '42', '湖北省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4201', '4201', '武汉市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420102', '420102', '江岸区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420103', '420103', '江汉区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420104', '420104', '硚口区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420105', '420105', '汉阳区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420106', '420106', '武昌区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420107', '420107', '青山区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420111', '420111', '洪山区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420112', '420112', '东西湖区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420113', '420113', '汉南区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420114', '420114', '蔡甸区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420115', '420115', '江夏区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420116', '420116', '黄陂区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420117', '420117', '新洲区', '4201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4202', '4202', '黄石市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420202', '420202', '黄石港区', '4202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420203', '420203', '西塞山区', '4202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420204', '420204', '下陆区', '4202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420205', '420205', '铁山区', '4202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420222', '420222', '阳新县', '4202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420281', '420281', '大冶市', '4202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4203', '4203', '十堰市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420302', '420302', '茅箭区', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420303', '420303', '张湾区', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420304', '420304', '郧阳区', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420322', '420322', '郧西县', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420323', '420323', '竹山县', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420324', '420324', '竹溪县', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420325', '420325', '房县', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420381', '420381', '丹江口市', '4203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4205', '4205', '宜昌市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420502', '420502', '西陵区', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420503', '420503', '伍家岗区', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420504', '420504', '点军区', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420505', '420505', '猇亭区', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420506', '420506', '夷陵区', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420525', '420525', '远安县', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420526', '420526', '兴山县', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420527', '420527', '秭归县', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420528', '420528', '长阳土家族自治县', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420529', '420529', '五峰土家族自治县', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420581', '420581', '宜都市', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420582', '420582', '当阳市', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420583', '420583', '枝江市', '4205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4206', '4206', '襄阳市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420602', '420602', '襄城区', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420606', '420606', '樊城区', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420607', '420607', '襄州区', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420624', '420624', '南漳县', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420625', '420625', '谷城县', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420626', '420626', '保康县', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420682', '420682', '老河口市', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420683', '420683', '枣阳市', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420684', '420684', '宜城市', '4206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4207', '4207', '鄂州市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420702', '420702', '梁子湖区', '4207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420703', '420703', '华容区', '4207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420704', '420704', '鄂城区', '4207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4208', '4208', '荆门市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420802', '420802', '东宝区', '4208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420804', '420804', '掇刀区', '4208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420822', '420822', '沙洋县', '4208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420881', '420881', '钟祥市', '4208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420882', '420882', '京山市', '4208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4209', '4209', '孝感市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420902', '420902', '孝南区', '4209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420921', '420921', '孝昌县', '4209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420922', '420922', '大悟县', '4209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420923', '420923', '云梦县', '4209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420981', '420981', '应城市', '4209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420982', '420982', '安陆市', '4209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('420984', '420984', '汉川市', '4209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4210', '4210', '荆州市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421002', '421002', '沙市区', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421003', '421003', '荆州区', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421022', '421022', '公安县', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421023', '421023', '监利县', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421024', '421024', '江陵县', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421071', '421071', '荆州经济技术开发区', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421081', '421081', '石首市', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421083', '421083', '洪湖市', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421087', '421087', '松滋市', '4210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4211', '4211', '黄冈市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421102', '421102', '黄州区', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421121', '421121', '团风县', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421122', '421122', '红安县', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421123', '421123', '罗田县', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421124', '421124', '英山县', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421125', '421125', '浠水县', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421126', '421126', '蕲春县', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421127', '421127', '黄梅县', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421171', '421171', '龙感湖管理区', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421181', '421181', '麻城市', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421182', '421182', '武穴市', '4211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4212', '4212', '咸宁市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421202', '421202', '咸安区', '4212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421221', '421221', '嘉鱼县', '4212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421222', '421222', '通城县', '4212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421223', '421223', '崇阳县', '4212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421224', '421224', '通山县', '4212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421281', '421281', '赤壁市', '4212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4213', '4213', '随州市', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421303', '421303', '曾都区', '4213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421321', '421321', '随县', '4213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('421381', '421381', '广水市', '4213', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4228', '4228', '恩施土家族苗族自治州', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422801', '422801', '恩施市', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422802', '422802', '利川市', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422822', '422822', '建始县', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422823', '422823', '巴东县', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422825', '422825', '宣恩县', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422826', '422826', '咸丰县', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422827', '422827', '来凤县', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('422828', '422828', '鹤峰县', '4228', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4290', '4290', '省直辖县级行政区划', '42', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('429004', '429004', '仙桃市', '4290', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('429005', '429005', '潜江市', '4290', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('429006', '429006', '天门市', '4290', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('429021', '429021', '神农架林区', '4290', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('43', '43', '湖南省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4301', '4301', '长沙市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430102', '430102', '芙蓉区', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430103', '430103', '天心区', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430104', '430104', '岳麓区', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430105', '430105', '开福区', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430111', '430111', '雨花区', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430112', '430112', '望城区', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430121', '430121', '长沙县', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430181', '430181', '浏阳市', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430182', '430182', '宁乡市', '4301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4302', '4302', '株洲市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430202', '430202', '荷塘区', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430203', '430203', '芦淞区', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430204', '430204', '石峰区', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430211', '430211', '天元区', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430212', '430212', '渌口区', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430223', '430223', '攸县', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430224', '430224', '茶陵县', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430225', '430225', '炎陵县', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430271', '430271', '云龙示范区', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430281', '430281', '醴陵市', '4302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4303', '4303', '湘潭市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430302', '430302', '雨湖区', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430304', '430304', '岳塘区', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430321', '430321', '湘潭县', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430371', '430371', '湖南湘潭高新技术产业园区', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430372', '430372', '湘潭昭山示范区', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430373', '430373', '湘潭九华示范区', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430381', '430381', '湘乡市', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430382', '430382', '韶山市', '4303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4304', '4304', '衡阳市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430405', '430405', '珠晖区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430406', '430406', '雁峰区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430407', '430407', '石鼓区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430408', '430408', '蒸湘区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430412', '430412', '南岳区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430421', '430421', '衡阳县', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430422', '430422', '衡南县', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430423', '430423', '衡山县', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430424', '430424', '衡东县', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430426', '430426', '祁东县', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430471', '430471', '衡阳综合保税区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430472', '430472', '湖南衡阳高新技术产业园区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430473', '430473', '湖南衡阳松木经济开发区', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430481', '430481', '耒阳市', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430482', '430482', '常宁市', '4304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4305', '4305', '邵阳市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430502', '430502', '双清区', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430503', '430503', '大祥区', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430511', '430511', '北塔区', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430522', '430522', '新邵县', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430523', '430523', '邵阳县', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430524', '430524', '隆回县', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430525', '430525', '洞口县', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430527', '430527', '绥宁县', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430528', '430528', '新宁县', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430529', '430529', '城步苗族自治县', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430581', '430581', '武冈市', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430582', '430582', '邵东市', '4305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4306', '4306', '岳阳市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430602', '430602', '岳阳楼区', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430603', '430603', '云溪区', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430611', '430611', '君山区', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430621', '430621', '岳阳县', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430623', '430623', '华容县', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430624', '430624', '湘阴县', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430626', '430626', '平江县', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430671', '430671', '岳阳市屈原管理区', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430681', '430681', '汨罗市', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430682', '430682', '临湘市', '4306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4307', '4307', '常德市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430702', '430702', '武陵区', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430703', '430703', '鼎城区', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430721', '430721', '安乡县', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430722', '430722', '汉寿县', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430723', '430723', '澧县', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430724', '430724', '临澧县', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430725', '430725', '桃源县', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430726', '430726', '石门县', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430771', '430771', '常德市西洞庭管理区', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430781', '430781', '津市市', '4307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4308', '4308', '张家界市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430802', '430802', '永定区', '4308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430811', '430811', '武陵源区', '4308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430821', '430821', '慈利县', '4308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430822', '430822', '桑植县', '4308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4309', '4309', '益阳市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430902', '430902', '资阳区', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430903', '430903', '赫山区', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430921', '430921', '南县', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430922', '430922', '桃江县', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430923', '430923', '安化县', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430971', '430971', '益阳市大通湖管理区', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430972', '430972', '湖南益阳高新技术产业园区', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('430981', '430981', '沅江市', '4309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4310', '4310', '郴州市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431002', '431002', '北湖区', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431003', '431003', '苏仙区', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431021', '431021', '桂阳县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431022', '431022', '宜章县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431023', '431023', '永兴县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431024', '431024', '嘉禾县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431025', '431025', '临武县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431026', '431026', '汝城县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431027', '431027', '桂东县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431028', '431028', '安仁县', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431081', '431081', '资兴市', '4310', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4311', '4311', '永州市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431102', '431102', '零陵区', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431103', '431103', '冷水滩区', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431121', '431121', '祁阳县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431122', '431122', '东安县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431123', '431123', '双牌县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431124', '431124', '道县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431125', '431125', '江永县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431126', '431126', '宁远县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431127', '431127', '蓝山县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431128', '431128', '新田县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431129', '431129', '江华瑶族自治县', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431171', '431171', '永州经济技术开发区', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431172', '431172', '永州市金洞管理区', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431173', '431173', '永州市回龙圩管理区', '4311', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4312', '4312', '怀化市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431202', '431202', '鹤城区', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431221', '431221', '中方县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431222', '431222', '沅陵县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431223', '431223', '辰溪县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431224', '431224', '溆浦县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431225', '431225', '会同县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431226', '431226', '麻阳苗族自治县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431227', '431227', '新晃侗族自治县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431228', '431228', '芷江侗族自治县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431229', '431229', '靖州苗族侗族自治县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431230', '431230', '通道侗族自治县', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431271', '431271', '怀化市洪江管理区', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431281', '431281', '洪江市', '4312', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4313', '4313', '娄底市', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431302', '431302', '娄星区', '4313', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431321', '431321', '双峰县', '4313', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431322', '431322', '新化县', '4313', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431381', '431381', '冷水江市', '4313', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('431382', '431382', '涟源市', '4313', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4331', '4331', '湘西土家族苗族自治州', '43', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433101', '433101', '吉首市', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433122', '433122', '泸溪县', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433123', '433123', '凤凰县', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433124', '433124', '花垣县', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433125', '433125', '保靖县', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433126', '433126', '古丈县', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433127', '433127', '永顺县', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('433130', '433130', '龙山县', '4331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('44', '44', '广东省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4401', '4401', '广州市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440103', '440103', '荔湾区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440104', '440104', '越秀区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440105', '440105', '海珠区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440106', '440106', '天河区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440111', '440111', '白云区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440112', '440112', '黄埔区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440113', '440113', '番禺区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440114', '440114', '花都区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440115', '440115', '南沙区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440117', '440117', '从化区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440118', '440118', '增城区', '4401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4402', '4402', '韶关市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440203', '440203', '武江区', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440204', '440204', '浈江区', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440205', '440205', '曲江区', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440222', '440222', '始兴县', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440224', '440224', '仁化县', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440229', '440229', '翁源县', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440232', '440232', '乳源瑶族自治县', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440233', '440233', '新丰县', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440281', '440281', '乐昌市', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440282', '440282', '南雄市', '4402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4403', '4403', '深圳市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440303', '440303', '罗湖区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440304', '440304', '福田区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440305', '440305', '南山区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440306', '440306', '宝安区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440307', '440307', '龙岗区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440308', '440308', '盐田区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440309', '440309', '龙华区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440310', '440310', '坪山区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440311', '440311', '光明区', '4403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4404', '4404', '珠海市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440402', '440402', '香洲区', '4404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440403', '440403', '斗门区', '4404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440404', '440404', '金湾区', '4404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4405', '4405', '汕头市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440507', '440507', '龙湖区', '4405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440511', '440511', '金平区', '4405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440512', '440512', '濠江区', '4405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440513', '440513', '潮阳区', '4405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440514', '440514', '潮南区', '4405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440515', '440515', '澄海区', '4405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440523', '440523', '南澳县', '4405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4406', '4406', '佛山市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440604', '440604', '禅城区', '4406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440605', '440605', '南海区', '4406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440606', '440606', '顺德区', '4406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440607', '440607', '三水区', '4406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440608', '440608', '高明区', '4406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4407', '4407', '江门市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440703', '440703', '蓬江区', '4407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440704', '440704', '江海区', '4407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440705', '440705', '新会区', '4407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440781', '440781', '台山市', '4407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440783', '440783', '开平市', '4407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440784', '440784', '鹤山市', '4407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440785', '440785', '恩平市', '4407', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4408', '4408', '湛江市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440802', '440802', '赤坎区', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440803', '440803', '霞山区', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440804', '440804', '坡头区', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440811', '440811', '麻章区', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440823', '440823', '遂溪县', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440825', '440825', '徐闻县', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440881', '440881', '廉江市', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440882', '440882', '雷州市', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440883', '440883', '吴川市', '4408', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4409', '4409', '茂名市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440902', '440902', '茂南区', '4409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440904', '440904', '电白区', '4409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440981', '440981', '高州市', '4409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440982', '440982', '化州市', '4409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('440983', '440983', '信宜市', '4409', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4412', '4412', '肇庆市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441202', '441202', '端州区', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441203', '441203', '鼎湖区', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441204', '441204', '高要区', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441223', '441223', '广宁县', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441224', '441224', '怀集县', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441225', '441225', '封开县', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441226', '441226', '德庆县', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441284', '441284', '四会市', '4412', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4413', '4413', '惠州市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441302', '441302', '惠城区', '4413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441303', '441303', '惠阳区', '4413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441322', '441322', '博罗县', '4413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441323', '441323', '惠东县', '4413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441324', '441324', '龙门县', '4413', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4414', '4414', '梅州市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441402', '441402', '梅江区', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441403', '441403', '梅县区', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441422', '441422', '大埔县', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441423', '441423', '丰顺县', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441424', '441424', '五华县', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441426', '441426', '平远县', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441427', '441427', '蕉岭县', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441481', '441481', '兴宁市', '4414', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4415', '4415', '汕尾市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441502', '441502', '城区', '4415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441521', '441521', '海丰县', '4415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441523', '441523', '陆河县', '4415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441581', '441581', '陆丰市', '4415', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4416', '4416', '河源市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441602', '441602', '源城区', '4416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441621', '441621', '紫金县', '4416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441622', '441622', '龙川县', '4416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441623', '441623', '连平县', '4416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441624', '441624', '和平县', '4416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441625', '441625', '东源县', '4416', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4417', '4417', '阳江市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441702', '441702', '江城区', '4417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441704', '441704', '阳东区', '4417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441721', '441721', '阳西县', '4417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441781', '441781', '阳春市', '4417', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4418', '4418', '清远市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441802', '441802', '清城区', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441803', '441803', '清新区', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441821', '441821', '佛冈县', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441823', '441823', '阳山县', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441825', '441825', '连山壮族瑶族自治县', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441826', '441826', '连南瑶族自治县', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441881', '441881', '英德市', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441882', '441882', '连州市', '4418', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4419', '4419', '东莞市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900003', '441900003', '东城街道', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900004', '441900004', '南城街道', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900005', '441900005', '万江街道', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900006', '441900006', '莞城街道', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900101', '441900101', '石碣镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900102', '441900102', '石龙镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900103', '441900103', '茶山镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900104', '441900104', '石排镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900105', '441900105', '企石镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900106', '441900106', '横沥镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900107', '441900107', '桥头镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900108', '441900108', '谢岗镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900109', '441900109', '东坑镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900110', '441900110', '常平镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900111', '441900111', '寮步镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900112', '441900112', '樟木头镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900113', '441900113', '大朗镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900114', '441900114', '黄江镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900115', '441900115', '清溪镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900116', '441900116', '塘厦镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900117', '441900117', '凤岗镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900118', '441900118', '大岭山镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900119', '441900119', '长安镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900121', '441900121', '虎门镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900122', '441900122', '厚街镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900123', '441900123', '沙田镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900124', '441900124', '道滘镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900125', '441900125', '洪梅镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900126', '441900126', '麻涌镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900127', '441900127', '望牛墩镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900128', '441900128', '中堂镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900129', '441900129', '高埗镇', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900401', '441900401', '松山湖', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900402', '441900402', '东莞港', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('441900403', '441900403', '东莞生态园', '4419', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4420', '4420', '中山市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000001', '442000001', '石岐街道', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000002', '442000002', '东区街道', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000003', '442000003', '中山港街道', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000004', '442000004', '西区街道', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000005', '442000005', '南区街道', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000006', '442000006', '五桂山街道', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000100', '442000100', '小榄镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000101', '442000101', '黄圃镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000102', '442000102', '民众镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000103', '442000103', '东凤镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000104', '442000104', '东升镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000105', '442000105', '古镇镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000106', '442000106', '沙溪镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000107', '442000107', '坦洲镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000108', '442000108', '港口镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000109', '442000109', '三角镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000110', '442000110', '横栏镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000111', '442000111', '南头镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000112', '442000112', '阜沙镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000113', '442000113', '南朗镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000114', '442000114', '三乡镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000115', '442000115', '板芙镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000116', '442000116', '大涌镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('442000117', '442000117', '神湾镇', '4420', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4451', '4451', '潮州市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445102', '445102', '湘桥区', '4451', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445103', '445103', '潮安区', '4451', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445122', '445122', '饶平县', '4451', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4452', '4452', '揭阳市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445202', '445202', '榕城区', '4452', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445203', '445203', '揭东区', '4452', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445222', '445222', '揭西县', '4452', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445224', '445224', '惠来县', '4452', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445281', '445281', '普宁市', '4452', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4453', '4453', '云浮市', '44', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445302', '445302', '云城区', '4453', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445303', '445303', '云安区', '4453', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445321', '445321', '新兴县', '4453', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445322', '445322', '郁南县', '4453', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('445381', '445381', '罗定市', '4453', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('45', '45', '广西壮族自治区', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4501', '4501', '南宁市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450102', '450102', '兴宁区', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450103', '450103', '青秀区', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450105', '450105', '江南区', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450107', '450107', '西乡塘区', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450108', '450108', '良庆区', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450109', '450109', '邕宁区', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450110', '450110', '武鸣区', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450123', '450123', '隆安县', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450124', '450124', '马山县', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450125', '450125', '上林县', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450126', '450126', '宾阳县', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450127', '450127', '横县', '4501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4502', '4502', '柳州市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450202', '450202', '城中区', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450203', '450203', '鱼峰区', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450204', '450204', '柳南区', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450205', '450205', '柳北区', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450206', '450206', '柳江区', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450222', '450222', '柳城县', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450223', '450223', '鹿寨县', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450224', '450224', '融安县', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450225', '450225', '融水苗族自治县', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450226', '450226', '三江侗族自治县', '4502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4503', '4503', '桂林市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450302', '450302', '秀峰区', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450303', '450303', '叠彩区', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450304', '450304', '象山区', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450305', '450305', '七星区', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450311', '450311', '雁山区', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450312', '450312', '临桂区', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450321', '450321', '阳朔县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450323', '450323', '灵川县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450324', '450324', '全州县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450325', '450325', '兴安县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450326', '450326', '永福县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450327', '450327', '灌阳县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450328', '450328', '龙胜各族自治县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450329', '450329', '资源县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450330', '450330', '平乐县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450332', '450332', '恭城瑶族自治县', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450381', '450381', '荔浦市', '4503', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4504', '4504', '梧州市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450403', '450403', '万秀区', '4504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450405', '450405', '长洲区', '4504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450406', '450406', '龙圩区', '4504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450421', '450421', '苍梧县', '4504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450422', '450422', '藤县', '4504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450423', '450423', '蒙山县', '4504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450481', '450481', '岑溪市', '4504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4505', '4505', '北海市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450502', '450502', '海城区', '4505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450503', '450503', '银海区', '4505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450512', '450512', '铁山港区', '4505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450521', '450521', '合浦县', '4505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4506', '4506', '防城港市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450602', '450602', '港口区', '4506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450603', '450603', '防城区', '4506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450621', '450621', '上思县', '4506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450681', '450681', '东兴市', '4506', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4507', '4507', '钦州市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450702', '450702', '钦南区', '4507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450703', '450703', '钦北区', '4507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450721', '450721', '灵山县', '4507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450722', '450722', '浦北县', '4507', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4508', '4508', '贵港市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450802', '450802', '港北区', '4508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450803', '450803', '港南区', '4508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450804', '450804', '覃塘区', '4508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450821', '450821', '平南县', '4508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450881', '450881', '桂平市', '4508', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4509', '4509', '玉林市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450902', '450902', '玉州区', '4509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450903', '450903', '福绵区', '4509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450921', '450921', '容县', '4509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450922', '450922', '陆川县', '4509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450923', '450923', '博白县', '4509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450924', '450924', '兴业县', '4509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('450981', '450981', '北流市', '4509', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4510', '4510', '百色市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451002', '451002', '右江区', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451003', '451003', '田阳区', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451022', '451022', '田东县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451024', '451024', '德保县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451026', '451026', '那坡县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451027', '451027', '凌云县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451028', '451028', '乐业县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451029', '451029', '田林县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451030', '451030', '西林县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451031', '451031', '隆林各族自治县', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451081', '451081', '靖西市', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451082', '451082', '平果市', '4510', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4511', '4511', '贺州市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451102', '451102', '八步区', '4511', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451103', '451103', '平桂区', '4511', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451121', '451121', '昭平县', '4511', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451122', '451122', '钟山县', '4511', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451123', '451123', '富川瑶族自治县', '4511', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4512', '4512', '河池市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451202', '451202', '金城江区', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451203', '451203', '宜州区', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451221', '451221', '南丹县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451222', '451222', '天峨县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451223', '451223', '凤山县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451224', '451224', '东兰县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451225', '451225', '罗城仫佬族自治县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451226', '451226', '环江毛南族自治县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451227', '451227', '巴马瑶族自治县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451228', '451228', '都安瑶族自治县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451229', '451229', '大化瑶族自治县', '4512', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4513', '4513', '来宾市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451302', '451302', '兴宾区', '4513', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451321', '451321', '忻城县', '4513', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451322', '451322', '象州县', '4513', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451323', '451323', '武宣县', '4513', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451324', '451324', '金秀瑶族自治县', '4513', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451381', '451381', '合山市', '4513', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4514', '4514', '崇左市', '45', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451402', '451402', '江州区', '4514', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451421', '451421', '扶绥县', '4514', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451422', '451422', '宁明县', '4514', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451423', '451423', '龙州县', '4514', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451424', '451424', '大新县', '4514', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451425', '451425', '天等县', '4514', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('451481', '451481', '凭祥市', '4514', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('46', '46', '海南省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4601', '4601', '海口市', '46', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460105', '460105', '秀英区', '4601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460106', '460106', '龙华区', '4601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460107', '460107', '琼山区', '4601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460108', '460108', '美兰区', '4601', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4602', '4602', '三亚市', '46', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460202', '460202', '海棠区', '4602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460203', '460203', '吉阳区', '4602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460204', '460204', '天涯区', '4602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460205', '460205', '崖州区', '4602', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4603', '4603', '三沙市', '46', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460321', '460321', '西沙群岛', '4603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460322', '460322', '南沙群岛', '4603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460323', '460323', '中沙群岛的岛礁及其海域', '4603', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4604', '4604', '儋州市', '46', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400100', '460400100', '那大镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400101', '460400101', '和庆镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400102', '460400102', '南丰镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400103', '460400103', '大成镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400104', '460400104', '雅星镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400105', '460400105', '兰洋镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400106', '460400106', '光村镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400107', '460400107', '木棠镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400108', '460400108', '海头镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400109', '460400109', '峨蔓镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400111', '460400111', '王五镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400112', '460400112', '白马井镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400113', '460400113', '中和镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400114', '460400114', '排浦镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400115', '460400115', '东成镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400116', '460400116', '新州镇', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400499', '460400499', '洋浦经济开发区', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('460400500', '460400500', '华南热作学院', '4604', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('4690', '4690', '省直辖县级行政区划', '46', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469001', '469001', '五指山市', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469002', '469002', '琼海市', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469005', '469005', '文昌市', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469006', '469006', '万宁市', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469007', '469007', '东方市', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469021', '469021', '定安县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469022', '469022', '屯昌县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469023', '469023', '澄迈县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469024', '469024', '临高县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469025', '469025', '白沙黎族自治县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469026', '469026', '昌江黎族自治县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469027', '469027', '乐东黎族自治县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469028', '469028', '陵水黎族自治县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469029', '469029', '保亭黎族苗族自治县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('469030', '469030', '琼中黎族苗族自治县', '4690', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('50', '50', '重庆市', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5001', '5001', '市辖区', '50', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500101', '500101', '万州区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500102', '500102', '涪陵区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500103', '500103', '渝中区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500104', '500104', '大渡口区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500105', '500105', '江北区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500106', '500106', '沙坪坝区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500107', '500107', '九龙坡区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500108', '500108', '南岸区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500109', '500109', '北碚区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500110', '500110', '綦江区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500111', '500111', '大足区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500112', '500112', '渝北区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500113', '500113', '巴南区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500114', '500114', '黔江区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500115', '500115', '长寿区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500116', '500116', '江津区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500117', '500117', '合川区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500118', '500118', '永川区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500119', '500119', '南川区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500120', '500120', '璧山区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500151', '500151', '铜梁区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500152', '500152', '潼南区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500153', '500153', '荣昌区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500154', '500154', '开州区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500155', '500155', '梁平区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500156', '500156', '武隆区', '5001', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5002', '5002', '县', '50', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500229', '500229', '城口县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500230', '500230', '丰都县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500231', '500231', '垫江县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500233', '500233', '忠县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500235', '500235', '云阳县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500236', '500236', '奉节县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500237', '500237', '巫山县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500238', '500238', '巫溪县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500240', '500240', '石柱土家族自治县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500241', '500241', '秀山土家族苗族自治县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500242', '500242', '酉阳土家族苗族自治县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('500243', '500243', '彭水苗族土家族自治县', '5002', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('51', '51', '四川省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5101', '5101', '成都市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510104', '510104', '锦江区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510105', '510105', '青羊区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510106', '510106', '金牛区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510107', '510107', '武侯区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510108', '510108', '成华区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510112', '510112', '龙泉驿区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510113', '510113', '青白江区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510114', '510114', '新都区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510115', '510115', '温江区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510116', '510116', '双流区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510117', '510117', '郫都区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510118', '510118', '新津区', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510121', '510121', '金堂县', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510129', '510129', '大邑县', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510131', '510131', '蒲江县', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510181', '510181', '都江堰市', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510182', '510182', '彭州市', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510183', '510183', '邛崃市', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510184', '510184', '崇州市', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510185', '510185', '简阳市', '5101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5103', '5103', '自贡市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510302', '510302', '自流井区', '5103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510303', '510303', '贡井区', '5103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510304', '510304', '大安区', '5103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510311', '510311', '沿滩区', '5103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510321', '510321', '荣县', '5103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510322', '510322', '富顺县', '5103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5104', '5104', '攀枝花市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510402', '510402', '东区', '5104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510403', '510403', '西区', '5104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510411', '510411', '仁和区', '5104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510421', '510421', '米易县', '5104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510422', '510422', '盐边县', '5104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5105', '5105', '泸州市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510502', '510502', '江阳区', '5105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510503', '510503', '纳溪区', '5105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510504', '510504', '龙马潭区', '5105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510521', '510521', '泸县', '5105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510522', '510522', '合江县', '5105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510524', '510524', '叙永县', '5105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510525', '510525', '古蔺县', '5105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5106', '5106', '德阳市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510603', '510603', '旌阳区', '5106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510604', '510604', '罗江区', '5106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510623', '510623', '中江县', '5106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510681', '510681', '广汉市', '5106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510682', '510682', '什邡市', '5106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510683', '510683', '绵竹市', '5106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5107', '5107', '绵阳市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510703', '510703', '涪城区', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510704', '510704', '游仙区', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510705', '510705', '安州区', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510722', '510722', '三台县', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510723', '510723', '盐亭县', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510725', '510725', '梓潼县', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510726', '510726', '北川羌族自治县', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510727', '510727', '平武县', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510781', '510781', '江油市', '5107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5108', '5108', '广元市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510802', '510802', '利州区', '5108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510811', '510811', '昭化区', '5108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510812', '510812', '朝天区', '5108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510821', '510821', '旺苍县', '5108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510822', '510822', '青川县', '5108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510823', '510823', '剑阁县', '5108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510824', '510824', '苍溪县', '5108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5109', '5109', '遂宁市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510903', '510903', '船山区', '5109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510904', '510904', '安居区', '5109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510921', '510921', '蓬溪县', '5109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510923', '510923', '大英县', '5109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('510981', '510981', '射洪市', '5109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5110', '5110', '内江市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511002', '511002', '市中区', '5110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511011', '511011', '东兴区', '5110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511024', '511024', '威远县', '5110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511025', '511025', '资中县', '5110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511071', '511071', '内江经济开发区', '5110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511083', '511083', '隆昌市', '5110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5111', '5111', '乐山市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511102', '511102', '市中区', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511111', '511111', '沙湾区', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511112', '511112', '五通桥区', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511113', '511113', '金口河区', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511123', '511123', '犍为县', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511124', '511124', '井研县', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511126', '511126', '夹江县', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511129', '511129', '沐川县', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511132', '511132', '峨边彝族自治县', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511133', '511133', '马边彝族自治县', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511181', '511181', '峨眉山市', '5111', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5113', '5113', '南充市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511302', '511302', '顺庆区', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511303', '511303', '高坪区', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511304', '511304', '嘉陵区', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511321', '511321', '南部县', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511322', '511322', '营山县', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511323', '511323', '蓬安县', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511324', '511324', '仪陇县', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511325', '511325', '西充县', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511381', '511381', '阆中市', '5113', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5114', '5114', '眉山市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511402', '511402', '东坡区', '5114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511403', '511403', '彭山区', '5114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511421', '511421', '仁寿县', '5114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511423', '511423', '洪雅县', '5114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511424', '511424', '丹棱县', '5114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511425', '511425', '青神县', '5114', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5115', '5115', '宜宾市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511502', '511502', '翠屏区', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511503', '511503', '南溪区', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511504', '511504', '叙州区', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511523', '511523', '江安县', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511524', '511524', '长宁县', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511525', '511525', '高县', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511526', '511526', '珙县', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511527', '511527', '筠连县', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511528', '511528', '兴文县', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511529', '511529', '屏山县', '5115', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5116', '5116', '广安市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511602', '511602', '广安区', '5116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511603', '511603', '前锋区', '5116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511621', '511621', '岳池县', '5116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511622', '511622', '武胜县', '5116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511623', '511623', '邻水县', '5116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511681', '511681', '华蓥市', '5116', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5117', '5117', '达州市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511702', '511702', '通川区', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511703', '511703', '达川区', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511722', '511722', '宣汉县', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511723', '511723', '开江县', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511724', '511724', '大竹县', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511725', '511725', '渠县', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511771', '511771', '达州经济开发区', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511781', '511781', '万源市', '5117', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5118', '5118', '雅安市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511802', '511802', '雨城区', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511803', '511803', '名山区', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511822', '511822', '荥经县', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511823', '511823', '汉源县', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511824', '511824', '石棉县', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511825', '511825', '天全县', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511826', '511826', '芦山县', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511827', '511827', '宝兴县', '5118', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5119', '5119', '巴中市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511902', '511902', '巴州区', '5119', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511903', '511903', '恩阳区', '5119', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511921', '511921', '通江县', '5119', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511922', '511922', '南江县', '5119', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511923', '511923', '平昌县', '5119', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('511971', '511971', '巴中经济开发区', '5119', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5120', '5120', '资阳市', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('512002', '512002', '雁江区', '5120', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('512021', '512021', '安岳县', '5120', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('512022', '512022', '乐至县', '5120', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5132', '5132', '阿坝藏族羌族自治州', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513201', '513201', '马尔康市', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513221', '513221', '汶川县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513222', '513222', '理县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513223', '513223', '茂县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513224', '513224', '松潘县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513225', '513225', '九寨沟县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513226', '513226', '金川县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513227', '513227', '小金县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513228', '513228', '黑水县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513230', '513230', '壤塘县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513231', '513231', '阿坝县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513232', '513232', '若尔盖县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513233', '513233', '红原县', '5132', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5133', '5133', '甘孜藏族自治州', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513301', '513301', '康定市', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513322', '513322', '泸定县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513323', '513323', '丹巴县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513324', '513324', '九龙县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513325', '513325', '雅江县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513326', '513326', '道孚县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513327', '513327', '炉霍县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513328', '513328', '甘孜县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513329', '513329', '新龙县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513330', '513330', '德格县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513331', '513331', '白玉县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513332', '513332', '石渠县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513333', '513333', '色达县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513334', '513334', '理塘县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513335', '513335', '巴塘县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513336', '513336', '乡城县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513337', '513337', '稻城县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513338', '513338', '得荣县', '5133', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5134', '5134', '凉山彝族自治州', '51', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513401', '513401', '西昌市', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513422', '513422', '木里藏族自治县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513423', '513423', '盐源县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513424', '513424', '德昌县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513425', '513425', '会理县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513426', '513426', '会东县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513427', '513427', '宁南县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513428', '513428', '普格县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513429', '513429', '布拖县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513430', '513430', '金阳县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513431', '513431', '昭觉县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513432', '513432', '喜德县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513433', '513433', '冕宁县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513434', '513434', '越西县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513435', '513435', '甘洛县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513436', '513436', '美姑县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('513437', '513437', '雷波县', '5134', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('52', '52', '贵州省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5201', '5201', '贵阳市', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520102', '520102', '南明区', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520103', '520103', '云岩区', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520111', '520111', '花溪区', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520112', '520112', '乌当区', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520113', '520113', '白云区', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520115', '520115', '观山湖区', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520121', '520121', '开阳县', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520122', '520122', '息烽县', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520123', '520123', '修文县', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520181', '520181', '清镇市', '5201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5202', '5202', '六盘水市', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520201', '520201', '钟山区', '5202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520203', '520203', '六枝特区', '5202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520221', '520221', '水城县', '5202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520281', '520281', '盘州市', '5202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5203', '5203', '遵义市', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520302', '520302', '红花岗区', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520303', '520303', '汇川区', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520304', '520304', '播州区', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520322', '520322', '桐梓县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520323', '520323', '绥阳县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520324', '520324', '正安县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520325', '520325', '道真仡佬族苗族自治县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520326', '520326', '务川仡佬族苗族自治县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520327', '520327', '凤冈县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520328', '520328', '湄潭县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520329', '520329', '余庆县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520330', '520330', '习水县', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520381', '520381', '赤水市', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520382', '520382', '仁怀市', '5203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5204', '5204', '安顺市', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520402', '520402', '西秀区', '5204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520403', '520403', '平坝区', '5204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520422', '520422', '普定县', '5204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520423', '520423', '镇宁布依族苗族自治县', '5204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520424', '520424', '关岭布依族苗族自治县', '5204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520425', '520425', '紫云苗族布依族自治县', '5204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5205', '5205', '毕节市', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520502', '520502', '七星关区', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520521', '520521', '大方县', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520522', '520522', '黔西县', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520523', '520523', '金沙县', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520524', '520524', '织金县', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520525', '520525', '纳雍县', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520526', '520526', '威宁彝族回族苗族自治县', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520527', '520527', '赫章县', '5205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5206', '5206', '铜仁市', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520602', '520602', '碧江区', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520603', '520603', '万山区', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520621', '520621', '江口县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520622', '520622', '玉屏侗族自治县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520623', '520623', '石阡县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520624', '520624', '思南县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520625', '520625', '印江土家族苗族自治县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520626', '520626', '德江县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520627', '520627', '沿河土家族自治县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('520628', '520628', '松桃苗族自治县', '5206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5223', '5223', '黔西南布依族苗族自治州', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522301', '522301', '兴义市', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522302', '522302', '兴仁市', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522323', '522323', '普安县', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522324', '522324', '晴隆县', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522325', '522325', '贞丰县', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522326', '522326', '望谟县', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522327', '522327', '册亨县', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522328', '522328', '安龙县', '5223', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5226', '5226', '黔东南苗族侗族自治州', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522601', '522601', '凯里市', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522622', '522622', '黄平县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522623', '522623', '施秉县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522624', '522624', '三穗县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522625', '522625', '镇远县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522626', '522626', '岑巩县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522627', '522627', '天柱县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522628', '522628', '锦屏县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522629', '522629', '剑河县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522630', '522630', '台江县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522631', '522631', '黎平县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522632', '522632', '榕江县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522633', '522633', '从江县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522634', '522634', '雷山县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522635', '522635', '麻江县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522636', '522636', '丹寨县', '5226', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5227', '5227', '黔南布依族苗族自治州', '52', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522701', '522701', '都匀市', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522702', '522702', '福泉市', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522722', '522722', '荔波县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522723', '522723', '贵定县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522725', '522725', '瓮安县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522726', '522726', '独山县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522727', '522727', '平塘县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522728', '522728', '罗甸县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522729', '522729', '长顺县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522730', '522730', '龙里县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522731', '522731', '惠水县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('522732', '522732', '三都水族自治县', '5227', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('53', '53', '云南省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5301', '5301', '昆明市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530102', '530102', '五华区', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530103', '530103', '盘龙区', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530111', '530111', '官渡区', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530112', '530112', '西山区', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530113', '530113', '东川区', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530114', '530114', '呈贡区', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530115', '530115', '晋宁区', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530124', '530124', '富民县', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530125', '530125', '宜良县', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530126', '530126', '石林彝族自治县', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530127', '530127', '嵩明县', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530128', '530128', '禄劝彝族苗族自治县', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530129', '530129', '寻甸回族彝族自治县', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530181', '530181', '安宁市', '5301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5303', '5303', '曲靖市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530302', '530302', '麒麟区', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530303', '530303', '沾益区', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530304', '530304', '马龙区', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530322', '530322', '陆良县', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530323', '530323', '师宗县', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530324', '530324', '罗平县', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530325', '530325', '富源县', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530326', '530326', '会泽县', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530381', '530381', '宣威市', '5303', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5304', '5304', '玉溪市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530402', '530402', '红塔区', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530403', '530403', '江川区', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530423', '530423', '通海县', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530424', '530424', '华宁县', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530425', '530425', '易门县', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530426', '530426', '峨山彝族自治县', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530427', '530427', '新平彝族傣族自治县', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530428', '530428', '元江哈尼族彝族傣族自治县', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530481', '530481', '澄江市', '5304', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5305', '5305', '保山市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530502', '530502', '隆阳区', '5305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530521', '530521', '施甸县', '5305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530523', '530523', '龙陵县', '5305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530524', '530524', '昌宁县', '5305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530581', '530581', '腾冲市', '5305', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5306', '5306', '昭通市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530602', '530602', '昭阳区', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530621', '530621', '鲁甸县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530622', '530622', '巧家县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530623', '530623', '盐津县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530624', '530624', '大关县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530625', '530625', '永善县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530626', '530626', '绥江县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530627', '530627', '镇雄县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530628', '530628', '彝良县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530629', '530629', '威信县', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530681', '530681', '水富市', '5306', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5307', '5307', '丽江市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530702', '530702', '古城区', '5307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530721', '530721', '玉龙纳西族自治县', '5307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530722', '530722', '永胜县', '5307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530723', '530723', '华坪县', '5307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530724', '530724', '宁蒗彝族自治县', '5307', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5308', '5308', '普洱市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530802', '530802', '思茅区', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530821', '530821', '宁洱哈尼族彝族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530822', '530822', '墨江哈尼族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530823', '530823', '景东彝族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530824', '530824', '景谷傣族彝族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530825', '530825', '镇沅彝族哈尼族拉祜族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530826', '530826', '江城哈尼族彝族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530827', '530827', '孟连傣族拉祜族佤族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530828', '530828', '澜沧拉祜族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530829', '530829', '西盟佤族自治县', '5308', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5309', '5309', '临沧市', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530902', '530902', '临翔区', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530921', '530921', '凤庆县', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530922', '530922', '云县', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530923', '530923', '永德县', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530924', '530924', '镇康县', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530925', '530925', '双江拉祜族佤族布朗族傣族自治县', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530926', '530926', '耿马傣族佤族自治县', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('530927', '530927', '沧源佤族自治县', '5309', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5323', '5323', '楚雄彝族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532301', '532301', '楚雄市', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532322', '532322', '双柏县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532323', '532323', '牟定县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532324', '532324', '南华县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532325', '532325', '姚安县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532326', '532326', '大姚县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532327', '532327', '永仁县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532328', '532328', '元谋县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532329', '532329', '武定县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532331', '532331', '禄丰县', '5323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5325', '5325', '红河哈尼族彝族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532501', '532501', '个旧市', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532502', '532502', '开远市', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532503', '532503', '蒙自市', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532504', '532504', '弥勒市', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532523', '532523', '屏边苗族自治县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532524', '532524', '建水县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532525', '532525', '石屏县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532527', '532527', '泸西县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532528', '532528', '元阳县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532529', '532529', '红河县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532530', '532530', '金平苗族瑶族傣族自治县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532531', '532531', '绿春县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532532', '532532', '河口瑶族自治县', '5325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5326', '5326', '文山壮族苗族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532601', '532601', '文山市', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532622', '532622', '砚山县', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532623', '532623', '西畴县', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532624', '532624', '麻栗坡县', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532625', '532625', '马关县', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532626', '532626', '丘北县', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532627', '532627', '广南县', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532628', '532628', '富宁县', '5326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5328', '5328', '西双版纳傣族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532801', '532801', '景洪市', '5328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532822', '532822', '勐海县', '5328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532823', '532823', '勐腊县', '5328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5329', '5329', '大理白族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532901', '532901', '大理市', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532922', '532922', '漾濞彝族自治县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532923', '532923', '祥云县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532924', '532924', '宾川县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532925', '532925', '弥渡县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532926', '532926', '南涧彝族自治县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532927', '532927', '巍山彝族回族自治县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532928', '532928', '永平县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532929', '532929', '云龙县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532930', '532930', '洱源县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532931', '532931', '剑川县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('532932', '532932', '鹤庆县', '5329', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5331', '5331', '德宏傣族景颇族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533102', '533102', '瑞丽市', '5331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533103', '533103', '芒市', '5331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533122', '533122', '梁河县', '5331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533123', '533123', '盈江县', '5331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533124', '533124', '陇川县', '5331', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5333', '5333', '怒江傈僳族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533301', '533301', '泸水市', '5333', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533323', '533323', '福贡县', '5333', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533324', '533324', '贡山独龙族怒族自治县', '5333', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533325', '533325', '兰坪白族普米族自治县', '5333', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5334', '5334', '迪庆藏族自治州', '53', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533401', '533401', '香格里拉市', '5334', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533422', '533422', '德钦县', '5334', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('533423', '533423', '维西傈僳族自治县', '5334', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('54', '54', '西藏自治区', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5401', '5401', '拉萨市', '54', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540102', '540102', '城关区', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540103', '540103', '堆龙德庆区', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540104', '540104', '达孜区', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540121', '540121', '林周县', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540122', '540122', '当雄县', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540123', '540123', '尼木县', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540124', '540124', '曲水县', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540127', '540127', '墨竹工卡县', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540171', '540171', '格尔木藏青工业园区', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540172', '540172', '拉萨经济技术开发区', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540173', '540173', '西藏文化旅游创意园区', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540174', '540174', '达孜工业园区', '5401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5402', '5402', '日喀则市', '54', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540202', '540202', '桑珠孜区', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540221', '540221', '南木林县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540222', '540222', '江孜县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540223', '540223', '定日县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540224', '540224', '萨迦县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540225', '540225', '拉孜县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540226', '540226', '昂仁县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540227', '540227', '谢通门县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540228', '540228', '白朗县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540229', '540229', '仁布县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540230', '540230', '康马县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540231', '540231', '定结县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540232', '540232', '仲巴县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540233', '540233', '亚东县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540234', '540234', '吉隆县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540235', '540235', '聂拉木县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540236', '540236', '萨嘎县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540237', '540237', '岗巴县', '5402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5403', '5403', '昌都市', '54', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540302', '540302', '卡若区', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540321', '540321', '江达县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540322', '540322', '贡觉县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540323', '540323', '类乌齐县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540324', '540324', '丁青县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540325', '540325', '察雅县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540326', '540326', '八宿县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540327', '540327', '左贡县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540328', '540328', '芒康县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540329', '540329', '洛隆县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540330', '540330', '边坝县', '5403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5404', '5404', '林芝市', '54', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540402', '540402', '巴宜区', '5404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540421', '540421', '工布江达县', '5404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540422', '540422', '米林县', '5404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540423', '540423', '墨脱县', '5404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540424', '540424', '波密县', '5404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540425', '540425', '察隅县', '5404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540426', '540426', '朗县', '5404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5405', '5405', '山南市', '54', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540502', '540502', '乃东区', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540521', '540521', '扎囊县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540522', '540522', '贡嘎县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540523', '540523', '桑日县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540524', '540524', '琼结县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540525', '540525', '曲松县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540526', '540526', '措美县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540527', '540527', '洛扎县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540528', '540528', '加查县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540529', '540529', '隆子县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540530', '540530', '错那县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540531', '540531', '浪卡子县', '5405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5406', '5406', '那曲市', '54', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540602', '540602', '色尼区', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540621', '540621', '嘉黎县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540622', '540622', '比如县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540623', '540623', '聂荣县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540624', '540624', '安多县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540625', '540625', '申扎县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540626', '540626', '索县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540627', '540627', '班戈县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540628', '540628', '巴青县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540629', '540629', '尼玛县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('540630', '540630', '双湖县', '5406', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('5425', '5425', '阿里地区', '54', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('542521', '542521', '普兰县', '5425', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('542522', '542522', '札达县', '5425', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('542523', '542523', '噶尔县', '5425', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('542524', '542524', '日土县', '5425', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('542525', '542525', '革吉县', '5425', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('542526', '542526', '改则县', '5425', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('542527', '542527', '措勤县', '5425', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('61', '61', '陕西省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6101', '6101', '西安市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610102', '610102', '新城区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610103', '610103', '碑林区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610104', '610104', '莲湖区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610111', '610111', '灞桥区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610112', '610112', '未央区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610113', '610113', '雁塔区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610114', '610114', '阎良区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610115', '610115', '临潼区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610116', '610116', '长安区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610117', '610117', '高陵区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610118', '610118', '鄠邑区', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610122', '610122', '蓝田县', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610124', '610124', '周至县', '6101', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6102', '6102', '铜川市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610202', '610202', '王益区', '6102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610203', '610203', '印台区', '6102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610204', '610204', '耀州区', '6102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610222', '610222', '宜君县', '6102', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6103', '6103', '宝鸡市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610302', '610302', '渭滨区', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610303', '610303', '金台区', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610304', '610304', '陈仓区', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610322', '610322', '凤翔县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610323', '610323', '岐山县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610324', '610324', '扶风县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610326', '610326', '眉县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610327', '610327', '陇县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610328', '610328', '千阳县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610329', '610329', '麟游县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610330', '610330', '凤县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610331', '610331', '太白县', '6103', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6104', '6104', '咸阳市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610402', '610402', '秦都区', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610403', '610403', '杨陵区', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610404', '610404', '渭城区', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610422', '610422', '三原县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610423', '610423', '泾阳县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610424', '610424', '乾县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610425', '610425', '礼泉县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610426', '610426', '永寿县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610428', '610428', '长武县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610429', '610429', '旬邑县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610430', '610430', '淳化县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610431', '610431', '武功县', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610481', '610481', '兴平市', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610482', '610482', '彬州市', '6104', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6105', '6105', '渭南市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610502', '610502', '临渭区', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610503', '610503', '华州区', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610522', '610522', '潼关县', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610523', '610523', '大荔县', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610524', '610524', '合阳县', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610525', '610525', '澄城县', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610526', '610526', '蒲城县', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610527', '610527', '白水县', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610528', '610528', '富平县', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610581', '610581', '韩城市', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610582', '610582', '华阴市', '6105', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6106', '6106', '延安市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610602', '610602', '宝塔区', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610603', '610603', '安塞区', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610621', '610621', '延长县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610622', '610622', '延川县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610625', '610625', '志丹县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610626', '610626', '吴起县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610627', '610627', '甘泉县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610628', '610628', '富县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610629', '610629', '洛川县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610630', '610630', '宜川县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610631', '610631', '黄龙县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610632', '610632', '黄陵县', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610681', '610681', '子长市', '6106', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6107', '6107', '汉中市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610702', '610702', '汉台区', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610703', '610703', '南郑区', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610722', '610722', '城固县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610723', '610723', '洋县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610724', '610724', '西乡县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610725', '610725', '勉县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610726', '610726', '宁强县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610727', '610727', '略阳县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610728', '610728', '镇巴县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610729', '610729', '留坝县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610730', '610730', '佛坪县', '6107', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6108', '6108', '榆林市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610802', '610802', '榆阳区', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610803', '610803', '横山区', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610822', '610822', '府谷县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610824', '610824', '靖边县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610825', '610825', '定边县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610826', '610826', '绥德县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610827', '610827', '米脂县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610828', '610828', '佳县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610829', '610829', '吴堡县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610830', '610830', '清涧县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610831', '610831', '子洲县', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610881', '610881', '神木市', '6108', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6109', '6109', '安康市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610902', '610902', '汉滨区', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610921', '610921', '汉阴县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610922', '610922', '石泉县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610923', '610923', '宁陕县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610924', '610924', '紫阳县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610925', '610925', '岚皋县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610926', '610926', '平利县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610927', '610927', '镇坪县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610928', '610928', '旬阳县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('610929', '610929', '白河县', '6109', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6110', '6110', '商洛市', '61', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('611002', '611002', '商州区', '6110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('611021', '611021', '洛南县', '6110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('611022', '611022', '丹凤县', '6110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('611023', '611023', '商南县', '6110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('611024', '611024', '山阳县', '6110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('611025', '611025', '镇安县', '6110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('611026', '611026', '柞水县', '6110', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('62', '62', '甘肃省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6201', '6201', '兰州市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620102', '620102', '城关区', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620103', '620103', '七里河区', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620104', '620104', '西固区', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620105', '620105', '安宁区', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620111', '620111', '红古区', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620121', '620121', '永登县', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620122', '620122', '皋兰县', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620123', '620123', '榆中县', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620171', '620171', '兰州新区', '6201', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6202', '6202', '嘉峪关市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620201001', '620201001', '雄关街道', '6202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620201002', '620201002', '钢城街道', '6202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620201100', '620201100', '新城镇', '6202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620201101', '620201101', '峪泉镇', '6202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620201102', '620201102', '文殊镇', '6202', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6203', '6203', '金昌市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620302', '620302', '金川区', '6203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620321', '620321', '永昌县', '6203', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6204', '6204', '白银市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620402', '620402', '白银区', '6204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620403', '620403', '平川区', '6204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620421', '620421', '靖远县', '6204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620422', '620422', '会宁县', '6204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620423', '620423', '景泰县', '6204', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6205', '6205', '天水市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620502', '620502', '秦州区', '6205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620503', '620503', '麦积区', '6205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620521', '620521', '清水县', '6205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620522', '620522', '秦安县', '6205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620523', '620523', '甘谷县', '6205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620524', '620524', '武山县', '6205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620525', '620525', '张家川回族自治县', '6205', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6206', '6206', '武威市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620602', '620602', '凉州区', '6206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620621', '620621', '民勤县', '6206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620622', '620622', '古浪县', '6206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620623', '620623', '天祝藏族自治县', '6206', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6207', '6207', '张掖市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620702', '620702', '甘州区', '6207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620721', '620721', '肃南裕固族自治县', '6207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620722', '620722', '民乐县', '6207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620723', '620723', '临泽县', '6207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620724', '620724', '高台县', '6207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620725', '620725', '山丹县', '6207', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6208', '6208', '平凉市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620802', '620802', '崆峒区', '6208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620821', '620821', '泾川县', '6208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620822', '620822', '灵台县', '6208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620823', '620823', '崇信县', '6208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620825', '620825', '庄浪县', '6208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620826', '620826', '静宁县', '6208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620881', '620881', '华亭市', '6208', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6209', '6209', '酒泉市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620902', '620902', '肃州区', '6209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620921', '620921', '金塔县', '6209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620922', '620922', '瓜州县', '6209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620923', '620923', '肃北蒙古族自治县', '6209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620924', '620924', '阿克塞哈萨克族自治县', '6209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620981', '620981', '玉门市', '6209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('620982', '620982', '敦煌市', '6209', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6210', '6210', '庆阳市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621002', '621002', '西峰区', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621021', '621021', '庆城县', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621022', '621022', '环县', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621023', '621023', '华池县', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621024', '621024', '合水县', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621025', '621025', '正宁县', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621026', '621026', '宁县', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621027', '621027', '镇原县', '6210', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6211', '6211', '定西市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621102', '621102', '安定区', '6211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621121', '621121', '通渭县', '6211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621122', '621122', '陇西县', '6211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621123', '621123', '渭源县', '6211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621124', '621124', '临洮县', '6211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621125', '621125', '漳县', '6211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621126', '621126', '岷县', '6211', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6212', '6212', '陇南市', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621202', '621202', '武都区', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621221', '621221', '成县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621222', '621222', '文县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621223', '621223', '宕昌县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621224', '621224', '康县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621225', '621225', '西和县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621226', '621226', '礼县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621227', '621227', '徽县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('621228', '621228', '两当县', '6212', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6229', '6229', '临夏回族自治州', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622901', '622901', '临夏市', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622921', '622921', '临夏县', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622922', '622922', '康乐县', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622923', '622923', '永靖县', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622924', '622924', '广河县', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622925', '622925', '和政县', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622926', '622926', '东乡族自治县', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('622927', '622927', '积石山保安族东乡族撒拉族自治县', '6229', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6230', '6230', '甘南藏族自治州', '62', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623001', '623001', '合作市', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623021', '623021', '临潭县', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623022', '623022', '卓尼县', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623023', '623023', '舟曲县', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623024', '623024', '迭部县', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623025', '623025', '玛曲县', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623026', '623026', '碌曲县', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('623027', '623027', '夏河县', '6230', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('63', '63', '青海省', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6301', '6301', '西宁市', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630102', '630102', '城东区', '6301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630103', '630103', '城中区', '6301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630104', '630104', '城西区', '6301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630105', '630105', '城北区', '6301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630106', '630106', '湟中区', '6301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630121', '630121', '大通回族土族自治县', '6301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630123', '630123', '湟源县', '6301', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6302', '6302', '海东市', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630202', '630202', '乐都区', '6302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630203', '630203', '平安区', '6302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630222', '630222', '民和回族土族自治县', '6302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630223', '630223', '互助土族自治县', '6302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630224', '630224', '化隆回族自治县', '6302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('630225', '630225', '循化撒拉族自治县', '6302', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6322', '6322', '海北藏族自治州', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632221', '632221', '门源回族自治县', '6322', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632222', '632222', '祁连县', '6322', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632223', '632223', '海晏县', '6322', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632224', '632224', '刚察县', '6322', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6323', '6323', '黄南藏族自治州', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632321', '632321', '同仁县', '6323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632322', '632322', '尖扎县', '6323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632323', '632323', '泽库县', '6323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632324', '632324', '河南蒙古族自治县', '6323', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6325', '6325', '海南藏族自治州', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632521', '632521', '共和县', '6325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632522', '632522', '同德县', '6325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632523', '632523', '贵德县', '6325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632524', '632524', '兴海县', '6325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632525', '632525', '贵南县', '6325', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6326', '6326', '果洛藏族自治州', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632621', '632621', '玛沁县', '6326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632622', '632622', '班玛县', '6326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632623', '632623', '甘德县', '6326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632624', '632624', '达日县', '6326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632625', '632625', '久治县', '6326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632626', '632626', '玛多县', '6326', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6327', '6327', '玉树藏族自治州', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632701', '632701', '玉树市', '6327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632722', '632722', '杂多县', '6327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632723', '632723', '称多县', '6327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632724', '632724', '治多县', '6327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632725', '632725', '囊谦县', '6327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632726', '632726', '曲麻莱县', '6327', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6328', '6328', '海西蒙古族藏族自治州', '63', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632801', '632801', '格尔木市', '6328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632802', '632802', '德令哈市', '6328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632803', '632803', '茫崖市', '6328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632821', '632821', '乌兰县', '6328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632822', '632822', '都兰县', '6328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632823', '632823', '天峻县', '6328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('632857', '632857', '大柴旦行政委员会', '6328', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('64', '64', '宁夏回族自治区', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6401', '6401', '银川市', '64', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640104', '640104', '兴庆区', '6401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640105', '640105', '西夏区', '6401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640106', '640106', '金凤区', '6401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640121', '640121', '永宁县', '6401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640122', '640122', '贺兰县', '6401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640181', '640181', '灵武市', '6401', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6402', '6402', '石嘴山市', '64', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640202', '640202', '大武口区', '6402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640205', '640205', '惠农区', '6402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640221', '640221', '平罗县', '6402', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6403', '6403', '吴忠市', '64', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640302', '640302', '利通区', '6403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640303', '640303', '红寺堡区', '6403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640323', '640323', '盐池县', '6403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640324', '640324', '同心县', '6403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640381', '640381', '青铜峡市', '6403', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6404', '6404', '固原市', '64', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640402', '640402', '原州区', '6404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640422', '640422', '西吉县', '6404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640423', '640423', '隆德县', '6404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640424', '640424', '泾源县', '6404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640425', '640425', '彭阳县', '6404', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6405', '6405', '中卫市', '64', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640502', '640502', '沙坡头区', '6405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640521', '640521', '中宁县', '6405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('640522', '640522', '海原县', '6405', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('65', '65', '新疆维吾尔自治区', NULL, 1);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6501', '6501', '乌鲁木齐市', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650102', '650102', '天山区', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650103', '650103', '沙依巴克区', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650104', '650104', '新市区', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650105', '650105', '水磨沟区', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650106', '650106', '头屯河区', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650107', '650107', '达坂城区', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650109', '650109', '米东区', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650121', '650121', '乌鲁木齐县', '6501', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6502', '6502', '克拉玛依市', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650202', '650202', '独山子区', '6502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650203', '650203', '克拉玛依区', '6502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650204', '650204', '白碱滩区', '6502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650205', '650205', '乌尔禾区', '6502', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6504', '6504', '吐鲁番市', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650402', '650402', '高昌区', '6504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650421', '650421', '鄯善县', '6504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650422', '650422', '托克逊县', '6504', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6505', '6505', '哈密市', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650502', '650502', '伊州区', '6505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650521', '650521', '巴里坤哈萨克自治县', '6505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('650522', '650522', '伊吾县', '6505', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6523', '6523', '昌吉回族自治州', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652301', '652301', '昌吉市', '6523', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652302', '652302', '阜康市', '6523', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652323', '652323', '呼图壁县', '6523', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652324', '652324', '玛纳斯县', '6523', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652325', '652325', '奇台县', '6523', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652327', '652327', '吉木萨尔县', '6523', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652328', '652328', '木垒哈萨克自治县', '6523', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6527', '6527', '博尔塔拉蒙古自治州', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652701', '652701', '博乐市', '6527', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652702', '652702', '阿拉山口市', '6527', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652722', '652722', '精河县', '6527', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652723', '652723', '温泉县', '6527', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6528', '6528', '巴音郭楞蒙古自治州', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652801', '652801', '库尔勒市', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652822', '652822', '轮台县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652823', '652823', '尉犁县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652824', '652824', '若羌县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652825', '652825', '且末县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652826', '652826', '焉耆回族自治县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652827', '652827', '和静县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652828', '652828', '和硕县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652829', '652829', '博湖县', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652871', '652871', '库尔勒经济技术开发区', '6528', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6529', '6529', '阿克苏地区', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652901', '652901', '阿克苏市', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652902', '652902', '库车市', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652922', '652922', '温宿县', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652924', '652924', '沙雅县', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652925', '652925', '新和县', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652926', '652926', '拜城县', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652927', '652927', '乌什县', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652928', '652928', '阿瓦提县', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('652929', '652929', '柯坪县', '6529', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6530', '6530', '克孜勒苏柯尔克孜自治州', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653001', '653001', '阿图什市', '6530', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653022', '653022', '阿克陶县', '6530', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653023', '653023', '阿合奇县', '6530', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653024', '653024', '乌恰县', '6530', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6531', '6531', '喀什地区', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653101', '653101', '喀什市', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653121', '653121', '疏附县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653122', '653122', '疏勒县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653123', '653123', '英吉沙县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653124', '653124', '泽普县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653125', '653125', '莎车县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653126', '653126', '叶城县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653127', '653127', '麦盖提县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653128', '653128', '岳普湖县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653129', '653129', '伽师县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653130', '653130', '巴楚县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653131', '653131', '塔什库尔干塔吉克自治县', '6531', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6532', '6532', '和田地区', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653201', '653201', '和田市', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653221', '653221', '和田县', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653222', '653222', '墨玉县', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653223', '653223', '皮山县', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653224', '653224', '洛浦县', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653225', '653225', '策勒县', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653226', '653226', '于田县', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('653227', '653227', '民丰县', '6532', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6540', '6540', '伊犁哈萨克自治州', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654002', '654002', '伊宁市', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654003', '654003', '奎屯市', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654004', '654004', '霍尔果斯市', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654021', '654021', '伊宁县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654022', '654022', '察布查尔锡伯自治县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654023', '654023', '霍城县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654024', '654024', '巩留县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654025', '654025', '新源县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654026', '654026', '昭苏县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654027', '654027', '特克斯县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654028', '654028', '尼勒克县', '6540', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6542', '6542', '塔城地区', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654201', '654201', '塔城市', '6542', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654202', '654202', '乌苏市', '6542', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654221', '654221', '额敏县', '6542', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654223', '654223', '沙湾县', '6542', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654224', '654224', '托里县', '6542', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654225', '654225', '裕民县', '6542', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654226', '654226', '和布克赛尔蒙古自治县', '6542', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6543', '6543', '阿勒泰地区', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654301', '654301', '阿勒泰市', '6543', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654321', '654321', '布尔津县', '6543', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654322', '654322', '富蕴县', '6543', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654323', '654323', '福海县', '6543', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654324', '654324', '哈巴河县', '6543', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654325', '654325', '青河县', '6543', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('654326', '654326', '吉木乃县', '6543', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('6590', '6590', '自治区直辖县级行政区划', '65', 2);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659001', '659001', '石河子市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659002', '659002', '阿拉尔市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659003', '659003', '图木舒克市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659004', '659004', '五家渠市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659005', '659005', '北屯市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659006', '659006', '铁门关市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659007', '659007', '双河市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659008', '659008', '可克达拉市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659009', '659009', '昆玉市', '6590', 3);
INSERT INTO `dic_city` (`id`, `code`, `name`, `parent_id`, `level`) VALUES ('659010', '659010', '胡杨河市', '6590', 3);
COMMIT;

-- ----------------------------
-- Table structure for gen_create_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_create_column_config`;
CREATE TABLE `gen_create_column_config` (
  `id` varchar(32) NOT NULL,
  `required` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否必填',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='新增功能代码生成配置';

-- ----------------------------
-- Records of gen_create_column_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_form
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_form`;
CREATE TABLE `gen_custom_form` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `is_dialog` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否对话框表单',
  `dialog_tittle` varchar(100) DEFAULT NULL COMMENT '对话框标题',
  `dialog_width` varchar(100) DEFAULT '1' COMMENT '对话框宽度',
  `form_config` longtext NOT NULL COMMENT '表单配置',
  `prefix_submit` longtext COMMENT '前置提交脚本',
  `suffix_submit` longtext COMMENT '后置提交脚本',
  `require_query` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否需要查询',
  `query_bean` longtext COMMENT '查询数据Bean名称',
  `handle_bean` longtext COMMENT '操作数据Bean名称',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义对话框';

-- ----------------------------
-- Records of gen_custom_form
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_form_category
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_form_category`;
CREATE TABLE `gen_custom_form_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义列表分类';

-- ----------------------------
-- Records of gen_custom_form_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_list
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_list`;
CREATE TABLE `gen_custom_list` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `data_obj_id` varchar(32) NOT NULL DEFAULT '1' COMMENT '数据对象ID',
  `list_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '列表类型',
  `label_width` int(11) NOT NULL COMMENT '表单Label宽度',
  `has_page` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否分页',
  `tree_data` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否树形列表',
  `id_column` varchar(32) DEFAULT NULL COMMENT 'ID字段',
  `id_column_rela_id` varchar(32) DEFAULT NULL COMMENT 'ID字段关联ID',
  `tree_pid_column` varchar(32) DEFAULT NULL COMMENT '父级ID字段',
  `tree_pid_column_rela_id` varchar(32) DEFAULT NULL COMMENT '父级ID字段关联ID',
  `tree_node_column` varchar(32) DEFAULT NULL COMMENT '树形节点字段',
  `tree_node_column_rela_id` varchar(32) DEFAULT NULL COMMENT '树形节点字段关联ID',
  `tree_children_key` varchar(100) DEFAULT NULL COMMENT '子节点Key值',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `query_prefix_sql` longtext COMMENT '查询前置SQL',
  `query_suffix_sql` longtext COMMENT '查询后置SQL',
  `suffix_sql` longtext COMMENT '后置SQL',
  `allow_export` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许导出',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义列表';

-- ----------------------------
-- Records of gen_custom_list
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_list_category
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_list_category`;
CREATE TABLE `gen_custom_list_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义列表分类';

-- ----------------------------
-- Records of gen_custom_list_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_list_detail
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_list_detail`;
CREATE TABLE `gen_custom_list_detail` (
  `id` varchar(32) NOT NULL,
  `custom_list_id` varchar(32) NOT NULL COMMENT '自定义列表ID',
  `rela_id` varchar(32) DEFAULT NULL COMMENT '关联ID',
  `data_entity_id` varchar(32) DEFAULT NULL COMMENT '数据实体ID',
  `width_type` tinyint(3) NOT NULL COMMENT '宽度类型',
  `width` int(11) NOT NULL COMMENT '宽度',
  `sortable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否页面排序',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `type` tinyint(3) NOT NULL COMMENT '类型',
  `formatter` longtext COMMENT '格式化脚本',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `custom_list_id` (`custom_list_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义列表明细';

-- ----------------------------
-- Records of gen_custom_list_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_list_handle_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_list_handle_column`;
CREATE TABLE `gen_custom_list_handle_column` (
  `id` varchar(32) NOT NULL,
  `custom_list_id` varchar(32) NOT NULL COMMENT '自定义列表ID',
  `name` varchar(32) NOT NULL COMMENT '显示名称',
  `view_type` varchar(20) NOT NULL COMMENT '显示类型',
  `btn_type` tinyint(3) NOT NULL COMMENT '按钮类型',
  `btn_config` longtext COMMENT '按钮配置',
  `request_param` longtext COMMENT '请求参数',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `width` int(11) NOT NULL COMMENT '宽度',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义列表工具栏';

-- ----------------------------
-- Records of gen_custom_list_handle_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_list_query_params
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_list_query_params`;
CREATE TABLE `gen_custom_list_query_params` (
  `id` varchar(32) NOT NULL,
  `custom_list_id` varchar(32) NOT NULL COMMENT '自定义列表ID',
  `rela_id` varchar(32) NOT NULL COMMENT '关联ID',
  `data_entity_id` varchar(32) NOT NULL COMMENT '数据实体ID',
  `front_show` tinyint(1) NOT NULL DEFAULT '1' COMMENT '前端显示',
  `query_type` tinyint(3) NOT NULL COMMENT '查询类型',
  `form_width` int(11) NOT NULL DEFAULT '6' COMMENT '表单宽度',
  `default_value` longtext COMMENT '默认值',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `type` tinyint(3) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `custom_list_id` (`custom_list_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义列表查询参数';

-- ----------------------------
-- Records of gen_custom_list_query_params
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_list_toolbar
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_list_toolbar`;
CREATE TABLE `gen_custom_list_toolbar` (
  `id` varchar(32) NOT NULL,
  `custom_list_id` varchar(32) NOT NULL COMMENT '自定义列表ID',
  `name` varchar(32) NOT NULL COMMENT '显示名称',
  `view_type` varchar(20) NOT NULL COMMENT '显示类型',
  `btn_type` tinyint(3) NOT NULL COMMENT '按钮类型',
  `btn_config` longtext COMMENT '按钮配置',
  `request_param` longtext COMMENT '请求参数',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义列表工具栏';

-- ----------------------------
-- Records of gen_custom_list_toolbar
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_page
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_page`;
CREATE TABLE `gen_custom_page` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `page_code` longtext NOT NULL COMMENT '页面代码',
  `script_code` longtext COMMENT '脚本代码',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='自定义页面';

-- ----------------------------
-- Records of gen_custom_page
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_page_category
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_page_category`;
CREATE TABLE `gen_custom_page_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自定义页面分类';

-- ----------------------------
-- Records of gen_custom_page_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_selector
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_selector`;
CREATE TABLE `gen_custom_selector` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `custom_list_id` varchar(32) NOT NULL DEFAULT '1' COMMENT '数据对象ID',
  `dialog_tittle` varchar(100) DEFAULT NULL COMMENT '对话框标题',
  `dialog_width` varchar(100) NOT NULL DEFAULT '1' COMMENT '对话框宽度',
  `placeholder` varchar(100) DEFAULT NULL COMMENT '占位符',
  `id_column` varchar(32) DEFAULT NULL COMMENT 'ID字段',
  `id_column_rela_id` varchar(32) DEFAULT NULL COMMENT 'ID字段关联ID',
  `name_column` varchar(32) DEFAULT NULL COMMENT '名称ID字段',
  `name_column_rela_id` varchar(32) DEFAULT NULL COMMENT '名称ID字段关联ID',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义对话框';

-- ----------------------------
-- Records of gen_custom_selector
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_custom_selector_category
-- ----------------------------
DROP TABLE IF EXISTS `gen_custom_selector_category`;
CREATE TABLE `gen_custom_selector_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='自定义对话框分类';

-- ----------------------------
-- Records of gen_custom_selector_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_data_entity
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_entity`;
CREATE TABLE `gen_data_entity` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `gen_status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态',
  `table_schema` varchar(64) NOT NULL COMMENT '数据表所属的数据库名',
  `table_name` varchar(64) NOT NULL COMMENT '数据库表名',
  `engine` varchar(64) DEFAULT NULL COMMENT '数据库引擎',
  `table_collation` varchar(32) DEFAULT NULL COMMENT '字符校验编码集',
  `table_comment` varchar(2048) NOT NULL COMMENT '备注',
  `convert_type` tinyint(3) NOT NULL COMMENT '转换方式',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据实体';

-- ----------------------------
-- Records of gen_data_entity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_data_entity_category
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_entity_category`;
CREATE TABLE `gen_data_entity_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据实体分类';

-- ----------------------------
-- Records of gen_data_entity_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_data_entity_detail
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_entity_detail`;
CREATE TABLE `gen_data_entity_detail` (
  `id` varchar(32) NOT NULL,
  `entity_id` varchar(32) NOT NULL COMMENT '实体ID',
  `name` varchar(64) NOT NULL COMMENT '字段显示名称',
  `column_name` varchar(64) NOT NULL COMMENT '字段名称',
  `is_key` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否主键',
  `data_type` tinyint(3) NOT NULL COMMENT '数据类型',
  `column_order` int(11) NOT NULL COMMENT '排序编号',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `view_type` tinyint(3) NOT NULL COMMENT '显示类型',
  `data_dic_id` varchar(32) DEFAULT NULL COMMENT '数据字典ID',
  `custom_selector_id` varchar(32) DEFAULT NULL COMMENT '自定义选择器ID',
  `fix_enum` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否内置枚举',
  `enum_back` varchar(200) DEFAULT NULL COMMENT '后端枚举名',
  `enum_front` varchar(200) DEFAULT NULL COMMENT '前端枚举名',
  `regular_expression` varchar(200) DEFAULT NULL COMMENT '正则表达式',
  `is_order` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否排序字段',
  `order_type` varchar(20) DEFAULT NULL COMMENT '排序类型',
  `len` bigint(20) DEFAULT NULL COMMENT '长度',
  `decimals` int(11) DEFAULT NULL COMMENT '小数位数',
  `db_column_name` varchar(64) NOT NULL COMMENT '字段名',
  `db_data_type` varchar(64) NOT NULL DEFAULT '' COMMENT '字段数据类型',
  `is_nullable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许为空',
  `column_default` longtext COMMENT '默认值',
  `ordinal_position` bigint(21) unsigned NOT NULL DEFAULT '0' COMMENT '字段排序',
  `column_comment` varchar(1024) NOT NULL DEFAULT '' COMMENT '字段备注',
  `db_len` bigint(20) DEFAULT NULL COMMENT '长度',
  `db_decimals` int(11) DEFAULT NULL COMMENT '小数位数',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `entity_id` (`entity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据实体明细';

-- ----------------------------
-- Records of gen_data_entity_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_data_obj
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_obj`;
CREATE TABLE `gen_data_obj` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `main_table_id` varchar(32) NOT NULL DEFAULT '1' COMMENT '主表ID',
  `main_table_alias` varchar(200) NOT NULL COMMENT '主表别名',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据对象';

-- ----------------------------
-- Records of gen_data_obj
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_data_obj_category
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_obj_category`;
CREATE TABLE `gen_data_obj_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据实体分类';

-- ----------------------------
-- Records of gen_data_obj_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_data_obj_detail
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_obj_detail`;
CREATE TABLE `gen_data_obj_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `data_obj_id` varchar(32) NOT NULL COMMENT '数据对象ID',
  `main_table_detail_ids` longtext NOT NULL COMMENT '主表字段',
  `rela_type` tinyint(3) NOT NULL COMMENT '关联类型',
  `rela_mode` tinyint(3) NOT NULL COMMENT '关联方式',
  `sub_table_id` varchar(32) NOT NULL COMMENT '子表ID',
  `sub_table_alias` varchar(200) NOT NULL COMMENT '子表别名',
  `sub_table_detail_ids` longtext NOT NULL COMMENT '子表字段',
  `order_no` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `data_obj_id` (`data_obj_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据对象明细';

-- ----------------------------
-- Records of gen_data_obj_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_data_obj_query_detail
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_obj_query_detail`;
CREATE TABLE `gen_data_obj_query_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `data_obj_id` varchar(32) NOT NULL COMMENT '数据对象ID',
  `custom_name` varchar(200) NOT NULL COMMENT '显示名称',
  `custom_sql` longtext NOT NULL COMMENT '自定义SQL',
  `custom_alias` varchar(200) NOT NULL COMMENT '自定义别名',
  `data_type` tinyint(3) NOT NULL COMMENT '数据类型',
  `order_no` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `data_obj_id` (`data_obj_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据对象自定义查询明细';

-- ----------------------------
-- Records of gen_data_obj_query_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_detail_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_detail_column_config`;
CREATE TABLE `gen_detail_column_config` (
  `id` varchar(32) NOT NULL,
  `span` int(11) NOT NULL COMMENT '列宽',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='详情功能代码生成配置';

-- ----------------------------
-- Records of gen_detail_column_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_generate_info
-- ----------------------------
DROP TABLE IF EXISTS `gen_generate_info`;
CREATE TABLE `gen_generate_info` (
  `id` varchar(32) NOT NULL,
  `template_type` tinyint(3) NOT NULL COMMENT '生成模板类型',
  `package_name` varchar(200) NOT NULL COMMENT '包名',
  `module_name` varchar(200) NOT NULL COMMENT '模块名',
  `biz_name` varchar(200) NOT NULL COMMENT '业务名',
  `class_name` varchar(200) NOT NULL COMMENT '类名',
  `parent_menu_id` varchar(32) DEFAULT NULL COMMENT '父级菜单ID',
  `key_type` tinyint(3) NOT NULL COMMENT '主键类型',
  `author` varchar(100) DEFAULT NULL COMMENT '作者',
  `class_description` varchar(200) NOT NULL COMMENT '类描述',
  `menu_code` varchar(20) NOT NULL COMMENT '本级菜单编号',
  `menu_name` varchar(200) NOT NULL COMMENT '本级菜单名称',
  `detail_span` int(11) NOT NULL COMMENT '详情页Span总数量',
  `is_cache` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否应用缓存',
  `has_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否内置删除功能',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='代码生成基本信息';

-- ----------------------------
-- Records of gen_generate_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_query_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_query_column_config`;
CREATE TABLE `gen_query_column_config` (
  `id` varchar(32) NOT NULL,
  `width_type` tinyint(3) NOT NULL COMMENT '宽度类型',
  `width` int(11) NOT NULL COMMENT '宽度',
  `sortable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否页面排序',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='查询功能代码生成配置';

-- ----------------------------
-- Records of gen_query_column_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_query_params_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_query_params_column_config`;
CREATE TABLE `gen_query_params_column_config` (
  `id` varchar(32) NOT NULL,
  `query_type` tinyint(3) NOT NULL COMMENT '查询类型',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='查询参数功能代码生成配置';

-- ----------------------------
-- Records of gen_query_params_column_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_simple_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_simple_table`;
CREATE TABLE `gen_simple_table` (
  `id` varchar(32) NOT NULL,
  `table_schema` varchar(64) NOT NULL COMMENT '数据表所属的数据库名',
  `table_name` varchar(64) NOT NULL COMMENT '数据库表名',
  `engine` varchar(64) DEFAULT NULL COMMENT '数据库引擎',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `table_collation` varchar(32) DEFAULT NULL COMMENT '字符校验编码集',
  `table_comment` varchar(2048) NOT NULL COMMENT '备注',
  `convert_type` tinyint(3) NOT NULL COMMENT '转换方式',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `table_schema` (`table_schema`,`table_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据库单表';

-- ----------------------------
-- Records of gen_simple_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_simple_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_simple_table_column`;
CREATE TABLE `gen_simple_table_column` (
  `id` varchar(32) NOT NULL,
  `table_id` varchar(32) NOT NULL,
  `column_name` varchar(64) NOT NULL COMMENT '字段名',
  `data_type` varchar(64) NOT NULL DEFAULT '' COMMENT '字段数据类型',
  `is_nullable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许为空',
  `is_key` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否主键',
  `column_default` longtext COMMENT '默认值',
  `ordinal_position` bigint(21) unsigned NOT NULL DEFAULT '0' COMMENT '字段排序',
  `column_comment` varchar(1024) NOT NULL DEFAULT '' COMMENT '字段备注',
  `len` bigint(20) DEFAULT NULL COMMENT '长度',
  `decimals` int(11) DEFAULT NULL COMMENT '小数位数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据库单表列信息';

-- ----------------------------
-- Records of gen_simple_table_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_update_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_update_column_config`;
CREATE TABLE `gen_update_column_config` (
  `id` varchar(32) NOT NULL,
  `required` tinyint(1) NOT NULL DEFAULT '0',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='修改功能代码生成配置';

-- ----------------------------
-- Records of gen_update_column_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for op_logs
-- ----------------------------
DROP TABLE IF EXISTS `op_logs`;
CREATE TABLE `op_logs` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(200) NOT NULL COMMENT '日志名称',
  `log_type` tinyint(3) NOT NULL COMMENT '类别',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `extra` longtext COMMENT '补充信息',
  `ip` varchar(100) NOT NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `create_by` (`create_by`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='操作日志';

-- ----------------------------
-- Records of op_logs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for recursion_mapping
-- ----------------------------
DROP TABLE IF EXISTS `recursion_mapping`;
CREATE TABLE `recursion_mapping` (
  `id` varchar(32) NOT NULL,
  `node_id` varchar(32) NOT NULL COMMENT '节点ID',
  `node_type` tinyint(3) NOT NULL COMMENT '节点类型',
  `path` longtext COMMENT '从顶点到当前结点的路径，用,分割',
  `level` int(11) NOT NULL COMMENT '节点层级',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `node_id` (`node_id`,`node_type`) USING BTREE,
  KEY `node_type` (`node_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='树形数据节点关系表';

-- ----------------------------
-- Records of recursion_mapping
-- ----------------------------
BEGIN;
INSERT INTO `recursion_mapping` (`id`, `node_id`, `node_type`, `path`, `level`) VALUES ('1', '2', 1, NULL, 1);
INSERT INTO `recursion_mapping` (`id`, `node_id`, `node_type`, `path`, `level`) VALUES ('2', '4', 1, '2', 2);
INSERT INTO `recursion_mapping` (`id`, `node_id`, `node_type`, `path`, `level`) VALUES ('3', '3', 1, '1', 2);
INSERT INTO `recursion_mapping` (`id`, `node_id`, `node_type`, `path`, `level`) VALUES ('4', '1', 1, NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for settle_check_sheet
-- ----------------------------
DROP TABLE IF EXISTS `settle_check_sheet`;
CREATE TABLE `settle_check_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `total_pay_amount` decimal(24,2) NOT NULL COMMENT '应付金额',
  `total_payed_amount` decimal(24,2) NOT NULL COMMENT '已付金额',
  `total_discount_amount` decimal(24,2) NOT NULL COMMENT '已优惠金额',
  `start_date` date NOT NULL COMMENT '起始日期',
  `end_date` date NOT NULL COMMENT '截止日期',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商对账单';

-- ----------------------------
-- Records of settle_check_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_check_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `settle_check_sheet_detail`;
CREATE TABLE `settle_check_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '对账单ID',
  `biz_id` varchar(32) NOT NULL COMMENT '单据ID',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  `calc_type` tinyint(3) NOT NULL COMMENT '计算类型',
  `pay_amount` decimal(24,2) NOT NULL COMMENT '应付金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`biz_id`) USING BTREE,
  KEY `biz_id` (`biz_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商对账单明细';

-- ----------------------------
-- Records of settle_check_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_fee_sheet
-- ----------------------------
DROP TABLE IF EXISTS `settle_fee_sheet`;
CREATE TABLE `settle_fee_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `sheet_type` tinyint(3) NOT NULL COMMENT '单据类型',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商费用单';

-- ----------------------------
-- Records of settle_fee_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_fee_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `settle_fee_sheet_detail`;
CREATE TABLE `settle_fee_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '费用单ID',
  `item_id` varchar(32) NOT NULL COMMENT '项目ID',
  `amount` decimal(24,2) NOT NULL COMMENT '金额',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`item_id`) USING BTREE,
  KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商费用单明细';

-- ----------------------------
-- Records of settle_fee_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_in_item
-- ----------------------------
DROP TABLE IF EXISTS `settle_in_item`;
CREATE TABLE `settle_in_item` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='收入项目';

-- ----------------------------
-- Records of settle_in_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_out_item
-- ----------------------------
DROP TABLE IF EXISTS `settle_out_item`;
CREATE TABLE `settle_out_item` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='支出项目';

-- ----------------------------
-- Records of settle_out_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_pre_sheet
-- ----------------------------
DROP TABLE IF EXISTS `settle_pre_sheet`;
CREATE TABLE `settle_pre_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商预付款单';

-- ----------------------------
-- Records of settle_pre_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_pre_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `settle_pre_sheet_detail`;
CREATE TABLE `settle_pre_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '预付款单ID',
  `item_id` varchar(32) NOT NULL COMMENT '项目ID',
  `amount` decimal(24,2) NOT NULL COMMENT '金额',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`item_id`) USING BTREE,
  KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商预付款单明细';

-- ----------------------------
-- Records of settle_pre_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_sheet
-- ----------------------------
DROP TABLE IF EXISTS `settle_sheet`;
CREATE TABLE `settle_sheet` (
  `id` varchar(32) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '单号',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `total_amount` decimal(24,2) NOT NULL COMMENT '总金额',
  `total_discount_amount` decimal(24,2) NOT NULL COMMENT '已优惠金额',
  `start_date` date NOT NULL COMMENT '起始日期',
  `end_date` date NOT NULL COMMENT '截止日期',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商结算单';

-- ----------------------------
-- Records of settle_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for settle_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `settle_sheet_detail`;
CREATE TABLE `settle_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '结算单ID',
  `biz_id` varchar(32) NOT NULL COMMENT '单据ID',
  `pay_amount` decimal(24,2) NOT NULL COMMENT '实付金额',
  `discount_amount` decimal(24,2) NOT NULL COMMENT '优惠金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`biz_id`) USING BTREE,
  KEY `biz_id` (`biz_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商结算单明细';

-- ----------------------------
-- Records of settle_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sw_file_box
-- ----------------------------
DROP TABLE IF EXISTS `sw_file_box`;
CREATE TABLE `sw_file_box` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `url` longtext NOT NULL COMMENT 'Url',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文件收纳箱';

-- ----------------------------
-- Records of sw_file_box
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sw_online_excel
-- ----------------------------
DROP TABLE IF EXISTS `sw_online_excel`;
CREATE TABLE `sw_online_excel` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `content` longtext NOT NULL COMMENT '内容',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1-在用 0停用',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='在线Excel';

-- ----------------------------
-- Records of sw_online_excel
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_data_dic
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dic`;
CREATE TABLE `sys_data_dic` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据字典';

-- ----------------------------
-- Records of sys_data_dic
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_data_dic_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dic_category`;
CREATE TABLE `sys_data_dic_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据字典分类';

-- ----------------------------
-- Records of sys_data_dic_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_data_dic_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dic_item`;
CREATE TABLE `sys_data_dic_item` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `dic_id` varchar(32) NOT NULL COMMENT '字典ID',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dic_id` (`dic_id`,`code`) USING BTREE,
  UNIQUE KEY `dic_id_2` (`dic_id`,`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据字典';

-- ----------------------------
-- Records of sys_data_dic_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_data_permission_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_permission_data`;
CREATE TABLE `sys_data_permission_data` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `biz_id` varchar(32) NOT NULL COMMENT '业务ID',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  `permission_type` tinyint(3) NOT NULL COMMENT '权限类型',
  `permission` longtext NOT NULL COMMENT '数据权限',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `biz_id` (`biz_id`,`biz_type`,`permission_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据权限数据';

-- ----------------------------
-- Records of sys_data_permission_data
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_data_permission_model_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_permission_model_detail`;
CREATE TABLE `sys_data_permission_model_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `model_id` int(11) NOT NULL COMMENT '模型ID',
  `condition_type` longtext NOT NULL COMMENT '条件',
  `input_type` tinyint(3) NOT NULL COMMENT '输入类型',
  `table_name` varchar(200) NOT NULL COMMENT '表名',
  `column_name` varchar(200) NOT NULL COMMENT '字段名',
  `enum_name` varchar(200) DEFAULT NULL COMMENT '前端枚举名',
  `sql_value` longtext COMMENT 'SQL',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `model_id` (`model_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据权限模型明细';

-- ----------------------------
-- Records of sys_data_permission_model_detail
-- ----------------------------
BEGIN;
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (1, '编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (2, '名称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (3, '简称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'short_name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (4, 'SKU', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'sku_code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (5, '外部编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'external_code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (6, '品类编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'category', 'code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (7, '品类名称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'category', 'name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (8, '品牌编号', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'brand', 'code', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (9, '品牌名称', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'brand', 'name', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (10, '规格', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'spec', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (11, '单位', 1, '0,1,2,3,4,5,6,7,8,9,10', 0, 'product', 'unit', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (12, '状态', 1, '0,5,6,7', 1, 'product', 'available', 'AVAILABLE', NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (13, '创建时间', 1, '0,1,2,3,4,5', 2, 'product', 'create_time', NULL, NULL);
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (14, '创建人部门与本人相同', 2, '6', 99, 'order', 'create_by_id', NULL, 'SELECT __ud.user_id FROM sys_user_dept AS __ud WHERE __ud.dept_id IN ({__var#curDeptIds})');
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (15, '创建人部门是本人的上级部门', 2, '6', 99, 'order', 'create_by_id', NULL, 'SELECT DISTINCT __ud.user_id FROM sys_user_dept AS __ud WHERE FIND_IN_SET(__ud.dept_id,(SELECT GROUP_CONCAT(__mp.path) FROM sys_dept AS __d INNER JOIN recursion_mapping AS __mp ON __mp.node_id=__d.id AND __mp.node_type=1 WHERE __d.id IN ({__var#curDeptIds}) AND __mp.level> 1))');
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (16, '创建人部门是本人的下级部门', 2, '6', 99, 'order', 'create_by_id', NULL, 'SELECT DISTINCT __ud.user_id FROM sys_user_dept AS __ud WHERE __ud.dept_id IN (SELECT __mp.node_id FROM recursion_mapping AS __mp WHERE FIND_IN_SET((SELECT __d.id FROM sys_dept AS __d WHERE __d.id IN ({__var#curDeptIds})),__mp.path) AND __mp.node_type=1)');
INSERT INTO `sys_data_permission_model_detail` (`id`, `name`, `model_id`, `condition_type`, `input_type`, `table_name`, `column_name`, `enum_name`, `sql_value`) VALUES (17, '创建时间', 2, '0,1,2,3,4,5', 2, 'order', 'create_time', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `short_name` varchar(20) NOT NULL COMMENT '简称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`id`, `code`, `name`, `short_name`, `parent_id`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1', '001', '销售中心', '销售中心', NULL, 1, '', '系统管理员', '1', '2022-01-13 00:37:06', '系统管理员', '1', '2022-01-13 00:37:06');
INSERT INTO `sys_dept` (`id`, `code`, `name`, `short_name`, `parent_id`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2', '002', '研发中心', '研发中心', NULL, 1, '', '系统管理员', '1', '2022-01-13 00:37:17', '系统管理员', '1', '2022-01-13 00:37:17');
INSERT INTO `sys_dept` (`id`, `code`, `name`, `short_name`, `parent_id`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3', '001001', '销售部', '销售部', '1', 1, '', '系统管理员', '1', '2022-01-13 00:38:03', '系统管理员', '1', '2022-01-13 00:38:03');
INSERT INTO `sys_dept` (`id`, `code`, `name`, `short_name`, `parent_id`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4', '002001', '研发部', '研发部', '2', 1, '', '系统管理员', '1', '2022-01-13 00:38:15', '系统管理员', '1', '2022-01-13 00:38:15');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称（前端使用）',
  `title` varchar(20) NOT NULL COMMENT '标题',
  `icon` varchar(200) DEFAULT NULL COMMENT '图标',
  `component_type` tinyint(3) DEFAULT NULL COMMENT '组件类型（前端使用）',
  `component` varchar(200) DEFAULT NULL COMMENT '组件（前端使用）',
  `request_param` longtext COMMENT '自定义请求参数',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父级ID',
  `sys_module_id` varchar(32) DEFAULT NULL COMMENT '系统模块ID',
  `path` varchar(200) DEFAULT NULL COMMENT '路由路径（前端使用）',
  `no_cache` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否缓存（前端使用）',
  `display` tinyint(3) NOT NULL COMMENT '类型 0-目录 1-菜单 2-功能',
  `hidden` tinyint(1) DEFAULT '0' COMMENT '是否隐藏（前端使用）',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限',
  `is_special` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否特殊菜单',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `code` (`code`,`name`,`title`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('0001', '0001', 'Xingyun', '星云工作台', 'a-menu', NULL, '', NULL, NULL, NULL, '/xingyun', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000', '1000', 'System', '系统管理', 'a-menu', NULL, '', NULL, '0001', '2', '/system', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000001', '1000001', 'Menu', '菜单管理', NULL, 0, '/system/menu/index', NULL, '1000', '2', '/menu', 0, 1, 0, 'system:menu:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000001001', '1000001001', '', '新增菜单', NULL, 0, '', NULL, '1000001', '2', '', 0, 2, 0, 'system:menu:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000001002', '1000001002', '', '修改菜单', NULL, 0, '', NULL, '1000001', '2', '', 0, 2, 0, 'system:menu:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000001003', '1000001003', '', '删除菜单', NULL, 0, '', NULL, '1000001', '2', '', 0, 2, 0, 'system:menu:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000002', '1000002', 'Dept', '部门管理', NULL, 0, '/system/dept/index', NULL, '1000', '2', '/dept', 0, 1, 0, 'system:dept:query', 1, 1, '', '系统管理员', '1', '2021-07-05 01:09:27', '系统管理员', '1', '2021-07-05 01:09:27');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000002001', '1000002001', '', '新增部门', NULL, 0, '', NULL, '1000002', '2', '', 0, 2, 0, 'system:dept:add', 1, 1, '', '系统管理员', '1', '2021-06-27 01:33:31', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000002002', '1000002002', '', '修改部门', NULL, 0, '', NULL, '1000002', '2', '', 0, 2, 0, 'system:dept:modify', 1, 1, '', '系统管理员', '1', '2021-06-27 01:33:47', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000002003', '1000002003', '', '部门权限', NULL, 0, '', NULL, '1000002', '2', '', 0, 2, 0, 'system:dept:permission', 1, 1, '', '系统管理员', '1', '2021-06-27 01:33:47', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000003', '1000003', 'Position', '岗位管理', NULL, 0, '/system/position/index', NULL, '1000', '2', '/position', 0, 1, 0, 'system:position:query', 1, 1, '', '系统管理员', '1', '2021-07-01 23:26:17', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000003001', '1000003001', '', '新增岗位', NULL, 0, '', NULL, '1000003', '2', '', 0, 2, 0, 'system:position:add', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:17', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000003002', '1000003002', '', '修改岗位', NULL, 0, '', NULL, '1000003', '2', '', 0, 2, 0, 'system:position:modify', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000004', '1000004', 'Role', '角色管理', NULL, 0, '/system/role/index', NULL, '1000', '2', '/role', 0, 1, 0, 'system:role:query', 1, 1, '', '系统管理员', '1', '2021-07-04 00:35:49', '系统管理员', '1', '2021-07-04 00:35:49');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000004001', '1000004001', '', '新增角色', NULL, 0, '', NULL, '1000004', '2', '', 0, 2, 0, 'system:role:add', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:17', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000004002', '1000004002', '', '修改角色', NULL, 0, '', NULL, '1000004', '2', '', 0, 2, 0, 'system:role:modify', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000004003', '1000004003', '', '角色授权', NULL, 0, '', NULL, '1000004', '2', '', 0, 2, 0, 'system:role:permission', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000005', '1000005', 'User', '用户管理', NULL, 0, '/system/user/index', NULL, '1000', '2', '/user', 0, 1, 0, 'system:user:query', 1, 1, '', '系统管理员', '1', '2021-07-05 01:08:40', '系统管理员', '1', '2021-07-05 01:08:40');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000005001', '1000005001', '', '新增用户', NULL, 0, '', NULL, '1000005', '2', '', 0, 2, 0, 'system:user:add', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:17', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000005002', '1000005002', '', '修改用户', NULL, 0, '', NULL, '1000005', '2', '', 0, 2, 0, 'system:user:modify', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000005003', '1000005003', '', '用户授权', NULL, 0, '', NULL, '1000005', '2', '', 0, 2, 0, 'system:user:permission', 1, 1, '', '系统管理员', '1', '2021-06-30 00:32:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000006', '1000006', 'Oplog', '操作日志', NULL, 0, '/system/oplog/index', NULL, '1000', '2', '/oplog', 0, 1, 0, 'system:oplog:query', 1, 1, '', '系统管理员', '1', '2021-07-05 01:08:40', '系统管理员', '1', '2021-07-05 01:08:40');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000007', '1000007', 'SysParameter', '系统参数', NULL, 0, '/system/parameter/index', NULL, '1000', '2', '/parameter', 0, 1, 0, 'system:parameter:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000007001', '1000007001', '', '新增系统参数', NULL, 0, '', NULL, '1000007', '2', '', 0, 2, 0, 'system:parameter:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000007002', '1000007002', '', '修改系统参数', NULL, 0, '', NULL, '1000007', '2', '', 0, 2, 0, 'system:parameter:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000007003', '1000007003', '', '删除系统参数', NULL, 0, '', NULL, '1000007', '2', '', 0, 2, 0, 'system:parameter:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000008', '1000008', 'PublishSysNotice', '发布系统通知', NULL, 0, '/system/notice/publish', NULL, '1000', '2', '/system/notice/publish', 0, 1, 0, 'system:notice:publish', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000008001', '1000008001', '', '新增系统通知', NULL, 0, '', NULL, '1000008', '2', '', 0, 2, 0, 'system:notice:add', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000008002', '1000008002', '', '修改系统通知', NULL, 0, '', NULL, '1000008', '2', '', 0, 2, 0, 'system:notice:modify', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000009', '1000009', 'SysNotice', '系统通知', NULL, 0, '/system/notice/index', NULL, '1000', '2', '/system/notice', 0, 1, 0, 'system:notice:query', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010', '1000010', 'SysDataDic', '数据字典', NULL, 0, '/system/dic/index', NULL, '1000', '2', '/dic', 0, 1, 0, 'system:dic:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010001', '1000010001', '', '新增数据字典', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010002', '1000010002', '', '修改数据字典', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010003', '1000010003', '', '删除数据字典', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010004', '1000010004', '', '新增数据字典分类', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic-category:add', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010005', '1000010005', '', '修改数据字典分类', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic-category:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010006', '1000010006', '', '删除数据字典分类', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic-category:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010007', '1000010007', '', '新增数据字典值', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic-item:add', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010008', '1000010008', '', '修改数据字典值', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic-item:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000010009', '1000010009', '', '删除数据字典值', NULL, 0, '', NULL, '1000010', '2', '', 0, 2, 0, 'system:dic-item:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000011', '1000011', 'SysTenant', '租户管理', NULL, 0, '/system/tenant/index', NULL, '1001', '1', '/system/tenant', 0, 1, 0, 'system:tenant:query', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000011001', '1000011001', '', '新增租户', NULL, 0, '', NULL, '1000011', '1', '', 0, 2, 0, 'system:tenant:add', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000011002', '1000011002', '', '修改租户', NULL, 0, '', NULL, '1000011', '1', '', 0, 2, 0, 'system:tenant:modify', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000011003', '1000011003', '', '授权模块', NULL, 0, '', NULL, '1000011', '1', '', 0, 2, 0, 'system:tenant:module', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000012', '1000012', 'OpenDomain', '开放域', 'a-menu', 0, '/system/open-domain/index', NULL, '1001', '14', '/open-domain', 0, 1, 0, 'system:open-domain:config', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1001', '1001', 'Platform', '平台管理', 'a-menu', NULL, '', NULL, '0001', '1', '/platform', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1001001', '1001001', 'OnelineCode', '在线开发', 'a-menu', 0, '/iframes/index', NULL, '1001', '1', '/online-code?src=${magic-api.base-url}${magic-api.web}/index.html', 0, 1, 0, 'system:online-code:config', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000', '2000', 'BaseData', '基础信息管理', 'a-menu', NULL, '', NULL, '0001', '3', '/base-data', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002', '2000002', 'StoreCenterInfo', '仓库信息', NULL, 0, '/base-data/store-center/index', NULL, '2000', '3', '/store-center', 0, 1, 0, 'base-data:store-center:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002001', '2000002001', '', '新增仓库', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:store-center:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002002', '2000002002', '', '修改仓库', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:store-center:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002003', '2000002003', '', '导入仓库', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:store-center:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000004', '2000004', 'Customer', '客户信息', NULL, 0, '/base-data/customer/index', NULL, '2000', '3', '/customer', 0, 1, 0, 'base-data:customer:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000004001', '2000004001', '', '新增客户', NULL, 0, '', NULL, '2000004', '3', '', 0, 2, 0, 'base-data:customer:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000004002', '2000004002', '', '修改客户', NULL, 0, '', NULL, '2000004', '3', '', 0, 2, 0, 'base-data:customer:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000004003', '2000004003', '', '导入客户', NULL, 0, '', NULL, '2000004', '3', '', 0, 2, 0, 'base-data:customer:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000005', '2000005', 'Supplier', '供应商信息', NULL, 0, '/base-data/supplier/index', NULL, '2000', '3', '/supplier', 0, 1, 0, 'base-data:supplier:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000005001', '2000005001', '', '新增供应商', NULL, 0, '', NULL, '2000005', '3', '', 0, 2, 0, 'base-data:supplier:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000005002', '2000005002', '', '修改供应商', NULL, 0, '', NULL, '2000005', '3', '', 0, 2, 0, 'base-data:supplier:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000005003', '2000005003', '', '导入供应商', NULL, 0, '', NULL, '2000005', '3', '', 0, 2, 0, 'base-data:supplier:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000006', '2000006', 'Member', '会员信息', NULL, 0, '/base-data/member/index', NULL, '2000', '3', '/member', 0, 1, 0, 'base-data:member:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000006001', '2000006001', '', '新增会员', NULL, 0, '', NULL, '2000006', '3', '', 0, 2, 0, 'base-data:member:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000006002', '2000006002', '', '修改会员', NULL, 0, '', NULL, '2000006', '3', '', 0, 2, 0, 'base-data:member:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000006003', '2000006003', '', '导入会员', NULL, 0, '', NULL, '2000006', '3', '', 0, 2, 0, 'base-data:member:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000007', '2000007', 'Shop', '门店信息', NULL, 0, '/base-data/shop/index', NULL, '2000', '3', '/shop', 0, 1, 0, 'base-data:shop:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000007001', '2000007001', '', '新增门店', NULL, 0, '', NULL, '2000007', '3', '', 0, 2, 0, 'base-data:shop:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000007002', '2000007002', '', '修改门店', NULL, 0, '', NULL, '2000007', '3', '', 0, 2, 0, 'base-data:shop:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000007003', '2000007003', '', '导入门店', NULL, 0, '', NULL, '2000007', '3', '', 0, 2, 0, 'base-data:shop:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000008', '2000008', 'PayType', '支付方式', NULL, 0, '/base-data/pay-type/index', NULL, '2000', '3', '/pay-type', 0, 1, 0, 'base-data:pay-type:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000008001', '2000008001', '', '新增支付方式', NULL, 0, '', NULL, '2000008', '3', '', 0, 2, 0, 'base-data:pay-type:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000008002', '2000008002', '', '修改支付方式', NULL, 0, '', NULL, '2000008', '3', '', 0, 2, 0, 'base-data:pay-type:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009', '2000009', 'Address', '地址库', NULL, 0, '/base-data/address/index', NULL, '2000', '15', '/address', 0, 1, 0, 'base-data:address:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009001', '2000009001', '', '新增地址', NULL, 0, '', NULL, '2000009', '15', '', 0, 2, 0, 'base-data:address:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009002', '2000009002', '', '修改地址', NULL, 0, '', NULL, '2000009', '15', '', 0, 2, 0, 'base-data:address:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009003', '2000009003', '', '导出地址', NULL, 0, '', NULL, '2000009', '15', '', 0, 2, 0, 'base-data:address:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000009004', '2000009004', '', '导入地址', NULL, 0, '', NULL, '2000009', '15', '', 0, 2, 0, 'base-data:address:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000010', '2000010', 'LogisticsCompany', '物流公司', NULL, 0, '/base-data/logistics/company/index', NULL, '2000', '15', '/logistics/company', 0, 1, 0, 'base-data:logistics-company:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000010001', '2000010001', '', '新增物流公司', NULL, 0, '', NULL, '2000010', '15', '', 0, 2, 0, 'base-data:logistics-company:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000010002', '2000010002', '', '修改物流公司', NULL, 0, '', NULL, '2000010', '15', '', 0, 2, 0, 'base-data:logistics-company:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001', '2001', 'Product', '商品中心', 'a-menu', NULL, '', NULL, '0001', '4', '/product', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001001', '2001001', 'ProductCategory', '商品类目', NULL, 0, '/base-data/product/category/index', NULL, '2001', '4', '/category', 0, 1, 0, 'base-data:product:category:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001001001', '2001001001', '', '新增类目', NULL, 0, '', NULL, '2001001', '4', '', 0, 2, 0, 'base-data:product:category:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001001002', '2001001002', '', '修改类目', NULL, 0, '', NULL, '2001001', '4', '', 0, 2, 0, 'base-data:product:category:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001001003', '2001001003', '', '导入类目', NULL, 0, '', NULL, '2001001', '4', '', 0, 2, 0, 'base-data:product:category:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001002', '2001002', 'ProductBrand', '商品品牌', NULL, 0, '/base-data/product/brand/index', NULL, '2001', '4', '/brand', 0, 1, 0, 'base-data:product:brand:query', 1, 1, '', '系统管理员', '1', '2021-07-06 17:01:00', '系统管理员', '1', '2021-07-06 17:01:00');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001002001', '2001002001', '', '新增品牌', NULL, 0, '', NULL, '2001002', '4', '', 0, 2, 0, 'base-data:product:brand:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001002002', '2001002002', '', '修改品牌', NULL, 0, '', NULL, '2001002', '4', '', 0, 2, 0, 'base-data:product:brand:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001002003', '2001002003', '', '导入品牌', NULL, 0, '', NULL, '2001002', '4', '', 0, 2, 0, 'base-data:product:brand:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001003', '2001003', 'ProductSalePropGroup', '商品销售属性组', NULL, 0, '/base-data/product/saleprop/index', NULL, '2001', '4', '/spec', 0, 1, 0, 'base-data:product:saleprop-group:query', 1, 1, '', '系统管理员', '1', '2021-07-06 17:01:00', '系统管理员', '1', '2021-07-06 17:01:00');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001003001', '2001003001', '', '新增销售属性组', NULL, 0, '', NULL, '2001003', '4', '', 0, 2, 0, 'base-data:product:saleprop-group:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001003002', '2001003002', '', '修改销售属性组', NULL, 0, '', NULL, '2001003', '4', '', 0, 2, 0, 'base-data:product:saleprop-group:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001003003', '2001003003', '', '查询销售属性', NULL, 0, '', NULL, '2001003', '4', '', 0, 2, 0, 'base-data:product:saleprop-item:query', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001003004', '2001003004', '', '新增销售属性', NULL, 0, '', NULL, '2001003', '4', '', 0, 2, 0, 'base-data:product:saleprop-item:add', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001003005', '2001003005', '', '修改销售属性', NULL, 0, '', NULL, '2001003', '4', '', 0, 2, 0, 'base-data:product:saleprop-item:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001004', '2001004', 'ProductProperty', '商品属性', NULL, 0, '/base-data/product/property/index', NULL, '2001', '4', '/property', 0, 1, 0, 'base-data:product:property:query', 1, 1, '', '系统管理员', '1', '2021-07-06 17:01:00', '系统管理员', '1', '2021-07-06 17:01:00');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001004001', '2001004001', '', '新增属性', NULL, 0, '', NULL, '2001004', '4', '', 0, 2, 0, 'base-data:product:property:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001004002', '2001004002', '', '修改属性', NULL, 0, '', NULL, '2001004', '4', '', 0, 2, 0, 'base-data:product:property:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001004003', '2001004003', '', '查询属性值', NULL, 0, '', NULL, '2001004', '4', '', 0, 2, 0, 'base-data:product:property-item:query', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001004004', '2001004004', '', '新增属性值', NULL, 0, '', NULL, '2001004', '4', '', 0, 2, 0, 'base-data:product:property-item:add', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001004005', '2001004005', '', '修改属性值', NULL, 0, '', NULL, '2001004', '4', '', 0, 2, 0, 'base-data:product:property-item:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001005', '2001005', 'ProductInfo', '商品管理', NULL, 0, '/base-data/product/info/index', NULL, '2001', '4', '/info', 0, 1, 0, 'base-data:product:info:query', 1, 1, '', '系统管理员', '1', '2021-07-06 17:01:00', '系统管理员', '1', '2021-07-06 17:01:00');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001005001', '2001005001', '', '新增商品', NULL, 0, '', NULL, '2001005', '4', '', 0, 2, 0, 'base-data:product:info:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001005002', '2001005002', '', '修改商品', NULL, 0, '', NULL, '2001005', '4', '', 0, 2, 0, 'base-data:product:info:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001005003', '2001005003', '', '导入商品', NULL, 0, '', NULL, '2001005', '4', '', 0, 2, 0, 'base-data:product:info:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006', '2001006', 'ProductPoly', '商品SPU', NULL, 0, '/base-data/product/poly/index', NULL, '2001', '4', '/poly', 0, 1, 0, 'base-data:product:poly:query', 1, 1, '', '系统管理员', '1', '2021-07-06 17:01:00', '系统管理员', '1', '2021-07-06 17:01:00');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006001', '2001006001', '', '新增商品SPU', NULL, 0, '', NULL, '2001006', '4', '', 0, 2, 0, 'base-data:product:poly:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006002', '2001006002', '', '修改商品SPU', NULL, 0, '', NULL, '2001006', '4', '', 0, 2, 0, 'base-data:product:poly:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006003', '2001006003', '', '导入商品SPU', NULL, 0, '', NULL, '2001006', '4', '', 0, 2, 0, 'base-data:product:poly:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002', '2002', 'Purchase', '采购管理', 'a-menu', NULL, '', NULL, '0001', '5', '/purchase', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002001', '2002001', 'PurchaseConfig', '采购参数设置', NULL, 0, '/sc/purchase/config/index', NULL, '2002', '5', '/config', 1, 1, 0, 'purchase:config:modify', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002002', '2002002', 'PurchaseOrder', '采购订单管理', NULL, 0, '/sc/purchase/order/index', NULL, '2002', '5', '/order', 0, 1, 0, 'purchase:order:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002002001', '2002002001', '', '新增采购订单', NULL, 0, '', NULL, '2002002', '5', '', 0, 2, 0, 'purchase:order:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002002002', '2002002002', '', '修改采购订单', NULL, 0, '', NULL, '2002002', '5', '', 0, 2, 0, 'purchase:order:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002002003', '2002002003', '', '删除采购订单', NULL, 0, '', NULL, '2002002', '5', '', 0, 2, 0, 'purchase:order:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002002004', '2002002004', '', '审核采购订单', NULL, 0, '', NULL, '2002002', '5', '', 0, 2, 0, 'purchase:order:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002002005', '2002002005', '', '导出采购订单', NULL, 0, '', NULL, '2002002', '5', '', 0, 2, 0, 'purchase:order:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002002006', '2002002006', '', '导入采购订单', NULL, 0, '', NULL, '2002002', '5', '', 0, 2, 0, 'purchase:order:import', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002003', '2002003', 'ReceiveSheet', '采购收货管理', NULL, 0, '/sc/purchase/receive/index', NULL, '2002', '5', '/receive', 0, 1, 0, 'purchase:receive:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002003001', '2002003001', '', '新增采购收货单', NULL, 0, '', NULL, '2002003', '5', '', 0, 2, 0, 'purchase:receive:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002003002', '2002003002', '', '修改采购收货单', NULL, 0, '', NULL, '2002003', '5', '', 0, 2, 0, 'purchase:receive:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002003003', '2002003003', '', '删除采购收货单', NULL, 0, '', NULL, '2002003', '5', '', 0, 2, 0, 'purchase:receive:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002003004', '2002003004', '', '审核采购收货单', NULL, 0, '', NULL, '2002003', '5', '', 0, 2, 0, 'purchase:receive:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002003005', '2002003005', '', '导出采购收货单', NULL, 0, '', NULL, '2002003', '5', '', 0, 2, 0, 'purchase:receive:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002003006', '2002003006', '', '导入采购收货单', NULL, 0, '', NULL, '2002003', '5', '', 0, 2, 0, 'purchase:receive:import', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002004', '2002004', 'PurchaseReturn', '采购退货管理', NULL, 0, '/sc/purchase/return/index', NULL, '2002', '5', '/return', 0, 1, 0, 'purchase:return:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002004001', '2002004001', '', '新增采购退货单', NULL, 0, '', NULL, '2002004', '5', '', 0, 2, 0, 'purchase:return:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002004002', '2002004002', '', '修改采购退货单', NULL, 0, '', NULL, '2002004', '5', '', 0, 2, 0, 'purchase:return:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002004003', '2002004003', '', '删除采购退货单', NULL, 0, '', NULL, '2002004', '5', '', 0, 2, 0, 'purchase:return:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002004004', '2002004004', '', '审核采购退货单', NULL, 0, '', NULL, '2002004', '5', '', 0, 2, 0, 'purchase:return:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2002004005', '2002004005', '', '导出采购退货单', NULL, 0, '', NULL, '2002004', '5', '', 0, 2, 0, 'purchase:return:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003', '2003', 'Sale', '销售管理', 'a-menu', NULL, '', NULL, '0001', '6', '/sale', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003001', '2003001', 'SaleConfig', '销售参数设置', NULL, 0, '/sc/sale/config/index', NULL, '2003', '6', '/config', 1, 1, 0, 'sale:config:modify', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003002', '2003002', 'SaleOrder', '销售订单管理', NULL, 0, '/sc/sale/order/index', NULL, '2003', '6', '/order', 0, 1, 0, 'sale:order:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003002001', '2003002001', '', '新增销售订单', NULL, 0, '', NULL, '2003002', '6', '', 0, 2, 0, 'sale:order:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003002002', '2003002002', '', '修改销售订单', NULL, 0, '', NULL, '2003002', '6', '', 0, 2, 0, 'sale:order:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003002003', '2003002003', '', '删除销售订单', NULL, 0, '', NULL, '2003002', '6', '', 0, 2, 0, 'sale:order:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003002004', '2003002004', '', '审核销售订单', NULL, 0, '', NULL, '2003002', '6', '', 0, 2, 0, 'sale:order:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003002005', '2003002005', '', '导出销售订单', NULL, 0, '', NULL, '2003002', '6', '', 0, 2, 0, 'sale:order:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003003', '2003003', 'SaleOutSheet', '销售出库管理', NULL, 0, '/sc/sale/out/index', NULL, '2003', '6', '/out', 0, 1, 0, 'sale:out:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003003001', '2003003001', '', '新增销售出库单', NULL, 0, '', NULL, '2003003', '6', '', 0, 2, 0, 'sale:out:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003003002', '2003003002', '', '修改销售出库单', NULL, 0, '', NULL, '2003003', '6', '', 0, 2, 0, 'sale:out:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003003003', '2003003003', '', '删除销售出库单', NULL, 0, '', NULL, '2003003', '6', '', 0, 2, 0, 'sale:out:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003003004', '2003003004', '', '审核销售出库单', NULL, 0, '', NULL, '2003003', '6', '', 0, 2, 0, 'sale:out:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003003005', '2003003005', '', '导出销售出库单', NULL, 0, '', NULL, '2003003', '6', '', 0, 2, 0, 'sale:out:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003004', '2003004', 'SaleReturn', '销售退货管理', NULL, 0, '/sc/sale/return/index', NULL, '2003', '6', '/return', 0, 1, 0, 'sale:return:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003004001', '2003004001', '', '新增销售退货单', NULL, 0, '', NULL, '2003004', '6', '', 0, 2, 0, 'sale:return:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003004002', '2003004002', '', '修改销售退货单', NULL, 0, '', NULL, '2003004', '6', '', 0, 2, 0, 'sale:return:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003004003', '2003004003', '', '删除销售退货单', NULL, 0, '', NULL, '2003004', '6', '', 0, 2, 0, 'sale:return:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003004004', '2003004004', '', '审核销售退货单', NULL, 0, '', NULL, '2003004', '6', '', 0, 2, 0, 'sale:return:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2003004005', '2003004005', '', '导出销售退货单', NULL, 0, '', NULL, '2003004', '6', '', 0, 2, 0, 'sale:return:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004', '2004', 'Retail', '零售管理', 'a-menu', NULL, '', NULL, '0001', '7', '/retail', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004001', '2004001', 'RetailConfig', '零售参数设置', NULL, 0, '/sc/retail/config/index', NULL, '2004', '7', '/config', 1, 1, 0, 'retail:config:modify', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004002', '2004002', 'RetailOutSheet', '零售出库管理', NULL, 0, '/sc/retail/out/index', NULL, '2004', '7', '/out', 0, 1, 0, 'retail:out:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004002001', '2004002001', '', '新增零售出库单', NULL, 0, '', NULL, '2004002', '7', '', 0, 2, 0, 'retail:out:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004002002', '2004002002', '', '修改零售出库单', NULL, 0, '', NULL, '2004002', '7', '', 0, 2, 0, 'retail:out:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004002003', '2004002003', '', '删除零售出库单', NULL, 0, '', NULL, '2004002', '7', '', 0, 2, 0, 'retail:out:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004002004', '2004002004', '', '审核零售出库单', NULL, 0, '', NULL, '2004002', '7', '', 0, 2, 0, 'retail:out:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004002005', '2004002005', '', '导出零售出库单', NULL, 0, '', NULL, '2004002', '7', '', 0, 2, 0, 'retail:out:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004003', '2004003', 'RetailReturn', '零售退货管理', NULL, 0, '/sc/retail/return/index', NULL, '2004', '7', '/return', 0, 1, 0, 'retail:return:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004003001', '2004003001', '', '新增零售退货单', NULL, 0, '', NULL, '2004003', '7', '', 0, 2, 0, 'retail:return:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004003002', '2004003002', '', '修改零售退货单', NULL, 0, '', NULL, '2004003', '7', '', 0, 2, 0, 'retail:return:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004003003', '2004003003', '', '删除零售退货单', NULL, 0, '', NULL, '2004003', '7', '', 0, 2, 0, 'retail:return:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004003004', '2004003004', '', '审核零售退货单', NULL, 0, '', NULL, '2004003', '7', '', 0, 2, 0, 'retail:return:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2004003005', '2004003005', '', '导出零售退货单', NULL, 0, '', NULL, '2004003', '7', '', 0, 2, 0, 'retail:return:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000', '3000', 'StockManage', '库存管理', 'a-menu', NULL, '', NULL, '0001', '8', '/stock', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000001', '3000001', 'ProductStock', '商品库存', NULL, 0, '/sc/stock/product/index', NULL, '3000', '8', '/product', 0, 1, 0, 'stock:product:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000001001', '3000001001', '', '导出商品库存', NULL, 0, '', NULL, '3000001', '8', '', 0, 2, 0, 'stock:product:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000003', '3000003', 'ProductStockLog', '商品库存变动记录', NULL, 0, '/sc/stock/product-log/index', NULL, '3000', '8', '/product/log', 0, 1, 0, 'stock:product-log:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000003001', '3000003001', '', '导出商品库存变动记录', NULL, 0, '', NULL, '3000003', '8', '', 0, 2, 0, 'stock:product-log:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004', '3000004', 'TakeStock', '库存盘点', 'a-menu', NULL, '', NULL, '0001', '9', '/take', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004001', '3000004001', 'TakeStockConfig', '盘点参数设置', NULL, 0, '/sc/stock/take/config/index', NULL, '3000004', '9', '/config', 1, 1, 0, 'stock:take:config:modify', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004002', '3000004002', 'PreTakeStockSheet', '预先盘点单管理', NULL, 0, '/sc/stock/take/pre/index', NULL, '3000004', '9', '/pre', 0, 1, 0, 'stock:take:pre:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004002001', '3000004002001', '', '新增预先盘点单', NULL, 0, '', NULL, '3000004002', '9', '', 0, 2, 0, 'stock:take:pre:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004002002', '3000004002002', '', '修改预先盘点单', NULL, 0, '', NULL, '3000004002', '9', '', 0, 2, 0, 'stock:take:pre:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004002003', '3000004002003', '', '删除预先盘点单', NULL, 0, '', NULL, '3000004002', '9', '', 0, 2, 0, 'stock:take:pre:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004002004', '3000004002004', '', '导出预先盘点单', NULL, 0, '', NULL, '3000004002', '9', '', 0, 2, 0, 'stock:take:pre:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003', '3000004003', 'TakeStockPlan', '盘点任务管理', NULL, 0, '/sc/stock/take/plan/index', NULL, '3000004', '9', '/plan', 0, 1, 0, 'stock:take:plan:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003001', '3000004003001', '', '新增盘点任务', NULL, 0, '', NULL, '3000004003', '9', '', 0, 2, 0, 'stock:take:plan:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003002', '3000004003002', '', '修改盘点任务', NULL, 0, '', NULL, '3000004003', '9', '', 0, 2, 0, 'stock:take:plan:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003003', '3000004003003', '', '删除盘点任务', NULL, 0, '', NULL, '3000004003', '9', '', 0, 2, 0, 'stock:take:plan:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003004', '3000004003004', '', '导出盘点任务', NULL, 0, '', NULL, '3000004003', '9', '', 0, 2, 0, 'stock:take:plan:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003005', '3000004003005', '', '差异生成', NULL, 0, '', NULL, '3000004003', '9', '', 0, 2, 0, 'stock:take:plan:create-diff', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003006', '3000004003006', '', '差异处理', NULL, 0, '', NULL, '3000004003', '9', '', 0, 2, 0, 'stock:take:plan:handle-diff', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004003007', '3000004003007', '', '作废盘点任务', NULL, 0, '', NULL, '3000004003', '9', '', 0, 2, 0, 'stock:take:plan:cancel', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004004', '3000004004', 'TakeStockSheet', '盘点单管理', NULL, 0, '/sc/stock/take/sheet/index', NULL, '3000004', '9', '/sheet', 0, 1, 0, 'stock:take:sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004004001', '3000004004001', '', '新增盘点单', NULL, 0, '', NULL, '3000004004', '9', '', 0, 2, 0, 'stock:take:sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004004002', '3000004004002', '', '修改盘点单', NULL, 0, '', NULL, '3000004004', '9', '', 0, 2, 0, 'stock:take:sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004004003', '3000004004003', '', '删除盘点单', NULL, 0, '', NULL, '3000004004', '9', '', 0, 2, 0, 'stock:take:sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004004004', '3000004004004', '', '导出盘点单', NULL, 0, '', NULL, '3000004004', '9', '', 0, 2, 0, 'stock:take:sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004004005', '3000004004005', '', '审核盘点单', NULL, 0, '', NULL, '3000004004', '9', '', 0, 2, 0, 'stock:take:sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000004004006', '3000004004006', '', '取消审核盘点单', NULL, 0, '', NULL, '3000004004', '9', '', 0, 2, 0, 'stock:take:sheet:cancel-approve', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005', '3000005', 'StockAdjust', '库存调整', 'a-menu', NULL, '', NULL, '0001', '10', '/take-adjust', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005001', '3000005001', 'StockCostAdjustSheet', '库存成本调整', NULL, 0, '/sc/stock/adjust/cost/index', NULL, '3000005', '10', '/cost', 0, 1, 0, 'stock:adjust:cost:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005001001', '3000005001001', '', '新增库存成本调整单', NULL, 0, '', NULL, '3000005001', '10', '', 0, 2, 0, 'stock:adjust:cost:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005001002', '3000005001002', '', '修改库存成本调整单', NULL, 0, '', NULL, '3000005001', '10', '', 0, 2, 0, 'stock:adjust:cost:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005001003', '3000005001003', '', '删除库存成本调整单', NULL, 0, '', NULL, '3000005001', '10', '', 0, 2, 0, 'stock:adjust:cost:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005001004', '3000005001004', '', '导出库存成本调整单', NULL, 0, '', NULL, '3000005001', '10', '', 0, 2, 0, 'stock:adjust:cost:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005001005', '3000005001005', '', '审核库存成本调整单', NULL, 0, '', NULL, '3000005001', '10', '', 0, 2, 0, 'stock:adjust:cost:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005002', '3000005002', 'StockAdjustReason', '库存调整原因', NULL, 0, '/sc/stock/adjust/stock/reason/index', NULL, '3000005', '10', '/stock/reason', 0, 1, 0, 'stock:adjust:reason:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005002001', '3000005002001', '', '新增库存成本调整单', NULL, 0, '', NULL, '3000005002', '10', '', 0, 2, 0, 'stock:adjust:reason:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005002002', '3000005002002', '', '修改库存成本调整单', NULL, 0, '', NULL, '3000005002', '10', '', 0, 2, 0, 'stock:adjust:reason:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005003', '3000005003', 'StockAdjustSheet', '库存数量调整', NULL, 0, '/sc/stock/adjust/stock/index', NULL, '3000005', '10', '/stock', 0, 1, 0, 'stock:adjust:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005003001', '3000005003001', '', '新增库存调整单', NULL, 0, '', NULL, '3000005003', '10', '', 0, 2, 0, 'stock:adjust:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005003002', '3000005003002', '', '修改库存调整单', NULL, 0, '', NULL, '3000005003', '10', '', 0, 2, 0, 'stock:adjust:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005003003', '3000005003003', '', '删除库存调整单', NULL, 0, '', NULL, '3000005003', '10', '', 0, 2, 0, 'stock:adjust:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005003004', '3000005003004', '', '导出库存调整单', NULL, 0, '', NULL, '3000005003', '10', '', 0, 2, 0, 'stock:adjust:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000005003005', '3000005003005', '', '审核库存调整单', NULL, 0, '', NULL, '3000005003', '10', '', 0, 2, 0, 'stock:adjust:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000006', '3000006', 'ScTransferOrder', '仓库调拨', NULL, 0, '/sc/stock/transfer/index', NULL, '3000', '8', '/transfer', 0, 1, 0, 'stock:sc-transfer:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000006001', '3000006001', '', '新增仓库调拨单', NULL, 0, '', NULL, '3000006', '8', '', 0, 2, 0, 'stock:sc-transfer:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000006002', '3000006002', '', '修改仓库调拨单', NULL, 0, '', NULL, '3000006', '8', '', 0, 2, 0, 'stock:sc-transfer:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000006003', '3000006003', '', '删除仓库调拨单', NULL, 0, '', NULL, '3000006', '8', '', 0, 2, 0, 'stock:sc-transfer:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000006004', '3000006004', '', '导出仓库调拨单', NULL, 0, '', NULL, '3000006', '8', '', 0, 2, 0, 'stock:sc-transfer:export', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000006005', '3000006005', '', '审核仓库调拨单', NULL, 0, '', NULL, '3000006', '8', '', 0, 2, 0, 'stock:sc-transfer:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000006006', '3000006006', '', '仓库调拨单收货', NULL, 0, '', NULL, '3000006', '8', '', 0, 2, 0, 'stock:sc-transfer:receive', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000', '4000', 'SettleManage', '结算管理', 'a-menu', NULL, '', NULL, '0001', '11', '/settle', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000001', '4000001', 'SettleInItem', '收入项目', NULL, 0, '/settle/in-item/index', NULL, '4000', '11', '/in-item', 0, 1, 0, 'settle:in-item:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000001001', '4000001001', '', '新增收入项目', NULL, 0, '', NULL, '4000001', '11', '', 0, 2, 0, 'settle:in-item:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000001002', '4000001002', '', '修改收入项目', NULL, 0, '', NULL, '4000001', '11', '', 0, 2, 0, 'settle:in-item:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000001003', '4000001003', '', '导出收入项目', NULL, 0, '', NULL, '4000001', '11', '', 0, 2, 0, 'settle:in-item:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000002', '4000002', 'SettleOutItem', '支出项目', NULL, 0, '/settle/out-item/index', NULL, '4000', '11', '/out-item', 0, 1, 0, 'settle:out-item:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000002001', '4000002001', '', '新增支出项目', NULL, 0, '', NULL, '4000002', '11', '', 0, 2, 0, 'settle:out-item:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000002002', '4000002002', '', '修改支出项目', NULL, 0, '', NULL, '4000002', '11', '', 0, 2, 0, 'settle:out-item:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000002003', '4000002003', '', '导出支出项目', NULL, 0, '', NULL, '4000002', '11', '', 0, 2, 0, 'settle:out-item:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000003', '4000003', 'SettleFeeSheet', '供应商费用', NULL, 0, '/settle/fee-sheet/index', NULL, '4000007', '11', '/fee-sheet', 0, 1, 0, 'settle:fee-sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000003001', '4000003001', '', '新增供应商费用单', NULL, 0, '', NULL, '4000003', '11', '', 0, 2, 0, 'settle:fee-sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000003002', '4000003002', '', '修改供应商费用单', NULL, 0, '', NULL, '4000003', '11', '', 0, 2, 0, 'settle:fee-sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000003003', '4000003003', '', '删除供应商费用单', NULL, 0, '', NULL, '4000003', '11', '', 0, 2, 0, 'settle:fee-sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000003004', '4000003004', '', '审核供应商费用单', NULL, 0, '', NULL, '4000003', '11', '', 0, 2, 0, 'settle:fee-sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000003005', '4000003005', '', '导出供应商费用单', NULL, 0, '', NULL, '4000003', '11', '', 0, 2, 0, 'settle:fee-sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000004', '4000004', 'SettlePreSheet', '供应商预付款', NULL, 0, '/settle/pre-sheet/index', NULL, '4000007', '11', '/pre-sheet', 0, 1, 0, 'settle:pre-sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000004001', '4000004001', '', '新增供应商预付款单', NULL, 0, '', NULL, '4000004', '11', '', 0, 2, 0, 'settle:pre-sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000004002', '4000004002', '', '修改供应商预付款单', NULL, 0, '', NULL, '4000004', '11', '', 0, 2, 0, 'settle:pre-sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000004003', '4000004003', '', '删除供应商预付款单', NULL, 0, '', NULL, '4000004', '11', '', 0, 2, 0, 'settle:pre-sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000004004', '4000004004', '', '审核供应商预付款单', NULL, 0, '', NULL, '4000004', '11', '', 0, 2, 0, 'settle:pre-sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000004005', '4000004005', '', '导出供应商预付款单', NULL, 0, '', NULL, '4000004', '11', '', 0, 2, 0, 'settle:pre-sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000005', '4000005', 'SettleCheckSheet', '供应商对账', NULL, 0, '/settle/check-sheet/index', NULL, '4000007', '11', '/check-sheet', 0, 1, 0, 'settle:check-sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000005001', '4000005001', '', '新增供应商对账单', NULL, 0, '', NULL, '4000005', '11', '', 0, 2, 0, 'settle:check-sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000005002', '4000005002', '', '修改供应商对账单', NULL, 0, '', NULL, '4000005', '11', '', 0, 2, 0, 'settle:check-sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000005003', '4000005003', '', '删除供应商对账单', NULL, 0, '', NULL, '4000005', '11', '', 0, 2, 0, 'settle:check-sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000005004', '4000005004', '', '审核供应商对账单', NULL, 0, '', NULL, '4000005', '11', '', 0, 2, 0, 'settle:check-sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000005005', '4000005005', '', '导出供应商对账单', NULL, 0, '', NULL, '4000005', '11', '', 0, 2, 0, 'settle:check-sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000006', '4000006', 'SettleSheet', '供应商结算', NULL, 0, '/settle/sheet/index', NULL, '4000007', '11', '/sheet', 0, 1, 0, 'settle:sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000006001', '4000006001', '', '新增供应商结算单', NULL, 0, '', NULL, '4000006', '11', '', 0, 2, 0, 'settle:sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000006002', '4000006002', '', '修改供应商结算单', NULL, 0, '', NULL, '4000006', '11', '', 0, 2, 0, 'settle:sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000006003', '4000006003', '', '删除供应商结算单', NULL, 0, '', NULL, '4000006', '11', '', 0, 2, 0, 'settle:sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000006004', '4000006004', '', '审核供应商结算单', NULL, 0, '', NULL, '4000006', '11', '', 0, 2, 0, 'settle:sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000006005', '4000006005', '', '导出供应商结算单', NULL, 0, '', NULL, '4000006', '11', '', 0, 2, 0, 'settle:sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000007', '4000007', 'SupplierSettleManage', '供应商结算', 'a-menu', NULL, '', NULL, '4000', '11', '/supplier', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000008', '4000008', 'CustomerSettleManage', '客户结算', 'a-menu', NULL, '', NULL, '4000', '11', '/customer', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-05 01:21:35', '系统管理员', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000009', '4000009', 'CustomerSettleFeeSheet', '客户费用', NULL, 0, '/customer-settle/fee-sheet/index', NULL, '4000008', '11', '/fee-sheet', 0, 1, 0, 'customer-settle:fee-sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000009001', '4000009001', '', '新增客户费用单', NULL, 0, '', NULL, '4000009', '11', '', 0, 2, 0, 'customer-settle:fee-sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000009002', '4000009002', '', '修改客户费用单', NULL, 0, '', NULL, '4000009', '11', '', 0, 2, 0, 'customer-settle:fee-sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000009003', '4000009003', '', '删除客户费用单', NULL, 0, '', NULL, '4000009', '11', '', 0, 2, 0, 'customer-settle:fee-sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000009004', '4000009004', '', '审核客户费用单', NULL, 0, '', NULL, '4000009', '11', '', 0, 2, 0, 'customer-settle:fee-sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000009005', '4000009005', '', '导出客户费用单', NULL, 0, '', NULL, '4000009', '11', '', 0, 2, 0, 'customer-settle:fee-sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000010', '4000010', 'CustomerSettlePreSheet', '客户预收款', NULL, 0, '/customer-settle/pre-sheet/index', NULL, '4000008', '11', '/pre-sheet', 0, 1, 0, 'customer-settle:pre-sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000010001', '4000010001', '', '新增客户预收款单', NULL, 0, '', NULL, '4000010', '11', '', 0, 2, 0, 'customer-settle:pre-sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000010002', '4000010002', '', '修改客户预收款单', NULL, 0, '', NULL, '4000010', '11', '', 0, 2, 0, 'customer-settle:pre-sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000010003', '4000010003', '', '删除客户预收款单', NULL, 0, '', NULL, '4000010', '11', '', 0, 2, 0, 'customer-settle:pre-sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000010004', '4000010004', '', '审核客户预收款单', NULL, 0, '', NULL, '4000010', '11', '', 0, 2, 0, 'customer-settle:pre-sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000010005', '4000010005', '', '导出客户预收款单', NULL, 0, '', NULL, '4000010', '11', '', 0, 2, 0, 'customer-settle:pre-sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000011', '4000011', 'CustomerSettleCheckSheet', '客户对账', NULL, 0, '/customer-settle/check-sheet/index', NULL, '4000008', '11', '/check-sheet', 0, 1, 0, 'customer-settle:check-sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000011001', '4000011001', '', '新增客户对账单', NULL, 0, '', NULL, '4000011', '11', '', 0, 2, 0, 'customer-settle:check-sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000011002', '4000011002', '', '修改客户对账单', NULL, 0, '', NULL, '4000011', '11', '', 0, 2, 0, 'customer-settle:check-sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000011003', '4000011003', '', '删除客户对账单', NULL, 0, '', NULL, '4000011', '11', '', 0, 2, 0, 'customer-settle:check-sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000011004', '4000011004', '', '审核客户对账单', NULL, 0, '', NULL, '4000011', '11', '', 0, 2, 0, 'customer-settle:check-sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000011005', '4000011005', '', '导出客户对账单', NULL, 0, '', NULL, '4000011', '11', '', 0, 2, 0, 'customer-settle:check-sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000012', '4000012', 'CustomerSettleSheet', '客户结算', NULL, 0, '/customer-settle/sheet/index', NULL, '4000008', '11', '/sheet', 0, 1, 0, 'customer-settle:sheet:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000012001', '4000012001', '', '新增客户结算单', NULL, 0, '', NULL, '4000012', '11', '', 0, 2, 0, 'customer-settle:sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000012002', '4000012002', '', '修改客户结算单', NULL, 0, '', NULL, '4000012', '11', '', 0, 2, 0, 'customer-settle:sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000012003', '4000012003', '', '删除客户结算单', NULL, 0, '', NULL, '4000012', '11', '', 0, 2, 0, 'customer-settle:sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000012004', '4000012004', '', '审核客户结算单', NULL, 0, '', NULL, '4000012', '11', '', 0, 2, 0, 'customer-settle:sheet:approve', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('4000012005', '4000012005', '', '导出客户结算单', NULL, 0, '', NULL, '4000012', '11', '', 0, 2, 0, 'customer-settle:sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000', '5000', 'Logistics', '物流管理', 'a-menu', NULL, '', NULL, '0001', '15', '/logistics', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001', '5000001', 'LogisticsSheet', '物流单管理', NULL, 0, '/sc/logistics/sheet/index', NULL, '5000', '15', '/sheet', 0, 1, 0, 'logistics:sheet:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001001', '5000001001', '', '新增物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001002', '5000001002', '', '修改物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001003', '5000001003', '', '删除物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001004', '5000001004', '', '物流单发货', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:delivery', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001005', '5000001005', '', '导入物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001006', '5000001006', '', '导出物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000', '9000', 'Development', '开发管理', 'a-menu', NULL, '', NULL, '0001', '12', '/development', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000001', '9000001', 'Qrtz', '定时器管理', NULL, 0, '/development/qrtz/index', NULL, '9000', '12', '/qrtz', 0, 1, 0, 'development:qrtz:manage', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000002', '9000002', 'DataEntity', '数据实体', NULL, 0, '/development/data/entity/index', NULL, '9000', '12', '/data/entity', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000003', '9000003', 'DataObj', '数据对象', NULL, 0, '/development/data/obj/index', NULL, '9000', '12', '/data/obj', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000004', '9000004', 'CustomList', '自定义列表', NULL, 0, '/development/custom/list/index', NULL, '9000', '12', '/custom/list', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000005', '9000005', 'CustomSelector', '自定义选择器', NULL, 0, '/development/custom/selector/index', NULL, '9000', '12', '/custom/selector', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000006', '9000006', 'FmDesigner', '表单生成器', NULL, 0, '/development/fm-designer/index', NULL, '9000', '12', '/fm-designer', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000007', '9000007', 'CustomForm', '自定义表单', NULL, 0, '/development/custom/form/index', NULL, '9000', '12', '/custom/form', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000008', '9000008', 'CustomPage', '自定义页面', NULL, 0, '/development/custom/page/index', NULL, '9000', '12', '/custom/page', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9001', '9001', 'SmartWork', '便捷办公', 'a-menu', NULL, '', NULL, '0001', '13', '/smart-work', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9001001', '9001001', 'FileBox', '文件收纳箱', NULL, 0, '/smart-work/file-box/index', NULL, '9001', '13', '/file-box', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9001002', '9001002', 'OnlineExcel', '在线Excel', NULL, 0, '/smart-work/online-excel/index', NULL, '9001', '13', '/online-excel', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
COMMIT;

-- ----------------------------
-- Table structure for sys_menu_collect
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_collect`;
CREATE TABLE `sys_menu_collect` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id, menu_id` (`user_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='菜单收藏';

-- ----------------------------
-- Records of sys_menu_collect
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT 'ID',
  `title` varchar(200) CHARACTER SET utf8mb4 NOT NULL COMMENT '标题',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `published` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否发布',
  `create_by` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `readed_num` int(11) NOT NULL DEFAULT '0' COMMENT '已读人数',
  `un_read_num` int(11) NOT NULL DEFAULT '0' COMMENT '未读人数',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='系统通知';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_notice_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_log`;
CREATE TABLE `sys_notice_log` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `notice_id` varchar(32) NOT NULL COMMENT '标题',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `readed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `read_time` datetime DEFAULT NULL COMMENT '已读时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `notice_id` (`notice_id`,`user_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统通知记录';

-- ----------------------------
-- Records of sys_notice_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pm_key` varchar(100) NOT NULL COMMENT '键',
  `pm_value` longtext COMMENT '值',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `pm_key` (`pm_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统参数';

-- ----------------------------
-- Records of sys_parameter
-- ----------------------------
BEGIN;
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (1, 'tx-map.key', 'OLJBZ-ZFJK6-QWUSK-MB7XT-6UTN2-AWBSY', '腾讯地图Key', '系统管理员', '1', '2022-05-22 04:18:59', '系统管理员', '1', '2022-05-22 04:18:59');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (2, 'tx-map.secret', 'secret', '腾讯地图Secret', '系统管理员', '1', '2022-05-22 04:18:59', '系统管理员', '1', '2022-05-22 04:18:59');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (3, 'excel-import.max-size', '2000', 'Excel导入最大条数', '系统管理员', '1', '2022-06-10 21:39:32', '系统管理员', '1', '2022-06-10 21:39:32');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (8, 'mail', '{\n    \"pass\":\"123456\",\n    \"sslEnable\":true,\n    \"timeOut\":30000,\n    \"port\":25,\n    \"host\":\"smtp.xingyun.com\",\n    \"connectTimeOut\":1000,\n    \"from\":\"test@xingyun.com\",\n    \"user\":\"test\"\n}', '邮件配置', '系统管理员', '1', '2023-03-14 21:13:44', '系统管理员', '1', '2023-03-14 21:13:44');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (9, 'upload.type', 'LOCAL', '上传类型，分为LOCAL、OSS、COS、OBS。LOCAL：服务器本地存储。OSS：阿里云对象存储。COS：腾讯云对象存储。OBS：华为云对象存储', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2023-06-27 10:38:10');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (10, 'upload.oss.config', '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"internalEndPoint\":\"\",\"accessKeyId\":\"yourAccessKeyId\",\"accessKeySecret\":\"yourAccessKeySecret\",\"bucketName\":\"yourBucketName\"}', '阿里云对象存储配置信息，upload.type=OSS时生效，注意：当服务器与OSS同一地域时，建议填写internalEndPoint，此值表示内网endpoint，在上传时会优先使用内网endpoint。customUrl为自定义域名（需带协议）为空代表不使用自定义域名，示例值：https://www.lframework.com。其他参数均在阿里云控台获取。', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2023-06-27 10:38:10');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (11, 'upload.obs.config', '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"ak\":\"yourAccessKeyId\",\"sk\":\"yourAccessKeySecret\",\"bucketName\":\"yourBucketName\"}', '华为云对象存储配置信息，upload.type=OBS时生效。customUrl为自定义域名（需带协议）为空代表不使用自定义域名，示例值：https://www.lframework.com。其他参数均在华为云控台获取。', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2023-06-27 10:38:10');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (12, 'upload.cos.config', '{\"customUrl\":\"yourCustomUrl\",\"region\":\"yourRegion\",\"secretId\":\"yourSecretId\",\"secretKey\":\"yourSecretKey\",\"bucketName\":\"yourBucketName\"}', '腾讯云对象存储配置信息，upload.type=COS时生效。customUrl为下载文件时的域名，如果使用自定义域名，示例值：https://www.lframework.com，如果不使用自定义域名，那么就填写COS的访问域名。其他参数均在腾讯云控台获取。', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2023-06-27 14:48:02');
COMMIT;

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '岗位编号',
  `name` varchar(20) NOT NULL COMMENT '岗位名称',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='岗位';

-- ----------------------------
-- Records of sys_position
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `code`, `name`, `permission`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1', '001', '系统管理员', 'admin', 1, '系统管理员', '系统管理员', '1', '2021-05-08 18:04:41', '系统管理员', '1', '2021-05-08 18:04:45');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_id, menu_id` (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色与菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `telephone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `gender` tinyint(3) NOT NULL DEFAULT '0' COMMENT '性别 0-未知 1-男 2-女',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1-在用 0停用',
  `lock_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '锁定状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `code`, `name`, `username`, `password`, `email`, `telephone`, `gender`, `available`, `lock_status`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1', '001', '系统管理员', 'admin', '$2a$10$IJtHluhnhAYkgvM4PdKuZek5PWbtuxtjB9pB.twZdxg/qrlR4s4q6', 'xingyun@lframework.com', '17600000001', 0, 1, 0, '', '系统管理员', '1', '2021-04-22 22:00:27', '系统管理员', '1', '2023-03-09 13:30:44');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `dept_id` varchar(32) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id` (`user_id`,`dept_id`) USING BTREE,
  KEY `dept_id` (`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户与部门关系表';

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_position`;
CREATE TABLE `sys_user_position` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `position_id` varchar(32) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id` (`user_id`,`position_id`) USING BTREE,
  KEY `position_id` (`position_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关系表';

-- ----------------------------
-- Records of sys_user_position
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_id, role_id` (`user_id`,`role_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户与角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_telephone
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_telephone`;
CREATE TABLE `sys_user_telephone` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `telephone` varchar(11) NOT NULL COMMENT '手机号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `telephone` (`telephone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户绑定手机号表';

-- ----------------------------
-- Records of sys_user_telephone
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_logistics_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_logistics_sheet`;
CREATE TABLE `tbl_logistics_sheet` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '业务单据号',
  `logistics_no` varchar(100) DEFAULT NULL COMMENT '物流单号',
  `logistics_company_id` varchar(32) NOT NULL COMMENT '物流公司ID',
  `sender_name` varchar(100) NOT NULL COMMENT '寄件人姓名',
  `sender_telephone` varchar(20) NOT NULL COMMENT '寄件人联系电话',
  `sender_province_id` varchar(32) NOT NULL COMMENT '寄件人省',
  `sender_city_id` varchar(32) NOT NULL COMMENT '寄件人市',
  `sender_district_id` varchar(32) NOT NULL COMMENT '寄件人区',
  `sender_address` varchar(200) NOT NULL COMMENT '寄件人地址',
  `receiver_name` varchar(100) NOT NULL COMMENT '收件人姓名',
  `receiver_telephone` varchar(20) NOT NULL COMMENT '收件人联系电话',
  `receiver_province_id` varchar(32) NOT NULL COMMENT '收件人省',
  `receiver_city_id` varchar(32) NOT NULL COMMENT '收件人市',
  `receiver_district_id` varchar(32) NOT NULL COMMENT '收件人区',
  `receiver_address` varchar(200) NOT NULL COMMENT '收件人地址',
  `total_weight` decimal(16,2) DEFAULT NULL COMMENT '总重量（kg）',
  `total_volume` decimal(16,2) DEFAULT NULL COMMENT '总体积（cm3）',
  `total_amount` decimal(16,2) DEFAULT NULL COMMENT '物流费',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `delivery_by` varchar(32) DEFAULT NULL COMMENT '发货人',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `logistics_no` (`logistics_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流单';

-- ----------------------------
-- Records of tbl_logistics_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_logistics_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_logistics_sheet_detail`;
CREATE TABLE `tbl_logistics_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '物流单ID',
  `biz_id` varchar(32) NOT NULL COMMENT '业务单据ID',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY ```biz_id``` (`biz_id`,`biz_type`) USING BTREE,
  KEY `sheet_id` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流单明细';

-- ----------------------------
-- Records of tbl_logistics_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_order_chart
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order_chart`;
CREATE TABLE `tbl_order_chart` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `total_amount` decimal(24,2) NOT NULL COMMENT '单据总金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_date` varchar(19) NOT NULL COMMENT '创建日期',
  `create_hour` varchar(13) NOT NULL COMMENT '创建时间（小时）',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `create_date` (`create_date`) USING BTREE,
  KEY `create_hour` (`create_hour`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单图表数据';

-- ----------------------------
-- Records of tbl_order_chart
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_order_pay_type
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order_pay_type`;
CREATE TABLE `tbl_order_pay_type` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `pay_type_id` varchar(32) NOT NULL COMMENT '支付方式ID',
  `pay_amount` decimal(24,2) NOT NULL COMMENT '支付金额',
  `text` varchar(200) DEFAULT NULL COMMENT '支付内容',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单支付方式';

-- ----------------------------
-- Records of tbl_order_pay_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_order_time_line
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order_time_line`;
CREATE TABLE `tbl_order_time_line` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '单据ID',
  `content` longtext COMMENT '描述内容',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='单据时间轴';

-- ----------------------------
-- Records of tbl_order_time_line
-- ----------------------------
BEGIN;
COMMIT;

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
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存预先盘点单';

-- ----------------------------
-- Records of tbl_pre_take_stock_sheet
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存预先盘点单明细';

-- ----------------------------
-- Records of tbl_pre_take_stock_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_product_stock
-- ----------------------------
DROP TABLE IF EXISTS `tbl_product_stock`;
CREATE TABLE `tbl_product_stock` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `stock_num` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `tax_price` decimal(24,6) NOT NULL COMMENT '含税价格',
  `tax_amount` decimal(24,2) NOT NULL COMMENT '含税金额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `product_id` (`product_id`,`sc_id`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品库存';

-- ----------------------------
-- Records of tbl_product_stock
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_product_stock_log
-- ----------------------------
DROP TABLE IF EXISTS `tbl_product_stock_log`;
CREATE TABLE `tbl_product_stock_log` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `ori_tax_price` decimal(24,6) NOT NULL COMMENT '原含税成本价',
  `cur_tax_price` decimal(24,6) NOT NULL COMMENT '现含税成本价',
  `ori_stock_num` int(11) NOT NULL,
  `cur_stock_num` int(11) NOT NULL,
  `stock_num` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `tax_amount` decimal(24,2) NOT NULL COMMENT '含税金额',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `biz_id` varchar(32) DEFAULT NULL COMMENT '业务单据ID',
  `biz_code` varchar(32) DEFAULT NULL COMMENT '业务单据号',
  `biz_detail_id` varchar(32) DEFAULT NULL COMMENT '业务单据明细ID',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='商品库存变动记录';

-- ----------------------------
-- Records of tbl_product_stock_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_purchase_config
-- ----------------------------
DROP TABLE IF EXISTS `tbl_purchase_config`;
CREATE TABLE `tbl_purchase_config` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `receive_require_purchase` tinyint(1) NOT NULL DEFAULT '0' COMMENT '采购收货单是否关联采购订单',
  `receive_multiple_relate_purchase` tinyint(1) NOT NULL DEFAULT '0' COMMENT '采购收货单是否多次关联采购订单',
  `purchase_return_require_receive` tinyint(1) NOT NULL DEFAULT '0' COMMENT '采购退货单是否关联采购收货单',
  `purchase_return_multiple_relate_receive` tinyint(1) NOT NULL DEFAULT '0' COMMENT '采购退货单是否多次关联采购收货单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购参数设置';

-- ----------------------------
-- Records of tbl_purchase_config
-- ----------------------------
BEGIN;
INSERT INTO `tbl_purchase_config` (`id`, `receive_require_purchase`, `receive_multiple_relate_purchase`, `purchase_return_require_receive`, `purchase_return_multiple_relate_receive`) VALUES ('1', 1, 1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for tbl_purchase_order
-- ----------------------------
DROP TABLE IF EXISTS `tbl_purchase_order`;
CREATE TABLE `tbl_purchase_order` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `purchaser_id` varchar(32) DEFAULT NULL COMMENT '采购员ID',
  `expect_arrive_date` date DEFAULT NULL COMMENT '预计到货日期',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '采购数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '采购金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购订单';

-- ----------------------------
-- Records of tbl_purchase_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_purchase_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_purchase_order_detail`;
CREATE TABLE `tbl_purchase_order_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `order_num` int(11) NOT NULL COMMENT '采购数量',
  `tax_price` decimal(16,2) NOT NULL COMMENT '采购价',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `receive_num` int(11) NOT NULL DEFAULT '0' COMMENT '已收货数量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购订单明细';

-- ----------------------------
-- Records of tbl_purchase_order_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_purchase_return
-- ----------------------------
DROP TABLE IF EXISTS `tbl_purchase_return`;
CREATE TABLE `tbl_purchase_return` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `purchaser_id` varchar(32) DEFAULT NULL COMMENT '采购员ID',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `receive_sheet_id` varchar(32) DEFAULT NULL COMMENT '收货单ID',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '退货金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `tx_id` varchar(100) DEFAULT NULL COMMENT '事务ID',
  `ori_settle_status` tinyint(3) DEFAULT NULL COMMENT '结算状态中间态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE,
  KEY `receive_sheet_id` (`receive_sheet_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购退单';

-- ----------------------------
-- Records of tbl_purchase_return
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_purchase_return_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_purchase_return_detail`;
CREATE TABLE `tbl_purchase_return_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `return_id` varchar(32) NOT NULL COMMENT '收货单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `return_num` int(11) NOT NULL COMMENT '退货数量',
  `tax_price` decimal(16,2) NOT NULL COMMENT '采购价',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `receive_sheet_detail_id` varchar(32) DEFAULT NULL COMMENT '收货单明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `return_id` (`return_id`) USING BTREE,
  KEY `receive_sheet_detail_id` (`receive_sheet_detail_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购退单明细';

-- ----------------------------
-- Records of tbl_purchase_return_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_receive_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_receive_sheet`;
CREATE TABLE `tbl_receive_sheet` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
  `purchaser_id` varchar(32) DEFAULT NULL COMMENT '采购员ID',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `receive_date` date DEFAULT NULL COMMENT '到货日期',
  `purchase_order_id` varchar(32) DEFAULT NULL COMMENT '采购单ID',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '收货金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `tx_id` varchar(100) DEFAULT NULL COMMENT '事务ID',
  `ori_settle_status` tinyint(3) DEFAULT NULL COMMENT '结算状态中间态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `supplier_id` (`supplier_id`) USING BTREE,
  KEY `purchase_order_id` (`purchase_order_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购收货单';

-- ----------------------------
-- Records of tbl_receive_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_receive_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_receive_sheet_detail`;
CREATE TABLE `tbl_receive_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '收货单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `order_num` int(11) NOT NULL COMMENT '采购数量',
  `tax_price` decimal(16,2) NOT NULL COMMENT '采购价',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `purchase_order_detail_id` varchar(32) DEFAULT NULL COMMENT '采购订单明细ID',
  `return_num` int(11) NOT NULL DEFAULT '0' COMMENT '已退货数量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sheet_id` (`sheet_id`) USING BTREE,
  KEY `purchase_order_detail_id` (`purchase_order_detail_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购收货单明细';

-- ----------------------------
-- Records of tbl_receive_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_retail_config
-- ----------------------------
DROP TABLE IF EXISTS `tbl_retail_config`;
CREATE TABLE `tbl_retail_config` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `retail_out_sheet_require_member` tinyint(1) NOT NULL DEFAULT '1' COMMENT '零售出库单上的会员是否必填',
  `retail_return_require_out_stock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '零售退货单是否关联零售出库单',
  `retail_return_multiple_relate_out_stock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '零售退货单是否多次关联零售出库单',
  `retail_return_require_member` tinyint(1) NOT NULL DEFAULT '1' COMMENT '零售退货单上的会员是否必填',
  `retail_out_sheet_require_logistics` tinyint(1) NOT NULL DEFAULT '0' COMMENT '零售出库单是否需要发货',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='零售参数设置';

-- ----------------------------
-- Records of tbl_retail_config
-- ----------------------------
BEGIN;
INSERT INTO `tbl_retail_config` (`id`, `retail_out_sheet_require_member`, `retail_return_require_out_stock`, `retail_return_multiple_relate_out_stock`, `retail_return_require_member`, `retail_out_sheet_require_logistics`) VALUES ('1', 0, 0, 1, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_retail_out_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_retail_out_sheet`;
CREATE TABLE `tbl_retail_out_sheet` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `member_id` varchar(32) DEFAULT NULL COMMENT '会员ID',
  `saler_id` varchar(32) DEFAULT NULL COMMENT '销售员ID',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '出库金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `member_id` (`member_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='零售出库单';

-- ----------------------------
-- Records of tbl_retail_out_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_retail_out_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_retail_out_sheet_detail`;
CREATE TABLE `tbl_retail_out_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '出库单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `order_num` int(11) NOT NULL COMMENT '出库数量',
  `ori_price` decimal(16,2) NOT NULL COMMENT '原价',
  `tax_price` decimal(16,2) NOT NULL COMMENT '现价',
  `discount_rate` decimal(16,2) NOT NULL COMMENT '折扣率（%）',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `return_num` int(11) NOT NULL DEFAULT '0' COMMENT '已退货数量',
  `ori_bundle_detail_id` varchar(32) DEFAULT NULL COMMENT '组合商品原始明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sheet_id` (`sheet_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='零售出库单明细';

-- ----------------------------
-- Records of tbl_retail_out_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_retail_out_sheet_detail_bundle
-- ----------------------------
DROP TABLE IF EXISTS `tbl_retail_out_sheet_detail_bundle`;
CREATE TABLE `tbl_retail_out_sheet_detail_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '出库单ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '组合商品数量',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `product_order_num` int(11) NOT NULL COMMENT '单品数量',
  `product_ori_price` decimal(16,2) NOT NULL COMMENT '单品原价',
  `product_tax_price` decimal(16,2) NOT NULL COMMENT '单品含税价格',
  `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
  `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_detail_id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='零售出库单组合商品明细';

-- ----------------------------
-- Records of tbl_retail_out_sheet_detail_bundle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_retail_out_sheet_detail_lot
-- ----------------------------
DROP TABLE IF EXISTS `tbl_retail_out_sheet_detail_lot`;
CREATE TABLE `tbl_retail_out_sheet_detail_lot` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `order_num` int(11) NOT NULL COMMENT '出库数量',
  `return_num` int(11) NOT NULL DEFAULT '0' COMMENT '已退货数量',
  `cost_tax_amount` decimal(24,2) NOT NULL COMMENT '含税成本金额',
  `settle_status` tinyint(3) NOT NULL COMMENT '结算状态',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='零售出库单批次明细';

-- ----------------------------
-- Records of tbl_retail_out_sheet_detail_lot
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_retail_return
-- ----------------------------
DROP TABLE IF EXISTS `tbl_retail_return`;
CREATE TABLE `tbl_retail_return` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `member_id` varchar(32) DEFAULT NULL COMMENT '会员ID',
  `saler_id` varchar(32) DEFAULT NULL COMMENT '销售员ID',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `out_sheet_id` varchar(32) DEFAULT NULL COMMENT '出库单ID',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '退货金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `out_sheet_id` (`out_sheet_id`) USING BTREE,
  KEY `member_id` (`member_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='零售退单';

-- ----------------------------
-- Records of tbl_retail_return
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_retail_return_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_retail_return_detail`;
CREATE TABLE `tbl_retail_return_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `return_id` varchar(32) NOT NULL COMMENT '退货单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `return_num` int(11) NOT NULL COMMENT '退货数量',
  `ori_price` decimal(16,2) NOT NULL COMMENT '原价',
  `tax_price` decimal(16,2) NOT NULL COMMENT '现价',
  `discount_rate` decimal(16,2) NOT NULL COMMENT '折扣率（%）',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `out_sheet_detail_id` varchar(32) DEFAULT NULL COMMENT '出库单明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `return_id` (`return_id`) USING BTREE,
  KEY `out_sheet_detail_id` (`out_sheet_detail_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='零售退单明细';

-- ----------------------------
-- Records of tbl_retail_return_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_config
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_config`;
CREATE TABLE `tbl_sale_config` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `out_stock_require_sale` tinyint(1) NOT NULL DEFAULT '0' COMMENT '销售出库单是否关联销售订单',
  `out_stock_multiple_relate_sale` tinyint(1) NOT NULL DEFAULT '0' COMMENT '销售出库单是否多次关联销售订单',
  `sale_return_require_out_stock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '销售退货单是否关联销售出库单',
  `sale_return_multiple_relate_out_stock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '销售退货单是否多次关联销售出库单',
  `out_stock_require_logistics` tinyint(1) NOT NULL DEFAULT '0' COMMENT '销售出库单是否需要物流单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售参数设置';

-- ----------------------------
-- Records of tbl_sale_config
-- ----------------------------
BEGIN;
INSERT INTO `tbl_sale_config` (`id`, `out_stock_require_sale`, `out_stock_multiple_relate_sale`, `sale_return_require_out_stock`, `sale_return_multiple_relate_out_stock`, `out_stock_require_logistics`) VALUES ('1', 0, 0, 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_order
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_order`;
CREATE TABLE `tbl_sale_order` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `saler_id` varchar(32) DEFAULT NULL COMMENT '销售员ID',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '销售数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '销售金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售订单';

-- ----------------------------
-- Records of tbl_sale_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_order_detail`;
CREATE TABLE `tbl_sale_order_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '订单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `order_num` int(11) NOT NULL COMMENT '销售数量',
  `ori_price` decimal(16,2) NOT NULL COMMENT '原价',
  `tax_price` decimal(16,2) NOT NULL COMMENT '现价',
  `discount_rate` decimal(16,2) NOT NULL COMMENT '折扣率（%）',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `out_num` int(11) NOT NULL DEFAULT '0' COMMENT '已出库数量',
  `ori_bundle_detail_id` varchar(32) DEFAULT NULL COMMENT '组合商品原始明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售订单明细';

-- ----------------------------
-- Records of tbl_sale_order_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_order_detail_bundle
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_order_detail_bundle`;
CREATE TABLE `tbl_sale_order_detail_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '销售单ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '组合商品数量',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `product_order_num` int(11) NOT NULL COMMENT '单品数量',
  `product_ori_price` decimal(16,2) NOT NULL COMMENT '单品原价',
  `product_tax_price` decimal(16,2) NOT NULL COMMENT '单品含税价格',
  `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
  `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `order_id` (`order_id`,`product_detail_id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售单组合商品明细';

-- ----------------------------
-- Records of tbl_sale_order_detail_bundle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_out_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_out_sheet`;
CREATE TABLE `tbl_sale_out_sheet` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `saler_id` varchar(32) DEFAULT NULL COMMENT '销售员ID',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `sale_order_id` varchar(32) DEFAULT NULL COMMENT '销售单ID',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '出库金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `tx_id` varchar(100) DEFAULT NULL COMMENT '事务ID',
  `ori_settle_status` tinyint(3) DEFAULT NULL COMMENT '结算状态中间态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `sale_order_id` (`sale_order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售出库单';

-- ----------------------------
-- Records of tbl_sale_out_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_out_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_out_sheet_detail`;
CREATE TABLE `tbl_sale_out_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '出库单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `order_num` int(11) NOT NULL COMMENT '出库数量',
  `ori_price` decimal(16,2) NOT NULL COMMENT '原价',
  `tax_price` decimal(16,2) NOT NULL COMMENT '现价',
  `discount_rate` decimal(16,2) NOT NULL COMMENT '折扣率（%）',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `sale_order_detail_id` varchar(32) DEFAULT NULL COMMENT '销售订单明细ID',
  `ori_bundle_detail_id` varchar(32) DEFAULT NULL COMMENT '组合商品原始明细ID',
  `return_num` int(11) NOT NULL DEFAULT '0' COMMENT '已退货数量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sheet_id` (`sheet_id`) USING BTREE,
  KEY `purchase_order_detail_id` (`sale_order_detail_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售出库单明细';

-- ----------------------------
-- Records of tbl_sale_out_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_out_sheet_detail_bundle
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_out_sheet_detail_bundle`;
CREATE TABLE `tbl_sale_out_sheet_detail_bundle` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '出库单ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `main_product_id` varchar(32) NOT NULL COMMENT '组合商品ID',
  `order_num` int(11) NOT NULL DEFAULT '0' COMMENT '组合商品数量',
  `product_id` varchar(32) NOT NULL COMMENT '单品ID',
  `product_order_num` int(11) NOT NULL COMMENT '单品数量',
  `product_ori_price` decimal(16,2) NOT NULL COMMENT '单品原价',
  `product_tax_price` decimal(16,2) NOT NULL COMMENT '单品含税价格',
  `product_tax_rate` varchar(16) NOT NULL COMMENT '单品税率',
  `product_detail_id` varchar(32) DEFAULT NULL COMMENT '单品明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_detail_id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售出库单组合商品明细';

-- ----------------------------
-- Records of tbl_sale_out_sheet_detail_bundle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_out_sheet_detail_lot
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_out_sheet_detail_lot`;
CREATE TABLE `tbl_sale_out_sheet_detail_lot` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `order_num` int(11) NOT NULL COMMENT '出库数量',
  `return_num` int(11) NOT NULL DEFAULT '0' COMMENT '已退货数量',
  `cost_tax_amount` decimal(24,2) NOT NULL COMMENT '含税成本金额',
  `settle_status` tinyint(3) NOT NULL COMMENT '结算状态',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `detail_id` (`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售出库单批次明细';

-- ----------------------------
-- Records of tbl_sale_out_sheet_detail_lot
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_return
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_return`;
CREATE TABLE `tbl_sale_return` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `customer_id` varchar(32) NOT NULL COMMENT '客户ID',
  `saler_id` varchar(32) DEFAULT NULL COMMENT '销售员ID',
  `payment_date` date DEFAULT NULL COMMENT '付款日期',
  `out_sheet_id` varchar(32) DEFAULT NULL COMMENT '出库单ID',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '退货金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `tx_id` varchar(100) DEFAULT NULL COMMENT '事务ID',
  `ori_settle_status` tinyint(3) DEFAULT NULL COMMENT '结算状态中间态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE,
  KEY `customer_id` (`customer_id`) USING BTREE,
  KEY `out_sheet_id` (`out_sheet_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售退单';

-- ----------------------------
-- Records of tbl_sale_return
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sale_return_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sale_return_detail`;
CREATE TABLE `tbl_sale_return_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `return_id` varchar(32) NOT NULL COMMENT '退货单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `return_num` int(11) NOT NULL COMMENT '退货数量',
  `ori_price` decimal(16,2) NOT NULL COMMENT '原价',
  `tax_price` decimal(16,2) NOT NULL COMMENT '现价',
  `discount_rate` decimal(16,2) NOT NULL COMMENT '折扣率（%）',
  `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
  `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
  `out_sheet_detail_id` varchar(32) DEFAULT NULL COMMENT '出库单明细ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `return_id` (`return_id`) USING BTREE,
  KEY `out_sheet_detail_id` (`out_sheet_detail_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='销售退单明细';

-- ----------------------------
-- Records of tbl_sale_return_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sc_transfer_order
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sc_transfer_order`;
CREATE TABLE `tbl_sc_transfer_order` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '单号',
  `source_sc_id` varchar(32) NOT NULL COMMENT '转出仓库ID',
  `target_sc_id` varchar(32) NOT NULL COMMENT '转入仓库ID',
  `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '调拨数量',
  `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '调拨成本金额',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `source_sc_id` (`source_sc_id`) USING BTREE,
  KEY `target_sc_id` (`target_sc_id`) USING BTREE,
  KEY `create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='仓库调拨单';

-- ----------------------------
-- Records of tbl_sc_transfer_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sc_transfer_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sc_transfer_order_detail`;
CREATE TABLE `tbl_sc_transfer_order_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '调拨单ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `transfer_num` int(11) NOT NULL COMMENT '调拨数量',
  `tax_price` decimal(16,2) DEFAULT NULL COMMENT '成本价',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `receive_num` int(11) NOT NULL DEFAULT '0' COMMENT '已收货数量',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='仓库调拨单明细';

-- ----------------------------
-- Records of tbl_sc_transfer_order_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sc_transfer_order_detail_receive
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sc_transfer_order_detail_receive`;
CREATE TABLE `tbl_sc_transfer_order_detail_receive` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `order_id` varchar(32) NOT NULL COMMENT '调拨单ID',
  `detail_id` varchar(32) NOT NULL COMMENT '明细ID',
  `receive_num` int(11) NOT NULL COMMENT '收货数量',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `order_id` (`order_id`,`detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='仓库调拨单收货明细';

-- ----------------------------
-- Records of tbl_sc_transfer_order_detail_receive
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_shop
-- ----------------------------
DROP TABLE IF EXISTS `tbl_shop`;
CREATE TABLE `tbl_shop` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '所属部门ID',
  `lng` decimal(16,6) DEFAULT NULL COMMENT '经度',
  `lat` decimal(16,6) DEFAULT NULL COMMENT '纬度',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1-在用 0停用',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='门店';

-- ----------------------------
-- Records of tbl_shop
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_stock_adjust_reason
-- ----------------------------
DROP TABLE IF EXISTS `tbl_stock_adjust_reason`;
CREATE TABLE `tbl_stock_adjust_reason` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存调整原因';

-- ----------------------------
-- Records of tbl_stock_adjust_reason
-- ----------------------------
BEGIN;
INSERT INTO `tbl_stock_adjust_reason` (`id`, `code`, `name`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1', '001', '初始化数据', 1, '系统内置', '系统管理员', '1', '2023-04-18 14:04:34', '系统管理员', '1', '2023-04-18 14:04:53');
INSERT INTO `tbl_stock_adjust_reason` (`id`, `code`, `name`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2', '002', '损溢', 1, '系统内置', '系统管理员', '1', '2023-04-18 14:04:34', '系统管理员', '1', '2023-04-18 14:04:53');
INSERT INTO `tbl_stock_adjust_reason` (`id`, `code`, `name`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3', '003', '其他', 1, '系统内置', '系统管理员', '1', '2023-04-18 14:04:34', '系统管理员', '1', '2023-04-18 14:04:53');
COMMIT;

-- ----------------------------
-- Table structure for tbl_stock_adjust_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tbl_stock_adjust_sheet`;
CREATE TABLE `tbl_stock_adjust_sheet` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '业务单据号',
  `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  `reason_id` varchar(32) NOT NULL COMMENT '调整原因ID',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存调整单';

-- ----------------------------
-- Records of tbl_stock_adjust_sheet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_stock_adjust_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_stock_adjust_sheet_detail`;
CREATE TABLE `tbl_stock_adjust_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '单据ID',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `stock_num` int(11) NOT NULL COMMENT '调整库存数量',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `order_no` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存调整单明细';

-- ----------------------------
-- Records of tbl_stock_adjust_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

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
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存成本调整单';

-- ----------------------------
-- Records of tbl_stock_cost_adjust_sheet
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存成本调整单明细';

-- ----------------------------
-- Records of tbl_stock_cost_adjust_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存盘点参数设置';

-- ----------------------------
-- Records of tbl_take_stock_config
-- ----------------------------
BEGIN;
INSERT INTO `tbl_take_stock_config` (`id`, `show_product`, `show_stock`, `auto_change_stock`, `allow_change_num`, `cancel_hours`) VALUES ('1', 1, 1, 1, 1, 168);
COMMIT;

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
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存盘点任务';

-- ----------------------------
-- Records of tbl_take_stock_plan
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `plan_id` (`plan_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存盘点任务明细';

-- ----------------------------
-- Records of tbl_take_stock_plan_detail
-- ----------------------------
BEGIN;
COMMIT;

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
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `plan_id` (`plan_id`) USING BTREE,
  KEY `sc_id` (`sc_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存盘点单';

-- ----------------------------
-- Records of tbl_take_stock_sheet
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sheet_id` (`sheet_id`,`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='库存盘点单明细';

-- ----------------------------
-- Records of tbl_take_stock_sheet_detail
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
