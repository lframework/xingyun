<html>
<body>
<table>
    <colgroup>
        <col style="width: 29.8mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.6mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.4mm;"/>
        <col style="width: 22.4mm;"/>
    </colgroup>
    <tbody>
    <tr height="24" style="height:14.40pt;" class="firstRow">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            单据号
        </td>
        <td colspan="10" class="et3" style="border-width: 1px; border-style: solid;">${code}</td>
    </tr>
    <tr height="24" style="height:14.40pt;" class="firstRow">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            采购订单号
        </td>
        <td colspan="10" class="et3" style="border-width: 1px; border-style: solid;">${purchaseOrderCode}</td>
    </tr>
    <tr height="24" style="height:14.40pt;">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            仓库编号
        </td>
        <td colspan="2" class="et3" style="border-width: 1px; border-style: solid;">${scCode}</td>
        <td class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            仓库名称
        </td>
        <td colspan="2" class="et3" style="border-width: 1px; border-style: solid;">${scName}</td>
        <td class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            采购员
        </td>
        <td class="et3" style="border-width: 1px; border-style: solid;">${purchaserName}</td>
        <td colspan="2" class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            付款日期
        </td>
        <td class="et3" style="border-width: 1px; border-style: solid;">${paymentDate}</td>
    </tr>
    <tr height="24" style="height:14.40pt;">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            供应商编号
        </td>
        <td colspan="2" class="et3" style="border-width: 1px; border-style: solid;">${supplierCode}</td>
        <td class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            供应商名称
        </td>
        <td colspan="4" class="et3" style="border-width: 1px; border-style: solid;">${supplierName}</td>
        <td colspan="2" class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            实际到货日期
        </td>
        <td class="et3" style="border-width: 1px; border-style: solid;">${receiveDate}</td>
    </tr>
    <tr height="24" style="height:14.40pt;">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            创建人
        </td>
        <td colspan="2" class="et3" style="border-width: 1px; border-style: solid;">${createBy}</td>
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            创建时间
        </td>
        <td colspan="7" class="et3" style="border-width: 1px; border-style: solid;">${createTime}</td>
    </tr>
    <tr height="24" style="height:14.40pt;">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            审核人
        </td>
        <td colspan="2" class="et3" style="border-width: 1px; border-style: solid;">${approveBy}</td>
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            审核时间
        </td>
        <td colspan="7" class="et3" style="border-width: 1px; border-style: solid;">${approveTime}</td>
    </tr>
    <tr height="24" style="height:14.40pt;">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            备注
        </td>
        <td colspan="10" class="et3" style="border-width: 1px; border-style: solid;">${description}</td>
    </tr>
    <tr height="24" style="height:14.40pt;">
        <td class="et2" height="14" x:str="" style="border-width: 1px; border-style: solid;">
            商品编号
        </td>
        <td colspan="3" class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            商品名称
        </td>
        <td colspan="2" class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            商品SKU编号
        </td>
        <td colspan="2" class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            商品外部编号
        </td>
        <td class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            采购价
        </td>
        <td class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            收货数量
        </td>
        <td class="et2" x:str="" style="border-width: 1px; border-style: solid;">
            收货金额
        </td>
    </tr>
    <#if details??>
        <#list details as detail>
    <tr height="24" style="height:14.40pt;">
        <td class="et5" height="14" style="border-width: 1px; border-style: solid;">${detail.productCode}</td>
        <td colspan="3" class="et6" style="border-width: 1px; border-style: solid; border-right-color: rgb(0, 0, 0);">${detail.productName}</td>
        <td colspan="2" class="et6" style="border-width: 1px; border-style: solid; border-right-color: rgb(0, 0, 0);">${detail.skuCode}</td>
        <td colspan="2" class="et6" style="border-width: 1px; border-style: solid; border-right-color: rgb(0, 0, 0);">${detail.externalCode}</td>
        <td class="et5" style="border-width: 1px; border-style: solid;">${detail.purchasePrice}</td>
        <td class="et5" style="border-width: 1px; border-style: solid;">${detail.receiveNum}</td>
        <td class="et5" style="border-width: 1px; border-style: solid;">${detail.receiveAmount}</td>
    </tr>
        </#list>
    </#if>
    </tbody>
</table>
</body>
</html>