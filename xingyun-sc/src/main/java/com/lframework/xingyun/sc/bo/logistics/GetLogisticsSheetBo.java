package com.lframework.xingyun.sc.bo.logistics;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Customer;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.customer.CustomerService;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetFullDto;
import com.lframework.xingyun.sc.dto.logistics.LogisticsSheetFullDto.DetailDto;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import com.lframework.xingyun.template.core.dto.UserDto;
import com.lframework.xingyun.template.core.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class GetLogisticsSheetBo extends BaseBo<LogisticsSheetFullDto> {

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
   * 物流单号
   */
  @ApiModelProperty("物流单号")
  private String logisticsNo;

  /**
   * 物流公司ID
   */
  @ApiModelProperty("物流公司ID")
  private String logisticsCompanyId;

  /**
   * 寄件人姓名
   */
  @ApiModelProperty("寄件人姓名")
  private String senderName;

  /**
   * 寄件人联系电话
   */
  @ApiModelProperty("寄件人联系电话")
  private String senderTelephone;

  /**
   * 寄件人省
   */
  @ApiModelProperty("寄件人省")
  private String senderProvinceId;

  /**
   * 寄件人市
   */
  @ApiModelProperty("寄件人市")
  private String senderCityId;

  /**
   * 寄件人区
   */
  @ApiModelProperty("寄件人区")
  private String senderDistrictId;

  /**
   * 寄件人地区
   */
  @ApiModelProperty("寄件人地区")
  private String senderCity;

  /**
   * 寄件人地址
   */
  @ApiModelProperty("寄件人地址")
  private String senderAddress;

  /**
   * 收件人姓名
   */
  @ApiModelProperty("收件人姓名")
  private String receiverName;

  /**
   * 收件人联系电话
   */
  @ApiModelProperty("收件人联系电话")
  private String receiverTelephone;

  /**
   * 收件人省
   */
  @ApiModelProperty("收件人省")
  private String receiverProvinceId;

  /**
   * 收件人市
   */
  @ApiModelProperty("收件人市")
  private String receiverCityId;

  /**
   * 收件人区
   */
  @ApiModelProperty("收件人区")
  private String receiverDistrictId;

  /**
   * 收件人地区
   */
  @ApiModelProperty("收件人地区")
  private String receiverCity;

  /**
   * 收件人地址
   */
  @ApiModelProperty("收件人地址")
  private String receiverAddress;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @EnumConvert
  private Integer status;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 总重量
   */
  @ApiModelProperty("总重量")
  private BigDecimal totalWeight;

  /**
   * 总体积
   */
  @ApiModelProperty("总体积")
  private BigDecimal totalVolume;

  /**
   * 物流费
   */
  @ApiModelProperty("物流费")
  private BigDecimal totalAmount;

  /**
   * 发货人
   */
  @ApiModelProperty("发货人")
  private String deliveryBy;

  /**
   * 发货时间
   */
  @ApiModelProperty("发货时间")
  private LocalDateTime deliveryTime;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;

  /**
   * 订单明细
   */
  @ApiModelProperty("订单明细")
  private List<OrderDetailBo> details;

  public GetLogisticsSheetBo() {

  }

  public GetLogisticsSheetBo(LogisticsSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<LogisticsSheetFullDto> convert(LogisticsSheetFullDto dto) {

    return super.convert(dto, GetLogisticsSheetBo::getDetails);
  }

  @Override
  protected void afterInit(LogisticsSheetFullDto dto) {

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(OrderDetailBo::new)
          .collect(Collectors.toList());
    }

    if (StringUtil.isNotBlank(dto.getDeliveryBy())) {
      UserService userService = ApplicationUtil.getBean(UserService.class);
      UserDto deliveryBy = userService.findById(dto.getDeliveryBy());
      this.deliveryBy = deliveryBy.getName();
    }

    DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
    List<DicCityDto> senderCitys = dicCityService.getChainById(senderDistrictId);
    this.senderCity = senderCitys.stream().map(DicCityDto::getName).collect(Collectors.joining(StringPool.CITY_SPLIT));

    List<DicCityDto> receiverCitys = dicCityService.getChainById(receiverDistrictId);
    this.receiverCity = receiverCitys.stream().map(DicCityDto::getName).collect(Collectors.joining(StringPool.CITY_SPLIT));
  }

  @Data
  public static class OrderDetailBo extends BaseBo<LogisticsSheetFullDto.DetailDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 物流单ID
     */
    @ApiModelProperty("物流单ID")
    private String sheetId;

    /**
     * 业务单据ID
     */
    @ApiModelProperty("业务单据ID")
    private String bizId;

    /**
     * 业务单据号
     */
    @ApiModelProperty("业务单据号")
    private String bizCode;

    /**
     * 业务类型
     */
    @ApiModelProperty("业务类型")
    @EnumConvert
    private Integer bizType;

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
     * 收货人ID
     */
    @ApiModelProperty("收货人ID")
    private String receiverId;

    /**
     * 收货人姓名
     */
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    public OrderDetailBo(LogisticsSheetFullDto.DetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<LogisticsSheetFullDto.DetailDto> convert(LogisticsSheetFullDto.DetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(DetailDto dto) {
      if (dto.getBizType() == LogisticsSheetDetailBizType.SALE_OUT_SHEET) {
        SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(
            SaleOutSheetService.class);
        SaleOutSheet saleOutSheet = saleOutSheetService.getById(dto.getBizId());
        this.bizCode = saleOutSheet.getCode();
        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(saleOutSheet.getScId());
        this.scId = sc.getId();
        this.scName = sc.getName();

        CustomerService customerService = ApplicationUtil.getBean(CustomerService.class);
        Customer customer = customerService.findById(saleOutSheet.getCustomerId());
        this.receiverId = customer.getId();
        this.receiverName = customer.getName();

        this.createBy = saleOutSheet.getCreateBy();
        this.createTime = saleOutSheet.getCreateTime();
      } else if (dto.getBizType() == LogisticsSheetDetailBizType.RETAIL_OUT_SHEET) {
        RetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
            RetailOutSheetService.class);
        RetailOutSheet retailOutSheet = retailOutSheetService.getById(dto.getBizId());
        this.bizCode = retailOutSheet.getCode();
        StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
        StoreCenter sc = storeCenterService.findById(retailOutSheet.getScId());
        this.scId = sc.getId();
        this.scName = sc.getName();

        if (StringUtil.isNotBlank(retailOutSheet.getMemberId())) {
          MemberService memberService = ApplicationUtil.getBean(MemberService.class);
          Member member = memberService.findById(retailOutSheet.getMemberId());
          this.receiverId = member.getId();
          this.receiverName = member.getName();
        }

        this.createBy = retailOutSheet.getCreateBy();
        this.createTime = retailOutSheet.getCreateTime();
      }
    }
  }
}
