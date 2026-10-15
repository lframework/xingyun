package com.lframework.xingyun.crm.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.IdUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.service.GenerateCodeService;
import com.lframework.xingyun.crm.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.crm.entity.Clue;
import com.lframework.xingyun.crm.entity.Opportunity;
import com.lframework.xingyun.crm.enums.ClueStatus;
import com.lframework.xingyun.crm.enums.CrmOpLogType;
import com.lframework.xingyun.crm.enums.OpportunityStatus;
import com.lframework.xingyun.crm.mappers.ClueMapper;
import com.lframework.xingyun.crm.service.ClueService;
import com.lframework.xingyun.crm.service.OpportunityService;
import com.lframework.xingyun.crm.vo.clue.CreateClueVo;
import com.lframework.xingyun.crm.vo.clue.QueryClueVo;
import com.lframework.xingyun.crm.vo.clue.UpdateClueVo;
import com.lframework.xingyun.crm.vo.opportunity.CreateOpportunityVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClueServiceImpl extends BaseMpServiceImpl<ClueMapper, Clue> implements ClueService {

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private OpportunityService opportunityService;

  @Override
  public PageResult<Clue> query(Integer pageIndex, Integer pageSize, QueryClueVo vo) {
    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Clue> datas = getBaseMapper().query(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = CrmOpLogType.class, name = "新增线索，编号：{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateClueVo vo) {
    Clue clue = new Clue();
    clue.setId(IdUtil.getId());
    clue.setCode(generateCodeService.generate(GenerateCodeTypePool.CLUE));
    clue.setContactName(vo.getContactName());
    clue.setContactPhone(vo.getContactPhone());
    clue.setCompanyName(vo.getCompanyName());
    clue.setSource(vo.getSource());
    clue.setOwnerId(vo.getOwnerId());
    clue.setStatus(ClueStatus.PENDING);
    clue.setDescription(vo.getDescription());
    getBaseMapper().insert(clue);
    return clue.getId();
  }

  @OpLog(type = CrmOpLogType.class, name = "修改线索，编号：{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateClueVo vo) {
    Clue clue = getById(vo.getId());
    if (clue == null) {
      throw new DefaultClientException("线索不存在！");
    }
    if (clue.getStatus() == ClueStatus.CONVERTED) {
      throw new DefaultClientException("已转化的线索不允许修改！");
    }
    LambdaUpdateWrapper<Clue> wrapper = Wrappers.<Clue>lambdaUpdate()
        .set(Clue::getContactName, vo.getContactName())
        .set(Clue::getContactPhone, vo.getContactPhone())
        .set(Clue::getCompanyName, vo.getCompanyName())
        .set(Clue::getSource, vo.getSource())
        .set(Clue::getOwnerId, vo.getOwnerId())
        .set(Clue::getDescription, vo.getDescription())
        .eq(Clue::getId, vo.getId());
    update(wrapper);
  }

  @OpLog(type = CrmOpLogType.class, name = "标记线索无效，编号：{}", params = "#code")
  @Transactional
  @Override
  public void markInvalid(String id) {
    Clue clue = getById(id);
    if (clue == null) {
      throw new DefaultClientException("线索不存在！");
    }
    if (clue.getStatus() == ClueStatus.CONVERTED) {
      throw new DefaultClientException("已转化的线索不允许操作！");
    }
    LambdaUpdateWrapper<Clue> wrapper = Wrappers.<Clue>lambdaUpdate()
        .set(Clue::getStatus, ClueStatus.INVALID)
        .eq(Clue::getId, id);
    update(wrapper);
  }

  @OpLog(type = CrmOpLogType.class, name = "线索转化商机，线索编号：{}", params = "#code")
  @Transactional
  @Override
  public String convertToOpportunity(String id) {
    Clue clue = getById(id);
    if (clue == null) {
      throw new DefaultClientException("线索不存在！");
    }
    if (clue.getStatus() == ClueStatus.CONVERTED) {
      throw new DefaultClientException("该线索已转化！");
    }
    if (clue.getStatus() == ClueStatus.INVALID) {
      throw new DefaultClientException("无效线索不允许转化！");
    }

    // 创建商机
    CreateOpportunityVo oppVo = new CreateOpportunityVo();
    oppVo.setName(clue.getCompanyName() != null ? clue.getCompanyName() + "-商机" : clue.getContactName() + "-商机");
    oppVo.setClueId(id);
    oppVo.setOwnerId(clue.getOwnerId());
    oppVo.setStage(0);
    String opportunityId = opportunityService.create(oppVo);

    // 更新线索状态为已转化
    LambdaUpdateWrapper<Clue> wrapper = Wrappers.<Clue>lambdaUpdate()
        .set(Clue::getStatus, ClueStatus.CONVERTED)
        .eq(Clue::getId, id);
    update(wrapper);

    return opportunityId;
  }

  @OpLog(type = CrmOpLogType.class, name = "删除线索，编号：{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {
    Clue clue = getById(id);
    if (clue == null) {
      throw new DefaultClientException("线索不存在！");
    }
    if (clue.getStatus() == ClueStatus.CONVERTED) {
      throw new DefaultClientException("已转化的线索不允许删除！");
    }
    removeById(id);
  }
}
