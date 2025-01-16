package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroup;
import com.lframework.xingyun.template.inner.vo.system.notify.CreateSysNotifyGroupVo;
import com.lframework.xingyun.template.inner.vo.system.notify.QuerySysNotifyGroupVo;
import com.lframework.xingyun.template.inner.vo.system.notify.SysNotifyGroupSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.notify.UpdateSysNotifyGroupVo;
import java.util.List;
import java.util.Set;

public interface SysNotifyGroupService extends BaseMpService<SysNotifyGroup> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysNotifyGroup> query(Integer pageIndex, Integer pageSize,
      QuerySysNotifyGroupVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysNotifyGroup> query(QuerySysNotifyGroupVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysNotifyGroup findById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<SysNotifyGroup> selector(Integer pageIndex, Integer pageSize,
      SysNotifyGroupSelectorVo vo);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysNotifyGroupVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysNotifyGroupVo vo);

  /**
   * 根据ID查询接收人ID
   *
   * @param id
   * @return
   */
  Set<String> getReceiveUserIds(String id);
}
