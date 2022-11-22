INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000005', '9000005', 'CustomDialog', '自定义对话框', NULL, 0, '/development/custom/dialog/index', '9000', '/custom/dialog', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
ALTER TABLE `gen_custom_list` ADD COLUMN `list_type` tinyint(3) NOT NULL DEFAULT 0 COMMENT '列表类型' AFTER `data_obj_id`;
ALTER TABLE `gen_custom_list`
    CHANGE COLUMN `tree_id_column` `id_column` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ID字段' AFTER `tree_data`,
    CHANGE COLUMN `tree_id_column_rela_id` `id_column_rela_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ID字段关联ID' AFTER `id_column`;
ALTER TABLE `gen_custom_list_query_params`
    ADD COLUMN `front_show` tinyint(1) NOT NULL DEFAULT 1 COMMENT '前端显示' AFTER `data_entity_id`;
UPDATE `sys_menu` SET `code` = '9000005', `name` = 'CustomSelector', `title` = '自定义选择器', `icon` = NULL, `component_type` = 0, `component` = '/development/custom/selector/index', `parent_id` = '9000', `path` = '/custom/selector', `no_cache` = 0, `display` = 1, `hidden` = 0, `permission` = '', `is_special` = 1, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2021-05-08 18:37:01', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2021-12-09 17:54:42' WHERE `id` = '9000005';
ALTER TABLE `gen_data_entity_detail`
    ADD COLUMN `custom_selector_id` varchar(32) NULL COMMENT '自定义选择器ID' AFTER `data_dic_id`;

DROP TABLE IF EXISTS gen_custom_selector;
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
   KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义对话框';

DROP TABLE IF EXISTS gen_custom_selector_category;
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
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义对话框分类';
