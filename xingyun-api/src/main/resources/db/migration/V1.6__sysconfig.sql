ALTER TABLE `sys_user` ADD COLUMN `lock_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '锁定状态' AFTER `available`;
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('1000000', '1000000', 'SysConfig', '系统设置', '/system/config/index', '1000', '/config', 0, 1, 0, 'system:config:modify', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');

DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `allow_regist` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许注册',
  `allow_lock` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否允许锁定用户',
  `fail_num` int(11) NOT NULL DEFAULT '5' COMMENT '登录失败次数',
  `allow_captcha` tinyint(1) NOT NULL COMMENT '是否允许验证码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO `sys_config` (`id`, `allow_regist`, `allow_lock`, `fail_num`, `allow_captcha`) VALUES ('1', 0, 0, 7, 1);
COMMIT;
