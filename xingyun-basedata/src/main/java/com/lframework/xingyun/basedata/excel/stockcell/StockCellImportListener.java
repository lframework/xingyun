package com.lframework.xingyun.basedata.excel.stockcell;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.excel.ExcelImportListener;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.enums.StockCellType;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import com.lframework.xingyun.basedata.service.storecenter.StoreCenterService;
import com.lframework.xingyun.basedata.vo.stockcell.CreateStockCellVo;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockCellImportListener extends ExcelImportListener<StockCellImportModel> {

  @Override
  protected void doInvoke(StockCellImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getScCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不能为空");
    }

    StoreCenterService storeCenterService = ApplicationUtil.getBean(StoreCenterService.class);
    Wrapper<StoreCenter> queryWrapper = Wrappers.lambdaQuery(StoreCenter.class)
        .eq(StoreCenter::getCode, data.getScCode()).eq(StoreCenter::getAvailable, true);
    StoreCenter sc = storeCenterService.getOne(queryWrapper);
    if (sc == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓库编号”不存在");
    }
    data.setScId(sc.getId());
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex()
              + "行“编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“名称”不能为空");
    }

    if (StringUtil.isBlank(data.getCellType())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓位类别”不能为空");
    }

    if (EnumUtil.getByDesc(StockCellType.class, data.getCellType()) == null) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“仓位类别”只能填写“" + Arrays.stream(
              StockCellType.values()).map(
              StockCellType::getDesc
          ).collect(Collectors.joining(StringPool.STR_SPLIT_CN)) + "”");
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    StockCellService stockCellService = ApplicationUtil.getBean(StockCellService.class);
    List<StockCellImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      StockCellImportModel data = datas.get(i);

      CreateStockCellVo vo = new CreateStockCellVo();
      vo.setScId(data.getScId());
      vo.setCode(data.getCode());
      vo.setName(data.getName());
      vo.setCellType(EnumUtil.getByDesc(StockCellType.class, data.getCellType()).getCode());
      vo.setDescription(data.getDescription());

      try {
        stockCellService.create(vo);
      } catch (Exception e) {
        if (!(e instanceof ClientException)) {
          log.error(e.getMessage(), e);
        }
        throw new DefaultClientException("第" + (i + 1) + "行数据导入失败，原因：" + e.getMessage());
      }

      this.setSuccessProcessByIndex(i);
    }
  }

  @Override
  protected void doComplete() {
  }
}
