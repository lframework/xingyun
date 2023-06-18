INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000010', '2000010', 'LogisticsCompany', '物流公司', NULL, 0, '/base-data/logistics/company/index', NULL, '2000', '/logistics/company', 0, 1, 0, 'base-data:logistics-company:query', 1, 1, '', '系统管理员', '1', '2021-07-05 21:59:35', '系统管理员', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000010001', '2000010001', '', '新增物流公司', NULL, 0, '', NULL, '2000010', '', 0, 2, 0, 'base-data:logistics-company:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000010002', '2000010002', '', '修改物流公司', NULL, 0, '', NULL, '2000010', '', 0, 2, 0, 'base-data:logistics-company:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
ALTER TABLE `base_data_product`
ADD COLUMN `weight` decimal(16, 2) NULL COMMENT '重量（kg）' AFTER `unit`,
ADD COLUMN `volume` decimal(16, 2) NULL COMMENT '体积（cm3）' AFTER `weight`;

ALTER TABLE `sys_menu`
ADD COLUMN `sys_module_id` varchar(32) NULL COMMENT '系统模块ID' AFTER `parent_id`;

UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000001001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000001002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000001003';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000002001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000002002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000002003';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000003';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000003001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000003002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000004';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000004001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000004002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000004003';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000005';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000005001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000005002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000005003';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000006';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000007';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000007001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000007002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000007003';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000008';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000008001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000008002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000009';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010001';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010002';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010003';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010004';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010005';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010006';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010007';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010008';
UPDATE `sys_menu` SET `sys_module_id` = '2' WHERE `id` = '1000010009';
UPDATE `sys_menu` SET `sys_module_id` = '1' WHERE `id` = '1000011';
UPDATE `sys_menu` SET `sys_module_id` = '1' WHERE `id` = '1000011001';
UPDATE `sys_menu` SET `sys_module_id` = '1' WHERE `id` = '1000011002';
UPDATE `sys_menu` SET `sys_module_id` = '14' WHERE `id` = '1000012';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000002';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000002001';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000002002';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000002003';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000004';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000004001';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000004002';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000004003';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000005';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000005001';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000005002';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000005003';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000006';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000006001';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000006002';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000006003';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000007';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000007001';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000007002';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000007003';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000008';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000008001';
UPDATE `sys_menu` SET `sys_module_id` = '3' WHERE `id` = '2000008002';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000009';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000009001';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000009002';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000009003';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000009004';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000010';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000010001';
UPDATE `sys_menu` SET `sys_module_id` = '15' WHERE `id` = '2000010002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001001001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001001002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001001003';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001002001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001002002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001002003';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001003';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001003001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001003002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001003003';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001003004';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001003005';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001004';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001004001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001004002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001004003';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001004004';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001004005';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001005';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001005001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001005002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001005003';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001006';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001006001';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001006002';
UPDATE `sys_menu` SET `sys_module_id` = '4' WHERE `id` = '2001006003';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002001';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002002';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002002001';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002002002';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002002003';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002002004';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002002005';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002002006';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002003';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002003001';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002003002';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002003003';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002003004';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002003005';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002003006';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002004';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002004001';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002004002';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002004003';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002004004';
UPDATE `sys_menu` SET `sys_module_id` = '5' WHERE `id` = '2002004005';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003001';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003002';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003002001';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003002002';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003002003';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003002004';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003002005';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003003';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003003001';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003003002';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003003003';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003003004';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003003005';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003004';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003004001';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003004002';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003004003';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003004004';
UPDATE `sys_menu` SET `sys_module_id` = '6' WHERE `id` = '2003004005';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004001';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004002';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004002001';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004002002';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004002003';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004002004';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004002005';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004003';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004003001';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004003002';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004003003';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004003004';
UPDATE `sys_menu` SET `sys_module_id` = '7' WHERE `id` = '2004003005';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000001';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000001001';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000003';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000003001';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004001';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004002';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004002001';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004002002';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004002003';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004002004';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003001';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003002';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003003';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003004';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003005';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003006';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004003007';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004004';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004004001';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004004002';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004004003';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004004004';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004004005';
UPDATE `sys_menu` SET `sys_module_id` = '9' WHERE `id` = '3000004004006';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005001';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005001001';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005001002';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005001003';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005001004';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005001005';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005002';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005002001';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005002002';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005003';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005003001';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005003002';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005003003';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005003004';
UPDATE `sys_menu` SET `sys_module_id` = '10' WHERE `id` = '3000005003005';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000006';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000006001';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000006002';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000006003';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000006004';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000006005';
UPDATE `sys_menu` SET `sys_module_id` = '8' WHERE `id` = '3000006006';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000001001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000001002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000001003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000002001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000002002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000002003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000003001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000003002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000003003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000003004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000003005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000004001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000004002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000004003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000004004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000004005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000005001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000005002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000005003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000005004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000005005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000006';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000006001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000006002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000006003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000006004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000006005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000007';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000008';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000009';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000009001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000009002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000009003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000009004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000009005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000010';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000010001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000010002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000010003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000010004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000010005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000011';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000011001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000011002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000011003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000011004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000011005';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000012';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000012001';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000012002';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000012003';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000012004';
UPDATE `sys_menu` SET `sys_module_id` = '11' WHERE `id` = '4000012005';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000001';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000002';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000003';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000004';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000005';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000006';
UPDATE `sys_menu` SET `sys_module_id` = '12' WHERE `id` = '9000007';
UPDATE `sys_menu` SET `sys_module_id` = '13' WHERE `id` = '9001';
UPDATE `sys_menu` SET `sys_module_id` = '13' WHERE `id` = '9001001';
UPDATE `sys_menu` SET `sys_module_id` = '13' WHERE `id` = '9001002';
UPDATE `sys_menu` SET `title` = '库存数量调整' WHERE `id` = '3000005003';
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1000011003', '1000011003', '', '授权模块', NULL, 0, '', NULL, '1000011', '1', '', 0, 2, 0, 'system:tenant:module', 0, 1, '', '系统管理员', '1', '2022-08-18 14:31:12', '系统管理员', '1', '2022-08-18 14:31:12');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1001', '1001', 'Platform', '平台管理', 'a-menu', NULL, '', NULL, '0001', '1', '/platform', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
UPDATE `sys_menu` SET `parent_id` = '1001' WHERE `id` = '1000011';
UPDATE `sys_menu` SET `parent_id` = '1001' WHERE `id` = '1000012';

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000', '5000', 'Logistics', '物流管理', 'a-menu', NULL, '', NULL, '0001', '15', '/logistics', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001', '5000001', 'LogisticsSheet', '物流单管理', NULL, 0, '/sc/logistics/sheet/index', NULL, '5000', '15', '/sheet', 0, 1, 0, 'logistics:sheet:query', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001001', '5000001001', '', '新增物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:add', 1, 1, '', '系统管理员', '1', '2021-05-12 22:50:27', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001002', '5000001002', '', '修改物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001003', '5000001003', '', '删除物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001004', '5000001004', '', '物流单发货', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:delivery', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001005', '5000001005', '', '导入物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('5000001006', '5000001006', '', '导出物流单', NULL, 0, '', NULL, '5000001', '15', '', 0, 2, 0, 'logistics:sheet:export', 1, 1, '', '系统管理员', '1', '2021-05-12 23:24:36', '系统管理员', '1', '2021-07-04 00:34:23');
ALTER TABLE `tbl_sale_config`
ADD COLUMN `out_stock_require_logistics` tinyint(1) NOT NULL DEFAULT 0 COMMENT '销售出库单是否需要物流单' AFTER `sale_return_multiple_relate_out_stock`;
ALTER TABLE `tbl_retail_config`
ADD COLUMN `retail_out_sheet_require_logistics` tinyint(1) NOT NULL DEFAULT 0 COMMENT '零售出库单是否需要发货' AFTER `retail_return_require_member`;

