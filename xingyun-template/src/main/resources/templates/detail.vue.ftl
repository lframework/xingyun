<template>
  <a-modal v-model="visible" :mask-closable="false" width="40%" title="查看" :dialog-style="{ top: '20px' }" :footer="null">
    <div v-if="visible" v-permission="['${moduleName}:${bizName}:query']" v-loading="loading">
      <a-descriptions :column="${detailSpan}" bordered>
        <#list columns as column>
        <a-descriptions-item label="${column.description}" :span="${column.span}">
            <#if column.fixEnum>
          {{ $enums.${column.frontType}.getDesc(formData.${column.name}) }}
            <#else>
                <#if column.hasAvailableTag>
          <available-tag :available="formData.available" />
                <#elseif column.dataDicCode??>
          {{ formData.${column.name}DicValue }}
                <#else>
          {{ formData.${column.name} }}
                </#if>
            </#if>
        </a-descriptions-item>
        </#list>
      </a-descriptions>
    </div>
  </a-modal>
</template>
<script>
<#if hasAvailableTag>
import AvailableTag from '@/components/Tag/Available'
</#if>
export default {
  // 使用组件
  components: {
    <#if hasAvailableTag>AvailableTag</#if>
  },
  props: {
    ${keys[0].name}: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      // 是否可见
      visible: false,
      // 是否显示加载框
      loading: false,
      // 表单数据
      formData: {}
    }
  },
  created() {
    this.initFormData()
  },
  methods: {
    // 打开对话框 由父页面触发
    openDialog() {
      this.visible = true

      this.$nextTick(() => this.open())
    },
    // 关闭对话框
    closeDialog() {
      this.visible = false
      this.$emit('close')
    },
    // 初始化表单数据
    initFormData() {
      this.formData = {
        ${keys[0].name}: '',
        <#list columns as column>
        ${column.name}: ''<#if column_index != columns?size - 1>,</#if>
        </#list>
      }
    },
    // 页面显示时触发
    open() {
      // 初始化数据
      this.initFormData()

      // 查询数据
      this.loadFormData()
    },
    // 查询数据
    async loadFormData() {
      this.loading = true
      await this.$api.${moduleName}.${bizName}.get(this.id).then(data => {
        this.formData = data
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
