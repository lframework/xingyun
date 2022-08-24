package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.facade.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.facade.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 预先盘点单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface PreTakeStockSheetMapper extends BaseMapper<PreTakeStockSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PreTakeStockSheet> query(@Param("vo") QueryPreTakeStockSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PreTakeStockSheetFullDto getDetail(@Param("id") String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<PreTakeStockSheetSelectorDto> selector(@Param("vo") PreTakeStockSheetSelectorVo vo);

  /**
   * 根据预先盘点单、盘点任务查询商品信息
   *
   * @param id
   * @param planId
   * @return
   */
  List<QueryPreTakeStockSheetProductDto> getProducts(@Param("id") String id,
      @Param("planId") String planId);
}
