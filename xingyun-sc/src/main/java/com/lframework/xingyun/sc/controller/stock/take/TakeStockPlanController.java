package com.lframework.xingyun.sc.controller.stock.take;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.components.threads.DefaultRunnable;
import com.lframework.starter.web.core.utils.CronUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.components.qrtz.QrtzHandler;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.sc.bo.stock.take.plan.GetTakeStockPlanBo;
import com.lframework.xingyun.sc.bo.stock.take.plan.QueryTakeStockPlanBo;
import com.lframework.xingyun.sc.bo.stock.take.plan.QueryTakeStockPlanProductBo;
import com.lframework.xingyun.sc.bo.stock.take.plan.TakeStockPlanFullBo;
import com.lframework.xingyun.sc.dto.stock.take.plan.QueryTakeStockPlanProductDto;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanFullDto;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.entity.TakeStockPlan;
import com.lframework.xingyun.sc.excel.stock.take.plan.TakeStockPlanExportTaskWorker;
import com.lframework.xingyun.sc.impl.stock.take.TakeStockPlanServiceImpl;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import com.lframework.xingyun.sc.service.stock.take.TakeStockPlanService;
import com.lframework.xingyun.sc.vo.stock.take.plan.CancelTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.CreateTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.HandleTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.QueryTakeStockPlanVo;
import com.lframework.xingyun.sc.vo.stock.take.plan.UpdateTakeStockPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * 盘点任务 Controller
 *
 * @author zmj
 */
@Api(tags = "盘点任务")
@Validated
@RestController
@RequestMapping("/stock/take/plan")
public class TakeStockPlanController extends DefaultBaseController {

  @Autowired
  private TakeStockPlanService takeStockPlanService;

  @Autowired
  private TakeStockConfigService takeStockConfigService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"stock:take:plan:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryTakeStockPlanBo>> query(@Valid QueryTakeStockPlanVo vo) {

    PageResult<TakeStockPlan> pageResult = takeStockPlanService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<TakeStockPlan> datas = pageResult.getDatas();
    List<QueryTakeStockPlanBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryTakeStockPlanBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出列表
   */
  @ApiOperation("导出列表")
  @HasPermission({"stock:take:plan:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QueryTakeStockPlanVo vo) {

    ExportTaskUtil.exportTask("盘点任务信息", TakeStockPlanExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:take:plan:query", "stock:take:sheet:add", "stock:take:sheet:modify"})
  @GetMapping
  public InvokeResult<GetTakeStockPlanBo> get(@NotBlank(message = "id不能为空！") String id) {

    TakeStockPlan data = takeStockPlanService.getById(id);
    if (data == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    GetTakeStockPlanBo result = new GetTakeStockPlanBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:take:plan:query", "stock:take:sheet:add", "stock:take:sheet:modify"})
  @GetMapping("/detail")
  public InvokeResult<TakeStockPlanFullBo> getDetail(@NotBlank(message = "id不能为空！") String id) {

    TakeStockPlanFullDto data = takeStockPlanService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("盘点任务不存在！");
    }

    TakeStockPlanFullBo result = new TakeStockPlanFullBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据盘点任务ID查询商品信息
   */
  @ApiOperation("根据盘点任务ID查询商品信息")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:take:sheet:add", "stock:take:sheet:modify"})
  @GetMapping("/products")
  public InvokeResult<List<QueryTakeStockPlanProductBo>> getProducts(
      @NotBlank(message = "id不能为空！") String id) {

    TakeStockConfig config = takeStockConfigService.get();
    if (!config.getShowProduct()) {
      // 如果不显示商品的话，则显示emptyList
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<QueryTakeStockPlanProductDto> datas = takeStockPlanService.getProducts(id);
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<QueryTakeStockPlanProductBo> results = datas.stream().map(QueryTakeStockPlanProductBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"stock:take:plan:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateTakeStockPlanVo vo) {

    vo.validate();

    String id = takeStockPlanService.create(vo);

    TakeStockConfig config = takeStockConfigService.get();

    Map<String, Object> params = new HashMap<>();
    params.put("id", id);
    params.put(StringPool.TENANT_ID_QRTZ, TenantContextHolder.getTenantId());

    ThreadUtil.execAsync(new DefaultRunnable(() -> {
      // 自动作废
      QrtzHandler.addJob(TakeStockPlanServiceImpl.AutoCancelJob.class,
          CronUtil.getDateTimeCron(LocalDateTime.now().plusHours(config.getCancelHours())), params);
    }));

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"stock:take:plan:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateTakeStockPlanVo vo) {

    takeStockPlanService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 差异生成
   */
  @ApiOperation("差异生成")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:take:plan:create-diff"})
  @PatchMapping("/diff")
  public InvokeResult<Void> createDiff(@NotBlank(message = "ID不能为空！") String id) {

    takeStockPlanService.createDiff(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 差异处理
   */
  @ApiOperation("差异处理")
  @HasPermission({"stock:take:plan:handle-diff"})
  @PatchMapping("/handle")
  public InvokeResult<Void> handleDiff(@Valid @RequestBody HandleTakeStockPlanVo vo) {

    takeStockPlanService.handleDiff(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 作废
   */
  @ApiOperation("作废")
  @HasPermission({"stock:take:plan:cancel"})
  @PatchMapping("/cancel")
  public InvokeResult<Void> cancel(@Valid CancelTakeStockPlanVo vo) {

    takeStockPlanService.cancel(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:take:plan:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "ID不能为空！") String id) {

    takeStockPlanService.deleteById(id);

    return InvokeResultBuilder.success();
  }
}
