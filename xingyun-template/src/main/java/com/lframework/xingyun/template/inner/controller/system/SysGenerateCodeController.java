package com.lframework.xingyun.template.inner.controller.system;

import cn.hutool.json.JSONArray;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.components.generator.GenerateCodeFactory;
import com.lframework.starter.web.components.generator.handler.GenerateCodeRuleHandler;
import com.lframework.starter.web.components.generator.rule.GenerateCodeRule;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.core.service.GenerateCodeService;
import com.lframework.xingyun.template.inner.bo.system.generate.GetSysGenerateCodeBo;
import com.lframework.xingyun.template.inner.bo.system.generate.QuerySysGenerateCodeBo;
import com.lframework.xingyun.template.inner.entity.SysGenerateCode;
import com.lframework.xingyun.template.inner.service.system.SysGenerateCodeService;
import com.lframework.xingyun.template.inner.vo.system.generate.CreateSysGenerateCodeVo;
import com.lframework.xingyun.template.inner.vo.system.generate.PreviewSysGenerateCodeVo;
import com.lframework.xingyun.template.inner.vo.system.generate.QuerySysGenerateCodeVo;
import com.lframework.xingyun.template.inner.vo.system.generate.SettingSysGenerateCodeVo;
import com.lframework.xingyun.template.inner.vo.system.generate.UpdateSysGenerateCodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * 编号规则 Controller
 *
 * @author zmj
 */
@Api(tags = "编号规则")
@Validated
@RestController
@RequestMapping("/system/generate/code")
public class SysGenerateCodeController extends DefaultBaseController {

  @Autowired
  private SysGenerateCodeService sysGenerateCodeService;

  @Autowired
  private GenerateCodeService generateCodeService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"system:generate-code:manage"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysGenerateCodeBo>> query(@Valid QuerySysGenerateCodeVo vo) {

    PageResult<SysGenerateCode> pageResult = sysGenerateCodeService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SysGenerateCode> datas = pageResult.getDatas();
    List<QuerySysGenerateCodeBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysGenerateCodeBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @HasPermission({"system:generate-code:manage"})
  @GetMapping
  public InvokeResult<GetSysGenerateCodeBo> get(@NotNull(message = "id不能为空！") Long id) {

    SysGenerateCode data = sysGenerateCodeService.getById(id);
    if (data == null) {
      throw new DefaultClientException("编号规则不存在！");
    }

    GetSysGenerateCodeBo result = new GetSysGenerateCodeBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"system:generate-code:manage"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysGenerateCodeVo vo) {

    sysGenerateCodeService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"system:generate-code:manage"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysGenerateCodeVo vo) {

    sysGenerateCodeService.update(vo);

    sysGenerateCodeService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @HasPermission({"system:generate-code:manage"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotNull(message = "id不能为空！") Integer id) {

    SysGenerateCode data = sysGenerateCodeService.findById(id);

    sysGenerateCodeService.deleteById(id);

    sysGenerateCodeService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 设置规则
   */
  @ApiOperation("设置规则")
  @HasPermission({"system:generate-code:manage"})
  @PatchMapping
  public InvokeResult<Void> setting(@Valid SettingSysGenerateCodeVo vo) {

    sysGenerateCodeService.setting(vo);

    sysGenerateCodeService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 预览
   */
  @ApiOperation("预览")
  @HasPermission({"system:generate-code:manage"})
  @PostMapping("/preview")
  public InvokeResult<String> preview(@Valid PreviewSysGenerateCodeVo vo) {

    JSONArray configArr = JsonUtil.parseArray(vo.getConfigStr());
    List<GenerateCodeRuleHandler> ruleHandlerList = GenerateCodeFactory.getInstance(vo.getConfigStr());

    StringBuilder builder = new StringBuilder();
    List<GenerateCodeRule> ruleList = generateCodeService.getRules(vo.getConfigStr());
    for (int i = 0; i < ruleHandlerList.size(); i++) {
      GenerateCodeRuleHandler ruleHandler = ruleHandlerList.get(i);
      builder.append(ruleHandler.generateSimple(ruleList.get(i)));
    }

    return InvokeResultBuilder.success(builder.toString());
  }
}
