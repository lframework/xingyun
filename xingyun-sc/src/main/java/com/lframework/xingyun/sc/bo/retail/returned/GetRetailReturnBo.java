package com.lframework.xingyun.sc.bo.retail.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.bo.paytype.OrderPayTypeBo;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.service.paytype.OrderPayTypeService;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetRetailReturnBo extends BaseBo<RetailReturnFullDto> {

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
   * 客户ID
   */
  @Schema(description = "客户ID")
  private String memberId;

  /**
   * 客户名称
   */
  @Schema(description = "客户名称")
  private String memberName;

  /**
   * 销售员ID
   */
  @Schema(description = "销售员ID")
  private String salerId;

  /**
   * 销售员姓名
   */
  @Schema(description = "销售员姓名")
  private String salerName;

  /**
   * 付款日期
   */
  @Schema(description = "付款日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate paymentDate;

  /**
   * 销售出库单ID
   */
  @Schema(description = "销售出库单ID")
  private String outSheetId;

  /**
   * 销售出库单号
   */
  @Schema(description = "销售出库单号")
  private String outSheetCode;

  /**
   * 退货数量
   */
  @Schema(description = "退货数量")
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  @Schema(description = "赠品数量")
  private BigDecimal giftNum;

  /**
   * 退货金额
   */
  @Schema(description = "退货金额")
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

  public GetRetailReturnBo(RetailReturnFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<RetailReturnFullDto> convert(RetailReturnFullDto dto) {

    return super.convert(dto, GetRetailReturnBo::getStatus, GetRetailReturnBo::getSettleStatus,
        GetRetailReturnBo::getDetails);
  }

  @Override
  protected void afterInit(RetailReturnFullDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    this.scName = storeCenterService.findById(dto.getScId()).getName();

    if (!StringUtil.isBlank(dto.getMemberId())) {
      MemberService memberService = ApplicationUtil.getBean(MemberService.class);
      this.memberName = memberService.findById(dto.getMemberId()).getName();
    }

    SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
    if (!StringUtil.isBlank(dto.getSalerId())) {
      this.salerName = userService.findById(dto.getSalerId()).getName();
    }

    RetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
        RetailOutSheetService.class);
    if (!StringUtil.isBlank(dto.getOutSheetId())) {
      RetailOutSheet outSheet = retailOutSheetService.getById(dto.getOutSheetId());
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
      this.details = dto.getDetails().stream().map(ReturnDetailBo::new)
          .collect(Collectors.toList());
    }

    OrderPayTypeService orderPayTypeService = ApplicationUtil.getBean(OrderPayTypeService.class);
    List<OrderPayType> orderPayTypes = orderPayTypeService.findByOrderId(dto.getId());
    this.payTypes = orderPayTypes.stream().map(OrderPayTypeBo::new).collect(Collectors.toList());
  }

  @Data
  public static class ReturnDetailBo extends BaseBo<RetailReturnFullDto.ReturnDetailDto> {

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
     * 出库数量
     */
    @Schema(description = "出库数量")
    private BigDecimal outNum;

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
     * 原价
     */
    @Schema(description = "原价")
    private BigDecimal retailPrice;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal taxPrice;

    /**
     * 折扣
     */
    @Schema(description = "折扣")
    private BigDecimal discountRate;

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
     * 销售出库单明细ID
     */
    @Schema(description = "销售出库单明细ID")
    private String outSheetDetailId;

    public ReturnDetailBo(RetailReturnFullDto.ReturnDetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<RetailReturnFullDto.ReturnDetailDto> convert(
        RetailReturnFullDto.ReturnDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(RetailReturnFullDto.ReturnDetailDto dto) {

      this.retailPrice = dto.getTaxPrice();

      if (!StringUtil.isBlank(dto.getOutSheetDetailId())) {
        BigDecimal sourceOutNum =
            dto.getSourceOutNum() == null ? BigDecimal.ZERO : dto.getSourceOutNum();
        BigDecimal sourceReturnNum =
            dto.getSourceReturnNum() == null ? BigDecimal.ZERO : dto.getSourceReturnNum();
        this.outNum = sourceOutNum;
        this.remainNum = NumberUtil.sub(sourceOutNum, sourceReturnNum);
      }
    }
  }
}
