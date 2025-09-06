ALTER TABLE `tenant`
    ADD COLUMN `server_name` varchar(200) NULL COMMENT '域名' AFTER `name`;