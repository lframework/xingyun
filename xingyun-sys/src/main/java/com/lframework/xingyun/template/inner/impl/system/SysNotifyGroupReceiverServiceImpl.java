package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroupReceiver;
import com.lframework.xingyun.template.inner.mappers.system.SysNotifyGroupReceiverMapper;
import com.lframework.xingyun.template.inner.service.system.SysNotifyGroupReceiverService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SysNotifyGroupReceiverServiceImpl extends
    BaseMpServiceImpl<SysNotifyGroupReceiverMapper, SysNotifyGroupReceiver>
    implements SysNotifyGroupReceiverService {

  @Override
  public List<String> getReceiverIdsByGroupId(String groupId) {
    Wrapper<SysNotifyGroupReceiver> queryWrapper = Wrappers.lambdaQuery(
            SysNotifyGroupReceiver.class).select(SysNotifyGroupReceiver::getReceiverId)
        .eq(SysNotifyGroupReceiver::getGroupId, groupId);
    return this.list(queryWrapper).stream().map(SysNotifyGroupReceiver::getReceiverId)
        .collect(Collectors.toList());
  }
}
