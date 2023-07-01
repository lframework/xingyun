package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.xingyun.template.inner.dto.system.notice.SysNoticeDto;
import com.lframework.xingyun.template.inner.entity.SysNotice;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.notice.CreateSysNoticeVo;
import com.lframework.xingyun.template.inner.vo.system.notice.QuerySysNoticeByUserVo;
import com.lframework.xingyun.template.inner.vo.system.notice.QuerySysNoticeVo;
import com.lframework.xingyun.template.inner.vo.system.notice.UpdateSysNoticeVo;
import java.util.List;

/**
 * 系统通知 Service
 *
 * @author zmj
 */
public interface SysNoticeService extends BaseMpService<SysNotice> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<SysNotice> query(Integer pageIndex, Integer pageSize, QuerySysNoticeVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysNotice> query(QuerySysNoticeVo vo);

  /**
   * 根据用户查询
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<QuerySysNoticeByUserDto> queryByUser(Integer pageIndex, Integer pageSize,
      QuerySysNoticeByUserVo vo);

  /**
   * 根据ID查询内容
   *
   * @param id
   * @return
   */
  SysNoticeDto getContent(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysNotice findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysNoticeVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysNoticeVo vo);

  /**
   * 发布
   *
   * @param id
   */
  void publish(String id);

  /**
   * 设置已读
   *
   * @param id
   * @param userId
   */
  void setReaded(String id, String userId);

  /**
   * 发送通知
   */
  void noticeForWs();

  /**
   * 发送通知
   */
  void noticeForWs(String userId);

  /**
   * 发送通知
   */
  void noticeForWsWithSessionId(String sessionId);

}
