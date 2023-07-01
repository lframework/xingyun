package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysOpenDomain;
import com.lframework.xingyun.template.inner.vo.system.open.QuerySysOpenDomainVo;
import com.lframework.xingyun.template.inner.vo.system.open.CreateSysOpenDomainVo;
import com.lframework.xingyun.template.inner.vo.system.open.SysOpenDomainSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.open.UpdateSysOpenDomainSecretVo;
import com.lframework.xingyun.template.inner.vo.system.open.UpdateSysOpenDomainVo;

public interface SysOpenDomainService extends BaseMpService<SysOpenDomain> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<SysOpenDomain> query(QuerySysOpenDomainVo vo);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<SysOpenDomain> selector(SysOpenDomainSelectorVo vo);


  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysOpenDomain findById(Integer id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysOpenDomainVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysOpenDomainVo vo);

  /**
   * 修改Api密钥
   *
   * @param vo
   */
  void updateApiSecret(UpdateSysOpenDomainSecretVo vo);
}
