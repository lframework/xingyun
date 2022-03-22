package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetDto;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockCostAdjustSheet;
import com.lframework.xingyun.sc.vo.stock.adjust.QueryStockCostAdjustSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 库存成本调整单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface StockCostAdjustSheetMapper extends BaseMapper<StockCostAdjustSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<StockCostAdjustSheetDto> query(@Param("vo") QueryStockCostAdjustSheetVo vo);

  /**
   * 根据ID查询
   */
  StockCostAdjustSheetDto getById(@Param("id") String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockCostAdjustSheetFullDto getDetail(@Param("id") String id);
}
