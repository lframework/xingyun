package com.lframework.xingyun.template.gen.vo.custom.list;

import com.lframework.xingyun.template.gen.enums.GenCustomListBtnType;
import com.lframework.xingyun.template.gen.enums.GenCustomListBtnViewType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenCustomListHandleColumnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 显示名称
   */
  @ApiModelProperty("显示名称")
  @NotNull(message = "显示名称不能为空！")
  private String name;

  /**
   * 显示类型
   */
  @ApiModelProperty("显示类型")
  @NotNull(message = "显示类型不能为空！")
  @IsEnum(enumClass = GenCustomListBtnViewType.class, message = "显示类型格式错误！")
  private String viewType;

  /**
   * 按钮类型
   */
  @ApiModelProperty("按钮类型")
  @NotNull(message = "按钮类型不能为空！")
  @IsEnum(enumClass = GenCustomListBtnType.class, message = "显示类型格式错误！")
  private Integer btnType;

  /**
   * 按钮配置
   */
  @ApiModelProperty("按钮配置")
  private String btnConfig;

  /**
   * 图标
   */
  @ApiModelProperty("图标")
  private String icon;

  /**
   * 请求参数
   */
  @ApiModelProperty("请求参数")
  private String requestParam;

  /**
   * 宽度
   */
  @ApiModelProperty(value = "宽度", required = true)
  @NotNull(message = "宽度不能为空！")
  @Min(value = 0, message = "宽度必须大于0！")
  private Integer width;
}
