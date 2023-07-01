package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseProductVo;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
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
  List<PurchaseOrder> query(@Param("vo") QueryPurchaseOrderVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseOrderFullDto getDetail(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<PurchaseOrder> selector(@Param("vo") PurchaseOrderSelectorVo vo,
      @Param("dataPermission") String dataPermission);

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
      @Param("multipleRelate") boolean multipleRelate,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据关键字查询采购商品信息
   *
   * @param condition
   * @return
   */
  List<PurchaseProductDto> queryPurchaseByCondition(
      @Param("condition") String condition, @Param("dataPermission") String dataPermission);

  /**
   * 查询可采购商品信息
   *
   * @param vo
   * @return
   */
  List<PurchaseProductDto> queryPurchaseList(@Param("vo") QueryPurchaseProductVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseProductDto getPurchaseById(String id);
}
