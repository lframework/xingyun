package com.lframework.xingyun.crm.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.crm.entity.FollowUp;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FollowUpMapper extends BaseMapper<FollowUp> {

  List<FollowUp> listByClueId(@Param("clueId") String clueId);

  List<FollowUp> listByOpportunityId(@Param("opportunityId") String opportunityId);
}
