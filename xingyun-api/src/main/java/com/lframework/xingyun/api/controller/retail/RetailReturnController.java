package com.lframework.xingyun.api.controller.retail;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.retail.returned.GetRetailReturnBo;
import com.lframework.xingyun.api.bo.retail.returned.PrintRetailReturnBo;
import com.lframework.xingyun.api.bo.retail.returned.QueryRetailReturnBo;
import com.lframework.xingyun.api.model.retail.returned.RetailReturnExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnDto;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.service.retail.IRetailReturnService;
import com.lframework.xingyun.sc.vo.retail.returned.ApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.ApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.BatchApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.BatchApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.CreateRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.QueryRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.UpdateRetailReturnVo;
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
 * 零售退单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/retail/return")
public class RetailReturnController extends DefaultBaseController {

  @Autowired
  private IRetailReturnService retailReturnService;

  /**
   * 打印
   */
  @PreAuthorize("@permission.valid('retail:return:query')")
  @GetMapping("/print")
  public InvokeResult print(@NotBlank(message = "退单ID不能为空！") String id) {

    RetailReturnFullDto data = retailReturnService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("零售退货单不存在！");
    }

    PrintRetailReturnBo result = new PrintRetailReturnBo(data);

    A4ExcelPortraitPrintBo<PrintRetailReturnBo> printResult = new A4ExcelPortraitPrintBo<>(
        "print/retail-return.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * 退单列表
   */
  @PreAuthorize("@permission.valid('retail:return:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryRetailReturnVo vo) {

    PageResult<RetailReturnDto> pageResult = retailReturnService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<RetailReturnDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryRetailReturnBo> results = datas.stream().map(QueryRetailReturnBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @PreAuthorize("@permission.valid('retail:return:export')")
  @PostMapping("/export")
  public void export(@Valid QueryRetailReturnVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil
        .multipartExportXls("零售退货单信息", RetailReturnExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<RetailReturnDto> pageResult = retailReturnService
            .query(pageIndex, getExportSize(), vo);
        List<RetailReturnDto> datas = pageResult.getDatas();
        List<RetailReturnExportModel> models = datas.stream().map(RetailReturnExportModel::new)
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
  @PreAuthorize("@permission.valid('retail:return:query')")
  @GetMapping
  public InvokeResult getById(@NotBlank(message = "退单ID不能为空！") String id) {

    RetailReturnFullDto data = retailReturnService.getDetail(id);

    GetRetailReturnBo result = new GetRetailReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建
   */
  @PreAuthorize("@permission.valid('retail:return:add')")
  @PostMapping
  public InvokeResult create(@RequestBody @Valid CreateRetailReturnVo vo) {

    vo.validate();

    String id = retailReturnService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @PreAuthorize("@permission.valid('retail:return:modify')")
  @PutMapping
  public InvokeResult update(@RequestBody @Valid UpdateRetailReturnVo vo) {

    vo.validate();

    retailReturnService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @PreAuthorize("@permission.valid('retail:return:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult approvePass(@RequestBody @Valid ApprovePassRetailReturnVo vo) {

    retailReturnService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @PreAuthorize("@permission.valid('retail:return:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassRetailReturnVo vo) {

    retailReturnService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @PreAuthorize("@permission.valid('retail:return:approve')")
  @PostMapping("/approve/pass/direct")
  public InvokeResult directApprovePass(@RequestBody @Valid CreateRetailReturnVo vo) {

    retailReturnService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @PreAuthorize("@permission.valid('retail:return:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseRetailReturnVo vo) {

    retailReturnService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @PreAuthorize("@permission.valid('retail:return:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseRetailReturnVo vo) {

    retailReturnService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @PreAuthorize("@permission.valid('retail:return:delete')")
  @DeleteMapping
  public InvokeResult deleteById(@NotBlank(message = "零售退货单ID不能为空！") String id) {

    retailReturnService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @PreAuthorize("@permission.valid('retail:return:delete')")
  @DeleteMapping("/batch")
  public InvokeResult deleteByIds(
      @RequestBody @NotEmpty(message = "请选择需要删除的零售退货单！") List<String> ids) {

    retailReturnService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }
}
