import { request } from '@/utils/request'

export default {

<#if query??>
  /**
   * 查询列表
   * @param params
   * @returns {AxiosPromise}
   */
  query: (params) => {
    return request({
      url: '/${moduleName}/${bizName}/query',
      method: 'get',
      params: params
    })
  },
</#if>

<#if detail??>
  /**
   * 根据ID查询
   * @param ${keys[0].name}
   * @returns {AxiosPromise}
   */
  get: (${keys[0].name}) => {
    return request({
      url: '/${moduleName}/${bizName}',
      method: 'get',
      params: {
        ${keys[0].name}: ${keys[0].name}
      }
    })
  },
</#if>

<#if create??>
  /**
   * 新增
   * @param params
   * @returns {AxiosPromise}
   */
  create: (params) => {
    return request({
      url: '/${moduleName}/${bizName}',
      method: 'post',
      data: params
    })
  },
</#if>

<#if update??>
  /**
   * 修改
   * @param params
   * @returns {AxiosPromise}
   */
  modify: (params) => {
    return request({
      url: '/${moduleName}/${bizName}',
      method: 'put',
      data: params
    })
  },
</#if>

<#if hasDelete>
  /**
   * 删除
   * @param params
   * @returns {AxiosPromise}
   */
  deleteById: (${keys[0].name}) => {
    return request({
      url: '/${moduleName}/${bizName}',
      method: 'delete',
      data: {
        ${keys[0].name}: ${keys[0].name}
      }
    })
  },
</#if>
}