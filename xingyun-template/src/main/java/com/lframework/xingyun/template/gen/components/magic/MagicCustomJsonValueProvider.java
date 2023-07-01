package com.lframework.xingyun.template.gen.components.magic;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.RequestEntity;
import org.ssssssss.magicapi.core.interceptor.ResultProvider;
import org.ssssssss.magicapi.modules.db.model.Page;

@Component
public class MagicCustomJsonValueProvider implements ResultProvider {

  /**
   * 定义返回结果，默认返回JsonBean
   */
  @Override
  public Object buildResult(RequestEntity requestEntity, int code, String message, Object data) {
    if (code >= 200 && code <= 299) {
      return InvokeResultBuilder.success(data);
    } else {
      throw new DefaultClientException(message);
    }
  }

  /**
   * 定义分页返回结果，该项会被封装在Json结果内， 此方法可以不覆盖，默认返回PageResult
   */
  @Override
  public Object buildPageResult(RequestEntity requestEntity, Page page, long total,
      List<Map<String, Object>> data) {

    return PageResultUtil.newInstance(page.getOffset() / page.getLimit() + 1, page.getLimit(),
        total, data);
  }
}
