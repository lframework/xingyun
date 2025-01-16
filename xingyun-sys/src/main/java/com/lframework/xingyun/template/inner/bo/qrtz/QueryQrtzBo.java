package com.lframework.xingyun.template.inner.bo.qrtz;

import com.lframework.xingyun.template.inner.dto.qrtz.QrtzDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryQrtzBo extends BaseBo<QrtzDto> {

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 分组
   */
  @ApiModelProperty("分组")
  private String group;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 任务类型
   */
  @ApiModelProperty("任务类型")
  private Integer jobType;

  /**
   * Cron表达式
   */
  @ApiModelProperty("Cron表达式")
  private String cron;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private String state;

  public QueryQrtzBo(QrtzDto dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<QrtzDto> convert(QrtzDto dto) {
    return super.convert(dto, QueryQrtzBo::getState);
  }

  @Override
  protected void afterInit(QrtzDto dto) {
    this.jobType = dto.getJobType().getCode();
    if (dto.getState() != null) {
      this.state = dto.getState().getCode();
    }
  }
}
