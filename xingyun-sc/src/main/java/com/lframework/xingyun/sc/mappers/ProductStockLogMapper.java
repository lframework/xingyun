package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import com.lframework.xingyun.sc.vo.stock.log.QueryProductStockLogVo;
import com.lframework.xingyun.core.annotations.permission.DataPermission;
import com.lframework.xingyun.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.core.components.permission.SysDataPermissionDataPermissionType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-14
 */
public interface ProductStockLogMapper extends BaseMapper<ProductStockLog> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "scCode", alias = "sc.code"),
      @Sort(value = "productCode", alias = "g.code"),
      @Sort(value = "oriStockNum", alias = "gsl", autoParse = true),
      @Sort(value = "curStockNum", alias = "gsl", autoParse = true),
      @Sort(value = "stockNum", alias = "gsl", autoParse = true),
      @Sort(value = "oriTaxPrice", alias = "gsl", autoParse = true),
      @Sort(value = "curTaxPrice", alias = "gsl", autoParse = true),
      @Sort(value = "taxAmount", alias = "gsl", autoParse = true),
      @Sort(value = "createTime", alias = "gsl", autoParse = true),
      @Sort(value = "bizCode", alias = "gsl", autoParse = true),
      @Sort(value = "bizType", alias = "gsl", autoParse = true),
  })
  @DataPermissions(type = SysDataPermissionDataPermissionType.PRODUCT, value = {
      @DataPermission(template = "product", alias = "g"),
      @DataPermission(template = "brand", alias = "b"),
      @DataPermission(template = "category", alias = "c")
  })
  List<ProductStockLog> query(@Param("vo") QueryProductStockLogVo vo);
}
