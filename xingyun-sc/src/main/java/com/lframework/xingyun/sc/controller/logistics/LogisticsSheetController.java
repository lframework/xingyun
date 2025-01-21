package com.lframework.xingyun.sc.controller.logistics;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.sc.bo.logistics.GetLogisticsSheetBo;
import com.lframework.xingyun.sc.bo.logistics.GetLogisticsSheetDeliveryBo;
import com.lframework.xingyun.sc.bo.logistics.QueryLogisticsSheetBizOrderBo;
import com.lframework.xingyun.sc.bo.logistics.QueryLogisticsSheetBo;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetBizOrderDto;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetFullDto;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import com.lframework.xingyun.sc.excel.logistics.LogisticsSheetDeliveryImportListener;
import com.lframework.xingyun.sc.excel.logistics.LogisticsSheetDeliveryImportModel;
import com.lframework.xingyun.sc.excel.logistics.LogisticsSheetExportModel;
import com.lframework.xingyun.sc.excel.logistics.LogisticsSheetImportListener;
import com.lframework.xingyun.sc.excel.logistics.LogisticsSheetImportModel;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetService;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetDetailService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetDetailService;
import com.lframework.xingyun.sc.vo.logistics.CreateLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.DeliveryLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.LogisticsSheetCalcWeightOrVolumeVo;
import com.lframework.xingyun.sc.vo.logistics.LogisticsSheetCalcWeightOrVolumeVo.BizOrderVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetBizOrderVo;
import com.lframework.xingyun.sc.vo.logistics.QueryLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.UpdateLogisticsSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 物流单管理
 *
 * @author zmj
 */
@Api(tags = "物流单管理")
@Validated
@RestController
@RequestMapping("/logistics/sheet")
public class LogisticsSheetController extends DefaultBaseController {

  @Autowired
  private LogisticsSheetService logisticsSheetService;

  @Autowired
  private SaleOutSheetDetailService saleOutSheetDetailService;

  @Autowired
  private RetailOutSheetDetailService retailOutSheetDetailService;

