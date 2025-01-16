package com.lframework.xingyun.core.components.qrtz;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.web.components.tenant.TenantContextHolder;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务
 */
@Slf4j
@DisallowConcurrentExecution
public abstract class QrtzJob implements Job, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    log.info("开始执行定时任务【{}】", this.getClass().getName());
    long begTime = System.currentTimeMillis();

    try {
      // 这里获取tenantId 如果为空则不设置租户ID
      Integer tenantId = (Integer) context.getJobDetail().getJobDataMap().get(StringPool.TENANT_ID_QRTZ);
      if (tenantId != null) {
        TenantContextHolder.setTenantId(tenantId);
      }
      this.onExecute(context);
      long endTime = System.currentTimeMillis();
      log.info("定时任务【{}】执行完毕，共耗时{}ms", this.getClass().getName(), endTime - begTime);
    } catch (Exception e) {
      if (e instanceof ClientException) {
        log.warn("执行定时任务【{}】失败", this.getClass().getName());
        log.warn(e.getMessage(), e);
      } else {
        log.error("执行定时任务【{}】失败", this.getClass().getName());
        log.error(e.getMessage(), e);
      }
      throw new JobExecutionException(e);
    } finally {
      TenantContextHolder.clearTenantId();
    }
  }

  protected abstract void onExecute(JobExecutionContext context) throws Exception;
}
