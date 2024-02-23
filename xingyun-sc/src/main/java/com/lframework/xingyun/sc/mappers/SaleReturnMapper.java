package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;
import com.lframework.xingyun.template.core.annotations.permission.DataPermission;
import com.lframework.xingyun.template.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.core.components.permission.SysDataPermissionDataPermissionType;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-04
 */
public interface SaleReturnMapper extends BaseMapper<SaleReturn> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "r", autoParse = true),
      @Sort(value = "createTime", alias = "r", autoParse = true),
      @Sort(value = "approveTime", alias = "r", autoParse = true),
  })
  @DataPermissions(type = SysDataPermissionDataPermissionType.ORDER, value = {
      @DataPermission(template = "order", alias = "r")
  })
  List<SaleReturn> query(@Param("vo") QuerySaleReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SaleReturnFullDto getDetail(String id);

  /**
   * 查询已审核列表
   *
   * @param customerId
   * @param startTime
   * @param endTime
   * @return
   */
  List<SaleReturn> getApprovedList(@Param("customerId") String customerId,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
      @Param("settleStatus") SettleStatus settleStatus);
}
