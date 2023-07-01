package com.lframework.xingyun.template.gen.controller;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.gen.builders.CustomFormBuilder;
import com.lframework.xingyun.template.gen.builders.CustomListBuilder;
import com.lframework.xingyun.template.gen.builders.CustomPageBuilder;
import com.lframework.xingyun.template.gen.builders.CustomSelectorBuilder;
import com.lframework.xingyun.template.gen.components.custom.page.CustomPageConfig;
import com.lframework.xingyun.template.gen.entity.GenCustomForm;
import com.lframework.xingyun.template.gen.mappers.GenMapper;
import com.lframework.xingyun.template.gen.service.GenCustomFormService;
import com.lframework.xingyun.template.gen.components.custom.form.CustomFormConfig;
import com.lframework.xingyun.template.gen.components.custom.form.CustomFormHandler;
import com.lframework.xingyun.template.gen.components.custom.list.CustomListConfig;
import com.lframework.xingyun.template.gen.components.custom.selector.CustomSelectorConfig;
import com.lframework.xingyun.template.gen.components.data.obj.DataObjectQueryObj;
import com.lframework.xingyun.template.gen.components.data.obj.DataObjectQueryParamObj;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供低代码相关功能所需的接口
 */
@Slf4j
@Api(tags = "低代码接口")
@Validated
@RestController
@RequestMapping("/gen/api")
public class GenController extends DefaultBaseController {

  @Autowired
  private CustomListBuilder customListBuilder;

  @Autowired
  private CustomSelectorBuilder customSelectorBuilder;

  @Autowired
  private CustomFormBuilder customFormBuilder;

  @Autowired
  private GenMapper genMapper;

  @Autowired
  private GenCustomFormService genCustomFormService;

  @Autowired
  private CustomPageBuilder customPageBuilder;

  @ApiOperation("自定义列表配置")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/custom/list/config")
  public InvokeResult<CustomListConfig> getCustomListConfig(
      @NotBlank(message = "ID不能为空！") String id) {

    CustomListConfig config = customListBuilder.buildConfig(id);

    return InvokeResultBuilder.success(config);
  }

  @ApiOperation("查询自定义列表数据（分页）")
  @PostMapping("/custom/list/query")
  public InvokeResult<PageResult<Map<String, Object>>> customListQueryPage(
      @NotBlank(message = "ID不能为空！") String id,
      @RequestBody DataObjectQueryParamObj vo) {

    DataObjectQueryObj queryObj = customListBuilder.buildQueryObj(id, vo);

    PageHelperUtil.startPage(vo);
    List<Map<String, Object>> datas = genMapper.findList(queryObj);
    PageResult<Map<String, Object>> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    for (DataObjectQueryObj.QueryField field : queryObj.getFields()) {
      for (Map<String, Object> data : datas) {
        Object oriValue = data.get(field.getColumnAlias());
        Object newValue = Convert.convert(field.getDataType().getClazz(),
            data.get(field.getColumnAlias()));
        data.put(field.getColumnAlias(), newValue == null ? oriValue : newValue);
      }
    }

    return InvokeResultBuilder.success(pageResult);
  }

  @ApiOperation("查询自定义列表数据（不分页）")
  @PostMapping("/custom/list/query/list")
  public InvokeResult<List<Map<String, Object>>> customListQueryList(
      @NotBlank(message = "ID不能为空！") String id,
      @RequestBody DataObjectQueryParamObj vo) {

    DataObjectQueryObj queryObj = customListBuilder.buildQueryObj(id, vo);

    List<Map<String, Object>> datas = genMapper.findList(queryObj);

    for (DataObjectQueryObj.QueryField field : queryObj.getFields()) {
      for (Map<String, Object> data : datas) {
        Object oriValue = data.get(field.getColumnAlias());
        Object newValue = Convert.convert(field.getDataType().getClazz(),
            data.get(field.getColumnAlias()));
        data.put(field.getColumnAlias(), newValue == null ? oriValue : newValue);
      }
    }

    return InvokeResultBuilder.success(datas);
  }

