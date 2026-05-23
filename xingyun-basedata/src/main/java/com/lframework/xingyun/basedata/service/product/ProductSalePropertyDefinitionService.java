package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.UpdateProductSalePropertyDefinitionVo;
import java.util.List;

public interface ProductSalePropertyDefinitionService extends BaseMpService<ProductSalePropertyDefinition> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<ProductSalePropertyDefinition> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropertyDefinitionVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSalePropertyDefinition> query(QueryProductSalePropertyDefinitionVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ProductSalePropertyDefinition findById(String id);

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
  String create(CreateProductSalePropertyDefinitionVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateProductSalePropertyDefinitionVo vo);

  /**
   * 查询模型
   *
   * @return
   */
  List<ProductSalePropertyDefinitionModelorDto> getModelor();
}
