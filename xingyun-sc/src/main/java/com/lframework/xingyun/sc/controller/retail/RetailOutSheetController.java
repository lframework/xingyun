package com.lframework.xingyun.sc.controller.retail;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.bo.print.A4ExcelPortraitPrintBo;
import com.lframework.xingyun.sc.bo.purchase.receive.GetPaymentDateBo;
import com.lframework.xingyun.sc.bo.retail.RetailProductBo;
import com.lframework.xingyun.sc.bo.retail.out.GetRetailOutSheetBo;
import com.lframework.xingyun.sc.bo.retail.out.PrintRetailOutSheetBo;
import com.lframework.xingyun.sc.bo.retail.out.QueryRetailOutSheetBo;
import com.lframework.xingyun.sc.bo.retail.out.QueryRetailOutSheetWithReturnBo;
import com.lframework.xingyun.sc.bo.retail.out.RetailOutSheetWithReturnBo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.retail.RetailProductDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.excel.retail.out.RetailOutSheetExportModel;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import com.lframework.xingyun.sc.vo.retail.out.ApprovePassRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.ApproveRefuseRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.CreateRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.retail.out.QueryRetailProductVo;
import com.lframework.xingyun.sc.vo.retail.out.UpdateRetailOutSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * 零售出库单管理
 *
 * @author zmj
 */
@Api(tags = "零售出库单管理")
@Validated
@RestController
@RequestMapping("/retail/out/sheet")
public class RetailOutSheetController extends DefaultBaseController {

  @Autowired
  private RetailOutSheetService retailOutSheetService;

