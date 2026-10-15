package com.lframework.xingyun.crm.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.core.annotations.sort.Sort;
import com.lframework.starter.web.core.annotations.sort.Sorts;
import com.lframework.xingyun.crm.entity.Opportunity;
import com.lframework.xingyun.crm.vo.opportunity.QueryOpportunityVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OpportunityMapper extends BaseMapper<Opportunity> {

  @Sorts({
      @Sort(value = "code", alias = "o", autoParse = true),
      @Sort(value = "createTime", alias = "o", autoParse = true),
      @Sort(value = "expectedCloseDate", alias = "o", autoParse = true),
  })
  List<Opportunity> query(@Param("vo") QueryOpportunityVo vo);
}
