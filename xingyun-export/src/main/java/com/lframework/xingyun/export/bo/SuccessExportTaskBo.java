package com.lframework.xingyun.export.bo;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.export.entity.ExportTask;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SuccessExportTaskBo extends BaseBo<ExportTask> {

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
   * 文件大小
   */
  @ApiModelProperty("文件大小")
  private String fileSize;

  /**
   * 上传记录ID
   */
  @ApiModelProperty("上传记录ID")
  private String recordId;

  /**
   * 总数据条数
   */
  @ApiModelProperty("总数据条数")
  private Long totalCount;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  /**
   * 完成时间
   */
  @ApiModelProperty("完成时间")
  private LocalDateTime finishTime;

  public SuccessExportTaskBo(ExportTask dto) {
    super(dto);
  }
}
