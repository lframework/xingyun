package com.lframework.xingyun.basedata.vo.shop;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShopVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @NotBlank(message = "请输入编号！")
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
  private BigDecimal lon;

  /**
   * 纬度
   */
  @ApiModelProperty("纬度")
  @TypeMismatch(message = "纬度格式有误！")
  private BigDecimal lat;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

}
