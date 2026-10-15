-- ----------------------------
-- 排产计划模块：生产工单、工单明细
-- 菜单 8000 段，sys_module_id 复用 produce 模块（id=4，根据实际调整）
-- ----------------------------

-- 生产工单主表
DROP TABLE IF EXISTS `tbl_production_order`;
CREATE TABLE `tbl_production_order` (
  `id`               varchar(32)    NOT NULL COMMENT 'ID',
  `code`             varchar(32)    NOT NULL COMMENT '工单编号',
  `bom_id`           varchar(32)    NOT NULL COMMENT 'BOM ID',
  `product_id`       varchar(32)    NOT NULL COMMENT '成品商品ID',
  `sku_id`           varchar(32)    NOT NULL COMMENT '成品SKU ID',
  `sc_id`            varchar(32)    NOT NULL COMMENT '入库仓库ID',
  `plan_num`         decimal(16,2)  NOT NULL COMMENT '计划生产数量',
  `actual_num`       decimal(16,2)  DEFAULT NULL COMMENT '实际生产数量',
  `plan_start_date`  date           NOT NULL COMMENT '计划开始日期',
  `plan_end_date`    date           NOT NULL COMMENT '计划完工日期',
  `actual_end_date`  date           DEFAULT NULL COMMENT '实际完工日期',
  `sale_order_id`    varchar(32)    DEFAULT NULL COMMENT '来源销售订单ID',
  `owner_id`         varchar(32)    DEFAULT NULL COMMENT '负责人ID',
  `status`           tinyint(3)     NOT NULL DEFAULT 0 COMMENT '状态（0-待排产 1-已排产 2-生产中 3-已完工 4-已取消）',
  `description`      varchar(500)   DEFAULT NULL COMMENT '备注',
  `create_by`        varchar(32)    NOT NULL COMMENT '创建人',
  `create_by_id`     varchar(32)    NOT NULL COMMENT '创建人ID',
  `create_time`      datetime       NOT NULL COMMENT '创建时间',
  `update_by`        varchar(32)    NOT NULL COMMENT '修改人',
  `update_by_id`     varchar(32)    NOT NULL COMMENT '修改人ID',
  `update_time`      datetime       NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_bom_id` (`bom_id`),
  KEY `idx_status` (`status`),
  KEY `idx_plan_start` (`plan_start_date`),
  KEY `idx_sale_order` (`sale_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产工单';

-- 生产工单明细（BOM子件快照）
DROP TABLE IF EXISTS `tbl_production_order_detail`;
CREATE TABLE `tbl_production_order_detail` (
  `id`          varchar(32)   NOT NULL COMMENT 'ID',
  `order_id`    varchar(32)   NOT NULL COMMENT '工单ID',
  `product_id`  varchar(32)   NOT NULL COMMENT '子件商品ID',
  `sku_id`      varchar(32)   NOT NULL COMMENT '子件SKU ID',
  `bom_num`     decimal(16,4) NOT NULL COMMENT 'BOM单位用量',
  `require_num` decimal(16,2) NOT NULL COMMENT '计划消耗数量',
  `order_no`    int           NOT NULL DEFAULT 1 COMMENT '排序号',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产工单明细';

-- 菜单：生产工单（追加到 produce 模块菜单下，parent 8000 = 生产管理目录，根据实际 sys_menu id 调整）
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('8000001', '8000001', 'ProduceOrder', '生产工单', NULL, 0, '/produce/order/index', NULL, '0', '17', '/produce/order', 0, 1, 0, 'produce:order:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('8000001001', '8000001001', '', '新增工单', NULL, 0, '', NULL, '8000001', '17', '', 0, 2, 0, 'produce:order:add', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('8000001002', '8000001002', '', '修改工单', NULL, 0, '', NULL, '8000001', '17', '', 0, 2, 0, 'produce:order:modify', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('8000001003', '8000001003', '', '删除工单', NULL, 0, '', NULL, '8000001', '17', '', 0, 2, 0, 'produce:order:delete', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('8000001004', '8000001004', '', '完工确认', NULL, 0, '', NULL, '8000001', '17', '', 0, 2, 0, 'produce:order:complete', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- 工单编号规则
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES
(216, '生产工单编号', '[{"type":6,"val":"GD"},{"type":1,"pattern":"yyyyMMdd"},{"type":3,"key":"produce_order_code_seq_key_20240101","len":5,"step":1,"expireSeconds":86400}]');

COMMIT;
