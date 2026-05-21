INSERT INTO base_data_product_category_property (id, property_id, category_id)
SELECT REPLACE(UUID(), '-', ''), t.property_id, t.category_id
FROM (
SELECT DISTINCT cp.property_id, leaf.id AS category_id
FROM base_data_product_category_property cp
INNER JOIN base_data_product_category bound_category ON bound_category.id = cp.category_id
INNER JOIN recursion_mapping rm ON FIND_IN_SET(cp.category_id, rm.path) AND rm.node_type = 2
INNER JOIN base_data_product_category leaf ON leaf.id = rm.node_id AND leaf.available = TRUE
LEFT JOIN base_data_product_category child ON child.parent_id = leaf.id AND child.available = TRUE
LEFT JOIN base_data_product_category_property exists_cp ON exists_cp.property_id = cp.property_id
  AND exists_cp.category_id = leaf.id
WHERE bound_category.available = TRUE
  AND child.id IS NULL
  AND exists_cp.id IS NULL
) t;

INSERT INTO base_data_product_category_property (id, property_id, category_id)
SELECT REPLACE(UUID(), '-', ''), p.id, c.id
FROM base_data_product_property p
INNER JOIN base_data_product_category c ON c.available = TRUE
LEFT JOIN base_data_product_category child ON child.parent_id = c.id AND child.available = TRUE
LEFT JOIN base_data_product_category_property cp ON cp.property_id = p.id
  AND cp.category_id = c.id
WHERE p.available = TRUE
  AND p.property_type = 1
  AND child.id IS NULL
  AND cp.id IS NULL;

DELETE cp
FROM base_data_product_category_property cp
INNER JOIN base_data_product_category c ON c.id = cp.category_id
INNER JOIN base_data_product_category child ON child.parent_id = c.id AND child.available = TRUE;

UPDATE base_data_product_property
SET property_type = 2,
    column_data_type = CASE WHEN column_type = 3 THEN 3 ELSE NULL END
WHERE available = TRUE;
