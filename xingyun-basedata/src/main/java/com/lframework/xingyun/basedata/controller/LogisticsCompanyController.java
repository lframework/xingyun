package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.logistics.company.GetLogisticsCompanyBo;
import com.lframework.xingyun.basedata.bo.logistics.company.QueryLogisticsCompanyBo;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.service.logistics.LogisticsCompanyService;
import com.lframework.xingyun.basedata.vo.logistics.company.CreateLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.QueryLogisticsCompanyVo;
import com.lframework.xingyun.basedata.vo.logistics.company.UpdateLogisticsCompanyVo;
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
 * 物流公司管理
 *
 * @author zmj
 */
@Api(tags = "物流公司管理")
@Validated
@RestController
@RequestMapping("/basedata/logistics/company")
public class LogisticsCompanyController extends DefaultBaseController {

  @Autowired
  private LogisticsCompanyService logisticsCompanyService;

  /**
   * 物流公司列表
   */
  @ApiOperation("物流公司列表")
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
  @ApiOperation("查询物流公司")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
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
   * 停用物流公司
   */
  @ApiOperation("停用物流公司")
  @HasPermission({"base-data:logistics-company:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "物流公司ID不能为空！") String id) {

    logisticsCompanyService.unable(id);

    logisticsCompanyService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用物流公司
   */
  @ApiOperation("启用物流公司")
  @HasPermission({"base-data:logistics-company:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "物流公司ID不能为空！") String id) {

    logisticsCompanyService.enable(id);

    logisticsCompanyService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增物流公司
   */
  @ApiOperation("新增物流公司")
  @HasPermission({"base-data:logistics-company:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateLogisticsCompanyVo vo) {

    logisticsCompanyService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改物流公司
   */
  @ApiOperation("修改物流公司")
  @HasPermission({"base-data:logistics-company:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateLogisticsCompanyVo vo) {

    logisticsCompanyService.update(vo);

    logisticsCompanyService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
