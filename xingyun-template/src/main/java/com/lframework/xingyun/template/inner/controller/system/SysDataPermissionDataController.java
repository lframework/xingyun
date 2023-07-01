package com.lframework.xingyun.template.inner.controller.system;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.template.core.vo.permission.CreateSysDataPermissionDataVo;
import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.xingyun.template.core.entity.SysDataPermissionData;
import com.lframework.xingyun.template.core.enums.SysDataPermissionDataBizType;
import com.lframework.xingyun.template.inner.service.system.SysDataPermissionDataService;
import com.lframework.xingyun.template.inner.service.system.SysRoleService;
import com.lframework.starter.web.common.security.SecurityConstants;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据权限数据
 *
 * @author zmj
 */
@Api(tags = "数据权限数据")
@Validated
@RestController
@RequestMapping("/system/data/permission/data")
public class SysDataPermissionDataController extends DefaultBaseController {

  @Autowired
  private SysDataPermissionDataService sysDataPermissionDataService;

  @Autowired
  private SysRoleService sysRoleService;

  @ApiOperation("保存")
  @PostMapping
  public InvokeResult<Void> save(@Valid @RequestBody List<CreateSysDataPermissionDataVo> data) {
    if (CollectionUtil.isEmpty(data)) {
      return InvokeResultBuilder.success();
    }

    for (CreateSysDataPermissionDataVo vo : data) {
      if (EnumUtil.getByCode(SysDataPermissionDataBizType.class, vo.getBizType())
          == SysDataPermissionDataBizType.ROLE) {
        List<SysRole> roles = vo.getBizIds().stream().map(t -> sysRoleService.findById(t))
            .filter(
                Objects::nonNull)
            .filter(t -> SecurityConstants.PERMISSION_ADMIN_NAME.equals(t.getPermission()))
            .collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(roles)) {
          throw new DefaultClientException(
              "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME
                  + "】为内置权限，不允许设置数据权限！");
        }
      }
    }

    List<SysDataPermissionData> records = data.stream()
        .flatMap(t -> t.getBizIds().stream()
            .map(bizId -> {
              SysDataPermissionData record = sysDataPermissionDataService.getByBizId(bizId,
                  t.getBizType(),
                  t.getPermissionType());
              if (record == null) {
                record = new SysDataPermissionData();
                record.setId(IdUtil.getId());
                record.setBizId(bizId);
                record.setBizType(
                    EnumUtil.getByCode(SysDataPermissionDataBizType.class, t.getBizType()));
                record.setPermissionType(t.getPermissionType());
              }
              record.setPermission(t.getPermission());
              return record;
            }))
        .collect(Collectors.toList());

    sysDataPermissionDataService.saveOrUpdateAllColumnBatch(records);

    return InvokeResultBuilder.success();
  }
}
