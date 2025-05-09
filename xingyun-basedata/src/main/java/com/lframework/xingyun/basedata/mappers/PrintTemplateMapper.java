package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import com.lframework.xingyun.basedata.vo.print.QueryPrintTemplateVo;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 打印模板 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2025-01-28
 */
public interface PrintTemplateMapper extends BaseMapper<PrintTemplate> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "name", alias = "tb", autoParse = true),
  })
  List<PrintTemplate> query(@Param("vo") QueryPrintTemplateVo vo);
}
