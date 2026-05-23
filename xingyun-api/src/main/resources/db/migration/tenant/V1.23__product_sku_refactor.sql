INSERT INTO base_data_product_category_property (id, property_id, category_id)
SELECT REPLACE(UUID(), '-', ''), t.property_id, t.category_id
FROM (
         SELECT DISTINCT cp.property_id, leaf.id AS category_id
         FROM base_data_product_category_property cp
                  INNER JOIN base_data_product_category bound_category ON bound_category.id = cp.category_id
                  INNER JOIN recursion_mapping rm ON FIND_IN_SET(cp.category_id, rm.path) AND rm.node_type = 2
                  INNER JOIN base_data_product_category leaf ON leaf.id = rm.node_id AND leaf.available = TRUE
                  LEFT JOIN base_data_product_category child ON child.parent_id = leaf.id AND child.available = TRUE
                  LEFT JOIN base_data_product_category_property exists_cp ON exists_cp.property_id = cp.property_id
             AND exists_cp.category_id = leaf.id
         WHERE bound_category.available = TRUE
           AND child.id IS NULL
           AND exists_cp.id IS NULL
     ) t;

INSERT INTO base_data_product_category_property (id, property_id, category_id)
SELECT REPLACE(UUID(), '-', ''), p.id, c.id
FROM base_data_product_property p
         INNER JOIN base_data_product_category c ON c.available = TRUE
         LEFT JOIN base_data_product_category child ON child.parent_id = c.id AND child.available = TRUE
         LEFT JOIN base_data_product_category_property cp ON cp.property_id = p.id
    AND cp.category_id = c.id
WHERE p.available = TRUE
  AND p.property_type = 1
  AND child.id IS NULL
  AND cp.id IS NULL;

DELETE cp
FROM base_data_product_category_property cp
INNER JOIN base_data_product_category c ON c.id = cp.category_id
INNER JOIN base_data_product_category child ON child.parent_id = c.id AND child.available = TRUE;

UPDATE base_data_product_property
SET property_type = 2,
    column_data_type = CASE WHEN column_type = 3 THEN 3 ELSE NULL END
WHERE available = TRUE;

UPDATE `sys_menu`
SET `title` = '商品分类属性'
WHERE `id` = '2001004';

UPDATE `sys_menu`
SET `title` = '新增商品分类属性'
WHERE `id` = '2001004001';

UPDATE `sys_menu`
SET `title` = '修改商品分类属性'
WHERE `id` = '2001004002';

UPDATE `sys_menu`
SET `title` = '查询分类属性值'
WHERE `id` = '2001004003';

UPDATE `sys_menu`
SET `title` = '新增分类属性值'
WHERE `id` = '2001004004';

UPDATE `sys_menu`
SET `title` = '修改分类属性值'
WHERE `id` = '2001004005';

UPDATE `sys_menu`
SET `title` = '删除商品分类属性'
WHERE `id` = '2001004006';

UPDATE `sys_menu`
SET `title` = '删除分类属性值'
WHERE `id` = '2001004007';
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

