package com.lframework.xingyun.template.inner.bo.system.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.xingyun.template.inner.entity.SysNotice;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 系统通知 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysNoticeBo extends BaseBo<SysNotice> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 是否发布
   */
  @ApiModelProperty("是否发布")
  private Boolean published;

  /**
   * 发布时间
   */
  @ApiModelProperty("发布时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime publishTime;

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

  /**
   * 已读人数
   */
  @ApiModelProperty("已读人数")
  private Integer readedNum;

  /**
   * 未读人数
   */
  @ApiModelProperty("未读人数")
  private Integer unReadNum;

  public QuerySysNoticeBo() {

  }

  public QuerySysNoticeBo(SysNotice dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysNotice dto) {
  }
}
