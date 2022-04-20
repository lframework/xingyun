package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 盘点单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface TakeStockSheetMapper extends BaseMapper<TakeStockSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<TakeStockSheet> query(@Param("vo") QueryTakeStockSheetVo vo);

  /**
   * 根据ID查询详情
   *
   * @param id
   * @return
   */
  TakeStockSheetFullDto getDetail(String id);

  /**
   * 根据预先盘点单ID判断是否有盘点单关联
   *
   * @param preSheetId
   * @return
   */
  Boolean hasRelatePreTakeStockSheet(String preSheetId);

  /**
   * 根据盘点任务ID查询是否有未审核通过的盘点单
   *
   * @param planId
   * @return
   */
  Boolean hasUnApprove(String planId);
}
