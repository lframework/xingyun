package com.lframework.xingyun.sc.api.excel.purchase.returned;

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
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.sc.biz.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturn;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseReturnExportModel extends BaseBo<PurchaseReturn> implements ExcelModel {

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
   * 供应商编号
   */
  @ExcelProperty("供应商编号")
  private String supplierCode;

  /**
   * 供应商名称
   */
  @ExcelProperty("供应商名称")
  private String supplierName;

  /**
   * 采购员姓名
   */
  @ExcelProperty("采购员")
  private String purchaserName;

  /**
   * 单据总金额
   */
  @ExcelProperty("单据总金额")
  private BigDecimal totalAmount;

  /**
   * 商品数量
   */
  @ExcelProperty("商品数量")
  private Integer receiveNum;

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
   * 采购收货单号
   */
  @ExcelProperty("采购收货单号")
  private String receiveSheetCode;

  public PurchaseReturnExportModel() {

  }

  public PurchaseReturnExportModel(PurchaseReturn dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<PurchaseReturn> convert(PurchaseReturn dto) {

    return this;
  }

  @Override
  protected void afterInit(PurchaseReturn dto) {

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();

    SupplierFeignClient supplierFeignClient = ApplicationUtil.getBean(SupplierFeignClient.class);
    Supplier supplier = supplierFeignClient.findById(dto.getSupplierId()).getData();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto purchaser = null;
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      purchaser = userService.findById(dto.getPurchaserId());
    }
    UserDto createBy = userService.findById(dto.getCreateBy());
    UserDto approveBy = null;
    if (!StringUtil.isBlank(dto.getApproveBy())) {
      approveBy = userService.findById(dto.getApproveBy());
    }

    this.setCode(dto.getCode());
    this.setScCode(sc.getCode());
    this.setScName(sc.getName());
    this.setSupplierCode(supplier.getCode());
    this.setSupplierName(supplier.getName());
    this.setPurchaserName(purchaser == null ? null : purchaser.getName());
    this.setTotalAmount(dto.getTotalAmount());
    this.setReceiveNum(dto.getTotalNum());
    this.setGiftNum(dto.getTotalGiftNum());
    this.setCreateTime(DateUtil.toDate(dto.getCreateTime()));
    this.setCreateBy(createBy.getName());
    this.setStatus(dto.getStatus().getDesc());
    if (dto.getApproveTime() != null) {
      this.setApproveTime(DateUtil.toDate(dto.getApproveTime()));
    }
    if (approveBy != null) {
      this.setApproveBy(approveBy.getName());
    }
    this.setSettleStatus(dto.getSettleStatus().getDesc());
    this.setDescription(dto.getDescription());
    if (!StringUtil.isBlank(dto.getReceiveSheetId())) {
      IReceiveSheetService receiveSheetService = ApplicationUtil.getBean(
          IReceiveSheetService.class);
      ReceiveSheet receiveSheet = receiveSheetService.getById(dto.getReceiveSheetId());
      this.setReceiveSheetCode(receiveSheet.getCode());
    }
  }
}
