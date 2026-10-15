-- ----------------------------
-- 修复生产管理菜单图标
-- ----------------------------
UPDATE `sys_menu` SET `icon` = 'ant-design:tool-outlined' WHERE `code` = '6000' AND `id` = '6000';