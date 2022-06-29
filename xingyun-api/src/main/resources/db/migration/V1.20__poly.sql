ALTER TABLE `base_data_product_saleprop_item_relation`
    ADD COLUMN `poly_id` varchar(32) NOT NULL COMMENT '聚合ID' AFTER `id`,
ADD INDEX `poly_id`(`poly_id`, `sale_prop_item_id`, `order_no`) USING BTREE;
update base_data_product_saleprop_item_relation as r INNER JOIN base_data_product as p
on p.id = r.product_id set r.poly_id = p.poly_id;
ALTER TABLE `base_data_product_saleprop_item_relation`
DROP INDEX `poly_id`,
ADD INDEX `poly_id`(`poly_id`, `sale_prop_item_id`) USING BTREE;

ALTER TABLE `base_data_product_saleprop_item_relation`
    CHANGE COLUMN `sale_prop_item_id` `sale_prop_item_id1` varchar (32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '销售属性ID1' AFTER `product_id`,
    ADD COLUMN `sale_prop_group_id1` varchar (32) NOT NULL COMMENT '销售属性组ID1' AFTER `product_id`,
    ADD COLUMN `sale_prop_group_id2` varchar (32) NOT NULL COMMENT '销售属性组ID2' AFTER `sale_prop_item_id1`,
    ADD COLUMN `sale_prop_item_id2` varchar (32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '销售属性ID2' AFTER `sale_prop_group_id2`,
DROP
PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;
update base_data_product_saleprop_item_relation as r1 left join base_data_product_saleprop_item_relation as r2
on r2.poly_id = r1.poly_id and r2.product_id = r1.product_id and r2.order_no = 2
    left join base_data_product_saleprop_item as i on i.id = r2.sale_prop_item_id1
    left join base_data_product_saleprop_group as g on g.id = i.group_id
    set r1.sale_prop_item_id2 = i.id, r1.sale_prop_group_id2 = g.id
where r1.order_no = 1;
delete
from base_data_product_saleprop_item_relation
where order_no = 2;
update base_data_product_saleprop_item_relation as r left join base_data_product_saleprop_item as i
on i.id = r.sale_prop_item_id1
    set r.sale_prop_group_id1 = i.group_id;
ALTER TABLE `base_data_product_saleprop_item_relation`
DROP
COLUMN `order_no`,
DROP INDEX `product_id`,
DROP INDEX `poly_id`,
ADD UNIQUE INDEX `product_id`(`product_id`, `sale_prop_item_id1`, `sale_prop_item_id2`) USING BTREE,
ADD INDEX `poly_id`(`poly_id`, `sale_prop_group_id1`, `sale_prop_group_id2`) USING BTREE;
ALTER TABLE `base_data_product_saleprop_item_relation`
    ADD UNIQUE INDEX `poly_id_group`(`poly_id`, `sale_prop_item_id1`, `sale_prop_item_id2`) USING BTREE;