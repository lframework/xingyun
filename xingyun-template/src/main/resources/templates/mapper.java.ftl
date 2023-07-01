package ${packageName}.mappers;

import BaseMapper;
import ${packageName}.entity.${className};
<#if queryParams??>
import ${packageName}.vo.${moduleName}.${bizName}.Query${className}Vo;
</#if>
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ${classDescription} Mapper 接口
 * </p>
 *
<#if author??>
 * @author ${author}
</#if>
 */
public interface ${className}Mapper extends BaseMapper<${className}> {

    <#if queryParams??>
	/**
     * 查询列表
     * @param vo
     * @return
     */
    List${r"<"}${className}${r">"} query(@Param("vo") Query${className}Vo vo);
    </#if>
}
