DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `id`           varchar(32) CHARACTER SET utf8      NOT NULL COMMENT 'ID',
    `title`        varchar(200) CHARACTER SET utf8     NOT NULL COMMENT '标题',
    `content`      longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
    `available`    tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
    `published`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否发布',
    `create_by`    varchar(32) CHARACTER SET utf8      NOT NULL COMMENT '创建人',
    `create_time`  datetime                            NOT NULL COMMENT '创建时间',
    `update_by`    varchar(32) CHARACTER SET utf8      NOT NULL COMMENT '修改人',
    `update_time`  datetime                            NOT NULL COMMENT '修改时间',
    `readed_num`   int(11) NOT NULL DEFAULT '0' COMMENT '已读人数',
    `un_read_num`  int(11) NOT NULL DEFAULT '0' COMMENT '未读人数',
    `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统通知';

DROP TABLE IF EXISTS `sys_notice_log`;
CREATE TABLE `sys_notice_log`
(
    `id`        varchar(32) NOT NULL COMMENT 'ID',
    `notice_id` varchar(32) NOT NULL COMMENT '标题',
    `user_id`   varchar(32) NOT NULL COMMENT '用户ID',
    `readed`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读',
    `read_time` datetime DEFAULT NULL COMMENT '已读时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `notice_id` (`notice_id`,`user_id`),
    KEY         `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统通知记录';

INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`,
                       `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`,
                       `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000008', '1000008', 'PublishSysNotice', '发布系统通知', NULL, '/system/notice/publish', '1000',
        '/system/notice/publish', 0, 1, 0, 'system:notice:publish', 0, 1, '', '1',
        '2022-08-18 14:31:12', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`,
                       `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`,
                       `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000008001', '1000008001', '', '新增系统通知', NULL, '', '1000008', '', 0, 2, 0,
        'system:notice:add', 0, 1, '', '1', '2022-08-18 14:31:12', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`,
                       `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`,
                       `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000008002', '1000008002', '', '修改系统通知', NULL, '', '1000008', '', 0, 2, 0,
        'system:notice:modify', 0, 1, '', '1', '2022-08-18 14:31:12', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`,
                       `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`,
                       `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('1000009', '1000009', 'SysNotice', '系统通知', NULL, '/system/notice/index', '1000',
        '/system/notice', 0, 1, 0, 'system:notice:query', 0, 1, '', '1', '2022-08-18 14:31:12', '1',
        '2022-08-18 14:31:12');