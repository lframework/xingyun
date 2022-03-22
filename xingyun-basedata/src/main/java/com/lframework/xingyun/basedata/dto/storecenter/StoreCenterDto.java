package com.lframework.xingyun.basedata.dto.storecenter;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StoreCenterDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "StoreCenterDto";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 联系人
   */
  private String contact;

  /**
   * 联系人手机号码
   */
  private String telephone;

  /**
   * 地区ID
   */
  private String cityId;

  /**
   * 地址
   */
  private String address;

  /**
   * 仓库人数
   */
  private Integer peopleNum;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;
}
