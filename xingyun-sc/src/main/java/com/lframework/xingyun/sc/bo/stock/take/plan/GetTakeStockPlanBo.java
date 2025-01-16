package com.lframework.xingyun.sc.bo.stock.take.plan;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductBrandService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 盘点任务 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetTakeStockPlanBo extends BaseBo<TakeStockPlan> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 业务单据号
     */
    @ApiModelProperty("业务单据号")
    private String code;

    /**
     * 仓库ID
     */
    @ApiModelProperty("仓库ID")
    private String scId;

    /**
     * 仓库名称
     */
    @ApiModelProperty("仓库名称")
    private String scName;

    /**
     * 盘点类别
     */
    @ApiModelProperty("盘点类别")
    private Integer takeType;

    /**
     * 盘点状态
     */
    @ApiModelProperty("盘点状态")
    private Integer takeStatus;

    /**
     * 业务名称
     */
    @ApiModelProperty("业务名称")
    private String bizName;

    public GetTakeStockPlanBo() {

    }

    public GetTakeStockPlanBo(TakeStockPlan dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<TakeStockPlan> convert(TakeStockPlan dto) {

        return super.convert(dto, GetTakeStockPlanBo::getTakeType, GetTakeStockPlanBo::getTakeStatus);
    }

    @Override
    protected void afterInit(TakeStockPlan dto) {

        this.takeType = dto.getTakeType().getCode();
        this.takeStatus = dto.getTakeStatus().getCode();

        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());

        this.scName = sc.getName();

        String bizId = dto.getBizId();
        if (dto.getTakeType() == TakeStockPlanType.CATEGORY) {
            ProductCategoryService productCategoryService = ApplicationUtil.getBean(
                ProductCategoryService.class);
            String[] categoryIds = bizId.split(",");
            StringBuilder builder = new StringBuilder();
            for (String categoryId : categoryIds) {
                ProductCategory productCategory = productCategoryService.findById(categoryId);
                builder.append(productCategory.getName()).append(StringPool.STR_SPLIT_CN);
            }

            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }

            this.bizName = builder.toString();
        } else if (dto.getTakeType() == TakeStockPlanType.BRAND) {
            ProductBrandService productBrandService = ApplicationUtil.getBean(ProductBrandService.class);
            String[] brandIds = bizId.split(",");
            StringBuilder builder = new StringBuilder();
            for (String brandId : brandIds) {
                ProductBrand productBrand = productBrandService.findById(brandId);
                builder.append(productBrand.getName()).append(StringPool.STR_SPLIT_CN);
            }

            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }

            this.bizName = builder.toString();
        }
    }
}
