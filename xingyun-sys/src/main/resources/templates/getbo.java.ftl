package ${packageName}.bo.${moduleName}.${bizName};

import com.fasterxml.jackson.annotation.JsonFormat;
import ${packageName}.entity.${className};
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>

import lombok.Data;

/**
 * <p>
 * ${classDescription} GetBo
 * </p>
 *
<#if author??>
 * @author ${author}
</#if>
 */
@Data
public class Get${className}Bo extends BaseBo${r"<"}${className}${r">"} {

    /**
     * ${keys[0].description}
     */
    @ApiModelProperty("${keys[0].description}")
    private ${keys[0].dataType} ${keys[0].name};

    <#list columns as column>
    /**
     * ${column.description}
     */
    @ApiModelProperty("${column.description}")
        <#if column.dataType == 'LocalDateTime'>
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
        </#if>
        <#if column.dataType == 'LocalDate'>
    @JsonFormat(pattern = StringPool.DATE_PATTERN)
        </#if>
        <#if column.dataType == 'LocalTime'>
    @JsonFormat(pattern = StringPool.TIME_PATTERN)
        </#if>
    private <#if column.fixEnum>${column.enumCodeType}<#else>${column.dataType}</#if> ${column.name};

        <#if column.dataDicCode??>
    /**
     * ${column.description}字典值
     */
    @ApiModelProperty("${column.description}字典值")
    private String ${column.name}DicValue;

        </#if>
    </#list>
    public Get${className}Bo() {

    }

    public Get${className}Bo(${className} dto) {

        super(dto);
    }

    @Override
    public BaseBo${r"<"}${className}${r">"} convert(${className} dto) {
        <#if hasFixEnum>
        return super.convert(dto<#list columns as column><#if column.fixEnum>, Get${className}Bo::get${column.nameProperty}</#if></#list>);
        <#else>
        return super.convert(dto);
        </#if>
    }

    @Override
    protected void afterInit(${className} dto) {

        <#list columns as column>
          <#if column.dataDicCode??>
        ISysDataDicItemService sysDataDicItemService = ApplicationUtil.getBean(ISysDataDicItemService.class);

            <#break>
          </#if>
        </#list>
        <#list columns as column>
            <#if column.fixEnum>
        this.${column.name} = dto.get${column.nameProperty}().getCode();

            <#elseif column.dataDicCode??>
        if (!StringUtil.isBlank(dto.get${column.nameProperty}())) {
          String[] ${column.name}DicArr = dto.get${column.nameProperty}().split(StringPool.DATA_DIC_SPLIT);
          this.${column.name}DicValue = sysDataDicItemService.findByCode(${column.name}DicArr[0], ${column.name}DicArr[1]).getName();
        }

            </#if>
        </#list>
    }
}
