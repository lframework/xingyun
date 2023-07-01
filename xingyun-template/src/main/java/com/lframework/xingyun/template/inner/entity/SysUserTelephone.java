package com.lframework.xingyun.template.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

@Data
@TableName("sys_user_telephone")
public class SysUserTelephone extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 手机号
   */
  private String telephone;

  /**
   * 用户ID
   */
  private String userId;
}
