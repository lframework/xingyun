package com.lframework.xingyun.basedata.vo.storecenter;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStoreCenterVo implements BaseVo, Serializable {

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
   * 联系人
   */
  @ApiModelProperty("联系人")
  private String contact;

  /**
   * 联系人手机号码
   */
  @ApiModelProperty("联系人手机号码")
  private String telephone;

  /**
   * 地区ID
   */
  @ApiModelProperty("地区ID")
  private String cityId;

  /**
   * 地址
   */
  @ApiModelProperty("地址")
  private String address;

  /**
   * 仓库人数
   */
  @ApiModelProperty("仓库人数")
  private Integer peopleNum;

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
