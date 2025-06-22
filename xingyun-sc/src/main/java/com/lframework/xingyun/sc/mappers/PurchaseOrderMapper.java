package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import com.lframework.starter.web.inner.components.permission.ProductDataPermissionDataPermissionType;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderFullDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseOrderWithReceiveDto;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.vo.purchase.PurchaseOrderSelectorVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseOrderWithReceiveVo;
import com.lframework.xingyun.sc.vo.purchase.QueryPurchaseProductVo;
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
  @Sorts({
      @Sort(value = "code", alias = "o", autoParse = true),
      @Sort(value = "createTime", alias = "o", autoParse = true),
      @Sort(value = "approveTime", alias = "o", autoParse = true),
  })
  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "o")
  })
  List<PurchaseOrder> query(@Param("vo") QueryPurchaseOrderVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseOrderFullDto getDetail(@Param("id") String id, @Param("isForm") Boolean isForm);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "o")
  })
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
  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "o")
  })
  List<PurchaseOrder> queryWithReceive(@Param("vo") QueryPurchaseOrderWithReceiveVo vo,
      @Param("multipleRelate") boolean multipleRelate);

  /**
   * 根据关键字查询采购商品信息
   *
   * @param condition
   * @return
   */
  @DataPermissions(type = ProductDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<PurchaseProductDto> queryPurchaseByCondition(
      @Param("condition") String condition);

  /**
   * 查询可采购商品信息
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = ProductDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<PurchaseProductDto> queryPurchaseList(@Param("vo") QueryPurchaseProductVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PurchaseProductDto getPurchaseById(String id);
}
