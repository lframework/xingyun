package com.lframework.xingyun.export.bo;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.export.entity.ExportTask;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FailExportTaskBo extends BaseBo<ExportTask> {

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
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  /**
   * 错误信息
   */
  @ApiModelProperty("错误信息")
  private String errorMsg;

  public FailExportTaskBo(ExportTask dto) {
    super(dto);
  }
}
