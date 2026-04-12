package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.settle.bo.item.out.GetSettleOutItemBo;
import com.lframework.xingyun.settle.bo.item.out.QuerySettleOutItemBo;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.excel.item.out.SettleOutItemExportTaskWorker;
import com.lframework.xingyun.settle.service.SettleOutItemService;
import com.lframework.xingyun.settle.vo.item.out.CreateSettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.QuerySettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.UpdateSettleOutItemVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支出项目
 *
 * @author zmj
 */
@Tag(name = "支出项目")
@Validated
@RestController
@RequestMapping("/settle/item/out")
public class SettleOutItemController extends DefaultBaseController {

  @Autowired
  private SettleOutItemService settleOutItemService;

  /**
   * 支出项目列表
   */
  @Operation(summary = "支出项目列表")
  @HasPermission({"settle:out-item:query", "settle:out-item:add", "settle:out-item:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySettleOutItemBo>> query(@Valid QuerySettleOutItemVo vo) {

    PageResult<SettleOutItem> pageResult = settleOutItemService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettleOutItem> datas = pageResult.getDatas();
    List<QuerySettleOutItemBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySettleOutItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询支出项目
   */
  @Operation(summary = "查询支出项目")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"settle:out-item:query", "settle:out-item:add", "settle:out-item:modify"})
  @GetMapping
  public InvokeResult<GetSettleOutItemBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SettleOutItem data = settleOutItemService.findById(id);
    if (data == null) {
      throw new DefaultClientException("支出项目不存在！");
    }

    GetSettleOutItemBo result = new GetSettleOutItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 导出支出项目
   */
  @Operation(summary = "导出支出项目")
  @HasPermission({"settle:out-item:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QuerySettleOutItemVo vo) {

    ExportTaskUtil.exportTask("支出项目信息", SettleOutItemExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();

  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "根据ID删除")
  @HasPermission({"settle:out-item:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "支出项目ID不能为空！") String id) {

    settleOutItemService.deleteById(id);

    settleOutItemService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增支出项目
   */
  @Operation(summary = "新增支出项目")
  @HasPermission({"settle:out-item:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSettleOutItemVo vo) {

    settleOutItemService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改支出项目
   */
  @Operation(summary = "修改支出项目")
  @HasPermission({"settle:out-item:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSettleOutItemVo vo) {

    settleOutItemService.update(vo);

    settleOutItemService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
