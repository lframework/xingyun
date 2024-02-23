package com.lframework.xingyun.template.inner.mappers;

import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.entity.Tenant;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.vo.system.tenant.QueryTenantVo;
import com.lframework.xingyun.template.inner.vo.system.tenant.TenantSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenantMapper extends BaseMapper<Tenant> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "id", alias = "tb", autoParse = true),
      @Sort(value = "name", alias = "tb", autoParse = true),
  })
  List<Tenant> query(@Param("vo") QueryTenantVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<Tenant> selector(@Param("vo") TenantSelectorVo vo);
}
