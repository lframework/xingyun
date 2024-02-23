package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.common.threads.DefaultRunnable;
import com.lframework.starter.web.dto.WsPushData;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.websocket.components.WsDataPusher;
import com.lframework.starter.websocket.events.UserConnectEvent;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.entity.SysUser;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.inner.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.xingyun.template.inner.dto.system.notice.SysNoticeDto;
import com.lframework.xingyun.template.inner.entity.SysNotice;
import com.lframework.xingyun.template.inner.entity.SysNoticeLog;
import com.lframework.xingyun.template.inner.vo.system.notice.CreateSysNoticeVo;
import com.lframework.xingyun.template.inner.vo.system.notice.QuerySysNoticeByUserVo;
import com.lframework.xingyun.template.inner.vo.system.notice.QuerySysNoticeVo;
import com.lframework.xingyun.template.inner.vo.system.notice.UpdateSysNoticeVo;
import com.lframework.xingyun.template.inner.mappers.system.SysNoticeMapper;
import com.lframework.xingyun.template.inner.service.system.SysNoticeLogService;
import com.lframework.xingyun.template.inner.service.system.SysNoticeService;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import com.lframework.xingyun.template.inner.vo.system.user.QuerySysUserVo;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysNoticeServiceImpl extends BaseMpServiceImpl<SysNoticeMapper, SysNotice> implements
    SysNoticeService {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private SysNoticeLogService sysNoticeLogService;

  @Autowired
  private WsDataPusher wsDataPusher;

  @Override
  public PageResult<SysNotice> query(Integer pageIndex, Integer pageSize, QuerySysNoticeVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysNotice> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysNotice> query(QuerySysNoticeVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<QuerySysNoticeByUserDto> queryByUser(Integer pageIndex, Integer pageSize,
      QuerySysNoticeByUserVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<QuerySysNoticeByUserDto> datas = getBaseMapper().queryByUser(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysNotice.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysNoticeDto getContent(String id) {
    SysNotice record = getBaseMapper().selectById(id);
    if (record == null) {
      return null;
    }

    return new SysNoticeDto(record);
  }

  @Override
  public SysNotice findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "新增系统通知，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysNoticeVo vo) {

    SysNotice data = new SysNotice();
    data.setId(IdUtil.getId());
    data.setTitle(vo.getTitle());
    data.setContent(vo.getContent());
    data.setPublished(vo.getPublished());

    getBaseMapper().insert(data);

    if (vo.getPublished()) {
      ThreadUtil.execAsync(new DefaultRunnable(() -> {
        SysNoticeService thisService = getThis(getClass());
        thisService.publish(data.getId());
      }));
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "修改系统通知，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysNoticeVo vo) {

    SysNotice data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("系统通知不存在！");
    }

    LambdaUpdateWrapper<SysNotice> updateWrapper = Wrappers.lambdaUpdate(SysNotice.class)
        .set(SysNotice::getTitle, vo.getTitle()).set(SysNotice::getContent, vo.getContent())
        .set(SysNotice::getAvailable, vo.getAvailable())
        .set(SysNotice::getPublished, vo.getPublished()).set(SysNotice::getPublishTime, null)
        .set(SysNotice::getReadedNum, 0).set(SysNotice::getUnReadNum, 0)
        .eq(SysNotice::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    // 无论发布还是不发布 都要删除
    Wrapper<SysNoticeLog> deleteLogWrapper = Wrappers.lambdaQuery(SysNoticeLog.class)
        .eq(SysNoticeLog::getNoticeId, data.getId());
    sysNoticeLogService.remove(deleteLogWrapper);

    if (vo.getPublished()) {
      ThreadUtil.execAsync(new DefaultRunnable(() -> {
        SysNoticeService thisService = getThis(getClass());
        thisService.publish(data.getId());
      }));
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "发布系统通知，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void publish(String id) {

    // 查询所有用户
    QuerySysUserVo querySysUserVo = new QuerySysUserVo();
    querySysUserVo.setAvailable(true);
    List<SysUser> users = sysUserService.query(querySysUserVo);

    Wrapper<SysNotice> updateWrapper = Wrappers.lambdaUpdate(SysNotice.class)
        .eq(SysNotice::getId, id).set(SysNotice::getReadedNum, 0)
        .set(SysNotice::getUnReadNum, users.size()).set(SysNotice::getPublished, Boolean.TRUE)
        .set(SysNotice::getPublishTime, LocalDateTime.now());
    this.update(updateWrapper);

    Wrapper<SysNoticeLog> deleteLogWrapper = Wrappers.lambdaQuery(SysNoticeLog.class)
        .eq(SysNoticeLog::getNoticeId, id);
    sysNoticeLogService.remove(deleteLogWrapper);

    if (!CollectionUtil.isEmpty(users)) {
      List<SysNoticeLog> logs = users.stream().map(t -> {
        SysNoticeLog log = new SysNoticeLog();
        log.setId(IdUtil.getId());
        log.setNoticeId(id);
        log.setUserId(t.getId());
        log.setReaded(Boolean.FALSE);

        return log;
      }).collect(Collectors.toList());

      sysNoticeLogService.saveBatch(logs);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setReaded(String id, String userId) {
    if (sysNoticeLogService.setReaded(id, userId)) {
      getBaseMapper().setReaded(id);
    }
  }

  @Override
  public void noticeForWs() {
    WsPushData pushData = new WsPushData();
    pushData.setBizType("sysNotice");
    pushData.setAll(Boolean.TRUE);

    wsDataPusher.push(pushData);
  }

  @Override
  public void noticeForWs(String userId) {
    WsPushData pushData = new WsPushData();
    pushData.setBizType("sysNotice");
    pushData.setIncludeUserId(userId);

    wsDataPusher.push(pushData);
  }

  @Override
  public void noticeForWsWithSessionId(String sessionId) {
    WsPushData pushData = new WsPushData();
    pushData.setBizType("sysNotice");
    pushData.setIncludeSessionIds(Collections.singletonList(sessionId));

    wsDataPusher.push(pushData);
  }

  @CacheEvict(value = SysNotice.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }

  @Component
  public static class ReloadNoticeListener implements ApplicationListener<UserConnectEvent> {

    @Autowired
    private SysNoticeService sysNoticeService;

    @Override
    public void onApplicationEvent(UserConnectEvent event) {
      sysNoticeService.noticeForWsWithSessionId(event.getSessionId());
    }
  }
}
