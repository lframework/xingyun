package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.threads.DefaultRunnable;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.bo.system.message.site.GetSysSiteMessageBo;
import com.lframework.xingyun.template.inner.bo.system.message.site.QueryMySysSiteMessageBo;
import com.lframework.xingyun.template.inner.bo.system.message.site.QuerySysSiteMessageBo;
import com.lframework.xingyun.template.inner.dto.message.site.SiteMessageDto;
import com.lframework.xingyun.template.inner.entity.SysSiteMessage;
import com.lframework.xingyun.template.inner.service.system.SysSiteMessageService;
import com.lframework.xingyun.template.inner.vo.system.message.site.QuerySysSiteMessageByUserVo;
import com.lframework.xingyun.template.inner.vo.system.message.site.QuerySysSiteMessageVo;
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
 * 站内信 Controller
 *
 * @author zmj
 */
@Api(tags = "站内信")
@Validated
@RestController
@RequestMapping("/system/message/site")
public class SysSiteMessageController extends DefaultBaseController {

  @Autowired
  private SysSiteMessageService sysSiteMessageService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission("system:site-message:manage")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysSiteMessageBo>> query(@Valid QuerySysSiteMessageVo vo) {

    PageResult<SysSiteMessage> pageResult = sysSiteMessageService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);

    List<SysSiteMessage> datas = pageResult.getDatas();
    List<QuerySysSiteMessageBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysSiteMessageBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询我的站内信
   */
  @ApiOperation("查询我的站内信")
  @GetMapping("/query/my")
  public InvokeResult<PageResult<QueryMySysSiteMessageBo>> queryMySiteMessage(
      @Valid QuerySysSiteMessageByUserVo vo) {

    vo.setUserId(SecurityUtil.getCurrentUser().getId());

    PageResult<SysSiteMessage> pageResult = sysSiteMessageService.queryByUser(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysSiteMessage> datas = pageResult.getDatas();
    List<QueryMySysSiteMessageBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryMySysSiteMessageBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询内容
   */
  @ApiOperation("根据ID查询内容")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping("/content")
  public InvokeResult<SiteMessageDto> getContent(@NotBlank(message = "id不能为空！") String id) {

    SiteMessageDto data = sysSiteMessageService.getContent(id);
    if (data == null) {
      throw new DefaultClientException("站内信不存在！");
    }

    String currentUserId = SecurityUtil.getCurrentUser().getId();
    ThreadUtil.execAsync(new DefaultRunnable(() -> {
      if (sysSiteMessageService.setReaded(id)) {
        sysSiteMessageService.noticeForWs(currentUserId);
      }
    }));

    return InvokeResultBuilder.success(data);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @HasPermission("system:site-message:manage")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetSysSiteMessageBo> get(@NotBlank(message = "id不能为空！") String id) {

    SysSiteMessage data = sysSiteMessageService.findById(id);
    if (data == null) {
      throw new DefaultClientException("站内信不存在！");
    }

    GetSysSiteMessageBo result = new GetSysSiteMessageBo(data);

    return InvokeResultBuilder.success(result);
  }
}
