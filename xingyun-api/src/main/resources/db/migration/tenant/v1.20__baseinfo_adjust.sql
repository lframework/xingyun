ALTER TABLE `base_data_product`
    MODIFY COLUMN `sku_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'SKU' AFTER `short_name`,
    MODIFY COLUMN `brand_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '品牌ID' AFTER `category_id`;