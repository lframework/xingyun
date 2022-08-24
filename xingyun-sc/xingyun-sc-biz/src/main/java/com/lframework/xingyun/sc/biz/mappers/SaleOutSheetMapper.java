package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.dto.sale.out.SaleOutSheetFullDto;
import com.lframework.xingyun.sc.facade.dto.sale.out.SaleOutSheetWithReturnDto;
import com.lframework.xingyun.sc.facade.entity.SaleOutSheet;
import com.lframework.xingyun.sc.facade.vo.sale.out.QuerySaleOutSheetVo;
import com.lframework.xingyun.sc.facade.vo.sale.out.QuerySaleOutSheetWithReturnVo;
import com.lframework.xingyun.sc.facade.vo.sale.out.SaleOutSheetSelectorVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-26
 */
public interface SaleOutSheetMapper extends BaseMapper<SaleOutSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleOutSheet> query(@Param("vo") QuerySaleOutSheetVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SaleOutSheet> selector(@Param("vo") SaleOutSheetSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleOutSheetFullDto getDetail(String id);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleOutSheetFullDto> queryFulls(@Param("vo") QuerySaleOutSheetVo vo);

  /**
   * 根据ID查询（销售退货业务）
   *
   * @param id
   * @return
   */
  SaleOutSheetWithReturnDto getWithReturn(@Param("id") String id,
      @Param("requireOut") Boolean requireOut);

  /**
   * 查询列表（销售退货业务）
   *
   * @param vo
   * @return
   */
  List<SaleOutSheet> queryWithReturn(@Param("vo") QuerySaleOutSheetWithReturnVo vo,
      @Param("multipleRelate") boolean multipleRelate);

  /**
   * 查询已审核列表
   *
   * @param customerId
   * @param startTime
   * @param endTime
   * @return
   */
  List<SaleOutSheet> getApprovedList(@Param("customerId") String customerId,
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
