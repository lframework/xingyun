package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.threads.DefaultRunnable;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.template.inner.bo.system.notice.GetSysNoticeBo;
import com.lframework.xingyun.template.inner.bo.system.notice.QueryMySysNoticeBo;
import com.lframework.xingyun.template.inner.bo.system.notice.QuerySysNoticeBo;
import com.lframework.xingyun.template.inner.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.xingyun.template.inner.dto.system.notice.SysNoticeDto;
import com.lframework.xingyun.template.inner.entity.SysNotice;
import com.lframework.xingyun.template.inner.vo.system.notice.CreateSysNoticeVo;
import com.lframework.xingyun.template.inner.vo.system.notice.QuerySysNoticeByUserVo;
import com.lframework.xingyun.template.inner.vo.system.notice.QuerySysNoticeVo;
import com.lframework.xingyun.template.inner.vo.system.notice.UpdateSysNoticeVo;
import com.lframework.xingyun.template.inner.service.system.SysNoticeService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统通知 Controller
 *
 * @author zmj
 */
@Api(tags = "系统通知")
@Validated
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends DefaultBaseController {

  @Autowired
  private SysNoticeService sysNoticeService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"system:notice:publish"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysNoticeBo>> query(@Valid QuerySysNoticeVo vo) {

    PageResult<SysNotice> pageResult = sysNoticeService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<SysNotice> datas = pageResult.getDatas();
    List<QuerySysNoticeBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysNoticeBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询我的通知
   */
  @ApiOperation("查询我的通知")
  @GetMapping("/query/my")
  public InvokeResult<PageResult<QueryMySysNoticeBo>> queryMyNotice(
      @Valid QuerySysNoticeByUserVo vo) {

    vo.setUserId(SecurityUtil.getCurrentUser().getId());

    PageResult<QuerySysNoticeByUserDto> pageResult = sysNoticeService.queryByUser(getPageIndex(vo),
        getPageSize(vo), vo);

    List<QuerySysNoticeByUserDto> datas = pageResult.getDatas();
    List<QueryMySysNoticeBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryMySysNoticeBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询内容
   */
  @ApiOperation("根据ID查询内容")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping("/content")
  public InvokeResult<SysNoticeDto> getContent(@NotBlank(message = "id不能为空！") String id) {

    SysNoticeDto data = sysNoticeService.getContent(id);
    if (data == null) {
      throw new DefaultClientException("系统通知不存在！");
    }

    String currentUserId = SecurityUtil.getCurrentUser().getId();
    ThreadUtil.execAsync(new DefaultRunnable(() -> {
      sysNoticeService.setReaded(id, currentUserId);

      sysNoticeService.noticeForWs(currentUserId);
    }));

    return InvokeResultBuilder.success(data);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetSysNoticeBo> get(@NotBlank(message = "id不能为空！") String id) {

    SysNotice data = sysNoticeService.findById(id);
    if (data == null) {
      throw new DefaultClientException("系统通知不存在！");
    }

    GetSysNoticeBo result = new GetSysNoticeBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"system:notice:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysNoticeVo vo) {

    sysNoticeService.create(vo);

    if (vo.getPublished()) {
      sysNoticeService.noticeForWs();
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"system:notice:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysNoticeVo vo) {

    sysNoticeService.update(vo);

    sysNoticeService.cleanCacheByKey(vo.getId());

    if (vo.getPublished()) {
      sysNoticeService.noticeForWs();
    }

    return InvokeResultBuilder.success();
  }
}
