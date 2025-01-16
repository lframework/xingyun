package com.lframework.xingyun.template.inner.bo.system.oplog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.core.entity.OpLogs;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GetOpLogBo extends BaseBo<OpLogs> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 日志名称
   */
  @ApiModelProperty("日志名称")
  private String name;

  /**
   * 类别
   */
  @ApiModelProperty("类别")
  private Integer logType;

  /**
   * IP地址
   */
  @ApiModelProperty("IP地址")
  private String ip;

  /**
   * 补充信息
   */
  @ApiModelProperty("补充信息")
  private String extra;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public GetOpLogBo() {

  }

  public GetOpLogBo(OpLogs dto) {

    super(dto);
  }
}
