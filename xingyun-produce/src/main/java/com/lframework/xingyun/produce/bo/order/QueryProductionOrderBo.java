package com.lframework.xingyun.produce.bo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.produce.entity.ProductionOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductionOrderBo extends BaseBo<ProductionOrder> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "工单编号")
  private String code;

  @Schema(description = "成品名称")
  private String productName;

  @Schema(description = "计划生产数量")
  private BigDecimal planNum;

  @Schema(description = "实际生产数量")
  private BigDecimal actualNum;

  @Schema(description = "计划开始日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate planStartDate;

  @Schema(description = "计划完工日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate planEndDate;

  @Schema(description = "实际完工日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate actualEndDate;

  @Schema(description = "负责人姓名")
  private String ownerName;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "备注")
  private String description;

  @Schema(description = "创建人")
  private String createBy;

  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QueryProductionOrderBo(ProductionOrder dto) {
    super(dto);
  }

  @Override
  public BaseBo<ProductionOrder> convert(ProductionOrder dto) {
    return super.convert(dto, QueryProductionOrderBo::getStatus);
  }

  @Override
  protected void afterInit(ProductionOrder dto) {
    if (!StringUtil.isBlank(dto.getProductId())) {
      ProductService productService = ApplicationUtil.getBean(ProductService.class);
      Product product = productService.findById(dto.getProductId());
      if (product != null) {
        this.productName = product.getName();
      }
    }
    if (!StringUtil.isBlank(dto.getOwnerId())) {
      SysUserService userService = ApplicationUtil.getBean(SysUserService.class);
      this.ownerName = userService.findById(dto.getOwnerId()).getName();
    }
    this.status = dto.getStatus().getCode();
  }
}
