package com.lframework.xingyun.template.inner.components.qrtz;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.xingyun.core.components.qrtz.QrtzJob;
import com.lframework.xingyun.template.inner.enums.QrtzJobType;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.GroovyUtil;
import java.lang.reflect.Method;
import java.util.List;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

/**
 * @author zmj
 * @since 2022/8/20
 */
public class DynamicQrtzJob extends QrtzJob {

  @Override
  protected void onExecute(JobExecutionContext context) throws Exception {

    JobDetail jobDetail = context.getJobDetail();
    JobDataMap jobDataMap = jobDetail.getJobDataMap();
    QrtzJobType jobType = EnumUtil.getByCode(QrtzJobType.class, jobDataMap.getInt("jobType"));
    if (jobType == QrtzJobType.EXCUTE_CLASS) {
      String targetClassName = jobDataMap.getString("targetClassName");
      String targetMethodName = jobDataMap.getString("targetMethodName");
      List<String> targetParamTypes = (List<String>) jobDataMap.get("targetParamTypes");
      List<String> targetParams = (List<String>) jobDataMap.get("targetParams");

      Class clazz = Class.forName(targetClassName);
      Object target = ReflectUtil.newInstance(clazz);
      Class[] paramTypes = null;
      if (!CollectionUtil.isEmpty(targetParamTypes)) {
        paramTypes = new Class[targetParamTypes.size()];
        for (int i = 0; i < targetParamTypes.size(); i++) {
          String targetParamType = targetParamTypes.get(i);
          paramTypes[i] = Class.forName(targetParamType);
        }
      }
      Object[] params = null;
      if (!CollectionUtil.isEmpty(targetParams)) {
        params = new Object[targetParams.size()];
        for (int i = 0; i < targetParams.size(); i++) {
          String targetParamType = targetParams.get(i);
          params[i] = targetParamType;
        }
      }

      Method method = ReflectUtil.getMethod(clazz, targetMethodName, paramTypes);
      ReflectUtil.invoke(target, method, params);
    } else {
      String script = jobDataMap.getString("script");
      GroovyUtil.excuteScript(script);
    }
  }
}
