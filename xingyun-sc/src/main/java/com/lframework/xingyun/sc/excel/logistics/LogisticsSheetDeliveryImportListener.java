package com.lframework.xingyun.sc.excel.logistics;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelImportListener;
import com.lframework.xingyun.sc.entity.LogisticsSheet;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetService;
import com.lframework.xingyun.sc.vo.logistics.DeliveryLogisticsSheetVo;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogisticsSheetDeliveryImportListener extends
    ExcelImportListener<LogisticsSheetDeliveryImportModel> {

  @Override
  protected void doInvoke(LogisticsSheetDeliveryImportModel data, AnalysisContext context) {

    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“单据号”不能为空");
    }

    LogisticsSheetService logisticsSheetService = ApplicationUtil.getBean(
        LogisticsSheetService.class);
    Wrapper<LogisticsSheet> queryWrapper = Wrappers.lambdaQuery(LogisticsSheet.class)
        .eq(LogisticsSheet::getCode, data.getCode());
    LogisticsSheet sheet = logisticsSheetService.getOne(queryWrapper);
    if (sheet == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“单据号”不存在");
    }
    data.setId(sheet.getId());

    if (data.getTotalAmount() != null) {
      if (NumberUtil.lt(data.getTotalAmount(), BigDecimal.ZERO)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“物流费”不能小于0");
      }
      if (!NumberUtil.isNumberPrecision(data.getTotalAmount(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“物流费”最多允许2位小数");
      }
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    LogisticsSheetService logisticsSheetService = ApplicationUtil.getBean(
        LogisticsSheetService.class);

    List<LogisticsSheetDeliveryImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      LogisticsSheetDeliveryImportModel data = datas.get(i);

      DeliveryLogisticsSheetVo deliveryLogisticsSheetVo = new DeliveryLogisticsSheetVo();
      deliveryLogisticsSheetVo.setId(data.getId());
      deliveryLogisticsSheetVo.setLogisticsNo(data.getLogisticsNo());
      deliveryLogisticsSheetVo.setTotalAmount(data.getTotalAmount());

      try {
        logisticsSheetService.delivery(deliveryLogisticsSheetVo);
      } catch (Exception e) {
        throw new DefaultClientException(
            "第" + (i + 1) + "行发货失败，失败原因：" + e.getMessage());
      }

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {

  }
}
