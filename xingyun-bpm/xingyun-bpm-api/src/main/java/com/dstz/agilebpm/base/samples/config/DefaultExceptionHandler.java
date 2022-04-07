package com.dstz.agilebpm.base.samples.config;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.response.impl.ResultMsg;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处理 没有@cathErr 的控制器
 *
 * @author jeff
 */
public class DefaultExceptionHandler implements HandlerExceptionResolver {

  private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
      Object handler,
      Exception ex) {
    ModelAndView mv = new ModelAndView();
    /* 使用response返回 */
    response.setStatus(HttpStatus.OK.value()); // 设置状态码
    response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
    response.setCharacterEncoding("UTF-8"); // 避免乱码
    response.setHeader("Cache-Control", "no-cache, must-revalidate");
    try {
      response.getWriter().write(
          JSONObject.toJSONString(new ResultMsg<>(BaseStatusCode.SYSTEM_ERROR, ex.getMessage())));
    } catch (IOException e) {
      log.error("与客户端通讯异常:" + e.getMessage(), e);
    }

    log.debug("异常:" + ex.getMessage(), ex);
    return mv;
  }

}
