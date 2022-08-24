ALTER TABLE `sys_config`
    ADD COLUMN `allow_telephone_login` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否允许手机号登录' AFTER `allow_regist`,
ADD COLUMN `telephone_login_sign_name` varchar(200) NOT NULL DEFAULT '' COMMENT '手机号登录时的signName' AFTER `allow_telephone_login`,
ADD COLUMN `telephone_login_template_code` varchar(200) NOT NULL DEFAULT '' COMMENT '手机号登录时的templateCode' AFTER `telephone_login_sign_name`;