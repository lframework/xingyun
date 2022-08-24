SET FOREIGN_KEY_CHECKS=0;
DELETE FROM qrtz_cron_triggers;
DELETE FROM qrtz_job_details;
DELETE FROM qrtz_locks;
DELETE FROM qrtz_triggers;
SET FOREIGN_KEY_CHECKS=1;
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('9000002', '9000002', 'Qrtz', '定时器管理', NULL, '/development/qrtz/index', '9000', '/qrtz', 0, 1, 0, 'development:qrtz:manage', 1, 1, '', '1', '2021-05-08 18:37:01', '1', '2021-12-09 17:54:42');