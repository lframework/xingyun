package com.lframework.xingyun.api.model.stock.take.plan;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeStockPlanExportModel extends BaseBo<TakeStockPlanDto> implements ExcelModel {

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

  public TakeStockPlanExportModel(TakeStockPlanDto dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockPlanDto> convert(TakeStockPlanDto dto) {
    return super.convert(dto, TakeStockPlanExportModel::getTakeStatus,
        TakeStockPlanExportModel::getTakeType);
  }

  @Override
  protected void afterInit(TakeStockPlanDto dto) {
    this.takeType = dto.getTakeType().getDesc();
    this.takeStatus = dto.getTakeStatus().getDesc();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());

    this.scCode = sc.getCode();
    this.scName = sc.getName();

    String bizId = dto.getBizId();
    if (dto.getTakeType() == TakeStockPlanType.CATEGORY) {
      IProductCategoryService productCategoryService = ApplicationUtil
          .getBean(IProductCategoryService.class);
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
      IProductBrandService productBrandService = ApplicationUtil
          .getBean(IProductBrandService.class);
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
