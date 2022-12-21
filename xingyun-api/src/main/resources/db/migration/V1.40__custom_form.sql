INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('9000007', '9000007', 'CustomForm', '自定义表单', NULL, 0, '/development/custom/form/index', '9000', '/custom/form', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
ALTER TABLE `sys_menu`
    ADD COLUMN `request_param` longtext NULL COMMENT '自定义请求参数' AFTER `component`;
ALTER TABLE `gen_custom_list_detail`
    ADD COLUMN `formatter` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '格式化脚本' AFTER `type`;
ALTER TABLE `gen_custom_list`
    ADD COLUMN `allow_export` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否允许导出' AFTER `suffix_sql`;

DROP TABLE IF EXISTS gen_custom_list_handle_column;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义列表工具栏';

DROP TABLE IF EXISTS gen_custom_list_toolbar;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义列表工具栏';

DROP TABLE IF EXISTS gen_custom_form_category;
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
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义表单分类';

DROP TABLE IF EXISTS gen_custom_form;
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
   KEY `category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义表单';