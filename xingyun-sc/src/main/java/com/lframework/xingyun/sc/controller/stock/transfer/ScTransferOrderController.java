package com.lframework.xingyun.sc.controller.stock.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.sc.bo.stock.transfer.QueryScTransferOrderBo;
import com.lframework.xingyun.sc.bo.stock.transfer.QueryScTransferOrderDetailReceiveBo;
import com.lframework.xingyun.sc.bo.stock.transfer.ScTransferOrderFullBo;
import com.lframework.xingyun.sc.bo.stock.transfer.ScTransferProductBo;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferOrderFullDto;
import com.lframework.xingyun.sc.dto.stock.transfer.ScTransferProductDto;
import com.lframework.xingyun.sc.entity.ScTransferOrder;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetailReceive;
import com.lframework.xingyun.sc.excel.stock.transfer.ScTransferOrderExportTaskWorker;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderDetailReceiveService;
import com.lframework.xingyun.sc.service.stock.transfer.ScTransferOrderService;
import com.lframework.xingyun.sc.vo.stock.transfer.ApprovePassScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ApproveRefuseScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.CreateScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferOrderDetailReceiveVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.QueryScTransferProductVo;
import com.lframework.xingyun.sc.vo.stock.transfer.ReceiveScTransferOrderVo;
import com.lframework.xingyun.sc.vo.stock.transfer.UpdateScTransferOrderVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
 * 仓库调拨单 Controller
 *
 * @author zmj
 */
@Tag(name = "仓库调拨单")
@Validated
@RestController
@RequestMapping("/stock/transfer/sc")
public class ScTransferOrderController extends DefaultBaseController {

  @Autowired
  private ScTransferOrderService scTransferOrderService;

  @Autowired
  private ScTransferOrderDetailReceiveService scTransferOrderDetailReceiveService;

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @HasPermission({"stock:sc-transfer:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryScTransferOrderBo>> query(
      @Valid QueryScTransferOrderVo vo) {

    PageResult<ScTransferOrder> pageResult = scTransferOrderService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<ScTransferOrder> datas = pageResult.getDatas();
    List<QueryScTransferOrderBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryScTransferOrderBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @Operation(summary = "根据ID查询")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"stock:sc-transfer:query"})
  @GetMapping("/detail")
  public InvokeResult<ScTransferOrderFullBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    ScTransferOrderFullDto data = scTransferOrderService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("仓库调拨单不存在！");
    }

    ScTransferOrderFullBo result = new ScTransferOrderFullBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 导出
   */
  @Operation(summary = "导出")
  @HasPermission({"stock:sc-transfer:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QueryScTransferOrderVo vo) {

    ExportTaskUtil.exportTask("仓库调拨单信息", ScTransferOrderExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据关键字查询商品列表
   */
  @Operation(summary = "根据关键字查询商品列表")
  @Parameters({
      @Parameter(name = "scId", description = "仓库ID", in = ParameterIn.QUERY, required = true),
      @Parameter(name = "condition", description = "关键字", in = ParameterIn.QUERY, required = true)})
  @HasPermission({"stock:sc-transfer:add", "stock:sc-transfer:modify"})
  @GetMapping("/product/search")
  public InvokeResult<List<ScTransferProductBo>> searchProducts(
      @NotBlank(message = "仓库ID不能为空！") String scId,
      String condition) {

    if (StringUtil.isBlank(condition)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }
    PageResult<ScTransferProductDto> pageResult = scTransferOrderService.queryScTransferByCondition(
        getPageIndex(), getPageSize(), scId, condition);
    List<ScTransferProductBo> results = CollectionUtil.emptyList();
    List<ScTransferProductDto> datas = pageResult.getDatas();
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ScTransferProductBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询商品列表
   */
  @Operation(summary = "查询商品列表")
  @HasPermission({"stock:sc-transfer:add", "stock:sc-transfer:modify"})
  @GetMapping("/product/list")
  public InvokeResult<PageResult<ScTransferProductBo>> queryProductList(
      @Valid QueryScTransferProductVo vo) {

    PageResult<ScTransferProductDto> pageResult = scTransferOrderService.queryScTransferList(
        getPageIndex(vo),
        getPageSize(vo), vo);
    List<ScTransferProductBo> results = null;
    List<ScTransferProductDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(ScTransferProductBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 新增
   */
  @Operation(summary = "新增")
  @HasPermission({"stock:sc-transfer:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateScTransferOrderVo vo) {

    vo.validate();

    scTransferOrderService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @Operation(summary = "修改")
  @HasPermission({"stock:sc-transfer:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateScTransferOrderVo vo) {

    vo.validate();

    scTransferOrderService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "根据ID删除")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"stock:sc-transfer:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "id不能为空！") String id) {

    scTransferOrderService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @Operation(summary = "审核通过")
  @HasPermission({"stock:sc-transfer:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassScTransferOrderVo vo) {

    scTransferOrderService.approvePass(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @Operation(summary = "直接审核通过")
  @HasPermission({"stock:sc-transfer:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateScTransferOrderVo vo) {

    vo.validate();

    scTransferOrderService.directApprovePass(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @Operation(summary = "审核拒绝")
  @HasPermission({"stock:sc-transfer:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(
      @RequestBody @Valid ApproveRefuseScTransferOrderVo vo) {

    scTransferOrderService.approveRefuse(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 收货
   */
  @Operation(summary = "收货")
  @HasPermission({"stock:sc-transfer:receive"})
  @PatchMapping("/receive")
  public InvokeResult<Void> receive(@RequestBody @Valid ReceiveScTransferOrderVo vo) {

    scTransferOrderService.receive(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 收货记录
   */
  @Operation(summary = "收货记录")
  @HasPermission({"stock:sc-transfer:query"})
  @GetMapping("/receive/detail")
  public InvokeResult<PageResult<QueryScTransferOrderDetailReceiveBo>> receiveDetail(
      @Valid QueryScTransferOrderDetailReceiveVo vo) {

    Wrapper<ScTransferOrderDetailReceive> queryWrapper = Wrappers.lambdaQuery(
            ScTransferOrderDetailReceive.class)
        .eq(ScTransferOrderDetailReceive::getOrderId, vo.getOrderId())
        .eq(ScTransferOrderDetailReceive::getDetailId, vo.getDetailId())
        .orderByAsc(ScTransferOrderDetailReceive::getCreateTime);

    Page<ScTransferOrderDetailReceive> page = new Page<>(getPageIndex(vo), getPageSize(vo));
    Page<ScTransferOrderDetailReceive> pageInfo = scTransferOrderDetailReceiveService.page(page,
        queryWrapper);
    PageResult<ScTransferOrderDetailReceive> pageResult = PageResultUtil.convert(pageInfo);
    List<ScTransferOrderDetailReceive> datas = pageResult.getDatas();
    List<QueryScTransferOrderDetailReceiveBo> results = datas.stream()
        .map(QueryScTransferOrderDetailReceiveBo::new).collect(
            Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
