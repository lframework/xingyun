package com.lframework.xingyun.sc.listeners.mq;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.locker.LockBuilder;
import com.lframework.starter.common.locker.Locker;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mq.core.service.MqProducerService;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.web.inner.dto.notify.SysNotifyDto;
import com.lframework.starter.web.inner.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.core.queue.MqStringPool;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import com.lframework.xingyun.sc.entity.ProductStockWarningNotify;
import com.lframework.xingyun.sc.impl.stock.warning.ProductStockWarningSysNotifyRule;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningNotifyService;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component("productStockWarningStockChangeListener")
public class ProductStockWarningStockChangeListener {

  @Autowired
  private ProductStockWarningService productStockWarningService;

  @Autowired
  private LockBuilder lockBuilder;

  @Autowired
  private ProductStockWarningNotifyService productStockWarningNotifyService;

  @Autowired
  private MqProducerService mqProducerService;

  @Autowired
  private ProductService productService;

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = "product_stock_warning.add_stock"), exchange = @Exchange(value = MqStringPool.ADD_STOCK_EXCHANGE, type = ExchangeTypes.FANOUT))})
  public void addStock(Message<ProductStockChangeDto> message) {
    handleStockChange(message, true);
  }

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = "product_stock_warning.sub_stock"), exchange = @Exchange(value = MqStringPool.SUB_STOCK_EXCHANGE, type = ExchangeTypes.FANOUT))})
  public void subStock(Message<ProductStockChangeDto> message) {
    handleStockChange(message, false);
  }

  private void handleStockChange(Message<ProductStockChangeDto> message, boolean isAdd) {
    ProductStockChangeDto dto = message.getPayload();
    ProductStockWarning productStockWarning = getProductStockWarning(dto.getScId(),
        dto.getProductId());
    if (productStockWarning == null) {
      return;
    }

    Integer currentStock = dto.getCurStockNum();
    if ((isAdd && productStockWarning.getMaxLimit() <= currentStock) || (!isAdd
        && productStockWarning.getMinLimit() >= currentStock)) {
      log.info("scId = {}, productId = {}, 预警{}限 = {}, 当前库存 = {}, 开始预警",
          dto.getScId(), dto.getProductId(), isAdd ? "上" : "下",
          isAdd ? productStockWarning.getMaxLimit() : productStockWarning.getMinLimit(),
          currentStock);

      List<ProductStockWarningNotify> notifyList = productStockWarningNotifyService.list();
      if (CollectionUtil.isEmpty(notifyList)) {
        log.info("没有设置预警通知组，不发送消息");
        return;
      }

      try {
        Product product = productService.findById(dto.getProductId());
        if (product == null) {
          log.warn("商品 {} 不存在", dto.getProductId());
          return;
        }

        Locker locker = lockBuilder.buildLocker(
            "product_stock_warning_" + dto.getScId() + "_" + dto.getProductId(),
            60000L,
            5000L);
        if (locker.lock()) {
          try {
            sendNotifications(notifyList, product, productStockWarning, currentStock, isAdd);
          } finally {
            locker.unLock();
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }
  }

  private void sendNotifications(List<ProductStockWarningNotify> notifyList, Product product,
      ProductStockWarning productStockWarning, Integer currentStock, boolean isAdd) {
    for (ProductStockWarningNotify notify : notifyList) {
      // 预警增加时效，不能一直预警
      LocalDateTime lastNotifyTime = productStockWarningNotifyService.getLastNotifyTime(
          productStockWarning.getId(), notify.getNotifyGroupId());
      if (lastNotifyTime != null && !lastNotifyTime.plusHours(3).isBefore(LocalDateTime.now())) {
        // 预警超过3个小时才会重新预警
        log.info("warningId {}, notifyGruopId {}, 距离上次预警不足3小时，不重复提醒",
            productStockWarning.getId(), notify.getNotifyGroupId());
        continue;
      }

      log.info("开始发送预警，通知组ID = {}", notify.getId());
      SysNotifyDto sysNotifyDto = new SysNotifyDto();
      Map<String, Object> variables = new HashMap<>(6, 1);
      variables.put("productCode", product.getCode());
      variables.put("productName", product.getName());
      variables.put("bizType", isAdd ? 1 : 0);
      variables.put("currentStock", currentStock);
      variables.put("maxLimit", productStockWarning.getMaxLimit());
      variables.put("minLimit", productStockWarning.getMinLimit());

      sysNotifyDto.setVariables(JsonUtil.toJsonString(variables));
      sysNotifyDto.setBizType(ProductStockWarningSysNotifyRule.BIZ_TYPE);
      sysNotifyDto.setNotifyGroupId(notify.getNotifyGroupId());

      try {
        mqProducerService.createSysNotify(sysNotifyDto);
        productStockWarningNotifyService.setLastNotifyTime(productStockWarning.getId(),
            notify.getNotifyGroupId());
      } catch (Exception e) {
        log.error("发送通知失败: {}", e.getMessage(), e);
      }
    }
  }

  private ProductStockWarning getProductStockWarning(String scId, String productId) {
    return productStockWarningService.getOne(
        Wrappers.lambdaQuery(ProductStockWarning.class).eq(ProductStockWarning::getScId, scId)
            .eq(ProductStockWarning::getProductId, productId)
            .eq(ProductStockWarning::getAvailable, Boolean.TRUE));
  }
}
