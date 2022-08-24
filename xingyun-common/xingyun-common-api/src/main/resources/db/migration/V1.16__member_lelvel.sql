ALTER TABLE `base_data_member`
    ADD COLUMN `level_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员等级' AFTER `update_time`,
ADD INDEX `level_id`(`level_id`) USING BTREE;

DROP TABLE IF EXISTS `tbl_member_level`;
CREATE TABLE `tbl_member_level`
(
    `id`          varchar(32)  NOT NULL COMMENT 'ID',
    `code`        varchar(20)  NOT NULL COMMENT '编号',
    `name`        varchar(20)  NOT NULL COMMENT '名称',
    `exp`         int(11) NOT NULL COMMENT '经验值',
    `is_default`  tinyint(1) NOT NULL COMMENT '是否默认等级',
    `available`   tinyint(1) NOT NULL COMMENT '状态',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`   varchar(32)  NOT NULL COMMENT '创建人',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)  NOT NULL COMMENT '修改人',
    `update_time` datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `tbl_member_level` COMMENT = '会员等级';

INSERT INTO `tbl_member_level`
VALUES ('1', '1', '默认等级', 0, 1, 1, '默认等级', '1', '2022-06-11 19:39:33', '1', '2022-06-11 19:39:36');

UPDATE `base_data_member`
SET level_id = '1';

ALTER TABLE `tbl_member_level`
    MODIFY COLUMN `available` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态' AFTER `is_default`,
    ADD UNIQUE INDEX `exp`(`exp`) USING BTREE;

INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('0003', '0003', 'Crm', '星云CRM', '', NULL, '/crm', 0, 0, 0, '', 1, 1, '', '1',
        '2021-07-05 01:21:35', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('0003001', '0003001', 'Member', '会员管理', NULL, '0003', '/member', 0, 0, 0, NULL, 1, 1, '',
        '1', '2022-04-22 22:52:24', '1', '2022-04-22 22:52:24');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('0003001001', '0003001001', 'MemberLevel', '会员等级', '/base-data/member/level/index',
        '0003001', '/level', 0, 1, 0, 'member:level:query', 0, 1, '', '1', '2022-06-11 22:20:35',
        '1', '2022-06-11 22:20:35');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('0003001001001', '0003001001001', '', '新增会员等级', '', '0003001001', '', 0, 2, 0,
        'member:level:add', 0, 1, '', '1', '2022-06-11 22:20:35', '1', '2022-06-11 22:20:35');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('0003001001002', '0003001001002', '', '修改会员等级', '', '0003001001', '', 0, 2, 0,
        'member:level:modify', 0, 1, '', '1', '2022-06-11 22:20:35', '1', '2022-06-11 22:20:35');

INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`,
                       `no_cache`, `display`, `hidden`, `permission`, `is_special`,
                       `available`, `description`, `create_by`, `create_time`,
                       `update_by`, `update_time`)
VALUES ('0003001002', '0003001002', 'MemberLevelConfig', '会员等级规则',
        '/base-data/member/level/config/index', '0003001', '/level-config', 0, 1, 0,
        'member:level:config', 0, 1, '', '1', '2022-06-11 22:20:35', '1', '2022-06-11 22:20:35');

DROP TABLE IF EXISTS `tbl_member_level_config`;
CREATE TABLE `tbl_member_level_config`
(
    `id`               varchar(32) NOT NULL COMMENT 'ID',
    `exp`              int(11) NOT NULL DEFAULT '0' COMMENT '每消费1元获得的经验值',
    `is_down_grade`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否自动降级',
    `down_grade_cycle` tinyint(3) NOT NULL DEFAULT '1' COMMENT '降级周期',
    `down_grade_exp`   int(11) NOT NULL COMMENT '每次降级的经验值',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `tbl_member_level_config`
VALUES ('1', 1, 0, 1, 20);

ALTER TABLE `tbl_member_level_config` COMMENT = '会员等级规则';

ALTER TABLE `base_data_member`
    ADD COLUMN `exp` int(11) NOT NULL DEFAULT 0 COMMENT '当前经验值' AFTER `level_id`;

ALTER TABLE `base_data_member`
DROP
COLUMN `level_id`,
DROP
COLUMN `exp`,
DROP INDEX `level_id`;

DROP TABLE IF EXISTS `crm_member`;
CREATE TABLE `crm_member`
(
    `id`       varchar(32) NOT NULL COMMENT '会员ID',
    `level_id` varchar(32) DEFAULT NULL COMMENT '会员等级',
    `exp`      int(11) NOT NULL DEFAULT '0' COMMENT '当前经验值',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CRM会员信息';

INSERT INTO crm_member
SELECT id, 1, 0
FROM base_data_member;

ALTER TABLE `crm_member`
    ADD COLUMN `last_drop_time` datetime NULL COMMENT '末次降级时间' AFTER `exp`;

ALTER TABLE `crm_member`
    CHANGE COLUMN `last_drop_time` `last_drop_date` date NULL DEFAULT NULL COMMENT '末次降级日期' AFTER `exp`;