CREATE TABLE `base_data_product_sku`
(
    `id`           varchar(32) NOT NULL COMMENT 'ID',
    `product_id`   varchar(32) NOT NULL COMMENT '商品ID',
    `code`         varchar(20) NOT NULL COMMENT 'SKU编号',
    `multi_code`   tinyint(1)  NOT NULL DEFAULT 0 COMMENT '是否一品多码',
    `available`    tinyint(1)  NOT NULL COMMENT '状态',
    `create_by`    varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time`  datetime    NOT NULL COMMENT '创建时间',
    `update_by`    varchar(32) NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
    `update_time`  datetime    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `product_id` (`product_id`),
    KEY `code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品SKU';

CREATE TABLE `base_data_product_sku_code`
(
    `id`        varchar(32) NOT NULL COMMENT 'ID',
    `sku_id`    varchar(32) NOT NULL COMMENT 'SKU ID',
    `code`      varchar(20) NOT NULL COMMENT '编号',
    `is_main`   tinyint(1)  NOT NULL DEFAULT 0 COMMENT '是否主编号',
    `code_type` tinyint(3)  NOT NULL COMMENT '编号类型',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sku_id_code_type` (`sku_id`, `code`, `code_type`),
    KEY `code` (`code`),
    KEY `sku_id_code_type_idx` (`sku_id`, `code_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='SKU检索码';

CREATE TABLE `base_data_product_sku_purchase`
(
    `id`    varchar(32)    NOT NULL COMMENT 'SKU ID',
    `price` decimal(24, 6) NOT NULL COMMENT '采购价',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='SKU采购价';

CREATE TABLE `base_data_product_sku_sale`
(
    `id`    varchar(32)    NOT NULL COMMENT 'SKU ID',
    `price` decimal(24, 6) NOT NULL COMMENT '销售价',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='SKU销售价';

CREATE TABLE `base_data_product_sku_retail`
(
    `id`    varchar(32)    NOT NULL COMMENT 'SKU ID',
    `price` decimal(24, 6) NOT NULL COMMENT '零售价',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='SKU零售价';

CREATE TABLE `base_data_product_sku_sale_property_relation`
(
    `id`               varchar(32) NOT NULL COMMENT 'ID',
    `sku_id`           varchar(32) NOT NULL COMMENT 'SKU ID',
    `property_id`      varchar(32) NOT NULL COMMENT '销售属性ID',
    `property_item_id` varchar(32) NOT NULL COMMENT '销售属性值ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `sku_id_property_id` (`sku_id`, `property_id`),
    KEY `property_item_id` (`property_item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='SKU销售属性关系';

CREATE TABLE `base_data_product_sku_bundle`
(
    `id`             varchar(32)    NOT NULL COMMENT 'ID',
    `main_sku_id`    varchar(32)    NOT NULL COMMENT '组合SKU ID',
    `sku_id`         varchar(32)    NOT NULL COMMENT '单品SKU ID',
    `bundle_num`     int(11)        NOT NULL COMMENT '包含数量',
    `purchase_price` decimal(24, 6) NOT NULL COMMENT '采购价',
    `sale_price`     decimal(24, 6) NOT NULL COMMENT '销售价',
    `retail_price`   decimal(24, 6) NOT NULL COMMENT '零售价',
    `create_by`      varchar(32)    NOT NULL COMMENT '创建人',
    `create_by_id`   varchar(32)    NOT NULL COMMENT '创建人ID',
    `create_time`    datetime       NOT NULL COMMENT '创建时间',
    `update_by`      varchar(32)    NOT NULL COMMENT '修改人',
    `update_by_id`   varchar(32)    NOT NULL COMMENT '修改人ID',
    `update_time`    datetime       NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `main_sku_id` (`main_sku_id`, `sku_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组合商品SKU';

INSERT INTO `base_data_product_sku` (`id`, `product_id`, `code`, `multi_code`, `available`,
                                     `create_by`, `create_by_id`, `create_time`, `update_by`,
                                     `update_by_id`, `update_time`)
SELECT `id`, `id`, `code`, `multi_code`, `available`, `create_by`, `create_by_id`, `create_time`,
       `update_by`, `update_by_id`, `update_time`
FROM `base_data_product`;

INSERT INTO `base_data_product_sku_code` (`id`, `sku_id`, `code`, `is_main`, `code_type`)
SELECT `id`, `product_id`, `code`, `is_main`, IF(`is_main` = 1, 1, 3)
FROM `base_data_product_code`;

INSERT INTO `base_data_product_sku_purchase` (`id`, `price`)
SELECT `id`, `price`
FROM `base_data_product_purchase`;

INSERT INTO `base_data_product_sku_sale` (`id`, `price`)
SELECT `id`, `price`
FROM `base_data_product_sale`;

INSERT INTO `base_data_product_sku_retail` (`id`, `price`)
SELECT `id`, `price`
FROM `base_data_product_retail`;

INSERT INTO `base_data_product_sku_bundle` (`id`, `main_sku_id`, `sku_id`, `bundle_num`,
                                            `purchase_price`, `sale_price`, `retail_price`,
                                            `create_by`, `create_by_id`, `create_time`,
                                            `update_by`, `update_by_id`, `update_time`)
SELECT `id`, `main_product_id`, `product_id`, `bundle_num`, `purchase_price`, `sale_price`,
       `retail_price`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`,
       `update_time`
FROM `base_data_product_bundle`;

ALTER TABLE `base_data_product`
    MODIFY COLUMN `code` varchar(20) NULL DEFAULT NULL COMMENT '商品编号',
    ADD COLUMN `sku_type` tinyint(3) NOT NULL DEFAULT 1 COMMENT 'SKU类型' AFTER `product_type`,
    ADD COLUMN `detail_images` longtext NULL COMMENT '详情图片' AFTER `sale_tax_rate`;

UPDATE `base_data_product`
SET `code` = NULL,
    `sku_type` = 1;

ALTER TABLE `base_data_product`
    DROP COLUMN `multi_code`;

-- 库存、仓位、盘点和单据表新增 SKU 字段；旧数据 sku_id 直接回填旧 product_id。
ALTER TABLE `tbl_pre_take_stock_sheet_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_pre_take_stock_sheet_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_pre_take_stock_sheet_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    DROP INDEX `sheet_id`,
    ADD UNIQUE KEY `sheet_id_sku_id` (`sheet_id`, `sku_id`),
    ADD KEY `product_id` (`product_id`);

ALTER TABLE `tbl_product_stock`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_product_stock` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_product_stock`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    DROP INDEX `product_id`,
    ADD UNIQUE KEY `sku_id` (`sku_id`, `sc_id`),
    ADD KEY `product_id` (`product_id`);

ALTER TABLE `tbl_product_stock_log`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_product_stock_log` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_product_stock_log`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_product_stock_warning`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_product_stock_warning` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_product_stock_warning`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    DROP INDEX `sc_id`,
    ADD UNIQUE KEY `sc_id_sku_id` (`sc_id`, `sku_id`),
    ADD KEY `product_id` (`product_id`);

ALTER TABLE `tbl_purchase_order_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_purchase_order_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_purchase_order_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_purchase_order_detail_form`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_purchase_order_detail_form` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_purchase_order_detail_form`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_purchase_order_detail_bundle`
    ADD COLUMN `main_sku_id` varchar(32) NULL COMMENT '组合SKU ID' AFTER `main_product_id`,
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_purchase_order_detail_bundle` SET `main_sku_id` = `main_product_id`, `sku_id` = `product_id`;
ALTER TABLE `tbl_purchase_order_detail_bundle`
    MODIFY COLUMN `main_sku_id` varchar(32) NOT NULL COMMENT '组合SKU ID',
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID';

ALTER TABLE `tbl_purchase_order_detail_bundle_form`
    ADD COLUMN `main_sku_id` varchar(32) NULL COMMENT '组合SKU ID' AFTER `main_product_id`,
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_purchase_order_detail_bundle_form` SET `main_sku_id` = `main_product_id`, `sku_id` = `product_id`;
ALTER TABLE `tbl_purchase_order_detail_bundle_form`
    MODIFY COLUMN `main_sku_id` varchar(32) NOT NULL COMMENT '组合SKU ID',
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID';

ALTER TABLE `tbl_purchase_return_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_purchase_return_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_purchase_return_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_receive_sheet_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_receive_sheet_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_receive_sheet_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_receive_sheet_detail_bundle`
    ADD COLUMN `main_sku_id` varchar(32) NULL COMMENT '组合SKU ID' AFTER `main_product_id`,
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_receive_sheet_detail_bundle` SET `main_sku_id` = `main_product_id`, `sku_id` = `product_id`;
ALTER TABLE `tbl_receive_sheet_detail_bundle`
    MODIFY COLUMN `main_sku_id` varchar(32) NOT NULL COMMENT '组合SKU ID',
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID';

ALTER TABLE `tbl_retail_out_sheet_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_retail_out_sheet_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_retail_out_sheet_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_retail_out_sheet_detail_bundle`
    ADD COLUMN `main_sku_id` varchar(32) NULL COMMENT '组合SKU ID' AFTER `main_product_id`,
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_retail_out_sheet_detail_bundle` SET `main_sku_id` = `main_product_id`, `sku_id` = `product_id`;
ALTER TABLE `tbl_retail_out_sheet_detail_bundle`
    MODIFY COLUMN `main_sku_id` varchar(32) NOT NULL COMMENT '组合SKU ID',
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID';

ALTER TABLE `tbl_retail_return_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_retail_return_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_retail_return_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_sale_order_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_sale_order_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_sale_order_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_sale_order_detail_bundle`
    ADD COLUMN `main_sku_id` varchar(32) NULL COMMENT '组合SKU ID' AFTER `main_product_id`,
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_sale_order_detail_bundle` SET `main_sku_id` = `main_product_id`, `sku_id` = `product_id`;
ALTER TABLE `tbl_sale_order_detail_bundle`
    MODIFY COLUMN `main_sku_id` varchar(32) NOT NULL COMMENT '组合SKU ID',
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID';

ALTER TABLE `tbl_sale_out_sheet_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_sale_out_sheet_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_sale_out_sheet_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_sale_out_sheet_detail_bundle`
    ADD COLUMN `main_sku_id` varchar(32) NULL COMMENT '组合SKU ID' AFTER `main_product_id`,
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_sale_out_sheet_detail_bundle` SET `main_sku_id` = `main_product_id`, `sku_id` = `product_id`;
ALTER TABLE `tbl_sale_out_sheet_detail_bundle`
    MODIFY COLUMN `main_sku_id` varchar(32) NOT NULL COMMENT '组合SKU ID',
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID';

ALTER TABLE `tbl_sale_return_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_sale_return_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_sale_return_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_sc_transfer_order_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_sc_transfer_order_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_sc_transfer_order_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_stock_adjust_sheet_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_stock_adjust_sheet_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_stock_adjust_sheet_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    DROP INDEX `sheet_id`,
    ADD UNIQUE KEY `sheet_id_sku_id` (`sheet_id`, `sku_id`),
    ADD KEY `product_id` (`product_id`);

ALTER TABLE `tbl_stock_cell_product`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_stock_cell_product` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_stock_cell_product`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    ADD KEY `sku_id` (`sku_id`);

ALTER TABLE `tbl_take_stock_plan_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_take_stock_plan_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_take_stock_plan_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    DROP INDEX `plan_id`,
    ADD UNIQUE KEY `plan_id_sku_id` (`plan_id`, `sku_id`),
    ADD KEY `product_id` (`product_id`);

ALTER TABLE `tbl_take_stock_sheet_detail`
    ADD COLUMN `sku_id` varchar(32) NULL COMMENT 'SKU ID' AFTER `product_id`;
UPDATE `tbl_take_stock_sheet_detail` SET `sku_id` = `product_id`;
ALTER TABLE `tbl_take_stock_sheet_detail`
    MODIFY COLUMN `sku_id` varchar(32) NOT NULL COMMENT 'SKU ID',
    DROP INDEX `sheet_id`,
    ADD UNIQUE KEY `sheet_id_sku_id` (`sheet_id`, `sku_id`),
    ADD KEY `product_id` (`product_id`);

DROP TABLE `base_data_product_code`;
DROP TABLE `base_data_product_purchase`;
DROP TABLE `base_data_product_sale`;
DROP TABLE `base_data_product_retail`;
DROP TABLE `base_data_product_bundle`;

ALTER TABLE `base_data_product_sku`
    ADD COLUMN `sale_property_text` varchar(1000) NOT NULL DEFAULT '' COMMENT '销售属性' AFTER `multi_code`;

UPDATE `base_data_product_sku` s
    LEFT JOIN (
    SELECT
    r.`sku_id`,
    GROUP_CONCAT(CONCAT(p.`name`, '：', i.`name`) ORDER BY p.`code` SEPARATOR ' / ') AS `sale_property_text`
    FROM `base_data_product_sku_sale_property_relation` r
    INNER JOIN `base_data_product_sale_property` p ON p.`id` = r.`property_id`
    INNER JOIN `base_data_product_sale_property_item` i ON i.`id` = r.`property_item_id`
    GROUP BY r.`sku_id`
    ) t ON t.`sku_id` = s.`id`
    SET s.`sale_property_text` = IFNULL(t.`sale_property_text`, '');

DELETE FROM `base_data_product_sku_code` WHERE `code_type` = 2;
ALTER TABLE `base_data_product`
    ADD COLUMN `main_image` longtext NULL COMMENT '商品主图' AFTER `detail_images`;

ALTER TABLE `base_data_product_sku`
    ADD COLUMN `main_image` longtext NULL COMMENT 'SKU主图' AFTER `sale_property_text`;

RENAME TABLE
    `base_data_product_property` TO `base_data_product_category_property_definition`,
    `base_data_product_property_item` TO `base_data_product_category_property_item`,
    `base_data_product_category_property` TO `base_data_product_category_property_relation`,
    `base_data_product_property_relation` TO `base_data_product_category_property_value_relation`,
    `base_data_product_sale_property` TO `base_data_product_sale_property_definition`,
    `base_data_product_category_sale_property` TO `base_data_product_category_sale_property_relation`;

INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`,
                        `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`,
                        `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                        `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`,
                        `update_time`)
VALUES ('2001004008', '2001004008', '', '导入商品分类属性', NULL, 0, '', NULL, '2001004', '4',
        '', 0, 2, 0, 'base-data:product:property:import', 1, 1, '', '系统管理员', '1', NOW(),
        '系统管理员', '1', NOW()),
       ('200100401008', '200100401008', '', '导入商品销售属性', NULL, 0, '', NULL, '200100401', '4',
        '', 0, 2, 0, 'base-data:product:sale-property:import', 1, 1, '', '系统管理员', '1', NOW(),
        '系统管理员', '1', NOW());

ALTER TABLE `sys_parameter`
    ADD COLUMN `is_encrypt` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否加密值' AFTER `pm_value`;

ALTER TABLE `sys_dept`
DROP INDEX `code`,
DROP INDEX `name`,
  ADD INDEX `code` (`code`) USING BTREE,
  ADD INDEX `name` (`name`) USING BTREE;

ALTER TABLE `base_data_product_sale_property_item`
DROP INDEX `property_id_code`,
  ADD INDEX `property_id_code` (`property_id`, `code`) USING BTREE;
