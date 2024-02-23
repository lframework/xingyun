package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.xingyun.sc.enums.LogisticsSheetStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 物流单
 * </p>
 *
 * @author zmj
 * @since 2023-06-13
 */
@Data
@TableName("tbl_logistics_sheet")
public class LogisticsSheet extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "LogisticsSheet";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 业务单据号
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
   * 状态
   */
  private LogisticsSheetStatus status;

  /**
   * 备注
   */
  private String description;

  /**
   * 发货人
   */
  private String deliveryBy;

  /**
   * 发货时间
   */
  private LocalDateTime deliveryTime;

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
