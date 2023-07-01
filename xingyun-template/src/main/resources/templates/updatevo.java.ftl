package ${packageName}.vo.${moduleName}.${bizName};

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import java.io.Serializable;

@Data
public class Update${className}Vo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ${keys[0].description}
     */
    @ApiModelProperty(value = "${keys[0].description}", required = true)
<#if keys[0].dataType == 'String'>
    @NotBlank(message = "${keys[0].name}不能为空！")
<#else>
    @NotNull(message = "${keys[0].name}不能为空！")
</#if>
    private ${keys[0].dataType} ${keys[0].name};

<#list columns as column>
    /**
     * ${column.description}
     */
    <#if column.required>
    @ApiModelProperty(value = "${column.description}", required = true)
    <#else>
    @ApiModelProperty("${column.description}")
    </#if>
    <#if column.dataType != 'String'>
    @TypeMismatch(message = "${column.description}格式有误！")
    </#if>
    <#if column.required>
    @${column.validateAnno}(message = "${column.validateMsg}${column.description}！")
        <#if column.fixEnum>
    @IsEnum(message = "${column.validateMsg}${column.description}！", enumClass = ${column.dataType}.class)
        </#if>
    </#if>
    <#if column.regularExpression??>
    @Pattern(regexp = "${column.regularExpression?replace("\\", "\\\\")}", message = "${column.description}格式有误！")
    </#if>
    <#if column.isDecimalType>
      <#if (column.decimals??) && column.decimals gt 0>
    @IsNumberPrecision(message = "${column.description}最多允许${column.decimals}位小数！", value = ${column.decimals})
      </#if>
    <#else>
      <#if column.dataType == 'String' && (column.viewType == 0 || column.viewType == 1)>
        <#if (column.len??) && column.len gt 0>
    @Length(message = "${column.description}最多允许${column.len}个字符！")
        </#if>
      </#if>
    </#if>
    private <#if column.fixEnum>${column.enumCodeType}<#else>${column.dataType}</#if> ${column.name};

</#list>
}
