package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.settle.bo.sheet.GetSettleSheetBo;
import com.lframework.xingyun.settle.bo.sheet.QuerySettleSheetBo;
import com.lframework.xingyun.settle.bo.sheet.SettleBizItemBo;
import com.lframework.xingyun.settle.dto.sheet.SettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.entity.SettleSheet;
import com.lframework.xingyun.settle.excel.sheet.SettleSheetExportTaskWorker;
import com.lframework.xingyun.settle.service.SettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.ApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.ApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.CreateSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QuerySettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QueryUnSettleBizItemVo;
import com.lframework.xingyun.settle.vo.sheet.UpdateSettleSheetVo;
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
 * 供应商结算单
 *
 * @author zmj
 */
@Api(tags = "供应商结算单")
@Validated
@RestController
@RequestMapping("/settle/sheet")
public class SettleSheetController extends DefaultBaseController {

  @Autowired
  private SettleSheetService settleSheetService;

  /**
   * 供应商结算单列表
   */
  @ApiOperation("供应商结算单列表")
  @HasPermission({"settle:sheet:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySettleSheetBo>> query(@Valid QuerySettleSheetVo vo) {

    PageResult<SettleSheet> pageResult = settleSheetService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<SettleSheet> datas = pageResult.getDatas();
    List<QuerySettleSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySettleSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"settle:sheet:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QuerySettleSheetVo vo) {

    ExportTaskUtil.exportTask("供应商结算单信息", SettleSheetExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"settle:sheet:query"})
  @GetMapping
  public InvokeResult<GetSettleSheetBo> findById(
      @NotBlank(message = "供应商结算单ID不能为空！") String id) {

    SettleSheetFullDto data = settleSheetService.getDetail(id);

    GetSettleSheetBo result = new GetSettleSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建供应商结算单
   */
  @ApiOperation("创建供应商结算单")
  @HasPermission({"settle:sheet:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateSettleSheetVo vo) {

    vo.validate();

    String id = settleSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改供应商结算单
   */
  @ApiOperation("修改供应商结算单")
  @HasPermission({"settle:sheet:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateSettleSheetVo vo) {

    vo.validate();

    settleSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过供应商结算单
   */
  @ApiOperation("审核通过供应商结算单")
  @HasPermission({"settle:sheet:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSettleSheetVo vo) {

    settleSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过供应商结算单
   */
  @ApiOperation("直接审核通过供应商结算单")
  @HasPermission({"settle:sheet:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSettleSheetVo vo) {

    settleSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝供应商结算单
   */
  @ApiOperation("审核拒绝供应商结算单")
  @HasPermission({"settle:sheet:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSettleSheetVo vo) {

    settleSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除供应商结算单
   */
  @ApiOperation("删除供应商结算单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"settle:sheet:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "供应商结算单ID不能为空！") String id) {

    settleSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询未结算的业务单据
   */
  @ApiOperation("查询未结算的业务单据")
  @HasPermission({"settle:sheet:add", "settle:sheet:modify"})
  @GetMapping("/unsettle-items")
  public InvokeResult<List<SettleBizItemBo>> getUnCheckItems(@Valid QueryUnSettleBizItemVo vo) {

    List<SettleBizItemDto> results = settleSheetService.getUnSettleBizItems(vo);
    List<SettleBizItemBo> datas = CollectionUtil.emptyList();
    if (!CollectionUtil.isEmpty(results)) {
      datas = results.stream().map(SettleBizItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(datas);
  }
}
