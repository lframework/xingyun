package com.lframework.xingyun.sc.excel.logistics;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.service.logistics.LogisticsCompanyService;
import com.lframework.starter.web.inner.dto.dic.city.DicCityDto;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.service.DicCityService;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import com.lframework.starter.web.inner.service.system.SysUserService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class LogisticsSheetExportModel extends BaseBo<LogisticsSheet> implements ExcelModel {

  /**
   * 单号
   */
  @ExcelProperty("单号")
  private String code;

  /**
   * 物流单号
   */
  @ExcelProperty("物流单号")
  private String logisticsNo;

  /**
   * 物流公司
   */
  @ExcelProperty("物流公司")
  private String logisticsCompanyName;

  /**
   * 寄件人姓名
   */
  @ExcelProperty("寄件人姓名")
  private String senderName;

  /**
   * 寄件人联系电话
   */
  @ExcelProperty("寄件人联系电话")
  private String senderTelephone;

  /**
   * 寄件人省
   */
  @ExcelProperty("寄件人省")
  private String senderProvinceName;

  /**
   * 寄件人市
   */
  @ExcelProperty("寄件人市")
  private String senderCityName;

  /**
   * 寄件人区
   */
  @ExcelProperty("寄件人区")
  private String senderDistrictName;

  /**
   * 寄件人地址
   */
  @ExcelProperty("寄件人地址")
  private String senderAddress;

  /**
   * 收件人姓名
   */
  @ExcelProperty("收件人姓名")
  private String receiverName;

  /**
   * 收件人联系电话
   */
  @ExcelProperty("收件人联系电话")
  private String receiverTelephone;

  /**
   * 收件人省
   */
  @ExcelProperty("收件人省")
  private String receiverProvinceName;

  /**
   * 收件人市
   */
  @ExcelProperty("收件人市")
  private String receiverCityName;

  /**
   * 收件人区
   */
  @ExcelProperty("收件人区")
  private String receiverDistrictName;

  /**
   * 收件人地址
   */
  @ExcelProperty("收件人地址")
  private String receiverAddress;

  /**
   * 状态
   */
  @ExcelProperty("状态")
  private String status;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

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
   * 创建人
   */
  @ExcelProperty("操作人")
  private String createBy;

  /**
   * 创建时间
   */
  @ExcelProperty("操作时间")
  private Date createTime;

  /**
   * 发货人
   */
  @ExcelProperty("发货人")
  private String deliveryBy;

  /**
   * 发货时间
   */
  @ExcelProperty("发货时间")
  private Date deliveryTime;

  public LogisticsSheetExportModel() {

  }

  public LogisticsSheetExportModel(LogisticsSheet dto) {

    super(dto);
  }

  @Override
  protected void afterInit(LogisticsSheet dto) {

    LogisticsCompanyService logisticsCompanyService = ApplicationUtil.getBean(
        LogisticsCompanyService.class);
    LogisticsCompany logisticsCompany = logisticsCompanyService.findById(
        dto.getLogisticsCompanyId());
    this.logisticsCompanyName = logisticsCompany.getName();

    DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
    DicCityDto senderProvince = dicCityService.findById(dto.getSenderProvinceId());
    this.senderProvinceName = senderProvince.getName();

    DicCityDto senderCity = dicCityService.findById(dto.getSenderCityId());
    this.senderCityName = senderCity.getName();

    DicCityDto senderDistrict = dicCityService.findById(dto.getSenderDistrictId());
    this.senderDistrictName = senderDistrict.getName();

    DicCityDto receiverProvince = dicCityService.findById(dto.getReceiverProvinceId());
    this.receiverProvinceName = receiverProvince.getName();

    DicCityDto receiverCity = dicCityService.findById(dto.getReceiverCityId());
    this.receiverCityName = receiverCity.getName();

    DicCityDto receiverDistrict = dicCityService.findById(dto.getReceiverDistrictId());
    this.receiverDistrictName = receiverDistrict.getName();

    this.status = dto.getStatus().getDesc();

    if (StringUtil.isNotBlank(dto.getDeliveryBy())) {
      SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
      SysUser deliveryBy = userService.findById(dto.getDeliveryBy());
      this.deliveryBy = deliveryBy.getName();
    }
  }
}
