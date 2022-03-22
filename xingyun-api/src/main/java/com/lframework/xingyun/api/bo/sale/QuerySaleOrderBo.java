package com.lframework.xingyun.api.bo.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySaleOrderBo extends BaseBo<SaleOrderDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 单号
   */
  private String code;

  /**
   * 仓库编号
   */
  private String scCode;

  /**
   * 仓库名称
   */
  private String scName;

  /**
   * 客户编号
   */
  private String customerCode;

  /**
   * 客户名称
   */
  private String customerName;

  /**
   * 销售员姓名
   */
  private String salerName;

  /**
   * 销售数量
   */
  private Integer totalNum;

  /**
   * 赠品数量
   */
  private Integer totalGiftNum;

  /**
   * 销售金额
   */
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 审核人
   */
  private String approveBy;

  /**
   * 审核时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 拒绝原因
   */
  private String refuseReason;

  public QuerySaleOrderBo(SaleOrderDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SaleOrderDto> convert(SaleOrderDto dto) {

    return super.convert(dto, QuerySaleOrderBo::getStatus);
  }

  @Override
  protected void afterInit(SaleOrderDto dto) {

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
    CustomerDto customer = customerService.getById(dto.getCustomerId());
    this.customerCode = customer.getCode();
    this.customerName = customer.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    if (!StringUtil.isBlank(dto.getSalerId())) {
      this.salerName = userService.getById(dto.getSalerId()).getName();
    }

    this.createBy = userService.getById(dto.getCreateBy()).getName();

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
    }

    this.status = dto.getStatus().getCode();
  }
}
