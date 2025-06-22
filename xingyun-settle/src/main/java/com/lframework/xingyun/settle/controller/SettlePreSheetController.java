package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.settle.bo.pre.GetSettlePreSheetBo;
import com.lframework.xingyun.settle.bo.pre.QuerySettlePreSheetBo;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetFullDto;
import com.lframework.xingyun.settle.entity.SettlePreSheet;
import com.lframework.xingyun.settle.excel.pre.SettlePreSheetExportTaskWorker;
import com.lframework.xingyun.settle.service.SettlePreSheetService;
import com.lframework.xingyun.settle.vo.pre.ApprovePassSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.ApproveRefuseSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.CreateSettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.QuerySettlePreSheetVo;
import com.lframework.xingyun.settle.vo.pre.UpdateSettlePreSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 供应商预付款单
 *
 * @author zmj
 */
@Api(tags = "供应商预付款单")
@Validated
@RestController
@RequestMapping("/settle/presheet")
public class SettlePreSheetController extends DefaultBaseController {

  @Autowired
  private SettlePreSheetService settlePreSheetService;

  /**
   * 供应商预付款单列表
   */
  @ApiOperation("供应商预付款单列表")
  @HasPermission({"settle:pre-sheet:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySettlePreSheetBo>> query(@Valid QuerySettlePreSheetVo vo) {

    PageResult<SettlePreSheet> pageResult = settlePreSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettlePreSheet> datas = pageResult.getDatas();
    List<QuerySettlePreSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySettlePreSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"settle:pre-sheet:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QuerySettlePreSheetVo vo) {
    ExportTaskUtil.exportTask("供应商预付款单信息", SettlePreSheetExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"settle:pre-sheet:query"})
  @GetMapping
  public InvokeResult<GetSettlePreSheetBo> findById(
      @NotBlank(message = "供应商预付款单ID不能为空！") String id) {

    SettlePreSheetFullDto data = settlePreSheetService.getDetail(id);

    GetSettlePreSheetBo result = new GetSettlePreSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建供应商预付款单
   */
  @ApiOperation("创建供应商预付款单")
  @HasPermission({"settle:pre-sheet:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateSettlePreSheetVo vo) {

    vo.validate();

    String id = settlePreSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改供应商预付款单
   */
  @ApiOperation("修改供应商预付款单")
  @HasPermission({"settle:pre-sheet:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateSettlePreSheetVo vo) {

    vo.validate();

    settlePreSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过供应商预付款单
   */
  @ApiOperation("审核通过供应商预付款单")
  @HasPermission({"settle:pre-sheet:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSettlePreSheetVo vo) {

    settlePreSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过供应商预付款单
   */
  @ApiOperation("直接审核通过供应商预付款单")
  @HasPermission({"settle:pre-sheet:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSettlePreSheetVo vo) {

    settlePreSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝供应商预付款单
   */
  @ApiOperation("审核拒绝供应商预付款单")
  @HasPermission({"settle:pre-sheet:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSettlePreSheetVo vo) {

    settlePreSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除供应商预付款单
   */
  @ApiOperation("删除供应商预付款单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"settle:pre-sheet:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "供应商预付款单ID不能为空！") String id) {

    settlePreSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }
}
