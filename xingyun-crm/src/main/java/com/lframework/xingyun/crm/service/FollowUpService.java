package com.lframework.xingyun.crm.service;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.crm.entity.FollowUp;
import com.lframework.xingyun.crm.vo.followup.CreateFollowUpVo;
import java.util.List;

public interface FollowUpService extends BaseMpService<FollowUp> {

  List<FollowUp> listByClueId(String clueId);

  List<FollowUp> listByOpportunityId(String opportunityId);

  String create(CreateFollowUpVo vo);

  void deleteById(String id);
}
