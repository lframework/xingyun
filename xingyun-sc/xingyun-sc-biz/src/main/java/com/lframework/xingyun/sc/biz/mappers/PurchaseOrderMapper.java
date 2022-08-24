package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.facade.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrder;
import com.lframework.xingyun.sc.facade.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.facade.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.facade.vo.purchase.QueryPurchaseOrderWithRecevieVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrder> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PurchaseOrder> query(@Param("vo") QueryPurchaseOrderVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseOrderFullDto getDetail(String id);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<PurchaseOrderFullDto> queryFulls(@Param("vo") QueryPurchaseOrderVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<PurchaseOrder> selector(@Param("vo") PurchaseOrderSelectorVo vo);

  /**
   * 根据ID查询（收货业务）
   *
   * @param id
   * @return
   */
  PurchaseOrderWithReceiveDto getWithReceive(@Param("id") String id,
      @Param("requirePurchase") Boolean requirePurchase);

  /**
   * 查询列表（收货业务）
   *
   * @param vo
   * @return
   */
  List<PurchaseOrder> queryWithReceive(@Param("vo") QueryPurchaseOrderWithRecevieVo vo,
      @Param("multipleRelate") boolean multipleRelate);
}
