package com.lframework.xingyun.core.vo;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 查询操作日志Vo
 *
 * @author zmj
 */
@Data
public class QueryOpLogsVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 日志名称
   */
  @ApiModelProperty("日志名称")
  private String name;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
  private String createBy;

  /**
   * 日志类别
   */
  @ApiModelProperty("日志类别")
  private Integer logType;

  /**
   * 创建起始时间
   */
  @ApiModelProperty("创建起始时间")
  private LocalDateTime startTime;

  /**
   * 创建截止时间
   */
  @ApiModelProperty("创建截止时间")
  private LocalDateTime endTime;
}
