package com.lframework.xingyun.basedata.bo.product.category;

import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetProductCategoryBo extends BaseBo<ProductCategory> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 父级ID
     */
    @Schema(description = "父级ID")
    private String parentId;

    /**
     * 父级名称
     */
    @Schema(description = "父级名称")
    private String parentName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    public GetProductCategoryBo() {

    }

    public GetProductCategoryBo(ProductCategory dto) {

        super(dto);
    }

    @Override
    protected void afterInit(ProductCategory dto) {

        if (!StringUtil.isBlank(this.getParentId())) {
            ProductCategoryService productCategoryService = ApplicationUtil.getBean(
                ProductCategoryService.class);
            ProductCategory parentCategory = productCategoryService.findById(this.getParentId());
            if (!ObjectUtil.isNull(parentCategory)) {
                this.setParentName(parentCategory.getName());
            }
        }
    }
}
