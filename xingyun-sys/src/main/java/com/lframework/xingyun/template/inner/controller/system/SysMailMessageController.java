package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.bo.system.message.mail.GetSysMailMessageBo;
import com.lframework.xingyun.template.inner.bo.system.message.mail.QuerySysMailMessageBo;
import com.lframework.xingyun.template.inner.entity.SysMailMessage;
import com.lframework.xingyun.template.inner.service.system.SysMailMessageService;
import com.lframework.xingyun.template.inner.vo.system.message.mail.QuerySysMailMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件消息 Controller
 *
 * @author zmj
 */
@Api(tags = "邮件消息")
@Validated
@RestController
@RequestMapping("/system/message/mail")
public class SysMailMessageController extends DefaultBaseController {

  @Autowired
  private SysMailMessageService sysMailMessageService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission("system:mail-message:manage")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysMailMessageBo>> query(@Valid QuerySysMailMessageVo vo) {

    PageResult<SysMailMessage> pageResult = sysMailMessageService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);

    List<SysMailMessage> datas = pageResult.getDatas();
    List<QuerySysMailMessageBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysMailMessageBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @HasPermission("system:mail-message:manage")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetSysMailMessageBo> get(@NotBlank(message = "id不能为空！") String id) {

    SysMailMessage data = sysMailMessageService.findById(id);
    if (data == null) {
      throw new DefaultClientException("邮件消息不存在！");
    }

    GetSysMailMessageBo result = new GetSysMailMessageBo(data);

    return InvokeResultBuilder.success(result);
  }
}
