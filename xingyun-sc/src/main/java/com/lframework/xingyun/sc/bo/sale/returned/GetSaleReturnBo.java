package com.lframework.xingyun.sc.bo.sale.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.bo.paytype.OrderPayTypeBo;
import com.lframework.xingyun.sc.dto.sale.SaleProductDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.sale.SaleOrderService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetSaleReturnBo extends BaseBo<SaleReturnFullDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 客户ID
   */
  @ApiModelProperty("客户ID")
  private String customerId;

  /**
   * 客户名称
   */
  @ApiModelProperty("客户名称")
  private String customerName;

  /**
   * 销售员ID
   */
  @ApiModelProperty("销售员ID")
  private String salerId;

  /**
   * 销售员姓名
   */
  @ApiModelProperty("销售员姓名")
  private String salerName;

  /**
   * 付款日期
   */
  @ApiModelProperty("付款日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate paymentDate;

  /**
   * 销售出库单ID
   */
  @ApiModelProperty("销售出库单ID")
  private String outSheetId;

  /**
   * 销售出库单号
   */
  @ApiModelProperty("销售出库单号")
  private String outSheetCode;

  /**
   * 退货数量
   */
  @ApiModelProperty("退货数量")
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  @ApiModelProperty("赠品数量")
  private BigDecimal giftNum;

  /**
   * 退货金额
   */
  @ApiModelProperty("退货金额")
  private BigDecimal totalAmount;

  /**
   * 支付方式
   */
  @ApiModelProperty("支付方式")
  private List<OrderPayTypeBo> payTypes;

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
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 审核人
   */
  @ApiModelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @ApiModelProperty("审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  /**
   * 拒绝原因
   */
  @ApiModelProperty("拒绝原因")
  private String refuseReason;

  /**
   * 结算状态
   */
  @ApiModelProperty("结算状态")
  private Integer settleStatus;

  /**
   * 订单明细
   */
  @ApiModelProperty("订单明细")
  private List<ReturnDetailBo> details;

  public GetSaleReturnBo(SaleReturnFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SaleReturnFullDto> convert(SaleReturnFullDto dto) {

    return super.convert(dto, GetSaleReturnBo::getStatus, GetSaleReturnBo::getSettleStatus,
        GetSaleReturnBo::getDetails);
  }

  @Override
  protected void afterInit(SaleReturnFullDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    this.scName = storeCenterService.findById(dto.getScId()).getName();

    CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
    this.customerName = customerService.findById(dto.getCustomerId()).getName();

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getSalerId())) {
      this.salerName = userService.findById(dto.getSalerId()).getName();
    }

    SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(SaleOutSheetService.class);
    if (!StringUtil.isBlank(dto.getOutSheetId())) {
      SaleOutSheet outSheet = saleOutSheetService.getById(dto.getOutSheetId());
      this.outSheetCode = outSheet.getCode();
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
      this.details = dto.getDetails().stream().map(t -> new ReturnDetailBo(this.getScId(), t))
          .collect(Collectors.toList());
    }

    OrderPayTypeService orderPayTypeService = ApplicationUtil.getBean(OrderPayTypeService.class);
    List<OrderPayType> orderPayTypes = orderPayTypeService.findByOrderId(dto.getId());
    this.payTypes = orderPayTypes.stream().map(OrderPayTypeBo::new).collect(Collectors.toList());
  }

  @Data
  public static class ReturnDetailBo extends BaseBo<SaleReturnFullDto.ReturnDetailDto> {

    /**
     * 明细ID
     */
    @ApiModelProperty("明细ID")
    private String id;

    /**
     * 商品ID
     */
    @ApiModelProperty("商品ID")
    private String productId;

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
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * 出库数量
     */
    @ApiModelProperty("出库数量")
    private BigDecimal outNum;

    /**
     * 剩余退货数量
     */
    @ApiModelProperty("剩余退货数量")
    private BigDecimal remainNum;

    /**
     * 退货数量
     */
    @ApiModelProperty("退货数量")
    private BigDecimal returnNum;

    /**
     * 原价
     */
    @ApiModelProperty("原价")
    private BigDecimal salePrice;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal taxPrice;

    /**
     * 折扣
     */
    @ApiModelProperty("折扣")
    private BigDecimal discountRate;

    /**
     * 是否赠品
     */
    @ApiModelProperty("是否赠品")
    private Boolean isGift;

    /**
     * 税率
     */
    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 销售出库单明细ID
     */
    @ApiModelProperty("销售出库单明细ID")
    private String outSheetDetailId;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    public ReturnDetailBo(String scId, SaleReturnFullDto.ReturnDetailDto dto) {

      this.scId = scId;
      this.init(dto);
    }

    @Override
    public BaseBo<SaleReturnFullDto.ReturnDetailDto> convert(
        SaleReturnFullDto.ReturnDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(SaleReturnFullDto.ReturnDetailDto dto) {

      this.returnNum = dto.getReturnNum();
      this.salePrice = dto.getOriPrice();
      this.taxPrice = dto.getTaxPrice();
      this.discountRate = dto.getDiscountRate();

      SaleOrderService saleOrderService = ApplicationUtil.getBean(SaleOrderService.class);
      SaleProductDto product = saleOrderService.getSaleById(dto.getProductId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.unit = product.getUnit();
      this.spec = product.getSpec();
      this.categoryName = product.getCategoryName();
      this.brandName = product.getBrandName();

      if (!StringUtil.isBlank(dto.getOutSheetDetailId())) {
        SaleOutSheetDetailLotService receiveSheetDetailService = ApplicationUtil.getBean(
            SaleOutSheetDetailLotService.class);
        SaleOutSheetDetailLotDto outSheetDetail = receiveSheetDetailService.findById(
            dto.getOutSheetDetailId());
        this.outNum = outSheetDetail.getOrderNum();
        this.remainNum = NumberUtil.sub(outSheetDetail.getOrderNum(),
            outSheetDetail.getReturnNum());
      }
    }
  }
}
