package com.lframework.xingyun.sc.api.bo.purchase.returned;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.sc.biz.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.facade.dto.purchase.returned.PurchaseReturnFullDto;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
import com.lframework.xingyun.sc.facade.enums.PurchaseReturnStatus;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrintPurchaseReturnBo extends BasePrintDataBo<PurchaseReturnFullDto> {

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
   * 采购收货单号
   */
  @ApiModelProperty("采购收货单号")
  private String receiveSheetCode;

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
  private List<ReturnDetailBo> details;

  public PrintPurchaseReturnBo(PurchaseReturnFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseReturnFullDto> convert(PurchaseReturnFullDto dto) {

    return super.convert(dto, PrintPurchaseReturnBo::getDetails);
  }

  @Override
  protected void afterInit(PurchaseReturnFullDto dto) {

    this.purchaserName = StringPool.EMPTY_STR;
    this.paymentDate = StringPool.EMPTY_STR;
    this.receiveSheetCode = StringPool.EMPTY_STR;
    this.approveBy = StringPool.EMPTY_STR;
    this.approveTime = StringPool.EMPTY_STR;

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    SupplierFeignClient suppliesupplierFeignClientService = ApplicationUtil.getBean(
        SupplierFeignClient.class);
    Supplier supplier = suppliesupplierFeignClientService.findById(dto.getSupplierId()).getData();
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    IReceiveSheetService receiveSheetService = ApplicationUtil.getBean(IReceiveSheetService.class);
    if (!StringUtil.isBlank(dto.getReceiveSheetId())) {
      ReceiveSheet receiveSheet = receiveSheetService.getById(dto.getReceiveSheetId());
      this.receiveSheetCode = receiveSheet.getCode();
    }

    if (dto.getPaymentDate() != null) {
      this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
    }

    this.createBy = userService.findById(dto.getCreateBy()).getName();
    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == PurchaseReturnStatus.APPROVE_PASS) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(ReturnDetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class ReturnDetailBo extends BaseBo<PurchaseReturnFullDto.ReturnDetailDto> {

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
     * 外部编号
     */
    @ApiModelProperty("外部编号")
    private String externalCode;

    /**
     * 退货数量
     */
    @ApiModelProperty("退货数量")
    private Integer returnNum;

    /**
     * 采购价
     */
    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    /**
     * 退货金额
     */
    @ApiModelProperty("退货金额")
    private BigDecimal returnAmount;

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
      this.returnAmount = NumberUtil.mul(dto.getReturnNum(), dto.getTaxPrice());

      ProductFeignClient productFeignClient = ApplicationUtil.getBean(ProductFeignClient.class);
      PurchaseProductDto product = productFeignClient.getPurchaseById(dto.getProductId()).getData();

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
    }
  }
}
