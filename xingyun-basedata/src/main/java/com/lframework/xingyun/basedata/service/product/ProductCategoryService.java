package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
import java.util.Collection;
import java.util.List;

public interface ProductCategoryService extends BaseMpService<ProductCategory> {

  /**
   * 查询全部类目信息
   *
   * @return
   */
  List<ProductCategory> getAllProductCategories();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductCategory findById(String id);

  /**
   * 选择器
   *
   * @return
   */
  List<ProductCategory> selector(QueryProductCategorySelectorVo vo);

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
  String create(CreateProductCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductCategoryVo vo);

  /**
   * 保存关系
   *
   * @param categoryId
   * @param parentId
   */
  void saveRecursion(String categoryId, String parentId);
}
