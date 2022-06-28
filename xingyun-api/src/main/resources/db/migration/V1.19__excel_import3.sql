INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('2000004003', '2000004003', '', '导入客户', '', '2000004', '', 0, 2, 0,
        'base-data:customer:import', 1, 1, '', '1', '2021-05-12 23:23:33', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('2000005003', '2000005003', '', '导入供应商', '', '2000005', '', 0, 2, 0,
        'base-data:supplier:import', 1, 1, '', '1', '2021-05-12 23:23:33', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('2000007003', '2000007003', '', '导入门店', '', '2000007', '', 0, 2, 0, 'base-data:shop:import',
        1, 1, '', '1', '2021-05-12 23:23:33', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('2001002003', '2001002003', '', '导入品牌', '', '2001002', '', 0, 2, 0,
        'base-data:product:brand:import', 1, 1, '', '1', '2021-05-12 23:23:33', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('2001001003', '2001001003', '', '导入类目', '', '2001001', '', 0, 2, 0,
        'base-data:product:category:import', 1, 1, '', '1', '2021-05-12 23:23:33', '1',
        '2021-07-04 00:34:23');

DROP TABLE IF EXISTS `base_data_product_poly_sale_prop_group`;
CREATE TABLE `base_data_product_poly_sale_prop_group`
(
    `id`                 varchar(32) NOT NULL COMMENT 'ID',
    `poly_id`            varchar(32) NOT NULL COMMENT '商品聚合ID',
    `sale_prop_group_id` varchar(32) NOT NULL COMMENT '销售属性组ID',
    `order_no`           int(11) NOT NULL COMMENT '排序',
    PRIMARY KEY (`id`),
    KEY                  `poly_id` (`poly_id`,`sale_prop_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SPU与销售属性组关系表';

INSERT INTO base_data_product_poly_sale_prop_group
SELECT r.id,
       p.poly_id,
       g.id AS sale_prop_group_id,
       r.order_no
FROM base_data_product_saleprop_item_relation AS r
         INNER JOIN base_data_product AS p ON p.id = r.product_id
         INNER JOIN base_data_product_saleprop_item AS i ON i.id = r.sale_prop_item_id
         INNER JOIN base_data_product_saleprop_group AS g ON g.id = i.group_id
GROUP BY p.poly_id,
         g.id;

INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('2001005003', '2001005003', '', '导入商品', '', '2001005', '', 0, 2, 0,
        'base-data:product:info:import', 1, 1, '', '1', '2021-05-12 23:23:33', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('2001006003', '2001006003', '', '导入商品SPU', '', '2001006', '', 0, 2, 0,
        'base-data:product:poly:import', 1, 1, '', '1', '2021-05-12 23:23:33', '1',
        '2021-07-04 00:34:23');