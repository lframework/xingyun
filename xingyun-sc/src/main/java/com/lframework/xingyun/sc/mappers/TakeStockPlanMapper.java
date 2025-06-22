package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.vo.stock.take.plan.QueryTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.TakeStockPlanSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 盘点任务 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface TakeStockPlanMapper extends BaseMapper<TakeStockPlan> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "createTime", alias = "tb", autoParse = true),
      @Sort(value = "updateTime", alias = "tb", autoParse = true),
  })
  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "tb")
  })
  List<TakeStockPlan> query(@Param("vo") QueryTakeStockPlanVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "tb")
  })
  List<TakeStockPlan> selector(@Param("vo") TakeStockPlanSelectorVo vo);

  /**
   * 根据盘点任务ID查询商品信息
   *
   * @param planId
   * @return
   */
  List<QueryTakeStockPlanProductDto> getProducts(@Param("planId") String planId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  TakeStockPlanFullDto getDetail(String id);
}
