package com.lframework.xingyun.basedata.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.print.GetPrintTemplateBo;
import com.lframework.xingyun.basedata.bo.print.GetPrintTemplateCompSettingBo;
import com.lframework.xingyun.basedata.bo.print.GetPrintTemplateSettingBo;
import com.lframework.xingyun.basedata.bo.print.QueryPrintTemplateBo;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import com.lframework.xingyun.basedata.entity.PrintTemplateComp;
import com.lframework.xingyun.basedata.service.print.PrintTemplateCompService;
import com.lframework.xingyun.basedata.service.print.PrintTemplateService;
import com.lframework.xingyun.basedata.vo.print.CreatePrintTemplateVo;
import com.lframework.xingyun.basedata.vo.print.QueryPrintTemplateVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateDemoDataVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateSettingVo;
import com.lframework.xingyun.basedata.vo.print.UpdatePrintTemplateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
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
 * 打印模板
 *
 * @author zmj
 */
@Api(tags = "打印模板")
@Validated
@RestController
@RequestMapping("/basedata/print/template")
public class PrintTemplateController extends DefaultBaseController {

  @Autowired
  private PrintTemplateService printTemplateService;

  @Autowired
  private PrintTemplateCompService printTemplateCompService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"base-data:print-template:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryPrintTemplateBo>> query(@Valid QueryPrintTemplateVo vo) {

    PageResult<PrintTemplate> pageResult = printTemplateService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<PrintTemplate> datas = pageResult.getDatas();
    List<QueryPrintTemplateBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryPrintTemplateBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:print-template:query"})
  @GetMapping
  public InvokeResult<GetPrintTemplateBo> get(@NotNull(message = "ID不能为空！") Integer id) {

    PrintTemplate data = printTemplateService.findById(id);
    if (data == null) {
      throw new DefaultClientException("打印模板不存在！");
    }

    GetPrintTemplateBo result = new GetPrintTemplateBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"base-data:print-template:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreatePrintTemplateVo vo) {

    printTemplateService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"base-data:print-template:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdatePrintTemplateVo vo) {

    printTemplateService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询设置
   */
  @ApiOperation("查询设置")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/setting")
  public InvokeResult<GetPrintTemplateSettingBo> getSetting(
      @NotNull(message = "ID不能为空！") Integer id) {

    PrintTemplate data = printTemplateService.findById(id);
    if (data == null) {
      throw new DefaultClientException("打印模板不存在！");
    }

    GetPrintTemplateSettingBo result = new GetPrintTemplateSettingBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 保存设置
   */
  @ApiOperation("保存设置")
  @HasPermission({"base-data:print-template:modify"})
  @PutMapping("/setting")
  public InvokeResult<Void> updateSetting(@Valid UpdatePrintTemplateSettingVo vo) {

    printTemplateService.updateSetting(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("设置示例数据")
  @HasPermission({"base-data:print-template:modify"})
  @PutMapping("/setting/demo")
  public InvokeResult<Void> updateDemoData(@Valid UpdatePrintTemplateDemoDataVo vo) {

    printTemplateService.updateDemoData(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询组件设置
   */
  @ApiOperation("查询组件设置")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:print-template:modify"})
  @GetMapping("/setting/comp")
  public InvokeResult<List<GetPrintTemplateCompSettingBo>> getCompSetting(
      @NotNull(message = "ID不能为空！") Integer id) {
    List<PrintTemplateComp> datas = new ArrayList<>();
    datas.addAll(printTemplateCompService.list(Wrappers.lambdaQuery(PrintTemplateComp.class)
        .eq(PrintTemplateComp::getTemplateId, 0)));
    datas.addAll(printTemplateCompService.list(Wrappers.lambdaQuery(PrintTemplateComp.class)
        .eq(PrintTemplateComp::getTemplateId, id)));

    List<GetPrintTemplateCompSettingBo> results = datas.stream()
        .map(GetPrintTemplateCompSettingBo::new).collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }
}
