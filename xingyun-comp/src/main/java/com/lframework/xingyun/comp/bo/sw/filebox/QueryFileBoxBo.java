package com.lframework.xingyun.comp.bo.sw.filebox;

import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.comp.entity.FileBox;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 文件收纳箱 QueryBo
 * </p>
 */
@Data
public class QueryFileBoxBo extends BaseBo<FileBox> {

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

  @ApiModelProperty("文件大小")
  private String fileSize;

  @ApiModelProperty("文件类型")
  @EnumConvert
  private Integer fileType;

  @ApiModelProperty("文件路径")
  private String filePath;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  public QueryFileBoxBo() {

  }

  public QueryFileBoxBo(FileBox dto) {

    super(dto);
  }

}
