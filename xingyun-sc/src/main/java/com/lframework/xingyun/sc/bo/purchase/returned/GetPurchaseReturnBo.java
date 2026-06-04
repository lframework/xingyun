package com.lframework.xingyun.sc.bo.purchase.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.SupplierService;
import com.lframework.xingyun.sc.bo.paytype.OrderPayTypeBo;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetPurchaseReturnBo extends BaseBo<PurchaseReturnFullDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 单号
   */
  @Schema(description = "单号")
  private String code;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 供应商ID
   */
  @Schema(description = "供应商ID")
  private String supplierId;

  /**
   * 供应商名称
   */
  @Schema(description = "供应商名称")
  private String supplierName;

  /**
   * 采购员ID
   */
  @Schema(description = "采购员ID")
  private String purchaserId;

  /**
   * 采购员姓名
   */
  @Schema(description = "采购员姓名")
  private String purchaserName;

  /**
   * 付款日期
   */
  @Schema(description = "付款日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate paymentDate;

  /**
   * 采购收货单ID
   */
  @Schema(description = "采购收货单ID")
  private String receiveSheetId;

  /**
   * 采购收货单号
   */
  @Schema(description = "采购收货单号")
  private String receiveSheetCode;

  /**
   * 采购数量
   */
  @Schema(description = "采购数量")
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  @Schema(description = "赠品数量")
  private BigDecimal giftNum;

  /**
   * 采购金额
   */
  @Schema(description = "采购金额")
  private BigDecimal totalAmount;

  /**
   * 支付方式
   */
  @Schema(description = "支付方式")
  private List<OrderPayTypeBo> payTypes;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 审核人
   */
  @Schema(description = "审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @Schema(description = "审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Integer status;

  /**
   * 拒绝原因
   */
  @Schema(description = "拒绝原因")
  private String refuseReason;

  /**
   * 结算状态
   */
  @Schema(description = "结算状态")
  private Integer settleStatus;

  /**
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<ReturnDetailBo> details;

  public GetPurchaseReturnBo(PurchaseReturnFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseReturnFullDto> convert(PurchaseReturnFullDto dto) {

    return super.convert(dto, GetPurchaseReturnBo::getStatus, GetPurchaseReturnBo::getSettleStatus,
        GetPurchaseReturnBo::getDetails);
  }

  @Override
  protected void afterInit(PurchaseReturnFullDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    this.scName = storeCenterService.findById(dto.getScId()).getName();

    SupplierService supplierService = ApplicationUtil.getBean(SupplierService.class);
    this.supplierName = supplierService.findById(dto.getSupplierId()).getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(ReceiveSheetService.class);
    if (!StringUtil.isBlank(dto.getReceiveSheetId())) {
      ReceiveSheet receiveSheet = receiveSheetService.getById(dto.getReceiveSheetId());
      this.receiveSheetCode = receiveSheet.getCode();
    }

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.status = dto.getStatus().getCode();
    this.settleStatus = dto.getSettleStatus().getCode();

    this.totalNum = dto.getTotalNum();
    this.giftNum = dto.getTotalGiftNum();
    this.totalAmount = dto.getTotalAmount();

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(ReturnDetailBo::new)
          .collect(Collectors.toList());
    }

    OrderPayTypeService orderPayTypeService = ApplicationUtil.getBean(OrderPayTypeService.class);
    List<OrderPayType> orderPayTypes = orderPayTypeService.findByOrderId(dto.getId());
    this.payTypes = orderPayTypes.stream().map(OrderPayTypeBo::new).collect(Collectors.toList());
  }

  @Data
  public static class ReturnDetailBo extends BaseBo<PurchaseReturnFullDto.ReturnDetailDto> {

    /**
     * 明细ID
     */
    @Schema(description = "明细ID")
    private String id;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String productId;

    /**
     * SKU ID
     */
    @Schema(description = "SKU ID")
    private String skuId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String productCode;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String skuCode;

    /**
     * 销售属性
     */
    @Schema(description = "销售属性")
    private String salePropertyText;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    /**
     * 单位
     */
    @Schema(description = "单位")
    private String unit;

    /**
     * 规格
     */
    @Schema(description = "规格")
    private String spec;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 收货数量
     */
    @Schema(description = "收货数量")
    private BigDecimal receiveNum;

    /**
     * 剩余退货数量
     */
    @Schema(description = "剩余退货数量")
    private BigDecimal remainNum;

    /**
     * 退货数量
     */
    @Schema(description = "退货数量")
    private BigDecimal returnNum;

    /**
     * 退货价
     */
    @Schema(description = "退货价")
    private BigDecimal purchasePrice;

    /**
     * 含税成本价
     */
    @Schema(description = "含税成本价")
    private BigDecimal taxCostPrice;

    /**
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    /**
     * 是否赠品
     */
    @Schema(description = "是否赠品")
    private Boolean isGift;

    /**
     * 税率
     */
    @Schema(description = "税率")
    private BigDecimal taxRate;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    /**
     * 采购收货单明细ID
     */
    @Schema(description = "采购收货单明细ID")
    private String receiveSheetDetailId;

    public ReturnDetailBo(PurchaseReturnFullDto.ReturnDetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<PurchaseReturnFullDto.ReturnDetailDto> convert(
        PurchaseReturnFullDto.ReturnDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(PurchaseReturnFullDto.ReturnDetailDto dto) {

      this.returnNum = dto.getReturnNum();
      this.purchasePrice = dto.getTaxPrice();

      if (!StringUtil.isBlank(dto.getReceiveSheetDetailId())) {
        BigDecimal sourceReceiveNum =
            dto.getSourceReceiveNum() == null ? BigDecimal.ZERO : dto.getSourceReceiveNum();
        BigDecimal sourceReturnNum =
            dto.getSourceReturnNum() == null ? BigDecimal.ZERO : dto.getSourceReturnNum();
        this.receiveNum = sourceReceiveNum;
        this.remainNum = NumberUtil.sub(sourceReceiveNum, sourceReturnNum);
      }

      this.taxCostPrice =
          dto.getTaxCostPrice() == null ? BigDecimal.ZERO
              : NumberUtil.getNumber(dto.getTaxCostPrice(), 6);
      this.stockNum = dto.getStockNum() == null ? BigDecimal.ZERO : dto.getStockNum();
    }
  }
}
