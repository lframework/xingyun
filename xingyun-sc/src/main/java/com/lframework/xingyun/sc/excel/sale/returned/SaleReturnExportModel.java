package com.lframework.xingyun.sc.excel.sale.returned;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class SaleReturnExportModel extends BaseBo<SaleReturn> implements ExcelModel {

  /**
   * 单号
   */
  @ExcelProperty("业务单据号")
  private String code;

  /**
   * 仓库编号
   */
  @ExcelProperty("仓库编号")
  private String scCode;

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
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  @ExcelProperty("赠品数量")
  private BigDecimal giftNum;

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

  /**
   * 销售出库单号
   */
  @ExcelProperty("销售出库单号")
  private String outSheetCode;

  public SaleReturnExportModel() {

  }

  public SaleReturnExportModel(SaleReturn dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<SaleReturn> convert(SaleReturn dto) {

    return this;
  }

  @Override
  protected void afterInit(SaleReturn dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());

    CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
    Customer customer = customerService.findById(dto.getCustomerId());

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    SysUser saler = null;
    if (!StringUtil.isBlank(dto.getSalerId())) {
      saler = userService.findById(dto.getSalerId());
    }
    SysUser approveBy = null;
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      approveBy = userService.findById(dto.getApproveBy());
    }

    this.setCode(dto.getCode());
    this.setScCode(sc.getCode());
    this.setScName(sc.getName());
    this.setCustomerCode(customer.getCode());
    this.setCustomerName(customer.getName());
    this.setSalerName(saler == null ? null : saler.getName());
    this.setTotalAmount(dto.getTotalAmount());
    this.setTotalNum(dto.getTotalNum());
    this.setGiftNum(dto.getTotalGiftNum());
    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
    this.setStatus(dto.getStatus().getDesc());
    if (dto.getApproveTime() != null) {
      this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
    }
    if (approveBy != null) {
      this.setApproveBy(approveBy.getName());
    }
    this.setSettleStatus(dto.getSettleStatus().getDesc());
    this.setDescription(dto.getDescription());
    if (!StringUtil.isBlank(dto.getOutSheetId())) {
      SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(SaleOutSheetService.class);
      SaleOutSheet saleOutSheet = saleOutSheetService.getById(dto.getOutSheetId());
      this.setOutSheetCode(saleOutSheet.getCode());
    }
  }
}