  /**
   * 打印
   */
  @ApiOperation("打印")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"retail:out:query"})
  @GetMapping("/print")
  public InvokeResult<A4ExcelPortraitPrintBo<PrintRetailOutSheetBo>> print(
      @NotBlank(message = "订单ID不能为空！") String id) {

    RetailOutSheetFullDto data = retailOutSheetService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("零售出库单不存在！");
    }

    PrintRetailOutSheetBo result = new PrintRetailOutSheetBo(data);
    A4ExcelPortraitPrintBo<PrintRetailOutSheetBo> printResult = new A4ExcelPortraitPrintBo<>(
        "print/retail-out-sheet.ftl", result);

    return InvokeResultBuilder.success(printResult);
  }

  /**
   * 订单列表
   */
  @ApiOperation("订单列表")
  @HasPermission({"retail:out:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryRetailOutSheetBo>> query(@Valid QueryRetailOutSheetVo vo) {

    PageResult<RetailOutSheet> pageResult = retailOutSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<RetailOutSheet> datas = pageResult.getDatas();
    List<QueryRetailOutSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryRetailOutSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"retail:out:export"})
  @PostMapping("/export")
  public void export(@Valid QueryRetailOutSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("零售出库单信息",
        RetailOutSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<RetailOutSheet> pageResult = retailOutSheetService.query(pageIndex,
            getExportSize(), vo);
        List<RetailOutSheet> datas = pageResult.getDatas();
        List<RetailOutSheetExportModel> models = datas.stream().map(RetailOutSheetExportModel::new)
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
  @HasPermission({"retail:out:query"})
  @GetMapping
  public InvokeResult<GetRetailOutSheetBo> findById(
      @NotBlank(message = "订单ID不能为空！") String id) {

    RetailOutSheetFullDto data = retailOutSheetService.getDetail(id);

    GetRetailOutSheetBo result = new GetRetailOutSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据会员ID查询默认付款日期
   */
  @ApiOperation("根据会员ID查询默认付款日期")
  @ApiImplicitParam(value = "会员ID", name = "memberId", paramType = "query")
  @HasPermission({"retail:out:add", "retail:out:modify"})
  @GetMapping("/paymentdate")
  public InvokeResult<GetPaymentDateBo> getPaymentDate(String memberId) {

    GetPaymentDateDto data = retailOutSheetService.getPaymentDate(memberId);

    GetPaymentDateBo result = new GetPaymentDateBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询（零售退货业务）
   */
  @ApiOperation("根据ID查询（零售退货业务）")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"retail:return:add", "retail:return:modify"})
  @GetMapping("/return")
  public InvokeResult<RetailOutSheetWithReturnBo> getWithReturn(
      @NotBlank(message = "出库单ID不能为空！") String id) {

    RetailOutSheetWithReturnDto data = retailOutSheetService.getWithReturn(id);
    RetailOutSheetWithReturnBo result = new RetailOutSheetWithReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 查询列表（零售退货业务）
   */
  @ApiOperation("查询列表（零售退货业务）")
  @HasPermission({"retail:return:add", "retail:return:modify"})
  @GetMapping("/query/return")
  public InvokeResult<PageResult<QueryRetailOutSheetWithReturnBo>> queryWithReturn(
      @Valid QueryRetailOutSheetWithReturnVo vo) {

    PageResult<RetailOutSheet> pageResult = retailOutSheetService.queryWithReturn(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<RetailOutSheet> datas = pageResult.getDatas();

    List<QueryRetailOutSheetWithReturnBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryRetailOutSheetWithReturnBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载列表（零售退货业务）
   */
  @ApiOperation("加载列表（零售退货业务）")
  @HasPermission({"retail:return:add", "retail:return:modify"})
  @PostMapping("/query/return/load")
  public InvokeResult<List<QueryRetailOutSheetWithReturnBo>> loadWithReturn(
      @RequestBody(required = false) List<String> ids) {

    List<RetailOutSheet> datas = retailOutSheetService.listByIds(ids);

    List<QueryRetailOutSheetWithReturnBo> results = datas.stream()
        .map(QueryRetailOutSheetWithReturnBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 创建
   */
  @ApiOperation("创建")
  @HasPermission({"retail:out:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateRetailOutSheetVo vo) {

    vo.validate();

    String id = retailOutSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"retail:out:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateRetailOutSheetVo vo) {

    vo.validate();

    retailOutSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @ApiOperation("审核通过")
  @HasPermission({"retail:out:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassRetailOutSheetVo vo) {

    retailOutSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @ApiOperation("直接审核通过")
  @HasPermission({"retail:out:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateRetailOutSheetVo vo) {

    retailOutSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @ApiOperation("审核拒绝")
  @HasPermission({"retail:out:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseRetailOutSheetVo vo) {

    retailOutSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"retail:out:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "零售出库单ID不能为空！") String id) {

    retailOutSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据关键字查询商品
   */
  @ApiOperation("根据关键字查询可零售商品")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "仓库ID", name = "scId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @HasPermission({"retail:out:add", "retail:out:modify", "retail:return:add",
      "retail:return:modify"})
  @GetMapping("/product/search")
  public InvokeResult<List<RetailProductBo>> searchRetailProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId, String condition, Boolean isReturn) {

    if (isReturn == null) {
      isReturn = false;
    }

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    PageResult<RetailProductDto> pageResult = retailOutSheetService.queryRetailByCondition(
        getPageIndex(),
        getPageSize(), condition, isReturn);
    List<RetailProductBo> results = CollectionUtil.emptyList();
    List<RetailProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new RetailProductBo(scId, t)).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询可零售商品列表")
  @HasPermission({"retail:out:add", "retail:out:modify", "retail:return:add",
      "retail:return:modify"})
  @GetMapping("/product/list")
  public InvokeResult<PageResult<RetailProductBo>> queryRetailProductList(
      @Valid QueryRetailProductVo vo) {

    PageResult<RetailProductDto> pageResult = retailOutSheetService.queryRetailList(
        getPageIndex(vo),
        getPageSize(vo), vo);
    List<RetailProductBo> results = null;
    List<RetailProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(t -> new RetailProductBo(vo.getScId(), t))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
