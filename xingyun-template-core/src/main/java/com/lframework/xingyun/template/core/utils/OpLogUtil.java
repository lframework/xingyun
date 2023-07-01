package com.lframework.xingyun.template.core.utils;

import com.lframework.xingyun.template.core.service.OpLogsService;
import com.lframework.xingyun.template.core.vo.CreateOpLogsVo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志Util
 *
 * @author zmj
 */
@Slf4j
public class OpLogUtil {

  private static final ThreadLocal<Map<String, Map<String, Object>>> VARIABLE_POOL = new ThreadLocal<>();

  private static final ThreadLocal<List<String>> LOG_ID_POOL = new ThreadLocal<>();

  private static final ThreadLocal<Map<String, Object>> EXTRA_POOL = new ThreadLocal<>();

  public static void init(String logId) {

    initPool();

    LOG_ID_POOL.get().add(logId);

    VARIABLE_POOL.get().putIfAbsent(logId, new HashMap<>());
  }

  public static void addLog(CreateOpLogsVo vo) {

    try {
      OpLogsService opLogsService = ApplicationUtil.getBean(OpLogsService.class);

      // 这里不异步，需要在同事务内执行
      opLogsService.create(vo);
    } catch (Exception e) {
      // 这里异常不抛出
      log.error(e.getMessage(), e);
    }
  }

  public static void addLogs(Collection<CreateOpLogsVo> list) {

    try {
      OpLogsService opLogsService = ApplicationUtil.getBean(OpLogsService.class);

      // 这里不异步，需要在同事务内执行
      opLogsService.create(list);
    } catch (Exception e) {
      // 这里异常不抛出
      log.error(e.getMessage(), e);
    }
  }

  public static void setVariable(String key, Object value) {

    VARIABLE_POOL.get().get(getCurrentLogId()).put(key, value);
  }

  public static Map<String, Object> getVariables() {

    return VARIABLE_POOL.get().get(getCurrentLogId());
  }

  public static String getExtra() {

    Object value = EXTRA_POOL.get().get(getCurrentLogId());

    return value == null ? null : JsonUtil.toJsonString(value);
  }

  public static void setExtra(Object value) {

    EXTRA_POOL.get().put(getCurrentLogId(), value);
  }

  public static void clear() {

    VARIABLE_POOL.get().remove(getCurrentLogId());
    EXTRA_POOL.get().remove(getCurrentLogId());
    LOG_ID_POOL.get().remove(LOG_ID_POOL.get().size() - 1);
  }

  private static void initPool() {

    if (VARIABLE_POOL.get() == null) {
      VARIABLE_POOL.set(new HashMap<>());
    }

    if (LOG_ID_POOL.get() == null) {
      LOG_ID_POOL.set(new ArrayList<>());
    }

    if (EXTRA_POOL.get() == null) {
      EXTRA_POOL.set(new HashMap<>());
    }
  }

  private static String getCurrentLogId() {

    return LOG_ID_POOL.get().get(LOG_ID_POOL.get().size() - 1);
  }
}
