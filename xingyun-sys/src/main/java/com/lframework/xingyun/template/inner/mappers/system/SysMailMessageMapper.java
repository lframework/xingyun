package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.entity.SysMailMessage;
import com.lframework.xingyun.template.inner.vo.system.message.mail.QuerySysMailMessageVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 邮件消息 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysMailMessageMapper extends BaseMapper<SysMailMessage> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysMailMessage> query(@Param("vo") QuerySysMailMessageVo vo);
}
