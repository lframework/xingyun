INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000015', '1000015', 'UserGroup', '用户分组', NULL, 0, '/system/user-group/index', NULL, '1000', '2', '/user-group', 0, 1, 0, 'system:user-group:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2025-01-19 18:36:11');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000015001', '1000015001', '', '新增用户分组', NULL, 0, '', NULL, '1000015', '8', '', 0, 2, 0, 'system:user-group:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2025-01-19 18:36:11');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000015002', '1000015002', '', '修改用户分组', NULL, 0, '', NULL, '1000015', '8', '', 0, 2, 0, 'system:user-group:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2025-01-19 18:36:11');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (99, '通用编号', '[{\"type\":1,\"pattern\":\"yyMMdd\"},{\"type\":3,\"key\":\"9dfa3174afa0464794e98e19ad7bb121ef24\",\"len\":\"5\",\"step\":1,\"expireSeconds\":86400}]');
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (11, '用户分组编号', '[{\"type\":1,\"pattern\":\"yyMMdd\"},{\"type\":3,\"key\":\"4bf9dafec322744f1f08bdf2d2569076a4d7\",\"len\":\"5\",\"step\":1,\"expireSeconds\":86400}]');
CREATE TABLE `sys_user_group` (
    `id` varchar(20) NOT NULL COMMENT 'ID',
    `code` varchar(20) NOT NULL COMMENT '编号',
    `name` varchar(20) NOT NULL COMMENT '名称',
    `description` varchar(200) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` varchar(32) NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `available` tinyint(1) NOT NULL COMMENT '状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`),
    UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组';

CREATE TABLE `sys_user_group_detail` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `user_id` varchar(32) NOT NULL COMMENT '用户ID',
    `group_id` varchar(32) NOT NULL COMMENT '用户组ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `user_id, group_id` (`user_id`,`group_id`) USING BTREE,
    KEY `group_id` (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户与用户组关系表';