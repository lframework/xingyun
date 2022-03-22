package com.lframework.xingyun.api.bo.basedata.product.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductBo extends BaseBo<ProductDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * SKU
   */
  private String skuCode;

  /**
   * 类目名称
   */
  private String categoryName;

  /**
   * 品牌名称
   */
  private String brandName;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QueryProductBo() {

  }

  public QueryProductBo(ProductDto dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductDto dto) {

    this.categoryName = dto.getPoly().getCategoryName();
    this.brandName = dto.getPoly().getBrandName();
  }
}
