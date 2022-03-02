package com.lframework.xingyun.api.bo.stock.take.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 盘点任务 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTakeStockPlanBo extends BaseBo<TakeStockPlanDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 业务单据号
     */
    private String code;

    /**
     * 仓库编号
     */
    private String scCode;

    /**
     * 仓库名称
     */
    private String scName;

    /**
     * 盘点类别
     */
    private Integer takeType;

    /**
     * 盘点状态
     */
    private Integer takeStatus;

    /**
     * 盘点内容
     */
    private String bizName;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    public QueryTakeStockPlanBo() {

    }

    public QueryTakeStockPlanBo(TakeStockPlanDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<TakeStockPlanDto> convert(TakeStockPlanDto dto) {

        return super.convert(dto, QueryTakeStockPlanBo::getTakeType, QueryTakeStockPlanBo::getTakeStatus);
    }

    @Override
    protected void afterInit(TakeStockPlanDto dto) {

        this.takeType = dto.getTakeType().getCode();
        this.takeStatus = dto.getTakeStatus().getCode();

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        StoreCenterDto sc = storeCenterService.getById(dto.getScId());

        this.scCode = sc.getCode();
        this.scName = sc.getName();

        String bizId = dto.getBizId();
        if (dto.getTakeType() == TakeStockPlanType.CATEGORY) {
            IProductCategoryService productCategoryService = ApplicationUtil.getBean(IProductCategoryService.class);
            String[] categoryIds = bizId.split(",");
            StringBuilder builder = new StringBuilder();
            for (String categoryId : categoryIds) {
                ProductCategoryDto productCategory = productCategoryService.getById(categoryId);
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
                ProductBrandDto productBrand = productBrandService.getById(brandId);
                builder.append(productBrand.getName()).append(StringPool.STR_SPLIT_CN);
            }

            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }

            this.bizName = builder.toString();
        }

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        this.createBy = userService.getById(dto.getCreateBy()).getName();
        this.updateBy = userService.getById(dto.getUpdateBy()).getName();
    }
}
