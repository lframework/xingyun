package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.template.inner.entity.SysUserGroupDetail;
import com.lframework.xingyun.template.inner.mappers.system.SysUserGroupDetailMapper;
import com.lframework.xingyun.template.inner.service.system.SysUserGroupDetailService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SysUserGroupDetailServiceImpl extends
    BaseMpServiceImpl<SysUserGroupDetailMapper, SysUserGroupDetail>
    implements SysUserGroupDetailService {

  @Override
  public List<String> getUserIdsByGroupId(String groupId) {
    Wrapper<SysUserGroupDetail> queryWrapper = Wrappers.lambdaQuery(
            SysUserGroupDetail.class).select(SysUserGroupDetail::getUserId)
        .eq(SysUserGroupDetail::getGroupId, groupId);
    return this.list(queryWrapper).stream().map(SysUserGroupDetail::getUserId)
        .collect(Collectors.toList());
  }
}
