ALTER TABLE `base_data_product_saleprop_item_relation`
DROP INDEX `product_id`,
ADD UNIQUE INDEX `product_id`(`product_id`) USING BTREE;