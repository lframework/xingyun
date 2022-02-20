INSERT INTO sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000', '9000', 'Development', '开发管理', '', NULL, '/development', 0, 0, 0, '', 1, 1, '', '1', '2021-07-04 00:22:05', '1', '2021-07-04 00:34:23');
INSERT INTO sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000001', '9000001', 'DataObject', '代码生成', '/development/data/index', '9000', '/development/data', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');

-- ----------------------------
-- Table structure for gen_create_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_create_column_config`;
CREATE TABLE `gen_create_column_config` (
    `id` varchar(32) NOT NULL,
    `required` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否必填',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_data_object
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_object`;
CREATE TABLE `gen_data_object` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `code` varchar(20) NOT NULL COMMENT '编号',
    `name` varchar(20) NOT NULL COMMENT '名称',
    `type` tinyint(3) NOT NULL COMMENT '类型 1 数据库单表',
    `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
    `description` varchar(200) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` varchar(32) NOT NULL COMMENT '修改人ID',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `gen_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_data_object_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_object_column`;
CREATE TABLE `gen_data_object_column` (
    `id` varchar(32) NOT NULL,
    `data_obj_id` varchar(32) NOT NULL COMMENT '数据对象ID',
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
    PRIMARY KEY (`id`),
    KEY `data_obj_id` (`data_obj_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_detail_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_detail_column_config`;
CREATE TABLE `gen_detail_column_config` (
    `id` varchar(32) NOT NULL,
    `span` int(11) NOT NULL COMMENT '列宽',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_query_params_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_query_params_column_config`;
CREATE TABLE `gen_query_params_column_config` (
    `id` varchar(32) NOT NULL,
    `query_type` tinyint(3) NOT NULL COMMENT '查询类型',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
    PRIMARY KEY (`id`),
    KEY `table_schema` (`table_schema`,`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gen_update_column_config
-- ----------------------------
DROP TABLE IF EXISTS `gen_update_column_config`;
CREATE TABLE `gen_update_column_config` (
    `id` varchar(32) NOT NULL,
    `required` tinyint(1) NOT NULL DEFAULT '0',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;