CREATE TABLE `base_data_product_code` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `product_id` varchar(32) NOT NULL COMMENT '商品ID',
    `code` varchar(20) NOT NULL COMMENT '编号',
    `is_main` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否主编号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `product_id` (`product_id`,`code`),
    KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品编号';

ALTER TABLE `base_data_product`
    ADD COLUMN `multi_code` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否一品多码' AFTER `code`;
INSERT INTO base_data_product_code (id, product_id, code, is_main)
SELECT code as id, id as product_id,code as code,true as is_main from base_data_product
where available = TRUE;


INSERT IGNORE INTO base_data_product_code (id, product_id, code, is_main)

select
    t.code AS id,
    t.product_id as product_id,
    t.code AS code,
    false as is_main
from (
     SELECT
         sku_code as code,
         id as product_id
     FROM
         base_data_product
     WHERE
         available = TRUE
       AND sku_code IS NOT NULL
       AND sku_code != ''

     union

     SELECT
         external_code as code,
         id as product_id
     FROM
         base_data_product
     WHERE
         available = TRUE
       AND external_code IS NOT NULL
       AND external_code != ''
 ) t;

ALTER TABLE `base_data_product`
DROP COLUMN `sku_code`,
DROP COLUMN `external_code`;

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002005', '2000002005', '', '仓位查询', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:stock-cell:query', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002006', '2000002006', '', '新增仓位', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:stock-cell:add', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002007', '2000002007', '', '修改仓位', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:stock-cell:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002008', '2000002008', '', '删除仓位', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:stock-cell:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2000002009', '2000002009', '', '导入仓位', NULL, 0, '', NULL, '2000002', '3', '', 0, 2, 0, 'base-data:stock-cell:import', 1, 1, '', '系统管理员', '1', '2021-05-12 23:23:33', '系统管理员', '1', '2021-07-04 00:34:23');

CREATE TABLE `base_data_stock_cell` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
    `code` varchar(20) NOT NULL COMMENT '编号',
    `name` varchar(20) NOT NULL COMMENT '名称',
    `cell_type` tinyint(3) NOT NULL DEFAULT '1' COMMENT '仓位类别',
    `available` tinyint(1) NOT NULL COMMENT '状态',
    `description` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by` varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` varchar(32) NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `code` (`code`) USING BTREE,
    KEY `name` (`name`),
    KEY `sc_id` (`sc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='仓位';

INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES (12, '仓位编号', '[{\"type\":\"6\",\"val\":\"C\"},{\"type\":\"1\",\"pattern\":\"yyMMdd\"},{\"type\":\"3\",\"key\":\"c31d11cdad371340a9985a110f8dad7cb9ea\",\"len\":\"5\",\"step\":1,\"expireType\":0,\"expireSeconds\":86400}]');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006', '2001006', 'StockCellProduct', '仓位商品管理', NULL, 0, '/base-data/stock-cell-product/index', NULL, '2001', '4', '/stock-cell-product', 0, 1, 0, 'base-data:stock-cell-product:query', 1, 1, '', '系统管理员', '1', '2021-07-06 17:01:00', '系统管理员', '1', '2021-07-06 17:01:00');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006001', '2001006001', '', '新增仓位商品', NULL, 0, '', NULL, '2001006', '4', '', 0, 2, 0, 'base-data:stock-cell-product:add', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006002', '2001006002', '', '修改仓位商品', NULL, 0, '', NULL, '2001006', '4', '', 0, 2, 0, 'base-data:stock-cell-product:modify', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006003', '2001006003', '', '删除仓位商品', NULL, 0, '', NULL, '2001006', '4', '', 0, 2, 0, 'base-data:stock-cell-product:delete', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('2001006004', '2001006004', '', '导入仓位商品', NULL, 0, '', NULL, '2001006', '4', '', 0, 2, 0, 'base-data:stock-cell-product:import', 1, 1, '', '系统管理员', '1', '2021-05-12 10:53:45', '系统管理员', '1', '2021-07-04 00:34:23');

CREATE TABLE `tbl_stock_cell_product` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
    `stock_cell_id` varchar(32) NOT NULL COMMENT '仓位ID',
    `product_id` varchar(32) NOT NULL COMMENT '商品ID',
    `create_by` varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `sc_id` (`sc_id`),
    KEY `stock_cell_id` (`stock_cell_id`),
    KEY `product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓位商品';

CREATE TABLE `sys_user_menu_sort` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `user_id` varchar(32) NOT NULL COMMENT '用户ID',
    `menu_id` varchar(32) NOT NULL COMMENT '菜单ID',
    `parent_id` varchar(32) DEFAULT NULL COMMENT '父级菜单ID',
    `sort_no` int(11) NOT NULL COMMENT '排序',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`),
    KEY `menu_id` (`menu_id`),
    KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;