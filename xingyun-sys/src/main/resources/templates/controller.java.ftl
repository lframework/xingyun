package ${packageName}.controller.${moduleName};

<#if detail??>
import ${packageName}.bo.${moduleName}.${bizName}.Get${className}Bo;
</#if>
<#if query??>
import ${packageName}.bo.${moduleName}.${bizName}.Query${className}Bo;
</#if>
<#if queryParams??>
import ${packageName}.vo.${moduleName}.${bizName}.Query${className}Vo;
</#if>
import ${packageName}.service.${moduleName}.${className}Service;
<#if create??>
import ${packageName}.vo.${moduleName}.${bizName}.Create${className}Vo;
</#if>
<#if update??>
import ${packageName}.vo.${moduleName}.${bizName}.Update${className}Vo;
</#if>
import ${packageName}.entity.${className};
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import com.lframework.starter.web.controller.DefaultBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.lframework.starter.web.annotations.security.HasPermission;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${classDescription} Controller
 *
<#if author??>
 * @author ${author}
</#if>
 */
@Api(tags = "${classDescription}")
@Validated
@RestController
@RequestMapping("/${moduleName}/${bizName}")
public class ${className}Controller extends DefaultBaseController {

    @Autowired
    private ${className}Service ${classNameProperty}Service;
    <#if query?? && queryParams??>

    /**
     * 查询列表
     */
    @ApiOperation("查询列表")
    @HasPermission({"${moduleName}:${bizName}:query"})
    @GetMapping("/query")
    public ${r"InvokeResult<PageResult<"}Query${className}${r"Bo>>"} query(@Valid Query${className}Vo vo) {

        PageResult${r"<"}${className}${r">"} pageResult = ${classNameProperty}Service.query(getPageIndex(vo), getPageSize(vo), vo);

        List${r"<"}${className}${r">"} datas = pageResult.getDatas();
        List${r"<"}Query${className}Bo${r">"} results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(Query${className}Bo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }
    </#if>
    <#if detail??>

    /**
     * 根据ID查询
     */
    @ApiOperation("根据ID查询")
    @ApiImplicitParam(value = "${keys[0].name}", name = "${keys[0].name}", paramType = "query", required = true)
    @HasPermission({"${moduleName}:${bizName}:query"})
    @GetMapping
    public ${r"InvokeResult<Get"}${className}${r"Bo>"} get(<#if keys[0].dataType == 'String'>@NotBlank<#else>@NotNull</#if>(message = "${keys[0].name}不能为空！") ${keys[0].dataType} ${keys[0].name}) {

        ${className} data = ${classNameProperty}Service.findById(${keys[0].name});
        if (data == null) {
            throw new DefaultClientException("${classDescription}不存在！");
        }

        Get${className}Bo result = new Get${className}Bo(data);

        return InvokeResultBuilder.success(result);
    }
    </#if>
    <#if create??>

    /**
     * 新增
     */
    @ApiOperation("新增")
    @HasPermission({"${moduleName}:${bizName}:add"})
    @PostMapping
    public ${r"InvokeResult<Void>"} create(@Valid Create${className}Vo vo) {

        ${classNameProperty}Service.create(vo);

        return InvokeResultBuilder.success();
    }
    </#if>
    <#if update??>

    /**
     * 修改
     */
    @ApiOperation("修改")
    @HasPermission({"${moduleName}:${bizName}:modify"})
    @PutMapping
    public ${r"InvokeResult<Void>"} update(@Valid Update${className}Vo vo) {

        ${classNameProperty}Service.update(vo);
        <#if isCache>

        ${classNameProperty}Service.cleanCacheByKey(vo.get${keys[0].nameProperty}());
        </#if>

        return InvokeResultBuilder.success();
    }
    </#if>
    <#if hasDelete>

    /**
     * 根据ID删除
     */
    @ApiOperation("根据ID删除")
    @ApiImplicitParam(value = "${keys[0].name}", name = "${keys[0].name}", paramType = "query", required = true)
    @HasPermission({"${moduleName}:${bizName}:delete"})
    @DeleteMapping
    public ${r"InvokeResult<Void>"} deleteById(<#if keys[0].dataType == 'String'>@NotBlank<#else>@NotNull</#if>(message = "${keys[0].name}不能为空！") ${keys[0].dataType} ${keys[0].name}) {

        ${classNameProperty}Service.deleteById(${keys[0].name});
        <#if isCache>

        ${classNameProperty}Service.cleanCacheByKey(${keys[0].name});
        </#if>

        return InvokeResultBuilder.success();
    }
    </#if>
}
