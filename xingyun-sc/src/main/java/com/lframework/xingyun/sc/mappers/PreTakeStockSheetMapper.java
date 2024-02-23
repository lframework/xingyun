package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockProductDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.vo.stock.take.pre.PreTakeStockSheetSelectorVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockProductVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import com.lframework.xingyun.template.core.annotations.permission.DataPermission;
import com.lframework.xingyun.template.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.core.components.permission.SysDataPermissionDataPermissionType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 预先盘点单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface PreTakeStockSheetMapper extends BaseMapper<PreTakeStockSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "updateTime", alias = "tb", autoParse = true),
  })
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "tb")
  })
  List<PreTakeStockSheet> query(@Param("vo") QueryPreTakeStockSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  PreTakeStockSheetFullDto getDetail(@Param("id") String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "tb")
  })
  List<PreTakeStockSheet> selector(@Param("vo") PreTakeStockSheetSelectorVo vo);

  /**
   * 根据预先盘点单、盘点任务查询商品信息
   *
   * @param id
   * @param planId
   * @return
   */
  List<QueryPreTakeStockSheetProductDto> getProducts(@Param("id") String id,
      @Param("planId") String planId);

  /**
   * 根据关键字查询预先盘点单商品信息
   *
   * @param condition
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<PreTakeStockProductDto> queryPreTakeStockByCondition(@Param("condition") String condition);

  /**
   * 查询预先盘点单商品信息
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<PreTakeStockProductDto> queryPreTakeStockList(@Param("vo") QueryPreTakeStockProductVo vo);
}
