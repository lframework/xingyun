package com.lframework.xingyun.sc.bo.retail.out;

import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class RetailOutSheetWithReturnBo extends BaseBo<RetailOutSheetWithReturnDto> {

  /**
   * 订单ID
   */
  @Schema(description = "订单ID")
  private String id;

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
   * 会员ID
   */
  @Schema(description = "会员ID")
  private String memberId;

  /**
   * 会员名称
   */
  @Schema(description = "会员名称")
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
   * 订单明细
   */
  @Schema(description = "订单明细")
  private List<DetailBo> details;

  public RetailOutSheetWithReturnBo() {

  }

  public RetailOutSheetWithReturnBo(RetailOutSheetWithReturnDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<RetailOutSheetWithReturnDto> convert(RetailOutSheetWithReturnDto dto) {

    return super.convert(dto, RetailOutSheetWithReturnBo::getSalerId,
        RetailOutSheetWithReturnBo::getDetails);
  }

  @Override
  protected void afterInit(RetailOutSheetWithReturnDto dto) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId());
    this.scName = sc.getName();

    if (!StringUtil.isBlank(dto.getMemberId())) {
      MemberService memberService = ApplicationUtil.getBean(MemberService.class);
      Member member = memberService.findById(dto.getMemberId());
      this.memberName = member.getName();
    }

    if (!StringUtil.isBlank(dto.getSalerId())) {
      SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
      SysUser saler = userService.findById(dto.getSalerId());

      this.salerId = saler.getId();
      this.salerName = saler.getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(DetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  public static class DetailBo extends BaseBo<RetailOutSheetWithReturnDto.SheetDetailDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
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
     * 库存数量
     */
    @Schema(description = "库存数量")
    private BigDecimal stockNum;

    /**
     * 剩余退货数量
     */
    @Schema(description = "剩余退货数量")
    private BigDecimal remainNum;

    /**
     * 是否赠品
     */
    @Schema(description = "是否赠品")
    private Boolean isGift;

    /**
     * 税率（%）
     */
    @Schema(description = "税率（%）")
    private BigDecimal taxRate;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    public DetailBo(RetailOutSheetWithReturnDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<RetailOutSheetWithReturnDto.SheetDetailDto> convert(
        RetailOutSheetWithReturnDto.SheetDetailDto dto) {

      return this;
    }

    @Override
    public <A> BaseBo<RetailOutSheetWithReturnDto.SheetDetailDto> convert(
        RetailOutSheetWithReturnDto.SheetDetailDto dto, SFunction<A, ?>... columns) {

      return this;
    }

    @Override
    protected void afterInit(RetailOutSheetWithReturnDto.SheetDetailDto dto) {

      this.outNum = dto.getOrderNum();
      this.retailPrice = dto.getOriPrice();

      BigDecimal returnNum = dto.getReturnNum() == null ? BigDecimal.ZERO : dto.getReturnNum();
      this.remainNum = NumberUtil.sub(dto.getOrderNum(), returnNum);
      this.stockNum = dto.getStockNum() == null ? BigDecimal.ZERO : dto.getStockNum();
    }
  }
}
