package com.lframework.xingyun.sc.controller.retail;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.sc.bo.retail.returned.GetRetailReturnBo;
import com.lframework.xingyun.sc.bo.retail.returned.PrintRetailReturnBo;
import com.lframework.xingyun.sc.bo.retail.returned.QueryRetailReturnBo;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.excel.retail.returned.RetailReturnExportTaskWorker;
import com.lframework.xingyun.sc.service.retail.RetailReturnService;
import com.lframework.xingyun.sc.vo.retail.returned.ApprovePassRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.ApproveRefuseRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.CreateRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.QueryRetailReturnVo;
import com.lframework.xingyun.sc.vo.retail.returned.UpdateRetailReturnVo;
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
 * 零售退单管理
 *
 * @author zmj
 */
@Api(tags = "零售退单管理")
@Validated
@RestController
@RequestMapping("/retail/return")
public class RetailReturnController extends DefaultBaseController {

  @Autowired
  private RetailReturnService retailReturnService;

  /**
   * 打印
   */
  @ApiOperation("打印")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"retail:return:query"})
  @GetMapping("/print")
  public InvokeResult<PrintRetailReturnBo> print(
      @NotBlank(message = "退单ID不能为空！") String id) {

    RetailReturnFullDto data = retailReturnService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("零售退货单不存在！");
    }

    PrintRetailReturnBo result = new PrintRetailReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 退单列表
   */
  @ApiOperation("退单列表")
  @HasPermission({"retail:return:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryRetailReturnBo>> query(@Valid QueryRetailReturnVo vo) {

    PageResult<RetailReturn> pageResult = retailReturnService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<RetailReturn> datas = pageResult.getDatas();
    List<QueryRetailReturnBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryRetailReturnBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出
   */
  @ApiOperation("导出")
  @HasPermission({"retail:return:export"})
  @PostMapping("/export")
  public InvokeResult<Void> export(@Valid QueryRetailReturnVo vo) {
    ExportTaskUtil.exportTask("零售退货单信息", RetailReturnExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"retail:return:query"})
  @GetMapping
  public InvokeResult<GetRetailReturnBo> findById(@NotBlank(message = "退单ID不能为空！") String id) {

    RetailReturnFullDto data = retailReturnService.getDetail(id);

    GetRetailReturnBo result = new GetRetailReturnBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 创建
   */
  @ApiOperation("创建")
  @HasPermission({"retail:return:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateRetailReturnVo vo) {

    vo.validate();

    String id = retailReturnService.create(vo);

    return InvokeResultBuilder.success(id);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"retail:return:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateRetailReturnVo vo) {

    vo.validate();

    retailReturnService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @ApiOperation("审核通过")
  @HasPermission({"retail:return:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@RequestBody @Valid ApprovePassRetailReturnVo vo) {

    retailReturnService.approvePass(vo);

    RetailReturn r = retailReturnService.getById(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @ApiOperation("直接审核通过")
  @HasPermission({"retail:return:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@RequestBody @Valid CreateRetailReturnVo vo) {

    retailReturnService.directApprovePass(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @ApiOperation("审核拒绝")
  @HasPermission({"retail:return:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@RequestBody @Valid ApproveRefuseRetailReturnVo vo) {

    retailReturnService.approveRefuse(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   */
  @ApiOperation("删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"retail:return:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "零售退货单ID不能为空！") String id) {

    retailReturnService.deleteById(id);

    return InvokeResultBuilder.success();
  }
}
