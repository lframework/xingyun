package com.lframework.xingyun.sc.controller.sale;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.sc.bo.sale.returned.GetSaleReturnBo;
import com.lframework.xingyun.sc.bo.sale.returned.PrintSaleReturnBo;
import com.lframework.xingyun.sc.bo.sale.returned.QuerySaleReturnBo;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.excel.sale.returned.SaleReturnExportTaskWorker;
import com.lframework.xingyun.sc.service.sale.SaleReturnService;
import com.lframework.xingyun.sc.vo.sale.returned.ApprovePassSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.ApproveRefuseSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.CreateSaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.QuerySaleReturnVo;
import com.lframework.xingyun.sc.vo.sale.returned.UpdateSaleReturnVo;
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
  public InvokeResult<PrintSaleReturnBo> print(
      @NotBlank(message = "退单ID不能为空！") String id) {

    SaleReturnFullDto data = saleReturnService.getDetail(id);

    if (data == null) {
      throw new DefaultClientException("销售退单不存在！");
    }

    PrintSaleReturnBo result = new PrintSaleReturnBo(data);

    return InvokeResultBuilder.success(result);
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
  public InvokeResult<Void> export(@Valid QuerySaleReturnVo vo) {

    ExportTaskUtil.exportTask("销售退货单信息", SaleReturnExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
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
}
