DROP TABLE IF EXISTS `tbl_order_time_line`;
CREATE TABLE `tbl_order_time_line`
(
    `id`          varchar(32) NOT NULL COMMENT 'ID',
    `order_id`    varchar(32) NOT NULL COMMENT '单据ID',
    `content`     longtext COMMENT '描述内容',
    `create_by`   varchar(32) NOT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `biz_type`    tinyint(3) NOT NULL COMMENT '业务类型',
    PRIMARY KEY (`id`),
    KEY           `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单据时间轴';