package com.lframework.xingyun.api.controller.settle;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.settle.sheet.GetSettleSheetBo;
import com.lframework.xingyun.api.bo.settle.sheet.QuerySettleSheetBo;
import com.lframework.xingyun.api.bo.settle.sheet.SettleBizItemBo;
import com.lframework.xingyun.api.model.settle.sheet.SettleSheetExportModel;
import com.lframework.xingyun.settle.dto.sheet.SettleBizItemDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetDto;
import com.lframework.xingyun.settle.dto.sheet.SettleSheetFullDto;
import com.lframework.xingyun.settle.service.ISettleSheetService;
import com.lframework.xingyun.settle.vo.sheet.ApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.ApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApprovePassSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.BatchApproveRefuseSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.CreateSettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QuerySettleSheetVo;
import com.lframework.xingyun.settle.vo.sheet.QueryUnSettleBizItemVo;
import com.lframework.xingyun.settle.vo.sheet.UpdateSettleSheetVo;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Validated
@RestController
@RequestMapping("/settle/sheet")
public class SettleSheetController extends DefaultBaseController {

  @Autowired
  private ISettleSheetService settleSheetService;

  /**
   * 供应商结算单列表
   */
  @PreAuthorize("@permission.valid('settle:sheet:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QuerySettleSheetVo vo) {

    PageResult<SettleSheetDto> pageResult = settleSheetService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<SettleSheetDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QuerySettleSheetBo> results = datas.stream().map(QuerySettleSheetBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @PreAuthorize("@permission.valid('settle:sheet:export')")
  @PostMapping("/export")
  public void export(@Valid QuerySettleSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil
        .multipartExportXls("供应商结算单信息", SettleSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<SettleSheetDto> pageResult = settleSheetService
            .query(pageIndex, getExportSize(), vo);
        List<SettleSheetDto> datas = pageResult.getDatas();
        List<SettleSheetExportModel> models = datas.stream().map(SettleSheetExportModel::new)
            .collect(Collectors.toList());
        builder.doWrite(models);

        if (!pageResult.isHasNext()) {
          break;
        }
        pageIndex++;
      }
    } finally {
      builder.finish();
    }
  }

  /**
   * 根据ID查询
   */
  @PreAuthorize("@permission.valid('settle:sheet:query')")
  @GetMapping
  public InvokeResult getById(@NotBlank(message = "供应商结算单ID不能为空！") String id) {

    SettleSheetFullDto data = settleSheetService.getDetail(id);

    GetSettleSheetBo result = new GetSettleSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:add')")
  @PostMapping
  public InvokeResult create(@RequestBody @Valid CreateSettleSheetVo vo) {

    vo.validate();

    String id = settleSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:modify')")
  @PutMapping
  public InvokeResult update(@RequestBody @Valid UpdateSettleSheetVo vo) {

    vo.validate();

    settleSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult approvePass(@RequestBody @Valid ApprovePassSettleSheetVo vo) {

    settleSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassSettleSheetVo vo) {

    settleSheetService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:approve')")
  @PostMapping("/approve/pass/direct")
  public InvokeResult directApprovePass(@RequestBody @Valid CreateSettleSheetVo vo) {

    settleSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseSettleSheetVo vo) {

    settleSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSettleSheetVo vo) {

    settleSheetService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:delete')")
  @DeleteMapping
  public InvokeResult deleteById(@NotBlank(message = "供应商结算单ID不能为空！") String id) {

    settleSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除供应商结算单
   */
  @PreAuthorize("@permission.valid('settle:sheet:delete')")
  @DeleteMapping("/batch")
  public InvokeResult deleteByIds(
      @RequestBody @NotEmpty(message = "请选择需要删除的供应商结算单！") List<String> ids) {

    settleSheetService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询未结算的业务单据
   */
  @PreAuthorize("@permission.valid('settle:sheet:add', 'settle:sheet:modify')")
  @GetMapping("/unsettle-items")
  public InvokeResult getUnCheckItems(@Valid QueryUnSettleBizItemVo vo) {

    List<SettleBizItemDto> results = settleSheetService.getUnSettleBizItems(vo);
    List<SettleBizItemBo> datas = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(results)) {
      datas = results.stream().map(SettleBizItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(datas);
  }
}