  /**
   * 物流单列表
   */
  @ApiOperation("物流单列表")
  @HasPermission({"logistics:sheet:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryLogisticsSheetBo>> query(@Valid QueryLogisticsSheetVo vo) {

    PageResult<LogisticsSheet> pageResult = logisticsSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<LogisticsSheet> datas = pageResult.getDatas();
    List<QueryLogisticsSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryLogisticsSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"logistics:sheet:query"})
  @GetMapping
  public InvokeResult<GetLogisticsSheetBo> findById(
      @NotBlank(message = "物流单ID不能为空！") String id) {

    LogisticsSheetFullDto data = logisticsSheetService.getDetail(id);

    GetLogisticsSheetBo result = new GetLogisticsSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 查询业务单据
   */
  @ApiOperation("查询业务单据")
  @HasPermission({"logistics:sheet:add", "logistics:sheet:modify"})
  @GetMapping("/biz")
  public InvokeResult<PageResult<QueryLogisticsSheetBizOrderBo>> queryBizOrder(
      @Valid QueryLogisticsSheetBizOrderVo vo) {

    PageResult<LogisticsSheetBizOrderDto> pageResult = logisticsSheetService.queryBizOrder(
        getPageIndex(vo), getPageSize(vo), vo);

    List<LogisticsSheetBizOrderDto> datas = pageResult.getDatas();
    List<QueryLogisticsSheetBizOrderBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryLogisticsSheetBizOrderBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 计算重量
   */
  @ApiOperation("计算重量")
  @HasPermission({"logistics:sheet:add", "logistics:sheet:modify"})
  @PostMapping("/calc/weight")
  public InvokeResult<BigDecimal> calcWeight(
      @Valid @RequestBody LogisticsSheetCalcWeightOrVolumeVo vo) {

    BigDecimal totalWeight = BigDecimal.ZERO;

    List<String> saleOutSheetIds = new ArrayList<>();
    List<String> retailOutSheetIds = new ArrayList<>();

    for (BizOrderVo bizOrder : vo.getBizOrders()) {
      LogisticsSheetDetailBizType bizType = EnumUtil.getByCode(LogisticsSheetDetailBizType.class,
          bizOrder.getBizType());

      if (bizType == LogisticsSheetDetailBizType.SALE_OUT_SHEET) {
        saleOutSheetIds.add(bizOrder.getBizId());
      } else if (bizType == LogisticsSheetDetailBizType.RETAIL_OUT_SHEET) {
        retailOutSheetIds.add(bizOrder.getBizId());
      }
    }

    if (CollectionUtil.isNotEmpty(saleOutSheetIds)) {
      totalWeight = NumberUtil.add(totalWeight,
          saleOutSheetDetailService.getTotalWeightBySheetIds(saleOutSheetIds));
    }

    if (CollectionUtil.isNotEmpty(retailOutSheetIds)) {
      totalWeight = NumberUtil.add(totalWeight,
          retailOutSheetDetailService.getTotalWeightBySheetIds(retailOutSheetIds));
    }

    return InvokeResultBuilder.success(totalWeight);
  }

  /**
   * 计算体积
   */
  @ApiOperation("计算体积")
  @HasPermission({"logistics:sheet:add", "logistics:sheet:modify"})
  @PostMapping("/calc/volume")
  public InvokeResult<BigDecimal> calcVolume(
      @Valid @RequestBody LogisticsSheetCalcWeightOrVolumeVo vo) {

    BigDecimal totalVolume = BigDecimal.ZERO;

    List<String> saleOutSheetIds = new ArrayList<>();
    List<String> retailOutSheetIds = new ArrayList<>();

    for (BizOrderVo bizOrder : vo.getBizOrders()) {
      LogisticsSheetDetailBizType bizType = EnumUtil.getByCode(LogisticsSheetDetailBizType.class,
          bizOrder.getBizType());

      if (bizType == LogisticsSheetDetailBizType.SALE_OUT_SHEET) {
        saleOutSheetIds.add(bizOrder.getBizId());
      } else if (bizType == LogisticsSheetDetailBizType.RETAIL_OUT_SHEET) {
        retailOutSheetIds.add(bizOrder.getBizId());
      }
    }

    if (CollectionUtil.isNotEmpty(saleOutSheetIds)) {
      totalVolume = NumberUtil.add(totalVolume,
          saleOutSheetDetailService.getTotalVolumeBySheetIds(saleOutSheetIds));
    }

    if (CollectionUtil.isNotEmpty(retailOutSheetIds)) {
      totalVolume = NumberUtil.add(totalVolume,
          retailOutSheetDetailService.getTotalVolumeBySheetIds(retailOutSheetIds));
    }

    return InvokeResultBuilder.success(totalVolume);
  }

  /**
   * 创建物流单
   */
  @ApiOperation("创建物流单")
  @HasPermission({"logistics:sheet:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateLogisticsSheetVo vo) {

    String id = logisticsSheetService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改物流单
   */
  @ApiOperation("修改物流单")
  @HasPermission({"logistics:sheet:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateLogisticsSheetVo vo) {

    logisticsSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除物流单
   */
  @ApiOperation("删除物流单")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"logistics:sheet:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "物流单ID不能为空！") String id) {

    logisticsSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询物流单发货信息
   */
  @ApiOperation("查询物流单发货信息")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"logistics:sheet:delivery"})
  @GetMapping("/delivery")
  public InvokeResult<GetLogisticsSheetDeliveryBo> queryDelivery(
      @NotBlank(message = "ID不能为空！") String id) {

    LogisticsSheet sheet = logisticsSheetService.getById(id);
    if (sheet == null) {
      throw new DefaultClientException("物流单不存在！");
    }

    return InvokeResultBuilder.success(new GetLogisticsSheetDeliveryBo(sheet));
  }

  /**
   * 物流单发货
   */
  @ApiOperation("物流单发货")
  @HasPermission({"logistics:sheet:delivery"})
  @PutMapping("/delivery")
  public InvokeResult<Void> delivery(@Valid DeliveryLogisticsSheetVo vo) {

    logisticsSheetService.delivery(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"logistics:sheet:export"})
  @PostMapping("/export")
  public void export(@Valid QueryLogisticsSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("物流单信息",
        LogisticsSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<LogisticsSheet> pageResult = logisticsSheetService.query(pageIndex,
            getExportSize(), vo);
        List<LogisticsSheet> datas = pageResult.getDatas();
        List<LogisticsSheetExportModel> models = datas.stream().map(LogisticsSheetExportModel::new)
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

  @ApiOperation("下载导入模板")
  @HasPermission({"logistics:sheet:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("物流单导入模板", LogisticsSheetImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"logistics:sheet:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    LogisticsSheetImportListener listener = new LogisticsSheetImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, LogisticsSheetImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载批量发货模板")
  @HasPermission({"logistics:sheet:delivery"})
  @GetMapping("/import/template/delivery")
  public void downloadDeliveryImportTemplate() {
    ExcelUtil.exportXls("物流单批量发货模板", LogisticsSheetDeliveryImportModel.class);
  }

  @ApiOperation("批量发货")
  @HasPermission({"logistics:sheet:delivery"})
  @PostMapping("/import/delivery")
  public InvokeResult<Void> deliveryImportExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    LogisticsSheetDeliveryImportListener listener = new LogisticsSheetDeliveryImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, LogisticsSheetDeliveryImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
