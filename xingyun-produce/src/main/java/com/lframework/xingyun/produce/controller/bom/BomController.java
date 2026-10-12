package com.lframework.xingyun.produce.controller.bom;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.produce.bo.bom.BomDetailBo;
import com.lframework.xingyun.produce.bo.bom.GetBomBo;
import com.lframework.xingyun.produce.bo.bom.QueryBomBo;
import com.lframework.xingyun.produce.dto.bom.BomDetailDto;
import com.lframework.xingyun.produce.entity.Bom;
import com.lframework.xingyun.produce.service.bom.BomDetailService;
import com.lframework.xingyun.produce.service.bom.BomService;
import com.lframework.xingyun.produce.vo.bom.CreateBomVo;
import com.lframework.xingyun.produce.vo.bom.QueryBomVo;
import com.lframework.xingyun.produce.vo.bom.UpdateBomVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BOM管理
 *
 * @author zmj
 */
@Tag(name = "BOM管理")
@Validated
@RestController
@RequestMapping("/produce/bom")
public class BomController extends DefaultBaseController {

  @Autowired
  private BomService bomService;

  @Autowired
  private BomDetailService bomDetailService;

  /**
   * BOM列表
   */
  @Operation(summary = "BOM列表")
  @HasPermission({"produce:bom:query", "produce:bom:add", "produce:bom:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryBomBo>> query(@Valid QueryBomVo vo) {

    PageResult<Bom> pageResult = bomService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<Bom> datas = pageResult.getDatas();
    List<QueryBomBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryBomBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询BOM
   */
  @Operation(summary = "查询BOM")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:bom:query", "produce:bom:add", "produce:bom:modify"})
  @GetMapping
  public InvokeResult<GetBomBo> get(@NotBlank(message = "ID不能为空！") String id) {

    Bom data = bomService.findById(id);
    if (data == null) {
      throw new DefaultClientException("BOM不存在！");
    }

    GetBomBo result = new GetBomBo(data);

    List<BomDetailDto> details = bomDetailService.listByBomId(id);
    if (!CollectionUtil.isEmpty(details)) {
      result.setDetails(details.stream().map(BomDetailBo::new).collect(Collectors.toList()));
    }

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增BOM
   */
  @Operation(summary = "新增BOM")
  @HasPermission({"produce:bom:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateBomVo vo) {

    bomService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改BOM
   */
  @Operation(summary = "修改BOM")
  @HasPermission({"produce:bom:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateBomVo vo) {

    bomService.update(vo);

    bomService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 审核启用
   */
  @Operation(summary = "审核启用")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:bom:approve"})
  @PostMapping("/approve")
  public InvokeResult<Void> approve(@NotBlank(message = "ID不能为空！") String id) {

    bomService.approve(id, getCurrentUser().getId());

    bomService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 停用
   */
  @Operation(summary = "停用")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:bom:disable"})
  @PostMapping("/disable")
  public InvokeResult<Void> disable(@NotBlank(message = "ID不能为空！") String id) {

    bomService.disable(id);

    bomService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除BOM
   */
  @Operation(summary = "删除BOM")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"produce:bom:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "ID不能为空！") String id) {

    bomService.deleteById(id);

    bomService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
