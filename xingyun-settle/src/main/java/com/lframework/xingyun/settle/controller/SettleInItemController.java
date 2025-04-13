package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.utils.ExportTaskUtil;
import com.lframework.xingyun.settle.bo.item.in.GetSettleInItemBo;
import com.lframework.xingyun.settle.bo.item.in.QuerySettleInItemBo;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.excel.item.in.SettleInItemExportTaskWorker;
import com.lframework.xingyun.settle.service.SettleInItemService;
import com.lframework.xingyun.settle.vo.item.in.CreateSettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.UpdateSettleInItemVo;
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
 * 收入项目
 *
 * @author zmj
 */
@Api(tags = "收入项目")
@Validated
@RestController
@RequestMapping("/settle/item/in")
public class SettleInItemController extends DefaultBaseController {

  @Autowired
  private SettleInItemService settleInItemService;

  /**
   * 收入项目列表
   */
  @ApiOperation("收入项目列表")
  @HasPermission({"settle:in-item:query", "settle:in-item:add", "settle:in-item:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySettleInItemBo>> query(@Valid QuerySettleInItemVo vo) {

    PageResult<SettleInItem> pageResult = settleInItemService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettleInItem> datas = pageResult.getDatas();
    List<QuerySettleInItemBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySettleInItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询收入项目
   */
  @ApiOperation("查询收入项目")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"settle:in-item:query", "settle:in-item:add", "settle:in-item:modify"})
  @GetMapping
  public InvokeResult<GetSettleInItemBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SettleInItem data = settleInItemService.findById(id);
    if (data == null) {
      throw new DefaultClientException("收入项目不存在！");
    }

    GetSettleInItemBo result = new GetSettleInItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 导出收入项目
   */
  @ApiOperation("导出收入项目")
  @HasPermission({"settle:in-item:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QuerySettleInItemVo vo) {

    ExportTaskUtil.exportTask("收入项目信息", SettleInItemExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 停用收入项目
   */
  @ApiOperation("停用收入项目")
  @HasPermission({"settle:in-item:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "收入项目ID不能为空！") String id) {

    settleInItemService.unable(id);

    settleInItemService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用收入项目
   */
  @ApiOperation("启用收入项目")
  @HasPermission({"settle:in-item:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "收入项目ID不能为空！") String id) {

    settleInItemService.enable(id);

    settleInItemService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增收入项目
   */
  @ApiOperation("新增收入项目")
  @HasPermission({"settle:in-item:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSettleInItemVo vo) {

    settleInItemService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改收入项目
   */
  @ApiOperation("修改收入项目")
  @HasPermission({"settle:in-item:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSettleInItemVo vo) {

    settleInItemService.update(vo);

    settleInItemService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
