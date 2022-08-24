package com.lframework.xingyun.settle.api.bo.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.settle.biz.service.ISettleOutItemService;
import com.lframework.xingyun.settle.facade.dto.pre.SettlePreSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.SettleOutItem;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSettlePreSheetBo extends BaseBo<SettlePreSheetFullDto> {

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
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

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
   * 总金额
   */
  @ApiModelProperty("总金额")
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
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

  private List<SheetDetailBo> details;

  public GetSettlePreSheetBo() {

  }

  public GetSettlePreSheetBo(SettlePreSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<SettlePreSheetFullDto> convert(SettlePreSheetFullDto dto) {

    return super.convert(dto, GetSettlePreSheetBo::getStatus, GetSettlePreSheetBo::getDetails);
  }

  @Override
  protected void afterInit(SettlePreSheetFullDto dto) {

    SupplierFeignClient supplierFeignClient = ApplicationUtil.getBean(SupplierFeignClient.class);
    Supplier supplier = supplierFeignClient.findById(dto.getSupplierId()).getData();
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    this.status = dto.getStatus().getCode();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto createBy = userService.findById(dto.getCreateBy());
    this.createBy = createBy.getName();

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(SheetDetailBo::new).collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class SheetDetailBo extends BaseBo<SettlePreSheetFullDto.SheetDetailDto> {

    /**
     * 明细ID
     */
    @ApiModelProperty("明细ID")
    private String id;

    /**
     * 项目ID
     */
    @ApiModelProperty("项目ID")
    private String itemId;

    /**
     * 项目名称
     */
    @ApiModelProperty("项目名称")
    private String itemName;

    /**
     * 金额
     */
    @ApiModelProperty("金额")
    private BigDecimal amount;

    public SheetDetailBo() {

    }

    public SheetDetailBo(SettlePreSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    protected void afterInit(SettlePreSheetFullDto.SheetDetailDto dto) {

      ISettleOutItemService settleOutItemService = ApplicationUtil.getBean(
          ISettleOutItemService.class);
      SettleOutItem item = settleOutItemService.findById(dto.getItemId());
      this.itemName = item.getName();
    }
  }
}
