package com.lframework.xingyun.comp.bo.sw.filebox;

import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.comp.entity.FileBox;
import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "ID")
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  @Schema(description = "文件大小")
  private String fileSize;

  @Schema(description = "文件类型")
  @EnumConvert
  private Integer fileType;

  @Schema(description = "文件路径")
  private String filePath;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  public QueryFileBoxBo() {

  }

  public QueryFileBoxBo(FileBox dto) {

    super(dto);
  }

}
