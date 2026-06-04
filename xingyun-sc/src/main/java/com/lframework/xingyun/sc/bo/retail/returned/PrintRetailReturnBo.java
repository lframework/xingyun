package com.lframework.xingyun.sc.bo.retail.returned;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnFullDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.enums.RetailReturnStatus;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class PrintRetailReturnBo extends BaseBo<RetailReturnFullDto> {

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
   * 客户编号
   */
  @Schema(description = "客户编号")
  private String memberCode;

  /**
   * 客户名称
   */
  @Schema(description = "客户名称")
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
   * 销售出库单号
   */
  @Schema(description = "销售出库单号")
  private String outSheetCode;

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
  private List<ReturnDetailBo> details;

  public PrintRetailReturnBo(RetailReturnFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<RetailReturnFullDto> convert(RetailReturnFullDto dto) {

    return super.convert(dto, PrintRetailReturnBo::getDetails);
  }

  @Override
  protected void afterInit(RetailReturnFullDto dto) {

    this.salerName = StringPool.EMPTY_STR;
    this.paymentDate = StringPool.EMPTY_STR;
    this.outSheetCode = StringPool.EMPTY_STR;
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

    RetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
        RetailOutSheetService.class);
    if (!StringUtil.isBlank(dto.getOutSheetId())) {
      RetailOutSheet outSheet = retailOutSheetService.getById(dto.getOutSheetId());
      this.outSheetCode = outSheet.getCode();
    }

    if (dto.getPaymentDate() != null) {
      this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
    }

    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == RetailReturnStatus.APPROVE_PASS) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(ReturnDetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class ReturnDetailBo extends BaseBo<RetailReturnFullDto.ReturnDetailDto> {

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
     * 退货数量
     */
    @Schema(description = "退货数量")
    private BigDecimal returnNum;

    /**
     * 价格
     */
    @Schema(description = "价格")
    private BigDecimal taxPrice;

    /**
     * 退货金额
     */
    @Schema(description = "退货金额")
    private BigDecimal returnAmount;

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

      this.returnNum = dto.getReturnNum();
      this.taxPrice = dto.getTaxPrice();
      this.returnAmount = dto.getTaxAmount();
    }
  }
}
