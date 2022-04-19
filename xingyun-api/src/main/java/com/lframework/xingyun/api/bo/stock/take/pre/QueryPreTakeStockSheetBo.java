package com.lframework.xingyun.api.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.storecenter.StoreCenterDto;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.sc.dto.stock.take.pre.PreTakeStockSheetDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 预先盘点单 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryPreTakeStockSheetBo extends BaseBo<PreTakeStockSheetDto> {

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
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  private Integer takeStatus;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public QueryPreTakeStockSheetBo() {

  }

  public QueryPreTakeStockSheetBo(PreTakeStockSheetDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PreTakeStockSheetDto> convert(PreTakeStockSheetDto dto) {

    return super.convert(dto, QueryPreTakeStockSheetBo::getTakeStatus);
  }

  @Override
  protected void afterInit(PreTakeStockSheetDto dto) {

    this.takeStatus = dto.getTakeStatus().getCode();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    this.updateBy = userService.getById(dto.getUpdateBy()).getName();

    IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
    StoreCenterDto sc = storeCenterService.getById(dto.getScId());
    this.scCode = sc.getCode();
    this.scName = sc.getName();
  }
}
