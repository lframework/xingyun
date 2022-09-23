INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010', '1000010', 'SysDataDic', '数据字典', NULL, '/system/dic/index', '1000', '/dic', 0, 1, 0, 'system:dic:query', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010001', '1000010001', '', '新增数据字典', NULL, '', '1000010', '', 0, 2, 0, 'system:dic:add', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010002', '1000010002', '', '修改数据字典', NULL, '', '1000010', '', 0, 2, 0, 'system:dic:modify', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010003', '1000010003', '', '删除数据字典', NULL, '', '1000010', '', 0, 2, 0, 'system:dic:delete', 1, 1, '', '1', '2021-05-12 23:24:36', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010004', '1000010004', '', '新增数据字典分类', NULL, '', '1000010', '', 0, 2, 0, 'system:dic-category:add', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010005', '1000010005', '', '修改数据字典分类', NULL, '', '1000010', '', 0, 2, 0, 'system:dic-category:modify', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010006', '1000010006', '', '删除数据字典分类', NULL, '', '1000010', '', 0, 2, 0, 'system:dic-category:delete', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010007', '1000010007', '', '新增数据字典值', NULL, '', '1000010', '', 0, 2, 0, 'system:dic-item:add', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010008', '1000010008', '', '修改数据字典值', NULL, '', '1000010', '', 0, 2, 0, 'system:dic-item:modify', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000010009', '1000010009', '', '删除数据字典值', NULL, '', '1000010', '', 0, 2, 0, 'system:dic-item:delete', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_data_dic`;
CREATE TABLE `sys_data_dic` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `name` (`name`),
  KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

DROP TABLE IF EXISTS `sys_data_dic_category`;
CREATE TABLE `sys_data_dic_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典分类';

DROP TABLE IF EXISTS `sys_data_dic_item`;
CREATE TABLE `sys_data_dic_item` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `dic_id` varchar(255) NOT NULL COMMENT '字典ID',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dic_id` (`dic_id`,`code`),
  UNIQUE KEY `dic_id_2` (`dic_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

DROP TABLE IF EXISTS `gen_data_entity_category`;
CREATE TABLE `gen_data_entity_category` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据实体分类';

DROP TABLE IF EXISTS `gen_data_entity`;
CREATE TABLE `gen_data_entity` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `gen_status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据实体';

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
  `fix_enum` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否内置枚举',
  `enum_back` varchar(200) DEFAULT NULL COMMENT '后端枚举名',
  `enum_front` varchar(200) DEFAULT NULL COMMENT '前端枚举名',
  `regular_expression` varchar(200) DEFAULT NULL COMMENT '正则表达式',
  `is_order` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否排序字段',
  `order_type` varchar(20) DEFAULT NULL COMMENT '排序类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `entity_id` (`entity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据实体明细';

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000003', '9000003', 'DataEntity', '数据实体', NULL, '/development/data/entity/index', '9000', '/data/entity', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');
insert into gen_data_entity_detail SELECT id, data_obj_id as entity_id, name, column_name, is_key, data_type, column_order, description, view_type, fix_enum, enum_back, enum_front, regular_expression, is_order, order_type FROM gen_data_object_column where data_obj_id IN (select id from gen_data_object where gen_status IN (1, 2));
insert into gen_data_entity SELECT id, name, null as category_id, available, description, create_by, create_time, update_by, update_time, gen_status FROM gen_data_object where gen_status IN (1,2);

UPDATE sys_menu set id = '9000004', code = '9000004' where id = '9000001';
UPDATE sys_menu set id = '9000001', code = '9000001' where id = '9000002';
UPDATE sys_menu set id = '9000002', code = '9000002' where id = '9000003';
UPDATE sys_menu set id = '9000003', code = '9000003' where id = '9000004';

delete from sys_menu where id = '9000003';
DROP TABLE IF EXISTS `gen_data_object`;
DROP TABLE IF EXISTS `gen_data_object_column`;

ALTER TABLE `gen_data_entity_detail`
ADD COLUMN `data_dic_id` varchar(32) NULL COMMENT '数据字典ID' AFTER `view_type`,
ADD COLUMN `len` int(11) NULL COMMENT '长度' AFTER `order_type`,
ADD COLUMN `decimals` int(11) NULL COMMENT '小数位数' AFTER `len`;

ALTER TABLE `gen_simple_table_column`
ADD COLUMN `len` int(11) NULL COMMENT '长度' AFTER `column_comment`,
ADD COLUMN `decimals` int(11) NULL COMMENT '小数位数' AFTER `len`;

ALTER TABLE `gen_data_entity_detail`
MODIFY COLUMN `len` bigint NULL DEFAULT NULL COMMENT '长度';

ALTER TABLE `gen_simple_table_column`
MODIFY COLUMN `len` bigint NULL DEFAULT NULL COMMENT '长度' AFTER `column_comment`;