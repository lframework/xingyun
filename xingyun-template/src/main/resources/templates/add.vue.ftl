<template>
  <a-modal v-model="visible" :mask-closable="false" width="40%" title="新增" :dialog-style="{ top: '20px' }" :footer="null">
    <div v-if="visible" v-permission="['${moduleName}:${bizName}:add']" v-loading="loading">
      <a-form-model ref="form" :label-col="{span: 4}" :wrapper-col="{span: 16}" :model="formData" :rules="rules">
        <#list columns as column>
        <a-form-model-item label="${column.description}" prop="${column.name}">
          <#assign formData="formData"/>
          <@format><#include "input-components.ftl" /></@format>
        </a-form-model-item>
        </#list>
        <div class="form-modal-footer">
          <a-space>
            <a-button type="primary" :loading="loading" html-type="submit" @click="submit">保存</a-button>
            <a-button :loading="loading" @click="closeDialog">取消</a-button>
          </a-space>
        </div>
      </a-form-model>
    </div>
  </a-modal>
</template>
<script>
export default {
  components: {
  },
  data() {
    return {
      // 是否可见
      visible: false,
      // 是否显示加载框
      loading: false,
      // 表单数据
      formData: {},
      // 表单校验规则
      rules: {
        <#list columns as column>
        <#if column.required || column.regularExpression??>
        ${column.name}: [
          <#if column.required>{ required: true, message: '${column.validateMsg}${column.description}' }</#if><#if column.required && column.regularExpression??>,</#if>
          <#if column.regularExpression??>
          {
            validator: (rule, value, callback) => {
              <#if !column.required>
              if (this.$utils.isEmpty(value)) {
                return callback()
              }
              </#if>
              if (${r"/"}${column.regularExpression}${r"/.test(value))"} {
                return callback()
              }
              return callback(new Error('${column.description}格式不正确'))
            }
          }
          </#if>
        ]<#if column_index != columns?size - 1>,</#if>
        </#if>
        </#list>
      }
    }
  },
  computed: {
  },
  created() {
    // 初始化表单数据
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
        <#list columns as column>
        ${column.name}: ''<#if column_index != columns?size - 1>,</#if>
        </#list>
      }
    },
    // 提交表单事件
    submit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true
          this.$api.${moduleName}.${bizName}.create(this.formData).then(() => {
            this.$msg.success('新增成功！')
            this.$emit('confirm')
            this.visible = false
          }).finally(() => {
            this.loading = false
          })
        }
      })
    },
    // 页面显示时触发
    open() {
      // 初始化表单数据
      this.initFormData()
    }
  }
}
</script>
