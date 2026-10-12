package com.lframework.xingyun.produce.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.xingyun.produce.entity.AssembleSheet;
import com.lframework.xingyun.produce.vo.assemble.QueryAssembleSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 组装单 Mapper
 *
 * @author zmj
 */
public interface AssembleSheetMapper extends BaseMapper<AssembleSheet> {

  /**
   * 查询列表
   */
  @Sorts({
      @Sort(value = "code", autoParse = true),
      @Sort(value = "createTime", autoParse = true),
  })
  List<AssembleSheet> query(@Param("vo") QueryAssembleSheetVo vo);
}
