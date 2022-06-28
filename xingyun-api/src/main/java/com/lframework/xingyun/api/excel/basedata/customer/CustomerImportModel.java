package com.lframework.xingyun.api.excel.basedata.customer;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.xingyun.basedata.enums.SettleType;
import lombok.Data;

@Data
public class CustomerImportModel implements ExcelModel {

  /**
   * ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 编号
   */
  @ExcelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ExcelProperty("名称")
  private String name;

  /**
   * 助记码
   */
  @ExcelProperty("助记码")
  private String mnemonicCode;

  /**
   * 联系人
   */
  @ExcelProperty("联系人")
  private String contact;

  /**
   * 联系电话
   */
  @ExcelProperty("联系电话")
  private String telephone;

  /**
   * 电子邮箱
   */
  @ExcelProperty("电子邮箱")
  private String email;

  /**
   * 邮编
   */
  @ExcelProperty("邮编")
  private String zipCode;

  /**
   * 传真
   */
  @ExcelProperty("传真")
  private String fax;

  /**
   * 地区
   */
  @ExcelProperty("地区")
  private String city;

  /**
   * 地区ID
   */
  @ExcelIgnore
  private String areaId;

  /**
   * 地址
   */
  @ExcelProperty("地址")
  private String address;

  /**
   * 收货人
   */
  @ExcelProperty("收货人")
  private String receiver;

  /**
   * 收货手机号
   */
  @ExcelProperty("收货手机号")
  private String receiveTelephone;

  /**
   * 收货地址
   */
  @ExcelProperty("收货地址")
  private String receiveAddress;

  /**
   * 结账方式
   */
  @ExcelProperty("结账方式")
  private String settleType;

  /**
   * 结账方式枚举
   */
  @ExcelIgnore
  private SettleType settleTypeEnum;

  /**
   * 统一社会信用代码
   */
  @ExcelProperty("统一社会信用代码")
  private String creditCode;

  /**
   * 纳税人识别号
   */
  @ExcelProperty("纳税人识别号")
  private String taxIdentifyNo;

  /**
   * 开户银行
   */
  @ExcelProperty("开户银行")
  private String bankName;

  /**
   * 户名
   */
  @ExcelProperty("户名")
  private String accountName;

  /**
   * 银行账号
   */
  @ExcelProperty("银行账号")
  private String accountNo;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;
}
