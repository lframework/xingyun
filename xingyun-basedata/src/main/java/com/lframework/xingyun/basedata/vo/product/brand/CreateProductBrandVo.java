package com.lframework.xingyun.basedata.vo.product.brand;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.UploadUrl;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductBrandVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
