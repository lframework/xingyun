package com.lframework.xingyun.export.bo;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.export.entity.ExportTask;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ExportingExportTaskBo extends BaseBo<ExportTask> {

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
   * 总数据条数
   */
  @ApiModelProperty("总数据条数")
  private Long totalCount;

  /**
   * 当前完成数据条数
   */
  @ApiModelProperty("当前完成数据条数")
  private Long curCount;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  public ExportingExportTaskBo(ExportTask dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<ExportTask> convert(ExportTask dto) {
    return super.convert(dto, ExportingExportTaskBo::getStatus);
  }

  @Override
  protected void afterInit(ExportTask dto) {
    this.status = dto.getStatus().getCode();
  }
}
