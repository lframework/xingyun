package com.lframework.xingyun.sc.bo.stock.warning;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 库存预警 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetProductStockWarningBo extends BaseBo<ProductStockWarning> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

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
   * 商品ID
   */
  @ApiModelProperty("商品ID")
  private String productId;

  /**
   * 商品编号
   */
  @ApiModelProperty("商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @ApiModelProperty("商品名称")
  private String productName;

  /**
   * 预警上限
   */
  @ApiModelProperty("预警上限")
  private Integer maxLimit;

  /**
   * 预警下限
   */
  @ApiModelProperty("预警下限")
  private Integer minLimit;

  /**
   * 操作人
   */
  @ApiModelProperty("操作人")
  private String updateBy;

  /**
   * 操作时间
   */
  @ApiModelProperty("操作时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public GetProductStockWarningBo() {

  }

  public GetProductStockWarningBo(ProductStockWarning dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductStockWarning dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Product product = productService.findById(dto.getProductId());
    this.productCode = product.getCode();
    this.productName = product.getName();
  }
}
