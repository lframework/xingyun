package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderWithRecevieVo;
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
  List<PurchaseOrderDto> query(@Param("vo") QueryPurchaseOrderVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseOrderDto getById(String id);

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
  List<PurchaseOrderDto> selector(@Param("vo") PurchaseOrderSelectorVo vo);

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
  List<PurchaseOrderDto> queryWithReceive(@Param("vo") QueryPurchaseOrderWithRecevieVo vo,
      @Param("multipleRelate") boolean multipleRelate);
}
