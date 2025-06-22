package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.inner.components.permission.ProductDataPermissionDataPermissionType;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import com.lframework.xingyun.sc.vo.stock.warning.QueryProductStockWarningVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 库存预警 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface ProductStockWarningMapper extends BaseMapper<ProductStockWarning> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "scCode", alias = "sc.code"),
      @Sort(value = "productCode", alias = "p.code"),
      @Sort(value = "minLimit", alias = "tb.min_limit"),
      @Sort(value = "maxLimit", alias = "tb.max_limit"),
      @Sort(value = "updateTime", alias = "tb.update_time"),
  })
  @DataPermissions(type = ProductDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "product", alias = "p"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<ProductStockWarning> query(@Param("vo") QueryProductStockWarningVo vo);
}
