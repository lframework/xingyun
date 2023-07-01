package com.lframework.xingyun.sc.excel.logistics;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.NumberUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.excel.ExcelImportListener;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.LogisticsCompany;
import com.lframework.xingyun.basedata.service.logistics.LogisticsCompanyService;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import com.lframework.xingyun.sc.entity.RetailOutSheet;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.enums.LogisticsSheetDetailBizType;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetDetailService;
import com.lframework.xingyun.sc.service.logistics.LogisticsSheetService;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import com.lframework.xingyun.sc.vo.logistics.CreateLogisticsSheetVo;
import com.lframework.xingyun.sc.vo.logistics.LogisticsSheetBizOrderVo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogisticsSheetImportListener extends ExcelImportListener<LogisticsSheetImportModel> {

  @Override
  protected void doInvoke(LogisticsSheetImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getSaleOutSheetCodes()) && StringUtil.isBlank(
        data.getRetailOutSheetCodes())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex()
          + "行“销售出库单号、零售出库单号”不能同时为空");
    }

    if (StringUtil.isNotBlank(data.getSaleOutSheetCodes())) {

      SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(SaleOutSheetService.class);
      List<String> arr = CollectionUtil.toList(
          data.getSaleOutSheetCodes().split(StringPool.STR_SPLIT));
      Wrapper<SaleOutSheet> querySaleOutSheetWrapper = Wrappers.lambdaQuery(SaleOutSheet.class)
          .in(SaleOutSheet::getCode, arr);
      List<SaleOutSheet> saleOutSheets = saleOutSheetService.list(querySaleOutSheetWrapper);
      if (saleOutSheets.size() != arr.size()) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销售出库单号（" + arr.stream()
                .filter(t -> !saleOutSheets.contains(t))
                .collect(Collectors.joining(StringPool.STR_SPLIT_CN)) + "）”不存在");
      }

      data.setSaleOutSheetIds(new HashMap<>());
      for (SaleOutSheet saleOutSheet : saleOutSheets) {
        data.getSaleOutSheetIds().put(saleOutSheet.getId(), saleOutSheet.getCode());
      }
    }

    if (StringUtil.isNotBlank(data.getRetailOutSheetCodes())) {

      RetailOutSheetService retailOutSheetService = ApplicationUtil.getBean(
          RetailOutSheetService.class);
      List<String> arr = CollectionUtil.toList(
          data.getRetailOutSheetCodes().split(StringPool.STR_SPLIT));
      Wrapper<RetailOutSheet> queryRetailOutSheetWrapper = Wrappers.lambdaQuery(
          RetailOutSheet.class).in(RetailOutSheet::getCode, arr);
      List<RetailOutSheet> retailOutSheets = retailOutSheetService.list(queryRetailOutSheetWrapper);
      if (retailOutSheets.size() != arr.size()) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“零售出库单号（" + arr.stream()
                .filter(t -> !retailOutSheets.contains(t))
                .collect(Collectors.joining(StringPool.STR_SPLIT_CN)) + "）”不存在");
      }

      data.setRetailOutSheetIds(new HashMap<>());
      for (RetailOutSheet retailOutSheet : retailOutSheets) {
        data.getRetailOutSheetIds().put(retailOutSheet.getId(), retailOutSheet.getCode());
      }
    }

    if (StringUtil.isBlank(data.getLogisticsCompanyCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“物流公司编号”不能为空");
    }
    LogisticsCompanyService logisticsCompanyService = ApplicationUtil.getBean(
        LogisticsCompanyService.class);
    Wrapper<LogisticsCompany> queryLogisticsCompanyWrapper = Wrappers.lambdaQuery(
        LogisticsCompany.class).eq(LogisticsCompany::getCode, data.getLogisticsCompanyCode());
    LogisticsCompany logisticsCompany = logisticsCompanyService.getOne(
        queryLogisticsCompanyWrapper);
    if (logisticsCompany == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“物流公司编号”不存在");
    }
    data.setLogisticsCompanyId(logisticsCompany.getId());

    if (StringUtil.isBlank(data.getSenderName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人姓名”不能为空");
    }
    if (StringUtil.isBlank(data.getSenderTelephone())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人联系电话”不能为空");
    }
    if (StringUtil.isBlank(data.getSenderProvinceName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人省”不能为空");
    }
    if (StringUtil.isBlank(data.getSenderCityName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人市”不能为空");
    }
    if (StringUtil.isBlank(data.getSenderDistrictName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人区”不能为空");
    }

    DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
    List<DicCityDto> allCitys = dicCityService.getAll();
    DicCityDto senderProvince = allCitys.stream()
        .filter(t -> StringUtil.isEmpty(t.getParentId()) && t.getName()
            .equals(data.getSenderProvinceName()))
        .findFirst().orElse(null);
    if (senderProvince == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人省”不存在");
    }
    data.setSenderProvinceId(senderProvince.getId());

    DicCityDto senderCity = allCitys.stream()
        .filter(t -> senderProvince.getId().equals(t.getParentId()) && t.getName()
            .equals(data.getSenderCityName()))
        .findFirst().orElse(null);
    if (senderCity == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人市”不存在");
    }
    data.setSenderCityId(senderCity.getId());

    DicCityDto senderDistrict = allCitys.stream()
        .filter(t -> senderCity.getId().equals(t.getParentId()) && t.getName()
            .equals(data.getSenderDistrictName()))
        .findFirst().orElse(null);
    if (senderDistrict == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人区”错误不存在");
    }
    data.setSenderDistrictId(senderDistrict.getId());

    if (StringUtil.isBlank(data.getSenderAddress())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“寄件人地址”不能为空");
    }

    if (StringUtil.isBlank(data.getReceiverName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人姓名”不能为空");
    }
    if (StringUtil.isBlank(data.getReceiverTelephone())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人联系电话”不能为空");
    }
    if (StringUtil.isBlank(data.getReceiverProvinceName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人省”不能为空");
    }
    if (StringUtil.isBlank(data.getReceiverCityName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人市”不能为空");
    }
    if (StringUtil.isBlank(data.getReceiverDistrictName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人区”不能为空");
    }

    DicCityDto receiverProvince = allCitys.stream()
        .filter(t -> StringUtil.isEmpty(t.getParentId()) && t.getName()
            .equals(data.getReceiverProvinceName()))
        .findFirst().orElse(null);
    if (receiverProvince == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人省”不存在");
    }
    data.setReceiverProvinceId(receiverProvince.getId());

    DicCityDto receiverCity = allCitys.stream()
        .filter(t -> receiverProvince.getId().equals(t.getParentId()) && t.getName()
            .equals(data.getReceiverCityName()))
        .findFirst().orElse(null);
    if (receiverCity == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人市”不存在");
    }
    data.setReceiverCityId(receiverCity.getId());

    DicCityDto receiverDistrict = allCitys.stream()
        .filter(t -> receiverCity.getId().equals(t.getParentId()) && t.getName()
            .equals(data.getReceiverDistrictName()))
        .findFirst().orElse(null);
    if (receiverDistrict == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人区”错误不存在");
    }
    data.setReceiverDistrictId(receiverDistrict.getId());

    if (StringUtil.isBlank(data.getReceiverAddress())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“收件人地址”不能为空");
    }
    if (data.getTotalWeight() != null) {
      if (NumberUtil.lt(data.getTotalWeight(), BigDecimal.ZERO)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“总重量”不能小于0");
      }
      if (!NumberUtil.isNumberPrecision(data.getTotalWeight(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“总重量”最多允许2位小数");
      }
    }

    if (data.getTotalVolume() != null) {
      if (NumberUtil.lt(data.getTotalVolume(), BigDecimal.ZERO)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“总体积”不能小于0");
      }
      if (!NumberUtil.isNumberPrecision(data.getTotalVolume(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“总体积”最多允许2位小数");
      }
    }

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

    LogisticsSheetDetailService logisticsSheetDetailService = ApplicationUtil.getBean(
        LogisticsSheetDetailService.class);

    List<LogisticsSheetImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      LogisticsSheetImportModel data = datas.get(i);
      if (CollectionUtil.isNotEmpty(data.getSaleOutSheetIds())) {
        for (Entry<String, String> entry : data.getSaleOutSheetIds().entrySet()) {
          if (logisticsSheetDetailService.getByBizId(entry.getKey(),
              LogisticsSheetDetailBizType.SALE_OUT_SHEET) != null) {
            throw new DefaultClientException(
                "销售出库单【" + entry.getValue() + "】已关联物流单，不允许再次关联物流单");
          }
        }
      }

      if (CollectionUtil.isNotEmpty(data.getRetailOutSheetIds())) {
        for (Entry<String, String> entry : data.getRetailOutSheetIds().entrySet()) {
          if (logisticsSheetDetailService.getByBizId(entry.getKey(),
              LogisticsSheetDetailBizType.RETAIL_OUT_SHEET) != null) {
            throw new DefaultClientException(
                "零售出库单【" + entry.getValue() + "】已关联物流单，不允许再次关联物流单");
          }
        }
      }

      CreateLogisticsSheetVo createLogisticsSheetVo = new CreateLogisticsSheetVo();
      createLogisticsSheetVo.setLogisticsNo(data.getLogisticsNo());
      createLogisticsSheetVo.setLogisticsCompanyId(data.getLogisticsCompanyId());
      createLogisticsSheetVo.setSenderName(data.getSenderName());
      createLogisticsSheetVo.setSenderTelephone(data.getSenderTelephone());
      createLogisticsSheetVo.setSenderProvinceId(data.getSenderProvinceId());
      createLogisticsSheetVo.setSenderCityId(data.getSenderCityId());
      createLogisticsSheetVo.setSenderDistrictId(data.getSenderDistrictId());
      createLogisticsSheetVo.setSenderAddress(data.getSenderAddress());
      createLogisticsSheetVo.setReceiverName(data.getReceiverName());
      createLogisticsSheetVo.setReceiverTelephone(data.getReceiverTelephone());
      createLogisticsSheetVo.setReceiverProvinceId(data.getReceiverProvinceId());
      createLogisticsSheetVo.setReceiverCityId(data.getReceiverCityId());
      createLogisticsSheetVo.setReceiverDistrictId(data.getReceiverDistrictId());
      createLogisticsSheetVo.setReceiverAddress(data.getReceiverAddress());
      createLogisticsSheetVo.setTotalWeight(data.getTotalWeight());
      createLogisticsSheetVo.setTotalVolume(data.getTotalVolume());
      createLogisticsSheetVo.setTotalAmount(data.getTotalAmount());
      createLogisticsSheetVo.setDescription(data.getDescription());
      createLogisticsSheetVo.setBizOrders(new ArrayList<>());
      if (CollectionUtil.isNotEmpty(data.getSaleOutSheetIds())) {
        for (Entry<String, String> entry : data.getSaleOutSheetIds().entrySet()) {
          LogisticsSheetBizOrderVo bizOrderVo = new LogisticsSheetBizOrderVo();
          bizOrderVo.setBizId(entry.getKey());
          bizOrderVo.setBizType(LogisticsSheetDetailBizType.SALE_OUT_SHEET.getCode());
          createLogisticsSheetVo.getBizOrders().add(bizOrderVo);
        }
      }

      if (CollectionUtil.isNotEmpty(data.getRetailOutSheetIds())) {
        for (Entry<String, String> entry : data.getRetailOutSheetIds().entrySet()) {
          LogisticsSheetBizOrderVo bizOrderVo = new LogisticsSheetBizOrderVo();
          bizOrderVo.setBizId(entry.getKey());
          bizOrderVo.setBizType(LogisticsSheetDetailBizType.RETAIL_OUT_SHEET.getCode());
          createLogisticsSheetVo.getBizOrders().add(bizOrderVo);
        }
      }

      try {
        logisticsSheetService.create(createLogisticsSheetVo);
      } catch (Exception e) {
        throw new DefaultClientException(
            "第" + (i + 1) + "行新增失败，失败原因：" + e.getMessage());
      }

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {

  }
}
