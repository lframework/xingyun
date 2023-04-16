package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockProductDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockProductVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
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
  List<PreTakeStockSheet> query(@Param("vo") QueryPreTakeStockSheetVo vo,
      @Param("dataPermission") String dataPermission);

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
  List<PreTakeStockSheet> selector(@Param("vo") PreTakeStockSheetSelectorVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据预先盘点单、盘点任务查询商品信息
   *
   * @param id
   * @param planId
   * @return
   */
  List<QueryPreTakeStockSheetProductDto> getProducts(@Param("id") String id,
      @Param("planId") String planId);

  /**
   * 根据关键字查询预先盘点单商品信息
   *
   * @param condition
   * @return
   */
  List<PreTakeStockProductDto> queryPreTakeStockByCondition(@Param("condition") String condition,
      @Param("dataPermission") String dataPermission);

  /**
   * 查询预先盘点单商品信息
   *
   * @param vo
   * @return
   */
  List<PreTakeStockProductDto> queryPreTakeStockList(@Param("vo") QueryPreTakeStockProductVo vo,
      @Param("dataPermission") String dataPermission);
}
