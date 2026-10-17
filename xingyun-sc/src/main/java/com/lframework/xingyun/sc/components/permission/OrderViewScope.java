package com.lframework.xingyun.sc.components.permission;

import lombok.AllArgsConstructor;
import lombok.Data;

/** 当前登录人的采购/销售订单查看范围。 */
@Data
@AllArgsConstructor
public class OrderViewScope {

  private int scope;

  private String userId;
}
