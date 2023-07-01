-- 功能菜单SQL
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('${menuId}', '${menuCode}', '${className}', '${menuName}', '/${moduleName}/${bizName}/index', <#if parentMenuId??>'${parentMenuId}'<#else>NULL</#if>, '/${moduleName}/${bizName}', 0, 1, 0, '${moduleName}:${bizName}:query', 0, 1, '', '1', NOW(), '1', NOW());
-- 权限SQL
<#if create??>
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('${IdUtil.getId()}', '${menuCode}001', '', '新增${classDescription}', '', '${menuId}', '', 0, 2, 0, '${moduleName}:${bizName}:add', 0, 1, '', '1', NOW(), '1', NOW());
</#if>
<#if update??>
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('${IdUtil.getId()}', '${menuCode}002', '', '修改${classDescription}', '', '${menuId}', '', 0, 2, 0, '${moduleName}:${bizName}:modify', 0, 1, '', '1', NOW(), '1', NOW());
</#if>