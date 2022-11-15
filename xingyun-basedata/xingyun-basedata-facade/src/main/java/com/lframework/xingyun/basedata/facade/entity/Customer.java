package com.lframework.xingyun.basedata.facade.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.facade.enums.SettleType;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_data_customer")
public class Customer extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "CustomerDto";
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
   * 助记码
   */
  private String mnemonicCode;

  /**
   * 联系人
   */
  private String contact;

  /**
   * 联系电话
   */
  private String telephone;

  /**
   * 电子邮箱
   */
  private String email;

  /**
   * 邮编
   */
  private String zipCode;

  /**
   * 传真
   */
  private String fax;

  /**
   * 地区ID
   */
  private String cityId;

  /**
   * 地址
   */
  private String address;

  /**
   * 收货人
   */
  private String receiver;

  /**
   * 收货手机号
   */
  private String receiveTelephone;

  /**
   * 收货地址
   */
  private String receiveAddress;

  /**
   * 结账方式
   */
  private SettleType settleType;

  /**
   * 统一社会信用代码
   */
  private String creditCode;

  /**
   * 纳税人识别号
   */
  private String taxIdentifyNo;

  /**
   * 开户银行
   */
  private String bankName;

  /**
   * 户名
   */
  private String accountName;

  /**
   * 银行账号
   */
  private String accountNo;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建人ID
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

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
