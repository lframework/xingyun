package com.lframework.xingyun.template.inner.impl.system;

import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.entity.SysMailMessage;
import com.lframework.xingyun.template.inner.mappers.system.SysMailMessageMapper;
import com.lframework.xingyun.template.inner.service.system.SysMailMessageService;
import com.lframework.xingyun.template.inner.vo.system.message.mail.QuerySysMailMessageVo;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SysMailMessageServiceImpl extends
    BaseMpServiceImpl<SysMailMessageMapper, SysMailMessage>
    implements SysMailMessageService {

  @Override
  public PageResult<SysMailMessage> query(Integer pageIndex, Integer pageSize,
      QuerySysMailMessageVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysMailMessage> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysMailMessage> query(QuerySysMailMessageVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public SysMailMessage findById(String id) {
    return getById(id);
  }
}
