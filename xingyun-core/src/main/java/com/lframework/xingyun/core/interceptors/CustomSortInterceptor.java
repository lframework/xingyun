package com.lframework.xingyun.core.interceptors;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.vo.SortPageVo;
import com.lframework.xingyun.core.annotations.sort.Sort;
import com.lframework.xingyun.core.annotations.sort.Sorts;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Slf4j
public class CustomSortInterceptor extends JsqlParserSupport implements InnerInterceptor {

    @Override
    public void beforeQuery(
        Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
        ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (parameter instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) parameter;
            List<SortPageVo> sortPageVos = map.values().stream()
                .filter(t -> t instanceof SortPageVo).map(t -> (SortPageVo) t)
                .collect(Collectors.toList());
            if (CollectionUtil.isEmpty(sortPageVos)) {
                return;
            }
            Map<String, String> sortMap = this.convertSortMap(ms.getId());
            if (CollectionUtil.isEmpty(sortMap)) {
                return;
            }

            StringBuilder sqlBuilder = new StringBuilder("ORDER BY ");

            List<String> orderBySqlElements = sortPageVos.stream()
                .filter(t -> sortMap.containsKey(t.getSortField())).map(
                    t -> sortMap.get(t.getSortField()) + " " + (
                        "asc".equalsIgnoreCase(t.getSortOrder()) ? "ASC" : "DESC")).distinct()
                .collect(
                    Collectors.toList());

            if (CollectionUtil.isEmpty(orderBySqlElements)) {
                return;
            }

            // order by code asc, name desc
            String sqlProvider = sqlBuilder.append(StringUtil.join(",", orderBySqlElements))
                .toString();

            CCJSqlParser parser = CCJSqlParserUtil.newParser(sqlProvider);

            PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);

            String sql = mpBs.sql();
            try {
                Statement statement = CCJSqlParserUtil.parse(sql);
                PlainSelect plainSelect = (PlainSelect) ((Select) statement).getSelectBody();
                plainSelect.setOrderByElements(parser.OrderByElements());
                mpBs.sql(statement.toString());
            } catch (JSQLParserException | ParseException e) {
                log.error("Failed to process, Error SQL: {}", sql);
                log.error(e.getMessage(), e);
            }
        }
    }

    private Map<String, String> convertSortMap(String statementId) {
        // 获取Mapper执行方法上的注解
        Method mapperMethod = null;
        try {
            mapperMethod = this.findMapperMethod(this.convertMsId(statementId));
        } catch (ClassNotFoundException e) {
            return null;
        }
        if (mapperMethod == null) {
            return null;
        }
        Sorts sorts = mapperMethod.getAnnotation(Sorts.class);

        if (sorts != null) {
            Sort[] sortList = sorts.value();
            Map<String, String> sortKeyMap = new HashMap<>(sortList.length);
            for (Sort sort : sortList) {
                if (sort.autoParse()) {
                    sortKeyMap.put(sort.value(), (StringUtil.isEmpty(sort.alias()) ? "" : (sort.alias() + ".")) + StringUtil.toUnderlineCase(sort.value()));
                } else {
                    sortKeyMap.put(sort.value(), StringUtil.isEmpty(sort.alias()) ? "" : sort.alias());
                }
            }

            return sortKeyMap;
        }

        return null;
    }

    private String convertMsId(String id) {
        return id;
    }

    private Method findMapperMethod(String statementId)
        throws ClassNotFoundException {
        int lastDotIndex = statementId.lastIndexOf(".");
        String className = statementId.substring(0, lastDotIndex);
        String methodName = statementId.substring(lastDotIndex + 1);

        Class<?> mapperInterface = Class.forName(className);
        return Arrays.stream(mapperInterface.getDeclaredMethods())
            .filter(method -> method.getName().equals(methodName))
            .findFirst()
            .orElse(null);
    }
}
