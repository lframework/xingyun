CREATE TABLE `sys_mail_message` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `mail` varchar(100) NOT NULL COMMENT '邮箱',
  `biz_key` varchar(20) NOT NULL COMMENT '业务键',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `send_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '发送状态',
  PRIMARY KEY (`id`),
  KEY `mail` (`mail`),
  KEY `biz_key` (`biz_key`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件消息';

CREATE TABLE `sys_notify_group` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `receiver_type` tinyint(3) NOT NULL COMMENT '接收者类型',
  `message_type` longtext NOT NULL COMMENT '消息类型',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `available` tinyint(1) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知组';

CREATE TABLE `sys_notify_group_receiver` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `group_id` varchar(20) NOT NULL COMMENT '消息通知组ID',
  `receiver_id` varchar(20) NOT NULL COMMENT '接收者ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`,`receiver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知组-接收者关系表';

CREATE TABLE `sys_site_message` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `receiver_id` varchar(20) NOT NULL COMMENT '接收人ID',
  `biz_key` varchar(20) NOT NULL COMMENT '业务键',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `readed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `read_time` datetime DEFAULT NULL COMMENT '已读时间',
  PRIMARY KEY (`id`),
  KEY `biz_key` (`biz_key`),
  KEY `receiver_id` (`receiver_id`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='站内信';

CREATE TABLE `tbl_product_stock_warning` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `sc_id` varchar(20) NOT NULL COMMENT '仓库ID',
  `product_id` varchar(20) NOT NULL COMMENT '商品ID',
  `max_limit` int(11) NOT NULL DEFAULT '0' COMMENT '预警上限',
  `min_limit` int(11) NOT NULL DEFAULT '0' COMMENT '预警下限',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sc_id` (`sc_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存预警';

CREATE TABLE `tbl_product_stock_warning_notify` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `notify_group_id` varchar(20) NOT NULL COMMENT '通知组ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `notify_group_id` (`notify_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存预警通知组';

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000014', '1000014', 'SysNotifyGroup', '消息通知组', NULL, 0, '/system/notify-group/index', NULL, '1000', '2', '/notify-group', 0, 1, 0, 'system:notify-group:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000014001', '1000014001', '', '新增消息通知组', NULL, 0, '', NULL, '1000014', '8', '', 0, 2, 0, 'system:notify-group:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000014002', '1000014002', '', '修改消息通知组', NULL, 0, '', NULL, '1000014', '8', '', 0, 2, 0, 'system:notify-group:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000007', '3000007', 'StockWarning', '库存预警', NULL, 0, '/sc/stock/warning/index', NULL, '3000', '8', '/warning', 0, 1, 0, 'stock:warning:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000007001', '3000007001', '', '新增库存预警', NULL, 0, '', NULL, '3000007', '8', '', 0, 2, 0, 'stock:warning:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000007002', '3000007002', '', '修改库存预警', NULL, 0, '', NULL, '3000007', '8', '', 0, 2, 0, 'stock:warning:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000007003', '3000007003', '', '删除库存预警', NULL, 0, '', NULL, '3000007', '8', '', 0, 2, 0, 'stock:warning:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('3000007004', '3000007004', '', '设置消息通知组', NULL, 0, '', NULL, '3000007', '8', '', 0, 2, 0, 'stock:warning:notify', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1002', '1002', 'MsgCenter', '消息中心', 'ant-design:message-outlined', NULL, '', NULL, NULL, '2', '/msg-center', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
UPDATE `sys_menu` SET `parent_id` = '1002' WHERE `id` = '1000009';
UPDATE `sys_menu` SET `code` = '1002001' WHERE `id` = '1000009';
UPDATE `sys_menu` SET `permission` = '' WHERE `id` = '1000009';
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1002002', '1002002', 'SysNotify', '站内信', NULL, 0, '/system/notify/index', NULL, '1002', '2', '/system/notify', 0, 1, 0, '', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
UPDATE `sys_menu` SET `parent_id` = '1002' WHERE `id` = '1000008';
UPDATE `sys_menu` SET `title` = '我的系统通知' WHERE `id` = '1000009';
UPDATE `sys_menu` SET `title` = '我的站内信' WHERE `id` = '1002002';
UPDATE `sys_menu` SET `code` = '1002002', `name` = 'SiteMessage', `title` = '我的站内信', `icon` = NULL, `component_type` = 0, `component` = '/system/site-message/index', `request_param` = NULL, `parent_id` = '1002', `sys_module_id` = '2', `path` = '/system/site-message', `no_cache` = 0, `display` = 1, `hidden` = 0, `permission` = '', `is_special` = 0, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2022-08-18 14:31:12', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2022-08-18 14:31:12' WHERE `id` = '1002002';
UPDATE `sys_menu` SET `code` = '1002001', `name` = 'MySysNotice', `title` = '我的系统通知', `icon` = NULL, `component_type` = 0, `component` = '/system/notice/index', `request_param` = NULL, `parent_id` = '1002', `sys_module_id` = '2', `path` = '/system/notice/my', `no_cache` = 0, `display` = 1, `hidden` = 0, `permission` = '', `is_special` = 0, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2022-08-18 14:31:12', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2022-08-18 14:31:12' WHERE `id` = '1000009';
UPDATE `sys_menu` SET `code` = '1002002', `name` = 'MySiteMessage', `title` = '我的站内信', `icon` = NULL, `component_type` = 0, `component` = '/system/site-message/index', `request_param` = NULL, `parent_id` = '1002', `sys_module_id` = '2', `path` = '/system/site-message/my', `no_cache` = 0, `display` = 1, `hidden` = 0, `permission` = '', `is_special` = 0, `available` = 1, `description` = '', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2022-08-18 14:31:12', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2022-08-18 14:31:12' WHERE `id` = '1002002';
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1002001', '1002001', 'SiteMessage', '站内信', NULL, 0, '/system/site-message/manage', NULL, '1002', '2', '/system/site-message/manage', 0, 1, 0, 'system:site-message:manage', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1002003', '1002003', 'MailMessage', '邮件消息', NULL, 0, '/system/mail-message/index', NULL, '1002', '2', '/system/mail-message', 0, 1, 0, 'system:mail-message:manage', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');