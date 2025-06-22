package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.settle.bo.fee.GetSettleFeeSheetBo;
import com.lframework.xingyun.settle.bo.fee.QuerySettleFeeSheetBo;
import com.lframework.xingyun.settle.dto.fee.SettleFeeSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleFeeSheet;
import com.lframework.xingyun.settle.excel.fee.SettleFeeSheetExportTaskWorker;
import com.lframework.xingyun.settle.service.SettleFeeSheetService;
import com.lframework.xingyun.settle.vo.fee.ApprovePassSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.ApproveRefuseSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.CreateSettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.QuerySettleFeeSheetVo;
import com.lframework.xingyun.settle.vo.fee.UpdateSettleFeeSheetVo;
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
 * 供应商费用单
 *
 * @author zmj
 */
@Api(tags = "供应商费用单")
@Validated
@RestController
@RequestMapping("/settle/feesheet")
public class SettleFeeSheetController extends DefaultBaseController {

  @Autowired
  private SettleFeeSheetService settleFeeSheetService;

  /**
   * 供应商费用单列表
   */
  @ApiOperation("供应商费用单列表")
  @HasPermission({"settle:fee-sheet:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySettleFeeSheetBo>> query(@Valid QuerySettleFeeSheetVo vo) {

    PageResult<SettleFeeSheet> pageResult = settleFeeSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettleFeeSheet> datas = pageResult.getDatas();
    List<QuerySettleFeeSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySettleFeeSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"settle:fee-sheet:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QuerySettleFeeSheetVo vo) {

    ExportTaskUtil.exportTask("供应商费用单信息", SettleFeeSheetExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"settle:fee-sheet:query"})
  @GetMapping
  public InvokeResult<GetSettleFeeSheetBo> findById(
      @NotBlank(message = "供应商费用单ID不能为空！") String id) {

    SettleFeeSheetFullDto data = settleFeeSheetService.getDetail(id);

    GetSettleFeeSheetBo result = new GetSettleFeeSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建供应商费用单
   */
  @ApiOperation("创建供应商费用单")
  @HasPermission({"settle:fee-sheet:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateSettleFeeSheetVo vo) {

    vo.validate();

    String id = settleFeeSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改供应商费用单
   */
  @ApiOperation("修改供应商费用单")
  @HasPermission({"settle:fee-sheet:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateSettleFeeSheetVo vo) {

    vo.validate();

    settleFeeSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过供应商费用单
   */
  @ApiOperation("审核通过供应商费用单")
  @HasPermission({"settle:fee-sheet:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSettleFeeSheetVo vo) {

    settleFeeSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过供应商费用单
   */
  @ApiOperation("直接审核通过供应商费用单")
  @HasPermission({"settle:fee-sheet:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSettleFeeSheetVo vo) {

    settleFeeSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝供应商费用单
   */
  @ApiOperation("审核拒绝供应商费用单")
  @HasPermission({"settle:fee-sheet:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSettleFeeSheetVo vo) {

    settleFeeSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除供应商费用单
   */
  @ApiOperation("删除供应商费用单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"settle:fee-sheet:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "供应商费用单ID不能为空！") String id) {

    settleFeeSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }
}
