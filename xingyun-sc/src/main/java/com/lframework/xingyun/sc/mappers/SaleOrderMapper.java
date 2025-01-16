package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleProductVo;
import com.lframework.xingyun.sc.vo.sale.SaleOrderSelectorVo;
import com.lframework.xingyun.core.annotations.permission.DataPermission;
import com.lframework.xingyun.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.core.components.permission.SysDataPermissionDataPermissionType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-21
 */
public interface SaleOrderMapper extends BaseMapper<SaleOrder> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "o", autoParse = true),
      @Sort(value = "createTime", alias = "o", autoParse = true),
  })
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "o")
  })
  List<SaleOrder> query(@Param("vo") QuerySaleOrderVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleOrderFullDto getDetail(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "o")
  })
  List<SaleOrder> selector(@Param("vo") SaleOrderSelectorVo vo);

  /**
   * 根据ID查询（出库业务）
   *
   * @param id
   * @return
   */
  SaleOrderWithOutDto getWithOut(@Param("id") String id, @Param("requireSale") Boolean requireSale);

  /**
   * 查询列表（出库业务）
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "o")
  })
  List<SaleOrder> queryWithOut(@Param("vo") QuerySaleOrderWithOutVo vo,
      @Param("multipleRelate") boolean multipleRelate);

  /**
   * 根据关键字销售采购商品信息
   *
   * @param condition
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<SaleProductDto> querySaleByCondition(
      @Param("condition") String condition, @Param("isReturn") Boolean isReturn);

  /**
   * 查询可销售商品信息
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<SaleProductDto> querySaleList(@Param("vo") QuerySaleProductVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleProductDto getSaleById(String id);
}
