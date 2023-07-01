package com.lframework.xingyun.template.core.dto;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户Dto
 *
 * @author zmj
 */
@Data
public class UserDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "UserDto";

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 姓名
   */
  private String name;
}
