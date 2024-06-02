/**
 * ${classDescription} GetBo
 *
<#if author??>
 * @author ${author}
</#if>
 */
export interface Get${className}Bo {

    /**
     * ${keys[0].description}
     */
    ${keys[0].name}: ${keys[0].frontDataType};

    <#list columns as column>
    /**
     * ${column.description}
     */
    ${column.name}: ${column.frontDataType};

    <#if column.dataDicCode??>
    /**
     * ${column.description}字典值
     */
    ${column.name}DicValue: string;

        </#if>
    </#list>
}
