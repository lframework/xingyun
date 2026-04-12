package com.lframework.xingyun.comp.bo.sw.filebox;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.comp.entity.FileBox;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 文件收纳箱 GetBo
 * </p>
 */
@Data
public class GetFileBoxBo extends BaseBo<FileBox> {

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
   * 上传记录ID
   */
  @Schema(description = "上传记录ID")
  private String recordId;

  /**
   * 上传类型
   */
  @Schema(description = "上传类型")
  private String contentType;

  @Schema(description = "文件类型")
  @EnumConvert
  private Integer fileType;

  /**
   * 文件大小
   */
  @Schema(description = "文件大小")
  private String fileSize;

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
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  public GetFileBoxBo() {

  }

  public GetFileBoxBo(FileBox dto) {

    super(dto);
  }

}
