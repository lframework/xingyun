package com.lframework.xingyun.basedata.dto.product.saleprop.item;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class SalePropItemByProductDto implements BaseDto, Serializable {

  /**
   * 销售属性ID1
   */
  private String itemId1;

  /**
   * 销售属性名称1
   */
  private String itemName1;

  /**
   * 销售属性组ID1
   */
  private String groupId1;

  /**
   * 销售属性组名称1
   */
  private String groupName1;

  /**
   * 销售属性ID2
   */
  private String itemId2;

  /**
   * 销售属性名称2
   */
  private String itemName2;

  /**
   * 销售属性组ID2
   */
  private String groupId2;

  /**
   * 销售属性组名称2
   */
  private String groupName2;

  /**
   * 商品ID
   */
  private String productId;
}
