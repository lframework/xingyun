package com.lframework.xingyun.basedata.bo.print;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.PrintTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryPrintTemplateBo extends BaseBo<PrintTemplate> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private Integer id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  @Schema(description = "修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @Schema(description = "修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QueryPrintTemplateBo() {

  }

  public QueryPrintTemplateBo(PrintTemplate dto) {

    super(dto);
  }
}
