<#if column.queryType == 0>
AND tb.${column.columnName} = ${r"#{vo."}${column.name}${r"}"}
<#elseif column.queryType == 1>
AND tb.${column.columnName} > ${r"#{vo."}${column.name}${r"}"}
<#elseif column.queryType == 2>
AND tb.${column.columnName} >= ${r"#{vo."}${column.name}${r"}"}
<#elseif column.queryType == 3>
<![CDATA[
AND tb.${column.columnName} < ${r"#{vo."}${column.name}${r"}"}
]]>
<#elseif column.queryType == 4>
<![CDATA[
AND tb.${column.columnName} <= ${r"#{vo."}${column.name}${r"}"}
]]>
<#elseif column.queryType == 5>
AND tb.${column.columnName} != ${r"#{vo."}${column.name}${r"}"}
<#elseif column.queryType == 6>
AND tb.${column.columnName} IN (${r"#{vo."}${column.name}${r"}"})
<#elseif column.queryType == 7>
AND tb.${column.columnName} NOT IN (${r"#{vo."}${column.name}${r"}"})
<#elseif column.queryType == 8>
AND tb.${column.columnName} LIKE CONCAT('%', ${r"#{vo."}${column.name}${r"}"})
<#elseif column.queryType == 9>
AND tb.${column.columnName} LIKE CONCAT(${r"#{vo."}${column.name}${r"}"}, '%')
<#elseif column.queryType == 10>
AND tb.${column.columnName} LIKE CONCAT('%', ${r"#{vo."}${column.name}${r"}"}, '%')
</#if>