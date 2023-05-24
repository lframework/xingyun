package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferOrderFullDto;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferProductDto;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferProductVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 仓库调拨单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface ScTransferOrderMapper extends BaseMapper<ScTransferOrder> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ScTransferOrder> query(@Param("vo") QueryScTransferOrderVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ScTransferOrderFullDto getDetail(@Param("id") String id);

  /**
   * 根据关键字查询库存成本调整单商品信息
   *
   * @param scId
   * @param condition
   * @return
   */
  List<ScTransferProductDto> queryScTransferByCondition(
      @Param("scId") String scId, @Param("condition") String condition,
      @Param("dataPermission") String dataPermission);

  /**
   * 查询库存成本调整单商品信息
   *
   * @param vo
   * @return
   */
  List<ScTransferProductDto> queryScTransferList(
      @Param("vo") QueryScTransferProductVo vo,
      @Param("dataPermission") String dataPermission);
}
