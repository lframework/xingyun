package com.lframework.xingyun.sc.impl.stock.warning;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.sc.entity.ProductStockWarningNotify;
import com.lframework.xingyun.sc.enums.ScOpLogType;
import com.lframework.xingyun.sc.mappers.ProductStockWarningNotifyMapper;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningNotifyService;
import java.time.LocalDateTime;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockWarningNotifyServiceImpl extends
    BaseMpServiceImpl<ProductStockWarningNotifyMapper, ProductStockWarningNotify> implements
    ProductStockWarningNotifyService {

  @Cacheable(cacheNames = "product_stock_warning_notify", key = "@cacheVariables.tenantId() + 'lastNotifyTime' + '_' + #warningId + '_' + #groupId", unless = "#result == null")
  @Override
  public LocalDateTime getLastNotifyTime(String warningId, String groupId) {
    return null;
  }

  @CachePut(cacheNames = "product_stock_warning_notify", key = "@cacheVariables.tenantId() + 'lastNotifyTime' + '_' + #warningId + '_' + #groupId")
  @Override
  public LocalDateTime setLastNotifyTime(String warningId, String groupId) {

    return LocalDateTime.now();
  }

  @OpLog(type = ScOpLogType.STOCK_WARNING, name = "新增消息通知组，ID：{}", params = "#groupId")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void createSetting(String groupId) {

    Wrapper<ProductStockWarningNotify> checkWrapper = Wrappers.lambdaQuery(
            ProductStockWarningNotify.class)
        .eq(ProductStockWarningNotify::getNotifyGroupId, groupId);
    if (count(checkWrapper) > 0) {
      throw new DefaultClientException("消息通知组已设置，不允许重复设置！");
    }

    ProductStockWarningNotify record = new ProductStockWarningNotify();
    record.setId(IdUtil.getId());
    record.setNotifyGroupId(groupId);

    save(record);
  }

  @OpLog(type = ScOpLogType.STOCK_WARNING, name = "删除消息通知组，ID：{}", params = "#groupId")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteSetting(String groupId) {
    Wrapper<ProductStockWarningNotify> deleteWrapper = Wrappers.lambdaQuery(
            ProductStockWarningNotify.class)
        .eq(ProductStockWarningNotify::getNotifyGroupId, groupId);
    remove(deleteWrapper);
  }
}
