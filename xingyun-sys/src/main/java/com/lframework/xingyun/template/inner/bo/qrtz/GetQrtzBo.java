package com.lframework.xingyun.template.inner.bo.qrtz;

import com.lframework.xingyun.template.inner.dto.qrtz.QrtzDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class GetQrtzBo extends BaseBo<QrtzDto> {

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
   * 租户ID
   */
  @ApiModelProperty("租户ID")
  private Integer tenantId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 执行类名
   */
  @ApiModelProperty("执行类名")
  private String targetClassName;

  /**
   * 执行方法名
   */
  @ApiModelProperty("执行方法名")
  private String targetMethodName;

  /**
   * 执行参数类型
   */
  @ApiModelProperty("执行参数类型")
  private List<String> targetParamTypes;

  /**
   * 执行参数
   */
  @ApiModelProperty("执行参数")
  private List<String> targetParams;

  /**
   * 脚本
   */
  @ApiModelProperty("脚本")
  private String script;

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

  public GetQrtzBo(QrtzDto dto) {
    super(dto);
  }

  @Override
  public <A> BaseBo<QrtzDto> convert(QrtzDto dto) {
    return super.convert(dto, GetQrtzBo::getState);
  }

  @Override
  protected void afterInit(QrtzDto dto) {
    this.jobType = dto.getJobType().getCode();
    if (dto.getState() != null) {
      this.state = dto.getState().getCode();
    }
  }
}
