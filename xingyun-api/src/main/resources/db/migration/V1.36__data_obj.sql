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

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000003', '9000003', 'DataObj', '数据对象', NULL, '/development/data/obj/index', '9000', '/data/obj', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000004', '9000004', 'CustomList', '自定义列表', NULL, '/development/custom/list/index', '9000', '/custom/list', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');