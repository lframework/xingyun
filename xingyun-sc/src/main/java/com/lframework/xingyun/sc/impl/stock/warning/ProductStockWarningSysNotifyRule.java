package com.lframework.xingyun.sc.impl.stock.warning;

import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.web.core.components.notify.SysNotifyRuleEmail;
import com.lframework.starter.web.core.components.notify.SysNotifyRuleSys;
import com.lframework.starter.web.inner.dto.notify.SysNotifyParamsDto;
import java.math.BigDecimal;
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
    BigDecimal minLimit = new BigDecimal(variables.get("minLimit"));
    BigDecimal maxLimit = new BigDecimal(variables.get("maxLimit"));

    return StringUtil.format(
        "商品编号：{}，商品名称：{}，当前库存：{}，{}：{}，{}",
        productCode, productName,
        currentStock, "0".equals(bizType) ? "已达到当前预警下限" : "已达到当前预警上限",
        "0".equals(bizType) ? NumberUtil.getNumber(minLimit, 8) : NumberUtil.getNumber(maxLimit, 8), "0".equals(bizType) ? "请尽快补货" : "请注意");
  }

  @Override
  public boolean match(Integer bizType) {
    return BIZ_TYPE.equals(bizType);
  }

  private Map<String, String> getVariables(Object vars) {
    return JsonUtil.parseMap(String.valueOf(vars), String.class, String.class);
  }
}
