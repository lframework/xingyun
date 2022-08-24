package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturn;
import com.lframework.xingyun.sc.facade.vo.purchase.returned.QueryPurchaseReturnVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-16
 */
public interface PurchaseReturnMapper extends BaseMapper<PurchaseReturn> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PurchaseReturn> query(@Param("vo") QueryPurchaseReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseReturnFullDto getDetail(String id);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PurchaseReturnFullDto> queryFulls(@Param("vo") QueryPurchaseReturnVo vo);

  /**
   * 查询已审核列表
   *
   * @param supplierId
   * @param startTime
   * @param endTime
   * @return
   */
  List<PurchaseReturn> getApprovedList(@Param("supplierId") String supplierId,
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
