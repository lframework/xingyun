SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000013', '1000013', 'SysGenerateCode', '编号规则', NULL, 0, '/system/generate-code/index', NULL, '1000', '2', '/generate-code', 0, 1, 0, 'system:generate-code:manage', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
-- ----------------------------
-- Table structure for sys_generate_code
-- ----------------------------
DROP TABLE IF EXISTS `sys_generate_code`;
CREATE TABLE `sys_generate_code` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `config_str` longtext NOT NULL COMMENT '配置信息（JSONArray）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_generate_code
-- ----------------------------
BEGIN;
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (1, '用户编号', '[{\"type\":6,\"val\":\"R\"},{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"58aabb1d160430444158d82e4c86cb9c7f2a\",\"len\":\"5\",\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (200, '采购订单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"37366f903834c842d5e8144bd707bdb77e35\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (201, '采购收货单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"12099be638c90d466b2a4af5465ed632459f\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (202, '采购退单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"60fdc467f35db94c8569f6ed68d103ce5452\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (203, '销售订单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"d141ddad1cea5344ebe85ac6d3c6550a9d5b\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (204, '销售出库单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"2bec018f14b3d647b9e8641a408dcecf58bc\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (205, '销售退货单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"240604dc9024834d9d29abf86dd6bf7f3989\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (206, '零售出库单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"60ecbed8c835454552f8c177c69c155bdaf8\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (207, '零售退货单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"8307f839bd2e24426de87884fc034fe8d546\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (208, '预先盘点单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"5142a4fc6392764d35bb852e9dcfe544a482\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (209, '盘点任务号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"e0937a56dc47a6437c1840195ab26a454db6\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (210, '盘点单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"d5a2827d3b2f6844aae858e6cb62449a3283\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (212, '库存调整单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"10787084f2177b430709b260f42085ce14e9\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (213, '仓库调拨单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"3b0668d95c93234ca65a6afa6f5c333819ea\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (214, '物流单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"f693245a4a05ab4e7f19362e45e09da59d74\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (300, '供应商对账单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"89398b781627df40f6fbfdab81fc7ab83c05\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (301, '供应商费用单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"286289a735b3f24859a91db603b14e48df4e\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (302, '供应商付款单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"f7ed22483ab83a435218cd0af3b6102fa308\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (303, '供应商结算单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"741524aed5dbd1452acbe05bf63a35d8bbeb\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (304, '客户对账单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"d8bddcd37139d24bb12ae66b0f6732eecef9\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (305, '客户费用单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"37f9d9a53699d042088918afe08d7de15d82\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (306, '客户预付款单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"9a2cb171fd93464fb76928b35f77d0045b32\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (307, '客户结算单号', '[{\"type\":1,\"pattern\":\"yyyyMMdd\"},{\"type\":3,\"key\":\"e63f16fc2acf984c3e9bef378e6ea4546204\",\"len\":10,\"step\":1,\"expireSeconds\":86400}]');
COMMIT;

UPDATE `sys_menu` SET `code` = '2001001', `name` = 'ProductCategory', `title` = '商品分类', `icon` = NULL, `component_type` = 0, `component` = '/base-data/product/category/index', `request_param` = NULL, `parent_id` = '2001', `sys_module_id` = '4', `path` = '/category', `no_cache` = 0, `display` = 1, `hidden` = 0, `permission` = 'base-data:product:category:query', `is_special` = 1, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2021-07-05 21:59:35', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2021-07-05 21:59:36' WHERE `id` = '2001001';
UPDATE `sys_menu` SET `code` = '2001001001', `name` = '', `title` = '新增分类', `icon` = NULL, `component_type` = 0, `component` = '', `request_param` = NULL, `parent_id` = '2001001', `sys_module_id` = '4', `path` = '', `no_cache` = 0, `display` = 2, `hidden` = 0, `permission` = 'base-data:product:category:add', `is_special` = 1, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2021-05-12 22:50:27', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2021-07-04 00:34:23' WHERE `id` = '2001001001';
UPDATE `sys_menu` SET `code` = '2001001002', `name` = '', `title` = '修改分类', `icon` = NULL, `component_type` = 0, `component` = '', `request_param` = NULL, `parent_id` = '2001001', `sys_module_id` = '4', `path` = '', `no_cache` = 0, `display` = 2, `hidden` = 0, `permission` = 'base-data:product:category:modify', `is_special` = 1, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2021-05-12 23:23:33', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2021-07-04 00:34:23' WHERE `id` = '2001001002';
UPDATE `sys_menu` SET `code` = '2001001003', `name` = '', `title` = '导入分类', `icon` = NULL, `component_type` = 0, `component` = '', `request_param` = NULL, `parent_id` = '2001001', `sys_module_id` = '4', `path` = '', `no_cache` = 0, `display` = 2, `hidden` = 0, `permission` = 'base-data:product:category:import', `is_special` = 1, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2021-05-12 23:23:33', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2021-07-04 00:34:23' WHERE `id` = '2001001003';
UPDATE `sys_menu` SET `title` = '库存调整' WHERE `id` = '3000005003';
UPDATE `sys_menu` SET `code` = '3000005002001', `name` = '', `title` = '新增库存调整原因', `icon` = NULL, `component_type` = 0, `component` = '', `request_param` = NULL, `parent_id` = '3000005002', `sys_module_id` = '10', `path` = '', `no_cache` = 0, `display` = 2, `hidden` = 0, `permission` = 'stock:adjust:reason:add', `is_special` = 1, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2021-05-12 22:50:27', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2021-07-04 00:34:23' WHERE `id` = '3000005002001';
UPDATE `sys_menu` SET `code` = '3000005002002', `name` = '', `title` = '修改库存调整原因', `icon` = NULL, `component_type` = 0, `component` = '', `request_param` = NULL, `parent_id` = '3000005002', `sys_module_id` = '10', `path` = '', `no_cache` = 0, `display` = 2, `hidden` = 0, `permission` = 'stock:adjust:reason:modify', `is_special` = 1, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2021-05-12 22:50:27', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2021-07-04 00:34:23' WHERE `id` = '3000005002002';

DELETE FROM `sys_menu` WHERE id IN ('3000005001', '3000005001001', '3000005001002', '3000005001003', '3000005001004', '3000005001005', '1000003', '1000003001', '1000003002');
DROP TABLE `tbl_stock_cost_adjust_sheet`;
DROP TABLE `tbl_stock_cost_adjust_sheet_detail`;
DROP TABLE sys_position;
SET FOREIGN_KEY_CHECKS = 1;