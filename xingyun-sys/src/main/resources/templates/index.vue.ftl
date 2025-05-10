<template>
  <div>
    <div v-permission="['${moduleName}:${bizName}:query']">
      <page-wrapper content-full-height fixed-height>
        <!-- 数据列表 -->
        <vxe-grid
          id="${className}"
          ref="grid"
          resizable
          show-overflow
          highlight-hover-row
          keep-source
          row-id="id"
          :proxy-config="proxyConfig"
          :columns="tableColumn"
          :toolbar-config="toolbarConfig"
          :custom-config="{}"
          :pager-config="{}"
          :loading="loading"
          height="auto"
        >
          <#if query?? && queryParams??>
          <template #form>
            <j-border>
              <j-form bordered label-width="80px" @collapse="$refs.grid.refreshColumn()">
                <#list queryParams.columns as column>
                <j-form-item label="${column.description}"<#if column.viewType != 0 && column.viewType != 1 && column.viewType != 5 && column.viewType != 7> :content-nest="false"</#if>>
                  <#assign formData="searchFormData"/>
                  <@format><#include "input-components.ftl" /></@format>
                </j-form-item>
                </#list>
              </j-form>
            </j-border>
          </template>
          </#if>
          <!-- 工具栏 -->
          <template #toolbar_buttons>
            <a-space>
              <#if query??>
              <a-button type="primary" :icon="h(SearchOutlined)" @click="search">查询</a-button>
              </#if>
              <#if create??>
              <a-button v-permission="['${moduleName}:${bizName}:add']" type="primary" :icon="h(PlusOutlined)" @click="$refs.addDialog.openDialog()">新增</a-button>
              </#if>
            </a-space>
          </template>
          <#list query.columns as column>
              <#if column.hasAvailableTag>

          <!-- 状态 列自定义内容 -->
          <template #available_default="{ row }">
            <available-tag :available="row.available" />
          </template>
              </#if>
          </#list>

          <!-- 操作 列自定义内容 -->
          <template #action_default="{ row }">
            <#if detail??>
            <a-button v-permission="['${moduleName}:${bizName}:query']" type="link" @click="e => { id = row.${keys[0].name};$nextTick(() => $refs.viewDialog.openDialog()) }">查看</a-button>
            </#if>
            <#if update??>
            <a-button v-permission="['${moduleName}:${bizName}:modify']" type="link" @click="e => { id = row.${keys[0].name};$nextTick(() => $refs.updateDialog.openDialog()) }">修改</a-button>
            </#if>
            <#if hasDelete>
              <a-button v-permission="['${moduleName}:${bizName}:delete']" type="link" class="ant-btn-link-danger" @click="e => { deleteRow(row.${keys[0].name}) }">删除</a-button>
            </#if>
          </template>
        </vxe-grid>
      </page-wrapper>
    </div>
    <#if create??>
    <!-- 新增窗口 -->
    <add ref="addDialog" @confirm="search" />

    </#if>
    <#if update??>
    <!-- 修改窗口 -->
    <modify :id="id" ref="updateDialog" @confirm="search" />

    </#if>
    <#if detail??>
    <!-- 查看窗口 -->
    <detail :id="id" ref="viewDialog" />

    </#if>
  </div>
</template>

<script>
import { h, defineComponent } from 'vue';
<#if create??>
import Add from './add.vue.ftl';
</#if>
<#if update??>
import Modify from './modify.vue.ftl';
</#if>
<#if detail??>
import Detail from './detail.vue.ftl';
</#if>
<#if hasAvailableTag>
</#if>
import * as api from '@/api/${moduleName}/${bizName}';
import { PlusOutlined, SearchOutlined } from '@ant-design/icons-vue';

export default defineComponent({
  name: '${className}',
  components: {
    <#if create??>Add, </#if><#if update??>Modify, </#if><#if detail??>Detail, </#if>
  },
  setup() {
    return {
      h,
      PlusOutlined,
      SearchOutlined,
    };
  },
  data() {
    return {
      loading: false,
      // 当前行数据
      id: '',
      // 查询列表的查询条件
      searchFormData: {
        <#if queryParams??>
        <#list queryParams.columns as column>
        <#if column.viewType == 6>
        ${column.name}Start: '',
        ${column.name}End: ''<#if column_index != queryParams.columns?size - 1>,</#if>
        <#else>
        ${column.name}: <#if column.fixEnum>undefined<#else>''</#if>,
        </#if>
        </#list>
        </#if>
      },
      // 工具栏配置
      toolbarConfig: {
        // 自定义左侧工具栏
        slots: {
          buttons: 'toolbar_buttons',
        },
      },
      // 列表数据配置
      tableColumn: [
        { type: 'seq', width: 40 },
        <#if query??>
        <#list query.columns as column>
        { field: '${column.name}', title: '${column.description}', <#if column.widthType == 0>width<#else>minWidth</#if>: ${column.width}<#if column.sortable>, sortable: true</#if><#if column.isNumberType>, align: 'right'</#if><#if column.hasAvailableTag>, slots: {default: 'available_default'}</#if><#if column.fixEnum>, formatter: ({ cellValue }) => { return this.${r"$enums"}.${column.frontType}.getDesc(cellValue) }</#if> },
        </#list>
        </#if>
        { title: '操作', width: <#if hasDelete>150<#else>120</#if>, fixed: 'right', slots: { default: 'action_default' }},
      ],
      // 请求接口配置
      proxyConfig: {
        props: {
          // 响应结果列表字段
          result: 'datas',
          // 响应结果总条数字段
          total: 'totalCount',
        },
        ajax: {
          // 查询接口
          query: ({ page, sorts, filters }) => {
            return api.query(this.buildQueryParams(page));
          }
        },
      },
    }
  },
  created() {
  },
  methods: {
    // 列表发生查询时的事件
    search() {
      this.$refs.grid.commitProxy('reload');
    },
    <#if hasDelete>
    deleteRow(id) {
      this.$msg.createConfirm('是否确定删除该${classDescription}？').then(() => {
        this.loading = true;
        this.$api.${moduleName}.${bizName}.deleteById(id).then(() => {
          this.$msg.createSuccess('删除成功！');
          this.search();
        }).finally(() => {
          this.loading = false;
        });
      });
    },
    </#if>
    // 查询前构建查询参数结构
    buildQueryParams(page) {
      return Object.assign({
        pageIndex: page.currentPage,
        pageSize: page.pageSize,
      }, this.buildSearchFormData());
    },
    // 查询前构建具体的查询参数
    buildSearchFormData() {
      return Object.assign({}, this.searchFormData);
    },
  },
});
</script>
<style scoped>
</style>
