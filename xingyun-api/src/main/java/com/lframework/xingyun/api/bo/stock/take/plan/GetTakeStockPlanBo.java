package com.lframework.xingyun.api.bo.stock.take.plan;

import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 盘点任务 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetTakeStockPlanBo extends BaseBo<TakeStockPlanDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 业务单据号
   */
  private String code;

  /**
   * 仓库ID
   */
  private String scId;

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
   * 业务名称
   */
  private String bizName;

  public GetTakeStockPlanBo() {

  }

  public GetTakeStockPlanBo(TakeStockPlanDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockPlanDto> convert(TakeStockPlanDto dto) {
    return super.convert(dto, GetTakeStockPlanBo::getTakeType, GetTakeStockPlanBo::getTakeStatus);
  }

  @Override
  protected void afterInit(TakeStockPlanDto dto) {
    this.takeType = dto.getTakeType().getCode();
    this.takeStatus = dto.getTakeStatus().getCode();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());

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
  }
}
