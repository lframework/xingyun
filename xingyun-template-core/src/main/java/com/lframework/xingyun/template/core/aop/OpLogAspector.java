package com.lframework.xingyun.template.core.aop;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.core.vo.CreateOpLogsVo;
import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.SpelUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

/**
 * OpLog切面
 *
 * @author zmj
 */
@Slf4j
@Aspect
@Component
@ConditionalOnProperty(value = "op-logs.enabled", matchIfMissing = true)
public class OpLogAspector {

  @Pointcut("@annotation(com.lframework.xingyun.template.core.annotations.OpLog)")
  public void opLogCutPoint() {

  }

  @Around(value = "opLogCutPoint()")
  public Object opLog(ProceedingJoinPoint joinPoint) throws Throwable {

    try {
      //生成logId，多层嵌套时，隔离不同bean的数据
      String logId = IdUtil.getUUID();
      OpLogUtil.init(logId);

      AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

      Object value = joinPoint.proceed();

      if (currentUser != null) {
        try {
          //获取方法的参数名和参数值
          MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
          List<String> paramNameList = Arrays.asList(methodSignature.getParameterNames());
          List<Object> paramList = Arrays.asList(joinPoint.getArgs());

          //将方法的参数名和参数值一一对应的放入上下文中
          EvaluationContext ctx = SpelUtil.buildContext();
          for (int i = 0; i < paramNameList.size(); i++) {
            ctx.setVariable(paramNameList.get(i), paramList.get(i));
          }

          //将返回值放入上下文中
          ctx.setVariable("_result", value);

          Map<String, Object> variables = OpLogUtil.getVariables();
          if (!CollectionUtil.isEmpty(variables)) {
            variables.forEach((k, v) -> {
              ctx.setVariable(k, v);
            });
          }

          // 解析SpEL表达式获取结果
          Object[] params;
          OpLog opLog = methodSignature.getMethod().getAnnotation(OpLog.class);
          if (!ArrayUtil.isEmpty(opLog.params())) {
            params = new Object[opLog.params().length];
            for (int i = 0; i < opLog.params().length; i++) {
              String param = opLog.params()[i];
              Object p = SpelUtil.parse(param, ctx);
              params[i] = p;
            }
          } else {
            params = new String[0];
          }

          List<String[]> paramsList = new ArrayList<>();
          //循环format
          if (opLog.loopFormat() && Arrays.stream(params).anyMatch(t -> t instanceof Collection)) {
            String[] strParams = new String[params.length];
            //collectionIndex的索引
            List<Integer> collectionIndexes = new ArrayList<>();
            for (int i = 0; i < params.length; i++) {
              //先处理不是Collection的元素
              if (params[i] instanceof Collection) {
                collectionIndexes.add(i);
                continue;
              }
              strParams[i] = params[i] == null ? null : params[i].toString();
            }

            paramsList.add(strParams);

            if (!CollectionUtil.isEmpty(collectionIndexes)) {
              //将所有的collection组合，例：collection1的size是2 collection2的size是3 则组合后的条数为2*3=6
              for (Integer collectionIndex : collectionIndexes) {
                List<String[]> tmpParamsList = new ArrayList<>();
                for (String[] paramsArr : paramsList) {

                  Collection collection = (Collection) params[collectionIndex];
                  for (Object o : collection) {
                    String[] tmp = new String[paramsArr.length];
                    for (int j = 0; j < paramsArr.length; j++) {
                      if (j == collectionIndex) {
                        tmp[j] = o == null ? null : o.toString();
                      } else {
                        tmp[j] = paramsArr[j];
                      }
                    }

                    tmpParamsList.add(tmp);
                  }
                }

                paramsList.clear();
                paramsList.addAll(tmpParamsList);
              }
            } else {
              paramsList.add(strParams);
            }
          } else {
            String[] strParams = new String[params.length];
            for (int i = 0; i < params.length; i++) {
              strParams[i] = params[i] == null ? null : params[i].toString();
            }
            paramsList.add(strParams);
          }

          List<CreateOpLogsVo> createOpLogsVoList = new ArrayList<>();
          for (String[] strArr : paramsList) {
            String extra = OpLogUtil.getExtra();
            if (extra == null) {
              if (opLog.autoSaveParams()) {
                // 没有手动指定extra
                if (CollectionUtil.isNotEmpty(paramNameList)) {
                  if (paramNameList.size() == 1) {
                    // 只有一个参数
                    OpLogUtil.setExtra(ctx.lookupVariable(paramNameList.get(0)));
                  } else {
                    // 多个参数
                    Map<String, Object> paramMap = new LinkedHashMap<>(paramNameList.size(), 1);
                    for (String paramName : paramNameList) {
                      paramMap.put(paramName, ctx.lookupVariable(paramName));
                    }
                    OpLogUtil.setExtra(paramMap);
                  }
                }
                extra = OpLogUtil.getExtra();
              }
            }

            String finalExtra = extra;
            CreateOpLogsVo vo = new CreateOpLogsVo();
            vo.setName(StringUtil.format(opLog.name(), strArr));
            vo.setLogType(opLog.type());
            vo.setExtra(finalExtra);
            vo.setIp(currentUser.getIp());
            createOpLogsVoList.add(vo);
          }

          if (CollectionUtil.isNotEmpty(createOpLogsVoList)) {
            OpLogUtil.addLogs(createOpLogsVoList);
          }
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
      return value;
    } finally {
      OpLogUtil.clear();
    }
  }
}
