package com.lframework.xingyun.crm.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.crm.bo.clue.QueryClueBo;
import com.lframework.xingyun.crm.entity.Clue;
import com.lframework.xingyun.crm.service.ClueService;
import com.lframework.xingyun.crm.vo.clue.CreateClueVo;
import com.lframework.xingyun.crm.vo.clue.QueryClueVo;
import com.lframework.xingyun.crm.vo.clue.UpdateClueVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
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
 * 销售线索管理
 */
@Tag(name = "销售线索管理")
@Validated
@RestController
@RequestMapping("/crm/clue")
public class ClueController extends DefaultBaseController {

  @Autowired
  private ClueService clueService;

  @Operation(summary = "线索列表")
  @HasPermission({"crm:clue:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryClueBo>> query(@Valid QueryClueVo vo) {
    PageResult<Clue> pageResult = clueService.query(getPageIndex(vo), getPageSize(vo), vo);
    List<Clue> datas = pageResult.getDatas();
    List<QueryClueBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryClueBo::new).collect(Collectors.toList());
    }
    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @Operation(summary = "新增线索")
  @HasPermission({"crm:clue:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateClueVo vo) {
    String id = clueService.create(vo);
    return InvokeResultBuilder.success(id);
  }

  @Operation(summary = "修改线索")
  @HasPermission({"crm:clue:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateClueVo vo) {
    clueService.update(vo);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "标记线索无效")
  @Parameter(name = "id", description = "线索ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:clue:modify"})
  @PatchMapping("/invalid")
  public InvokeResult<Void> markInvalid(@NotBlank(message = "线索ID不能为空！") String id) {
    clueService.markInvalid(id);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "线索转化为商机")
  @Parameter(name = "id", description = "线索ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:clue:convert"})
  @PostMapping("/convert")
  public InvokeResult<String> convert(@NotBlank(message = "线索ID不能为空！") String id) {
    String opportunityId = clueService.convertToOpportunity(id);
    return InvokeResultBuilder.success(opportunityId);
  }

  @Operation(summary = "删除线索")
  @Parameter(name = "id", description = "线索ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:clue:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "线索ID不能为空！") String id) {
    clueService.deleteById(id);
    return InvokeResultBuilder.success();
  }
}
