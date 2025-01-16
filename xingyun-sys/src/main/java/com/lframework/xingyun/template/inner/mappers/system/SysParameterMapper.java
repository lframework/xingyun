package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.entity.SysParameter;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.vo.system.parameter.QuerySysParameterVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统参数 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysParameterMapper extends BaseMapper<SysParameter> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "pmKey", alias = "tb", autoParse = true),
      @Sort(value = "createTime", alias = "tb", autoParse = true),
  })
  List<SysParameter> query(@Param("vo") QuerySysParameterVo vo);
}
