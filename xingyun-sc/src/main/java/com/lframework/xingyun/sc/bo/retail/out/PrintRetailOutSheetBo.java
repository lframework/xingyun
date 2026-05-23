package com.lframework.xingyun.sc.bo.retail.out;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.retail.RetailProductDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.enums.RetailOutSheetStatus;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PrintRetailOutSheetBo extends BaseBo<RetailOutSheetFullDto> {

  /**
   * 单号
   */
  @Schema(description = "单号")
  private String code;

  /**
   * 仓库编号
   */
  @Schema(description = "仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @Schema(description = "仓库名称")
  private String scName;

  /**
   * 会员编号
   */
  @Schema(description = "会员编号")
  private String memberCode;

  /**
   * 会员名称
   */
  @Schema(description = "会员名称")
  private String memberName;

  /**
   * 销售员姓名
   */
  @Schema(description = "销售员姓名")
  private String salerName;

  /**
   * 付款日期
   */
  @Schema(description = "付款日期")
  private String paymentDate;

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
  private String createTime;

  /**
   * 审核人
   */
  @Schema(description = "审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @Schema(description = "审核时间")
  private String approveTime;

  /**
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<OrderDetailBo> details;

  public PrintRetailOutSheetBo() {

  }

  public PrintRetailOutSheetBo(RetailOutSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<RetailOutSheetFullDto> convert(RetailOutSheetFullDto dto) {

    return super.convert(dto, PrintRetailOutSheetBo::getDetails);
  }

  @Override
  protected void afterInit(RetailOutSheetFullDto dto) {

    this.salerName = StringPool.EMPTY_STR;
    this.paymentDate = StringPool.EMPTY_STR;
    this.approveBy = StringPool.EMPTY_STR;
    this.approveTime = StringPool.EMPTY_STR;

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    if (!StringUtil.isBlank(dto.getMemberId())) {
      MemberService memberService = ApplicationUtil.getBean(MemberService.class);
      Member member = memberService.findById(dto.getMemberId());
      this.memberCode = member.getCode();
      this.memberName = member.getName();
    }

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getSalerId())) {
      this.salerName = userService.findById(dto.getSalerId()).getName();
    }

    if (dto.getPaymentDate() != null) {
      this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
    }

    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(OrderDetailBo::new).collect(Collectors.toList());
    }
  }

  @Data
  public static class OrderDetailBo extends BaseBo<RetailOutSheetFullDto.SheetDetailDto> {

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
     * 出库数量
     */
    @Schema(description = "出库数量")
    private BigDecimal outNum;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal taxPrice;

    /**
     * 出库金额
     */
    @Schema(description = "出库金额")
    private BigDecimal outAmount;

    public OrderDetailBo(RetailOutSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<RetailOutSheetFullDto.SheetDetailDto> convert(
        RetailOutSheetFullDto.SheetDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(RetailOutSheetFullDto.SheetDetailDto dto) {

      this.outNum = dto.getOrderNum();
      this.taxPrice = dto.getTaxPrice();
      this.outAmount = dto.getTaxAmount();

      RetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
          RetailOutSheetService.class);
      RetailProductDto product = retailOutSheetService.getRetailById(dto.getSkuId());

      this.productCode = product.getCode();
      this.skuCode = product.getSkuCode();
      this.salePropertyText = product.getSalePropertyText();
      this.productName = product.getName();
    }
  }
}
