package com.lframework.xingyun.template.inner.bo.system.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.inner.entity.SysDept;
import com.lframework.xingyun.template.inner.entity.SysPosition;
import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.xingyun.template.core.entity.SysUser;
import com.lframework.xingyun.template.inner.entity.SysUserDept;
import com.lframework.xingyun.template.inner.entity.SysUserPosition;
import com.lframework.xingyun.template.inner.entity.SysUserRole;
import com.lframework.xingyun.template.inner.service.system.SysDeptService;
import com.lframework.xingyun.template.inner.service.system.SysPositionService;
import com.lframework.xingyun.template.inner.service.system.SysRoleService;
import com.lframework.xingyun.template.inner.service.system.SysUserDeptService;
import com.lframework.xingyun.template.inner.service.system.SysUserPositionService;
import com.lframework.xingyun.template.inner.service.system.SysUserRoleService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class QuerySysUserBo extends BaseBo<SysUser> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 岗位名称
   */
  @ApiModelProperty("岗位名称")
  private String positionName;

  /**
   * 部门名称
   */
  @ApiModelProperty("部门名称")
  private String deptName;

  /**
   * 角色名称
   */
  @ApiModelProperty("角色名称")
  private String roleName;

  /**
   * 用户名
   */
  @ApiModelProperty("用户名")
  private String username;

  /**
   * 邮箱
   */
  @ApiModelProperty("邮箱")
  private String email;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  private String telephone;

  /**
   * 性别
   */
  @ApiModelProperty("性别")
  private Integer gender;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 是否锁定
   */
  @ApiModelProperty("是否锁定")
  private Boolean lockStatus;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QuerySysUserBo() {

  }

  public QuerySysUserBo(SysUser dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysUser dto) {

    SysUserPositionService sysUserPositionService = ApplicationUtil.getBean(
        SysUserPositionService.class);
    List<SysUserPosition> userPositions = sysUserPositionService.getByUserId(dto.getId());
    if (!CollectionUtil.isEmpty(userPositions)) {
      SysPositionService sysPositionService = ApplicationUtil.getBean(SysPositionService.class);
      List<String> positionNames = new ArrayList<>(userPositions.size());
      for (SysUserPosition userPosition : userPositions) {
        SysPosition position = sysPositionService.findById(userPosition.getPositionId());
        positionNames.add(position.getName());
      }

      this.positionName = StringUtil.join(StringPool.STR_SPLIT_CN, positionNames);
    }

    SysUserDeptService sysUserDeptService = ApplicationUtil.getBean(SysUserDeptService.class);
    List<SysUserDept> userDepts = sysUserDeptService.getByUserId(dto.getId());
    if (!CollectionUtil.isEmpty(userDepts)) {
      SysDeptService sysDeptService = ApplicationUtil.getBean(SysDeptService.class);
      List<String> deptNames = new ArrayList<>(userDepts.size());
      for (SysUserDept userDept : userDepts) {
        SysDept dept = sysDeptService.findById(userDept.getDeptId());
        deptNames.add(dept.getName());
      }

      this.deptName = StringUtil.join(StringPool.STR_SPLIT_CN, deptNames);
    }

    SysUserRoleService sysUserRoleService = ApplicationUtil.getBean(SysUserRoleService.class);
    List<SysUserRole> userRoles = sysUserRoleService.getByUserId(dto.getId());
    if (!CollectionUtil.isEmpty(userRoles)) {
      SysRoleService sysRoleService = ApplicationUtil.getBean(SysRoleService.class);
      List<String> roleNames = new ArrayList<>(userRoles.size());
      for (SysUserRole userRole : userRoles) {
        SysRole role = sysRoleService.findById(userRole.getRoleId());
        roleNames.add(role.getName());
      }

      this.roleName = StringUtil.join(StringPool.STR_SPLIT_CN, roleNames);
    }
  }
}
