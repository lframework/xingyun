ALTER TABLE `crm_member`
    ADD COLUMN `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号' AFTER `id`,
ADD COLUMN `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称' AFTER `code`,
ADD COLUMN `gender` tinyint(3) NOT NULL DEFAULT 0 COMMENT '性别' AFTER `name`,
ADD COLUMN `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话' AFTER `gender`,
ADD COLUMN `birthday` date NULL DEFAULT NULL COMMENT '出生日期' AFTER `telephone`,
ADD COLUMN `join_day` date NULL COMMENT '入会日期' AFTER `birthday`,
ADD COLUMN `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属门店' AFTER `join_day`,
ADD COLUMN `guider_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属导购' AFTER `shop_id`,
DROP
PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;

update base_data_member as m inner join (select telephone from base_data_member where telephone is not null group by telephone having count(*) > 1) as m2
on m2.telephone = m.telephone set m.description = m.telephone, m.telephone = NULL;

UPDATE crm_member AS c
    INNER JOIN base_data_member AS m
ON m.id = c.id
    SET c.CODE = m.CODE,
        c.NAME = m.NAME,
        c.gender = m.gender,
        c.telephone = m.telephone,
        c.birthday = m.birthday,
        c.join_day = m.join_day,
        c.shop_id = m.shop_id,
        c.guider_id = m.guider_id;

ALTER TABLE `crm_member`
    MODIFY COLUMN `join_day` date NOT NULL COMMENT '入会日期' AFTER `birthday`;

ALTER TABLE `crm_member`
    ADD UNIQUE INDEX `code`(`code`) USING BTREE;
ALTER TABLE `base_data_member`
    ADD UNIQUE INDEX `telephone`(`telephone`) USING BTREE;
ALTER TABLE `crm_member`
    ADD UNIQUE INDEX `telephone`(`telephone`) USING BTREE;

INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('0003001000', '0003001000', 'CrmMember', '全域会员', '/crm/member/index', '0003001',
        '/crm-member', 0, 1, 0, 'crm:member:query', 0, 1, '', '1', '2022-06-11 22:20:35', '1',
        '2022-06-11 22:20:35');
UPDATE `sys_menu`
SET `code`        = '0003001001',
    `name`        = 'MemberLevel',
    `title`       = '会员等级',
    `component`   = '/crm/member/level/index',
    `parent_id`   = '0003001',
    `path`        = '/level',
    `no_cache`    = 0,
    `display`     = 1,
    `hidden`      = 0,
    `permission`  = 'member:level:query',
    `is_special`  = 0,
    `available`   = 1,
    `description` = '',
    `create_by`   = '1',
    `create_time` = '2022-06-11 22:20:35',
    `update_by`   = '1',
    `update_time` = '2022-06-11 22:20:35'
WHERE `id` = '0003001001';
UPDATE `sys_menu`
SET `code`        = '0003001002',
    `name`        = 'MemberLevelConfig',
    `title`       = '会员等级规则',
    `component`   = '/crm/member/level/config/index',
    `parent_id`   = '0003001',
    `path`        = '/level-config',
    `no_cache`    = 0,
    `display`     = 1,
    `hidden`      = 0,
    `permission`  = 'member:level:config',
    `is_special`  = 0,
    `available`   = 1,
    `description` = '',
    `create_by`   = '1',
    `create_time` = '2022-06-11 22:20:35',
    `update_by`   = '1',
    `update_time` = '2022-06-11 22:20:35'
WHERE `id` = '0003001002';
UPDATE `sys_menu`
SET `code`        = '9000001',
    `name`        = 'DataObject',
    `title`       = '代码生成',
    `component`   = '/development/data/index',
    `parent_id`   = '9000',
    `path`        = '/data',
    `no_cache`    = 0,
    `display`     = 1,
    `hidden`      = 0,
    `permission`  = '',
    `is_special`  = 1,
    `available`   = 1,
    `description` = '',
    `create_by`   = '1',
    `create_time` = '2021-05-08 18:37:01',
    `update_by`   = '1',
    `update_time` = '2021-12-09 17:54:42'
WHERE `id` = '9000001';
UPDATE `sys_menu`
SET `code`        = '9001001',
    `name`        = 'FileBox',
    `title`       = '文件收纳箱',
    `component`   = '/smart-work/file-box/index',
    `parent_id`   = '9001',
    `path`        = '/file-box',
    `no_cache`    = 0,
    `display`     = 1,
    `hidden`      = 0,
    `permission`  = '',
    `is_special`  = 1,
    `available`   = 1,
    `description` = '',
    `create_by`   = '1',
    `create_time` = '2021-05-08 18:37:01',
    `update_by`   = '1',
    `update_time` = '2021-12-09 17:54:42'
WHERE `id` = '9001001';
UPDATE `sys_menu`
SET `code`        = '9001002',
    `name`        = 'OnlineExcel',
    `title`       = '在线Excel',
    `component`   = '/smart-work/online-excel/index',
    `parent_id`   = '9001',
    `path`        = '/online-excel',
    `no_cache`    = 0,
    `display`     = 1,
    `hidden`      = 0,
    `permission`  = '',
    `is_special`  = 1,
    `available`   = 1,
    `description` = '',
    `create_by`   = '1',
    `create_time` = '2021-05-08 18:37:01',
    `update_by`   = '1',
    `update_time` = '2021-12-09 17:54:42'
WHERE `id` = '9001002';