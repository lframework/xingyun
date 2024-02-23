package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.inner.bo.system.open.GetSysOpenDomainBo;
import com.lframework.xingyun.template.inner.bo.system.open.QuerySysOpenDomainBo;
import com.lframework.xingyun.template.inner.entity.SysOpenDomain;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.xingyun.template.inner.service.system.SysOpenDomainService;
import com.lframework.xingyun.template.inner.vo.system.open.QuerySysOpenDomainVo;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.vo.system.open.CreateSysOpenDomainVo;
import com.lframework.xingyun.template.inner.vo.system.open.UpdateSysOpenDomainSecretVo;
import com.lframework.xingyun.template.inner.vo.system.open.UpdateSysOpenDomainVo;
import com.lframework.starter.web.annotations.security.HasPermission;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开放域
 *
 * @author zmj
 */
@Api(tags = "开放域")
@Validated
@RestController
@RequestMapping("/system/open/domain")
public class SysOpenDomainController extends DefaultBaseController {

  @Autowired
  private SysOpenDomainService sysOpenDomainService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"system:open-domain:config"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysOpenDomainBo>> query(@Valid QuerySysOpenDomainVo vo) {

    PageResult<SysOpenDomain> pageResult = sysOpenDomainService.query(vo);

    List<SysOpenDomain> datas = pageResult.getDatas();
    List<QuerySysOpenDomainBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysOpenDomainBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 详情
   */
  @ApiOperation("详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"system:open-domain:config"})
  @GetMapping
  public InvokeResult<GetSysOpenDomainBo> get(@NotNull(message = "ID不能为空！") Integer id) {

    SysOpenDomain data = sysOpenDomainService.findById(id);
    if (data == null) {
      throw new DefaultClientException("开放域不存在！");
    }

    GetSysOpenDomainBo result = new GetSysOpenDomainBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"system:open-domain:config"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysOpenDomainVo vo) {

    sysOpenDomainService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"system:open-domain:config"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysOpenDomainVo vo) {

    sysOpenDomainService.update(vo);

    sysOpenDomainService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 修改Api密钥
   */
  @ApiOperation("修改Api密钥")
  @HasPermission({"system:open-domain:config"})
  @PutMapping("/secret")
  public InvokeResult<Void> updateSecret(@Valid UpdateSysOpenDomainSecretVo vo) {

    sysOpenDomainService.updateApiSecret(vo);

    sysOpenDomainService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
