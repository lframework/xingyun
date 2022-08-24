package com.lframework.xingyun.settle.api.excel.pre.customer;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.basedata.facade.CustomerFeignClient;
import com.lframework.xingyun.basedata.facade.entity.Customer;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.settle.facade.entity.CustomerSettlePreSheet;
import com.lframework.xingyun.settle.facade.enums.CustomerSettlePreSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerSettlePreSheetExportModel extends BaseBo<CustomerSettlePreSheet> implements
    ExcelModel {

  /**
   * 业务单据号
   */
  @ExcelProperty("业务单据号")
  private String code;

  /**
   * 客户编号
   */
  @ApiModelProperty("客户编号")
  private String customerCode;

  /**
   * 客户名称
   */
  @ApiModelProperty("客户名称")
  private String customerName;

  /**
   * 单据总金额
   */
  @ExcelProperty("单据总金额")
  private BigDecimal totalAmount;

  /**
   * 操作时间
   */
  @ExcelProperty("操作时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date createTime;

  /**
   * 操作人
   */
  @ExcelProperty("操作人")
  private String createBy;

  /**
   * 审核状态
   */
  @ExcelProperty("审核状态")
  private String status;

  /**
   * 审核时间
   */
  @ExcelProperty("审核时间")
  @DateTimeFormat(StringPool.DATE_TIME_PATTERN)
  private Date approveTime;

  /**
   * 审核人
   */
  @ExcelProperty("审核人")
  private String approveBy;

  /**
   * 结算状态
   */
  @ExcelProperty("结算状态")
  private String settleStatus;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

  public CustomerSettlePreSheetExportModel() {

  }

  public CustomerSettlePreSheetExportModel(CustomerSettlePreSheet dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<CustomerSettlePreSheet> convert(CustomerSettlePreSheet dto) {

    return this;
  }

  @Override
  protected void afterInit(CustomerSettlePreSheet dto) {

    CustomerFeignClient customerFeignClient = ApplicationUtil.getBean(CustomerFeignClient.class);
    Customer customer = customerFeignClient.findById(dto.getCustomerId()).getData();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto createBy = userService.findById(dto.getCreateBy());
    UserDto approveBy = null;
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      approveBy = userService.findById(dto.getApproveBy());
    }

    this.setCode(dto.getCode());
    this.setCustomerCode(customer.getCode());
    this.setCustomerName(customer.getName());
    this.setTotalAmount(dto.getTotalAmount());
    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
    this.setCreateBy(createBy.getName());
    this.setStatus(EnumUtil.getDesc(CustomerSettlePreSheetStatus.class, dto.getStatus()));
    if (approveBy != null) {
      this.setApproveBy(approveBy.getName());
    }

    if (dto.getApproveTime() != null) {
      this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
    }
    this.setSettleStatus(EnumUtil.getDesc(SettleStatus.class, dto.getSettleStatus()));
    this.setDescription(dto.getDescription());
  }
}
