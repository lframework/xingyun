INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000007', '4000007', 'SupplierSettleManage', '供应商结算', '', '4000', '/supplier', 0, 0, 0, '',
        1, 1, '', '1', '2021-07-05 01:21:35', '1', '2021-07-05 01:21:39');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000008', '4000008', 'CustomerSettleManage', '客户结算', '', '4000', '/customer', 0, 0, 0, '',
        1, 1, '', '1', '2021-07-05 01:21:35', '1', '2021-07-05 01:21:39');
UPDATE sys_menu
SET parent_id = '4000007'
WHERE id IN ('4000003', '4000004', '4000005', '4000006');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000009', '4000009', 'CustomerSettleFeeSheet', '客户费用', '/customer-settle/fee-sheet/index',
        '4000008', '/fee-sheet', 0, 1, 0, 'customer-settle:fee-sheet:query', 1, 1, '', '1',
        '2021-07-05 21:59:35', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000009001', '4000009001', '', '新增客户费用单', '', '4000009', '', 0, 2, 0,
        'customer-settle:fee-sheet:add', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000009002', '4000009002', '', '修改客户费用单', '', '4000009', '', 0, 2, 0,
        'customer-settle:fee-sheet:modify', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000009003', '4000009003', '', '删除客户费用单', '', '4000009', '', 0, 2, 0,
        'customer-settle:fee-sheet:delete', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000009004', '4000009004', '', '审核客户费用单', '', '4000009', '', 0, 2, 0,
        'customer-settle:fee-sheet:approve', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000009005', '4000009005', '', '导出客户费用单', '', '4000009', '', 0, 2, 0,
        'customer-settle:fee-sheet:export', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000010', '4000010', 'CustomerSettlePreSheet', '客户预收款', '/customer-settle/pre-sheet/index',
        '4000008', '/pre-sheet', 0, 1, 0, 'customer-settle:pre-sheet:query', 1, 1, '', '1',
        '2021-07-05 21:59:35', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000010001', '4000010001', '', '新增客户预收款单', '', '4000010', '', 0, 2, 0,
        'customer-settle:pre-sheet:add', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000010002', '4000010002', '', '修改客户预收款单', '', '4000010', '', 0, 2, 0,
        'customer-settle:pre-sheet:modify', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000010003', '4000010003', '', '删除客户预收款单', '', '4000010', '', 0, 2, 0,
        'customer-settle:pre-sheet:delete', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000010004', '4000010004', '', '审核客户预收款单', '', '4000010', '', 0, 2, 0,
        'customer-settle:pre-sheet:approve', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000010005', '4000010005', '', '导出客户预收款单', '', '4000010', '', 0, 2, 0,
        'customer-settle:pre-sheet:export', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000011', '4000011', 'CustomerSettleCheckSheet', '客户对账',
        '/customer-settle/check-sheet/index', '4000008', '/check-sheet', 0, 1, 0,
        'customer-settle:check-sheet:query', 1, 1, '', '1', '2021-07-05 21:59:35', '1',
        '2021-07-05 21:59:36');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000011001', '4000011001', '', '新增客户对账单', '', '4000011', '', 0, 2, 0,
        'customer-settle:check-sheet:add', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000011002', '4000011002', '', '修改客户对账单', '', '4000011', '', 0, 2, 0,
        'customer-settle:check-sheet:modify', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000011003', '4000011003', '', '删除客户对账单', '', '4000011', '', 0, 2, 0,
        'customer-settle:check-sheet:delete', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000011004', '4000011004', '', '审核客户对账单', '', '4000011', '', 0, 2, 0,
        'customer-settle:check-sheet:approve', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000011005', '4000011005', '', '导出客户对账单', '', '4000011', '', 0, 2, 0,
        'customer-settle:check-sheet:export', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000012', '4000012', 'CustomerSettleSheet', '客户结算', '/customer-settle/sheet/index',
        '4000008', '/sheet', 0, 1, 0, 'customer-settle:sheet:query', 1, 1, '', '1',
        '2021-07-05 21:59:35', '1', '2021-07-05 21:59:36');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000012001', '4000012001', '', '新增客户结算单', '', '4000012', '', 0, 2, 0,
        'customer-settle:sheet:add', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000012002', '4000012002', '', '修改客户结算单', '', '4000012', '', 0, 2, 0,
        'customer-settle:sheet:modify', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000012003', '4000012003', '', '删除客户结算单', '', '4000012', '', 0, 2, 0,
        'customer-settle:sheet:delete', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000012004', '4000012004', '', '审核客户结算单', '', '4000012', '', 0, 2, 0,
        'customer-settle:sheet:approve', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');
