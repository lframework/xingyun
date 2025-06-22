package com.lframework.xingyun.sc.dto.logistics;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import com.lframework.xingyun.sc.enums.LogisticsSheetStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class LogisticsSheetFullDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 单号
   */
  private String code;

  /**
   * 物流单号
   */
  private String logisticsNo;

  /**
   * 物流公司ID
   */
  private String logisticsCompanyId;

  /**
   * 寄件人姓名
   */
  private String senderName;

  /**
   * 寄件人联系电话
   */
  private String senderTelephone;

  /**
   * 寄件人省
   */
  private String senderProvinceId;

  /**
   * 寄件人市
   */
  private String senderCityId;

  /**
   * 寄件人区
   */
  private String senderDistrictId;

  /**
   * 寄件人地址
   */
  private String senderAddress;

  /**
   * 收件人姓名
   */
  private String receiverName;

  /**
   * 收件人联系电话
   */
  private String receiverTelephone;

  /**
   * 收件人省
   */
  private String receiverProvinceId;

  /**
   * 收件人市
   */
  private String receiverCityId;

  /**
   * 收件人区
   */
  private String receiverDistrictId;

  /**
   * 收件人地址
   */
  private String receiverAddress;

  /**
   * 状态
   */
  private LogisticsSheetStatus status;

  /**
   * 备注
   */
  private String description;

  /**
   * 总重量
   */
  private BigDecimal totalWeight;

  /**
   * 总体积
   */
  private BigDecimal totalVolume;

  /**
   * 物流费
   */
  private BigDecimal totalAmount;

  /**
   * 发货人
   */
  private String deliveryBy;

  /**
   * 发货时间
   */
  private LocalDateTime deliveryTime;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  private String updateBy;

  /**
   * 修改时间
   */
  private LocalDateTime updateTime;

  /**
   * 明细
   */
  private List<DetailDto> details;

  @Data
  public static class DetailDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 物流单ID
     */
    private String sheetId;

    /**
     * 业务单据ID
     */
    private String bizId;

    /**
     * 业务类型
     */
    private LogisticsSheetDetailBizType bizType;
  }
}
