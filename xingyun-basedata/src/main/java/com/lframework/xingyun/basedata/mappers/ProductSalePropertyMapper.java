package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductSaleProperty;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSalePropertyMapper extends BaseMapper<ProductSaleProperty> {

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
  List<ProductSaleProperty> query(@Param("vo") QueryProductSalePropertyVo vo);

  /**
   * 查询模型
   *
   * @return
   */
  List<ProductSalePropertyModelorDto> getModelor();
}
