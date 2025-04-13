ALTER TABLE `sw_file_box`
    CHANGE COLUMN `url` `record_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '上传记录ID' AFTER `name`;
UPDATE `sys_parameter` SET `pm_key` = 'upload.oss.config', `pm_value` = '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"internalEndPoint\":\"\",\"accessKeyId\":\"yourAccessKeyId\",\"accessKeySecret\":\"yourAccessKeySecret\",\"bucketName\":\"yourBacketName\"}', `description` = '阿里云对象存储配置信息，upload.type=OSS时生效，注意：当服务器与OSS同一地域时，建议填写internalEndPoint，此值表示内网endpoint，在上传时会优先使用内网endpoint。customUrl为自定义域名（需带协议）为空代表不使用自定义域名，示例值：https://www.lframework.com。其他参数均在阿里云控台获取。', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2023-06-27 10:38:05', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2025-04-10 20:48:54' WHERE `id` = 10;
UPDATE `sys_parameter` SET `pm_key` = 'upload.obs.config', `pm_value` = '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"ak\":\"yourAk\",\"sk\":\"yourSk\",\"bucketName\":\"yourBucketName\"}', `description` = '华为云对象存储配置信息，upload.type=OBS时生效。customUrl为自定义域名（目前华为云OBS不支持https协议的自定义域名）为空代表不使用自定义域名，示例值：http://www.lframework.com。其他参数均在华为云控台获取。', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2023-06-27 10:38:05', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2025-04-10 20:50:10' WHERE `id` = 11;
UPDATE `sys_parameter` SET `pm_key` = 'upload.cos.config', `pm_value` = '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"region\":\"yourRegion\",\"secretId\":\"yourSecretId\",\"secretKey\":\"yourSecretKey\",\"bucketName\":\"yourBucketName\"}', `description` = '腾讯云对象存储配置信息，upload.type=COS时生效。enpoint为下载文件时的域名，customUrl为自定义域名（需带协议）为空代表不使用自定义域名，示例值：https://www.lframework.com。其他参数均在腾讯云控台获取。', `create_by` = '系统管理员', `create_by_id` = '1', `create_time` = '2023-06-27 10:38:05', `update_by` = '系统管理员', `update_by_id` = '1', `update_time` = '2025-04-10 20:51:53' WHERE `id` = 12;
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (14, 'security-upload.type', 'LOCAL', '上传类型，分为LOCAL、OSS、COS、OBS。LOCAL：服务器本地存储。OSS：阿里云对象存储。COS：腾讯云对象存储。OBS：华为云对象存储', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2025-04-10 20:50:49');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (15, 'security-upload.oss.config', '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"internalEndPoint\":\"\",\"accessKeyId\":\"yourAccessKeyId\",\"accessKeySecret\":\"yourAccessKeySecret\",\"bucketName\":\"yourBacketName\"}', '阿里云对象存储配置信息，security-upload.type=OSS时生效，注意：当服务器与OSS同一地域时，建议填写internalEndPoint，此值表示内网endpoint，在上传时会优先使用内网endpoint。customUrl为自定义域名（需带协议）为空代表不使用自定义域名，示例值：https://www.lframework.com。其他参数均在阿里云控台获取。', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2025-04-10 20:48:59');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (16, 'security-upload.obs.config', '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"ak\":\"yourAk\",\"sk\":\"yourSk\",\"bucketName\":\"yourBucketName\"}', '华为云对象存储配置信息，security-upload.type=OBS时生效。customUrl为自定义域名（目前华为云OBS不支持https协议的自定义域名）为空代表不使用自定义域名，示例值：http://www.lframework.com。其他参数均在华为云控台获取。', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2025-04-10 20:50:40');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (17, 'security-upload.cos.config', '{\"customUrl\":\"\",\"endpoint\":\"yourEndpoint\",\"region\":\"yourRegion\",\"secretId\":\"yourSecretId\",\"secretKey\":\"yourSecretKey\",\"bucketName\":\"yourBucketName\"}', '腾讯云对象存储配置信息，security-upload.type=COS时生效。enpoint为下载文件时的域名，customUrl为自定义域名（需带协议）为空代表不使用自定义域名，示例值：https://www.lframework.com。其他参数均在腾讯云控台获取。', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2025-04-10 20:52:09');
INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (18, 'security-upload.sign-url-expired', '600', '预签名URL过期时间，单位：秒。', '系统管理员', '1', '2023-06-27 10:38:05', '系统管理员', '1', '2023-06-27 14:48:02');

CREATE TABLE `tbl_security_upload_record` (
    `id` varchar(20) NOT NULL COMMENT 'ID',
    `upload_type` varchar(20) NOT NULL COMMENT '上传方式',
    `file_path` longtext NOT NULL COMMENT '文件路径',
    `create_by` varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='安全上传记录';

INSERT INTO `sys_parameter` (`id`, `pm_key`, `pm_value`, `description`, `create_by`, `create_by_id`, `create_time`, `update_by`, `update_by_id`, `update_time`) VALUES (19, 'export.timeout', '600', '单个导出任务超时时间，单位：秒。', '系统管理员', '1', '2025-04-10 10:38:05', '系统管理员', '1', '2025-04-10 10:38:05');

CREATE TABLE `tbl_export_task` (
    `id` varchar(32) NOT NULL COMMENT 'ID',
    `name` varchar(200) NOT NULL COMMENT '名称',
    `record_id` varchar(32) DEFAULT NULL COMMENT '上传记录ID',
    `status` tinyint(3) NOT NULL COMMENT '状态',
    `error_msg` longtext COMMENT '错误信息',
    `req_class_name` longtext NOT NULL COMMENT '请求类名',
    `req_params` longtext COMMENT '请求类方法参数',
    `req_params_sign` varchar(40) DEFAULT NULL COMMENT '请求类方法参数签名',
    `total_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '总数据条数',
    `cur_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前完成数据条数',
    `file_size` varchar(200) DEFAULT NULL COMMENT '文件大小',
    `create_by` varchar(32) NOT NULL COMMENT '创建人',
    `create_by_id` varchar(32) NOT NULL COMMENT '创建人ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `finish_time` datetime DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`id`),
    KEY `status` (`status`),
    KEY `create_time` (`create_time`),
    KEY `finish_time` (`finish_time`),
    KEY `req_params_sign` (`req_params_sign`),
    KEY `create_by_id` (`create_by_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导出任务';

