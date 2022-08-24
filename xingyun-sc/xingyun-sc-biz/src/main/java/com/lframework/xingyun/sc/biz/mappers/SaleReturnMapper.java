package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.SaleReturn;
import com.lframework.xingyun.sc.facade.vo.sale.returned.QuerySaleReturnVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-04
 */
public interface SaleReturnMapper extends BaseMapper<SaleReturn> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleReturn> query(@Param("vo") QuerySaleReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleReturnFullDto getDetail(String id);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleReturnFullDto> queryFulls(@Param("vo") QuerySaleReturnVo vo);

  /**
   * 查询已审核列表
   *
   * @param customerId
   * @param startTime
   * @param endTime
   * @return
   */
  List<SaleReturn> getApprovedList(@Param("customerId") String customerId,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
      @Param("settleStatus") SettleStatus settleStatus);


  /**
   * 设置成结算中
   *
   * @param id
   * @return
   */
  int setPartSettle(@Param("id") String id);

  /**
   * 回滚设置成结算中
   *
   * @param id
   * @return
   */
  int rollbackSetPartSettle(@Param("id") String id, @Param("txId") String txId);

  /**
   * 设置成已结算
   *
   * @param id
   * @return
   */
  int setSettled(@Param("id") String id);

  /**
   * 回滚设置成已结算
   *
   * @param id
   * @return
   */
  int rollbackSetSettled(@Param("id") String id, @Param("txId") String txId);
}
