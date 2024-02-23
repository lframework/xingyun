package com.lframework.xingyun.template.core.components.permission;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.security.SecurityConstants;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.template.core.annotations.permission.DataPermission;
import com.lframework.xingyun.template.core.annotations.permission.DataPermissions;
import com.lframework.xingyun.template.core.service.SysDataPermissionModelDetailService;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

public class DataPermissionHandlerImpl implements DataPermissionHandler {

    private static final String EMPTY_SQL = "(1 = 1)";

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        try {
            String sqlSegment = this.convertDataPermissionSql(mappedStatementId);
            if (StringUtil.isEmpty(sqlSegment)) {
                return null;
            }
            Expression sqlSegmentExpression = CCJSqlParserUtil.parseCondExpression(sqlSegment);
            if (null != where) {
                return new AndExpression(where, sqlSegmentExpression);
            }
            return sqlSegmentExpression;
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDataPermission(SysDataPermissionDataPermissionType permissionType,
        List<String> keys,
        List<String> values) {

        Map<String, String> dataPermissionMap = null;
        Map<String, String> dataPermissionVar = null;
        try {
            SaSession session = StpUtil.getSession(false);
            if (session == null) {
                return EMPTY_SQL;
            }
            dataPermissionMap = (Map<String, String>) session.get(
                SecurityConstants.DATA_PERMISSION_SQL_MAP);

            dataPermissionVar = (Map<String, String>) session.get(
                SecurityConstants.DATA_PERMISSION_SQL_VAR);
            if (CollectionUtil.isEmpty(dataPermissionMap)) {
                return EMPTY_SQL;
            }
        } catch (SaTokenException e) {
            return EMPTY_SQL;
        }

        Assert.notNull(permissionType);
        Assert.notNull(keys);
        Assert.notNull(values);

        String sqlTemplate = dataPermissionMap.get(permissionType.getCode().toString());
        if (StringUtil.isBlank(sqlTemplate)) {
            return EMPTY_SQL;
        }

        if (keys.size() != values.size()) {
            throw new DefaultSysException("keys和values长度不一致！");
        }

        Map<String, String> sqlParams = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            sqlParams.put(keys.get(i), values.get(i));
        }

        if (CollectionUtil.isNotEmpty(dataPermissionVar)) {
            dataPermissionVar.forEach((k, v) -> {
                sqlParams.put("__var#" + k, v);
            });
        }

        SysDataPermissionModelDetailService sysDataPermissionModelDetailService = ApplicationUtil.getBean(
            SysDataPermissionModelDetailService.class);
        return sysDataPermissionModelDetailService.formatSql(sqlTemplate, sqlParams);
    }

    private String convertDataPermissionSql(String statementId) {
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
        DataPermissions dataPermissions = mapperMethod.getAnnotation(DataPermissions.class);

        if (dataPermissions != null) {
            SysDataPermissionDataPermissionType dataPermissionType = dataPermissions.type();
            DataPermission[] dataPermissionList = dataPermissions.value();
            String dataPermissionSql = this.getDataPermission(dataPermissionType,
                Arrays.stream(dataPermissionList).map(DataPermission::template)
                    .collect(Collectors.toList()),
                Arrays.stream(dataPermissionList).map(DataPermission::alias)
                    .collect(Collectors.toList()));

            return dataPermissionSql;
        }

        return null;
    }

    private String convertMsId(String id) {
        String pageHelperSuffix = "_COUNT";
        if (id.endsWith(pageHelperSuffix)) {
            return id.substring(0, id.length() - pageHelperSuffix.length());
        }

        String mpSuffix = "_mpCount";
        if (id.endsWith(mpSuffix)) {
            return id.substring(0, id.length() - mpSuffix.length());
        }

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
