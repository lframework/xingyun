package com.lframework.xingyun.template.inner.sign.handler;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.inner.sign.util.SignUtil;
import com.lframework.xingyun.template.inner.entity.SysOpenDomain;
import com.lframework.xingyun.template.inner.service.system.SysOpenDomainService;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import com.lframework.starter.web.sign.CheckSignHandler;
import com.lframework.starter.web.utils.TenantUtil;
import com.lframework.starter.web.vo.OpenApiReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultCheckSignHandler implements CheckSignHandler {

  @Autowired
  private SysOpenDomainService sysOpenDomainService;

  @Override
  public boolean check(OpenApiReqVo req) {
    Integer clientId = req.getClientId();
    if (clientId == null) {
      return false;
    }

    String timestamp = req.getTimestamp();
    if (StringUtil.isBlank(timestamp)) {
      return false;
    }

    String nonceStr = req.getNonceStr();
    if (StringUtil.isBlank(nonceStr)) {
      return false;
    }

    String params = req.getParams();
    if (StringUtil.isBlank(params)) {
      return false;
    }

    String sign = req.getSign();
    if (StringUtil.isBlank(sign)) {
      return false;
    }

    SysOpenDomain domain = sysOpenDomainService.findById(clientId);
    if (domain == null || !domain.getAvailable()) {
      return false;
    }

    return SignUtil
        .validate(clientId.toString(), domain.getApiSecret(), timestamp, nonceStr, params, sign);
  }

  @Override
  public void setTenantId(OpenApiReqVo req) {
    if (TenantUtil.enableTenant()) {
      SysOpenDomain domain = sysOpenDomainService.findById(req.getClientId());
      Integer tenantId = domain.getTenantId();
      if (tenantId == null) {
        throw new DefaultSysException("开放域没有设置租户ID");
      } else {
        TenantContextHolder.setTenantId(domain.getTenantId());
      }
    }
  }
}
