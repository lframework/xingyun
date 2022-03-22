package com.lframework.xingyun.api.model.stock;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductLotExportModel extends BaseBo<ProductLotWithStockDto> implements ExcelModel {

  /**
   * 批次号
   */
  @ExcelProperty("批次号")
  private String lotCode;

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
   * 商品编号
   */
  @ExcelProperty("商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @ExcelProperty("商品名称")
  private String productName;

  /**
   * 商品类目
   */
  @ExcelProperty("商品类目")
  private String categoryName;

  /**
   * 商品品牌
   */
  @ExcelProperty("商品品牌")
  private String brandName;

  /**
   * 销售属性1
   */
  @ExcelProperty("销售属性1")
  private String salePropItem1;

  /**
   * 销售属性2
   */
  @ExcelProperty("销售属性2")
  private String salePropItem2;

  /**
   * 供应商编号
   */
  @ExcelProperty("供应商编号")
  private String supplierCode;

  /**
   * 供应商名称
   */
  @ExcelProperty("供应商名称")
  private String supplierName;

  /**
   * 库存数量
   */
  @ExcelProperty("库存数量")
  private Integer stockNum;

  /**
   * 税率（%）
   */
  @ExcelProperty("税率（%）")
  private BigDecimal taxRate;

  /**
   * 生成时间
   */
  @ExcelProperty("生成时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date createTime;

  public ProductLotExportModel() {

  }

  public ProductLotExportModel(ProductLotWithStockDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<ProductLotWithStockDto> convert(ProductLotWithStockDto dto) {

    return this;
  }

  @Override
  protected void afterInit(ProductLotWithStockDto dto) {

    this.setLotCode(dto.getLotCode());
    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.setScCode(sc.getCode());
    this.setScName(sc.getName());

    ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
    SupplierDto supplier = supplierService.getById(dto.getSupplierId());
    this.setSupplierCode(supplier.getCode());
    this.setSupplierName(supplier.getName());

    IProductService productService = ApplicationUtil.getBean(IProductService.class);
    IProductSalePropItemService productSalePropItemService = ApplicationUtil
        .getBean(IProductSalePropItemService.class);

    ProductDto product = productService.getById(dto.getProductId());
    this.setProductCode(product.getCode());
    this.setProductName(product.getName());
    this.setCategoryName(product.getPoly().getCategoryName());
    this.setBrandName(product.getPoly().getBrandName());
    if (product.getPoly().getMultiSaleProp()) {
      List<SalePropItemByProductDto> saleProps = productSalePropItemService
          .getByProductId(product.getId());
      if (!CollectionUtil.isEmpty(saleProps)) {
        this.setSalePropItem1(saleProps.get(0).getName());
      }
      if (saleProps.size() > 1) {
        this.setSalePropItem2(saleProps.get(1).getName());
      }
    }

    this.setStockNum(dto.getStockNum());
    this.setTaxRate(dto.getTaxRate());
    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
  }
}
