package com.lframework.xingyun.export.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.export.bo.ExportingExportTaskBo;
import com.lframework.xingyun.export.bo.FailExportTaskBo;
import com.lframework.xingyun.export.bo.SuccessExportTaskBo;
import com.lframework.xingyun.export.entity.ExportTask;
import com.lframework.xingyun.export.enums.ExportTaskStatus;
import com.lframework.xingyun.export.service.ExportTaskService;
import com.lframework.xingyun.export.vo.QueryFailExportTaskVo;
import com.lframework.xingyun.export.vo.QuerySuccessExportTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出任务
 *
 * @author zmj
 */
@Api(tags = "导出任务")
@Validated
@RestController
@RequestMapping("/export/task")
public class ExportTaskController extends DefaultBaseController {

  @Autowired
  private ExportTaskService exportTaskService;

  /**
   * 正在导出列表
   */
  @ApiOperation("正在导出列表")
  @GetMapping("/exporting")
  public InvokeResult<List<ExportingExportTaskBo>> queryExporting() {

    Wrapper<ExportTask> queryWrapper = Wrappers.lambdaQuery(ExportTask.class)
        .eq(ExportTask::getCreateById, SecurityUtil.getCurrentUser().getId())
        .in(ExportTask::getStatus, ExportTaskStatus.CREATED, ExportTaskStatus.EXPORTING)
        .orderByDesc(ExportTask::getCreateTime);
    List<ExportTask> datas = exportTaskService.list(queryWrapper);

    List<ExportingExportTaskBo> results = datas.stream().map(ExportingExportTaskBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("导出成功列表")
  @GetMapping("/success")
  public InvokeResult<PageResult<SuccessExportTaskBo>> querySuccess(
      @Valid QuerySuccessExportTaskVo vo) {
    Wrapper<ExportTask> queryWrapper = Wrappers.lambdaQuery(ExportTask.class)
        .eq(ExportTask::getCreateById, SecurityUtil.getCurrentUser().getId())
        .eq(ExportTask::getStatus, ExportTaskStatus.SUCCESS)
        .like(StringUtil.isNotBlank(vo.getName()), ExportTask::getName, vo.getName())
        .orderByDesc(ExportTask::getCreateTime);

    Page<ExportTask> page = new Page<>(getPageIndex(vo), getPageSize(vo));

    Page<ExportTask> pageList = exportTaskService.page(page, queryWrapper);
    PageResult<ExportTask> pageResult = PageResultUtil.convert(pageList);
    List<ExportTask> datas = pageResult.getDatas();
    List<SuccessExportTaskBo> results = datas.stream().map(SuccessExportTaskBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("导出失败列表")
  @GetMapping("/fail")
  public InvokeResult<PageResult<FailExportTaskBo>> queryFail(
      @Valid QueryFailExportTaskVo vo) {
    Wrapper<ExportTask> queryWrapper = Wrappers.lambdaQuery(ExportTask.class)
        .eq(ExportTask::getCreateById, SecurityUtil.getCurrentUser().getId())
        .eq(ExportTask::getStatus, ExportTaskStatus.FAIL)
        .like(StringUtil.isNotBlank(vo.getName()), ExportTask::getName, vo.getName())
        .orderByDesc(ExportTask::getCreateTime);

    Page<ExportTask> page = new Page<>(getPageIndex(vo), getPageSize(vo));

    Page<ExportTask> pageList = exportTaskService.page(page, queryWrapper);
    PageResult<ExportTask> pageResult = PageResultUtil.convert(pageList);
    List<ExportTask> datas = pageResult.getDatas();
    List<FailExportTaskBo> results = datas.stream().map(FailExportTaskBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
