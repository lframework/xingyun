package com.lframework.xingyun.sc.controller.stock.take;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.sc.bo.stock.take.sheet.TakeStockSheetProductBo;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetProductDto;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.sc.bo.stock.take.sheet.QueryTakeStockSheetBo;
import com.lframework.xingyun.sc.bo.stock.take.sheet.TakeStockSheetFullBo;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.entity.TakeStockSheet;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.excel.stock.take.sheet.TakeStockSheetExportModel;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.CreateTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.UpdateTakeStockSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * 盘点单 Controller
 *
 * @author zmj
 */
@Api(tags = "盘点单")
@Validated
@RestController
@RequestMapping("/stock/take/sheet")
public class TakeStockSheetController extends DefaultBaseController {

  @Autowired
  private TakeStockSheetService takeStockSheetService;

  @Autowired
  private ProductService productService;

  @Autowired
  private TakeStockPlanService takeStockPlanService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"stock:take:sheet:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryTakeStockSheetBo>> query(@Valid QueryTakeStockSheetVo vo) {

    PageResult<TakeStockSheet> pageResult = takeStockSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<TakeStockSheet> datas = pageResult.getDatas();
    List<QueryTakeStockSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryTakeStockSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出列表
   */
  @ApiOperation("导出列表")
  @HasPermission({"stock:take:sheet:export"})
  @PostMapping("/export")
  public void export(@Valid QueryTakeStockSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("库存盘点单信息",
        TakeStockSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<TakeStockSheet> pageResult = takeStockSheetService.query(pageIndex,
            getExportSize(), vo);
        List<TakeStockSheet> datas = pageResult.getDatas();
        List<TakeStockSheetExportModel> models = datas.stream().map(TakeStockSheetExportModel::new)
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
  @HasPermission({"stock:take:sheet:query"})
  @GetMapping("/detail")
  public InvokeResult<TakeStockSheetFullBo> getDetail(@NotBlank(message = "id不能为空！") String id) {

    TakeStockSheetFullDto data = takeStockSheetService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("盘点单不存在！");
    }

    TakeStockSheetFullBo result = new TakeStockSheetFullBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据关键字查询商品列表
   */
  @ApiOperation("根据关键字查询商品列表")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "盘点任务ID", name = "planId", paramType = "query", required = true),
      @ApiImplicitParam(value = "关键字", name = "condition", paramType = "query", required = true)})
  @HasPermission({"stock:take:sheet:add", "stock:take:sheet:modify"})
  @GetMapping("/product/search")
  public InvokeResult<List<TakeStockSheetProductBo>> searchProducts(
      @NotBlank(message = "盘点任务ID不能为空！") String planId,
      String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(planId);
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      planId = null;
    }
    PageResult<TakeStockSheetProductDto> pageResult = takeStockSheetService.queryTakeStockByCondition(
        getPageIndex(),
        getPageSize(), planId, condition);
    List<TakeStockSheetProductBo> results = CollectionUtil.emptyList();
    List<TakeStockSheetProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      String finalPlanId = planId;
      results = datas.stream()
          .map(t -> new TakeStockSheetProductBo(t, finalPlanId, takeStockPlan.getScId()))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @ApiOperation("查询商品列表")
  @HasPermission({"stock:take:sheet:add", "stock:take:sheet:modify"})
  @GetMapping("/product/list")
  public InvokeResult<PageResult<TakeStockSheetProductBo>> queryProductList(
      @Valid QueryTakeStockSheetProductVo vo) {

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(vo.getPlanId());
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      vo.setPlanId(null);
    }

    PageResult<TakeStockSheetProductDto> pageResult = takeStockSheetService.queryTakeStockList(
        getPageIndex(),
        getPageSize(), vo);
    List<TakeStockSheetProductBo> results = null;
    List<TakeStockSheetProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream()
          .map(t -> new TakeStockSheetProductBo(t, vo.getPlanId(), takeStockPlan.getScId()))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"stock:take:sheet:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateTakeStockSheetVo vo) {

    takeStockSheetService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"stock:take:sheet:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateTakeStockSheetVo vo) {

    takeStockSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @ApiOperation("直接审核通过")
  @HasPermission({"stock:take:sheet:approve"})
  @PostMapping("/approve/direct")
  public InvokeResult<Void> directApprovePass(@Valid @RequestBody CreateTakeStockSheetVo vo) {

    takeStockSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @ApiOperation("审核通过")
  @HasPermission({"stock:take:sheet:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@Valid ApprovePassTakeStockSheetVo vo) {

    takeStockSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @ApiOperation("批量审核通过")
  @HasPermission({"stock:take:sheet:approve"})
  @PatchMapping("/approve/pass/batch")
  public InvokeResult<Void> batchApprovePass(
      @Valid @RequestBody BatchApprovePassTakeStockSheetVo vo) {

    takeStockSheetService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @ApiOperation("审核拒绝")
  @HasPermission({"stock:take:sheet:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@Valid ApproveRefuseTakeStockSheetVo vo) {

    takeStockSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @ApiOperation("批量审核拒绝")
  @HasPermission({"stock:take:sheet:approve"})
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult<Void> batchApprovePass(
      @Valid @RequestBody BatchApproveRefuseTakeStockSheetVo vo) {

    takeStockSheetService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 取消审核
   */
  @ApiOperation("取消审核")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:take:sheet:cancel-approve"})
  @PatchMapping("/approve/cancel")
  public InvokeResult<Void> cancelApprovePass(@NotBlank(message = "ID不能为空！") String id) {

    takeStockSheetService.cancelApprovePass(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:take:sheet:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "ID不能为空！") String id) {

    takeStockSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @HasPermission({"stock:take:sheet:delete"})
  @DeleteMapping("/batch")
  public InvokeResult<Void> batchDelete(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择要执行操作的库存盘点单！") @RequestBody List<String> ids) {

    takeStockSheetService.batchDelete(ids);

    return InvokeResultBuilder.success();
  }

}
