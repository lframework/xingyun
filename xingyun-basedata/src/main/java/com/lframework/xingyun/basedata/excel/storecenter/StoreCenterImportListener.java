package com.lframework.xingyun.basedata.excel.storecenter;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.excel.ExcelImportListener;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.DicCityService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreCenterImportListener extends ExcelImportListener<StoreCenterImportModel> {

  private List<String> checkList = new ArrayList<>();

  @Override
  protected void doInvoke(StoreCenterImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex()
              + "行“编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (checkList.contains(data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”与第" + (checkList.indexOf(data.getCode()) + 1) + "行重复");
    }
    checkList.add(data.getCode());
    Wrapper<StoreCenter> checkWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, data.getCode());
    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    if (storeCenterService.count(checkWrapper) > 0) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”已存在");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“名称”不能为空");
    }
    if (!StringUtil.isBlank(data.getCity())) {
      String[] arr = data.getCity().split("/");
      if (arr.length != 3) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex()
                + "行“地区”格式错误，应为省/市/区（县），例如：北京市/市辖区/东城区");
      }

      DicCityService dicCityService = ApplicationUtil.getBean(DicCityService.class);
      List<DicCityDto> allCitys = dicCityService.getAll();
      DicCityDto province = allCitys.stream()
          .filter(t -> StringUtil.isEmpty(t.getParentId()) && t.getName().equals(arr[0]))
          .findFirst().orElse(null);
      if (province == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“地区”错误，省份不存在");
      }
      DicCityDto city = allCitys.stream()
          .filter(t -> province.getId().equals(t.getParentId()) && t.getName().equals(arr[1]))
          .findFirst().orElse(null);
      if (city == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“地区”错误，市不存在");
      }
      DicCityDto area = allCitys.stream()
          .filter(t -> city.getId().equals(t.getParentId()) && t.getName().equals(arr[2]))
          .findFirst().orElse(null);
      if (area == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“地区”错误，区（县）不存在");
      }

      data.setAreaId(area.getId());
    }

    if (data.getPeopleNum() != null) {
      if (data.getPeopleNum() < 0) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“仓库人数”不允许小于0");
      }
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    List<StoreCenterImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      StoreCenterImportModel data = datas.get(i);

      StoreCenter record = new StoreCenter();
      record.setId(IdUtil.getId());

      record.setCode(data.getCode());
      record.setName(data.getName());
      record.setContact(data.getContact());
      record.setTelephone(data.getTelephone());
      record.setCityId(data.getAreaId());
      record.setAddress(data.getAddress());
      record.setPeopleNum(data.getPeopleNum());
      record.setDescription(
          StringUtil.isBlank(data.getDescription()) ? StringPool.EMPTY_STR : data.getDescription());
      record.setAvailable(Boolean.TRUE);

      storeCenterService.save(record);
      data.setId(record.getId());

      this.setSuccessProcessByIndex(i);
    }
  }

  @Override
  protected void doComplete() {
  }
}
