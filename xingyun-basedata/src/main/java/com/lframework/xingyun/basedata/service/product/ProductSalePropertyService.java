package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductSaleProperty;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.UpdateProductSalePropertyVo;
import java.util.List;

public interface ProductSalePropertyService extends BaseMpService<ProductSaleProperty> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<ProductSaleProperty> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropertyVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSaleProperty> query(QueryProductSalePropertyVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductSaleProperty findById(String id);

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
  String create(CreateProductSalePropertyVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductSalePropertyVo vo);

  /**
   * 查询模型
   *
   * @return
   */
  List<ProductSalePropertyModelorDto> getModelor();
}
