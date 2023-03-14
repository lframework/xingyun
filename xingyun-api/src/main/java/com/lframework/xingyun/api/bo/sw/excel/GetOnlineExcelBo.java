package com.lframework.xingyun.api.bo.sw.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.core.entity.OnlineExcel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 在线Excel GetBo
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetOnlineExcelBo extends BaseBo<OnlineExcel> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

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

  public GetOnlineExcelBo() {

  }

  public GetOnlineExcelBo(OnlineExcel dto) {

    super(dto);
  }

}
