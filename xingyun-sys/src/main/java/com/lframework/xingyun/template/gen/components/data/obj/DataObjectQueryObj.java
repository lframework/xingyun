package com.lframework.xingyun.template.gen.components.data.obj;

import com.lframework.xingyun.template.gen.enums.GenDataType;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class DataObjectQueryObj implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "DataObjectQueryObj";

    /**
     * SQL的SELECT部分
     */
    private List<QueryField> fields;

    /**
     * 主表 FROM部分
     */
    private String mainTable;

    /**
     * 主表别名
     */
    private String mainTableAlias;

    /**
     * 关联的子表
     */
    private List<QuerySubTable> subTables;

    /**
     * 查询参数
     */
    private DataObjectQueryParamObj queryParamObj;

    /**
     * 查询前置SQL
     */
    private String queryPrefixSql;

    /**
     * 查询后置SQL
     */
    private String querySuffixSql;

    /**
     * 后置SQL
     */
    private String suffixSql;

    @Data
    public static class QueryField implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 表别名 如果此值为空，代表为自定义查询字段
         */
        private String tableAlias;

        /**
         * 字段名
         */
        private String columnName;

        /**
         * 字段别名
         */
        private String columnAlias;

        /**
         * 数据类型
         */
        private GenDataType dataType;
    }

    @Data
    public static class QuerySubTable implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 子表
         */
        private String subTable;

        /**
         * 子表别名
         */
        private String subTableAlias;

        /**
         * 关联方式
         */
        private String joinType;

        /**
         * 关联条件 key：主表字段 value：子表字段
         */
        private List<QuerySubTableCondition> joinCondition;
    }

    @Data
    public static class QuerySubTableCondition implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 左侧字段
         */
        private String key;

        /**
         * 右侧字段
         */
        private String value;
    }
}
