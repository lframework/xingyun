package com.lframework.xingyun.produce.controller.assemble;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.produce.bo.assemble.AssembleSheetDetailBo;
import com.lframework.xingyun.produce.bo.assemble.AssembleShortageBo;
import com.lframework.xingyun.produce.bo.assemble.GetAssembleSheetBo;
import com.lframework.xingyun.produce.bo.assemble.QueryAssembleSheetBo;
import com.lframework.xingyun.produce.entity.AssembleSheet;
import com.lframework.xingyun.produce.entity.AssembleSheetDetail;
import com.lframework.xingyun.produce.service.assemble.AssembleSheetDetailService;
import com.lframework.xingyun.produce.service.assemble.AssembleSheetService;
import com.lframework.xingyun.produce.vo.assemble.ApprovePassAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.ApproveRefuseAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.CreateAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.QueryAssembleSheetVo;
import com.lframework.xingyun.produce.vo.assemble.UpdateAssembleSheetVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 组装单
 *
 * @author zmj
 */
@Tag(name = "组装单")
@Validated
@RestController
@RequestMapping("/produce/assemble")
public class AssembleSheetController extends DefaultBaseController {

  @Autowired
  private AssembleSheetService assembleSheetService;

  @Autowired
  private AssembleSheetDetailService assembleSheetDetailService;

  /**
   * 组装单列表
   */
  @Operation(summary = "组装单列表")
  @HasPermission({"produce:assemble:query", "produce:assemble:add", "produce:assemble:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryAssembleSheetBo>> query(@Valid QueryAssembleSheetVo vo) {

    PageResult<AssembleSheet> pageResult = assembleSheetService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<AssembleSheet> datas = pageResult.getDatas();
    List<QueryAssembleSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryAssembleSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询组装单详情
   */
  @Operation(summary = "查询组装单详情")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:assemble:query", "produce:assemble:add", "produce:assemble:modify"})
  @GetMapping
  public InvokeResult<GetAssembleSheetBo> get(@NotBlank(message = "ID不能为空！") String id) {

    AssembleSheet data = assembleSheetService.findById(id);
    if (data == null) {
      throw new DefaultClientException("组装单不存在！");
    }

    GetAssembleSheetBo result = new GetAssembleSheetBo(data);

    List<AssembleSheetDetail> details = assembleSheetDetailService.listBySheetId(id);
    if (!CollectionUtil.isEmpty(details)) {
      result.setDetails(
          details.stream().map(AssembleSheetDetailBo::new).collect(Collectors.toList()));
    }

    return InvokeResultBuilder.success(result);
  }

  /**
   * 缺料计算
   */
  @Operation(summary = "缺料计算")
  @HasPermission({"produce:assemble:query", "produce:assemble:add", "produce:assemble:modify"})
  @GetMapping("/calc")
  public InvokeResult<List<AssembleShortageBo>> calcShortage(
      @NotBlank(message = "请传入BOM ID！") String bomId,
      @NotNull(message = "请传入组装数量！") BigDecimal totalNum,
      @NotBlank(message = "请选择仓库！") String scId) {

    List<AssembleShortageBo> results = assembleSheetService.calcShortage(bomId, totalNum, scId);

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增组装单
   */
  @Operation(summary = "新增组装单")
  @HasPermission({"produce:assemble:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateAssembleSheetVo vo) {

    assembleSheetService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改组装单
   */
  @Operation(summary = "修改组装单")
  @HasPermission({"produce:assemble:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateAssembleSheetVo vo) {

    assembleSheetService.update(vo);

    assembleSheetService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 审核通过
   */
  @Operation(summary = "审核通过")
  @HasPermission({"produce:assemble:approve"})
  @PatchMapping("/approve/pass")
  public InvokeResult<Void> approvePass(@Valid ApprovePassAssembleSheetVo vo) {

    assembleSheetService.approvePass(vo, getCurrentUser().getId());

    assembleSheetService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 直接审核通过
   */
  @Operation(summary = "直接审核通过")
  @HasPermission({"produce:assemble:add", "produce:assemble:approve"})
  @PostMapping("/approve/pass/direct")
  public InvokeResult<Void> directApprovePass(@Valid CreateAssembleSheetVo vo) {

    assembleSheetService.directApprovePass(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 审核拒绝
   */
  @Operation(summary = "审核拒绝")
  @HasPermission({"produce:assemble:approve"})
  @PatchMapping("/approve/refuse")
  public InvokeResult<Void> approveRefuse(@Valid ApproveRefuseAssembleSheetVo vo) {

    assembleSheetService.approveRefuse(vo, getCurrentUser().getId());

    assembleSheetService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 删除组装单
   */
  @Operation(summary = "删除组装单")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:assemble:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "ID不能为空！") String id) {

    assembleSheetService.deleteById(id);

    assembleSheetService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
