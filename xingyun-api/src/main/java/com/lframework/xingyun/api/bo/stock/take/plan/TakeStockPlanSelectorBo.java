package com.lframework.xingyun.api.bo.stock.take.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.plan.TakeStockPlanSelectorDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TakeStockPlanSelectorBo extends BaseBo<TakeStockPlanSelectorDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
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
   * 盘点类别
   */
  @ApiModelProperty("盘点类别")
  private Integer takeType;

  /**
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  private Integer takeStatus;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public TakeStockPlanSelectorBo(TakeStockPlanSelectorDto dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockPlanSelectorDto> convert(TakeStockPlanSelectorDto dto) {
    return super
        .convert(dto, TakeStockPlanSelectorBo::getTakeType, TakeStockPlanSelectorBo::getTakeStatus);
  }

  @Override
  protected void afterInit(TakeStockPlanSelectorDto dto) {
    this.takeType = dto.getTakeType().getCode();
    this.takeStatus = dto.getTakeStatus().getCode();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();
  }
}
