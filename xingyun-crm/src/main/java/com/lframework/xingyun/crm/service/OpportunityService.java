package com.lframework.xingyun.crm.service;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.crm.entity.Opportunity;
import com.lframework.xingyun.crm.vo.opportunity.CloseOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.CreateOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.QueryOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.UpdateOpportunityVo;

public interface OpportunityService extends BaseMpService<Opportunity> {

  PageResult<Opportunity> query(Integer pageIndex, Integer pageSize, QueryOpportunityVo vo);

  String create(CreateOpportunityVo vo);

  void update(UpdateOpportunityVo vo);

  /** 赢单或输单 */
  void close(CloseOpportunityVo vo);

  /** 转化为销售订单，返回销售订单ID */
  String convertToSaleOrder(String id);

  void deleteById(String id);
}
