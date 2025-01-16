
export interface Update${className}Vo {

    /**
     * ${keys[0].description}
     */
    ${keys[0].name}: ${keys[0].frontDataType};

<#list columns as column>
    /**
     * ${column.description}
     */

    ${column.name}<#if !column.required>?</#if>: ${column.frontDataType};

</#list>
}
