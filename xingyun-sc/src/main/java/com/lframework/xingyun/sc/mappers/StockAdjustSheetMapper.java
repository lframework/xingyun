package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustProductDto;
import com.lframework.xingyun.sc.dto.stock.adjust.stock.StockAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockAdjustSheet;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustProductVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.QueryStockAdjustSheetVo;
import com.lframework.xingyun.template.core.annotations.permission.DataPermission;
import com.lframework.xingyun.template.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.core.components.permission.SysDataPermissionDataPermissionType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 库存调整单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface StockAdjustSheetMapper extends BaseMapper<StockAdjustSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "updateTime", alias = "tb", autoParse = true),
      @Sort(value = "approveTime", alias = "tb", autoParse = true),
  })
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "tb")
  })
  List<StockAdjustSheet> query(@Param("vo") QueryStockAdjustSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockAdjustSheetFullDto getDetail(@Param("id") String id);

  /**
   * 根据关键字查询库存调整单商品信息
   *
   * @param scId
   * @param condition
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<StockAdjustProductDto> queryStockAdjustByCondition(
      @Param("scId") String scId, @Param("condition") String condition);

  /**
   * 查询库存调整单商品信息
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<StockAdjustProductDto> queryStockAdjustList(
      @Param("vo") QueryStockAdjustProductVo vo);
}
