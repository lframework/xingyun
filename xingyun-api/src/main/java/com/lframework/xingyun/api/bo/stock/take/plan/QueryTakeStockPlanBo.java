package com.lframework.xingyun.api.bo.stock.take.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 盘点任务 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTakeStockPlanBo extends BaseBo<TakeStockPlan> {

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
     * 仓库编号
     */
    @ApiModelProperty("仓库编号")
    private String scCode;

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
     * 盘点内容
     */
    @ApiModelProperty("盘点内容")
    private String bizName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    public QueryTakeStockPlanBo() {

    }

    public QueryTakeStockPlanBo(TakeStockPlan dto) {

        super(dto);
    }

    @Override
    public BaseBo<TakeStockPlan> convert(TakeStockPlan dto) {

        return super.convert(dto, QueryTakeStockPlanBo::getTakeType, QueryTakeStockPlanBo::getTakeStatus);
    }

    @Override
    protected void afterInit(TakeStockPlan dto) {

        this.takeType = dto.getTakeType().getCode();
        this.takeStatus = dto.getTakeStatus().getCode();

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(dto.getScId());

        this.scCode = sc.getCode();
        this.scName = sc.getName();

        String bizId = dto.getBizId();
        if (dto.getTakeType() == TakeStockPlanType.CATEGORY) {
            IProductCategoryService productCategoryService = ApplicationUtil.getBean(IProductCategoryService.class);
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
            IProductBrandService productBrandService = ApplicationUtil.getBean(IProductBrandService.class);
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