DROP TABLE IF EXISTS `base_data_logistics_company`;
CREATE TABLE `base_data_logistics_company` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(20) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系人',
  `telephone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `city_id` varchar(32) DEFAULT NULL COMMENT '地区ID',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `available` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流公司';

DROP TABLE IF EXISTS `tbl_logistics_sheet`;
CREATE TABLE `tbl_logistics_sheet` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `code` varchar(32) NOT NULL COMMENT '业务单据号',
  `logistics_no` varchar(100) DEFAULT NULL COMMENT '物流单号',
  `logistics_company_id` varchar(32) NOT NULL COMMENT '物流公司ID',
  `sender_name` varchar(100) NOT NULL COMMENT '寄件人姓名',
  `sender_telephone` varchar(20) NOT NULL COMMENT '寄件人联系电话',
  `sender_province_id` varchar(32) NOT NULL COMMENT '寄件人省',
  `sender_city_id` varchar(32) NOT NULL COMMENT '寄件人市',
  `sender_district_id` varchar(32) NOT NULL COMMENT '寄件人区',
  `sender_address` varchar(200) NOT NULL COMMENT '寄件人地址',
  `receiver_name` varchar(100) NOT NULL COMMENT '收件人姓名',
  `receiver_telephone` varchar(20) NOT NULL COMMENT '收件人联系电话',
  `receiver_province_id` varchar(32) NOT NULL COMMENT '收件人省',
  `receiver_city_id` varchar(32) NOT NULL COMMENT '收件人市',
  `receiver_district_id` varchar(32) NOT NULL COMMENT '收件人区',
  `receiver_address` varchar(200) NOT NULL COMMENT '收件人地址',
  `total_weight` decimal(16,2) DEFAULT NULL COMMENT '总重量（kg）',
  `total_volume` decimal(16,2) DEFAULT NULL COMMENT '总体积（cm3）',
  `total_amount` decimal(16,2) DEFAULT NULL COMMENT '物流费',
  `status` tinyint(3) NOT NULL COMMENT '状态',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `delivery_by` varchar(32) DEFAULT NULL COMMENT '发货人',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `logistics_no` (`logistics_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流单';

DROP TABLE IF EXISTS `tbl_logistics_sheet_detail`;
CREATE TABLE `tbl_logistics_sheet_detail` (
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `sheet_id` varchar(32) NOT NULL COMMENT '物流单ID',
  `biz_id` varchar(32) NOT NULL COMMENT '业务单据ID',
  `biz_type` tinyint(3) NOT NULL COMMENT '业务类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY ```biz_id``` (`biz_id`,`biz_type`) USING BTREE,
  KEY `sheet_id` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流单明细';