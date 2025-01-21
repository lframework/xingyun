package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.UpdateProductBrandVo;
import java.util.Collection;
import java.util.List;

public interface ProductBrandService extends BaseMpService<ProductBrand> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<ProductBrand> query(Integer pageIndex, Integer pageSize, QueryProductBrandVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductBrand> query(QueryProductBrandVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<ProductBrand> selector(Integer pageIndex, Integer pageSize,
      QueryProductBrandSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductBrand findById(String id);

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
  String create(CreateProductBrandVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductBrandVo vo);
}
