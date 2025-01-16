package com.lframework.xingyun.basedata.enums;

import com.lframework.xingyun.core.enums.NodeType;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public final class ProductCategoryNodeType implements NodeType, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public Integer getCode() {

    return 2;
  }

  @Override
  public String getDesc() {

    return "商品分类";
  }
}
