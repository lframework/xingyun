package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.utils.EncryptUtil;
import com.lframework.xingyun.template.inner.bo.system.tenant.GetTenantBo;
import com.lframework.xingyun.template.inner.bo.system.tenant.QueryTenantBo;
import com.lframework.xingyun.template.inner.entity.Tenant;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.inner.service.TenantService;
import com.lframework.xingyun.template.inner.vo.system.tenant.CreateTenantVo;
import com.lframework.xingyun.template.inner.vo.system.tenant.QueryTenantVo;
import com.lframework.xingyun.template.inner.vo.system.tenant.UpdateTenantVo;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.common.event.ReloadTenantEvent;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户管理
 *
 * @author zmj
 */
@Slf4j
@Api(tags = "租户管理")
@Validated
@RestController
@RequestMapping("/system/tenant")
public class TenantController extends DefaultBaseController {

  @Autowired
  private TenantService tenantService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission(value = {"system:tenant:query", "system:tenant:add",
      "system:tenant:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryTenantBo>> query(@Valid QueryTenantVo vo) {

    PageResult<Tenant> pageResult = tenantService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<Tenant> datas = pageResult.getDatas();
    List<QueryTenantBo> results = datas.stream().map(QueryTenantBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询详情
   */
  @ApiOperation("查询详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission(value = {"system:tenant:query", "system:tenant:add",
      "system:tenant:modify"})
  @GetMapping
  public InvokeResult<GetTenantBo> get(@NotNull(message = "ID不能为空！") Integer id) {

    Tenant data = tenantService.findById(id);
    if (data == null) {
      throw new DefaultClientException("租户不存在！");
    }

    GetTenantBo result = new GetTenantBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增租户
   */
  @ApiOperation("新增租户")
  @HasPermission(value = {"system:tenant:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateTenantVo vo) {

    Integer tenantId = tenantService.create(vo);

    try {
      ReloadTenantEvent event = new ReloadTenantEvent(this, tenantId, vo.getJdbcUrl(),
          vo.getJdbcUsername(), vo.getJdbcPassword());
      ApplicationUtil.publishEvent(event);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new DefaultClientException(
          "动态加载租户数据源失败，请检查配置项！注意：虽然加载数据源失败，但是租户已经新增，请勿重复新增！");
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 修改租户
   */
  @ApiOperation("修改租户")
  @HasPermission(value = {"system:tenant:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateTenantVo vo) {

    tenantService.update(vo);

    tenantService.cleanCacheByKey(vo.getId());

    if (StringUtil.isNotBlank(vo.getJdbcUrl()) || StringUtil.isNotBlank(vo.getJdbcUsername())
        || StringUtil.isNotBlank(vo.getJdbcPassword())) {
      // 这里不走缓存
      Tenant tenant = tenantService.getById(vo.getId());
      try {
        ReloadTenantEvent event = new ReloadTenantEvent(this, tenant.getId(), tenant.getJdbcUrl(),
            tenant.getJdbcUsername(), EncryptUtil.decrypt(tenant.getJdbcPassword()));
        ApplicationUtil.publishEvent(event);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        throw new DefaultClientException(
            "动态加载租户数据源失败，请检查配置项！");
      }
    }

    return InvokeResultBuilder.success();
  }
}
