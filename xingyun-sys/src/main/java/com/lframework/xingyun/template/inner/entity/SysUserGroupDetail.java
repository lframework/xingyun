package com.lframework.xingyun.template.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_user_group_detail")
public class SysUserGroupDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 用户组ID
   */
  private String groupId;
}
