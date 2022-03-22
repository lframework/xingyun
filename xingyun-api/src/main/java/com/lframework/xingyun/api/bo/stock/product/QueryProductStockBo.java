package com.lframework.xingyun.api.bo.stock.product;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.ProductStockDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductStockBo extends BaseBo<ProductStockDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 仓库编号
   */
  private String scCode;

  /**
   * 仓库名称
   */
  private String scName;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品类目
   */
  private String categoryName;

  /**
   * 商品品牌
   */
  private String brandName;

  /**
   * 销售属性1
   */
  private String salePropItem1;

  /**
   * 销售属性2
   */
  private String salePropItem2;

  /**
   * 库存数量
   */
  private Integer stockNum;

  /**
   * 含税价格
   */
  private BigDecimal taxPrice;

  /**
   * 含税金额
   */
  private BigDecimal taxAmount;

  /**
   * 无税价格
   */
  private BigDecimal unTaxPrice;

  /**
   * 无税金额
   */
  private BigDecimal unTaxAmount;

  public QueryProductStockBo() {

  }

  public QueryProductStockBo(ProductStockDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductStockDto dto) {

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    IProductService productService = ApplicationUtil.getBean(IProductService.class);
    ProductDto product = productService.getById(dto.getProductId());
    this.productCode = product.getCode();
    this.productName = product.getName();
    this.categoryName = product.getPoly().getCategoryName();
    this.brandName = product.getPoly().getBrandName();
    if (product.getPoly().getMultiSaleProp()) {
      IProductSalePropItemService productSalePropItemService = ApplicationUtil
          .getBean(IProductSalePropItemService.class);
      List<SalePropItemByProductDto> saleProps = productSalePropItemService
          .getByProductId(product.getId());
      if (!CollectionUtil.isEmpty(saleProps)) {
        this.salePropItem1 = saleProps.get(0).getName();
        if (saleProps.size() > 1) {
          this.salePropItem2 = saleProps.get(1).getName();
        }
      }
    }

    this.taxPrice = NumberUtil.getNumber(dto.getTaxPrice(), 2);
    this.taxAmount = NumberUtil.getNumber(dto.getTaxAmount(), 2);
    this.unTaxPrice = NumberUtil.getNumber(dto.getUnTaxPrice(), 2);
    this.unTaxAmount = NumberUtil.getNumber(dto.getUnTaxAmount(), 2);
  }
}
