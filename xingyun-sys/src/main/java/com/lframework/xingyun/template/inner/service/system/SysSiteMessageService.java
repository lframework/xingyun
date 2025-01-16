package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.dto.message.site.SiteMessageDto;
import com.lframework.xingyun.template.inner.entity.SysSiteMessage;
import com.lframework.xingyun.template.inner.vo.system.message.site.QuerySysSiteMessageByUserVo;
import com.lframework.xingyun.template.inner.vo.system.message.site.QuerySysSiteMessageVo;
import java.util.List;

public interface SysSiteMessageService extends BaseMpService<SysSiteMessage> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysSiteMessage> query(Integer pageIndex, Integer pageSize, QuerySysSiteMessageVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysSiteMessage> query(QuerySysSiteMessageVo vo);

  /**
   * 根据用户查询
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysSiteMessage> queryByUser(Integer pageIndex, Integer pageSize,
      QuerySysSiteMessageByUserVo vo);

  /**
   * 查询内容
   *
   * @param id
   * @return
   */
  SiteMessageDto getContent(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysSiteMessage findById(String id);

  /**
   * 设置已读
   *
   * @param id
   */
  boolean setReaded(String id);

  /**
   * WS通知
   *
   * @param userId
   */
  void noticeForWs(String userId);

  /**
   * 发送通知
   */
  void noticeForWsWithSessionId(String sessionId);
}
