package com.lframework.xingyun.api.model.sale;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderDto;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaleOrderExportModel extends BaseBo<SaleOrderDto> implements ExcelModel {

  /**
   * 单号
   */
  @ExcelProperty("业务单据号")
  private String code;

  /**
   * 仓库编号
   */
  @ExcelProperty("仓库编号")
  private String scOde;

  /**
   * 仓库名称
   */
  @ExcelProperty("仓库名称")
  private String scName;

  /**
   * 客户编号
   */
  @ExcelProperty("客户编号")
  private String customerCode;

  /**
   * 客户名称
   */
  @ExcelProperty("客户名称")
  private String customerName;

  /**
   * 销售员姓名
   */
  @ExcelProperty("销售员")
  private String salerName;

  /**
   * 单据总金额
   */
  @ExcelProperty("单据总金额")
  private BigDecimal totalAmount;

  /**
   * 商品数量
   */
  @ExcelProperty("商品数量")
  private Integer totalNum;

  /**
   * 赠品数量
   */
  @ExcelProperty("赠品数量")
  private Integer giftNum;

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
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

  public SaleOrderExportModel() {

  }

  public SaleOrderExportModel(SaleOrderDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<SaleOrderDto> convert(SaleOrderDto dto) {

    return this;
  }

  @Override
  protected void afterInit(SaleOrderDto dto) {

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());

    ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
    CustomerDto customer = customerService.getById(dto.getCustomerId());

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto saler = userService.getById(dto.getSalerId());
    UserDto createBy = userService.getById(dto.getCreateBy());
    UserDto approveBy = null;
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      approveBy = userService.getById(dto.getApproveBy());
    }

    this.setCode(dto.getCode());
    this.setScOde(sc.getCode());
    this.setScName(sc.getName());
    this.setCustomerCode(customer.getCode());
    this.setCustomerName(customer.getName());
    if (saler != null) {
      this.setSalerName(saler.getName());
    }
    this.setTotalNum(dto.getTotalNum());
    this.setGiftNum(dto.getTotalGiftNum());
    this.setTotalAmount(dto.getTotalAmount());
    this.setDescription(dto.getDescription());
    this.setCreateBy(createBy.getName());
    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
    if (approveBy != null) {
      this.setApproveBy(approveBy.getName());
    }

    if (dto.getApproveTime() != null) {
      this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
    }

    this.setStatus(dto.getStatus().getDesc());
  }
}