  @ApiOperation("查询自定义列表数据（树形）")
  @PostMapping("/custom/list/query/tree")
  public InvokeResult<List<Map<String, Object>>> customListQueryTree(
      @NotBlank(message = "ID不能为空！") String id,
      @RequestBody DataObjectQueryParamObj vo) {

    // 查询全量数据
    DataObjectQueryObj queryObj = customListBuilder.buildQueryObj(id, null);

    List<Map<String, Object>> datas = genMapper.findList(queryObj);

    for (DataObjectQueryObj.QueryField field : queryObj.getFields()) {
      for (Map<String, Object> data : datas) {
        Object oriValue = data.get(field.getColumnAlias());
        Object newValue = Convert.convert(field.getDataType().getClazz(),
            data.get(field.getColumnAlias()));
        data.put(field.getColumnAlias(), newValue == null ? oriValue : newValue);
      }
    }

    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(datas);
    }

    DataObjectQueryObj filterQueryObj = customListBuilder.buildQueryObj(id, vo);
    List<Map<String, Object>> filterDatas = genMapper.findList(filterQueryObj);

    CustomListConfig config = customListBuilder.buildConfig(id);
    String idColumn = config.getListConfig().getIdColumn();
    List<String> ids = filterDatas.stream().map(t -> String.valueOf(t.get(idColumn))).collect(
        Collectors.toList());
    datas.stream().filter(t -> ids.contains(String.valueOf(t.get(idColumn))))
        .forEach(t -> t.put("id@show", true));

    return InvokeResultBuilder.success(datas);
  }

  @ApiOperation("自定义选择器配置")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/custom/selector/config")
  public InvokeResult<CustomSelectorConfig> getCustomSelectorConfig(
      @NotBlank(message = "ID不能为空！") String id) {

    CustomSelectorConfig config = customSelectorBuilder.buildConfig(id);

    return InvokeResultBuilder.success(config);
  }

  @ApiOperation("自定义表单配置")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/custom/form/config")
  public InvokeResult<CustomFormConfig> getCustomFormConfig(
      @NotBlank(message = "ID不能为空！") String id) {

    CustomFormConfig config = customFormBuilder.buildConfig(id);

    return InvokeResultBuilder.success(config);
  }

  @ApiOperation("查询自定义表单的数据")
  @PostMapping("/custom/form/get")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  public InvokeResult<Object> customFormGetData(@NotBlank(message = "ID不能为空！") String id) {

    GenCustomForm form = genCustomFormService.findById(id);
    if (form == null) {
      throw new DefaultClientException("自定义表单不存在！");
    }

    if (!form.getRequireQuery()) {
      throw new DefaultClientException("自定义表单无需查询数据！");
    }

    boolean flag = false;
    Object bean = ApplicationUtil.safeGetBean(form.getQueryBean());
    if (bean instanceof CustomFormHandler) {
      flag = true;
    }

    if (!flag) {
      log.error("自定义表单ID={}, 没有找到Bean", id);
      throw new DefaultClientException("自定义表单配置错误！");
    }

    CustomFormHandler handler = (CustomFormHandler) bean;
    Object param = handler.convertGetParam(RequestUtil.getRequestBodyStr());
    if (log.isDebugEnabled()) {
      log.debug("自定义表单getOne ID={}, param={}", id, param);
    }

    Object result = handler.getOne(param);
    if (log.isDebugEnabled()) {
      log.debug("自定义表单getOne ID={}, result={}", id, result);
    }

    return InvokeResultBuilder.success(result);
  }

  @ApiOperation("操作自定义表单的数据")
  @PostMapping("/custom/form/handle")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  public InvokeResult<Void> customFormHandleData(@NotBlank(message = "ID不能为空！") String id) {

    GenCustomForm form = genCustomFormService.findById(id);
    if (form == null) {
      throw new DefaultClientException("自定义表单不存在！");
    }

    if (StringUtil.isBlank(form.getHandleBean())) {
      return InvokeResultBuilder.success();
    }

    boolean flag = false;
    Object bean = ApplicationUtil.safeGetBean(form.getHandleBean());
    if (bean instanceof CustomFormHandler) {
      flag = true;
    }

    if (!flag) {
      log.error("自定义表单ID={}, 没有找到Bean", id);
      throw new DefaultClientException("自定义表单配置错误！");
    }

    CustomFormHandler handler = (CustomFormHandler) bean;
    Object param = handler.convertHandleParam(RequestUtil.getRequestBodyStr());
    if (log.isDebugEnabled()) {
      log.debug("自定义表单handleData ID={}, param={}", id, param);
    }

    handler.handle(param);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("获取自定义页面配置")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/custom/page/config")
  public InvokeResult<CustomPageConfig> get(@NotNull(message = "ID不能为空！") Integer id) {

    CustomPageConfig config = customPageBuilder.buildConfig(id);

    return InvokeResultBuilder.success(config);
  }
}
