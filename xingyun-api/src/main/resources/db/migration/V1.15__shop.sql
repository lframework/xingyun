DROP TABLE IF EXISTS `tbl_shop`;
CREATE TABLE `tbl_shop`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `code`        varchar(20)  NOT NULL COMMENT '编号',
    `name`        varchar(20)  NOT NULL COMMENT '名称',
    `dept_id`     varchar(32)           DEFAULT NULL COMMENT '所属部门ID',
    `lng`         decimal(16, 6)        DEFAULT NULL COMMENT '经度',
    `lat`         decimal(16, 6)        DEFAULT NULL COMMENT '纬度',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `available`   tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1-在用 0停用',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='门店';

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000007', '1000007', 'SysParameter', '系统参数', '/system/parameter/index', '1000', '/parameter', 0, 1, 0, 'system:parameter:query', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000007001', '1000007001', '', '新增系统参数', '', '1000007', '', 0, 2, 0, 'system:parameter:add', 1, 1, '', '1', '2021-05-12 22:50:27', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000007002', '1000007002', '', '修改系统参数', '', '1000007', '', 0, 2, 0, 'system:parameter:modify', 1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000007003', '1000007003', '', '删除系统参数', '', '1000007', '', 0, 2, 0, 'system:parameter:delete', 1, 1, '', '1', '2021-05-12 23:24:36', '1', '2021-07-04 00:34:23');

ALTER TABLE `base_data_member`
ADD COLUMN `shop_id` varchar(32) NULL COMMENT '所属门店' AFTER `join_day`,
ADD COLUMN `guider_id` varchar(32) NULL COMMENT '所属导购' AFTER `shop_id`;