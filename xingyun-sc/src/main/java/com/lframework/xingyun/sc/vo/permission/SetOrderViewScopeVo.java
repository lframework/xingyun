package com.lframework.xingyun.sc.vo.permission;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetOrderViewScopeVo {

  @NotBlank(message = "角色ID不能为空！")
  private String roleId;

  @NotNull(message = "请选择订单查看范围！")
  @Min(value = 0, message = "订单查看范围无效！")
  @Max(value = 2, message = "订单查看范围无效！")
  private Integer scope;
}
