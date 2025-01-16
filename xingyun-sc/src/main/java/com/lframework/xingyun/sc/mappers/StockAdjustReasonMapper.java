package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.QueryStockAdjustReasonVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.StockAdjustReasonSelectorVo;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2023-04-18
 */
public interface StockAdjustReasonMapper extends BaseMapper<StockAdjustReason> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "name", alias = "tb", autoParse = true),
      @Sort(value = "createTime", alias = "tb", autoParse = true),
      @Sort(value = "updateTime", alias = "tb", autoParse = true),
  })
  List<StockAdjustReason> query(@Param("vo") QueryStockAdjustReasonVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<StockAdjustReason> selector(@Param("vo") StockAdjustReasonSelectorVo vo);
}
