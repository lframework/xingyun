/**
 * ${classDescription} QueryBo
 *
<#if author??>
 * @author ${author}
</#if>
 */
export interface Query${className}Bo {

    /**
     * ${keys[0].description}
     */
    ${keys[0].name}: ${keys[0].frontDataType};

<#list columns as column>
    /**
     * ${column.description}
     */
    ${column.name}: ${column.frontDataType};

</#list>
}
