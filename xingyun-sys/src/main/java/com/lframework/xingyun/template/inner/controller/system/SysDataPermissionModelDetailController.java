package com.lframework.xingyun.template.inner.controller.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.template.inner.bo.system.permission.QuerySysDataPermissionModelDetailBo;
import com.lframework.xingyun.template.inner.bo.system.permission.SysDataPermissionModelDetailBo;
import com.lframework.xingyun.template.inner.entity.SysDataPermissionData;
import com.lframework.xingyun.template.inner.entity.SysDataPermissionModelDetail;
import com.lframework.xingyun.template.inner.enums.system.SysDataPermissionDataBizType;
import com.lframework.xingyun.template.inner.enums.system.SysDataPermissionModelDetailCalcType;
import com.lframework.xingyun.template.inner.enums.system.SysDataPermissionModelDetailConditionType;
import com.lframework.xingyun.template.inner.enums.system.SysDataPermissionModelDetailNodeType;
import com.lframework.xingyun.template.inner.service.system.SysDataPermissionModelDetailService;
import com.lframework.xingyun.template.inner.vo.system.permission.SysDataPermissionModelDetailVo;
import com.lframework.xingyun.template.inner.service.system.SysDataPermissionDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据权限Model明细
 *
 * @author zmj
 */
@Api(tags = "数据权限Model明细")
@Validated
@RestController
@RequestMapping("/system/data/permission/model/detail")
public class SysDataPermissionModelDetailController extends DefaultBaseController {

  @Autowired
  private SysDataPermissionModelDetailService sysDataPermissionModelDetailService;

  @Autowired
  private SysDataPermissionDataService sysDataPermissionDataService;

  @ApiOperation("根据模型ID查询")
  @ApiImplicitParam(value = "模型ID", name = "modelId", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<List<SysDataPermissionModelDetailBo>> getByModelId(
      @NotNull(message = "模型ID不能为空！") Integer modelId) {

    Wrapper<SysDataPermissionModelDetail> queryWrapper = Wrappers.lambdaQuery(
        SysDataPermissionModelDetail.class).eq(SysDataPermissionModelDetail::getModelId, modelId);
    List<SysDataPermissionModelDetail> datas = sysDataPermissionModelDetailService.list(
        queryWrapper);
    List<SysDataPermissionModelDetailBo> results = datas.stream()
        .map(SysDataPermissionModelDetailBo::new).collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("预览SQL")
  @PostMapping("/preview")
  public InvokeResult<String> preview(
      @Valid @RequestBody List<SysDataPermissionModelDetailVo> models) {

    String sql = sysDataPermissionModelDetailService.toSql(models);

    return InvokeResultBuilder.success(sql);
  }

  @ApiOperation("根据业务ID查询")
  @ApiImplicitParam(value = "模型ID", name = "modelId", paramType = "query", required = true)
  @GetMapping("/query")
  public InvokeResult<List<QuerySysDataPermissionModelDetailBo>> queryByBizId(
      @NotBlank(message = "业务ID不能为空！") String bizId,
      @NotNull(message = "业务类型不能为空！") @IsEnum(message = "业务类型格式错误！", enumClass = SysDataPermissionDataBizType.class) Integer bizType,
      @NotNull(message = " 权限类型不能为空！") Integer permissionType) {

    SysDataPermissionData data = sysDataPermissionDataService.getByBizId(bizId, bizType,
        permissionType);
    if (data == null) {
      return InvokeResultBuilder.success(Collections.emptyList());
    }

    List<SysDataPermissionModelDetailVo> voList = JsonUtil.parseList(data.getPermission(),
        SysDataPermissionModelDetailVo.class);
    if (CollectionUtil.isEmpty(voList)) {
      return InvokeResultBuilder.success(Collections.emptyList());
    }

    List<QuerySysDataPermissionModelDetailBo> results = this.buildChildren(voList);

    return InvokeResultBuilder.success(results);
  }

  private List<QuerySysDataPermissionModelDetailBo> buildChildren(
      List<SysDataPermissionModelDetailVo> children) {
    if (CollectionUtil.isEmpty(children)) {
      return Collections.emptyList();
    }

    List<QuerySysDataPermissionModelDetailBo> results = new ArrayList<>(children.size());
    for (SysDataPermissionModelDetailVo vo : children) {
      SysDataPermissionModelDetail modelDetail = sysDataPermissionModelDetailService.getById(
          vo.getDetailId());
      QuerySysDataPermissionModelDetailBo result = new QuerySysDataPermissionModelDetailBo();
      result.setId(vo.getId());
      result.setDetailId(vo.getDetailId());
      result.setCalcType(vo.getCalcType());
      result.setNodeType(vo.getNodeType());
      result.setValue(vo.getValue());
      result.setValues(vo.getValues());
      result.setConditionType(vo.getConditionType());
      SysDataPermissionModelDetailNodeType nodeType = EnumUtil.getByCode(
          SysDataPermissionModelDetailNodeType.class, vo.getNodeType());
      if (nodeType == SysDataPermissionModelDetailNodeType.CALC) {
        SysDataPermissionModelDetailCalcType calcType = EnumUtil.getByCode(
            SysDataPermissionModelDetailCalcType.class, vo.getCalcType());
        result.setName(calcType.getDesc());
        result.setChildren(this.buildChildren(vo.getChildren()));
      } else {
        result.setName(modelDetail.getName());
        result.setModelId(modelDetail.getModelId());
        result.setConditionTypes(
            Arrays.stream(modelDetail.getConditionType().split(StringPool.STR_SPLIT))
                .map(t -> EnumUtil.getByCode(
                    SysDataPermissionModelDetailConditionType.class, t).getCode())
                .toArray(Integer[]::new));
        result.setInputType(modelDetail.getInputType().getCode());
        result.setEnumName(modelDetail.getEnumName());
      }

      results.add(result);
    }

    return results;
  }
}
