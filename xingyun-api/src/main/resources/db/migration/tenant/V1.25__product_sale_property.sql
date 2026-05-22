CREATE TABLE `base_data_product_sale_property`
(
    `id`           varchar(32)  NOT NULL COMMENT 'ID',
    `code`         varchar(20)  NOT NULL COMMENT '编号',
    `name`         varchar(20)  NOT NULL COMMENT '名称',
    `available`    tinyint(1)   NOT NULL COMMENT '状态',
    `description`  varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`    varchar(32)  NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32)  NOT NULL COMMENT '创建人ID',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `update_by`    varchar(32)  NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32)  NOT NULL COMMENT '修改人ID',
    `update_time`  datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品销售属性';

CREATE TABLE `base_data_product_sale_property_item`
(
    `id`           varchar(32)  NOT NULL COMMENT 'ID',
    `code`         varchar(20)  NOT NULL COMMENT '编号',
    `name`         varchar(20)  NOT NULL COMMENT '名称',
    `property_id`  varchar(32)  NOT NULL COMMENT '销售属性ID',
    `available`    tinyint(1)   NOT NULL COMMENT '状态',
    `description`  varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
    `create_by`    varchar(32)  NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32)  NOT NULL COMMENT '创建人ID',
    `create_time`  datetime     NOT NULL COMMENT '创建时间',
    `update_by`    varchar(32)  NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32)  NOT NULL COMMENT '修改人ID',
    `update_time`  datetime     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `property_id_code` (`property_id`, `code`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='销售属性值';

CREATE TABLE `base_data_product_category_sale_property`
(
    `id`          varchar(32) NOT NULL COMMENT 'ID',
    `property_id` varchar(32) NOT NULL COMMENT '销售属性ID',
    `category_id` varchar(32) NOT NULL COMMENT '商品分类ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `property_id` (`property_id`, `category_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品分类和销售属性关系表';

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`,
                        `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`,
                        `update_time`)
VALUES ('200100401', '200100401', 'ProductSaleProperty', '商品销售属性', NULL, 0,
        '/base-data/product/sale-property/index', NULL, '2001', '4', '/sale-property', 0, 1, 0,
        'base-data:product:sale-property:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员',
        '1', NOW());

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`,
                        `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`,
                        `update_time`)
VALUES ('200100401001', '200100401001', '', '新增商品销售属性', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property:add', 1, 1, '', '系统管理员', '1', NOW(),
        '系统管理员', '1', NOW()),
       ('200100401002', '200100401002', '', '修改商品销售属性', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property:modify', 1, 1, '', '系统管理员', '1', NOW(),
        '系统管理员', '1', NOW()),
       ('200100401003', '200100401003', '', '删除商品销售属性', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property:delete', 1, 1, '', '系统管理员', '1', NOW(),
        '系统管理员', '1', NOW()),
       ('200100401004', '200100401004', '', '查询销售属性值', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property-item:query', 1, 1, '', '系统管理员', '1',
        NOW(), '系统管理员', '1', NOW()),
       ('200100401005', '200100401005', '', '新增销售属性值', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property-item:add', 1, 1, '', '系统管理员', '1',
        NOW(), '系统管理员', '1', NOW()),
       ('200100401006', '200100401006', '', '修改销售属性值', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property-item:modify', 1, 1, '', '系统管理员', '1',
        NOW(), '系统管理员', '1', NOW()),
       ('200100401007', '200100401007', '', '删除销售属性值', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property-item:delete', 1, 1, '', '系统管理员', '1',
        NOW(), '系统管理员', '1', NOW());
