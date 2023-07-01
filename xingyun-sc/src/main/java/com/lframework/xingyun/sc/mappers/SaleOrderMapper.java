package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.vo.sale.QuerySaleProductVo;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.vo.sale.SaleOrderSelectorVo;
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
  List<SaleOrder> query(@Param("vo") QuerySaleOrderVo vo,
      @Param("dataPermission") String dataPermission);

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
  List<SaleOrder> selector(@Param("vo") SaleOrderSelectorVo vo,
      @Param("dataPermission") String dataPermission);

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
  List<SaleOrder> queryWithOut(@Param("vo") QuerySaleOrderWithOutVo vo,
      @Param("multipleRelate") boolean multipleRelate,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据关键字销售采购商品信息
   *
   * @param condition
   * @return
   */
  List<SaleProductDto> querySaleByCondition(
      @Param("condition") String condition, @Param("isReturn") Boolean isReturn, @Param("dataPermission") String dataPermission);

  /**
   * 查询可销售商品信息
   *
   * @param vo
   * @return
   */
  List<SaleProductDto> querySaleList(@Param("vo") QuerySaleProductVo vo,
      @Param("dataPermission") String dataPermission);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleProductDto getSaleById(String id);
}
