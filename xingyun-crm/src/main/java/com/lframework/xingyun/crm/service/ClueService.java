package com.lframework.xingyun.crm.service;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.crm.entity.Clue;
import com.lframework.xingyun.crm.vo.clue.CreateClueVo;
import com.lframework.xingyun.crm.vo.clue.QueryClueVo;
import com.lframework.xingyun.crm.vo.clue.UpdateClueVo;

public interface ClueService extends BaseMpService<Clue> {

  PageResult<Clue> query(Integer pageIndex, Integer pageSize, QueryClueVo vo);

  String create(CreateClueVo vo);

  void update(UpdateClueVo vo);

  /** 标记线索为无效 */
  void markInvalid(String id);

  /** 转化为商机，返回新建商机ID */
  String convertToOpportunity(String id);

  void deleteById(String id);
}
