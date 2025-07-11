package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import com.lframework.starter.web.inner.components.permission.ProductDataPermissionDataPermissionType;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferOrderFullDto;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferProductDto;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferProductVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 仓库调拨单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface ScTransferOrderMapper extends BaseMapper<ScTransferOrder> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "tb", autoParse = true),
      @Sort(value = "createTime", alias = "tb", autoParse = true),
      @Sort(value = "approveTime", alias = "tb", autoParse = true),
  })
  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "tb")
  })
  List<ScTransferOrder> query(@Param("vo") QueryScTransferOrderVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  ScTransferOrderFullDto getDetail(@Param("id") String id);

  /**
   * 根据关键字查询库存调整单商品信息
   *
   * @param scId
   * @param condition
   * @return
   */
  @DataPermissions(type = ProductDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<ScTransferProductDto> queryScTransferByCondition(
      @Param("scId") String scId, @Param("condition") String condition);

  /**
   * 查询库存调整单商品信息
   *
   * @param vo
   * @return
   */
  @DataPermissions(type = ProductDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<ScTransferProductDto> queryScTransferList(
      @Param("vo") QueryScTransferProductVo vo);
}
