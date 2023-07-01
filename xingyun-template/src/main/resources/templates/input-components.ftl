<#if column.viewType == 0>
<a-input v-model="${formData}.${column.name}"<#if column.dataType == 'String' && (column.len??) && column.len gt 0> max-length="${column.len}"</#if> allow-clear />
<#elseif column.viewType == 1>
<a-textarea v-model="${formData}.${column.name}"<#if column.dataType == 'String' && (column.len??) && column.len gt 0> max-length="${column.len}"</#if> allow-clear />
<#elseif column.viewType == 2>
<a-date-picker
	v-model="${formData}.${column.name}"
	placeholder=""
	value-format="YYYY-MM-DD HH:mm:ss"
	show-time
/>
<#elseif column.viewType == 3>
<a-date-picker
    v-model="${formData}.${column.name}"
	placeholder=""
    value-format="YYYY-MM-DD"
/>
<#elseif column.viewType == 4>
<a-time-picker
    v-model="${formData}.${column.name}"
	placeholder=""
	value-format="HH:mm:ss"
>
</a-time-picker>
<#elseif column.viewType == 5>
<#if column.fixEnum>
<a-select v-model="${formData}.${column.name}" allow-clear>
	<a-select-option v-for="item in $enums.${column.frontType}.values()" :key="item.code" :value="item.code">{{ item.desc }}</a-select-option>
</a-select>
<#else>
<#if column.hasAvailableTag>
<a-select v-model="${formData}.${column.name}" allow-clear>
	<a-select-option v-for="item in $enums.AVAILABLE.values()" :key="item.code" :value="item.code">{{ item.desc }}</a-select-option>
</a-select>
<#else>
<a-select v-model="${formData}.${column.name}" allow-clear>
	<a-select-option :value="true">是</a-select-option>
	<a-select-option :value="false">否</a-select-option>
</a-select>
</#if>
</#if>
<#elseif column.viewType == 6>
<#if column.dataType == 'LocalDateTime'>
<div class="date-range-container">
	<a-date-picker
		v-model="${formData}.${column.name}Start"
		placeholder=""
		value-format="YYYY-MM-DD 00:00:00"
	/>
	<span class="date-split">至</span>
	<a-date-picker
		v-model="${formData}.${column.name}End"
		placeholder=""
		value-format="YYYY-MM-DD 23:59:59"
	/>
</div>
<#else>
<div class="date-range-container">
	<a-date-picker
		v-model="${formData}.${column.name}Start"
		placeholder=""
		show-time
		value-format="YYYY-MM-DD HH:mm:ss"
	/>
	<span class="date-split">至</span>
	<a-date-picker
		v-model="${formData}.${column.name}End"
		placeholder=""
		show-time
		value-format="YYYY-MM-DD HH:mm:ss"
	/>
</div>
</#if>
<#elseif column.viewType == 7>
<data-dic-picker code="${column.dataDicCode}" v-model="${formData}.${column.name}" />
<#elseif column.viewType == 8>
<custom-selector custom-selector-id="${column.customSelectorId}" v-model="${formData}.${column.name}" />
</#if>
