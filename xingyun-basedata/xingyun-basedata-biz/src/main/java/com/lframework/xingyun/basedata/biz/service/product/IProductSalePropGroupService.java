package com.lframework.xingyun.basedata.biz.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.CreateProductSalePropGroupVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.QueryProductSalePropGroupVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.UpdateProductSalePropGroupVo;
import java.util.Collection;
import java.util.List;

public interface IProductSalePropGroupService extends BaseMpService<ProductSalePropGroup> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<ProductSalePropGroup> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropGroupVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSalePropGroup> query(QueryProductSalePropGroupVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<ProductSalePropGroup> selector(Integer pageIndex, Integer pageSize,
      QueryProductSalePropGroupSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductSalePropGroup findById(String id);

  /**
   * 根据ID停用
   *
   * @param ids
   */
  void batchUnable(Collection<String> ids);

  /**
   * 根据ID启用
   *
   * @param ids
   */
  void batchEnable(Collection<String> ids);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateProductSalePropGroupVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductSalePropGroupVo vo);
}
