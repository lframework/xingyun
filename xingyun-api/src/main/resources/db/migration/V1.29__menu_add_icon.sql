ALTER TABLE `sys_menu`
    ADD COLUMN `icon` varchar(200) NULL COMMENT '图标' AFTER `title`;
UPDATE `sys_menu` SET `icon` = 'a-menu' WHERE `display` = 0;