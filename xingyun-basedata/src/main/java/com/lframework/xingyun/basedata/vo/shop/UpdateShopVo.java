package com.lframework.xingyun.basedata.vo.shop;

import com.lframework.starter.web.components.validation.IsCode;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateShopVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @NotBlank(message = "请输入编号！")
  @IsCode
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 所属部门ID
   */
  @ApiModelProperty("所属部门ID")
  private String deptId;

  /**
   * 经度
   */
  @ApiModelProperty("经度")
  @TypeMismatch(message = "经度格式有误！")
  private BigDecimal lng;

  /**
   * 纬度
   */
  @ApiModelProperty("纬度")
  @TypeMismatch(message = "纬度格式有误！")
  private BigDecimal lat;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @TypeMismatch(message = "状态格式有误！")
  @NotNull(message = "请选择状态！")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

}
