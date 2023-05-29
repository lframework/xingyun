package com.lframework.xingyun.core.utils;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SplitNumberUtil {

  /**
   * 将 totalNum 按照权重指标分摊
   *
   * @param totalNum
   * @param datas     key：唯一标识 value：权重指标
   * @param precision 精度
   * @return
   */
  public static Map<Object, Number> split(Number totalNum, Map<Object, Number> datas,
      int precision) {
    if (CollectionUtil.isEmpty(datas)) {
      return null;
    }

    if (precision < 0) {
      throw new DefaultSysException("precision不允许小于0！");
    }
    if (!NumberUtil.isNumberPrecision(totalNum, precision)) {
      throw new DefaultSysException("totalNum的小数位数不允许大于 " + precision + "！");
    }

    Number totalWeight = datas.values().stream().reduce(NumberUtil::add).orElse(BigDecimal.ZERO);
    Map<Object, Number> results = new HashMap<>(datas.size());
    Number remainNum = totalNum;

    int index = 0;
    int dataSize = datas.size();
    for (Entry<Object, Number> entry : datas.entrySet()) {
      Object key = entry.getKey();
      Number val = entry.getValue();
      if (index == dataSize - 1) {
        // 最后一行
        results.put(key, remainNum);
      } else {
        Number curNum = NumberUtil.getNumber(NumberUtil.mul(totalNum,
            NumberUtil.equal(totalWeight, BigDecimal.ZERO) ? dataSize
                : NumberUtil.div(val, totalWeight)), precision);
        remainNum = NumberUtil.sub(remainNum, curNum);
        if (NumberUtil.lt(remainNum, BigDecimal.ZERO)) {
          curNum = NumberUtil.add(remainNum, curNum);
          remainNum = BigDecimal.ZERO;
        }
        results.put(key, curNum);
      }
    }

    return results;
  }
}
