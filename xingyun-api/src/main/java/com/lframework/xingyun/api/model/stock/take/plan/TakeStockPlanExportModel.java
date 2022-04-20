package com.lframework.xingyun.api.model.stock.take.plan;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeStockPlanExportModel extends BaseBo<TakeStockPlan> implements ExcelModel {

    /**
     * 业务单据号
     */
    @ExcelProperty("业务单据号")
    private String code;

    /**
     * 仓库编号
     */
    @ExcelProperty("仓库编号")
    private String scCode;

    /**
     * 仓库名称
     */
    @ExcelProperty("仓库名称")
    private String scName;

    /**
     * 盘点类别
     */
    @ExcelProperty("盘点类别")
    private String takeType;

    /**
     * 业务名称
     */
    @ExcelProperty("业务名称")
    private String bizName;

    /**
     * 盘点状态
     */
    @ExcelProperty("盘点状态")
    private String takeStatus;

    /**
     * 创建时间
     */
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    @ExcelProperty("创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ExcelProperty("创建人")
    private String createBy;

    /**
     * 操作时间
     */
    @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
    @ExcelProperty("操作时间")
    private Date updateTime;

    /**
     * 操作人
     */
    @ExcelProperty("操作人")
    private String updateBy;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String description;

    public TakeStockPlanExportModel(TakeStockPlan dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<TakeStockPlan> convert(TakeStockPlan dto) {

        return super.convert(dto, TakeStockPlanExportModel::getTakeStatus, TakeStockPlanExportModel::getTakeType);
    }

    @Override
    protected void afterInit(TakeStockPlan dto) {

        this.takeType = dto.getTakeType().getDesc();
        this.takeStatus = dto.getTakeStatus().getDesc();

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

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        this.createBy = userService.findById(dto.getCreateBy()).getName();
        this.updateBy = userService.findById(dto.getUpdateBy()).getName();
    }
}
