package com.lframework.xingyun.template.core.vo.permission;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.template.core.enums.SysDataPermissionDataBizType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysDataPermissionDataVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务ID
   */
  @ApiModelProperty(value = "业务ID", required = true)
  @NotEmpty(message = "业务ID不能为空！")
  private List<String> bizIds;

  /**
   * 业务类型
   */
  @ApiModelProperty(value = "业务类型", required = true)
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型格式错误！", enumClass = SysDataPermissionDataBizType.class)
  private Integer bizType;

  /**
   * 权限类型
   */
  @ApiModelProperty(value = "权限类型", required = true)
  @NotNull(message = "权限类型不能为空！")
  private Integer permissionType;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;
}
