UPDATE `sys_menu`
SET `component` = '/system/online-code/index',
    `path` = '/online-code',
    `request_param` = NULL,
    `update_by` = '系统管理员',
    `update_by_id` = '1',
    `update_time` = NOW()
WHERE `id` = '1001001'
  AND `code` = '1001001'
  AND `permission` = 'system:online-code:config';
