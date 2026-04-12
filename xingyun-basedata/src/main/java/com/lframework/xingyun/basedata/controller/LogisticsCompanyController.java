package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.logistics.company.GetLogisticsCompanyBo;
import com.lframework.xingyun.basedata.bo.logistics.company.QueryLogisticsCompanyBo;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.service.logistics.LogisticsCompanyService;
import com.lframework.xingyun.basedata.vo.logistics.company.CreateLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.UpdateLogisticsCompanyVo;
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
 * 物流公司管理
 *
 * @author zmj
 */
@Tag(name = "物流公司管理")
@Validated
@RestController
@RequestMapping("/basedata/logistics/company")
public class LogisticsCompanyController extends DefaultBaseController {

  @Autowired
  private LogisticsCompanyService logisticsCompanyService;

  /**
   * 物流公司列表
   */
  @Operation(summary = "物流公司列表")
  @HasPermission({"base-data:logistics-company:query", "base-data:logistics-company:add",
      "base-data:logistics-company:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryLogisticsCompanyBo>> query(
      @Valid QueryLogisticsCompanyVo vo) {

    PageResult<LogisticsCompany> pageResult = logisticsCompanyService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<LogisticsCompany> datas = pageResult.getDatas();
    List<QueryLogisticsCompanyBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryLogisticsCompanyBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询物流公司
   */
  @Operation(summary = "查询物流公司")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
  @HasPermission({"base-data:logistics-company:query", "base-data:logistics-company:add",
      "base-data:logistics-company:modify"})
  @GetMapping
  public InvokeResult<GetLogisticsCompanyBo> get(@NotBlank(message = "ID不能为空！") String id) {

    LogisticsCompany data = logisticsCompanyService.findById(id);
    if (data == null) {
      throw new DefaultClientException("物流公司不存在！");
    }

    GetLogisticsCompanyBo result = new GetLogisticsCompanyBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据ID删除
   */
  @Operation(summary = "根据ID删除")
  @HasPermission({"base-data:logistics-company:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "物流公司ID不能为空！") String id) {

    logisticsCompanyService.deleteById(id);

    logisticsCompanyService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增物流公司
   */
  @Operation(summary = "新增物流公司")
  @HasPermission({"base-data:logistics-company:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateLogisticsCompanyVo vo) {

    logisticsCompanyService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改物流公司
   */
  @Operation(summary = "修改物流公司")
  @HasPermission({"base-data:logistics-company:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateLogisticsCompanyVo vo) {

    logisticsCompanyService.update(vo);

    logisticsCompanyService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
