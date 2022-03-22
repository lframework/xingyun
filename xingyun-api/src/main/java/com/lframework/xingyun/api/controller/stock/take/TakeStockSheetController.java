package com.lframework.xingyun.api.controller.stock.take;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.take.sheet.QueryTakeStockSheetBo;
import com.lframework.xingyun.api.bo.stock.take.sheet.TakeStockSheetFullBo;
import com.lframework.xingyun.api.bo.stock.take.sheet.TakeStockSheetProductBo;
import com.lframework.xingyun.api.model.stock.take.sheet.TakeStockSheetExportModel;
import com.lframework.xingyun.basedata.dto.product.info.TakeStockSheetProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockSheetService;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.ApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.BatchApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.CreateTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.QueryTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.sheet.UpdateTakeStockSheetVo;
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
 * 盘点单 Controller
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/stock/take/sheet")
public class TakeStockSheetController extends DefaultBaseController {

  @Autowired
  private ITakeStockSheetService takeStockSheetService;

  @Autowired
  private IProductService productService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  /**
   * 查询列表
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryTakeStockSheetVo vo) {

    PageResult<TakeStockSheetDto> pageResult = takeStockSheetService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<TakeStockSheetDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryTakeStockSheetBo> results = datas.stream().map(QueryTakeStockSheetBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 导出列表
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:export')")
  @PostMapping("/export")
  public void export(@Valid QueryTakeStockSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil
        .multipartExportXls("库存盘点单信息", TakeStockSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<TakeStockSheetDto> pageResult = takeStockSheetService
            .query(pageIndex, getExportSize(), vo);
        List<TakeStockSheetDto> datas = pageResult.getDatas();
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
  @PreAuthorize("@permission.valid('stock:take:sheet:query')")
  @GetMapping("/detail")
  public InvokeResult getDetail(@NotBlank(message = "id不能为空！") String id) {

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
  @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
  @GetMapping("/product/search")
  public InvokeResult searchProducts(@NotBlank(message = "盘点任务ID不能为空！") String planId,
      String condition) {
    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }

    TakeStockPlanDto takeStockPlan = takeStockPlanService.getById(planId);
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      planId = null;
    }
    PageResult<TakeStockSheetProductDto> pageResult = productService
        .queryTakeStockByCondition(getPageIndex(), getPageSize(), planId, condition);
    List<TakeStockSheetProductBo> results = Collections.EMPTY_LIST;
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
  @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
  @GetMapping("/product/list")
  public InvokeResult queryProductList(@Valid QueryTakeStockSheetProductVo vo) {

    TakeStockPlanDto takeStockPlan = takeStockPlanService.getById(vo.getPlanId());
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      vo.setPlanId(null);
    }

    PageResult<TakeStockSheetProductDto> pageResult = productService
        .queryTakeStockList(getPageIndex(), getPageSize(), vo);
    List<TakeStockSheetProductBo> results = Collections.EMPTY_LIST;
    List<TakeStockSheetProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream()
          .map(t -> new TakeStockSheetProductBo(t, vo.getPlanId(), takeStockPlan.getScId()))
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 新增
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:add')")
  @PostMapping
  public InvokeResult create(@Valid @RequestBody CreateTakeStockSheetVo vo) {

    takeStockSheetService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:modify')")
  @PutMapping
  public InvokeResult update(@Valid @RequestBody UpdateTakeStockSheetVo vo) {

    takeStockSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
  @PostMapping("/approve/direct")
  public InvokeResult directApprovePass(@Valid @RequestBody CreateTakeStockSheetVo vo) {

    takeStockSheetService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
  @PatchMapping("/approve/pass")
  public InvokeResult approvePass(@Valid ApprovePassTakeStockSheetVo vo) {

    takeStockSheetService.approvePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核通过
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
  @PatchMapping("/approve/pass/batch")
  public InvokeResult batchApprovePass(@Valid @RequestBody BatchApprovePassTakeStockSheetVo vo) {

    takeStockSheetService.batchApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
  @PatchMapping("/approve/refuse")
  public InvokeResult approveRefuse(@Valid ApproveRefuseTakeStockSheetVo vo) {

    takeStockSheetService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量审核拒绝
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:approve')")
  @PatchMapping("/approve/refuse/batch")
  public InvokeResult batchApprovePass(@Valid @RequestBody BatchApproveRefuseTakeStockSheetVo vo) {

    takeStockSheetService.batchApproveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 取消审核
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:cancel-approve')")
  @PatchMapping("/approve/cancel")
  public InvokeResult cancelApprovePass(@NotBlank(message = "ID不能为空！") String id) {

    takeStockSheetService.cancelApprovePass(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:delete')")
  @DeleteMapping
  public InvokeResult deleteById(@NotBlank(message = "ID不能为空！") String id) {

    takeStockSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:delete')")
  @DeleteMapping("/batch")
  public InvokeResult batchDelete(
      @NotEmpty(message = "请选择要执行操作的库存盘点单！") @RequestBody List<String> ids) {

    takeStockSheetService.batchDelete(ids);

    return InvokeResultBuilder.success();
  }

}
