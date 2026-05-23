package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductSelectorVo;
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
   * 选择器
   *
   * @return
   */
  PageResult<Product> selector(Integer pageIndex, Integer pageSize, QueryProductSelectorVo vo);

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
  List<String> getIdNotInProductCategoryPropertyDefinition(String propertyId);

  /**
   * 根据分类ID查询
   *
   * @param categoryId
   * @return
   */
  List<String> getIdByCategoryId(String categoryId);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);


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
   * 根据分类ID查询
   *
   * @param categoryIds
   * @return
   */
  List<Product> getByCategoryIds(List<String> categoryIds, Integer productType);

  /**
   * 根据品牌ID查询
   *
   * @param brandIds
   * @return
   */
  List<Product> getByBrandIds(List<String> brandIds, Integer productType);

  /**
   * 根据商品编号查询商品
   *
   * @param code
   * @return
   */
  Product findByCode(String code);

  /**
   * 记录扩展编号
   *
   * @param productId
   * @param multiCodes
   */
  void recordMultiCodes(String productId, List<String> multiCodes);
}
