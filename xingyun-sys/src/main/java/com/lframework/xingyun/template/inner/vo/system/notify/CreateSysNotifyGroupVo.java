package com.lframework.xingyun.template.inner.vo.system.notify;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.template.inner.enums.system.SysNotifyReceiverType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysNotifyGroupVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 接收者类型
   */
  @ApiModelProperty(value = "接收者类型", required = true)
  @NotNull(message = "接收者类型不能为空！")
  @IsEnum(enumClass = SysNotifyReceiverType.class, message = "接收者类型格式不正确！")
  private Integer receiverType;

  /**
   * 接收者ID
   */
  @ApiModelProperty(value = "接收者ID", required = true)
  @NotEmpty(message = "接收者ID不能为空！")
  private List<String> receiverIds;

  /**
   * 消息类型
   */
  @ApiModelProperty(value = "消息类型", required = true)
  @NotEmpty(message = "消息类型不能为空！")
  private List<Integer> messageType;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
