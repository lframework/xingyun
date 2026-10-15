package com.lframework.xingyun.crm.impl;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.IdUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.crm.entity.FollowUp;
import com.lframework.xingyun.crm.enums.CrmOpLogType;
import com.lframework.xingyun.crm.mappers.FollowUpMapper;
import com.lframework.xingyun.crm.service.FollowUpService;
import com.lframework.xingyun.crm.vo.followup.CreateFollowUpVo;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowUpServiceImpl extends BaseMpServiceImpl<FollowUpMapper, FollowUp>
    implements FollowUpService {

  @Override
  public List<FollowUp> listByClueId(String clueId) {
    return getBaseMapper().listByClueId(clueId);
  }

  @Override
  public List<FollowUp> listByOpportunityId(String opportunityId) {
    return getBaseMapper().listByOpportunityId(opportunityId);
  }

  @OpLog(type = CrmOpLogType.class, name = "新增跟进记录")
  @Transactional
  @Override
  public String create(CreateFollowUpVo vo) {
    if (vo.getClueId() == null && vo.getOpportunityId() == null) {
      throw new DefaultClientException("线索ID和商机ID不能同时为空！");
    }
    FollowUp followUp = new FollowUp();
    followUp.setId(IdUtil.getId());
    followUp.setClueId(vo.getClueId());
    followUp.setOpportunityId(vo.getOpportunityId());
    followUp.setFollowType(vo.getFollowType());
    followUp.setContent(vo.getContent());
    followUp.setNextFollowDate(vo.getNextFollowDate());
    getBaseMapper().insert(followUp);
    return followUp.getId();
  }

  @OpLog(type = CrmOpLogType.class, name = "删除跟进记录")
  @Transactional
  @Override
  public void deleteById(String id) {
    FollowUp followUp = getById(id);
    if (followUp == null) {
      throw new DefaultClientException("跟进记录不存在！");
    }
    removeById(id);
  }
}
