package com.lframework.xingyun.sc.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.facade.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.facade.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.facade.entity.SaleOrder;
import com.lframework.xingyun.sc.facade.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.facade.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.facade.vo.sale.SaleOrderSelectorVo;
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
  List<SaleOrder> query(@Param("vo") QuerySaleOrderVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleOrderFullDto getDetail(String id);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SaleOrderFullDto> queryFulls(@Param("vo") QuerySaleOrderVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
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
  List<SaleOrder> queryWithOut(@Param("vo") QuerySaleOrderWithOutVo vo,
      @Param("multipleRelate") boolean multipleRelate);
}
