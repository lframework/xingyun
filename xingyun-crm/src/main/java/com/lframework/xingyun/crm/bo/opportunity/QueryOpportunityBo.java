package com.lframework.xingyun.crm.bo.opportunity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.crm.entity.Opportunity;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryOpportunityBo extends BaseBo<Opportunity> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "商机编号")
  private String code;

  @Schema(description = "商机名称")
  private String name;

  @Schema(description = "关联客户名称")
  private String customerName;

  @Schema(description = "负责销售员姓名")
  private String ownerName;

  @Schema(description = "预计金额")
  private BigDecimal estimatedAmount;

  @Schema(description = "预计成交日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate expectedCloseDate;

  @Schema(description = "阶段")
  private Integer stage;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "备注")
  private String description;

  @Schema(description = "创建人")
  private String createBy;

  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryOpportunityBo(Opportunity dto) {
    super(dto);
  }

  @Override
  public BaseBo<Opportunity> convert(Opportunity dto) {
    return super.convert(dto, QueryOpportunityBo::getStage, QueryOpportunityBo::getStatus);
  }

  @Override
  protected void afterInit(Opportunity dto) {
    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getOwnerId())) {
      this.ownerName = userService.findById(dto.getOwnerId()).getName();
    }
    if (!StringUtil.isBlank(dto.getCustomerId())) {
      CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
      Customer customer = customerService.findById(dto.getCustomerId());
      if (customer != null) {
        this.customerName = customer.getName();
      }
    }
    this.stage = dto.getStage();
    this.status = dto.getStatus().getCode();
  }
}
