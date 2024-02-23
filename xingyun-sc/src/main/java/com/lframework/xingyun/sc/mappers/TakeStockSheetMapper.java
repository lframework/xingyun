package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;
import com.lframework.xingyun.template.core.annotations.permission.DataPermission;
import com.lframework.xingyun.template.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.core.components.permission.SysDataPermissionDataPermissionType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 盘点单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface TakeStockSheetMapper extends BaseMapper<TakeStockSheet> {

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
  List<TakeStockSheet> query(@Param("vo") QueryTakeStockSheetVo vo);

  /**
   * 根据ID查询详情
   *
   * @param id
   * @return
   */
  TakeStockSheetFullDto getDetail(String id);

  /**
   * 根据预先盘点单ID判断是否有盘点单关联
   *
   * @param preSheetId
   * @return
   */
  Boolean hasRelatePreTakeStockSheet(String preSheetId);

  /**
   * 根据盘点任务ID查询是否有未审核通过的盘点单
   *
   * @param planId
   * @return
   */
  Boolean hasUnApprove(String planId);

  /**
   * 根据关键字查询盘点单商品信息
   *
   * @param condition
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<TakeStockSheetProductDto> queryTakeStockByCondition(@Param("planId") String planId,
      @Param("condition") String condition);

  /**
   * 查询盘点单商品信息
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<TakeStockSheetProductDto> queryTakeStockList(@Param("vo") QueryTakeStockSheetProductVo vo);
}
