ALTER TABLE `sys_role`
    ADD COLUMN `category_id` varchar(32) NOT NULL COMMENT '分类ID' AFTER `id`;
ALTER TABLE `sys_role`
    ADD INDEX `category_id`(`category_id`) USING BTREE;
UPDATE sys_role SET category_id = '1';

CREATE TABLE `sys_role_category` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色分类';

INSERT INTO `sys_role_category` (`id`, `code`, `name`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1', '001', '默认', '系统管理员', '1', '2025-05-12 00:00:00', '系统管理员', '1', '2025-05-12 00:00:00');
