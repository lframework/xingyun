package com.lframework.xingyun.sc.controller.sale;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.core.bo.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.bo.sale.returned.GetSaleReturnBo;
import com.lframework.xingyun.sc.bo.sale.returned.PrintSaleReturnBo;
import com.lframework.xingyun.sc.bo.sale.returned.QuerySaleReturnBo;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.excel.sale.returned.SaleReturnExportModel;
import com.lframework.xingyun.sc.service.sale.SaleReturnService;
import com.lframework.xingyun.sc.vo.sale.returned.ApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.ApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.BatchApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.CreateSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.UpdateSaleReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
 * 销售退单管理
 *
 * @author zmj
 */
@Api(tags = "销售退单管理")
@Validated
@RestController
@RequestMapping("/sale/return")
public class SaleReturnController extends DefaultBaseController {

  @Autowired
  private SaleReturnService saleReturnService;

  /**
   * 打印
   */
  @ApiOperation("打印")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"sale:return:query"})
  @GetMapping("/print")
  public InvokeResult<A4ExcelPortraitPrintBo<PrintSaleReturnBo>> print(
      @NotBlank(message = "退单ID不能为空！") String id) {

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
  @ApiOperation("退单列表")
  @HasPermission({"sale:return:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySaleReturnBo>> query(@Valid QuerySaleReturnVo vo) {

    PageResult<SaleReturn> pageResult = saleReturnService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<SaleReturn> datas = pageResult.getDatas();
    List<QuerySaleReturnBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySaleReturnBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"sale:return:export"})
  @PostMapping("/export")
  public void export(@Valid QuerySaleReturnVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("销售退货单信息",
        SaleReturnExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<SaleReturn> pageResult = saleReturnService.query(pageIndex, getExportSize(), vo);
        List<SaleReturn> datas = pageResult.getDatas();
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
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"sale:return:query"})
  @GetMapping
  public InvokeResult<GetSaleReturnBo> findById(@NotBlank(message = "退单ID不能为空！") String id) {

    SaleReturnFullDto data = saleReturnService.getDetail(id);

    GetSaleReturnBo result = new GetSaleReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建
   */
  @ApiOperation("创建")
  @HasPermission({"sale:return:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateSaleReturnVo vo) {

    vo.validate();

    String id = saleReturnService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"sale:return:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateSaleReturnVo vo) {

    vo.validate();

    saleReturnService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @ApiOperation("审核通过")
  @HasPermission({"sale:return:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassSaleReturnVo vo) {

    saleReturnService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @ApiOperation("批量审核通过")
  @HasPermission({"sale:return:approve"})
  @PatchMapping("/approve/pass/batch")
  public InvokeResult<Void> batchApprovePass(@RequestBody @Valid BatchApprovePassSaleReturnVo vo) {

    saleReturnService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @ApiOperation("直接审核通过")
  @HasPermission({"sale:return:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateSaleReturnVo vo) {

    saleReturnService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @ApiOperation("审核拒绝")
  @HasPermission({"sale:return:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseSaleReturnVo vo) {

    saleReturnService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @ApiOperation("批量审核拒绝")
  @HasPermission({"sale:return:approve"})
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult<Void> batchApproveRefuse(
      @RequestBody @Valid BatchApproveRefuseSaleReturnVo vo) {

    saleReturnService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"sale:return:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "销售退货单ID不能为空！") String id) {

    saleReturnService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @ApiOperation("批量删除")
  @HasPermission({"sale:return:delete"})
  @DeleteMapping("/batch")
  public InvokeResult<Void> deleteByIds(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的销售退货单！") List<String> ids) {

    saleReturnService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }
}