INSERT INTO `sys_menu`(`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`,
                       `display`, `hidden`, `permission`, `is_special`, `available`, `description`,
                       `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('4000012005', '4000012005', '', '导出客户结算单', '', '4000012', '', 0, 2, 0,
        'customer-settle:sheet:export', 1, 1, '', '1', '2021-05-12 10:53:45', '1',
        '2021-07-04 00:34:23');

DROP TABLE IF EXISTS `customer_settle_check_sheet`;
CREATE TABLE `customer_settle_check_sheet`
(
    `id`                    varchar(32)    NOT NULL,
    `code`                  varchar(32)    NOT NULL COMMENT '单号',
    `customer_id`           varchar(32)    NOT NULL COMMENT '客户ID',
    `total_amount`          decimal(24, 2) NOT NULL COMMENT '总金额',
    `total_pay_amount`      decimal(24, 2) NOT NULL COMMENT '应付金额',
    `total_payed_amount`    decimal(24, 2) NOT NULL COMMENT '已付金额',
    `total_discount_amount` decimal(24, 2) NOT NULL COMMENT '已优惠金额',
    `start_date`            date           NOT NULL COMMENT '起始日期',
    `end_date`              date           NOT NULL COMMENT '截止日期',
    `description`           varchar(200) DEFAULT NULL COMMENT '备注',
    `create_by`             varchar(32)    NOT NULL COMMENT '创建人',
    `create_time`           datetime       NOT NULL COMMENT '创建时间',
    `update_by`             varchar(32)    NOT NULL COMMENT '修改人',
    `update_time`           datetime       NOT NULL COMMENT '修改时间',
    `approve_by`            varchar(32)  DEFAULT NULL COMMENT '审核人',
    `approve_time`          datetime     DEFAULT NULL COMMENT '审核时间',
    `status`                tinyint(3) NOT NULL COMMENT '状态',
    `refuse_reason`         varchar(200) DEFAULT NULL COMMENT '拒绝原因',
    `settle_status`         tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`) USING BTREE,
    KEY                     `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户对账单';

DROP TABLE IF EXISTS `customer_settle_check_sheet_detail`;
CREATE TABLE `customer_settle_check_sheet_detail`
(
    `id`          varchar(32)    NOT NULL COMMENT 'ID',
    `sheet_id`    varchar(32)    NOT NULL COMMENT '对账单ID',
    `biz_id`      varchar(32)    NOT NULL COMMENT '单据ID',
    `biz_type`    tinyint(3) NOT NULL COMMENT '业务类型',
    `calc_type`   tinyint(3) NOT NULL COMMENT '计算类型',
    `pay_amount`  decimal(24, 2) NOT NULL COMMENT '应付金额',
    `description` varchar(200) DEFAULT NULL COMMENT '备注',
    `order_no`    int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sheet_id` (`sheet_id`,`biz_id`) USING BTREE,
    KEY           `biz_id` (`biz_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户对账单明细';

DROP TABLE IF EXISTS `customer_settle_fee_sheet`;
CREATE TABLE `customer_settle_fee_sheet`
(
    `id`            varchar(32)    NOT NULL,
    `code`          varchar(32)    NOT NULL COMMENT '单号',
    `customer_id`   varchar(32)    NOT NULL COMMENT '客户ID',
    `sheet_type`    tinyint(3) NOT NULL COMMENT '单据类型',
    `total_amount`  decimal(24, 2) NOT NULL COMMENT '总金额',
    `description`   varchar(200) DEFAULT NULL COMMENT '备注',
    `create_by`     varchar(32)    NOT NULL COMMENT '创建人',
    `create_time`   datetime       NOT NULL COMMENT '创建时间',
    `update_by`     varchar(32)    NOT NULL COMMENT '修改人',
    `update_time`   datetime       NOT NULL COMMENT '修改时间',
    `approve_by`    varchar(32)  DEFAULT NULL COMMENT '审核人',
    `approve_time`  datetime     DEFAULT NULL COMMENT '审核时间',
    `status`        tinyint(3) NOT NULL COMMENT '状态',
    `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
    `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`) USING BTREE,
    KEY             `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户费用单';

DROP TABLE IF EXISTS `customer_settle_fee_sheet_detail`;
CREATE TABLE `customer_settle_fee_sheet_detail`
(
    `id`       varchar(32)    NOT NULL COMMENT 'ID',
    `sheet_id` varchar(32)    NOT NULL COMMENT '费用单ID',
    `item_id`  varchar(32)    NOT NULL COMMENT '项目ID',
    `amount`   decimal(24, 2) NOT NULL COMMENT '金额',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sheet_id` (`sheet_id`,`item_id`) USING BTREE,
    KEY        `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户费用单明细';

DROP TABLE IF EXISTS `customer_settle_pre_sheet`;
CREATE TABLE `customer_settle_pre_sheet`
(
    `id`            varchar(32)    NOT NULL,
    `code`          varchar(32)    NOT NULL COMMENT '单号',
    `customer_id`   varchar(32)    NOT NULL COMMENT '客户ID',
    `total_amount`  decimal(24, 2) NOT NULL COMMENT '总金额',
    `description`   varchar(200) DEFAULT NULL COMMENT '备注',
    `create_by`     varchar(32)    NOT NULL COMMENT '创建人',
    `create_time`   datetime       NOT NULL COMMENT '创建时间',
    `update_by`     varchar(32)    NOT NULL COMMENT '修改人',
    `update_time`   datetime       NOT NULL COMMENT '修改时间',
    `approve_by`    varchar(32)  DEFAULT NULL COMMENT '审核人',
    `approve_time`  datetime     DEFAULT NULL COMMENT '审核时间',
    `status`        tinyint(3) NOT NULL COMMENT '状态',
    `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
    `settle_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '结算状态',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`) USING BTREE,
    KEY             `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户预付款单';

DROP TABLE IF EXISTS `customer_settle_pre_sheet_detail`;
CREATE TABLE `customer_settle_pre_sheet_detail`
(
    `id`       varchar(32)    NOT NULL COMMENT 'ID',
    `sheet_id` varchar(32)    NOT NULL COMMENT '预付款单ID',
    `item_id`  varchar(32)    NOT NULL COMMENT '项目ID',
    `amount`   decimal(24, 2) NOT NULL COMMENT '金额',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sheet_id` (`sheet_id`,`item_id`) USING BTREE,
    KEY        `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户预付款单明细';

DROP TABLE IF EXISTS `customer_settle_sheet`;
CREATE TABLE `customer_settle_sheet`
(
    `id`                    varchar(32)    NOT NULL,
    `code`                  varchar(32)    NOT NULL COMMENT '单号',
    `customer_id`           varchar(32)    NOT NULL COMMENT '客户ID',
    `total_amount`          decimal(24, 2) NOT NULL COMMENT '总金额',
    `total_discount_amount` decimal(24, 2) NOT NULL COMMENT '已优惠金额',
    `start_date`            date           NOT NULL COMMENT '起始日期',
    `end_date`              date           NOT NULL COMMENT '截止日期',
    `description`           varchar(200) DEFAULT NULL COMMENT '备注',
    `create_by`             varchar(32)    NOT NULL COMMENT '创建人',
    `create_time`           datetime       NOT NULL COMMENT '创建时间',
    `update_by`             varchar(32)    NOT NULL COMMENT '修改人',
    `update_time`           datetime       NOT NULL COMMENT '修改时间',
    `approve_by`            varchar(32)  DEFAULT NULL COMMENT '审核人',
    `approve_time`          datetime     DEFAULT NULL COMMENT '审核时间',
    `status`                tinyint(3) NOT NULL COMMENT '状态',
    `refuse_reason`         varchar(200) DEFAULT NULL COMMENT '拒绝原因',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`) USING BTREE,
    KEY                     `customer_id` (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户结算单';

DROP TABLE IF EXISTS `customer_settle_sheet_detail`;
CREATE TABLE `customer_settle_sheet_detail`
(
    `id`              varchar(32)    NOT NULL COMMENT 'ID',
    `sheet_id`        varchar(32)    NOT NULL COMMENT '结算单ID',
    `biz_id`          varchar(32)    NOT NULL COMMENT '单据ID',
    `pay_amount`      decimal(24, 2) NOT NULL COMMENT '实付金额',
    `discount_amount` decimal(24, 2) NOT NULL COMMENT '优惠金额',
    `description`     varchar(200) DEFAULT NULL COMMENT '备注',
    `order_no`        int(11) NOT NULL COMMENT '排序编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `sheet_id` (`sheet_id`,`biz_id`) USING BTREE,
    KEY               `biz_id` (`biz_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户结算单明细';