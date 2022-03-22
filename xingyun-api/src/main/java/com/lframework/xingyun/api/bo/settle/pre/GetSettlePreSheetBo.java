package com.lframework.xingyun.api.bo.settle.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import com.lframework.xingyun.settle.dto.pre.SettlePreSheetFullDto;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
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
  private String id;

  /**
   * 单号
   */
  private String code;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 供应商编号
   */
  private String supplierCode;

  /**
   * 供应商名称
   */
  private String supplierName;

  /**
   * 总金额
   */
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 审核人
   */
  private String approveBy;

  /**
   * 审核时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  private Integer status;

  /**
   * 拒绝原因
   */
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

    ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
    SupplierDto supplier = supplierService.getById(dto.getSupplierId());
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    this.status = dto.getStatus().getCode();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto createBy = userService.getById(dto.getCreateBy());
    this.createBy = createBy.getName();

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.getById(dto.getApproveBy()).getName();
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
    private String id;

    /**
     * 项目ID
     */
    private String itemId;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 金额
     */
    private BigDecimal amount;

    public SheetDetailBo() {

    }

    public SheetDetailBo(SettlePreSheetFullDto.SheetDetailDto dto) {

      super(dto);
    }

    @Override
    protected void afterInit(SettlePreSheetFullDto.SheetDetailDto dto) {

      ISettleOutItemService settleOutItemService = ApplicationUtil
          .getBean(ISettleOutItemService.class);
      SettleOutItemDto item = settleOutItemService.getById(dto.getItemId());
      this.itemName = item.getName();
    }
  }
}
