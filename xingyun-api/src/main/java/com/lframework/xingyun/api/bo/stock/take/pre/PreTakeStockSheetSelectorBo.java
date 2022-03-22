package com.lframework.xingyun.api.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreTakeStockSheetSelectorBo extends BaseBo<PreTakeStockSheetSelectorDto> {

  /**
   * ID
   */
  private String id;

  /**
   * 业务单据号
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
   * 盘点状态
   */
  private Integer takeStatus;

  /**
   * 修改时间
   */
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public PreTakeStockSheetSelectorBo(PreTakeStockSheetSelectorDto dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<PreTakeStockSheetSelectorDto> convert(PreTakeStockSheetSelectorDto dto) {
    return super.convert(dto, PreTakeStockSheetSelectorBo::getTakeStatus);
  }

  @Override
  protected void afterInit(PreTakeStockSheetSelectorDto dto) {
    this.takeStatus = dto.getTakeStatus().getCode();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();
  }
}
