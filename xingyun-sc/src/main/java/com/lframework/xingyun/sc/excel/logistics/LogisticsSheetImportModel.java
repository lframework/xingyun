package com.lframework.xingyun.sc.excel.logistics;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.annotations.excel.ExcelRequired;
import com.lframework.starter.web.components.excel.ExcelModel;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class LogisticsSheetImportModel implements ExcelModel {

  /**
   * 物流单号
   */
  @ExcelProperty("物流单号")
  private String logisticsNo;

  /**
   * 销售出库单ID
   */
  @ExcelIgnore
  private Map<String, String> saleOutSheetIds;

  /**
   * 销售出库单号
   */
  @ExcelProperty("销售出库单号，多个用,分隔")
  private String saleOutSheetCodes;

  /**
   * 零售出库单ID
   */
  @ExcelIgnore
  private Map<String, String> retailOutSheetIds;

  /**
   * 零售出库单号
   */
  @ExcelProperty("零售出库单号，多个用,分隔")
  private String retailOutSheetCodes;

  /**
   * 物流公司ID
   */
  @ExcelIgnore
  private String logisticsCompanyId;

  /**
   * 物流公司编号
   */
  @ExcelRequired
  @ExcelProperty("物流公司编号")
  private String logisticsCompanyCode;

  /**
   * 寄件人姓名
   */
  @ExcelRequired
  @ExcelProperty("寄件人姓名")
  private String senderName;

  /**
   * 寄件人联系电话
   */
  @ExcelRequired
  @ExcelProperty("寄件人联系电话")
  private String senderTelephone;

  /**
   * 寄件人省ID
   */
  @ExcelIgnore
  private String senderProvinceId;

  /**
   * 寄件人省
   */
  @ExcelRequired
  @ExcelProperty("寄件人省")
  private String senderProvinceName;

  /**
   * 寄件人市ID
   */
  @ExcelIgnore
  private String senderCityId;

  /**
   * 寄件人市
   */
  @ExcelRequired
  @ExcelProperty("寄件人市")
  private String senderCityName;

  /**
   * 寄件人区ID
   */
  @ExcelIgnore
  private String senderDistrictId;

  /**
   * 寄件人区
   */
  @ExcelRequired
  @ExcelProperty("寄件人区")
  private String senderDistrictName;

  /**
   * 寄件人地址
   */
  @ExcelRequired
  @ExcelProperty("寄件人地址")
  private String senderAddress;

  /**
   * 收件人姓名
   */
  @ExcelRequired
  @ExcelProperty("收件人姓名")
  private String receiverName;

  /**
   * 收件人联系电话
   */
  @ExcelRequired
  @ExcelProperty("收件人联系电话")
  private String receiverTelephone;

  /**
   * 收件人省ID
   */
  @ExcelIgnore
  private String receiverProvinceId;

  /**
   * 收件人省
   */
  @ExcelRequired
  @ExcelProperty("收件人省")
  private String receiverProvinceName;

  /**
   * 收件人市ID
   */
  @ExcelIgnore
  private String receiverCityId;

  /**
   * 收件人市
   */
  @ExcelRequired
  @ExcelProperty("收件人市")
  private String receiverCityName;

  /**
   * 收件人区ID
   */
  @ExcelIgnore
  private String receiverDistrictId;

  /**
   * 收件人区
   */
  @ExcelRequired
  @ExcelProperty("收件人区")
  private String receiverDistrictName;

  /**
   * 收件人地址
   */
  @ExcelRequired
  @ExcelProperty("收件人地址")
  private String receiverAddress;

  /**
   * 总重量
   */
  @ExcelProperty("总重量（kg）")
  private BigDecimal totalWeight;

  /**
   * 总体积
   */
  @ExcelProperty("总体积（cm³）")
  private BigDecimal totalVolume;

  /**
   * 物流费
   */
  @ExcelProperty("物流费（元）")
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;
}
