package com.lframework.xingyun.api.bo.sale.out;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.sale.SaleOrderDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetFullDto;
import com.lframework.xingyun.sc.enums.SaleOutSheetStatus;
import com.lframework.xingyun.sc.service.sale.ISaleOrderService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrintSaleOutSheetBo extends BasePrintDataBo<SaleOutSheetFullDto> {

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
   * 销售员姓名
   */
  @ApiModelProperty("销售员姓名")
  private String salerName;

  /**
   * 付款日期
   */
  @ApiModelProperty("付款日期")
  private String paymentDate;

  /**
   * 销售订单号
   */
  @ApiModelProperty("销售订单号")
  private String saleOrderCode;

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

  public PrintSaleOutSheetBo() {

  }

  public PrintSaleOutSheetBo(SaleOutSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SaleOutSheetFullDto> convert(SaleOutSheetFullDto dto) {

    return super.convert(dto, PrintSaleOutSheetBo::getDetails);
  }

  @Override
  protected void afterInit(SaleOutSheetFullDto dto) {

    this.salerName = StringPool.EMPTY_STR;
    this.paymentDate = StringPool.EMPTY_STR;
    this.saleOrderCode = StringPool.EMPTY_STR;
    this.description = StringPool.EMPTY_STR;
    this.approveBy = StringPool.EMPTY_STR;
    this.approveTime = StringPool.EMPTY_STR;

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

    ISaleOrderService saleOrderService = ApplicationUtil.getBean(ISaleOrderService.class);
    if (!StringUtil.isBlank(dto.getSaleOrderId())) {
      SaleOrderDto saleOrder = saleOrderService.getById(dto.getSaleOrderId());
      this.saleOrderCode = saleOrder.getCode();
    }

    if (dto.getPaymentDate() != null) {
      this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
    }

    this.createBy = userService.getById(dto.getCreateBy()).getName();
    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == SaleOutSheetStatus.APPROVE_PASS) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(OrderDetailBo::new).collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class OrderDetailBo extends BaseBo<SaleOutSheetFullDto.SheetDetailDto> {

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
     * 外部编号
     */
    @ApiModelProperty("外部编号")
    private String externalCode;

    /**
     * 出库数量
     */
    @ApiModelProperty("出库数量")
    private Integer outNum;

    /**
     * 价格
     */
    @ApiModelProperty("价格")
    private BigDecimal taxPrice;

    /**
     * 折扣
     */
    @ApiModelProperty("折扣")
    private BigDecimal outAmount;

    public OrderDetailBo(SaleOutSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<SaleOutSheetFullDto.SheetDetailDto> convert(
        SaleOutSheetFullDto.SheetDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(SaleOutSheetFullDto.SheetDetailDto dto) {

      this.outNum = dto.getOrderNum();
      this.taxPrice = dto.getTaxPrice();
      this.outAmount = NumberUtil.mul(dto.getTaxPrice(), dto.getOrderNum());

      IProductService productService = ApplicationUtil.getBean(IProductService.class);
      SaleProductDto product = productService.getSaleById(dto.getProductId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
    }
  }
}
