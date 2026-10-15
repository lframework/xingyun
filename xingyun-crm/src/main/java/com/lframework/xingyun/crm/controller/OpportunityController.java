package com.lframework.xingyun.crm.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.crm.bo.opportunity.QueryOpportunityBo;
import com.lframework.xingyun.crm.entity.Opportunity;
import com.lframework.xingyun.crm.service.OpportunityService;
import com.lframework.xingyun.crm.vo.opportunity.CloseOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.CreateOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.QueryOpportunityVo;
import com.lframework.xingyun.crm.vo.opportunity.UpdateOpportunityVo;
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
 * 销售商机管理
 */
@Tag(name = "销售商机管理")
@Validated
@RestController
@RequestMapping("/crm/opportunity")
public class OpportunityController extends DefaultBaseController {

  @Autowired
  private OpportunityService opportunityService;

  @Operation(summary = "商机列表")
  @HasPermission({"crm:opportunity:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryOpportunityBo>> query(@Valid QueryOpportunityVo vo) {
    PageResult<Opportunity> pageResult = opportunityService.query(getPageIndex(vo), getPageSize(vo), vo);
    List<Opportunity> datas = pageResult.getDatas();
    List<QueryOpportunityBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryOpportunityBo::new).collect(Collectors.toList());
    }
    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @Operation(summary = "新增商机")
  @HasPermission({"crm:opportunity:add"})
  @PostMapping
  public InvokeResult<String> create(@RequestBody @Valid CreateOpportunityVo vo) {
    String id = opportunityService.create(vo);
    return InvokeResultBuilder.success(id);
  }

  @Operation(summary = "修改商机")
  @HasPermission({"crm:opportunity:modify"})
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateOpportunityVo vo) {
    opportunityService.update(vo);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "关闭商机（赢单/输单）")
  @HasPermission({"crm:opportunity:close"})
  @PatchMapping("/close")
  public InvokeResult<Void> close(@RequestBody @Valid CloseOpportunityVo vo) {
    opportunityService.close(vo);
    return InvokeResultBuilder.success();
  }

  @Operation(summary = "商机转化为销售订单（返回客户ID供前端预填）")
  @Parameter(name = "id", description = "商机ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:opportunity:convert"})
  @PostMapping("/convert")
  public InvokeResult<String> convertToSaleOrder(@NotBlank(message = "商机ID不能为空！") String id) {
    String customerId = opportunityService.convertToSaleOrder(id);
    return InvokeResultBuilder.success(customerId);
  }

  @Operation(summary = "删除商机")
  @Parameter(name = "id", description = "商机ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"crm:opportunity:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "商机ID不能为空！") String id) {
    opportunityService.deleteById(id);
    return InvokeResultBuilder.success();
  }
}
