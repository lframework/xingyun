package com.lframework.xingyun.template.inner.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.template.inner.entity.SysModule;
import com.lframework.xingyun.template.inner.mappers.SysModuleMapper;
import com.lframework.xingyun.template.inner.service.SysModuleService;
import org.springframework.stereotype.Service;

@DS("master")
@Service
public class SysModuleServiceImpl extends BaseMpServiceImpl<SysModuleMapper, SysModule> implements
    SysModuleService {

}
