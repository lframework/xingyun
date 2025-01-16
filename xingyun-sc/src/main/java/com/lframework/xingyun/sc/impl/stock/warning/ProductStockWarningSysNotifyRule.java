package com.lframework.xingyun.sc.impl.stock.warning;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.components.notify.SysNotifyRuleEmail;
import com.lframework.xingyun.core.components.notify.SysNotifyRuleSys;
import com.lframework.xingyun.core.dto.notify.SysNotifyParamsDto;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ProductStockWarningSysNotifyRule implements SysNotifyRuleSys,
    SysNotifyRuleEmail {

  public static final Integer BIZ_TYPE = 200;

  @Override
  public String getTitle(SysNotifyParamsDto params) {
    Map<String, String> variables = getVariables(params.getVariables());
    String productCode = variables.get("productCode");
    String productName = variables.get("productName");
    String bizType = variables.get("bizType");
    return StringUtil.format("{}商品编号：{}，商品名称：{}，{}", "【库存预警】", productCode,
        productName, "0".equals(bizType) ? "库存不足" : "库存超限");
  }

  @Override
  public String getContent(SysNotifyParamsDto params) {
    Map<String, String> variables = getVariables(params.getVariables());
    String productCode = variables.get("productCode");
    String productName = variables.get("productName");
    String currentStock = variables.get("currentStock");
    String bizType = variables.get("bizType");
    String minLimit = variables.get("minLimit");
    String maxLimit = variables.get("maxLimit");

    return StringUtil.format(
        "商品编号：{}，商品名称：{}，当前库存：{}，{}：{}，{}",
        productCode, productName,
        currentStock, "0".equals(bizType) ? "已达到当前预警下限" : "已达到当前预警上限",
        "0".equals(bizType) ? minLimit : maxLimit, "0".equals(bizType) ? "请尽快补货" : "请注意");
  }

  @Override
  public boolean match(Integer bizType) {
    return BIZ_TYPE.equals(bizType);
  }

  private Map<String, String> getVariables(Object vars) {
    return JsonUtil.parseMap(String.valueOf(vars), String.class, String.class);
  }
}
