package com.lframework.xingyun.template.inner.bo.system.user.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.entity.SysUserGroup;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 用户组 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysUserGroupBo extends BaseBo<SysUserGroup> {

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
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public QuerySysUserGroupBo() {

  }

  public QuerySysUserGroupBo(SysUserGroup dto) {

    super(dto);
  }
}
