package com.lframework.xingyun.basedata.biz.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.ProductPolySalePropGroup;
import com.lframework.xingyun.basedata.facade.vo.product.poly.saleprop.CreateProductPolySalePropGroupVo;
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
