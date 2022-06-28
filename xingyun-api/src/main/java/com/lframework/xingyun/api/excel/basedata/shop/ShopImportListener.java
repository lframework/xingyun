package com.lframework.xingyun.api.excel.basedata.shop;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.PatternPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.components.excel.ExcelImportListener;
import com.lframework.starter.mybatis.entity.DefaultSysDept;
import com.lframework.starter.mybatis.service.system.ISysDeptService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.service.shop.IShopService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShopImportListener extends ExcelImportListener<ShopImportModel> {

  @Override
  protected void doInvoke(ShopImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“名称”不能为空");
    }
    if (!StringUtil.isBlank(data.getDeptCode())) {
      ISysDeptService sysDeptService = ApplicationUtil.getBean(ISysDeptService.class);
      Wrapper<DefaultSysDept> queryWrapper = Wrappers.lambdaQuery(DefaultSysDept.class)
          .eq(DefaultSysDept::getCode, data.getDeptCode());
      DefaultSysDept dept = sysDeptService.getOne(queryWrapper);
      if (dept == null) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“所属部门编号”不存在");
      }
      data.setDeptId(dept.getId());
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    IShopService shopService = ApplicationUtil.getBean(IShopService.class);

    List<ShopImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ShopImportModel data = datas.get(i);

      boolean isInsert = false;
      Wrapper<Shop> queryWrapper = Wrappers.lambdaQuery(Shop.class)
          .eq(Shop::getCode, data.getCode());
      Shop record = shopService.getOne(queryWrapper);
      if (record == null) {
        record = new Shop();

        record.setId(IdUtil.getId());
        isInsert = true;
      }

      record.setCode(data.getCode());
      record.setName(data.getName());
      record.setDeptId(data.getDeptId());
      record.setDescription(data.getDescription());

      if (isInsert) {
        record.setAvailable(Boolean.TRUE);
      }

      shopService.saveOrUpdate(record);

      data.setId(record.getId());

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
    IShopService shopService = ApplicationUtil.getBean(IShopService.class);
    for (ShopImportModel data : this.getDatas()) {
      shopService.cleanCacheByKey(data.getId());
    }
  }
}
