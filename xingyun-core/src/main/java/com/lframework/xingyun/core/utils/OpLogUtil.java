package com.lframework.xingyun.core.utils;

import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.threads.DefaultRunnable;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.core.service.OpLogsService;
import com.lframework.xingyun.core.vo.CreateOpLogsVo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志Util
 *
 * @author zmj
 */
@Slf4j
public class OpLogUtil {

  private static final ThreadLocal<Map<String, Map<String, Object>>> VARIABLE_POOL = new InheritableThreadLocal<>();

  private static final ThreadLocal<List<String>> LOG_ID_POOL = new InheritableThreadLocal<>();

  private static final ThreadLocal<Map<String, Object>> EXTRA_POOL = new InheritableThreadLocal<>();

  private static final ThreadLocal<List<CreateOpLogsVo>> OP_LOG_POOL = new InheritableThreadLocal<>();

  private static final ExecutorService OP_LOG_EXECUTOR = ThreadUtil.newExecutorByBlockingCoefficient(
      0);

  public static void init(String logId) {

    initPool();

    LOG_ID_POOL.get().add(logId);

    VARIABLE_POOL.get().putIfAbsent(logId, new HashMap<>());
  }

  public static void addLogs(Collection<CreateOpLogsVo> list) {

    try {
      OP_LOG_POOL.get().addAll(list);
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

  public static void submitLog() {
    submitLog(SecurityUtil.getCurrentUser());
  }

  public static void submitLog(AbstractUserDetails currentUser) {
    if (LOG_ID_POOL.get() == null || LOG_ID_POOL.get().size() != 1) {
      return;
    }
    List<CreateOpLogsVo> logs = OP_LOG_POOL.get();
    OpLogsService opLogsService = ApplicationUtil.getBean(OpLogsService.class);
    OP_LOG_EXECUTOR.submit(new DefaultRunnable(() -> {
      if (SecurityUtil.getCurrentUser() != null) {
        opLogsService.create(logs);
      } else {
        if (currentUser != null) {
          try {
            SecurityUtil.setCurrentUser(currentUser);
            opLogsService.create(logs);
          } finally {
            SecurityUtil.removeCurrentUser();
          }
        }
      }
    }));
  }

  public static void clear() {
    boolean allClear = false;
    if (LOG_ID_POOL.get().size() == 1) {
      allClear = true;
    }

    VARIABLE_POOL.get().remove(getCurrentLogId());
    EXTRA_POOL.get().remove(getCurrentLogId());
    LOG_ID_POOL.get().remove(LOG_ID_POOL.get().size() - 1);

    if (allClear) {
      VARIABLE_POOL.remove();
      EXTRA_POOL.remove();
      LOG_ID_POOL.remove();
      OP_LOG_POOL.remove();
    }
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

    if (OP_LOG_POOL.get() == null) {
      OP_LOG_POOL.set(new ArrayList<>());
    }
  }

  private static String getCurrentLogId() {

    return LOG_ID_POOL.get().get(LOG_ID_POOL.get().size() - 1);
  }
}
