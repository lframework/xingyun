package com.lframework.xingyun.sc.controller.stock.adjust;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.reason.GetStockAdjustReasonBo;
import com.lframework.xingyun.sc.bo.stock.adjust.stock.reason.QueryStockAdjustReasonBo;
import com.lframework.xingyun.sc.entity.StockAdjustReason;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustReasonService;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.CreateStockAdjustReasonVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.QueryStockAdjustReasonVo;
import com.lframework.xingyun.sc.vo.stock.adjust.stock.reason.UpdateStockAdjustReasonVo;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存调整原因
 *
 * @author zmj
 */
@Api(tags = "库存调整原因")
@Validated
@RestController
@RequestMapping("/stock/adjust/reason")
public class StockAdjustReasonController extends DefaultBaseController {

  @Autowired
  private StockAdjustReasonService stockAdjustReasonService;

  /**
   * 库存调整原因列表
   */
  @ApiOperation("库存调整原因列表")
  @HasPermission({"stock:adjust:reason:query", "stock:adjust:reason:add",
      "stock:adjust:reason:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryStockAdjustReasonBo>> query(
      @Valid QueryStockAdjustReasonVo vo) {

    PageResult<StockAdjustReason> pageResult = stockAdjustReasonService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<StockAdjustReason> datas = pageResult.getDatas();
    List<QueryStockAdjustReasonBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryStockAdjustReasonBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询库存调整原因
   */
  @ApiOperation("查询库存调整原因")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:adjust:reason:query", "stock:adjust:reason:add",
      "stock:adjust:reason:modify"})
  @GetMapping
  public InvokeResult<GetStockAdjustReasonBo> get(@NotBlank(message = "ID不能为空！") String id) {

    StockAdjustReason data = stockAdjustReasonService.findById(id);
    if (data == null) {
      throw new DefaultClientException("库存调整原因不存在！");
    }

    GetStockAdjustReasonBo result = new GetStockAdjustReasonBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 停用库存调整原因
   */
  @ApiOperation("停用库存调整原因")
  @HasPermission({"stock:adjust:reason:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "库存调整原因ID不能为空！") String id) {

    stockAdjustReasonService.unable(id);

    stockAdjustReasonService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用库存调整原因
   */
  @ApiOperation("启用库存调整原因")
  @HasPermission({"stock:adjust:reason:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "库存调整原因ID不能为空！") String id) {

    stockAdjustReasonService.enable(id);

    stockAdjustReasonService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增库存调整原因
   */
  @ApiOperation("新增库存调整原因")
  @HasPermission({"stock:adjust:reason:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateStockAdjustReasonVo vo) {

    stockAdjustReasonService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改库存调整原因
   */
  @ApiOperation("修改库存调整原因")
  @HasPermission({"stock:adjust:reason:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateStockAdjustReasonVo vo) {

    stockAdjustReasonService.update(vo);

    stockAdjustReasonService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
