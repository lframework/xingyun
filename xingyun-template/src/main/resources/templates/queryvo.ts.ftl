import { PageVo } from '@/api/model/pageVo';

export interface Query${className}Vo extends PageVo {

    <#list columns as column>
    <#if column.viewType != 6>
    /**
     * ${column.description}
     */
     ${column.name}: ${column.frontDataType};
     </#if>
    <#if column.viewType == 6>
    /**
     * ${column.description} 起始时间
     */
    ${column.name}Start: ${column.frontDataType};

    /**
     * ${column.description} 截止时间
     */
    ${column.name}End: ${column.frontDataType};
    </#if>

    </#list>
}
