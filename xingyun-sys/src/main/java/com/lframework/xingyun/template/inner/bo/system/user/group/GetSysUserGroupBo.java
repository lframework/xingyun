package com.lframework.xingyun.template.inner.bo.system.user.group;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.template.inner.entity.SysUserGroup;
import com.lframework.xingyun.template.inner.service.system.SysUserGroupDetailService;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 用户组 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysUserGroupBo extends BaseBo<SysUserGroup> {

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
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 用户ID
   */
  @ApiModelProperty("用户ID")
  private List<String> userIds;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public GetSysUserGroupBo() {

  }

  public GetSysUserGroupBo(SysUserGroup dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysUserGroup dto) {

    SysUserGroupDetailService sysUserGroupDetailService = ApplicationUtil.getBean(
        SysUserGroupDetailService.class);
    this.userIds = sysUserGroupDetailService.getUserIdsByGroupId(dto.getId());
  }
}
