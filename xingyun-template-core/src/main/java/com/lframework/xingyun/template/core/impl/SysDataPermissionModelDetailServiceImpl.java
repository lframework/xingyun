package com.lframework.xingyun.template.core.impl;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.template.core.vo.permission.SysDataPermissionModelDetailVo;
import com.lframework.xingyun.template.core.entity.SysDataPermissionModelDetail;
import com.lframework.xingyun.template.core.enums.SysDataPermissionModelDetailCalcType;
import com.lframework.xingyun.template.core.enums.SysDataPermissionModelDetailConditionType;
import com.lframework.xingyun.template.core.enums.SysDataPermissionModelDetailInputType;
import com.lframework.xingyun.template.core.enums.SysDataPermissionModelDetailNodeType;
import com.lframework.xingyun.template.core.mappers.SysDataPermissionModelDetailMapper;
import com.lframework.xingyun.template.core.service.SysDataPermissionModelDetailService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SysDataPermissionModelDetailServiceImpl extends
    BaseMpServiceImpl<SysDataPermissionModelDetailMapper, SysDataPermissionModelDetail> implements
    SysDataPermissionModelDetailService {

  @Override
  public String toSql(List<SysDataPermissionModelDetailVo> models) {
    if (CollectionUtil.isEmpty(models)) {
      return null;
    }

    StringBuilder builder = new StringBuilder();

    for (SysDataPermissionModelDetailVo model : models) {
      SysDataPermissionModelDetailNodeType nodeType = EnumUtil.getByCode(
          SysDataPermissionModelDetailNodeType.class, model.getNodeType());
      if (nodeType == SysDataPermissionModelDetailNodeType.CALC) {
        SysDataPermissionModelDetailCalcType calcType = EnumUtil.getByCode(
            SysDataPermissionModelDetailCalcType.class, model.getCalcType());
        if (CollectionUtil.isNotEmpty(model.getChildren())) {
          builder.append(" (");
          builder.append(this.buildChildrenSql(model.getChildren(), calcType.getOperation()));
          builder.append(") ");
        }
        builder.append("AND ");
      } else {
        throw new DefaultSysException("nodeType有误，请检查！");
      }
    }

    if (builder.length() > 0) {
      builder.setLength(builder.length() - 4);
    }
    return builder.toString();
  }

  @Override
  public String formatSql(String sqlTemplate, Map<String, String> params) {
    return StringUtil.format(sqlTemplate, params);
  }

  private String buildChildrenSql(List<SysDataPermissionModelDetailVo> children, String operation) {
    if (CollectionUtil.isEmpty(children)) {
      return "";
    }

    StringBuilder builder = new StringBuilder();
    for (SysDataPermissionModelDetailVo child : children) {
      SysDataPermissionModelDetailNodeType nodeType = EnumUtil.getByCode(
          SysDataPermissionModelDetailNodeType.class, child.getNodeType());
      if (nodeType == SysDataPermissionModelDetailNodeType.CALC) {
        SysDataPermissionModelDetailCalcType calcType = EnumUtil.getByCode(
            SysDataPermissionModelDetailCalcType.class, child.getCalcType());
        if (CollectionUtil.isNotEmpty(child.getChildren())) {
          builder.append(" (");
          builder.append(this.buildChildrenSql(child.getChildren(), calcType.getOperation()));
          builder.append(")");
          builder.append(operation).append(" ");
        }
      } else {
        SysDataPermissionModelDetailConditionType conditionType = EnumUtil.getByCode(
            SysDataPermissionModelDetailConditionType.class, child.getConditionType());
        SysDataPermissionModelDetail record = this.getById(child.getDetailId());
        builder.append("{").append(record.getTableName()).append("}.")
            .append(record.getColumnName()).append(" ").append(conditionType.getOperation())
            .append(" ").append(this.buildLeft(conditionType))
            .append(this.buildValue(child, record))
            .append(this.buildRight(conditionType)).append(" ").append(operation).append(" ");
      }
    }

    if (builder.length() > 0) {
      builder.setLength(builder.length() - operation.length() - 1);
    }

    return builder.toString();
  }

  private String buildLeft(SysDataPermissionModelDetailConditionType conditionType) {
    if (conditionType == SysDataPermissionModelDetailConditionType.IN
        || conditionType == SysDataPermissionModelDetailConditionType.NOT_IN) {
      return "(";
    } else if (conditionType == SysDataPermissionModelDetailConditionType.LEFT_LIKE) {
      return "CONCAT('%',";
    } else if (conditionType == SysDataPermissionModelDetailConditionType.RIGHT_LIKE) {
      return "CONCAT(";
    } else if (conditionType == SysDataPermissionModelDetailConditionType.AROUND_LIKE) {
      return "CONCAT('%',";
    }

    return "";
  }

  private String buildRight(SysDataPermissionModelDetailConditionType conditionType) {
    if (conditionType == SysDataPermissionModelDetailConditionType.IN
        || conditionType == SysDataPermissionModelDetailConditionType.NOT_IN) {
      return ")";
    } else if (conditionType == SysDataPermissionModelDetailConditionType.LEFT_LIKE) {
      return ")";
    } else if (conditionType == SysDataPermissionModelDetailConditionType.RIGHT_LIKE) {
      return ",'%')";
    } else if (conditionType == SysDataPermissionModelDetailConditionType.AROUND_LIKE) {
      return ",'%')";
    }

    return "";
  }

  private String buildValue(SysDataPermissionModelDetailVo child,
      SysDataPermissionModelDetail record) {
    SysDataPermissionModelDetailConditionType conditionType = EnumUtil.getByCode(
        SysDataPermissionModelDetailConditionType.class, child.getConditionType());
    if (conditionType == SysDataPermissionModelDetailConditionType.IN
        || conditionType == SysDataPermissionModelDetailConditionType.NOT_IN) {
      if (record.getInputType() == SysDataPermissionModelDetailInputType.SQL) {
        return record.getSqlValue();
      } else {
        return CollectionUtil.join(
            child.getValues().stream().map(t -> "'" + t + "'").collect(Collectors.toList()), ",");
      }
    }

    return "'" + child.getValue() + "'";
  }
}
