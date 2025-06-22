package com.lframework.xingyun.sc.bo.purchase.receive;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.dto.purchase.PurchaseProductDto;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.entity.PurchaseOrder;
import com.lframework.xingyun.sc.enums.ReceiveSheetStatus;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PrintReceiveSheetBo extends BaseBo<ReceiveSheetFullDto> {

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 仓库编号
   */
  @ApiModelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 供应商编号
   */
  @ApiModelProperty("供应商编号")
  private String supplierCode;

  /**
   * 供应商名称
   */
  @ApiModelProperty("供应商名称")
  private String supplierName;

  /**
   * 采购员姓名
   */
  @ApiModelProperty("采购员姓名")
  private String purchaserName;

  /**
   * 付款日期
   */
  @ApiModelProperty("付款日期")
  private String paymentDate;

  /**
   * 到货日期
   */
  @ApiModelProperty("到货日期")
  private String receiveDate;

  /**
   * 采购订单号
   */
  @ApiModelProperty("采购订单号")
  private String purchaseOrderCode;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private String createTime;

  /**
   * 审核人
   */
  @ApiModelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @ApiModelProperty("审核时间")
  private String approveTime;

  /**
   * 订单明细
   */
  @ApiModelProperty("订单明细")
  private List<OrderDetailBo> details;

  public PrintReceiveSheetBo() {

  }

  public PrintReceiveSheetBo(ReceiveSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<ReceiveSheetFullDto> convert(ReceiveSheetFullDto dto) {

    return super.convert(dto, PrintReceiveSheetBo::getDetails);
  }

  @Override
  protected void afterInit(ReceiveSheetFullDto dto) {

    this.purchaserName = StringPool.EMPTY_STR;
    this.paymentDate = StringPool.EMPTY_STR;
    this.receiveDate = StringPool.EMPTY_STR;
    this.purchaseOrderCode = StringPool.EMPTY_STR;
    this.approveBy = StringPool.EMPTY_STR;
    this.approveTime = StringPool.EMPTY_STR;

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    Supplier supplier = supplierService.findById(dto.getSupplierId());
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(PurchaseOrderService.class);
    if (!StringUtil.isBlank(dto.getPurchaseOrderId())) {
      PurchaseOrder purchaseOrder = purchaseOrderService.getById(dto.getPurchaseOrderId());
      this.purchaseOrderCode = purchaseOrder.getCode();
    }

    if (dto.getPaymentDate() != null) {
      this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
    }

    if (dto.getReceiveDate() != null) {
      this.receiveDate = DateUtil.formatDate(dto.getReceiveDate());
    }

    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == ReceiveSheetStatus.APPROVE_PASS) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(OrderDetailBo::new).collect(Collectors.toList());
    }
  }

  @Data
  public static class OrderDetailBo extends BaseBo<ReceiveSheetFullDto.OrderDetailDto> {

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private String productCode;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * SKU编号
     */
    @ApiModelProperty("SKU编号")
    private String skuCode;

    /**
     * 简码
     */
    @ApiModelProperty("简码")
    private String externalCode;

    /**
     * 收货数量
     */
    @ApiModelProperty("收货数量")
    private Integer receiveNum;

    /**
     * 采购价
     */
    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    /**
     * 收货金额
     */
    @ApiModelProperty("收货金额")
    private BigDecimal receiveAmount;

    public OrderDetailBo(ReceiveSheetFullDto.OrderDetailDto dto) {

      this.init(dto);
    }

    @Override
    public BaseBo<ReceiveSheetFullDto.OrderDetailDto> convert(
        ReceiveSheetFullDto.OrderDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(ReceiveSheetFullDto.OrderDetailDto dto) {

      this.receiveNum = dto.getOrderNum();
      this.purchasePrice = dto.getTaxPrice();
      this.receiveAmount = NumberUtil.mul(dto.getOrderNum(), dto.getTaxPrice());

      PurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
          PurchaseOrderService.class);
      PurchaseProductDto product = purchaseOrderService.getPurchaseById(dto.getProductId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
    }
  }
}
