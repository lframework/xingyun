INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('9001', '9001', 'SmartWork', '便捷办公', '', '0001', '/smart-work', 0, 0, 0, '', 1, 1, '', '1',
        '2021-07-04 00:22:05', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('9001001', '9001001', 'FileBox', '文件收纳箱', '/smart-work/file-box/index', '9001',
        '/smart-work/file-box', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1',
        '2021-12-09 17:54:42');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('9001002', '9001002', 'OnlineExcel', '在线Excel', '/smart-work/online-excel/index', '9001',
        '/smart-work/online-excel', 0, 1, 0, '', 1, 1, '', '1', '2021-05-08 18:37:01', '1',
        '2021-12-09 17:54:42');
DROP TABLE IF EXISTS `sw_online_excel`;
CREATE TABLE `sw_online_excel`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `name`        varchar(200) NOT NULL COMMENT '名称',
    `content`     longtext     NOT NULL COMMENT '内容',
    `available`   tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1-在用 0停用',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线Excel';

DROP TABLE IF EXISTS `sw_file_box`;
CREATE TABLE `sw_file_box`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `name`        varchar(200) NOT NULL COMMENT '名称',
    `url`         longtext     NOT NULL COMMENT 'Url',
    `available`   tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件收纳箱';