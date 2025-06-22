package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.QueryProductCategorySelectorVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
import java.util.List;

public interface ProductCategoryService extends BaseMpService<ProductCategory> {

  /**
   * 查询全部分类信息
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
   * @param id
   */
  void unable(String id);

  /**
   * 根据ID启用
   *
   * @param id
   */
  void enable(String id);

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
