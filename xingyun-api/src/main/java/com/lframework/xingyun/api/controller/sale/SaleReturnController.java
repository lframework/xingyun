package com.lframework.xingyun.api.controller.sale;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.sale.returned.GetSaleReturnBo;
import com.lframework.xingyun.api.bo.sale.returned.PrintSaleReturnBo;
import com.lframework.xingyun.api.bo.sale.returned.QuerySaleReturnBo;
import com.lframework.xingyun.api.model.sale.returned.SaleReturnExportModel;
import com.lframework.xingyun.api.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnDto;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.service.sale.ISaleReturnService;
import com.lframework.xingyun.sc.vo.sale.returned.ApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.ApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.CreateSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.UpdateSaleReturnVo;
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
 * 销售退单管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/sale/return")
public class SaleReturnController extends DefaultBaseController {

  @Autowired
  private ISaleReturnService saleReturnService;

  /**
   * 打印
   */
  @PreAuthorize("@permission.valid('sale:return:query')")
  @GetMapping("/print")
  public InvokeResult print(@NotBlank(message = "退单ID不能为空！") String id) {

    SaleReturnFullDto data = saleReturnService.getDetail(id);

    if (data == null) {
      throw new DefaultClientException("销售退单不存在！");
    }

    PrintSaleReturnBo result = new PrintSaleReturnBo(data);

    A4ExcelPortraitPrintBo<PrintSaleReturnBo> printResult = new A4ExcelPortraitPrintBo<PrintSaleReturnBo>(
        "print/sale-return.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * 退单列表
   */
  @PreAuthorize("@permission.valid('sale:return:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QuerySaleReturnVo vo) {

    PageResult<SaleReturnDto> pageResult = saleReturnService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<SaleReturnDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QuerySaleReturnBo> results = datas.stream().map(QuerySaleReturnBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @PreAuthorize("@permission.valid('sale:return:export')")
  @PostMapping("/export")
  public void export(@Valid QuerySaleReturnVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil
        .multipartExportXls("销售退货单信息", SaleReturnExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<SaleReturnDto> pageResult = saleReturnService
            .query(pageIndex, getExportSize(), vo);
        List<SaleReturnDto> datas = pageResult.getDatas();
        List<SaleReturnExportModel> models = datas.stream().map(SaleReturnExportModel::new)
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
  @PreAuthorize("@permission.valid('sale:return:query')")
  @GetMapping
  public InvokeResult getById(@NotBlank(message = "退单ID不能为空！") String id) {

    SaleReturnFullDto data = saleReturnService.getDetail(id);

    GetSaleReturnBo result = new GetSaleReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建
   */
  @PreAuthorize("@permission.valid('sale:return:add')")
  @PostMapping
  public InvokeResult create(@RequestBody @Valid CreateSaleReturnVo vo) {

    vo.validate();

    String id = saleReturnService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @PreAuthorize("@permission.valid('sale:return:modify')")
  @PutMapping
  public InvokeResult update(@RequestBody @Valid UpdateSaleReturnVo vo) {

    vo.validate();

    saleReturnService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @PreAuthorize("@permission.valid('sale:return:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult approvePass(@RequestBody @Valid ApprovePassSaleReturnVo vo) {

    saleReturnService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @PreAuthorize("@permission.valid('sale:return:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassSaleReturnVo vo) {

    saleReturnService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @PreAuthorize("@permission.valid('sale:return:approve')")
  @PostMapping("/approve/pass/direct")
  public InvokeResult directApprovePass(@RequestBody @Valid CreateSaleReturnVo vo) {

    saleReturnService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @PreAuthorize("@permission.valid('sale:return:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseSaleReturnVo vo) {

    saleReturnService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @PreAuthorize("@permission.valid('sale:return:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseSaleReturnVo vo) {

    saleReturnService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @PreAuthorize("@permission.valid('sale:return:delete')")
  @DeleteMapping
  public InvokeResult deleteById(@NotBlank(message = "销售退货单ID不能为空！") String id) {

    saleReturnService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @PreAuthorize("@permission.valid('sale:return:delete')")
  @DeleteMapping("/batch")
  public InvokeResult deleteByIds(
      @RequestBody @NotEmpty(message = "请选择需要删除的销售退货单！") List<String> ids) {

    saleReturnService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }
}
