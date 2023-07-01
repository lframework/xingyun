package com.lframework.xingyun.template.inner.impl.system;

import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.template.inner.mappers.system.SysUserTelephoneMapper;
import com.lframework.xingyun.template.inner.entity.SysUserTelephone;
import com.lframework.xingyun.template.inner.service.system.SysUserTelephoneService;
import org.springframework.stereotype.Service;

@Service
public class SysUserTelephoneServiceImpl extends
    BaseMpServiceImpl<SysUserTelephoneMapper, SysUserTelephone> implements
    SysUserTelephoneService {

}
