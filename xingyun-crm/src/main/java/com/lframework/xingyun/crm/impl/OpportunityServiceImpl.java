package com.lframework.xingyun.crm.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.service.GenerateCodeService;
import com.lframework.xingyun.crm.components.code.GenerateCodeTypePool;
import com.lframework.xingyun.crm.entity.Opportunity;
import com.lframework.xingyun.crm.enums.CrmOpLogType;
import com.lframework.xingyun.crm.enums.OpportunityStatus;
import com.lframework.xingyun.crm.mappers.OpportunityMapper;
import com.lframework.xingyun.crm.service.OpportunityService;
import com.lframework.xingyun.crm.vo.opportunity.CloseOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.CreateOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.QueryOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.UpdateOpportunityVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpportunityServiceImpl extends BaseMpServiceImpl<OpportunityMapper, Opportunity>
    implements OpportunityService {

  @Autowired
  private GenerateCodeService generateCodeService;

  @Override
  public PageResult<Opportunity> query(Integer pageIndex, Integer pageSize, QueryOpportunityVo vo) {
    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Opportunity> datas = getBaseMapper().query(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = CrmOpLogType.class, name = "新增商机，编号：{}", params = "#code")
  @Transactional
  @Override
  public String create(CreateOpportunityVo vo) {
    Opportunity opp = new Opportunity();
    opp.setId(IdUtil.getId());
    opp.setCode(generateCodeService.generate(GenerateCodeTypePool.OPPORTUNITY));
    opp.setName(vo.getName());
    opp.setClueId(vo.getClueId());
    opp.setCustomerId(vo.getCustomerId());
    opp.setOwnerId(vo.getOwnerId());
    opp.setEstimatedAmount(vo.getEstimatedAmount());
    opp.setExpectedCloseDate(vo.getExpectedCloseDate());
    opp.setStage(vo.getStage() != null ? vo.getStage() : 0);
    opp.setStatus(OpportunityStatus.OPEN);
    opp.setDescription(vo.getDescription());
    getBaseMapper().insert(opp);
    return opp.getId();
  }

  @OpLog(type = CrmOpLogType.class, name = "修改商机，编号：{}", params = "#code")
  @Transactional
  @Override
  public void update(UpdateOpportunityVo vo) {
    Opportunity opp = getById(vo.getId());
    if (opp == null) {
      throw new DefaultClientException("商机不存在！");
    }
    if (opp.getStatus() != OpportunityStatus.OPEN) {
      throw new DefaultClientException("已关闭的商机不允许修改！");
    }
    LambdaUpdateWrapper<Opportunity> wrapper = Wrappers.<Opportunity>lambdaUpdate()
        .set(Opportunity::getName, vo.getName())
        .set(Opportunity::getCustomerId, vo.getCustomerId())
        .set(Opportunity::getOwnerId, vo.getOwnerId())
        .set(Opportunity::getEstimatedAmount, vo.getEstimatedAmount())
        .set(Opportunity::getExpectedCloseDate, vo.getExpectedCloseDate())
        .set(Opportunity::getStage, vo.getStage())
        .set(Opportunity::getDescription, vo.getDescription())
        .eq(Opportunity::getId, vo.getId());
    update(wrapper);
  }

  @OpLog(type = CrmOpLogType.class, name = "关闭商机，编号：{}", params = "#code")
  @Transactional
  @Override
  public void close(CloseOpportunityVo vo) {
    Opportunity opp = getById(vo.getId());
    if (opp == null) {
      throw new DefaultClientException("商机不存在！");
    }
    if (opp.getStatus() != OpportunityStatus.OPEN) {
      throw new DefaultClientException("该商机已关闭！");
    }
    OpportunityStatus newStatus = Boolean.TRUE.equals(vo.getWon())
        ? OpportunityStatus.WON : OpportunityStatus.LOST;
    // 赢单时阶段推进到4，输单推进到5
    int finalStage = Boolean.TRUE.equals(vo.getWon()) ? 4 : 5;
    LambdaUpdateWrapper<Opportunity> wrapper = Wrappers.<Opportunity>lambdaUpdate()
        .set(Opportunity::getStatus, newStatus)
        .set(Opportunity::getStage, finalStage)
        .set(Opportunity::getDescription, vo.getDescription() != null ? vo.getDescription() : opp.getDescription())
        .eq(Opportunity::getId, vo.getId());
    update(wrapper);
  }

  @OpLog(type = CrmOpLogType.class, name = "商机转化销售订单，商机编号：{}", params = "#code")
  @Transactional
  @Override
  public String convertToSaleOrder(String id) {
    Opportunity opp = getById(id);
    if (opp == null) {
      throw new DefaultClientException("商机不存在！");
    }
    if (opp.getStatus() != OpportunityStatus.OPEN) {
      throw new DefaultClientException("只有进行中的商机才能转化为销售订单！");
    }
    if (opp.getCustomerId() == null) {
      throw new DefaultClientException("商机未关联客户，请先补充客户信息再转化！");
    }
    // 自动赢单并关联客户，销售员在销售订单中自行填写商品明细
    LambdaUpdateWrapper<Opportunity> wrapper = Wrappers.<Opportunity>lambdaUpdate()
        .set(Opportunity::getStatus, OpportunityStatus.WON)
        .set(Opportunity::getStage, 4)
        .eq(Opportunity::getId, id);
    update(wrapper);

    // 返回客户ID供前端跳转至新建销售订单并预填客户
    return opp.getCustomerId();
  }

  @OpLog(type = CrmOpLogType.class, name = "删除商机，编号：{}", params = "#code")
  @Transactional
  @Override
  public void deleteById(String id) {
    Opportunity opp = getById(id);
    if (opp == null) {
      throw new DefaultClientException("商机不存在！");
    }
    if (opp.getStatus() == OpportunityStatus.WON) {
      throw new DefaultClientException("已赢单的商机不允许删除！");
    }
    removeById(id);
  }
}
