package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.CreateProductSalePropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.QueryProductSalePropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.UpdateProductSalePropertyItemVo;
import java.util.List;

public interface ProductSalePropertyItemService extends BaseMpService<ProductSalePropertyItem> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<ProductSalePropertyItem> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropertyItemVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSalePropertyItem> query(QueryProductSalePropertyItemVo vo);

  /**
   * 根据销售属性ID查询
   *
   * @param propertyId
   * @return
   */
  List<ProductSalePropertyItem> getByPropertyId(String propertyId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductSalePropertyItem findById(String id);

  /**
   * 新增
   *
   * @param vo
   * @return
   */
  String create(CreateProductSalePropertyItemVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductSalePropertyItemVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
