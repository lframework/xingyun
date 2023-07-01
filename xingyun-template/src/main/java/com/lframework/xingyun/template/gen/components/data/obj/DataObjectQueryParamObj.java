package com.lframework.xingyun.template.gen.components.data.obj;

import com.lframework.xingyun.template.gen.enums.GenQueryType;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.vo.PageVo;
import java.util.List;
import lombok.Data;

@Data
public class DataObjectQueryParamObj extends PageVo {

    /**
     * 条件
     */
    private List<Condition> conditions;

    @Data
    public static class Condition {
        /**
         * 表别名
         */
        private String tableAlias;

        /**
         * 字段名
         */
        private String columnName;

        /**
         * 查询类型
         */
        private Integer queryType;

        /**
         * 值前缀
         */
        private String valuePrefix;

        /**
         * 值
         */
        private Object value;

        /**
         * 值集合
         */
        private List<Object> values;

        /**
         * 值后缀
         */
        private String valueSuffix;

        public String getQueryTypeSql() {
            return EnumUtil.getByCode(GenQueryType.class, this.queryType).getOperation();
        }
    }
}
