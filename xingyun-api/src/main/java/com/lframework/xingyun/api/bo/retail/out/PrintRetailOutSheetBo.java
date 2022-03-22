package com.lframework.xingyun.api.bo.retail.out;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.DateUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.enums.RetailOutSheetStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrintRetailOutSheetBo extends BasePrintDataBo<RetailOutSheetFullDto> {

  /**
   * 单号
   */
  private String code;

  /**
   * 仓库编号
   */
  private String scCode;

  /**
   * 仓库名称
   */
  private String scName;

  /**
   * 会员编号
   */
  private String memberCode;

  /**
   * 会员名称
   */
  private String memberName;

  /**
   * 销售员姓名
   */
  private String salerName;

  /**
   * 付款日期
   */
  private String paymentDate;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人
   */
  private String createBy;

  /**
   * 创建时间
   */
  private String createTime;

  /**
   * 审核人
   */
  private String approveBy;

  /**
   * 审核时间
   */
  private String approveTime;

  /**
   * 订单明细
   */
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
    this.description = StringPool.EMPTY_STR;
    this.approveBy = StringPool.EMPTY_STR;
    this.approveTime = StringPool.EMPTY_STR;

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    IMemberService memberService = ApplicationUtil.getBean(IMemberService.class);
    MemberDto member = memberService.getById(dto.getMemberId());
    this.memberCode = member.getCode();
    this.memberName = member.getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    if (!StringUtil.isBlank(dto.getSalerId())) {
      this.salerName = userService.getById(dto.getSalerId()).getName();
    }

    if (dto.getPaymentDate() != null) {
      this.paymentDate = DateUtil.formatDate(dto.getPaymentDate());
    }

    this.createBy = userService.getById(dto.getCreateBy()).getName();
    this.createTime = DateUtil.formatDateTime(dto.getCreateTime());

    if (!StringUtil.isBlank(dto.getApproveBy())
        && dto.getStatus() == RetailOutSheetStatus.APPROVE_PASS) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
      this.approveTime = DateUtil.formatDateTime(dto.getApproveTime());
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(OrderDetailBo::new)
          .collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class OrderDetailBo extends BaseBo<RetailOutSheetFullDto.SheetDetailDto> {

    /**
     * 商品编号
     */
    private String productCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * SKU编号
     */
    private String skuCode;

    /**
     * 外部编号
     */
    private String externalCode;

    /**
     * 出库数量
     */
    private Integer outNum;

    /**
     * 价格
     */
    private BigDecimal taxPrice;

    /**
     * 出库金额
     */
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
      this.outAmount = NumberUtil.mul(dto.getTaxPrice(), dto.getOrderNum());

      IProductService productService = ApplicationUtil.getBean(IProductService.class);
      RetailProductDto product = productService.getRetailById(dto.getProductId());

      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
    }
  }
}
