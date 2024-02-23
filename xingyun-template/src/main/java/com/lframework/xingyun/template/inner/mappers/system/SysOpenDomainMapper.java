package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.entity.SysOpenDomain;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.vo.system.open.QuerySysOpenDomainVo;
import com.lframework.xingyun.template.inner.vo.system.open.SysOpenDomainSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-02
 */
public interface SysOpenDomainMapper extends BaseMapper<SysOpenDomain> {

  /**
   * 查询列表
   *
   * @return
   */
  @Sorts({
      @Sort(value = "id", autoParse = true),
      @Sort(value = "name", autoParse = true),
      @Sort(value = "tenantId", autoParse = true),
  })
  List<SysOpenDomain> query(@Param("vo") QuerySysOpenDomainVo vo);

  /**
   * 选择器
   *
   * @return
   */
  List<SysOpenDomain> selector(@Param("vo") SysOpenDomainSelectorVo vo);
}
