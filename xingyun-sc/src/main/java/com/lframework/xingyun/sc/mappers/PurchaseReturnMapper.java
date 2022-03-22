package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnDto;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.entity.PurchaseReturn;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.vo.purchase.returned.QueryPurchaseReturnVo;
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
  List<PurchaseReturnDto> query(@Param("vo") QueryPurchaseReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseReturnDto getById(String id);

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
  List<PurchaseReturnDto> getApprovedList(@Param("supplierId") String supplierId,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
      @Param("settleStatus") SettleStatus settleStatus);
}
