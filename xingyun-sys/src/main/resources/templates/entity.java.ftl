package ${packageName}.entity;

import com.baomidou.mybatisplus.annotation.TableName;
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import lombok.Data;

/**
 * <p>
 * ${classDescription}
 * </p>
 *
<#if author??>
 * @author ${author}
</#if>
 */
@Data
@TableName("${tableName}")
public class ${className} extends BaseEntity implements BaseDto {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "${className}";

    <#list columns as column>
    /**
     * ${column.description}
     */
    <#if !column.defaultConvertType>
        <#if !column.isKey>
    @TableField(value = "${column.columnName}"<#if column.fill?? && column.fill>, fill = FieldFill.${column.fillStrategy}</#if>)
        <#else>
    @TableId(value = "${column.columnName}"<#if column.autoIncrKey>, type = IdType.AUTO</#if><#if column.fill?? && column.fill>, fill = FieldFill.${column.fillStrategy}</#if><#if column.autoIncrKey>, type = IdType.AUTO</#if>)
        </#if>
    <#else>
        <#if !column.isKey && column.fill?? && column.fill>
    @TableField(fill = FieldFill.${column.fillStrategy})
        </#if>
    </#if>
    <#if column.defaultConvertType && column.isKey && column.autoIncrKey>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    </#if>
    private ${column.dataType} ${column.name};

    </#list>
}
