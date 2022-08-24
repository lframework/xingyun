package com.lframework.xingyun.sc.biz.impl.sale;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.StringUtil;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.mappers.SaleReturnMapper;
import com.lframework.xingyun.sc.biz.service.sale.ISaleReturnService;
import com.lframework.xingyun.sc.biz.service.sale.ISaleReturnTccService;
import com.lframework.xingyun.sc.facade.entity.SaleReturn;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleReturnTccServiceImpl implements ISaleReturnTccService {

  @Autowired
  private ISaleReturnService saleReturnService;

  @Autowired
  private SaleReturnMapper saleReturnMapper;

  @Override
  public Integer setUnSettle(String id) {
    SaleReturn record = saleReturnService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }

    Wrapper<SaleReturn> updateWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getTxId, RootContext.getXID()).eq(SaleReturn::getId, id);
    saleReturnService.update(updateWrapper);

    return saleReturnService.setUnSettle(id);
  }

  @Override
  public boolean commitSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleReturn> updateWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getOriSettleStatus, null).set(SaleReturn::getTxId, null)
        .eq(SaleReturn::getId, id).eq(SaleReturn::getTxId, context.getXid())
        .eq(SaleReturn::getOriSettleStatus, SettleStatus.PART_SETTLE);
    saleReturnService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleReturn> updateWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getSettleStatus, SettleStatus.PART_SETTLE)
        .set(SaleReturn::getOriSettleStatus, null).set(SaleReturn::getTxId, null)
        .eq(SaleReturn::getId, id).eq(SaleReturn::getOriSettleStatus, SettleStatus.PART_SETTLE)
        .eq(SaleReturn::getTxId, context.getXid());
    saleReturnService.update(updateWrapper);
    return true;
  }

  @Override
  public Integer setPartSettle(String id) {
    SaleReturn record = saleReturnService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<SaleReturn> updateWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getTxId, RootContext.getXID()).eq(SaleReturn::getId, id);
    saleReturnService.update(updateWrapper);
    return saleReturnService.setPartSettle(id);
  }

  @Override
  public boolean commitSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleReturn> updateWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getOriSettleStatus, null).set(SaleReturn::getTxId, null)
        .eq(SaleReturn::getId, id).eq(SaleReturn::getTxId, context.getXid())
        .in(SaleReturn::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    saleReturnService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    saleReturnMapper.rollbackSetPartSettle(id, context.getXid());

    return true;
  }

  @Override
  public Integer setSettled(String id) {
    SaleReturn record = saleReturnService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<SaleReturn> updateWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getTxId, RootContext.getXID()).eq(SaleReturn::getId, id);
    saleReturnService.update(updateWrapper);
    return saleReturnService.setSettled(id);
  }

  @Override
  public boolean commitSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleReturn> updateWrapper = Wrappers.lambdaUpdate(SaleReturn.class)
        .set(SaleReturn::getOriSettleStatus, null).set(SaleReturn::getTxId, null)
        .eq(SaleReturn::getId, id).eq(SaleReturn::getTxId, context.getXid())
        .in(SaleReturn::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    saleReturnService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    saleReturnMapper.rollbackSetSettled(id, context.getXid());
    return true;
  }
}
