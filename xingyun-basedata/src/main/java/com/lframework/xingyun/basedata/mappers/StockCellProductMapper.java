package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.stockcell.product.StockCellProductDto;
import com.lframework.xingyun.basedata.entity.StockCellProduct;
import com.lframework.xingyun.basedata.vo.stockcell.product.QueryStockCellProductVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 仓位商品 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2026-01-02
 */
public interface StockCellProductMapper extends BaseMapper<StockCellProduct> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "scCode", alias = "sc.code"),
      @Sort(value = "scName", alias = "sc.name"),
      @Sort(value = "stockCellCode", alias = "c.code"),
      @Sort(value = "stockCellName", alias = "c.name"),
      @Sort(value = "skuCode", alias = "s.code"),
      @Sort(value = "productCode", alias = "p.code"),
      @Sort(value = "productName", alias = "p.name"),
  })
  List<StockCellProductDto> query(@Param("vo") QueryStockCellProductVo vo);
}
