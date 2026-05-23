package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductCategoryPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductCategoryPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductCategoryPropertyItemVo;
import java.util.List;

public interface ProductCategoryPropertyItemService extends BaseMpService<ProductCategoryPropertyItem> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ProductCategoryPropertyItem> query(Integer pageIndex, Integer pageSize,
      QueryProductCategoryPropertyItemVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductCategoryPropertyItem> query(QueryProductCategoryPropertyItemVo vo);

  /**
   * 根据属性ID查询
   *
   * @param propertyId
   * @return
   */
  List<ProductCategoryPropertyItem> getByPropertyId(String propertyId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductCategoryPropertyItem findById(String id);

  /**
   * 新增
   *
   * @param vo
   * @return
   */
  String create(CreateProductCategoryPropertyItemVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductCategoryPropertyItemVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
