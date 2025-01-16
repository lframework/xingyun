package com.lframework.xingyun.comp.bo.sw.filebox;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.comp.entity.FileBox;
import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * Url
   */
  @ApiModelProperty("Url")
  private String url;

  /**
   * 上传类型
   */
  @ApiModelProperty("上传类型")
  private String contentType;

  @ApiModelProperty("文件类型")
  @EnumConvert
  private Integer fileType;

  /**
   * 文件大小
   */
  @ApiModelProperty("文件大小")
  private String fileSize;

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
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  public GetFileBoxBo() {

  }

  public GetFileBoxBo(FileBox dto) {

    super(dto);
  }

}
