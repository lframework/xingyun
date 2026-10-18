package com.lframework.xingyun.sc.vo.permission;

import com.lframework.starter.web.inner.vo.system.role.SysRoleMenuSettingVo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** 菜单和订单范围一起授权；范围为null时保留该角色原设置。 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorizeOrderRoleVo extends SysRoleMenuSettingVo {

  @Min(value = 0, message = "采购订单查看范围无效！")
  @Max(value = 3, message = "采购订单查看范围无效！")
  private Integer purchaseScope;

  @Min(value = 0, message = "销售订单查看范围无效！")
  @Max(value = 3, message = "销售订单查看范围无效！")
  private Integer saleScope;
}
