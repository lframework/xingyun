import { defHttp } from '/@/utils/http/axios';
import { PageResult } from '@/api/model/pageResult';
import { ContentTypeEnum } from '@/enums/httpEnum';
<#if query??>import { Query${className}Vo } from '@/api/${moduleName}/${bizName}/model/query${className}Vo';</#if>
<#if query??>import { Query${className}Bo } from '@/api/${moduleName}/${bizName}/model/query${className}Bo';</#if>
<#if detail??>import { ${r"Get"}${className}${r"Bo"} } from '@/api/${moduleName}/${bizName}/model/${r"get"}${className}${r"Bo"}';</#if>
<#if create??>import { Create${className}Vo } from '@/api/${moduleName}/${bizName}/model/create${className}Vo';</#if>
<#if update??>import { Update${className}Vo } from '@/api/${moduleName}/${bizName}/model/update${className}Vo';</#if>

const baseUrl = '/${moduleName}/${bizName}';
const region = 'cloud-api';

<#if query??>
/**
 * 查询列表
 * @param params
 * @returns {Promise}
 */
export function query(params: Query${className}Vo): ${r"Promise<PageResult<"}Query${className}${r"Bo>>"} {
  return defHttp.get<${r"PageResult<"}Query${className}${r"Bo>"}>(
    {
      url: baseUrl + '/query',
      params: params,
    },
    {
      region,
    },
  );
}
</#if>

<#if detail??>
/**
 * 根据ID查询
 * @param ${keys[0].name}
 * @returns {Promise}
 */
export function get(${keys[0].name}: ${keys[0].dataType}): ${r"Promise<Get"}${className}${r"Bo>"} {
  return defHttp.get<${r"Get"}${className}${r"Bo"}>(
    {
      url: baseUrl,
      params: {
        ${keys[0].name},
      }
    },
    {
      region,
    },
  );
}
</#if>

<#if create??>
/**
 * 新增
 * @param params
 * @returns {Promise}
 */
export function create(params: Create${className}Vo): ${r"Promise<void>"} {
  return defHttp.post<void>(
    {
      url: baseUrl,
      data: params,
    },
    {
      contentType: ContentTypeEnum.FORM_URLENCODED,
      region,
    },
  );
}
</#if>

<#if update??>
/**
 * 修改
 * @param params
 * @returns {Promise}
 */
export function update(params: Update${className}Vo): ${r"Promise<void>"} {
  return defHttp.put<void>(
    {
      url: baseUrl,
      data: params,
    },
    {
      contentType: ContentTypeEnum.FORM_URLENCODED,
      region,
    },
  );
}
</#if>

<#if hasDelete>
/**
 * 删除
 * @param params
 * @returns {Promise}
 */
export function deleteById(${keys[0].name}: ${keys[0].dataType}): ${r"Promise<void>"} {
  return defHttp.delete<void>(
    {
      url: baseUrl,
      data: {
        ${keys[0].name},
      },
    },
    {
      region,
      contentType: ContentTypeEnum.FORM_URLENCODED,
    },
  );
}
</#if>