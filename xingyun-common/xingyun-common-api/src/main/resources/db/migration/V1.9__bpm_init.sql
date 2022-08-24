-- 业务对象、以及表单模块的表创建语句

-- ----------------------------
-- Table structure for bus_column
-- ----------------------------
DROP TABLE IF EXISTS `bus_column`;
CREATE TABLE `bus_column`
(
    `id_`            varchar(64) NOT NULL COMMENT '主键',
    `table_id_`      varchar(64)  DEFAULT NULL COMMENT '表id',
    `key_`           varchar(64)  DEFAULT NULL COMMENT '别名',
    `name_`          varchar(64)  DEFAULT NULL COMMENT '名字',
    `type_`          varchar(64)  DEFAULT NULL COMMENT '类型',
    `length_`        int(11) DEFAULT NULL,
    `decimal_`       int(11) DEFAULT NULL,
    `required_`      tinyint(4) DEFAULT NULL,
    `primary_`       tinyint(4) DEFAULT NULL,
    `default_value_` varchar(128) DEFAULT NULL,
    `comment_`       varchar(256) DEFAULT NULL,
    `create_time_`   datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by_`     varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time_`   datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by_`     varchar(64)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务字段表';


-- ----------------------------
-- Table structure for bus_column_ctrl
-- ----------------------------
DROP TABLE IF EXISTS `bus_column_ctrl`;
CREATE TABLE `bus_column_ctrl`
(
    `id_`          varchar(64) NOT NULL COMMENT '主键',
    `column_id_`   varchar(64)  DEFAULT NULL COMMENT '字段ID',
    `type_`        varchar(64)  DEFAULT NULL COMMENT '控件类型',
    `config_`      varchar(256) DEFAULT NULL COMMENT '控件配置',
    `valid_rule_`  varchar(256) DEFAULT NULL COMMENT '验证规则',
    `create_time_` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by_`   varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time_` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by_`   varchar(64)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `column_id_unique` (`column_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字段控件表';


-- ----------------------------
-- Table structure for bus_object
-- ----------------------------
DROP TABLE IF EXISTS `bus_object`;
CREATE TABLE `bus_object`
(
    `id_`                  varchar(64) NOT NULL COMMENT '主键',
    `key_`                 varchar(64)  DEFAULT NULL COMMENT 'key',
    `name_`                varchar(128) DEFAULT NULL COMMENT '名字',
    `desc_`                varchar(256) DEFAULT NULL COMMENT '描述',
    `relation_json_`       text COMMENT 'relation字段用来持久化入库的字符串字段',
    `group_id_`            varchar(64)  DEFAULT NULL COMMENT '分组id',
    `group_name_`          varchar(128) DEFAULT NULL COMMENT '分组名称',
    `persistence_type_`    varchar(64)  DEFAULT NULL COMMENT '持久化类型',
    per_type_config_       varchar(255) DEFAULT NULL COMMENT '持久化类型的配置内容',
    `overall_arrangement_` text         DEFAULT NULL COMMENT '整体布局',
    `create_time_`         datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by_`           varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time_`         datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by_`           varchar(64)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `key_unique_idx` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务对象';


-- ----------------------------
-- Table structure for bus_permission
-- ----------------------------
DROP TABLE IF EXISTS `bus_permission`;
CREATE TABLE `bus_permission`
(
    `id_`               varchar(64) NOT NULL,
    `bo_key_`           varchar(128) DEFAULT NULL COMMENT 'boKey',
    `obj_type_`         varchar(64) NOT NULL COMMENT '配置这个权限的对象，可以是表单，流程，或流程节点',
    `obj_val_`          varchar(128) DEFAULT NULL COMMENT '能获取到配置权限的对象的唯一值\r\n 通常是key 或 id \r\n 可以是自定义的\r\n 例如 某个流程的某个节点，可以是 流程key.nodeKey\r\n 这样的格式\r\n',
    `bus_obj_map_json_` longtext COMMENT 'busObjMap的json数据',
    `rights_json_`      longtext COMMENT 'rights的json数据',
    `create_time_`      datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by_`        varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time_`      datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by_`        varchar(64)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `obj_type_obj_val_unique_idx_` (`obj_type_`,`obj_val_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='bo权限';


-- ----------------------------
-- Table structure for bus_table
-- ----------------------------
DROP TABLE IF EXISTS `bus_table`;
CREATE TABLE `bus_table`
(
    `id_`          varchar(64) NOT NULL COMMENT '主键',
    `key_`         varchar(64)  DEFAULT NULL COMMENT '业务表key',
    `name_`        varchar(64)  DEFAULT NULL COMMENT '表名',
    `comment_`     varchar(256) DEFAULT NULL COMMENT '描述',
    `ds_key_`      varchar(64)  DEFAULT NULL COMMENT '数据源的别名',
    `ds_name_`     varchar(128) DEFAULT NULL COMMENT '数据源名称',
    `group_id_`    varchar(64)  DEFAULT NULL COMMENT '分组id',
    `group_name_`  varchar(128) DEFAULT NULL COMMENT '分组名称',
    `external_`    smallint(6) DEFAULT NULL COMMENT '是否外部表',
    `create_time_` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by_`   varchar(64)  DEFAULT NULL COMMENT '创建人',
    `update_time_` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by_`   varchar(64)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `key_unique_idx` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表';

-- ----------------------------
-- Table structure for form_cust_dialog
-- ----------------------------
DROP TABLE IF EXISTS `form_cust_dialog`;
CREATE TABLE `form_cust_dialog`
(
    `id_`                    varchar(64)  NOT NULL COMMENT '主键',
    `key_`                   varchar(64)  DEFAULT NULL COMMENT '别名',
    `name_`                  varchar(128) NOT NULL COMMENT '名字',
    `desc_`                  varchar(256) DEFAULT NULL COMMENT '描述',
    `style_`                 varchar(32)  DEFAULT NULL COMMENT '显示类型',
    `ds_key_`                varchar(64)  DEFAULT NULL COMMENT '数据源别名',
    `ds_name_`               varchar(128) DEFAULT NULL COMMENT '数据源名字',
    `obj_type_`              varchar(32)  DEFAULT NULL COMMENT '对象类型',
    `obj_name_`              varchar(64)  NOT NULL COMMENT '对象名称',
    `page_`                  tinyint(4) DEFAULT NULL COMMENT '是否分页',
    `page_size_`             int(11) DEFAULT NULL COMMENT '分页大小',
    `width_`                 int(11) DEFAULT NULL COMMENT '弹出框的宽度',
    `height_`                int(11) DEFAULT NULL COMMENT '弹出框的高度',
    `system_`                tinyint(4) DEFAULT NULL COMMENT '是否系统内置',
    `multiple_`              tinyint(4) DEFAULT NULL COMMENT '是否多选',
    `tree_config_json_`      varchar(512) DEFAULT NULL COMMENT '树形的配置信息，json字段',
    `display_fields_json_`   text COMMENT '显示字段',
    `condition_fields_json_` text COMMENT '条件字段的json',
    `return_fields_json_`    text COMMENT '返回字段json',
    `sort_fields_json_`      text COMMENT '排序字段',
    `data_source_`           varchar(64)  DEFAULT NULL,
    PRIMARY KEY (`id_`),
    UNIQUE KEY `idx_unqiue` (`key_`) USING BTREE
) DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='自定义对话框';

-- ----------------------------
-- Table structure for form_def
-- ----------------------------
DROP TABLE IF EXISTS `form_def`;
CREATE TABLE `form_def`
(
    `id_`          varchar(64) NOT NULL COMMENT '主键',
    `type_`        varchar(64) NOT NULL COMMENT '分类（pc/mobile）',
    `key_`         varchar(64)  DEFAULT NULL COMMENT 'key',
    `name_`        varchar(128) DEFAULT NULL COMMENT '名字',
    `desc_`        varchar(256) DEFAULT NULL COMMENT '描述',
    `group_id_`    varchar(64)  DEFAULT NULL COMMENT '分组id',
    `group_name_`  varchar(128) DEFAULT NULL COMMENT '分组名称',
    `bo_key_`      varchar(64)  DEFAULT NULL COMMENT '业务对象key',
    `bo_name_`     varchar(128) DEFAULT NULL COMMENT '业务对象名称',
    `html_`        longtext COMMENT 'html',
    `create_time_` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by_`   varchar(64)  DEFAULT NULL COMMENT '创建人ID',
    `creator_`     varchar(128) DEFAULT NULL COMMENT '创建人名字',
    `update_time_` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by_`   varchar(64)  DEFAULT NULL COMMENT '更新人ID',
    `updator_`     varchar(128) DEFAULT NULL COMMENT '更新人名字',
    `version_`     int(11) DEFAULT NULL COMMENT '版本号',
    `delete_`      tinyint(4) DEFAULT NULL COMMENT '逻辑删除标记',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `key_unique_idx` (`key_`) USING BTREE
) DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='表单';

-- ----------------------------
-- Table structure for form_template
-- ----------------------------
DROP TABLE IF EXISTS `form_template`;
CREATE TABLE `form_template`
(
    `id_`        varchar(64) NOT NULL COMMENT '模板id',
    `name_`      varchar(128) DEFAULT NULL COMMENT '模板名称',
    `form_type_` varchar(64)  DEFAULT NULL COMMENT '表单类型（pc/mobile/vuepc）',
    `type_`      varchar(32)  DEFAULT NULL COMMENT '模板类型',
    `html_`      text COMMENT '模板内容',
    `desc_`      varchar(400) DEFAULT NULL COMMENT '模板描述',
    `editable_`  tinyint(4) DEFAULT NULL COMMENT '是否可以编辑',
    `key_`       varchar(64)  DEFAULT NULL COMMENT '别名',
    PRIMARY KEY (`id_`)
) DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='表单模版';

-- ----- 常用脚本，流水号，子系统，菜单资源，通用授权，面板，系统属性，数据字典等功能表


SET
FOREIGN_KEY_CHECKS=0;
-- ------------------sys 模块功能 持久化表-----------------

-- ----------------------------
-- Table structure for sys_authorization
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorization`;
CREATE TABLE `sys_authorization`
(
    `rights_id_`              varchar(64)  NOT NULL COMMENT 'id',
    `rights_object_`          varchar(64)  NOT NULL COMMENT '授权对象表分区用',
    `rights_target_`          varchar(64)  NOT NULL COMMENT '授权目标ID',
    `rights_type_`            varchar(64)  NOT NULL COMMENT '权限类型',
    `rights_identity_`        varchar(64)  NOT NULL COMMENT '授权标识',
    `rights_identity_name_`   varchar(255) NOT NULL COMMENT '标识名字',
    `rights_permission_code_` varchar(125) NOT NULL COMMENT '授权code=identity+type',
    `rights_create_time_`     timestamp    NOT NULL COMMENT '创建时间',
    `rights_create_by_`       varchar(64)  NOT NULL COMMENT '创建人',
    PRIMARY KEY (`rights_id_`),
    KEY                       `idx_permission_code_` (`rights_permission_code_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='通用资源授权配置';

-- ----------------------------
-- Table structure for sys_data_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dict`;
CREATE TABLE `sys_data_dict`
(
    `id_`          varchar(64)  NOT NULL COMMENT 'id',
    `parent_id_`   varchar(64) DEFAULT NULL COMMENT '上级id',
    `key_`         varchar(255) NOT NULL COMMENT 'key',
    `name_`        varchar(255) NOT NULL COMMENT 'name',
    `dict_key_`    varchar(255) NOT NULL COMMENT '字典key',
    `type_id_`     varchar(64) DEFAULT NULL COMMENT '分组id',
    `sn_`          int(10) DEFAULT NULL COMMENT '排序',
    `dict_type_`   varchar(10)  NOT NULL COMMENT 'dict/node字典项',
    `delete_flag_` varchar(1)  DEFAULT NULL COMMENT '是否删除',
    `create_time_` timestamp    NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='数据字典';

-- ----------------------------
-- Table structure for sys_data_source
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source`;
CREATE TABLE `sys_data_source`
(
    `id_`              varchar(64) NOT NULL COMMENT '主键',
    `key_`             varchar(64)  DEFAULT NULL COMMENT '别名',
    `name_`            varchar(64)  DEFAULT NULL COMMENT '数据源名称',
    `desc_`            varchar(256) DEFAULT NULL COMMENT '数据源的描述',
    `db_type_`         varchar(64)  DEFAULT NULL COMMENT '数据库类型',
    `class_path_`      varchar(100) DEFAULT NULL COMMENT '数据源全路径',
    `attributes_json_` text COMMENT '属性配置',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `key_unique` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='数据源';

-- ----------------------------
-- Table structure for sys_data_source_def
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source_def`;
CREATE TABLE `sys_data_source_def`
(
    `id_`              varchar(64) NOT NULL COMMENT '主键',
    `name_`            varchar(64)  DEFAULT NULL COMMENT '数据源名称',
    `class_path_`      varchar(100) DEFAULT NULL COMMENT '数据源全路径',
    `attributes_json_` text COMMENT '属性配置',
    PRIMARY KEY (`id_`),
    KEY                `class_path_unique` (`class_path_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='数据源模板';

-- ----------------------------
-- Table structure for sys_log_err
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_err`;
CREATE TABLE `sys_log_err`
(
    `id_`            varchar(50) NOT NULL COMMENT '主键',
    `account_`       varchar(20)   DEFAULT NULL COMMENT '帐号',
    `ip_`            varchar(20)   DEFAULT NULL COMMENT 'IP来源',
    `ip_address_`    varchar(255)  DEFAULT NULL COMMENT 'IP地址',
    `status_`        varchar(64)   DEFAULT NULL COMMENT '状态：unchecked，checked，fixed',
    `url_`           varchar(1500) DEFAULT NULL COMMENT '错误URL',
    `content_`       text COMMENT '出错信息',
    `request_param_` text COMMENT '请求参数',
    `create_time_`   datetime      DEFAULT NULL COMMENT '出错时间',
    `stack_trace_`   longtext COMMENT '出错异常堆栈',
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统异常日志';

-- ----------------------------
-- Table structure for sys_properties
-- ----------------------------
DROP TABLE IF EXISTS `sys_properties`;
CREATE TABLE `sys_properties`
(
    `id_`          varchar(64) NOT NULL COMMENT 'ID',
    `name_`        varchar(64)  DEFAULT NULL COMMENT '属性名',
    `alias_`       varchar(64)  DEFAULT NULL COMMENT '别名',
    `group_`       varchar(64)  DEFAULT NULL COMMENT '分组',
    `value_`       varchar(500) DEFAULT NULL COMMENT '值',
    `encrypt_`     int(11) DEFAULT NULL COMMENT '是否加密',
    `update_by_`   varchar(64)  DEFAULT NULL,
    `update_time_` datetime     DEFAULT NULL,
    `create_by_`   varchar(64)  DEFAULT NULL,
    `create_time_` datetime     DEFAULT NULL,
    `description_` varchar(500) DEFAULT NULL,
    `environment_` varchar(64)  DEFAULT NULL,
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统属性';

DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`
(
    `ID_`          varchar(64) NOT NULL COMMENT '主键',
    `system_id_`   varchar(64)  DEFAULT NULL COMMENT '子系统ID',
    `alias_`       varchar(64)  DEFAULT NULL COMMENT '别名',
    `name_`        varchar(64)  DEFAULT NULL COMMENT '名字',
    `url_`         varchar(120) DEFAULT NULL COMMENT '请求地址',
    `enable_`      int(11) DEFAULT NULL COMMENT '显示到菜单(1,显示,0 ,不显示)',
    `opened_`      int(11) DEFAULT NULL COMMENT '是否默认打开',
    `icon_`        varchar(50)  DEFAULT NULL COMMENT '图标',
    `type_`        varchar(50)  DEFAULT NULL COMMENT 'menu，button，link',
    `sn_`          int(10) DEFAULT NULL COMMENT '排序',
    `parent_id_`   varchar(50)  DEFAULT NULL COMMENT '父节点ID',
    `create_time_` datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源';

-- ----------------------------
-- Table structure for sys_res_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_role`;
CREATE TABLE `sys_res_role`
(
    `ID_`        varchar(50) NOT NULL DEFAULT '' COMMENT '主键',
    `SYSTEM_ID_` varchar(50)          DEFAULT NULL COMMENT '系统ID',
    `res_id_`    varchar(50)          DEFAULT NULL COMMENT '资源ID',
    `role_id_`   varchar(50)          DEFAULT NULL COMMENT '角色ID',
    PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='角色资源分配';

-- ----------------------------
-- Table structure for sys_script
-- ----------------------------
DROP TABLE IF EXISTS `sys_script`;
CREATE TABLE `sys_script`
(
    `id_`       varchar(64) NOT NULL COMMENT '主键',
    `name_`     varchar(128) DEFAULT NULL COMMENT '脚本名称',
    `script_`   text COMMENT '脚本',
    `category_` varchar(128) DEFAULT NULL COMMENT '脚本分类',
    `memo_`     varchar(512) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='常用脚本';

-- ----------------------------
-- Table structure for sys_serialno
-- ----------------------------
DROP TABLE IF EXISTS `sys_serialno`;
CREATE TABLE `sys_serialno`
(
    `id_`         varchar(64) NOT NULL COMMENT '主键',
    `name_`       varchar(64)  DEFAULT NULL COMMENT '名称',
    `alias_`      varchar(20)  DEFAULT NULL COMMENT '别名',
    `regulation_` varchar(128) DEFAULT NULL COMMENT '规则',
    `gen_type_`   smallint(6) DEFAULT NULL COMMENT '生成类型',
    `no_length_`  int(11) DEFAULT NULL COMMENT '流水号长度',
    `cur_date_`   varchar(20)  DEFAULT NULL COMMENT '当前日期',
    `init_value_` int(11) DEFAULT NULL COMMENT '初始值',
    `cur_value_`  int(11) DEFAULT NULL COMMENT '当前值',
    `step_`       smallint(6) DEFAULT NULL COMMENT '步长',
    PRIMARY KEY (`id_`),
    KEY           `idx_uni_alias_val` (`alias_`,`cur_value_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='流水号生成';

DROP TABLE IF EXISTS `sys_subsystem`;
CREATE TABLE `sys_subsystem`
(
    `id_`          varchar(64) NOT NULL COMMENT '主键',
    `name_`        varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '系统名称',
    `alias_`       varchar(64)                                     DEFAULT NULL COMMENT '系统别名',
    `url_`         varchar(500)                                    DEFAULT NULL COMMENT '子系统地址，空则为当前系统',
    `open_type_`   varchar(64)                                     DEFAULT NULL COMMENT '打开方式',
    `enabled_`     int(11) DEFAULT NULL COMMENT '是否可用 1 可用，0 ，不可用',
    `create_time_` datetime                                        DEFAULT NULL COMMENT '创建时间',
    `is_default_`  int(11) DEFAULT NULL COMMENT '是否默认 1 可用，0 ，不可用',
    `desc_`        varchar(500)                                    DEFAULT NULL,
    `config_`      varchar(2000)                                   DEFAULT NULL,
    `create_by_`   varchar(64)                                     DEFAULT NULL,
    `update_time_` datetime                                        DEFAULT NULL,
    `update_by_`   varchar(255)                                    DEFAULT NULL,
    PRIMARY KEY (`id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子系统定义';


-- ----------------------------
-- Table structure for sys_tree
-- ----------------------------
DROP TABLE IF EXISTS `sys_tree`;
CREATE TABLE `sys_tree`
(
    `id_`     varchar(64) NOT NULL COMMENT '主键',
    `key_`    varchar(64)  DEFAULT NULL COMMENT '别名',
    `name_`   varchar(256) DEFAULT NULL COMMENT '名字',
    `desc_`   varchar(256) DEFAULT NULL COMMENT '描述',
    `system_` tinyint(4) DEFAULT NULL COMMENT '是否系统内置树',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `key_unique_` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin    COMMENT='系统树';

-- ----------------------------
-- Table structure for sys_tree_node
-- ----------------------------
DROP TABLE IF EXISTS `sys_tree_node`;
CREATE TABLE `sys_tree_node`
(
    `id_`        varchar(64) NOT NULL COMMENT '主键',
    `key_`       varchar(64)  DEFAULT NULL COMMENT '别名',
    `name_`      varchar(128) DEFAULT NULL COMMENT '名字',
    `desc_`      varchar(256) DEFAULT NULL COMMENT '描述',
    `tree_id_`   varchar(64)  DEFAULT NULL COMMENT '所属树id',
    `parent_id_` varchar(64)  DEFAULT NULL COMMENT '父ID',
    `path_`      varchar(512) DEFAULT NULL COMMENT '路径 eg:pppid.ppid.pid',
    `sn_`        tinyint(4) DEFAULT NULL COMMENT '排序号',
    PRIMARY KEY (`id_`),
    UNIQUE KEY `tree_id_key_unique_` (`key_`,`tree_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin    COMMENT='系统树节点';

-- ----------------------------
-- Table structure for sys_workbench_layout
-- ----------------------------
DROP TABLE IF EXISTS `sys_workbench_layout`;
CREATE TABLE `sys_workbench_layout`
(
    `id_`          varchar(64)  NOT NULL,
    `panel_id_`    varchar(255) NOT NULL COMMENT '面板id',
    `cust_width_`  int(10) DEFAULT NULL COMMENT '自定义宽',
    `cust_height_` int(10) DEFAULT NULL COMMENT '自定义高',
    `sn_`          int(10) DEFAULT NULL COMMENT '排序',
    `user_id_`     varchar(64)  NOT NULL COMMENT '用户id',
    `create_time_` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id_`),
    KEY            `idx_panel_id_` (`panel_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='工作台布局';

-- ----------------------------
-- Table structure for sys_workbench_panel
-- ----------------------------
DROP TABLE IF EXISTS `sys_workbench_panel`;
CREATE TABLE `sys_workbench_panel`
(
    `id_`              varchar(64)  NOT NULL,
    `alias_`           varchar(255) NOT NULL COMMENT '标识',
    `name_`            varchar(255) NOT NULL DEFAULT '' COMMENT '名字',
    `type_`            varchar(64)           DEFAULT NULL COMMENT '类型',
    `desc_`            varchar(500)          DEFAULT NULL COMMENT '描述',
    `data_type_`       varchar(64)           DEFAULT NULL COMMENT '数据类型',
    `data_source_`     varchar(255)          DEFAULT NULL COMMENT '数据来源',
    `auto_refresh_`    int(10) DEFAULT '0' COMMENT '自动刷新',
    `width_`           int(10) DEFAULT NULL COMMENT '宽',
    `height_`          int(10) DEFAULT NULL COMMENT '高',
    `display_content_` text COMMENT '展示内容',
    `more_url_`        varchar(255)          DEFAULT NULL COMMENT '更多链接',
    `create_time_`     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by_`       varchar(64)           DEFAULT NULL,
    `update_time_`     datetime              DEFAULT NULL,
    `update_by_`       varchar(64)           DEFAULT NULL,
    `delete_flag_`     varchar(10)           DEFAULT NULL,
    PRIMARY KEY (`id_`),
    KEY                `idx_alias_` (`alias_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='工作台面板';

-- ----------------------------
-- Table structure for sys_workbench_panel_templ
-- ----------------------------
DROP TABLE IF EXISTS `sys_workbench_panel_templ`;
CREATE TABLE `sys_workbench_panel_templ`
(
    `id_`   varchar(64) NOT NULL,
    `key_`  varchar(255) DEFAULT NULL COMMENT '模板key',
    `name_` varchar(255) DEFAULT NULL,
    `desc_` varchar(500) DEFAULT NULL COMMENT '模板描述',
    `html_` text COMMENT '模板内容',
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='工作台面板模板';

DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`
(
    `id_`          varchar(64) NOT NULL COMMENT '主键',
    `name_`        varchar(64)  DEFAULT NULL COMMENT '附件名',
    `uploader_`    varchar(128) DEFAULT NULL COMMENT '上传器',
    `path_`        varchar(256) DEFAULT NULL,
    `create_time_` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by_`   varchar(64)  DEFAULT NULL COMMENT '创建人ID',
    `update_time_` datetime     DEFAULT NULL COMMENT '更新时间',
    `update_by_`   varchar(64)  DEFAULT NULL COMMENT '更新人ID',
    `version_`     int(11) DEFAULT NULL COMMENT '版本号',
    `delete_`      tinyint(4) DEFAULT NULL COMMENT '逻辑删除标记',
    PRIMARY KEY (`id_`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='系统附件';


-- 附件存储 2018-6-10 00:29:06
-- IUploader 实现db策略的上传实现
DROP TABLE IF EXISTS `db_uploader`;
CREATE TABLE `db_uploader`
(
    `id_`    varchar(64) NOT NULL,
    `bytes_` longblob,
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `c_holiday_conf`;
CREATE TABLE `c_holiday_conf`
(
    `id_`       varchar(64) NOT NULL,
    `name_`     varchar(255) DEFAULT NULL,
    `system_`   varchar(255) DEFAULT NULL,
    `year_`     int(255) DEFAULT NULL,
    `startDay_` date         DEFAULT NULL,
    `endDay_`   date         DEFAULT NULL,
    `type_`     varchar(255) DEFAULT NULL,
    `remark_`   varchar(500) DEFAULT NULL,
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `c_work_calendar`;
CREATE TABLE `c_work_calendar`
(
    `id_`        varchar(20) NOT NULL,
    `day_`       date         DEFAULT NULL,
    `isWorkDay_` varchar(20)  DEFAULT NULL,
    `type_`      varchar(255) DEFAULT NULL,
    `system_`    varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `c_schedule`;
CREATE TABLE `c_schedule`
(
    `id_`                varchar(20) NOT NULL COMMENT 'ID',
    `title_`             varchar(500)  DEFAULT NULL COMMENT '标题',
    `desc_`              varchar(2000) DEFAULT NULL COMMENT '描述',
    `task_url_`          varchar(255)  DEFAULT NULL COMMENT '任务连接',
    `type_`              varchar(64)   DEFAULT NULL COMMENT '类型',
    `open_type_`         varchar(64)   DEFAULT NULL COMMENT '任务打开方式',
    `owner_`             varchar(64)   DEFAULT NULL COMMENT '所属人',
    `owner_name_`        varchar(64)   DEFAULT NULL COMMENT '所属人',
    `participant_names_` varchar(1000) DEFAULT NULL COMMENT '参与者',
    `start_time_`        datetime      DEFAULT NULL COMMENT '开始日期',
    `end_time_`          datetime      DEFAULT NULL COMMENT '结束日期',
    `actual_start_time_` datetime      DEFAULT NULL COMMENT '实际开始日期',
    `complete_time_`     datetime      DEFAULT NULL COMMENT '完成时间',
    `rate_progress_`     int(10) DEFAULT NULL COMMENT '进度',
    `submitter_`         varchar(64)   DEFAULT NULL COMMENT '提交人',
    `submitNamer_`       varchar(64)   DEFAULT NULL COMMENT '提交人',
    `remark_`            varchar(500)  DEFAULT NULL,
    `isLock_`            varchar(10)   DEFAULT NULL,
    `create_time_`       datetime      DEFAULT NULL COMMENT '创建时间',
    `create_by_`         varchar(64)   DEFAULT NULL COMMENT '创建人',
    `update_time_`       datetime      DEFAULT NULL COMMENT '更新时间',
    `update_by_`         varchar(64)   DEFAULT NULL COMMENT '更新人',
    `delete_flag_`       varchar(10)   DEFAULT NULL COMMENT '删除标记',
    `rev_`               int(10) DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程';

DROP TABLE IF EXISTS `c_schedule_participant`;
CREATE TABLE `c_schedule_participant`
(
    `id_`                 varchar(20) NOT NULL COMMENT 'id',
    `schedule_id_`        varchar(20)  DEFAULT NULL COMMENT '日程ID',
    `participantor_name_` varchar(255) DEFAULT NULL COMMENT '参与者名字',
    `participantor_`      varchar(64)  DEFAULT NULL COMMENT '参与者',
    `rate_progress_`      int(10) DEFAULT NULL COMMENT 'ilka完成比例',
    `submit_comment_`     varchar(500) DEFAULT NULL COMMENT 'ilka提交注释',
    `create_time_`        datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time_`        datetime     DEFAULT NULL COMMENT '更新时间',
    `actual_start_time_`  datetime     DEFAULT NULL COMMENT 'ilka实际开始时间',
    `complete_time_`      datetime     DEFAULT NULL COMMENT 'ilka完成时间',
    PRIMARY KEY (`id_`),
    KEY                   `idx_schedule_id` (`schedule_id_`),
    KEY                   `idx_participantor` (`participantor_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程参与者';

DROP TABLE IF EXISTS `c_schedule_biz`;
CREATE TABLE `c_schedule_biz`
(
    `id_`          varchar(20) NOT NULL COMMENT 'id',
    `schedule_id_` varchar(20) NOT NULL COMMENT '日程id',
    `biz_id_`      varchar(20) NOT NULL COMMENT '业务id',
    `from_`        varchar(64) NOT NULL COMMENT '来源',
    PRIMARY KEY (`id_`),
    KEY            `idx_schedule_id` (`schedule_id_`),
    KEY            `idx_biz_id` (`biz_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程业务关联表';

-- 流程模块表创建语句   含 activiti SQL， AgileBPM包装的SQL


SET
FOREIGN_KEY_CHECKS=0;

-- ---------------- Activiti table create --------------------
-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `ACT_EVT_LOG`;
CREATE TABLE `ACT_EVT_LOG`
(
    `LOG_NR_`       bigint(20) NOT NULL AUTO_INCREMENT,
    `TYPE_`         varchar(64)        DEFAULT NULL,
    `PROC_DEF_ID_`  varchar(64)        DEFAULT NULL,
    `PROC_INST_ID_` varchar(64)        DEFAULT NULL,
    `EXECUTION_ID_` varchar(64)        DEFAULT NULL,
    `TASK_ID_`      varchar(64)        DEFAULT NULL,
    `TIME_STAMP_`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `USER_ID_`      varchar(255)       DEFAULT NULL,
    `DATA_`         longblob,
    `LOCK_OWNER_`   varchar(255)       DEFAULT NULL,
    `LOCK_TIME_`    timestamp NULL DEFAULT NULL,
    `IS_PROCESSED_` tinyint(4) DEFAULT '0',
    PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `ACT_GE_BYTEARRAY`;
CREATE TABLE `ACT_GE_BYTEARRAY`
(
    `ID_`            varchar(64) NOT NULL,
    `REV_`           int(11) DEFAULT NULL,
    `NAME_`          varchar(255) DEFAULT NULL,
    `DEPLOYMENT_ID_` varchar(64)  DEFAULT NULL,
    `BYTES_`         longblob,
    `GENERATED_`     tinyint(4) DEFAULT NULL,
    PRIMARY KEY (`ID_`),
    KEY              `act_ge_bytearray_ibfk_1` (`DEPLOYMENT_ID_`) USING BTREE,
    CONSTRAINT `act_ge_bytearray_ibfk_1` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `ACT_GE_PROPERTY`;
CREATE TABLE `ACT_GE_PROPERTY`
(
    `NAME_`  varchar(64) NOT NULL,
    `VALUE_` varchar(300) DEFAULT NULL,
    `REV_`   int(11) DEFAULT NULL,
    PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


DROP TABLE IF EXISTS `ACT_PROCDEF_INFO`;
CREATE TABLE `ACT_PROCDEF_INFO`
(
    `ID_`           varchar(64) NOT NULL,
    `PROC_DEF_ID_`  varchar(64) NOT NULL,
    `REV_`          int(11) DEFAULT NULL,
    `INFO_JSON_ID_` varchar(64) DEFAULT NULL,
    PRIMARY KEY (`ID_`),
    UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`) USING BTREE,
    KEY             `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`) USING BTREE
) ENGINE=InnoDB;

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_DEPLOYMENT`;
CREATE TABLE `ACT_RE_DEPLOYMENT`
(
    `ID_`          varchar(64) NOT NULL,
    `NAME_`        varchar(255) DEFAULT NULL,
    `CATEGORY_`    varchar(255) DEFAULT NULL,
    `TENANT_ID_`   varchar(255) DEFAULT NULL,
    `DEPLOY_TIME_` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_MODEL`;
CREATE TABLE `ACT_RE_MODEL`
(
    `ID_`                           varchar(64) NOT NULL,
    `REV_`                          int(11) DEFAULT NULL,
    `NAME_`                         varchar(255)         DEFAULT NULL,
    `KEY_`                          varchar(255)         DEFAULT NULL,
    `CATEGORY_`                     varchar(255)         DEFAULT NULL,
    `VERSION_`                      int(11) DEFAULT NULL,
    `META_INFO_`                    varchar(4000)        DEFAULT NULL,
    `DEPLOYMENT_ID_`                varchar(64)          DEFAULT NULL,
    `EDITOR_SOURCE_VALUE_ID_`       varchar(64)          DEFAULT NULL,
    `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64)          DEFAULT NULL,
    `TENANT_ID_`                    varchar(255)         DEFAULT NULL,
    `CREATE_TIME_`                  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `LAST_UPDATE_TIME_`             datetime    NOT NULL,
    PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RE_PROCDEF`;
CREATE TABLE `ACT_RE_PROCDEF`
(
    `ID_`                     varchar(64)  NOT NULL,
    `REV_`                    int(11) DEFAULT NULL,
    `CATEGORY_`               varchar(255)  DEFAULT NULL,
    `NAME_`                   varchar(255)  DEFAULT NULL,
    `KEY_`                    varchar(255) NOT NULL,
    `VERSION_`                int(11) NOT NULL,
    `DEPLOYMENT_ID_`          varchar(64)   DEFAULT NULL,
    `RESOURCE_NAME_`          varchar(4000) DEFAULT NULL,
    `DGRM_RESOURCE_NAME_`     varchar(4000) DEFAULT NULL,
    `DESCRIPTION_`            varchar(4000) DEFAULT NULL,
    `HAS_START_FORM_KEY_`     tinyint(4) DEFAULT NULL,
    `SUSPENSION_STATE_`       int(11) DEFAULT NULL,
    `TENANT_ID_`              varchar(255)  DEFAULT NULL,
    `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
    PRIMARY KEY (`ID_`),
    KEY                       `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_IDENTITYLINK`;
CREATE TABLE `ACT_RU_IDENTITYLINK`
(
    `ID_`           varchar(64) NOT NULL,
    `REV_`          int(11) DEFAULT NULL,
    `GROUP_ID_`     varchar(255) DEFAULT NULL,
    `TYPE_`         varchar(255) DEFAULT NULL,
    `USER_ID_`      varchar(255) DEFAULT NULL,
    `TASK_ID_`      varchar(64)  DEFAULT NULL,
    `PROC_INST_ID_` varchar(64)  DEFAULT NULL,
    `PROC_DEF_ID_`  varchar(64)  DEFAULT NULL,
    PRIMARY KEY (`ID_`),
    KEY             `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`) USING BTREE,
    KEY             `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`) USING BTREE,
    KEY             `act_ru_identitylink_ibfk_1` (`PROC_DEF_ID_`) USING BTREE,
    KEY             `act_ru_identitylink_ibfk_2` (`PROC_INST_ID_`) USING BTREE,
    KEY             `act_ru_identitylink_ibfk_3` (`TASK_ID_`) USING BTREE,
    CONSTRAINT `act_ru_identitylink_ibfk_1` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`),
    CONSTRAINT `act_ru_identitylink_ibfk_2` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
    CONSTRAINT `act_ru_identitylink_ibfk_3` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_JOB`;
CREATE TABLE `ACT_RU_JOB`
(
    `ID_`                  varchar(64)  NOT NULL,
    `REV_`                 int(11) DEFAULT NULL,
    `TYPE_`                varchar(255) NOT NULL,
    `LOCK_OWNER_`          varchar(255)          DEFAULT NULL,
    `EXCLUSIVE_`           tinyint(1) DEFAULT NULL,
    `EXECUTION_ID_`        varchar(64)           DEFAULT NULL,
    `PROCESS_INSTANCE_ID_` varchar(64)           DEFAULT NULL,
    `PROC_DEF_ID_`         varchar(64)           DEFAULT NULL,
    `RETRIES_`             int(11) DEFAULT NULL,
    `EXCEPTION_STACK_ID_`  varchar(64)           DEFAULT NULL,
    `EXCEPTION_MSG_`       varchar(4000)         DEFAULT NULL,
    `REPEAT_`              varchar(255)          DEFAULT NULL,
    `HANDLER_TYPE_`        varchar(255)          DEFAULT NULL,
    `HANDLER_CFG_`         varchar(4000)         DEFAULT NULL,
    `TENANT_ID_`           varchar(255)          DEFAULT NULL,
    `LOCK_EXP_TIME_`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `DUEDATE_`             datetime     NOT NULL,
    PRIMARY KEY (`ID_`),
    KEY                    `act_ru_job_ibfk_1` (`EXCEPTION_STACK_ID_`) USING BTREE,
    CONSTRAINT `act_ru_job_ibfk_1` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_EVENT_SUBSCR`;
CREATE TABLE `ACT_RU_EVENT_SUBSCR`
(
    `ID_`            varchar(64)  NOT NULL,
    `REV_`           int(11) DEFAULT NULL,
    `EVENT_TYPE_`    varchar(255) NOT NULL,
    `EVENT_NAME_`    varchar(255)          DEFAULT NULL,
    `EXECUTION_ID_`  varchar(64)           DEFAULT NULL,
    `PROC_INST_ID_`  varchar(64)           DEFAULT NULL,
    `ACTIVITY_ID_`   varchar(64)           DEFAULT NULL,
    `CONFIGURATION_` varchar(255)          DEFAULT NULL,
    `TENANT_ID_`     varchar(255)          DEFAULT NULL,
    `PROC_DEF_ID_`   varchar(64)           DEFAULT NULL,
    `CREATED_`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID_`),
    KEY              `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_EXECUTION`;
CREATE TABLE `ACT_RU_EXECUTION`
(
    `ID_`               varchar(64) NOT NULL,
    `REV_`              int(11) DEFAULT NULL,
    `PROC_INST_ID_`     varchar(64)  DEFAULT NULL,
    `BUSINESS_KEY_`     varchar(255) DEFAULT NULL,
    `PARENT_ID_`        varchar(64)  DEFAULT NULL,
    `PROC_DEF_ID_`      varchar(64)  DEFAULT NULL,
    `SUPER_EXEC_`       varchar(64)  DEFAULT NULL,
    `ACT_ID_`           varchar(255) DEFAULT NULL,
    `IS_ACTIVE_`        tinyint(4) DEFAULT NULL,
    `IS_CONCURRENT_`    tinyint(4) DEFAULT NULL,
    `IS_SCOPE_`         tinyint(4) DEFAULT NULL,
    `IS_EVENT_SCOPE_`   tinyint(4) DEFAULT NULL,
    `SUSPENSION_STATE_` int(11) DEFAULT NULL,
    `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
    `TENANT_ID_`        varchar(255) DEFAULT NULL,
    `NAME_`             varchar(255) DEFAULT NULL,
    `LOCK_TIME_`        timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`ID_`),
    KEY                 `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`) USING BTREE,
    KEY                 `act_ru_execution_ibfk_1` (`PARENT_ID_`) USING BTREE,
    KEY                 `act_ru_execution_ibfk_2` (`PROC_DEF_ID_`) USING BTREE,
    KEY                 `act_ru_execution_ibfk_4` (`SUPER_EXEC_`) USING BTREE,
    CONSTRAINT `act_ru_execution_ibfk_1` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`),
    CONSTRAINT `act_ru_execution_ibfk_2` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE,
    CONSTRAINT `act_ru_execution_ibfk_3` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


DROP TABLE IF EXISTS `ACT_RU_TASK`;
CREATE TABLE `ACT_RU_TASK`
(
    `ID_`               varchar(64) NOT NULL,
    `REV_`              int(11) DEFAULT NULL,
    `EXECUTION_ID_`     varchar(64)   DEFAULT NULL,
    `PROC_INST_ID_`     varchar(64)   DEFAULT NULL,
    `PROC_DEF_ID_`      varchar(64)   DEFAULT NULL,
    `NAME_`             varchar(255)  DEFAULT NULL,
    `PARENT_TASK_ID_`   varchar(64)   DEFAULT NULL,
    `DESCRIPTION_`      varchar(4000) DEFAULT NULL,
    `TASK_DEF_KEY_`     varchar(255)  DEFAULT NULL,
    `OWNER_`            varchar(255)  DEFAULT NULL,
    `ASSIGNEE_`         varchar(255)  DEFAULT NULL,
    `DELEGATION_`       varchar(64)   DEFAULT NULL,
    `PRIORITY_`         int(11) DEFAULT NULL,
    `SUSPENSION_STATE_` int(11) DEFAULT NULL,
    `CATEGORY_`         varchar(255)  DEFAULT NULL,
    `TENANT_ID_`        varchar(255)  DEFAULT NULL,
    `CREATE_TIME_`      timestamp NULL DEFAULT NULL,
    `DUE_DATE_`         datetime      DEFAULT NULL,
    `FORM_KEY_`         varchar(255)  DEFAULT NULL,
    PRIMARY KEY (`ID_`),
    KEY                 `ACT_RU_TASK_IBFK_1` (`EXECUTION_ID_`) USING BTREE,
    KEY                 `act_ru_task_ibfk_2` (`PROC_DEF_ID_`) USING BTREE,
    KEY                 `act_ru_task_ibfk_3` (`PROC_INST_ID_`) USING BTREE,
    CONSTRAINT `act_ru_task_ibfk_1` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
    CONSTRAINT `act_ru_task_ibfk_2` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE,
    CONSTRAINT `act_ru_task_ibfk_3` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `ACT_RU_VARIABLE`;
CREATE TABLE `ACT_RU_VARIABLE`
(
    `ID_`           varchar(64)  NOT NULL,
    `REV_`          int(11) DEFAULT NULL,
    `TYPE_`         varchar(255) NOT NULL,
    `NAME_`         varchar(255) NOT NULL,
    `EXECUTION_ID_` varchar(64)   DEFAULT NULL,
    `PROC_INST_ID_` varchar(64)   DEFAULT NULL,
    `TASK_ID_`      varchar(64)   DEFAULT NULL,
    `BYTEARRAY_ID_` varchar(64)   DEFAULT NULL,
    `DOUBLE_`       double        DEFAULT NULL,
    `LONG_`         bigint(20) DEFAULT NULL,
    `TEXT_`         varchar(4000) DEFAULT NULL,
    `TEXT2_`        varchar(4000) DEFAULT NULL,
    PRIMARY KEY (`ID_`),
    KEY             `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`) USING BTREE,
    KEY             `act_ru_variable_ibfk_1` (`BYTEARRAY_ID_`) USING BTREE,
    KEY             `act_ru_variable_ibfk_2` (`EXECUTION_ID_`) USING BTREE,
    KEY             `act_ru_variable_ibfk_3` (`PROC_INST_ID_`) USING BTREE,
    CONSTRAINT `act_ru_variable_ibfk_1` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
    CONSTRAINT `act_ru_variable_ibfk_2` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
    CONSTRAINT `act_ru_variable_ibfk_3` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- ---------------- AgileBPM table create --------------------

-- ----------------------------
-- Table structure for bpm_bus_link
-- ----------------------------
DROP TABLE IF EXISTS `bpm_bus_link`;
CREATE TABLE `bpm_bus_link`
(
    `id_`       varchar(64) NOT NULL COMMENT '主键',
    `def_id_`   varchar(64) DEFAULT NULL COMMENT '流程定义ID',
    `inst_id_`  varchar(64) DEFAULT NULL COMMENT '流程实例ID',
    `biz_id_`   varchar(64) DEFAULT NULL COMMENT '业务主键',
    `biz_code_` varchar(64) NOT NULL COMMENT 'bo_code',
    PRIMARY KEY (`id_`, `biz_code_`),
    KEY         `link_inst_id_idx` (`inst_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例与业务数据关系表'
/*!50500 PARTITION BY LIST  COLUMNS(biz_code_)
(PARTITION p01 VALUES IN ('unknown') ENGINE = InnoDB) */;



-- ----------------------------
-- Table structure for bpm_definition
-- ----------------------------
DROP TABLE IF EXISTS `bpm_definition`;
CREATE TABLE `bpm_definition`
(
    `id_`             varchar(64) NOT NULL COMMENT '流程定义ID',
    `name_`           varchar(64) NOT NULL COMMENT '流程名称',
    `key_`            varchar(64) NOT NULL COMMENT '流程业务主键',
    `desc_`           varchar(1024) DEFAULT NULL COMMENT '流程描述',
    `type_id_`        varchar(64)   DEFAULT NULL COMMENT '所属分类ID',
    `status_`         varchar(40)   DEFAULT NULL COMMENT '流程状态。草稿、发布、禁用',
    `act_def_id_`     varchar(64)   DEFAULT NULL COMMENT 'BPMN - 流程定义ID',
    `act_model_id_`   varchar(64)   DEFAULT NULL,
    `act_deploy_id_`  varchar(64)   DEFAULT NULL COMMENT 'BPMN - 流程发布ID',
    `version_`        int(11) DEFAULT NULL COMMENT '版本 - 当前版本号',
    `main_def_id_`    varchar(64)   DEFAULT NULL COMMENT '版本 - 主版本流程ID',
    `is_main_`        char(1)       DEFAULT NULL COMMENT '版本 - 是否主版本',
    `create_by_`      varchar(64)   DEFAULT NULL COMMENT '创建人ID',
    `create_time_`    datetime      DEFAULT NULL COMMENT '创建时间',
    `create_org_id_`  varchar(64)   DEFAULT NULL COMMENT '创建者所属组织ID',
    `update_by_`      varchar(64)   DEFAULT NULL COMMENT '更新人ID',
    `update_time_`    datetime      DEFAULT NULL COMMENT '更新时间',
    `support_mobile_` int(11) DEFAULT '0',
    `def_setting_`    text,
    `rev_`            int(11) DEFAULT NULL,
    PRIMARY KEY (`id_`),
    KEY               `bpm_process_def_key` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='流程定义';

-- ----------------------------
-- Table structure for bpm_instance
-- ----------------------------
DROP TABLE IF EXISTS `bpm_instance`;
CREATE TABLE `bpm_instance`
(
    `id_`             varchar(64)  NOT NULL COMMENT '流程实例ID',
    `subject_`        varchar(128) NOT NULL COMMENT '流程实例标题',
    `def_id_`         varchar(64)  NOT NULL COMMENT '流程定义ID',
    `act_def_id_`     varchar(64)  DEFAULT NULL COMMENT 'BPMN流程定义ID',
    `def_key_`        varchar(128) DEFAULT NULL COMMENT '流程定义Key',
    `def_name_`       varchar(128) NOT NULL COMMENT '流程名称',
    `biz_key_`        varchar(64)  DEFAULT NULL COMMENT '关联数据业务主键',
    `status_`         varchar(40)  DEFAULT NULL COMMENT '实例状态',
    `end_time_`       datetime     DEFAULT NULL COMMENT '实例结束时间',
    `duration_`       bigint(20) DEFAULT NULL COMMENT '持续时间(ms)',
    `type_id_`        varchar(64)  DEFAULT NULL COMMENT '所属分类ID',
    `act_inst_id_`    varchar(64)  DEFAULT NULL COMMENT 'BPMN流程实例ID',
    `create_by_`      varchar(64)  DEFAULT NULL COMMENT '创建人ID',
    `creator_`        varchar(64)  DEFAULT NULL COMMENT '创建人',
    `create_time_`    datetime     DEFAULT NULL COMMENT '创建时间',
    `create_org_id_`  varchar(64)  DEFAULT NULL COMMENT '创建者所属组织ID',
    `update_by_`      varchar(64)  DEFAULT NULL COMMENT '更新人ID',
    `update_time_`    datetime     DEFAULT NULL COMMENT '更新时间',
    `is_formmal_`     char(1)      NOT NULL COMMENT '是否正式数据',
    `parent_inst_id_` varchar(64)  DEFAULT NULL COMMENT '父实例Id',
    `is_forbidden_`   smallint(6) DEFAULT NULL COMMENT '禁止',
    `data_mode_`      varchar(20)  DEFAULT NULL,
    `support_mobile_` int(11) DEFAULT '0',
    `super_node_id_`  varchar(50)  DEFAULT NULL COMMENT '父流程定义节点ID',
    PRIMARY KEY (`id_`),
    KEY               `idx_proinst_bpminstid` (`act_inst_id_`) USING BTREE,
    KEY               `idx_proinst_parentId` (`parent_inst_id_`) USING BTREE,
    KEY               `idx_proinst_bizkey` (`biz_key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程实例';

-- ----------------------------
-- Table structure for bpm_task
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task`;
CREATE TABLE `bpm_task`
(
    `id_`               varchar(64)  NOT NULL COMMENT '任务ID',
    `name_`             varchar(64)  NOT NULL COMMENT '任务名称',
    `subject_`          varchar(128) NOT NULL COMMENT '待办事项标题',
    `inst_id_`          varchar(64)  NOT NULL COMMENT '关联 - 流程实例ID',
    `task_id_`          varchar(64)  DEFAULT NULL COMMENT '关联的任务ID',
    `act_inst_id_`      varchar(64)  DEFAULT NULL COMMENT 'activiti 实例id',
    `act_execution_id_` varchar(64)  DEFAULT NULL COMMENT 'activiti 执行id',
    `node_id_`          varchar(64)  DEFAULT NULL COMMENT '关联 - 任务节点ID',
    `def_id_`           varchar(64)  NOT NULL COMMENT '关联 - 流程定义ID',
    `assignee_id_`      varchar(64)  DEFAULT NULL COMMENT '任务执行人ID',
    `assignee_names_`   varchar(500) DEFAULT NULL,
    `status_`           varchar(64)  NOT NULL COMMENT '任务状态',
    `priority_`         int(11) DEFAULT NULL COMMENT '任务优先级',
    `due_time_`         datetime     DEFAULT NULL COMMENT '任务到期时间',
    `task_type_`        varchar(64)  DEFAULT NULL COMMENT '任务类型',
    `parent_id_`        varchar(64)  DEFAULT NULL COMMENT '父任务ID',
    `type_id_`          varchar(64)  DEFAULT NULL COMMENT '分类ID',
    `create_time_`      datetime     NOT NULL COMMENT '任务创建时间',
    `create_by_`        varchar(64)  DEFAULT NULL,
    `support_mobile_`   int(11) DEFAULT '0',
    `back_node_`        varchar(64)  DEFAULT NULL COMMENT '返回节点',
    PRIMARY KEY (`id_`),
    KEY                 `idx_bpmtask_instid` (`inst_id_`) USING BTREE,
    KEY                 `idx_bpmtask_taskid` (`task_id_`) USING BTREE,
    KEY                 `idx_bpmtask_parentid` (`parent_id_`) USING BTREE,
    KEY                 `idx_bpmtask_userid` (`assignee_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程任务';

-- ----------------------------
-- Table structure for bpm_task_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_identitylink`;
CREATE TABLE `bpm_task_identitylink`
(
    `id_`              varchar(64) NOT NULL COMMENT '主键',
    `task_id_`         varchar(64) DEFAULT NULL COMMENT '任务ID',
    `inst_id_`         varchar(64) DEFAULT NULL,
    `type_`            varchar(20) DEFAULT NULL COMMENT '候选人类型',
    `identity_name_`   varchar(64) DEFAULT NULL COMMENT '名字',
    `identity_`        varchar(64) DEFAULT NULL COMMENT 'ID',
    `permission_code_` varchar(64) DEFAULT NULL,
    PRIMARY KEY (`id_`),
    KEY                `idx_taskcandidate_taskid` (`task_id_`) USING BTREE,
    KEY                `idx_candidate_instid` (`inst_id_`) USING BTREE,
    KEY                `idx_permission_code_` (`permission_code_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='任务候选人';

-- ----------------------------
-- Table structure for bpm_task_opinion
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_opinion`;
CREATE TABLE `bpm_task_opinion`
(
    `id_`            varchar(64) NOT NULL COMMENT '意见ID',
    `inst_id_`       varchar(64) NOT NULL COMMENT '流程实例ID',
    `sup_inst_id_`   varchar(64)   DEFAULT NULL COMMENT '父流程实例ID',
    `task_id_`       varchar(64)   DEFAULT NULL COMMENT '任务ID',
    `task_key_`      varchar(64)   DEFAULT NULL COMMENT '任务定义Key',
    `task_name_`     varchar(255)  DEFAULT NULL COMMENT '任务名称',
    `token_`         varchar(64)   DEFAULT NULL COMMENT '任务令牌',
    `assign_info_`   varchar(2000) DEFAULT NULL COMMENT '任务分配情况',
    `approver_`      varchar(64)   DEFAULT NULL COMMENT '审批人',
    `approver_name_` varchar(64)   DEFAULT NULL COMMENT '审批人名字',
    `approve_time_`  datetime      DEFAULT NULL COMMENT '审批时间',
    `opinion_`       varchar(2000) DEFAULT NULL COMMENT '审批意见',
    `status_`        varchar(64) NOT NULL COMMENT '审批状态。start=发起流程；awaiting_check=待审批；agree=同意；against=反对；return=驳回；abandon=弃权；retrieve=追回',
    `form_id_`       varchar(64)   DEFAULT NULL COMMENT '表单定义ID',
    `create_by_`     varchar(255)  DEFAULT NULL,
    `create_time_`   datetime      DEFAULT NULL COMMENT '执行开始时间',
    `dur_ms_`        bigint(20) DEFAULT NULL COMMENT '持续时间(ms)',
    PRIMARY KEY (`id_`),
    KEY              `idx_opinion_supinstid` (`sup_inst_id_`) USING BTREE,
    KEY              `idx_opinion_task` (`task_id_`) USING BTREE,
    KEY              `idx_opinion_instId` (`inst_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程任务审批记录';

-- ----------------------------
-- Table structure for bpm_task_stack
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_stack`;
CREATE TABLE `bpm_task_stack`
(
    `id_`             varchar(64) NOT NULL COMMENT '主键',
    `task_id_`        varchar(64) NOT NULL COMMENT '任务ID',
    `inst_id_`        varchar(64)  DEFAULT NULL COMMENT '流程实例ID',
    `parent_id_`      varchar(64)  DEFAULT NULL COMMENT '父ID',
    `node_id_`        varchar(64) NOT NULL COMMENT '节点ID',
    `node_name_`      varchar(125) DEFAULT NULL,
    `start_time_`     datetime     DEFAULT NULL COMMENT '开始时间',
    `end_time`        datetime     DEFAULT NULL COMMENT '结束时间',
    `is_muliti_task_` smallint(6) DEFAULT NULL COMMENT '1=是\r\n                        0=否',
    `node_type_`      varchar(64)  DEFAULT NULL COMMENT '节点类型',
    `action_name_`    varchar(64)  DEFAULT NULL COMMENT '响应动作',
    PRIMARY KEY (`id_`),
    KEY               `idx_exestack_instid` (`inst_id_`) USING BTREE,
    KEY               `idx_exestack_taskid` (`task_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程执行堆栈树';


-- 流程提交日志插件
DROP TABLE IF EXISTS `bpm_submit_data_log`;
CREATE TABLE `bpm_submit_data_log`
(
    `id`          varchar(64) NOT NULL COMMENT 'id',
    `task_id_`    varchar(64)  DEFAULT NULL COMMENT '任务ID',
    `inst_id_`    varchar(64)  DEFAULT NULL COMMENT '实例ID',
    `data`        longtext COMMENT '业务数据',
    `destination` varchar(255) DEFAULT NULL COMMENT '目标节点',
    `extendConf`  varchar(500) DEFAULT NULL COMMENT '特殊配置',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务对象数据提交日志';


-- 催办相关表
DROP TABLE IF EXISTS `bpm_plugin_reminder_trigger`;
CREATE TABLE `bpm_plugin_reminder_trigger`
(
    `id_`                varchar(20) NOT NULL COMMENT 'ID',
    `task_id_`           varchar(20) NOT NULL COMMENT '任务ID',
    `reminder_desc_`     varchar(255)  DEFAULT NULL COMMENT '催办的描述',
    `before_script_`     varchar(500)  DEFAULT NULL COMMENT '催办前置脚本',
    `msg_type_`          varchar(10)   DEFAULT NULL COMMENT '催办消息类型',
    `html_msg_`          varchar(1000) DEFAULT NULL COMMENT 'html消息',
    `text_msg_`          varchar(500)  DEFAULT NULL COMMENT '普通消息',
    `is_calc_workday_`   int(1) DEFAULT NULL COMMENT '是否工作日计算',
    `is_urgent_`         int(1) DEFAULT NULL COMMENT '是否加急任务',
    `max_reminder_times` int(10) DEFAULT NULL COMMENT '最多催办次数',
    `reminder_times_`    int(10) DEFAULT NULL COMMENT '催办次数',
    `reminder_cycle_`    int(12) DEFAULT NULL COMMENT '催办周期',
    `duedate_`           datetime    NOT NULL COMMENT '到期时间',
    PRIMARY KEY (`id_`),
    KEY                  `task_id_` (`task_id_`) USING BTREE,
    KEY                  `duedate_` (`duedate_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程催办触发';

DROP TABLE IF EXISTS `bpm_plugin_reminder_log`;
CREATE TABLE `bpm_plugin_reminder_log`
(
    `id_`               varchar(20) NOT NULL COMMENT 'ID',
    `instance_id_`      varchar(20)  DEFAULT NULL COMMENT '实例ID',
    `reminder_title_`   varchar(255) DEFAULT NULL COMMENT '催办标题',
    `subject_`          varchar(500) DEFAULT NULL COMMENT '流程标题',
    `node_id_`          varchar(64)  DEFAULT NULL COMMENT '节点ID',
    `msg_type_`         varchar(64)  DEFAULT NULL COMMENT '催办消息类型',
    `reminder_users_`   varchar(500) DEFAULT NULL COMMENT '催办人',
    `reminder_userids_` varchar(500) DEFAULT NULL COMMENT '催办人ID',
    `reminder_date_`    datetime     DEFAULT NULL COMMENT '催办时间',
    `extend_`           varchar(500) DEFAULT NULL COMMENT '其他说明',
    PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程催办日志';

DROP TABLE IF EXISTS `bpm_user_agency_config`;
CREATE TABLE `bpm_user_agency_config`
(
    `id_`               varchar(64)   NOT NULL COMMENT '配置ID',
    `start_datetime_`   datetime      NOT NULL COMMENT '起始时间',
    `end_datetime_`     datetime      NOT NULL COMMENT '结束时间',
    `agency_flow_key_`  varchar(1000) NOT NULL COMMENT '代理流程编码，多个中间逗号分隔(,)',
    `agency_flow_name_` varchar(5000) NOT NULL COMMENT '代理流程名称，多个中间逗号分隔(,)',
    `config_user_id_`   varchar(64)   NOT NULL COMMENT '配置用户编码',
    `target_user_id_`   varchar(1000) NOT NULL COMMENT '目标用户编码，多个中间逗号分隔(,)',
    `target_user_name_` varchar(1000) NOT NULL COMMENT '目标用户姓名，多个中间逗哥分隔(,)',
    `enable_`           tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用/未启用(1/0)',
    `create_by_`        varchar(64) DEFAULT NULL COMMENT '创建人ID',
    `create_time_`      datetime    DEFAULT NULL COMMENT '创建时间',
    `create_org_id_`    varchar(64) DEFAULT NULL COMMENT '创建者所属组织ID',
    `update_by_`        varchar(64) DEFAULT NULL COMMENT '更新人ID',
    `update_time_`      datetime    DEFAULT NULL COMMENT '更新时间',
    `rev_`              int(11) DEFAULT NULL,
    PRIMARY KEY (`id_`),
    KEY                 `config_user_id_` (`config_user_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程用户代理配置';

DROP TABLE IF EXISTS `bpm_user_agency_log`;
CREATE TABLE `bpm_user_agency_log`
(
    `id_`               varchar(64) NOT NULL COMMENT '日志ID',
    `config_id_`        varchar(64) NOT NULL COMMENT '配置ID',
    `flow_instance_id_` varchar(64) NOT NULL COMMENT '流程实例编号',
    `task_id_`          varchar(64) NOT NULL COMMENT '代理任务编号',
    `task_node_id_`     varchar(64) NOT NULL COMMENT '代理任务节点',
    `task_node_name_`   varchar(64) NOT NULL COMMENT '代理任务节点名称',
    `create_by_`        varchar(64) DEFAULT NULL COMMENT '创建人ID',
    `create_time_`      datetime    DEFAULT NULL COMMENT '创建时间',
    `create_org_id_`    varchar(64) DEFAULT NULL COMMENT '创建者所属组织ID',
    `update_by_`        varchar(64) DEFAULT NULL COMMENT '更新人ID',
    `update_time_`      datetime    DEFAULT NULL COMMENT '更新时间',
    `rev_`              int(11) DEFAULT NULL,
    PRIMARY KEY (`id_`),
    KEY                 `config_id_` (`config_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程用户代理日志';


-- 系统分类
INSERT INTO `sys_tree` (`id_`, `key_`, `name_`, `desc_`, `system_`)
VALUES ('20000002810002', 'ywbfl', '业务表分类', '业务表分类的树', '1');
INSERT INTO `sys_tree` (`id_`, `key_`, `name_`, `desc_`, `system_`)
VALUES ('20000002900001', 'ywdxfl', '业务对象分类', '业务对象分类树', '1');
INSERT INTO `sys_tree` (`id_`, `key_`, `name_`, `desc_`, `system_`)
VALUES ('20000002960002', 'bdfl', '表单分类', '表单分类', '1');
INSERT INTO `sys_tree` (`id_`, `key_`, `name_`, `desc_`, `system_`)
VALUES ('20000007060001', 'dict', '数据字典分类', '数据字典', '1');
INSERT INTO `sys_tree` (`id_`, `key_`, `name_`, `desc_`, `system_`)
VALUES ('20000008980001', 'flow', '流程分类', NULL, '1');

-- 分类节点
INSERT INTO `sys_tree_node` (`id_`, `key_`, `name_`, `desc_`, `tree_id_`, `parent_id_`, `path_`,
                             `sn_`)
VALUES ('20000002810003', 'mrfl', '默认分类', NULL, '20000002810002', '0', '20000002810003.', '1');
INSERT INTO `sys_tree_node` (`id_`, `key_`, `name_`, `desc_`, `tree_id_`, `parent_id_`, `path_`,
                             `sn_`)
VALUES ('20000002900002', 'ywdxmrfl', '业务对象默认分类', NULL, '20000002900001', '0', '20000002900002.',
        '3');
INSERT INTO `sys_tree_node` (`id_`, `key_`, `name_`, `desc_`, `tree_id_`, `parent_id_`, `path_`,
                             `sn_`)
VALUES ('20000002960003', 'mrfl', '默认分类', NULL, '20000002960002', '0', '20000002960003.', '4');
INSERT INTO `sys_tree_node` (`id_`, `key_`, `name_`, `desc_`, `tree_id_`, `parent_id_`, `path_`,
                             `sn_`)
VALUES ('20000007060004', 'xtpz', '系统配置', NULL, '20000007060001', '0', '20000007060004.', '6');
INSERT INTO `sys_tree_node` (`id_`, `key_`, `name_`, `desc_`, `tree_id_`, `parent_id_`, `path_`,
                             `sn_`)
VALUES ('20000007060005', 'khxg', '客户相关', NULL, '20000007060001', '0', '20000007060005.', '7');
INSERT INTO `sys_tree_node` (`id_`, `key_`, `name_`, `desc_`, `tree_id_`, `parent_id_`, `path_`,
                             `sn_`)
VALUES ('20000008980003', 'bg', '办公', NULL, '20000008980001', '0', '20000008980003.', '7');
INSERT INTO `sys_tree_node` (`id_`, `key_`, `name_`, `desc_`, `tree_id_`, `parent_id_`, `path_`,
                             `sn_`)
VALUES ('20000008980004', 'ywlc', '业务流程', NULL, '20000008980001', '0', '20000008980004.', '8');

-- 系统参数
INSERT INTO `sys_properties`(`id_`, `name_`, `alias_`, `group_`, `value_`, `encrypt_`, `update_by_`,
                             `update_time_`, `create_by_`, `create_time_`, `description_`,
                             `environment_`)
VALUES ('405118036023705601', 's.k', 's.k', '系统参数',
        'f06f10d88d8261c6dacbae23f1704db8109590fe2dc6de0cd356ebd393a3e7a3d640b138b2d0e7201b11d63f78f98238',
        0, '1', '2019-05-14 12:46:12', NULL, '2018-12-21 22:31:04', '', 'DEV');
INSERT INTO `sys_properties`(`id_`, `name_`, `alias_`, `group_`, `value_`, `encrypt_`, `update_by_`,
                             `update_time_`, `create_by_`, `create_time_`, `description_`,
                             `environment_`)
VALUES ('6', '系统管理员', 'admin.account', '系统参数', 'admin', 0, '1', '2018-12-30 22:09:52', NULL, NULL,
        '系统管理员', 'DEV');

-- 系统脚本
INSERT INTO `sys_script` (`id_`, `name_`, `script_`, `category_`, `memo_`)
VALUES ('10000000000001', '获取流水号', 'return sysScript.getNextSerialNo(\"dayNo\");// 请赋值 流水号Alias',
        '系统内置', '获取流水号');
INSERT INTO `sys_script` (`id_`, `name_`, `script_`, `category_`, `memo_`)
VALUES ('10000000000002', '获取当前用户信息', 'sysScript.getCurrentUser().getFullname()', '系统内置',
        '获取用户的某一个属性');
INSERT INTO `sys_script` (`id_`, `name_`, `script_`, `category_`, `memo_`)
VALUES ('20000000570001', '获取当前组织ID',
        'import com.dstz.sys.util.ContextUtil;\n\nreturn ContextUtil.getCurrentGroupId();', '系统内置',
        '通过引入静态方法来调用系统脚本，这样不需要实现Iscript脚本 。');
INSERT INTO `sys_script` (`id_`, `name_`, `script_`, `category_`, `memo_`)
VALUES ('410230570225762305', '获取系统属性', 'sysScript.getProperty(\"admin.account\");', '系统内置',
        '获取系统属性，系统熟悉会分 开发，测试，生产不同环境');
INSERT INTO `sys_script` (`id_`, `name_`, `script_`, `category_`, `memo_`)
VALUES ('410230650073251841', '获取某个组织下的某些角色的人员列表',
        'orgScript.getSisByGroupAndRole(groupIds, roleCodes) ;\n\n//组织id，多个以“,”分隔；为空，则取当前用户所在主组织\n  //角色编码，多个以“,”分隔',
        '系统内置', '比如获取发起人的 部分负责人，orgScript.getSisByGroupAndRole(null, \"负责人角色\") ;');
INSERT INTO `sys_script` (`id_`, `name_`, `script_`, `category_`, `memo_`)
VALUES ('410230899184762881', '同步场景下、设置下一节点执行人',
        'bpmScriptUtil.persistenceFutureNodeUserSetting(variableScope,\"NodeId\");\n//配合人员脚本 bpmScriptUtil.getVariablesUserSetting(variableScope,\"nodeId\"); 使用',
        '流程脚本', '在同步节点时，对下一步执行人设置失败，可以使用该脚本在后置事件中，将人员设置持久化到流程变量中');

-- 数据源
INSERT INTO `sys_data_source_def` (`id_`, `name_`, `class_path_`, `attributes_json_`)
VALUES ('1', 'DruidDataSource数据源', 'com.alibaba.druid.pool.DruidDataSource',
        '[{\"comment\":\"url\",\"name\":\"url\",\"required\":true,\"type\":\"java.lang.String\"},{\"comment\":\"username\",\"name\":\"username\",\"required\":true,\"type\":\"java.lang.String\"},{\"comment\":\"password\",\"name\":\"password\",\"required\":true,\"type\":\"java.lang.String\"},{\"comment\":\"initialSize\",\"defaultValue\":\"1\",\"name\":\"initialSize\",\"required\":true,\"type\":\"int\"},{\"comment\":\"minIdle\",\"defaultValue\":\"10\",\"name\":\"minIdle\",\"required\":true,\"type\":\"int\"},{\"comment\":\"maxActive\",\"defaultValue\":\"10\",\"name\":\"maxActive\",\"required\":true,\"type\":\"int\"},{\"comment\":\"maxWait\",\"defaultValue\":\"6000\",\"name\":\"maxWait\",\"required\":true,\"type\":\"long\"},{\"comment\":\"timeBetweenEvictionRunsMillis\",\"defaultValue\":\"6000\",\"name\":\"timeBetweenEvictionRunsMillis\",\"required\":true,\"type\":\"long\"},{\"comment\":\"minEvictableIdleTimeMillis\",\"defaultValue\":\"30000\",\"name\":\"minEvictableIdleTimeMillis\",\"required\":true,\"type\":\"long\"},{\"comment\":\"连接失败后是否不再尝试\",\"defaultValue\":\"true\",\"name\":\"breakAfterAcquireFailure\",\"required\":true,\"type\":\"boolean\"},{\"comment\":\"校验sql\",\"defaultValue\":\"\",\"name\":\"validationQuery\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"testWhileIdle\",\"defaultValue\":\"true\",\"name\":\"testWhileIdle\",\"required\":true,\"type\":\"boolean\"},{\"comment\":\"testOnBorrow\",\"defaultValue\":\"false\",\"name\":\"testOnBorrow\",\"required\":true,\"type\":\"boolean\"},{\"comment\":\"testOnReturn\",\"defaultValue\":\"false\",\"name\":\"testOnReturn\",\"required\":true,\"type\":\"boolean\"},{\"comment\":\"poolPreparedStatements\",\"defaultValue\":\"true\",\"name\":\"poolPreparedStatements\",\"required\":true,\"type\":\"boolean\"},{\"comment\":\"maxPoolPreparedStatementPerConnectionSize\",\"defaultValue\":\"20\",\"name\":\"maxPoolPreparedStatementPerConnectionSize\",\"required\":true,\"type\":\"int\"},{\"comment\":\"filters\",\"defaultValue\":\"stat\",\"name\":\"filters\",\"required\":true,\"type\":\"java.lang.String\"},{\"comment\":\"removeAbandoned\",\"defaultValue\":\"true\",\"name\":\"removeAbandoned\",\"required\":true,\"type\":\"boolean\"},{\"comment\":\"removeAbandonedTimeout\",\"defaultValue\":\"2880\",\"name\":\"removeAbandonedTimeout\",\"required\":true,\"type\":\"int\"},{\"comment\":\"logAbandoned\",\"defaultValue\":\"true\",\"name\":\"logAbandoned\",\"required\":true,\"type\":\"boolean\"},{\"comment\":\"enable\",\"name\":\"enable\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"logDifferentThread\",\"name\":\"logDifferentThread\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"useGlobalDataSourceStat\",\"name\":\"useGlobalDataSourceStat\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"reStatEnable\",\"name\":\"reStatEnable\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"name\",\"name\":\"name\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"logWriter\",\"name\":\"logWriter\",\"required\":false,\"type\":\"java.io.PrintWriter\"},{\"comment\":\"loginTimeout\",\"name\":\"loginTimeout\",\"required\":false,\"type\":\"int\"},{\"comment\":\"maxIdle\",\"name\":\"maxIdle\",\"required\":false,\"type\":\"int\"},{\"comment\":\"failFast\",\"name\":\"failFast\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"dbType\",\"name\":\"dbType\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"queryTimeout\",\"name\":\"queryTimeout\",\"required\":false,\"type\":\"int\"},{\"comment\":\"proxyFilters\",\"name\":\"proxyFilters\",\"required\":false,\"type\":\"java.util.List\"},{\"comment\":\"oracle\",\"name\":\"oracle\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"useUnfairLock\",\"name\":\"useUnfairLock\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"timeBetweenLogStatsMillis\",\"name\":\"timeBetweenLogStatsMillis\",\"required\":false,\"type\":\"long\"},{\"comment\":\"clearFiltersEnable\",\"name\":\"clearFiltersEnable\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"notFullTimeoutRetryCount\",\"name\":\"notFullTimeoutRetryCount\",\"required\":false,\"type\":\"int\"},{\"comment\":\"maxWaitThreadCount\",\"name\":\"maxWaitThreadCount\",\"required\":false,\"type\":\"int\"},{\"comment\":\"phyTimeoutMillis\",\"name\":\"phyTimeoutMillis\",\"required\":false,\"type\":\"long\"},{\"comment\":\"maxEvictableIdleTimeMillis\",\"name\":\"maxEvictableIdleTimeMillis\",\"required\":false,\"type\":\"long\"},{\"comment\":\"driverClassName\",\"name\":\"driverClassName\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"transactionQueryTimeout\",\"name\":\"transactionQueryTimeout\",\"required\":false,\"type\":\"int\"},{\"comment\":\"exceptionSorterClassName\",\"name\":\"exceptionSorterClassName\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"defaultAutoCommit\",\"name\":\"defaultAutoCommit\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"defaultReadOnly\",\"name\":\"defaultReadOnly\",\"required\":false,\"type\":\"java.lang.Boolean\"},{\"comment\":\"defaultTransactionIsolation\",\"name\":\"defaultTransactionIsolation\",\"required\":false,\"type\":\"java.lang.Integer\"},{\"comment\":\"statLoggerClassName\",\"name\":\"statLoggerClassName\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"connectionProperties\",\"name\":\"connectionProperties\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"transactionThresholdMillis\",\"name\":\"transactionThresholdMillis\",\"required\":false,\"type\":\"long\"},{\"comment\":\"useOracleImplicitCache\",\"name\":\"useOracleImplicitCache\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"useLocalSessionState\",\"name\":\"useLocalSessionState\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"dupCloseLogEnable\",\"name\":\"dupCloseLogEnable\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"connectionErrorRetryAttempts\",\"name\":\"connectionErrorRetryAttempts\",\"required\":false,\"type\":\"int\"},{\"comment\":\"sharePreparedStatements\",\"name\":\"sharePreparedStatements\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"timeBetweenConnectErrorMillis\",\"name\":\"timeBetweenConnectErrorMillis\",\"required\":false,\"type\":\"long\"},{\"comment\":\"maxOpenPreparedStatements\",\"name\":\"maxOpenPreparedStatements\",\"required\":false,\"type\":\"int\"},{\"comment\":\"removeAbandonedTimeoutMillis\",\"name\":\"removeAbandonedTimeoutMillis\",\"required\":false,\"type\":\"long\"},{\"comment\":\"validationQueryTimeout\",\"name\":\"validationQueryTimeout\",\"required\":false,\"type\":\"int\"},{\"comment\":\"defaultCatalog\",\"name\":\"defaultCatalog\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"passwordCallbackClassName\",\"name\":\"passwordCallbackClassName\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"exceptionSorter\",\"name\":\"exceptionSorter\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"asyncCloseConnectionEnable\",\"name\":\"asyncCloseConnectionEnable\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"maxCreateTaskCount\",\"name\":\"maxCreateTaskCount\",\"required\":false,\"type\":\"int\"},{\"comment\":\"validConnectionCheckerClassName\",\"name\":\"validConnectionCheckerClassName\",\"required\":false,\"type\":\"java.lang.String\"},{\"comment\":\"accessToUnderlyingConnectionAllowed\",\"name\":\"accessToUnderlyingConnectionAllowed\",\"required\":false,\"type\":\"boolean\"},{\"comment\":\"numTestsPerEvictionRun\",\"name\":\"numTestsPerEvictionRun\",\"required\":false,\"type\":\"int\"}]');
INSERT INTO `sys_data_source` (`id_`, `key_`, `name_`, `desc_`, `db_type_`, `class_path_`,
                               `attributes_json_`)
VALUES ('1', 'dataSourceDefault', '本地数据源', '本地数据源', 'mysql', NULL, NULL);

-- 流水号
INSERT INTO `sys_serialno` (`id_`, `name_`, `alias_`, `regulation_`, `gen_type_`, `no_length_`,
                            `cur_date_`, `init_value_`, `cur_value_`, `step_`)
VALUES ('10000001620002', '每天使用一组流水号', 'dayNo', '{yyyy}{MM}{DD}{NO}', '1', '5', '20180710', '1',
        '1', '1');

-- 子系统
INSERT INTO `sys_subsystem`(`id_`, `name_`, `alias_`, `url_`, `open_type_`, `enabled_`,
                            `create_time_`, `is_default_`, `desc_`, `config_`, `create_by_`,
                            `update_time_`, `update_by_`)
VALUES ('1', '业务流程设计开发平台', 'agilebpm', NULL, NULL, 1, NULL, 1, '主系统', '用于系统特殊配置', NULL, NULL, NULL);


-- 面板授权
INSERT INTO `sys_authorization` (`rights_id_`, `rights_object_`, `rights_target_`, `rights_type_`,
                                 `rights_identity_`, `rights_identity_name_`,
                                 `rights_permission_code_`, `rights_create_time_`,
                                 `rights_create_by_`)
VALUES ('10000054820143', 'WORKBENCH', '10000053631203', 'all', 'user', '所有人', 'user-all',
        '2018-04-09 16:10:47', '1');
INSERT INTO `sys_authorization` (`rights_id_`, `rights_object_`, `rights_target_`, `rights_type_`,
                                 `rights_identity_`, `rights_identity_name_`,
                                 `rights_permission_code_`, `rights_create_time_`,
                                 `rights_create_by_`)
VALUES ('10000054820144', 'WORKBENCH', '10000053631202', 'all', 'user', '所有人', 'user-all',
        '2018-04-09 16:10:58', '1');
INSERT INTO `sys_authorization` (`rights_id_`, `rights_object_`, `rights_target_`, `rights_type_`,
                                 `rights_identity_`, `rights_identity_name_`,
                                 `rights_permission_code_`, `rights_create_time_`,
                                 `rights_create_by_`)
VALUES ('10000054820146', 'WORKBENCH', '10000051360122', 'all', 'user', '所有人', 'user-all',
        '2018-04-09 16:11:35', '1');
INSERT INTO `sys_authorization` (`rights_id_`, `rights_object_`, `rights_target_`, `rights_type_`,
                                 `rights_identity_`, `rights_identity_name_`,
                                 `rights_permission_code_`, `rights_create_time_`,
                                 `rights_create_by_`)
VALUES ('10000054820147', 'WORKBENCH', '10000049030124', 'all', 'user', '所有人', 'user-all',
        '2018-04-09 16:11:41', '1');
INSERT INTO `sys_authorization` (`rights_id_`, `rights_object_`, `rights_target_`, `rights_type_`,
                                 `rights_identity_`, `rights_identity_name_`,
                                 `rights_permission_code_`, `rights_create_time_`,
                                 `rights_create_by_`)
VALUES ('10000054820148', 'WORKBENCH', '10000047970167', 'all', 'user', '所有人', 'user-all',
        '2018-04-09 16:11:47', '1');
INSERT INTO `sys_authorization` (`rights_id_`, `rights_object_`, `rights_target_`, `rights_type_`,
                                 `rights_identity_`, `rights_identity_name_`,
                                 `rights_permission_code_`, `rights_create_time_`,
                                 `rights_create_by_`)
VALUES ('10000055210122', 'WORKBENCH', '10000053631205', 'all', 'user', '所有人', 'user-all',
        '2018-04-09 17:30:50', '1');
INSERT INTO `sys_authorization` (`rights_id_`, `rights_object_`, `rights_target_`, `rights_type_`,
                                 `rights_identity_`, `rights_identity_name_`,
                                 `rights_permission_code_`, `rights_create_time_`,
                                 `rights_create_by_`)
VALUES ('10000055980295', 'WORKBENCH', '10000053631201', 'all', 'user', '所有人', 'user-all',
        '2018-04-10 14:40:54', '1');
-- 默认布局
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313694701486081', '10000053631202', 25, 150, 0, 'default_layout',
        '2022-04-05 16:04:29');
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313694706466817', '10000053631201', 50, 150, 1, 'default_layout',
        '2022-04-05 16:04:29');
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313694715117569', '10000049030124', 100, 300, 2, 'default_layout',
        '2022-04-05 16:04:29');
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313694724030465', '10000047970167', 100, 504, 3, 'default_layout',
        '2022-04-05 16:04:29');
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313745007443969', '10000053631202', 25, 150, 0, '1', '2022-04-05 16:07:41');
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313745019240449', '10000053631201', 50, 150, 1, '1', '2022-04-05 16:07:41');
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313745031036929', '10000049030124', 100, 300, 2, '1', '2022-04-05 16:07:41');
INSERT INTO `sys_workbench_layout` (`id_`, `panel_id_`, `cust_width_`, `cust_height_`, `sn_`,
                                    `user_id_`, `create_time_`)
VALUES ('432313745037066241', '10000047970167', 100, 504, 3, '1', '2022-04-05 16:07:41');

-- 工作台案例
INSERT INTO `sys_workbench_panel` (`id_`, `alias_`, `name_`, `type_`, `desc_`, `data_type_`,
                                   `data_source_`, `auto_refresh_`, `width_`, `height_`,
                                   `display_content_`, `more_url_`, `create_time_`, `create_by_`,
                                   `update_time_`, `update_by_`, `delete_flag_`)
VALUES ('10000047970167', 'myCalendar', '我的日程', 'iframe', '我的日程', 'iframe',
        '/sys/schedule/scheduleDisplay.html', NULL, '75', '504', NULL,
        '/sys/schedule/scheduleDisplay.html', '2018-03-29 16:37:03', NULL, '2018-03-29 17:33:10',
        NULL, NULL);
INSERT INTO `sys_workbench_panel` (`id_`, `alias_`, `name_`, `type_`, `desc_`, `data_type_`,
                                   `data_source_`, `auto_refresh_`, `width_`, `height_`,
                                   `display_content_`, `more_url_`, `create_time_`, `create_by_`,
                                   `update_time_`, `update_by_`, `delete_flag_`)
VALUES ('10000049030124', 'list', '我的待办', 'basic', '测试', 'interface', 'bpmTaskManager.getTodoList',
        NULL, '75', '300',
        '<div class=\"row\">\n	 \n	<div class=\"col-sm-12\">\n		<div class=\"form-inline\">\n			<input type=\"text\" placeholder=\"搜索关键字\" class=\"input-sm form-control\" ng-model=\"param.subject$V\" > \n			<button type=\"button\" ng-click=\"loadPanelData()\" class=\"btn btn-sm btn-primary fa-search\"></button>\n		</div>\n	</div>\n</div>\n <div class=\"table-responsive\">\n	<table class=\"table table-striped\">\n		<thead>\n			<tr>\n				<th>任务名称</th>\n				<th>任务标题</th>\n				<th>创建时间</th>\n			</tr>\n		</thead>\n		<tbody>\n			<tr ng-repeat=\"task in list\">\n				<td>{{task.name}}</td>\n				<td><a ng-click=\"debugger;openFullWindow(\' /
        bpm / task /
        taskComplete.html?taskId=\'+this.task.id)\" >{{task.subject}}</a> </td>\n				<td>{{task.createTime}}</td>\n			</tr>\n		</tbody>\n	</table>\n</div>',
        '/bpm/my/todoTaskList.html', '2018-03-30 18:05:43', NULL, '2019-08-04 16:22:37', '1', NULL);
INSERT INTO `sys_workbench_panel` (`id_`, `alias_`, `name_`, `type_`, `desc_`, `data_type_`,
                                   `data_source_`, `auto_refresh_`, `width_`, `height_`,
                                   `display_content_`, `more_url_`, `create_time_`, `create_by_`,
                                   `update_time_`, `update_by_`, `delete_flag_`)
VALUES ('10000053631201', 'userInfo', '用户信息', 'basic', '用户信息展示', 'interface',
        'loginContext.getCurrentUser', '60', '25', '150',
        '<h4 class=\"no-margins\">{{userInfo.fullname}}</h4>\n<div class=\" pull-right  text-info\">{{userInfo.account}} <i class=\"fa-user-circle-o\"></i>\n</div>\n <small>{{userInfo.email}}</small>',
        '/org/user/userInfoEdit', '2018-03-08 09:50:19', NULL, '2018-03-29 16:08:54', NULL, NULL);
INSERT INTO `sys_workbench_panel` (`id_`, `alias_`, `name_`, `type_`, `desc_`, `data_type_`,
                                   `data_source_`, `auto_refresh_`, `width_`, `height_`,
                                   `display_content_`, `more_url_`, `create_time_`, `create_by_`,
                                   `update_time_`, `update_by_`, `delete_flag_`)
VALUES ('10000053631202', 'roleInfo', '角色信息', 'basic', '用户信息展示', 'interface',
        'loginContext.getCurrentUser', '60', '25', '150',
        '<p class=\"text-info\">{{roleInfo.email}}</p>\n<p class=\"text-info\">{{roleInfo.weixin}}</p>\n<p class=\"text-info\">{{roleInfo.mobile}}</p>',
        '1', '2018-03-12 11:26:16', NULL, '2018-03-29 16:20:53', NULL, NULL);
INSERT INTO `sys_workbench_panel` (`id_`, `alias_`, `name_`, `type_`, `desc_`, `data_type_`,
                                   `data_source_`, `auto_refresh_`, `width_`, `height_`,
                                   `display_content_`, `more_url_`, `create_time_`, `create_by_`,
                                   `update_time_`, `update_by_`, `delete_flag_`)
VALUES ('20000000570002', 'cacheDesign', '提前缓存设计器', 'iframe', NULL, 'iframe',
        '/flow-editor/modeler.html?modelId=20000000530051', NULL, '25', '20', NULL, NULL,
        '2018-08-17 15:06:33', NULL, '2018-08-17 15:06:33', NULL, NULL);


-- 系统菜单
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('1', '1', 'personOffice', '个人办公', '', 1, 1, 'slideshare', 'menu', 1, '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('1000000071000', '1', 'taskList', '任务列表', 'bpm/task/taskList.html', 1, 1, '', 'menu', 1,
        '10000000710005', '2016-11-16 20:04:26');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000000710005', '1', 'newFlow', '流程管理', NULL, 1, 1, '', 'menu', 1, '0',
        '2016-11-16 19:57:50');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000000710006', '1', 'list', '流程列表', 'bpm/definition/definitionList.html', 1, 1, '',
        'menu', 1, '10000000710005', '2016-11-16 20:04:26');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000001480006', '1', 'sysres', '资源管理', 'sys/sysResource/sysResourceList.html', 1, 1, '',
        'menu', 1, '44', '2017-03-16 23:52:15');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000001640004', '1', 'formCustDialogList', '自定义对话框',
        'form/formCustDialog/formCustDialogList.html', 1, 1, '', 'menu', 2, '56',
        '2017-04-05 23:38:15');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000001640007', '1', 'combinDialog', '组合对话框', '/form/combinateDialog/combinateDialogList',
        0, 1, '', 'menu', 2, '56', '2017-04-05 23:39:58');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000034500201', '1', 'gjjjr', '国家节假日', 'sys/holidayConf/holidayConfList.html', 1, 1, '',
        'menu', 1, '44', '2018-01-18 19:26:18');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000045331201', '1', 'rcb', '我的日程', 'sys/schedule/scheduleDisplay.html', 1, 1, '', 'menu',
        6, '1', '2018-01-31 15:10:48');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000047101201', '1', 'rcgl', '日程管理', 'sys/schedule/scheduleList.html', 1, 1, '', 'menu',
        6, '1', '2018-02-02 10:56:44');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('10000052971201', '1', 'gztgl', '工作台管理', 'sys/workbenchPanel/workbenchPanelList.html', 1, 1,
        '', 'menu', 2, '44', '2018-02-28 14:52:46');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('11', '1', 'myDraft', '我的草稿', 'bpm/my/draftList.html', 1, 1, '', 'menu', 1, '7', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('18', '1', 'orgManager', '用户组织', '', 1, 1, 'users', 'menu', 1, '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('2', '1', 'flowEvent', '事项办理', '', 1, 1, '', 'menu', 1, '1', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('20000001570004', '1', 'sysDataSourceDefList', '系统数据源模板',
        'sys/sysDataSourceDef/sysDataSourceDefList.html', 1, 1, '', 'menu', 1, '44',
        '2018-02-27 15:50:44');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('20000002880001', '1', 'sysTreeList', '系统树', 'sys/sysTree/sysTreeList.html', 1, 1, '',
        'menu', 3, '56', '2018-03-19 14:51:32');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('20000002980001', '1', 'copeTask', '我的抄送', NULL, 0, 1, '', 'menu', 1, '2',
        '2018-03-25 19:07:18');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('20000003070153', '1', 'processInstanceList', '流程实例', 'bpm/instance/instanceList.html', 1,
        1, '', 'menu', 1, '10000000710005', '2018-03-18 17:01:06');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('20000010520001', '1', 'myTodo', '待办事项', 'bpm/my/todoTaskList.html', 1, 1, '', 'menu', 1,
        '2', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('21', '1', 'roleMgr', '角色管理', 'org/role/roleList.html', 1, 1, '', 'menu', 1, '18', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('23', '1', 'flowManager', '表单管理', '', 1, 1, '', 'menu', 1, '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('25', '1', 'boManager', '业务对象', '', 1, 1, 'fa-database', 'menu', 1, '23', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('26', '1', 'businessTableList', '业务实体', 'bus/businessTable/businessTableList.html', 1, 1,
        '', 'menu', 1, '25', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('27', '1', 'businessObjectList', '业务对象', 'bus/buinessObject/businessObjectList.html', 1, 1,
        '', 'menu', 1, '25', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('28', '1', 'formDefManager', '表单定义', '', 1, 1, 'fa-th-list', 'menu', 1, '23', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('30', '1', 'boForm', '业务表单', 'form/formDef/formDefList.html?formType=pc', 1, 1, '', 'menu',
        1, '28', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('31', '1', 'mobileForm', '手机表单', 'form/formDef/formDefList.html?formType=mobile', 1, 1, '',
        'menu', 1, '28', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('32', '1', 'formTemplate', '表单模版', 'form/formTemplate/formTemplateList.html', 1, 1, '',
        'menu', 1, '28', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('33', '1', 'vueForm', 'vue表单', 'form/formDef/formDefList.html?formType=pc_vue', 0, 1, '',
        'menu', 0, '28', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('4', '1', 'myHandledEvent', '办理历史', 'bpm/my/approveList.html', 1, 1, '', 'menu', 1, '2',
        NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('403205519290925057', '1', 'errLog', '异常日志', 'sys/sysLogErr/sysLogErrList.html', 1, 1, '',
        'menu', 5, '56', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('404818977800323073', '1', 'icon', '字体图标', 'sys/icon/iconSelector.html', 1, 1, '', 'menu',
        9, '56', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406714104337661953', '1', 'userAgencyLogList', '代理记录',
        'bpmplugin/userAgencyConfig/userAgencyLogList.html', 1, 1, '', 'menu', 6, '7', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406714125032357889', '1', 'userAgencyConfig', '流程代理',
        'bpmplugin/userAgencyConfig/tabs.html', 1, 1, '', 'menu', 5, '7', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406719341437911041', '1', 'instanceList', '流程实例-部门', 'bpm/instance/instanceList_org.html',
        1, 1, '', 'menu', 6, '10000000710005', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406902948342530049', '2', 'userOrg', '用户组织', '', 1, 1, '', 'menu', 1, '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406902978254209025', '2', 'userManager', '用户管理', 'org/userList', 1, 1, '', 'menu', 1,
        '406902948342530049', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406902988961480705', '2', 'orgManager', '组织管理', 'org/groupList', 1, 1, '', 'menu', 1,
        '406902948342530049', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406903009770733569', '2', 'roleManager', '角色管理', '/org/roleList', 1, 1, '', 'menu', 1,
        '406902948342530049', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406903052897091585', '2', 'userOffice', '个人办公', NULL, 1, 1, '', 'menu', 1, '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406903063443144705', '2', 'mattersHandling', '事项办理', NULL, 1, 1, '', 'menu', 1,
        '406903052897091585', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406903071843287041', '2', 'todoTaskList', '待办事项', '/bpm/my/todoTaskList', 1, 1, '', 'menu',
        1, '406903063443144705', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('406903080497709057', '2', 'approveHistoryList', '办理历史', '/bpm/my/approveList', 1, 1, '',
        'menu', 1, '406903063443144705', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('407371267579052033', '2', 'mattersApply', '事项申请', NULL, 1, 1, '', 'menu', 2,
        '406903052897091585', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('407371280996368385', '2', 'myDraft', '我的草稿', '/bpm/my/draftList', 1, 1, '', 'menu', 2,
        '407371267579052033', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('407371284953432065', '2', 'startFlow', '发起申请', 'bpm/my/definitionList', 1, 1, '', 'menu',
        1, '407371267579052033', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('407371291024424961', '2', 'applyHistory', '申请历史', '/bpm/my/applyTaskList', 1, 1, '',
        'menu', 3, '407371267579052033', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('43', '1', 'sysSetting', '系统配置', '', 1, 1, 'cogs', 'menu', 1, '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('44', '1', 'systemMgr', '系统设置', '', 1, 1, '', 'menu', 1, '43', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('46', '1', 'dicManager', '数据字典', 'sys/dataDict/dataDictList.html', 1, 1, '', 'menu', 1,
        '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('49', '1', 'syspropertyMgr', '系统属性管理', 'sys/sysProperties/sysPropertiesList.html', 1, 1, '',
        'menu', 1, '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('50', '1', 'sysDataSourceList', '系统数据源', 'sys/sysDataSource/sysDataSourceList.html', 1, 1,
        '', 'menu', 1, '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('54', '1', 'subSystemMgr', '子系统管理', '/base/base/subsystem/subsystemList', 0, 1, '', 'menu',
        1, '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('56', '1', 'flowAssist', '开发辅助', '', 1, 1, '', 'menu', 1, '43', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('60', '1', 'serialNoMgr', '流水号', 'sys/serialNo/serialNoList.html', 1, 1, '', 'menu', 1,
        '56', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('61', '1', 'scriptMgr', '常用脚本', 'sys/script/scriptList.html', 1, 1, '', 'menu', 1, '56',
        NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('7', '1', 'myStartEvent', '事项申请', '', 1, 1, '', 'menu', 1, '1', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('8', '1', 'myStartFlow', '发起申请', 'bpm/my/definitionList.html', 1, 1, '', 'menu', 1, '7',
        NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`,
                            `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`)
VALUES ('9', '1', 'myRequest', '申请历史', 'bpm/my/applyTaskList.html', 1, 1, '', 'menu', 1, '7', NULL);

-- FORM 模块

-- 表单管理菜单启用
UPDATE `sys_resource`
SET `ENABLE_`='1'
WHERE (`ID_` = '23');
-- 默认使用 vue 表单
UPDATE `sys_resource`
SET `ENABLE_`='0'
WHERE (`ID_` = '30');
UPDATE `sys_resource`
SET `ENABLE_`='1'
WHERE (`ID_` = '33');

-- 表单初始化模板

INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`,
                             `key_`)
VALUES ('410230187015012353', '单列模板', 'pc', 'main',
        '<table class=\"form-table\">\r\n	<#list relation.table.columnsWithOutHidden as column>\r\n	<tr>								\r\n		<th>${column.comment}</th>\r\n		<td>${generator.getColumn(column,relation)}</td>								\r\n	</tr>\r\n	</#list>\r\n</table>\r\n${getOne2OneChild(relation)}\r\n\r\n\r\n\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<tr>\r\n							<th>${column.comment}</th>\r\n							<td>${generator.getColumn(column,relation)} </td>\r\n						</tr>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"#\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>',
        '单列模板', '0', 'mainOneColumn');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`,
                             `key_`)
VALUES ('410230187016323073', '两列模板', 'pc', 'main',
        '<table class=\"form-table\">\r\n	<#assign index=1>\r\n	<#list relation.table.columnsWithOutHidden as column>\r\n	<#if index==1>\r\n	<tr>\r\n	</#if>								\r\n		<th>${column.comment}</th>\r\n		<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>								\r\n	<#if field.isSeparator==true || !column_has_next || index==2>\r\n	</tr>\r\n	<#assign index=0>\r\n	</#if>\r\n	<#assign index=index+1>\r\n	</#list>\r\n</table>\r\n${getOne2OneChild(relation)}\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#assign index=1>\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<#if index==1>\r\n						<tr>\r\n						</#if>\r\n							<th>${column.comment}</th>\r\n							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>\r\n						<#if !column_has_next || index==2>\r\n						</tr>\r\n						<#assign index=0>\r\n						</#if> \r\n						<#assign index=index+1>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"#\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n\r\n<#function getColspan index,hasNext>\r\n	<#assign rtn=\"\">\r\n	\r\n	 <#if !hasNext && index !=2>\r\n		<#assign rtn=\"colspan=\'\"+((2-index)*2+1)+\"\'\"> \r\n	</#if>\r\n	\r\n	<#return rtn>\r\n</#function>', '两列模板', '0', 'mainTwoColumn');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187017371649', '三列模板', 'pc', 'main', '<table class=\"form-table\">\r\n	<#assign index=1>\r\n	<#list relation.table.columnsWithOutHidden as column>\r\n		<#if index==1>\r\n		<tr>\r\n		</#if>\r\n			<th>${column.comment}</th>\r\n			<td ${getColspan(index,column_has_next)}> ${generator.getColumn(column,relation)} </td>\r\n		<#if !column_has_next || index==3>\r\n		</tr>\r\n		<#assign index=0>\r\n		</#if> \r\n		<#assign index=index+1>\r\n	</#list>\r\n</table>\r\n ${getOne2OneChild(relation)}\r\n \r\n \r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#assign index=1>\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<#if index==1>\r\n						<tr>\r\n						</#if>\r\n							<th>${column.comment}</th>\r\n							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>\r\n						<#if !column_has_next || index==3>\r\n						</tr>\r\n						<#assign index=0>\r\n						</#if> \r\n						<#assign index=index+1>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"#\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getColspan index,hasNext>\r\n	<#assign rtn=\"\">\r\n	\r\n	 <#if !hasNext && index !=3>\r\n		<#assign rtn=\"colspan=\'\"+((3-index)*2+1)+\"\'\"> \r\n	</#if>\r\n	\r\n	<#return rtn>\r\n</#function>', '三列模板', '0', 'mainThreeColumn');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187018158081', '四列模板', 'pc', 'main', '<table class=\"form-table\">\r\n	<#assign index=1>\r\n	<#list relation.table.columnsWithOutHidden as column>\r\n		<#if index==1>\r\n		<tr>\r\n		</#if>\r\n			<th>${column.comment}</th>\r\n			<td ${getColspan(index,column_has_next)}> ${generator.getColumn(column,relation)} </td>\r\n		<#if !column_has_next || index==4>\r\n		</tr>\r\n		<#assign index=0>\r\n		</#if> \r\n		<#assign index=index+1>\r\n	</#list>\r\n</table>\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#assign index=1>\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<#if index==1>\r\n						<tr>\r\n						</#if>\r\n							<th>${column.comment}</th>\r\n							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>\r\n						<#if !column_has_next || index==4>\r\n						</tr>\r\n						<#assign index=0>\r\n						</#if> \r\n						<#assign index=index+1>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"#\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getColspan index,hasNext>\r\n	<#assign rtn=\"\">\r\n	\r\n	 <#if (!hasNext || isSeparator==true) && index !=4>\r\n		<#assign rtn=\"colspan=\'\"+((4-index)*2+1)+\"\'\"> \r\n	</#if>\r\n	\r\n	<#return rtn>\r\n</#function>', '四列模板', '0', 'mainFourColumn');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187018682369', '子表单列模板', 'pc', 'subTable', '<div ${generator.getSubAttrs(relation)} ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >\r\n	<div class=\"ibox-title\"><span class=\"title\">${relation.tableComment}</span>\r\n		<a href=\"javascript:void(0)\" class=\"btn btn-primary btn-sm fa fa-plus\" ng-model=\"${generator.getScopePath(relation)}\" ab-sub-add=\"initData.${relation.busObj.key}.${relation.tableKey}\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">添加</a>\r\n	</div>\r\n	<div class=\"ibox-content\" ng-repeat=\"${relation.tableKey} in ${generator.getScopePath(relation)} track by $index\"> ${getOne2ManyChild(relation)}<a class=\"btn btn-danger btn-xs fa fa-delete pull-right\" ng-click=\"ArrayTool.del($index,${generator.getScopePath(relation)})\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\"> 移除</a>\r\n		<table class=\"form-table\">\r\n		<#list relation.table.columnsWithOutHidden as column>\r\n			<tr>\r\n				<th>${column.comment}</th>\r\n				<td>${generator.getColumn(column,relation)} </td>\r\n			</tr>\r\n		</#list>\r\n		</table>\r\n		 ${getOne2OneChild(relation)}\r\n	</div>\r\n</div>\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<tr>\r\n							<th>${column.comment}</th>\r\n							<td>${generator.getColumn(column,relation)} </td>\r\n						</tr>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"javascript:void(0)\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>', '单列模板', '0', 'subOneColumn');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187019468801', '子表两列模板', 'pc', 'subTable', '<div ${generator.getSubAttrs(relation)} ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >\r\n	<div class=\"ibox-title\"><span class=\"title\">${relation.tableComment}</span>\r\n		<a href=\"###\" class=\"btn btn-primary btn-sm fa fa-plus\" ng-model=\"${generator.getScopePath(relation)}\" ab-sub-add=\"initData.${relation.busObj.key}.${relation.tableKey}\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >添加</a>\r\n	</div>\r\n	<div class=\"ibox-content\" ng-repeat=\"${relation.tableKey} in ${generator.getScopePath(relation)} track by $index\"> ${getOne2ManyChild(relation)}<a class=\"btn btn-danger btn-xs fa fa-delete pull-right\" ng-click=\"ArrayTool.del($index,${generator.getScopePath(relation)})\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\"> 移除</a>\r\n		<table class=\"form-table\">\r\n		<#assign index=1>\r\n		<#list relation.table.columnsWithOutHidden as column>\r\n			<#if index==1>\r\n			<tr>\r\n			</#if>\r\n				<th>${column.comment}</th>\r\n				<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>\r\n			<#if !column_has_next || index==2>\r\n			</tr>\r\n			<#assign index=0>\r\n			</#if> \r\n			<#assign index=index+1>\r\n		</#list>\r\n		</table>\r\n		 ${getOne2OneChild(relation)}\r\n	</div>\r\n</div>\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#assign index=1>\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<#if index==1>\r\n						<tr>\r\n						</#if>\r\n							<th>${column.comment}</th>\r\n							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>\r\n						<#if !column_has_next || index==2>\r\n						</tr>\r\n						<#assign index=0>\r\n						</#if> \r\n						<#assign index=index+1>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"#\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getColspan index,hasNext>\r\n	<#assign rtn=\"\">\r\n		 <#if !hasNext && index !=2>\r\n			<#assign rtn=\"colspan=\'\"+((2-index)*2+1)+\"\'\"> \r\n		</#if>\r\n<#return rtn>\r\n</#function> ', '两列模板', '0', 'subTwoColumn');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187020517377', '子表三列模板', 'pc', 'subTable', '<div ${generator.getSubAttrs(relation)} ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >\r\n	<div class=\"ibox-title\"><span class=\"title\">${relation.tableComment}</span>\r\n		<a href=\"###\" class=\"btn btn-primary btn-sm fa fa-plus\" ng-model=\"${generator.getScopePath(relation)}\" ab-sub-add=\"initData.${relation.busObj.key}.${relation.tableKey}\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">添加</a>\r\n	</div>\r\n	<div class=\"ibox-content\" ng-repeat=\"${relation.tableKey} in ${generator.getScopePath(relation)} track by $index\"> ${getOne2ManyChild(relation)}<a class=\"btn btn-danger btn-xs fa fa-delete pull-right\" ng-click=\"ArrayTool.del($index,${generator.getScopePath(relation)})\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\"> 移除</a>\r\n		<table class=\"form-table\">\r\n		<#assign index=1>\r\n		<#list relation.table.columnsWithOutHidden as column>\r\n			<#if index==1>\r\n			<tr>\r\n			</#if>\r\n				<th>${column.comment}</th>\r\n				<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>\r\n			<#if !column_has_next || index==3>\r\n			</tr>\r\n			<#assign index=0>\r\n			</#if> \r\n			<#assign index=index+1>\r\n		</#list>\r\n		</table>\r\n		 ${getOne2OneChild(relation)}\r\n	</div>\r\n</div>\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#assign index=1>\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<#if index==1>\r\n						<tr>\r\n						</#if>\r\n							<th>${column.comment}</th>\r\n							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>\r\n						<#if !column_has_next || index==3>\r\n						</tr>\r\n						<#assign index=0>\r\n						</#if> \r\n						<#assign index=index+1>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"#\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getColspan index,hasNext>\r\n	<#assign rtn=\"\">\r\n		 <#if (!hasNext || isSeparator==true) && index !=3>\r\n			<#assign rtn=\"colspan=\'\"+((3-index)*2+1)+\"\'\"> \r\n		</#if>\r\n<#return rtn>\r\n</#function>', '三列模板', '0', 'subThreeColumn');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187021565953', '子表单行模板', 'pc', 'subTable', '<div ${generator.getSubAttrs(relation)} ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >\r\n	<div class=\"ibox-title\"><span class=\"title\">${relation.tableComment}</span>\r\n		<a href=\"javascript:void(0)\" class=\"btn btn-primary btn-sm fa fa-plus\" ng-model=\"${generator.getScopePath(relation)}\" ab-sub-add=\"initData.${relation.busObj.key}.${relation.tableKey}\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">添加</a>\r\n	</div>\r\n	<div class=\"ibox-content\">\r\n		<table class=\"form-table\">\r\n			<thead>\r\n				<tr>\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n					<th>${column.comment}</th>\r\n					</#list>	\r\n					<th>操作</th>\r\n				</tr>\r\n			</thead>\r\n			<tr ng-repeat=\"${relation.tableKey} in ${generator.getScopePath(relation)} track by $index\">\r\n			<#list relation.table.columnsWithOutHidden as column>\r\n				<td>${generator.getColumn(column,relation)} </td>\r\n			</#list>\r\n			<td>${getOne2ManyChild(relation)}\r\n			<a class=\"btn btn-danger btn-sm fa fa-delete\" ng-click=\"ArrayTool.del($index,${generator.getScopePath(relation)})\" ab-edit-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\"> </a></td>\r\n			</tr>\r\n		</table>\r\n	</div>\r\n</div>\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${generator.getSubAttrs(relation)} >\r\n				<div class=\"block-title\"> <span class=\"title\">${relation.tableComment} </span>\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				<table class=\"form-table\">\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<tr>\r\n							<th>${column.comment}</th>\r\n							<td>${generator.getColumn(column,relation)} </td>\r\n						</tr>\r\n					</#list>\r\n				</table>\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"javascript:void(0)\" class=\"btn btn-link btn-sm fa fa-detail\" ng-model=\"${relation.parent.tableKey}\" ab-sub-detail=\"${relation.getBusObj().getKey()}-${relation.tableKey}\" ab-show-permission=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>', '单行模板，暂时不支持一对一', '0', 'subOneline');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187022352385', 'mobile主表模板', 'mobile', 'main', '<script>\r\n	<!--脚本将会混入表单自定义表单控件-->\r\n	window.custFormComponentMixin ={\r\n			data: function () {\r\n		    	return {\"test\":\"helloWorld\"};\r\n		  	},\r\n			created:function(){\r\n				console.log(\"混入对象的钩子被调用\");\r\n			},methods:{\r\n				testaaa:function(){alert(1)}\r\n			}\r\n	}\r\n</script>\r\n<div class=\"weui-cells weui-cells_form\">\r\n<#list relation.table.columnsWithOutHidden as column>\r\n	<div class=\"weui-cell\" v-ab-permission:show=\"${mobileGenerator.getPermissionPath(column,relation)}\">\r\n        <div class=\"weui-cell__hd\"><label class=\"weui-label\">${column.comment}</label></div>\r\n        <div class=\"weui-cell__bd\">${mobileGenerator.getColumn(column,relation)}</div>\r\n	</div>\r\n</#list>\r\n</div>\r\n${getOne2OneChild(relation)}\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${mobileGenerator.getSubAttrs(relation)} >\r\n				<div class=\"weui-cells__title\"> ${relation.tableComment}\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				\r\n				<div class=\"weui-cells weui-cells_form\">\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<div class=\"weui-cell\" v-ab-permission:show=\"${mobileGenerator.getPermissionPath(column,relation)}\">\r\n					        <div class=\"weui-cell__hd\"><label class=\"weui-label\">${column.comment}</label></div>\r\n					        <div class=\"weui-cell__bd\">${mobileGenerator.getColumn(column,relation)}</div>\r\n					    </div>\r\n					</#list>\r\n				</div>\r\n				\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n		<div class=\"pull-left\"><#list relationList as relation><a href=\"#\"  v-on:click=\"showSubTable(${relation.parent.tableKey},\'${relation.tableKey}\')\" class=\"fa fa-list-alt weui-btn weui-btn_mini weui-btn_primary\"  v-ab-permission:show=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">${relation.tableComment}详情</a>\r\n		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>', 'mobile主表模板', '0', 'mobileMainTemplate');
INSERT INTO `form_template` (`id_`, `name_`, `form_type_`, `type_`, `html_`, `desc_`, `editable_`, `key_`) VALUES ('410230187023138817', 'mobile子表模板', 'mobile', 'subTable', '<div ${mobileGenerator.getSubAttrs(relation)} v-ab-permission:show=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\" >\r\n	<#if mobileGenerator.isThreeChildren(relation)><popup v-model=\"subTableDialog.${relation.tableKey}\" position=\"bottom\" height=\"80%\"> </#if>\r\n	<div class=\"weui-cells__title\" ><span class=\"title\">${relation.tableComment}</span>\r\n		<ab-sub-add href=\"javascript:;\" v-model=\"${mobileGenerator.getScopePath(relation)}\" v-bind:init-data=\"initData.${relation.busObj.key}.${relation.tableKey}\" class=\"fa fa-plus weui-btn weui-btn_mini weui-btn_primary\" v-ab-permission:edit=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\"></ab-sub-add> \r\n	</div>\r\n	<div class=\"weui-cells weui-cells_form\" v-for=\"(${relation.tableKey},index) in ${mobileGenerator.getScopePath(relation)}\">\r\n		<div class=\"weui-cells__title\">\r\n		 	${getOne2ManyChild(relation)}\r\n		 	<a href=\"javascript:;\" v-sub-del=\"[${mobileGenerator.getScopePath(relation)},index]\" class=\"fa fa-trash weui-btn weui-btn_mini weui-btn_warn pull-right\" v-ab-permission:edit=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\"></a> \r\n		 </div>\r\n		<#list relation.table.columnsWithOutHidden as column>\r\n			<div class=\"weui-cell\" v-ab-permission:show=\"${mobileGenerator.getPermissionPath(column,relation)}\">\r\n		        <div class=\"weui-cell__hd\"><label class=\"weui-label\">${column.comment}</label></div>\r\n				<div class=\"weui-cell__bd\">${mobileGenerator.getColumn(column,relation)}</div>\r\n	    	</div>\r\n		</#list>\r\n		 ${getOne2OneChild(relation)}\r\n	</div>\r\n	<#if mobileGenerator.isThreeChildren(relation) ></popup></#if>\r\n</div>\r\n\r\n<#function getOne2OneChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToOne\')>\r\n	<#assign rtn>\r\n		<#list relationList as relation>\r\n			<div ${mobileGenerator.getSubAttrs(relation)} >\r\n				<div class=\"weui-cells__title\"> ${relation.tableComment}\r\n					${getOne2ManyChild(relation)}\r\n				</div>\r\n				\r\n				<div class=\"weui-cells weui-cells_form\">\r\n					<#list relation.table.columnsWithOutHidden as column>\r\n						<div class=\"weui-cell\" v-ab-permission:show=\"${mobileGenerator.getPermissionPath(column,relation)}\">\r\n					        <div class=\"weui-cell__hd\"><label class=\"weui-label\">${column.comment}</label></div>\r\n					        <div class=\"weui-cell__bd\">${mobileGenerator.getColumn(column,relation)}</div>\r\n					    </div>\r\n					</#list>\r\n				</div>\r\n				\r\n				${getOne2OneChild(relation)}\r\n			</div>\r\n		</#list>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>\r\n\r\n<#function getOne2ManyChild relation> \r\n	<#assign relationList = relation.getChildren(\'oneToMany\')>\r\n	<#assign rtn>\r\n		 <#if relationList?? && (relationList?size > 0) >\r\n			<div class=\"pull-left\"><#list relationList as relation><a href=\"#\"  v-on:click=\"showSubTable(${relation.parent.tableKey},\'${relation.tableKey}\')\" class=\"fa fa-list-alt weui-btn weui-btn_mini weui-btn_primary\"  v-ab-permission:show=\"tablePermission.${relation.busObj.key}.${relation.tableKey}\">${relation.tableComment}详情</a>		</#list>\r\n		</div>\r\n		</#if>\r\n	</#assign>\r\n	<#return rtn>\r\n</#function>', 'mobile子表模板', '0', 'mobileSubTemplate');


-- 系统对话框
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('10000005290006', 'zdydhklb', '自定义对话框列表', '自定义对话框列表', 'list', 'dataSourceDefault', '本地数据源', 'table', 'form_cust_dialog', 1, 10, 1300, 800, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name_\",\"formatter\":\"\",\"showName\":\"名字\"},{\"columnName\":\"key_\",\"showName\":\"别名\"},{\"columnName\":\"style_\",\"formatter\":\"var span = \'<span class=\\\"label label-primary\\\">表</span>\';\\n\\t\\tif (value == \'view\')\\n\\t\\t\\tspan = \'<span class=\\\"label label-warning\\\">视图</span>\';\\n\\t\\treturn span;\",\"showName\":\"显示类型\"}]', '[{\"columnName\":\"key_\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"别名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name_\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"名字\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"}]', '[{\"columnName\":\"id_\",\"sortType\":\"desc\"}]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('10000005470001', 'ywblb', '业务表列表', '业务表列表', 'list', 'dataSourceDefault', '本地数据源', 'table', 'bus_table', 1, 10, 1300, 600, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"comment_\",\"showName\":\"描述\"},{\"columnName\":\"key_\",\"showName\":\"业务表key\"},{\"columnName\":\"name_\",\"showName\":\"表名\"}]', '[{\"columnName\":\"key_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"业务表key\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"表名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"ds_key_\",\"returnName\":\"dsKey\"},{\"columnName\":\"comment_\",\"returnName\":\"comment\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000002250001', 'busObjectSelect', '业务对象选择', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'bus_object', 1, 10, 800, 600, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"key_\",\"showName\":\"别名\"},{\"columnName\":\"name_\",\"showName\":\"名字\"},{\"columnName\":\"desc_\",\"showName\":\"描述\"}]', '[{\"columnName\":\"key_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"key\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名字\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"desc_\",\"returnName\":\"desc\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000003130001', 'userSelector', '用户查询', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'sys_user', 1, 10, 930, 660, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name\",\"showName\":\"姓名\"},{\"columnName\":\"code\",\"showName\":\"编号\"},{\"columnName\":\"username\",\"showName\":\"账号\"},{\"columnName\":\"telephone\",\"showName\":\"手机号码\"}]', '[{\"columnName\":\"code\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"编号\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"姓名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"username\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"账号\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"available\",\"condition\":\"EQ\",\"dbType\":\"number\",\"showName\":\"0:禁用，1正常\",\"value\":{\"text\":\"1\"},\"valueSource\":\"fixedValue\"}]', '[{\"columnName\":\"id\",\"returnName\":\"id\"},{\"columnName\":\"name\",\"returnName\":\"name\"},{\"columnName\":\"username\",\"returnName\":\"account\"},{\"columnName\":\"code\",\"returnName\":\"code\"},{\"columnName\":\"telephone\",\"returnName\":\"telephone\"}]', '[{\"columnName\":\"code\",\"sortType\":\"asc\"}]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000003160001', 'orgSelector', '组织选择框', NULL, 'tree', 'dataSourceDefault', '本地数据源', 'table', 'sys_dept', 1, 10, 800, 600, 1, 1, '{\"id\":\"id\",\"pid\":\"parent_id\",\"pidInitVal\":\"0\",\"pidInitValScript\":false,\"showColumn\":\"name\"}', '[]', '[{\"columnName\":\"available\",\"condition\":\"EQ\",\"dbType\":\"number\",\"showName\":\"状态\",\"value\":{\"text\":\"1\"},\"valueSource\":\"fixedValue\"}]', '[{\"columnName\":\"id\",\"returnName\":\"id\"},{\"columnName\":\"name\",\"returnName\":\"name\"},{\"columnName\":\"parent_id\",\"returnName\":\"parent_id\"}]', '[{\"columnName\":\"code\",\"sortType\":\"asc\"}]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000003160002', 'roleSelector', '角色对话框', '', 'list', 'dataSourceDefault', '本地数据源', 'table', 'sys_role', 1, 10, 800, 600, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name\",\"showName\":\"名称\"},{\"columnName\":\"code\",\"showName\":\"编号\"}]', '[{\"columnName\":\"code\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"编号\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名称\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"available\",\"condition\":\"EQ\",\"dbType\":\"number\",\"showName\":\"状态\",\"value\":{\"text\":\"1\"},\"valueSource\":\"fixedValue\"}]', '[{\"columnName\":\"id\",\"returnName\":\"id\"},{\"columnName\":\"name\",\"returnName\":\"name\"},{\"columnName\":\"code\",\"returnName\":\"code\"}]', '[{\"columnName\":\"code\",\"sortType\":\"asc\"}]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000003460003', 'formSelector', '表单选择框', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'form_def', 1, 10, 800, 600, 1, 0, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name_\",\"showName\":\"名字\"},{\"columnName\":\"key_\",\"showName\":\"key\"},{\"columnName\":\"desc_\",\"showName\":\"描述\"},{\"columnName\":\"bo_name_\",\"showName\":\"业务对象\"},{\"columnName\":\"bo_key_\",\"showName\":\"业务对象key\"}]', '[{\"columnName\":\"bo_key_\",\"condition\":\"IN\",\"dbType\":\"varchar\",\"showName\":\"别名\",\"value\":{\"ctrlType\":\"\"},\"valueSource\":\"param\"},{\"columnName\":\"type_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"分类\",\"value\":{},\"valueSource\":\"param\"}]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"desc_\",\"returnName\":\"desc\"},{\"columnName\":\"group_id_\",\"returnName\":\"groupId\"},{\"columnName\":\"bo_key_\",\"returnName\":\"boKey\"},{\"columnName\":\"bo_name_\",\"returnName\":\"boName\"},{\"columnName\":\"type_\",\"returnName\":\"type_\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000004660004', 'getMyUsablePanels', '获取可用面板', '面板布局获取可用的面板', 'list', 'dataSourceDefault', '本地数据源', 'table', 'workbenchPanelManager.getMyUsablePanels', 1, 10, 800, 600, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name\",\"showName\":\"名字\"},{\"columnName\":\"desc\",\"showName\":\"描述\"}]', '[{\"columnName\":\"layoutKey\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"layoutKey\",\"value\":{},\"valueSource\":\"param\"}]', '[{\"columnName\":\"type\",\"returnName\":\"type\"},{\"columnName\":\"alias\",\"returnName\":\"alias\"},{\"columnName\":\"name\",\"returnName\":\"name\"},{\"columnName\":\"type\",\"returnName\":\"type\"},{\"columnName\":\"desc\",\"returnName\":\"desc\"},{\"columnName\":\"dataType\",\"returnName\":\"dataType\"},{\"columnName\":\"dataSource\",\"returnName\":\"dataSource\"},{\"columnName\":\"autoRefresh\",\"returnName\":\"autoRefresh\"},{\"columnName\":\"width\",\"returnName\":\"width\"},{\"columnName\":\"height\",\"returnName\":\"height\"},{\"columnName\":\"displayContent\",\"returnName\":\"displayContent\"},{\"columnName\":\"moreUrl\",\"returnName\":\"moreUrl\"},{\"columnName\":\"id\",\"returnName\":\"id\"},{\"columnName\":\"height\",\"returnName\":\"custHeight\"},{\"columnName\":\"width\",\"returnName\":\"custWidth\"}]', '[]', 'interface');
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000005180002', 'scriptSelector', '常用脚本选择框', '选择常用脚本', 'list', 'dataSourceDefault', '本地数据源', 'table', 'sys_script', 1, 10, 800, 600, 1, 0, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name_\",\"showName\":\"名称\"},{\"columnName\":\"category_\",\"showName\":\"分类\"},{\"columnName\":\"memo_\",\"showName\":\"备注\"}]', '[{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"脚本名称\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"script_\",\"returnName\":\"script\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000008820001', 'IdentitySeletor', '流水号选择', '流水号选择', 'list', 'dataSourceDefault', '本地数据源', 'table', 'sys_serialno', 1, 10, 800, 600, 1, 0, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name_\",\"showName\":\"名称\"},{\"columnName\":\"alias_\",\"showName\":\"别名\"},{\"columnName\":\"regulation_\",\"showName\":\"规则\"},{\"columnName\":\"step_\",\"showName\":\"步长\"}]', '[{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名称\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"alias_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"别名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"alias_\",\"returnName\":\"alias\"},{\"columnName\":\"regulation_\",\"returnName\":\"regulation\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000013260001', 'bpmDefSelector', '流程定义选择', '流程定义主版本', 'list', 'dataSourceDefault', '本地数据源', 'table', 'bpm_definition', 1, 10, 800, 600, 1, 0, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name_\",\"showName\":\"流程名称\"},{\"columnName\":\"key_\",\"showName\":\"流程业务主键\"},{\"columnName\":\"desc_\",\"showName\":\"流程描述\"}]', '[{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"流程名称\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"is_main_\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"版本 - 是否主版本\",\"value\":{\"text\":\"Y\"},\"valueSource\":\"fixedValue\"}]', '[{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"desc_\",\"returnName\":\"desc\"},{\"columnName\":\"type_id_\",\"returnName\":\"typeId\"},{\"columnName\":\"is_main_\",\"returnName\":\"isMain\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('405338661247254529', 'postSelector', '岗位选择', '岗位', 'list', 'dataSourceDefault', '本地数据源', 'table', 'sys_position', 1, 10, 800, 600, 1, 0, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name\",\"showName\":\"名称\"},{\"columnName\":\"code\",\"showName\":\"编号\"}]', '[{\"columnName\":\"available\",\"condition\":\"EQ\",\"dbType\":\"number\",\"showName\":\"状态\",\"value\":{\"text\":\"1\"},\"valueSource\":\"fixedValue\"},{\"columnName\":\"code\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"编号\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名称\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"id\",\"returnName\":\"id\"},{\"columnName\":\"code\",\"returnName\":\"code\"},{\"columnName\":\"name\",\"returnName\":\"name\"}]', '[{\"columnName\":\"code\",\"sortType\":\"asc\"}]', NULL);
INSERT INTO `form_cust_dialog` (`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('405608679250853889', 'workbenchPanelTempl', '面板模板选择', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'sys_workbench_panel_templ', 1, 10, 800, 600, 1, 0, '{\"pidInitValScript\":false}', '[{\"columnName\":\"key_\",\"showName\":\"别名\"},{\"columnName\":\"name_\",\"showName\":\"名字\"},{\"columnName\":\"desc_\",\"showName\":\"描述\"}]', '[{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名字\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"desc_\",\"returnName\":\"desc\"},{\"columnName\":\"html_\",\"returnName\":\"html\"}]', '[]', NULL);

-- activiti 初始化数据
INSERT INTO `ACT_GE_PROPERTY` (`NAME_`, `VALUE_`, `REV_`) VALUES ('next.dbid', '0', '0');
INSERT INTO `ACT_GE_PROPERTY` (`NAME_`, `VALUE_`, `REV_`) VALUES ('schema.history', 'create(5.22.0.0) upgrade(5.21.0.0->5.22.0.0)', '2');
INSERT INTO `ACT_GE_PROPERTY` (`NAME_`, `VALUE_`, `REV_`) VALUES ('schema.version', '5.22.0.0', '2');


-- 流程管理
UPDATE `sys_resource` SET `ENABLE_`='1' WHERE (`ID_`='10000000710005');
-- 个人办公
UPDATE `sys_resource` SET `ENABLE_`='1' WHERE (`ID_`='1');


-- 使用 angular 表单
UPDATE `sys_resource` SET `ENABLE_`='1' WHERE (`ID_`='30');
UPDATE `sys_resource` SET `ENABLE_`='0' WHERE (`ID_`='33');

-- 新增菜单
INSERT INTO `sys_menu` (`id`, `code`, `name`, `title`, `component`, `parent_id`, `path`, `no_cache`, `display`, `hidden`, `permission`, `is_special`, `available`, `description`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES ('8900', '8900', 'Bpm', 'OA审批', '', '', '${bpm.jump-url}/index.html?tokenKey=#{_tokenKey}&token=#{_fullToken}', 0, 0, 0, '', 1, 1, '', '1', '2021-07-05 01:21:35', '1', '2021-07-05 01:21:39');