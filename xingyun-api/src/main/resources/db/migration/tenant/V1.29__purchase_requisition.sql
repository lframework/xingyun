-- ----------------------------
-- 采购需求联动：采购建议单、建议单明细
-- 依赖 V1.28__production_order.sql（tbl_production_order 表）
-- ----------------------------

-- 采购建议单主表
DROP TABLE IF EXISTS `tbl_purchase_requisition`;
CREATE TABLE `tbl_purchase_requisition` (
  `id`                  varchar(32)   NOT NULL COMMENT 'ID',
  `code`                varchar(32)   NOT NULL COMMENT '单号',
  `production_order_id` varchar(32)   NOT NULL COMMENT '来源生产工单ID',
  `sc_id`               varchar(32)   NOT NULL COMMENT '目标收货仓库ID',
  `status`              tinyint(3)    NOT NULL DEFAULT 0 COMMENT '状态（0-待确认 1-已确认 2-已转采购单 3-已取消）',
  `description`         varchar(500)  DEFAULT NULL COMMENT '备注',
  `create_by`           varchar(32)   NOT NULL COMMENT '创建人',
  `create_by_id`        varchar(32)   NOT NULL COMMENT '创建人ID',
  `create_time`         datetime      NOT NULL COMMENT '创建时间',
  `update_by`           varchar(32)   NOT NULL COMMENT '修改人',
  `update_by_id`        varchar(32)   NOT NULL COMMENT '修改人ID',
  `update_time`         datetime      NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_production_order` (`production_order_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购建议单';

-- 采购建议单明细
DROP TABLE IF EXISTS `tbl_purchase_requisition_detail`;
CREATE TABLE `tbl_purchase_requisition_detail` (
  `id`              varchar(32)   NOT NULL COMMENT 'ID',
  `requisition_id`  varchar(32)   NOT NULL COMMENT '建议单ID',
  `product_id`      varchar(32)   NOT NULL COMMENT '商品ID',
  `sku_id`          varchar(32)   NOT NULL COMMENT 'SKU ID',
  `require_num`     decimal(16,2) NOT NULL COMMENT '需求数量（工单原始）',
  `suggest_num`     decimal(16,2) NOT NULL COMMENT '建议采购数量（可调整）',
  `order_no`        int           NOT NULL DEFAULT 1 COMMENT '排序号',
  PRIMARY KEY (`id`),
  KEY `idx_requisition_id` (`requisition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购建议单明细';

-- 菜单：采购建议单（追加到采购模块菜单下，sys_module_id 取采购模块对应值，parent 参照实际）
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES
('9100001', '9100001', 'PurchaseRequisition', '采购建议单', NULL, 0, '/purchase/requisition/index', NULL, '0', '5', '/purchase/requisition', 0, 1, 0, 'purchase:requisition:query', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('9100001001', '9100001001', '', '生成建议单', NULL, 0, '', NULL, '9100001', '5', '', 0, 2, 0, 'purchase:requisition:add', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('9100001002', '9100001002', '', '修改/调整', NULL, 0, '', NULL, '9100001', '5', '', 0, 2, 0, 'purchase:requisition:modify', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW()),
('9100001003', '9100001003', '', '转采购订单', NULL, 0, '', NULL, '9100001', '5', '', 0, 2, 0, 'purchase:requisition:convert', 1, 1, '', '系统管理员', '1', NOW(), '系统管理员', '1', NOW());

-- 采购建议单编号规则
INSERT INTO `sys_generate_code` (`id`, `name`, `config_str`) VALUES
(217, '采购建议单编号', '[{"type":6,"val":"PR"},{"type":1,"pattern":"yyyyMMdd"},{"type":3,"key":"purchase_req_code_seq_key_20240101","len":5,"step":1,"expireSeconds":86400}]');

COMMIT;
