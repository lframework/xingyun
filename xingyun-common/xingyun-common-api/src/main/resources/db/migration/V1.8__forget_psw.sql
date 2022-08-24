ALTER TABLE `sys_config`
ADD COLUMN `allow_forget_psw` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否开启忘记密码' AFTER `allow_captcha`,
ADD COLUMN `forget_psw_require_mail` tinyint(1) NOT NULL DEFAULT 0 COMMENT '忘记密码是否使用邮箱' AFTER `allow_forget_psw`,
ADD COLUMN `forget_psw_require_sms` tinyint(1) NOT NULL DEFAULT 0 COMMENT '忘记密码是否使用短信' AFTER `forget_psw_require_mail`,
ADD COLUMN `sign_name` varchar(200) NOT NULL DEFAULT '' COMMENT 'signName' AFTER `forget_psw_require_sms`,
ADD COLUMN `template_code` varchar(200) NOT NULL DEFAULT '' COMMENT 'templateCode' AFTER `sign_name`;