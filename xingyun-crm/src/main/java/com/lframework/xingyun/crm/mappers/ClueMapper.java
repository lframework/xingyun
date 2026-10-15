package com.lframework.xingyun.crm.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.xingyun.crm.entity.Clue;
import com.lframework.xingyun.crm.vo.clue.QueryClueVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClueMapper extends BaseMapper<Clue> {

  @Sorts({
      @Sort(value = "code", alias = "c", autoParse = true),
      @Sort(value = "createTime", alias = "c", autoParse = true),
  })
  List<Clue> query(@Param("vo") QueryClueVo vo);
}
