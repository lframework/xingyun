package com.lframework.xingyun.basedata.service.logistics;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.vo.logistics.company.CreateLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanySelectorVo;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.UpdateLogisticsCompanyVo;
import java.util.Collection;
import java.util.List;

public interface LogisticsCompanyService extends BaseMpService<LogisticsCompany> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<LogisticsCompany> query(Integer pageIndex, Integer pageSize,
      QueryLogisticsCompanyVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<LogisticsCompany> query(QueryLogisticsCompanyVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  LogisticsCompany findById(String id);

  /**
   * 根据ID停用
   *
   * @param id
   */
  void unable(String id);

  /**
   * 根据ID启用
   *
   * @param id
   */
  void enable(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateLogisticsCompanyVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateLogisticsCompanyVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<LogisticsCompany> selector(Integer pageIndex, Integer pageSize,
      QueryLogisticsCompanySelectorVo vo);
}
