package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyDefinitionVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSalePropertyDefinitionMapper extends BaseMapper<ProductSalePropertyDefinition> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "p", autoParse = true),
      @Sort(value = "name", alias = "p", autoParse = true),
  })
  List<ProductSalePropertyDefinition> query(@Param("vo") QueryProductSalePropertyDefinitionVo vo);

  /**
   * 查询模型
   *
   * @return
   */
  List<ProductSalePropertyDefinitionModelorDto> getModelor();
}
