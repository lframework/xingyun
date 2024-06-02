export interface Create${className}Vo {

    <#list columns as column>
    /**
     * ${column.description}
     */
    ${column.name}<#if !column.required>?</#if>: ${column.frontDataType};

    </#list>
}
