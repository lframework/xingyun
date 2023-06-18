package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.AddressEntityType;
import com.lframework.xingyun.basedata.enums.AddressType;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2023-03-26
 */
@Data
@TableName("base_data_address")
public class Address extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "Address";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 实体ID
   */
  private String entityId;

  /**
   * 实体类型
   */
  private AddressEntityType entityType;

  /**
   * 地址类型
   */
  private AddressType addressType;

  /**
   * 姓名
   */
  private String name;

  /**
   * 手机号
   */
  private String telephone;

  /**
   * 省
   */
  private String provinceId;

  /**
   * 市
   */
  private String cityId;

  /**
   * 区
   */
  private String districtId;

  /**
   * 详细地址
   */
  private String address;

  /**
   * 是否默认地址
   */
  private Boolean isDefault;


  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改人 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /**
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
