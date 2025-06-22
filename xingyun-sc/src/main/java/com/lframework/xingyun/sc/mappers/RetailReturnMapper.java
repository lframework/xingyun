package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.vo.retail.returned.QueryRetailReturnVo;
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
public interface RetailReturnMapper extends BaseMapper<RetailReturn> {

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
  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "r")
  })
  List<RetailReturn> query(@Param("vo") QueryRetailReturnVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  RetailReturnFullDto getDetail(String id);
}
