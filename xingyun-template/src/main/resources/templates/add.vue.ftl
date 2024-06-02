<template>
  <a-modal v-model:open="visible" :mask-closable="false" width="40%" title="新增" :dialog-style="{ top: '20px' }" :footer="null">
    <div v-if="visible" v-permission="['${moduleName}:${bizName}:add']" v-loading="loading">
      <a-form ref="form" :label-col="{span: 4}" :wrapper-col="{span: 16}" :model="formData" :rules="rules">
        <#list columns as column>
        <a-form-item label="${column.description}" name="${column.name}">
          <#assign formData="formData"/>
          <@format><#include "input-components.ftl" /></@format>
        </a-form-item>
        </#list>
        <div class="form-modal-footer">
          <a-space>
            <a-button type="primary" :loading="loading" html-type="submit" @click="submit">保存</a-button>
            <a-button :loading="loading" @click="closeDialog">取消</a-button>
          </a-space>
        </div>
      </a-form>
    </div>
  </a-modal>
</template>
<script>
import { defineComponent } from 'vue';
import * as api from '@/api/${moduleName}/${bizName}';

export default defineComponent({
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
                return Promise.resolve();
              }
              </#if>
              if (${r"/"}${column.regularExpression}${r"/.test(value))"} {
                return Promise.resolve();
              }
              return Promise.reject('${column.description}格式不正确');
            }
          },
          </#if>
        ],
        </#if>
        </#list>
      },
    }
  },
  computed: {
  },
  created() {
    // 初始化表单数据
    this.initFormData();
  },
  methods: {
    // 打开对话框 由父页面触发
    openDialog() {
      this.visible = true;
      
      this.$nextTick(() => this.open());
    },
    // 关闭对话框
    closeDialog() {
      this.visible = false;
      this.$emit('close');
    },
    // 初始化表单数据
    initFormData() {
      this.formData = {
        <#list columns as column>
        ${column.name}: '',
        </#list>
      };
    },
    // 提交表单事件
    submit() {
      this.$refs.form.validate().then((valid) => {
        if (valid) {
          this.loading = true;
          api.create(this.formData).then(() => {
            this.$msg.success('新增成功！');
            this.$emit('confirm');
            this.visible = false;
          }).finally(() => {
            this.loading = false;
          });
        }
      });
    },
    // 页面显示时触发
    open() {
      // 初始化表单数据
      this.initFormData();
    },
  },
});
</script>
