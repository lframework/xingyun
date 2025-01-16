package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.entity.SysGenerateCode;
import com.lframework.xingyun.template.inner.vo.system.generate.QuerySysGenerateCodeVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysGenerateCodeMapper extends
    BaseMapper<SysGenerateCode> {

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
  List<SysGenerateCode> query(@Param("vo") QuerySysGenerateCodeVo vo);
}
