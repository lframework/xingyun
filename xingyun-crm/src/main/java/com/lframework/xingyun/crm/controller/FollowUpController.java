package com.lframework.xingyun.crm.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.xingyun.crm.bo.followup.QueryFollowUpBo;
import com.lframework.xingyun.crm.entity.FollowUp;
import com.lframework.xingyun.crm.service.FollowUpService;
import com.lframework.xingyun.crm.vo.followup.CreateFollowUpVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 跟进记录管理
 */
@Tag(name = "跟进记录管理")
@Validated
@RestController
@RequestMapping("/crm/followup")
public class FollowUpController extends DefaultBaseController {

  @Autowired
  private FollowUpService followUpService;

  @Operation(summary = "线索跟进记录列表")
  @Parameter(name = "clueId", description = "线索ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:clue:query"})
  @GetMapping("/clue")
  public InvokeResult<List<QueryFollowUpBo>> listByClue(
      @NotBlank(message = "线索ID不能为空！") String clueId) {
    List<FollowUp> datas = followUpService.listByClueId(clueId);
    List<QueryFollowUpBo> results = CollectionUtil.isEmpty(datas) ? Collections.emptyList()
        : datas.stream().map(QueryFollowUpBo::new).collect(Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @Operation(summary = "商机跟进记录列表")
  @Parameter(name = "opportunityId", description = "商机ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:opportunity:query"})
  @GetMapping("/opportunity")
  public InvokeResult<List<QueryFollowUpBo>> listByOpportunity(
      @NotBlank(message = "商机ID不能为空！") String opportunityId) {
    List<FollowUp> datas = followUpService.listByOpportunityId(opportunityId);
    List<QueryFollowUpBo> results = CollectionUtil.isEmpty(datas) ? Collections.emptyList()
        : datas.stream().map(QueryFollowUpBo::new).collect(Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @Operation(summary = "新增跟进记录")
  @HasPermission({"crm:clue:query", "crm:opportunity:query"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateFollowUpVo vo) {
    String id = followUpService.create(vo);
    return InvokeResultBuilder.success(id);
  }

  @Operation(summary = "删除跟进记录")
  @Parameter(name = "id", description = "跟进记录ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:clue:modify", "crm:opportunity:modify"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "跟进记录ID不能为空！") String id) {
    followUpService.deleteById(id);
    return InvokeResultBuilder.success();
  }
}
