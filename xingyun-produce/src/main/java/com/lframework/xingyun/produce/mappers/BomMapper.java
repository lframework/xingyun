package com.lframework.xingyun.produce.mappers;

import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.produce.entity.Bom;
import com.lframework.xingyun.produce.vo.bom.QueryBomVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * BOM Mapper
 *
 * @author zmj
 */
public interface BomMapper extends BaseMapper<Bom> {

  /**
   * 查询列表
   */
  @Sorts({
      @Sort(value = "code", autoParse = true),
      @Sort(value = "createTime", autoParse = true),
  })
  List<Bom> query(@Param("vo") QueryBomVo vo);
}
