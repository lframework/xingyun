package com.lframework.xingyun.basedata.dto.supplier;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ManageType;
import com.lframework.xingyun.basedata.enums.SettleType;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SupplierDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "SupplierDto";

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
   * 发货地址
   */
  private String deliveryAddress;

  /**
   * 送货周期（天）
   */
  private Integer deliveryCycle;

  /**
   * 经营方式
   */
  private ManageType manageType;

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
