package com.lframework.xingyun.basedata.vo.product.poly.saleprop;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CreateProductPolySalePropGroupVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品聚合ID
   */
  private String polyId;

  /**
   * 销售属性组ID
   */
  private List<String> salePropGroupIds;
}
