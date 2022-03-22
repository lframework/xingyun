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
import com.lframework.xingyun.api.bo.stock.take.pre.GetPreTakeStockSheetBo;
import com.lframework.xingyun.api.bo.stock.take.pre.PreTakeStockProductBo;
import com.lframework.xingyun.api.bo.stock.take.pre.QueryPreTakeStockSheetBo;
import com.lframework.xingyun.api.bo.stock.take.pre.QueryPreTakeStockSheetProductBo;
import com.lframework.xingyun.api.model.stock.take.pre.PreTakeStockSheetExportModel;
import com.lframework.xingyun.basedata.dto.product.info.PreTakeStockProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryPreTakeStockProductVo;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.service.stock.take.IPreTakeStockSheetService;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.vo.stock.take.pre.CreatePreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import com.lframework.xingyun.sc.vo.stock.take.pre.UpdatePreTakeStockSheetVo;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预先盘点单 Controller
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/stock/take/pre")
public class PreTakeStockSheetController extends DefaultBaseController {

  @Autowired
  private IPreTakeStockSheetService preTakeStockSheetService;

  @Autowired
  private IProductService productService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  /**
   * 查询列表
   */
  @PreAuthorize("@permission.valid('stock:take:pre:query')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryPreTakeStockSheetVo vo) {

    PageResult<PreTakeStockSheetDto> pageResult = preTakeStockSheetService
        .query(getPageIndex(vo), getPageSize(vo), vo);

    List<PreTakeStockSheetDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryPreTakeStockSheetBo> results = datas.stream().map(QueryPreTakeStockSheetBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 导出列表
   */
  @PreAuthorize("@permission.valid('stock:take:pre:export')")
  @PostMapping("/export")
  public void export(@Valid QueryPreTakeStockSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil
        .multipartExportXls("预先盘点单信息", PreTakeStockSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<PreTakeStockSheetDto> pageResult = preTakeStockSheetService
            .query(pageIndex, getExportSize(), vo);
        List<PreTakeStockSheetDto> datas = pageResult.getDatas();
        List<PreTakeStockSheetExportModel> models = datas.stream()
            .map(PreTakeStockSheetExportModel::new)
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
   * 根据关键字查询商品列表
   */
  @PreAuthorize("@permission.valid('stock:take:pre:add', 'stock:take:pre:modify')")
  @GetMapping("/product/search")
  public InvokeResult searchProducts(String condition) {
    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(Collections.EMPTY_LIST);
    }
    PageResult<PreTakeStockProductDto> pageResult = productService
        .queryPreTakeStockByCondition(getPageIndex(), getPageSize(), condition);
    List<PreTakeStockProductBo> results = Collections.EMPTY_LIST;
    List<PreTakeStockProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PreTakeStockProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @PreAuthorize("@permission.valid('stock:take:pre:add', 'stock:take:pre:modify')")
  @GetMapping("/product/list")
  public InvokeResult queryProductList(@Valid QueryPreTakeStockProductVo vo) {

    PageResult<PreTakeStockProductDto> pageResult = productService
        .queryPreTakeStockList(getPageIndex(), getPageSize(), vo);
    List<PreTakeStockProductBo> results = Collections.EMPTY_LIST;
    List<PreTakeStockProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(PreTakeStockProductBo::new).collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 根据ID查询
   */
  @PreAuthorize("@permission.valid('stock:take:pre:query')")
  @GetMapping
  public InvokeResult getDetail(@NotBlank(message = "id不能为空！") String id) {

    PreTakeStockSheetFullDto data = preTakeStockSheetService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("预先盘点单不存在！");
    }

    GetPreTakeStockSheetBo result = new GetPreTakeStockSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据预先盘点单、盘点任务查询商品信息
   */
  @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
  @GetMapping("/products")
  public InvokeResult getProducts(@NotBlank(message = "ID不能为空！") String id,
      @NotBlank(message = "盘点任务ID不能为空！") String planId) {

    TakeStockPlanDto takeStockPlan = takeStockPlanService.getById(planId);
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      planId = null;
    }

    List<QueryPreTakeStockSheetProductDto> datas = preTakeStockSheetService.getProducts(id, planId);
    List<QueryPreTakeStockSheetProductBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream()
          .map(t -> new QueryPreTakeStockSheetProductBo(t, takeStockPlan.getScId()))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增
   */
  @PreAuthorize("@permission.valid('stock:take:pre:add')")
  @PostMapping
  public InvokeResult create(@Valid @RequestBody CreatePreTakeStockSheetVo vo) {

    vo.validate();

    preTakeStockSheetService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @PreAuthorize("@permission.valid('stock:take:pre:modify')")
  @PutMapping
  public InvokeResult update(@Valid @RequestBody UpdatePreTakeStockSheetVo vo) {

    vo.validate();

    preTakeStockSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  @PreAuthorize("@permission.valid('stock:take:pre:delete')")
  @DeleteMapping
  public InvokeResult deleteById(@NotBlank(message = "ID不能为空！") String id) {

    preTakeStockSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  @PreAuthorize("@permission.valid('stock:take:pre:delete')")
  @DeleteMapping("/batch")
  public InvokeResult batchDelete(
      @NotEmpty(message = "请选择要执行操作的预先盘点单！") @RequestBody List<String> ids) {

    preTakeStockSheetService.batchDelete(ids);

    return InvokeResultBuilder.success();
  }
}
