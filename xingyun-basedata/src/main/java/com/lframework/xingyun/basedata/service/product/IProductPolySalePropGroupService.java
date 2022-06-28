package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductPolySalePropGroup;
import com.lframework.xingyun.basedata.vo.product.poly.saleprop.CreateProductPolySalePropGroupVo;
import java.util.List;

public interface IProductPolySalePropGroupService extends BaseMpService<ProductPolySalePropGroup> {

  /**
   * 新增
   *
   * @param data
   */
  void create(CreateProductPolySalePropGroupVo data);

  /**
   * 根据PolyId查询
   *
   * @param polyId
   * @return
   */
  List<ProductPolySalePropGroup> getByPolyId(String polyId);
}
