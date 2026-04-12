package com.lframework.xingyun.comp.bo.sw.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.comp.entity.OnlineExcel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 在线Excel GetBo
 * </p>
 */
@Data
public class GetOnlineExcelBo extends BaseBo<OnlineExcel> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改时间
   */
  @Schema(description = "修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public GetOnlineExcelBo() {

  }

  public GetOnlineExcelBo(OnlineExcel dto) {

    super(dto);
  }

}
