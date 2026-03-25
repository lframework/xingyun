package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.stockcell.StockCellDto;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.vo.stockcell.QueryStockCellSelectorVo;
import com.lframework.xingyun.basedata.vo.stockcell.QueryStockCellVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 仓位 Mapper 接口
 * </p>
 *
 * @author lframework
 * @since 2026-01-01
 */
public interface StockCellMapper extends BaseMapper<StockCell> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", autoParse = true),
      @Sort(value = "name", autoParse = true),
      @Sort(value = "scCode", alias = "sc.code"),
      @Sort(value = "scName", alias = "sc.name"),
      @Sort(value = "createTime", autoParse = true),
      @Sort(value = "updateTime", autoParse = true),
  })
  List<StockCellDto> query(@Param("vo") QueryStockCellVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<StockCellDto> selector(@Param("vo") QueryStockCellSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockCellDto findById(String id);
}
