package com.lframework.xingyun.core.aop;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.config.properties.DefaultSettingProperties;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.SpelUtil;
import com.lframework.xingyun.core.annotations.OrderTimeLineLog;
import com.lframework.xingyun.core.entity.OrderTimeLine;
import com.lframework.xingyun.core.service.OrderTimeLineService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

/**
 * 单据时间轴切面
 *
 * @author zmj
 */
@Slf4j
@Aspect
@Component
public class OrderTimeLineLogAspect {

  @Autowired
  private DefaultSettingProperties defaultSettingProperties;

  private final ThreadLocal<Integer> POOL = new InheritableThreadLocal<>();

  @Pointcut("@annotation(com.lframework.xingyun.core.annotations.OrderTimeLineLog)")
  public void orderTimeLineLogCutPoint() {

  }

  @Around(value = "orderTimeLineLogCutPoint()")
  public Object orderTimeLineLog(ProceedingJoinPoint joinPoint) throws Throwable {

    AbstractUserDetails currentUser = SecurityUtil.getCurrentUser();

    String curUserId =
        currentUser == null ? defaultSettingProperties.getDefaultUserId() : currentUser.getId();
    String curUserName =
        currentUser == null ? defaultSettingProperties.getDefaultUserName() : currentUser.getName();

    Object value = null;

    try {
      if (POOL.get() != null) {
        POOL.set(POOL.get() + 1);
        // 如果出现嵌套，那么以最外层的为准，不进行嵌套隔离
        return joinPoint.proceed();
      }

      POOL.set(1);

      value = joinPoint.proceed();

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

        // 解析SpEL表达式获取结果
        Object[] params;
        OrderTimeLineLog orderTimeLineLog = methodSignature.getMethod()
            .getAnnotation(OrderTimeLineLog.class);
        if (!ArrayUtil.isEmpty(orderTimeLineLog.params())) {
          params = new Object[orderTimeLineLog.params().length];
          for (int i = 0; i < orderTimeLineLog.params().length; i++) {
            String param = orderTimeLineLog.params()[i];
            Object p = SpelUtil.parse(param, ctx);
            params[i] = p;
          }
        } else {
          params = new String[0];
        }

        List<String[]> paramsList = new ArrayList<>();
        //循环format
        if (orderTimeLineLog.loopFormat() && Arrays.stream(params)
            .anyMatch(t -> t instanceof Collection)) {
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

        Object[] orderIds;
        if (!ArrayUtil.isEmpty(orderTimeLineLog.orderId())) {
          orderIds = new Object[orderTimeLineLog.orderId().length];
          for (int i = 0; i < orderTimeLineLog.orderId().length; i++) {
            String orderId = orderTimeLineLog.orderId()[i];
            Object p = SpelUtil.parse(orderId, ctx);
            orderIds[i] = p;
          }
        } else {
          orderIds = new String[0];
        }

        List<String> orderIdList = new ArrayList<>();
        for (Object orderId : orderIds) {
          if (orderId == null) {
            continue;
          }

          if (orderId instanceof Collection) {
            Collection<Object> c = (Collection<Object>) orderId;
            CollectionUtil.forEach(c, (item, index) -> orderIdList.add(item.toString()));
          }

          orderIdList.add(orderId.toString());
        }

        if (orderTimeLineLog.delete()) {
          for (String orderId : orderIdList) {
            // 这里不异步，在同事务内执行
            OrderTimeLineService orderTimeLineService = ApplicationUtil.getBean(
                OrderTimeLineService.class);
            orderTimeLineService.deleteByOrder(orderId);
            orderTimeLineService.cleanCacheByKey(orderId);
          }
        } else {
          List<OrderTimeLine> records = new ArrayList<>();
          OrderTimeLineService orderTimeLineService = ApplicationUtil.getBean(
              OrderTimeLineService.class);
          for (String[] strArr : paramsList) {
            for (String orderId : orderIdList) {
              OrderTimeLine record = new OrderTimeLine();
              record.setId(IdUtil.getId());
              record.setOrderId(orderId);
              record.setContent(StringUtil.format(orderTimeLineLog.name(), strArr));
              record.setCreateBy(curUserName);
              record.setCreateById(curUserId);
              record.setBizType(orderTimeLineLog.type());
              records.add(record);
            }
          }
          if (CollectionUtil.isNotEmpty(records)) {
            // 这里不异步，在同事务内执行
            orderTimeLineService.saveBatch(records);
            orderTimeLineService.cleanCacheByKeys(orderIdList);
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    } finally {
      if (POOL.get() != null) {
        POOL.set(POOL.get() - 1);
        if (POOL.get() <= 0) {
          POOL.remove();
        }
      }
    }
    return value;
  }
}
