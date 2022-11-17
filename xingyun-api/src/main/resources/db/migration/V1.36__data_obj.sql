ALTER TABLE `gen_data_entity`
    ADD COLUMN `table_schema` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据表所属的数据库名' AFTER `gen_status`,
ADD COLUMN `table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据库表名' AFTER `table_schema`,
ADD COLUMN `engine` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库引擎' AFTER `table_name`,
ADD COLUMN `table_collation` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字符校验编码集' AFTER `engine`,
ADD COLUMN `table_comment` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注' AFTER `table_collation`,
ADD COLUMN `convert_type` tinyint(3) NOT NULL COMMENT '转换方式' AFTER `table_comment`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;

ALTER TABLE `gen_data_entity_detail`
    ADD COLUMN `db_data_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '字段数据类型' AFTER `decimals`,
ADD COLUMN `is_nullable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否允许为空' AFTER `db_data_type`,
ADD COLUMN `column_default` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '默认值' AFTER `is_nullable`,
ADD COLUMN `ordinal_position` bigint(21) UNSIGNED NOT NULL DEFAULT 0 COMMENT '字段排序' AFTER `column_default`,
ADD COLUMN `column_comment` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '字段备注' AFTER `ordinal_position`,
ADD COLUMN `db_len` bigint(20) NULL DEFAULT NULL COMMENT '长度' AFTER `column_comment`,
ADD COLUMN `db_decimals` int(11) NULL DEFAULT NULL COMMENT '小数位数' AFTER `db_len`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;
update gen_data_entity as e inner join gen_simple_table AS t ON t.id = e.id
    set e.table_schema = t.table_schema,
        e.table_name = t.table_name,
        e.engine = t.engine,
        e.table_collation = t.table_collation,
        e.table_comment = t.table_comment,
        e.convert_type = t.convert_type;
update gen_data_entity_detail as d INNER JOIN gen_simple_table_column AS c ON d.id = c.id
    set d.db_column_name = c.column_name,
        d.db_data_type = c.data_type,
        d.is_nullable = c.is_nullable,
        d.column_default = c.column_default,
        d.ordinal_position = c.ordinal_position,
        d.column_comment = c.column_comment,
        d.db_len = c.len,
        d.db_decimals = c.decimals;

DROP TABLE IF EXISTS gen_simple_table;
DROP TABLE IF EXISTS gen_simple_table_column;

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000003', '9000003', 'DataObj', '数据对象', NULL, '/development/data/obj/index', '9000', '/data/obj', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000004', '9000004', 'CustomList', '自定义列表', NULL, '/development/custom/list/index', '9000', '/custom/list', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');

DROP TABLE IF EXISTS gen_custom_list;
CREATE TABLE `gen_custom_list` (
   `id` varchar(32) NOT NULL COMMENT 'ID',
   `name` varchar(20) NOT NULL COMMENT '名称',
   `category_id` varchar(32) DEFAULT NULL COMMENT '分类ID',
   `data_obj_id` varchar(32) NOT NULL DEFAULT '1' COMMENT '数据对象ID',
   `label_width` int(11) NOT NULL COMMENT '表单Label宽度',
   `has_page` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否分页',
   `tree_data` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否树形列表',
   `tree_id_column` varchar(32) DEFAULT NULL COMMENT 'ID字段',
   `tree_id_column_rela_id` varchar(32) DEFAULT NULL COMMENT 'ID字段关联ID',
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
   PRIMARY KEY (`id`) USING BTREE,
   KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义列表';

DROP TABLE IF EXISTS gen_custom_list_category;
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
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义列表分类';

DROP TABLE IF EXISTS gen_custom_list_detail;
CREATE TABLE `gen_custom_list_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `custom_list_id` varchar(32) NOT NULL COMMENT '自定义列表ID',
  `rela_id` varchar(32) DEFAULT NULL COMMENT '关联ID',
  `data_entity_id` varchar(32) DEFAULT NULL COMMENT '数据实体ID',
  `width_type` tinyint(3) NOT NULL COMMENT '宽度类型',
  `width` int(11) NOT NULL COMMENT '宽度',
  `sortable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否页面排序',
  `order_no` int(11) NOT NULL COMMENT '排序编号',
  `type` tinyint(3) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `custom_list_id` (`custom_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义列表明细';

DROP TABLE IF EXISTS gen_custom_list_query_params;
CREATE TABLE `gen_custom_list_query_params` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `custom_list_id` varchar(32) NOT NULL COMMENT '自定义列表ID',
    `rela_id` varchar(32) NOT NULL COMMENT '关联ID',
    `data_entity_id` varchar(32) NOT NULL COMMENT '数据实体ID',
    `query_type` tinyint(3) NOT NULL COMMENT '查询类型',
    `form_width` int(11) NOT NULL DEFAULT '6' COMMENT '表单宽度',
    `default_value` longtext COMMENT '默认值',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    `type` tinyint(3) NOT NULL COMMENT '类型',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `custom_list_id` (`custom_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义列表查询参数';

DROP TABLE IF EXISTS gen_data_obj;
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
    KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据对象';

DROP TABLE IF EXISTS gen_data_obj_category;
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
     PRIMARY KEY (`id`),
     UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据实体分类';

DROP TABLE IF EXISTS gen_data_obj_detail;
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
   PRIMARY KEY (`id`),
   KEY `data_obj_id` (`data_obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据对象明细';

DROP TABLE IF EXISTS gen_data_obj_query_detail;
CREATE TABLE `gen_data_obj_query_detail` (
     `id` varchar(32) NOT NULL COMMENT 'ID',
     `data_obj_id` varchar(32) NOT NULL COMMENT '数据对象ID',
     `custom_name` varchar(200) NOT NULL COMMENT '显示名称',
     `custom_sql` longtext NOT NULL COMMENT '自定义SQL',
     `custom_alias` varchar(200) NOT NULL COMMENT '自定义别名',
     `data_type` tinyint(3) NOT NULL COMMENT '数据类型',
     `order_no` int(11) NOT NULL COMMENT '排序',
     PRIMARY KEY (`id`),
     KEY `data_obj_id` (`data_obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据对象自定义查询明细';