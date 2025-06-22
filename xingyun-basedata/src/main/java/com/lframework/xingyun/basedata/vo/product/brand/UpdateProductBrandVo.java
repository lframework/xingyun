package com.lframework.xingyun.basedata.vo.product.brand;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.UploadUrl;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductBrandVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 简称
   */
  @ApiModelProperty("简称")
  private String shortName;

  /**
   * logo
   */
  @ApiModelProperty("logo")
  @UploadUrl(message = "logo文件格式有误！")
  private String logo;

  /**
   * 简介
   */
  @ApiModelProperty("简介")
  private String introduction;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "状态不能为空！")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
