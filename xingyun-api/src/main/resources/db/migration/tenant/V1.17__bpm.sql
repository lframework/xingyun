INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1003', '1003', 'BpmManage', '流程中心', 'ant-design:project-outlined', NULL, '', NULL, NULL, '16', '/bpm', 0, 0, 0, '', 1, 1, '', '系统管理员', '1', '2021-07-04 00:22:05', '系统管理员', '1', '2021-07-04 00:34:23');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1003001', '1003001', 'DesignFlowDefinition', '流程设计', NULL, 0, '/bpm/flow/definition/index', NULL, '1003', '16', '/flow-definition', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1003002', '1003002', 'MyFlow', '我的发起', NULL, 0, '/bpm/flow/task/my/index', NULL, '1003', '16', '/flow-my', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');

CREATE TABLE `flow_definition`
(
    `id`              bigint          NOT NULL COMMENT '主键id',
    `flow_code`       varchar(40)     NOT NULL COMMENT '流程编码',
    `flow_name`       varchar(100)    NOT NULL COMMENT '流程名称',
    `category`        varchar(100)             DEFAULT NULL COMMENT '流程类别',
    `version`         varchar(20)     NOT NULL COMMENT '流程版本',
    `is_publish`      tinyint(1)      NOT NULL DEFAULT '0' COMMENT '是否发布（0未发布 1已发布 9失效）',
    `form_custom`     char(1)                  DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
    `form_path`       varchar(100)             DEFAULT NULL COMMENT '审批表单路径',
    `activity_status` tinyint(1)      NOT NULL DEFAULT '1' COMMENT '流程激活状态（0挂起 1激活）',
    `listener_type`   varchar(100)             DEFAULT NULL COMMENT '监听器类型',
    `listener_path`   varchar(400)             DEFAULT NULL COMMENT '监听器路径',
    `ext`             varchar(500)             DEFAULT NULL COMMENT '业务详情 存业务表对象json字符串',
    `create_time`     datetime                 DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime                 DEFAULT NULL COMMENT '更新时间',
    `del_flag`        char(1)                  DEFAULT '0' COMMENT '删除标志',
    `tenant_id`       varchar(40)              DEFAULT NULL COMMENT '租户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='流程定义表';

CREATE TABLE `flow_node`
(
    `id`              bigint        NOT NULL COMMENT '主键id',
    `node_type`       tinyint(1)      NOT NULL COMMENT '节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
    `definition_id`   bigint          NOT NULL COMMENT '流程定义id',
    `node_code`       varchar(100)    NOT NULL COMMENT '流程节点编码',
    `node_name`       varchar(100)  DEFAULT NULL COMMENT '流程节点名称',
    `permission_flag` varchar(200)  DEFAULT NULL COMMENT '权限标识（权限类型:权限标识，可以多个，用@@隔开)',
    `node_ratio`      decimal(6, 3) DEFAULT NULL COMMENT '流程签署比例值',
    `coordinate`      varchar(100)  DEFAULT NULL COMMENT '坐标',
    `any_node_skip`   varchar(100)  DEFAULT NULL COMMENT '任意结点跳转',
    `listener_type`   varchar(100)  DEFAULT NULL COMMENT '监听器类型',
    `listener_path`   varchar(400)  DEFAULT NULL COMMENT '监听器路径',
    `handler_type`    varchar(100)  DEFAULT NULL COMMENT '处理器类型',
    `handler_path`    varchar(400)  DEFAULT NULL COMMENT '处理器路径',
    `form_custom`     char(1)       DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
    `form_path`       varchar(100)  DEFAULT NULL COMMENT '审批表单路径',
    `version`         varchar(20)     NOT NULL COMMENT '版本',
    `create_time`     datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime      DEFAULT NULL COMMENT '更新时间',
    `ext`             text          COMMENT '扩展属性',
    `del_flag`        char(1)       DEFAULT '0' COMMENT '删除标志',
    `tenant_id`       varchar(40)   DEFAULT NULL COMMENT '租户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='流程节点表';

CREATE TABLE `flow_skip`
(
    `id`             bigint       NOT NULL COMMENT '主键id',
    `definition_id`  bigint          NOT NULL COMMENT '流程定义id',
    `now_node_code`  varchar(100)    NOT NULL COMMENT '当前流程节点的编码',
    `now_node_type`  tinyint(1)   DEFAULT NULL COMMENT '当前节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
    `next_node_code` varchar(100)    NOT NULL COMMENT '下一个流程节点的编码',
    `next_node_type` tinyint(1)   DEFAULT NULL COMMENT '下一个节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
    `skip_name`      varchar(100) DEFAULT NULL COMMENT '跳转名称',
    `skip_type`      varchar(40)  DEFAULT NULL COMMENT '跳转类型（PASS审批通过 REJECT退回）',
    `skip_condition` varchar(200) DEFAULT NULL COMMENT '跳转条件',
    `coordinate`     varchar(100) DEFAULT NULL COMMENT '坐标',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime     DEFAULT NULL COMMENT '更新时间',
    `del_flag`       char(1)      DEFAULT '0' COMMENT '删除标志',
    `tenant_id`      varchar(40)  DEFAULT NULL COMMENT '租户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='节点跳转关联表';

CREATE TABLE `flow_instance`
(
    `id`              bigint      NOT NULL COMMENT '主键id',
    `definition_id`   bigint      NOT NULL COMMENT '对应flow_definition表的id',
    `business_id`     varchar(40) NOT NULL COMMENT '业务id',
    `node_type`       tinyint(1)  NOT NULL COMMENT '节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
    `node_code`       varchar(40) NOT NULL COMMENT '流程节点编码',
    `node_name`       varchar(100)         DEFAULT NULL COMMENT '流程节点名称',
    `variable`        text COMMENT '任务变量',
    `flow_status`     varchar(20) NOT NULL COMMENT '流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）',
    `activity_status` tinyint(1)  NOT NULL DEFAULT '1' COMMENT '流程激活状态（0挂起 1激活）',
    `def_json`        text COMMENT '流程定义json',
    `create_by`       varchar(64)          DEFAULT '' COMMENT '创建者',
    `create_time`     datetime             DEFAULT NULL COMMENT '创建时间',
    `update_time`     datetime             DEFAULT NULL COMMENT '更新时间',
    `ext`             varchar(500)         DEFAULT NULL COMMENT '扩展字段，预留给业务系统使用',
    `del_flag`        char(1)              DEFAULT '0' COMMENT '删除标志',
    `tenant_id`       varchar(40)          DEFAULT NULL COMMENT '租户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='流程实例表';

CREATE TABLE `flow_task`
(
    `id`            bigint       NOT NULL COMMENT '主键id',
    `definition_id` bigint       NOT NULL COMMENT '对应flow_definition表的id',
    `instance_id`   bigint       NOT NULL COMMENT '对应flow_instance表的id',
    `node_code`     varchar(100) NOT NULL COMMENT '节点编码',
    `node_name`     varchar(100) DEFAULT NULL COMMENT '节点名称',
    `node_type`     tinyint(1)   NOT NULL COMMENT '节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
    `flow_status`     varchar(20) NOT NULL COMMENT '流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）',
    `form_custom`   char(1)      DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
    `form_path`     varchar(100) DEFAULT NULL COMMENT '审批表单路径',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '更新时间',
    `del_flag`      char(1)      DEFAULT '0' COMMENT '删除标志',
    `tenant_id`     varchar(40)  DEFAULT NULL COMMENT '租户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='待办任务表';

CREATE TABLE `flow_his_task`
(
    `id`               bigint(20)                   NOT NULL COMMENT '主键id',
    `definition_id`    bigint(20)                   NOT NULL COMMENT '对应flow_definition表的id',
    `instance_id`      bigint(20)                   NOT NULL COMMENT '对应flow_instance表的id',
    `task_id`          bigint(20)                   NOT NULL COMMENT '对应flow_task表的id',
    `node_code`        varchar(100)                 DEFAULT NULL COMMENT '开始节点编码',
    `node_name`        varchar(100)                 DEFAULT NULL COMMENT '开始节点名称',
    `node_type`        tinyint(1)                   DEFAULT NULL COMMENT '开始节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）',
    `target_node_code` varchar(200)                 DEFAULT NULL COMMENT '目标节点编码',
    `target_node_name` varchar(200)                 DEFAULT NULL COMMENT '结束节点名称',
    `approver`         varchar(40)                  DEFAULT NULL COMMENT '审批者',
    `cooperate_type`   tinyint(1)                   NOT NULL DEFAULT '0' COMMENT '协作方式(1审批 2转办 3委派 4会签 5票签 6加签 7减签)',
    `collaborator`     varchar(40)                  DEFAULT NULL COMMENT '协作人',
    `skip_type`        varchar(10)                  NOT NULL COMMENT '流转类型（PASS通过 REJECT退回 NONE无动作）',
    `flow_status`      varchar(20)                  NOT NULL COMMENT '流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）',
    `form_custom`      char(1)                      DEFAULT 'N' COMMENT '审批表单是否自定义（Y是 N否）',
    `form_path`        varchar(100)                 DEFAULT NULL COMMENT '审批表单路径',
    `message`          varchar(500)                 DEFAULT NULL COMMENT '审批意见',
    `variable`         TEXT                         DEFAULT NULL COMMENT '任务变量',
    `ext`              TEXT                         DEFAULT NULL COMMENT '业务详情 存业务表对象json字符串',
    `create_time`      datetime                     DEFAULT NULL COMMENT '任务开始时间',
    `update_time`      datetime                     DEFAULT NULL COMMENT '审批完成时间',
    `del_flag`         char(1)                      DEFAULT '0' COMMENT '删除标志',
    `tenant_id`        varchar(40)                  DEFAULT NULL COMMENT '租户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT ='历史任务记录表';


CREATE TABLE `flow_user`
(
    `id`           bigint      NOT NULL COMMENT '主键id',
    `type`         char(1)         NOT NULL COMMENT '人员类型（1待办任务的审批人权限 2待办任务的转办人权限 3待办任务的委托人权限）',
    `processed_by` varchar(80) DEFAULT NULL COMMENT '权限人',
    `associated`   bigint          NOT NULL COMMENT '任务表id',
    `create_time`  datetime    DEFAULT NULL COMMENT '创建时间',
    `create_by`    varchar(80) DEFAULT NULL COMMENT '创建人',
    `update_time`  datetime    DEFAULT NULL COMMENT '更新时间',
    `del_flag`     char(1)     DEFAULT '0' COMMENT '删除标志',
    `tenant_id`    varchar(40) DEFAULT NULL COMMENT '租户id',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `user_processed_type` (`processed_by`, `type`),
    KEY `user_associated` (`associated`) USING BTREE
) ENGINE = InnoDB COMMENT ='流程用户表';

ALTER TABLE `tbl_purchase_config`
    ADD COLUMN `purchase_require_bpm` tinyint(1) NOT NULL DEFAULT 0 COMMENT '采购订单是否开启审批流程' AFTER `id`,
ADD COLUMN `purchase_bpm_process_id` bigint(20) NULL COMMENT '采购订单关联的审批流程ID' AFTER `purchase_require_bpm`,
ADD COLUMN `purchase_bpm_process_code` varchar(200) NULL COMMENT '采购订单关联的审批流程编号' AFTER `purchase_bpm_process_id`;

CREATE TABLE `tbl_purchase_order_form` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `code` varchar(32) NOT NULL COMMENT '单号',
    `sc_id` varchar(32) NOT NULL COMMENT '仓库ID',
    `supplier_id` varchar(32) NOT NULL COMMENT '供应商ID',
    `purchaser_id` varchar(32) DEFAULT NULL COMMENT '采购员ID',
    `expect_arrive_date` date DEFAULT NULL COMMENT '预计到货日期',
    `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '采购数量',
    `total_gift_num` int(11) NOT NULL DEFAULT '0' COMMENT '赠品数量',
    `total_amount` decimal(24,2) NOT NULL DEFAULT '0.00' COMMENT '采购金额',
    `description` varchar(200) DEFAULT NULL COMMENT '备注',
    `create_by` varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_by` varchar(32) NOT NULL COMMENT '修改人',
    `update_by_id` varchar(32) NOT NULL COMMENT '修改人ID',
    `update_time` datetime NOT NULL COMMENT '修改时间',
    `approve_by` varchar(32) DEFAULT NULL COMMENT '审核人',
    `approve_time` datetime DEFAULT NULL COMMENT '审核时间',
    `status` tinyint(3) NOT NULL COMMENT '状态',
    `refuse_reason` varchar(200) DEFAULT NULL COMMENT '拒绝原因',
    `flow_instance_id` bigint(20) DEFAULT NULL COMMENT '关联的审批流程ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`) USING BTREE,
    KEY `sc_id` (`sc_id`) USING BTREE,
    KEY `supplier_id` (`supplier_id`) USING BTREE,
    KEY `create_time` (`create_time`) USING BTREE,
    KEY `flow_instance_id` (`flow_instance_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购订单';

CREATE TABLE `tbl_purchase_order_detail_form` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `order_id` varchar(32) NOT NULL COMMENT '订单ID',
    `product_id` varchar(32) NOT NULL COMMENT '商品ID',
    `order_num` int(11) NOT NULL COMMENT '采购数量',
    `tax_price` decimal(16,2) NOT NULL COMMENT '采购价',
    `is_gift` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否赠品',
    `tax_rate` decimal(16,2) NOT NULL COMMENT '税率（%）',
    `description` varchar(200) DEFAULT NULL COMMENT '备注',
    `order_no` int(11) NOT NULL COMMENT '排序编号',
    `receive_num` int(11) NOT NULL DEFAULT '0' COMMENT '已收货数量',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `order_id` (`order_id`) USING BTREE,
    KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='采购订单明细';

CREATE TABLE `flow_cu_instance` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `title` varchar(200) NOT NULL COMMENT '标题',
    `biz_type` varchar(100) NULL COMMENT '业务类型',
    `biz_flag` varchar(100) NULL COMMENT '业务标识',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程实例扩展表';

ALTER TABLE `tbl_purchase_order`
    ADD COLUMN `flow_instance_id` bigint(20) NULL COMMENT '关联的审批流程ID' AFTER `refuse_reason`,
ADD INDEX `flow_instance_id`(`flow_instance_id`) USING BTREE;

ALTER TABLE `flow_definition`
    MODIFY COLUMN `is_publish` tinyint(3) NOT NULL DEFAULT 0 COMMENT '是否发布（0未发布 1已发布 9失效）' AFTER `version`,
    MODIFY COLUMN `activity_status` tinyint(3) NOT NULL DEFAULT 1 COMMENT '流程激活状态（0挂起 1激活）' AFTER `form_path`;

CREATE TABLE `flow_cu_approve_his` (
    `id` bigint(20) NOT NULL COMMENT 'ID',
    `definition_id` bigint(20) NOT NULL COMMENT '流程定义ID',
    `instance_id` bigint(20) NOT NULL COMMENT '流程实例ID',
    `node_code` varchar(100) NOT NULL COMMENT '节点编码',
    `node_name` varchar(100) NOT NULL COMMENT '节点名称',
    `task_id` bigint(20) DEFAULT NULL COMMENT '流程任务ID',
    `create_by` varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `message` varchar(500) DEFAULT NULL COMMENT '说明',
    `skip_type` tinyint(3) NOT NULL COMMENT '审核类型',
    PRIMARY KEY (`id`),
    KEY `definition_id` (`definition_id`),
    KEY `instance_id` (`instance_id`),
    KEY `task_id` (`task_id`),
    KEY `node_code` (`node_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程审核历史';
CREATE TABLE `flow_cu_category` (
    `id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'ID',
    `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
    `parent_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父级ID',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='流程分类';
ALTER TABLE `flow_cu_instance`
    ADD COLUMN `start_time` datetime NOT NULL COMMENT '发起时间' AFTER `biz_flag`,
ADD COLUMN `end_time` datetime NULL COMMENT '结束时间' AFTER `start_time`;
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1003003', '1003003', 'FlowTodo', '待办任务', NULL, 0, '/bpm/flow/task/todo/index', NULL, '1003', '16', '/flow-todo', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `icon`, `component_type`, `component`, `request_param`, `parent_id`, `sys_module_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES ('1003004', '1003004', 'FlowInstanceManage', '流程实例管理', NULL, 0, '/bpm/flow/instance/manage/index', NULL, '1003', '16', '/flow-instance-manage', 0, 1, 0, '', 1, 1, '', '系统管理员', '1', '2021-05-08 18:37:01', '系统管理员', '1', '2021-12-09 17:54:42');
