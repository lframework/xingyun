package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.dto.WsPushData;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.websocket.components.WsDataPusher;
import com.lframework.starter.websocket.events.UserConnectEvent;
import com.lframework.xingyun.template.inner.dto.message.site.SiteMessageDto;
import com.lframework.xingyun.template.inner.entity.SysSiteMessage;
import com.lframework.xingyun.template.inner.mappers.system.SysSiteMessageMapper;
import com.lframework.xingyun.template.inner.service.system.SysSiteMessageService;
import com.lframework.xingyun.template.inner.vo.system.message.site.QuerySysSiteMessageByUserVo;
import com.lframework.xingyun.template.inner.vo.system.message.site.QuerySysSiteMessageVo;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysSiteMessageServiceImpl extends
    BaseMpServiceImpl<SysSiteMessageMapper, SysSiteMessage>
    implements SysSiteMessageService {

  @Autowired
  private WsDataPusher wsDataPusher;

  @Override
  public PageResult<SysSiteMessage> query(Integer pageIndex, Integer pageSize,
      QuerySysSiteMessageVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysSiteMessage> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysSiteMessage> query(QuerySysSiteMessageVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<SysSiteMessage> queryByUser(Integer pageIndex, Integer pageSize,
      QuerySysSiteMessageByUserVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysSiteMessage> datas = getBaseMapper().queryByUser(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public SiteMessageDto getContent(String id) {
    SysSiteMessage data = getById(id);
    if (data == null) {
      return null;
    }
    return new SiteMessageDto(data);
  }

  @Override
  public SysSiteMessage findById(String id) {
    return getById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public boolean setReaded(String id) {
    Wrapper<SysSiteMessage> updateWrapper = Wrappers.lambdaUpdate(SysSiteMessage.class)
        .set(SysSiteMessage::getReaded, true)
        .set(SysSiteMessage::getReadTime, LocalDateTime.now())
        .eq(SysSiteMessage::getId, id).eq(SysSiteMessage::getReaded, false);
    return update(updateWrapper);
  }

  @Override
  public void noticeForWs(String userId) {
    WsPushData pushData = new WsPushData();
    pushData.setBizType("siteMessage");
    pushData.setIncludeUserId(userId);

    wsDataPusher.push(pushData);
  }

  @Override
  public void noticeForWsWithSessionId(String sessionId) {
    WsPushData pushData = new WsPushData();
    pushData.setBizType("siteMessage");
    pushData.setIncludeSessionIds(Collections.singletonList(sessionId));

    wsDataPusher.push(pushData);
  }

  @Component
  public static class ReloadSiteMessageListener implements ApplicationListener<UserConnectEvent> {

    @Autowired
    private SysSiteMessageService sysSiteMessageService;

    @Override
    public void onApplicationEvent(UserConnectEvent event) {
      sysSiteMessageService.noticeForWsWithSessionId(event.getSessionId());
    }
  }
}
