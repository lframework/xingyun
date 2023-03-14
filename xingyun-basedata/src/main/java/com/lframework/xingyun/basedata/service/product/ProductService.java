package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import java.util.Collection;
import java.util.List;

public interface ProductService extends BaseMpService<Product> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<Product> query(Integer pageIndex, Integer pageSize, QueryProductVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<Product> query(QueryProductVo vo);

  /**
   * 查询商品品种数
   *
   * @param vo
   * @return
   */
  Integer queryCount(QueryProductVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  Product findById(String id);

  /**
   * 查询没有属性的ID
   *
   * @param propertyId
   * @return
   */
  List<String> getIdNotInProductProperty(String propertyId);

  /**
   * 根据类目ID查询
   *
   * @param categoryId
   * @return
   */
  List<String> getIdByCategoryId(String categoryId);

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
  String create(CreateProductVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductVo vo);

  /**
   * 根据类目ID查询
   *
   * @param categoryIds
   * @return
   */
  List<Product> getByCategoryIds(List<String> categoryIds);

  /**
   * 根据品牌ID查询
   *
   * @param brandIds
   * @return
   */
  List<Product> getByBrandIds(List<String> brandIds);
}
