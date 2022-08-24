package com.lframework.xingyun.sc.biz.impl.purchase;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.StringUtil;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.mappers.PurchaseReturnMapper;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseReturnService;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseReturnTccService;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturn;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseReturnTccServiceImpl implements IPurchaseReturnTccService {

  @Autowired
  private IPurchaseReturnService purchaseReturnService;

  @Autowired
  private PurchaseReturnMapper purchaseReturnMapper;

  @Override
  public Integer setUnSettle(String id) {
    PurchaseReturn record = purchaseReturnService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }

    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getTxId, RootContext.getXID()).eq(PurchaseReturn::getId, id);
    purchaseReturnService.update(updateWrapper);

    return purchaseReturnService.setUnSettle(id);
  }

  @Override
  public boolean commitSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getOriSettleStatus, null).set(PurchaseReturn::getTxId, null)
        .eq(PurchaseReturn::getId, id).eq(PurchaseReturn::getTxId, context.getXid())
        .eq(PurchaseReturn::getOriSettleStatus, SettleStatus.PART_SETTLE);
    purchaseReturnService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getSettleStatus, SettleStatus.PART_SETTLE)
        .set(PurchaseReturn::getOriSettleStatus, null).set(PurchaseReturn::getTxId, null)
        .eq(PurchaseReturn::getId, id)
        .eq(PurchaseReturn::getOriSettleStatus, SettleStatus.PART_SETTLE)
        .eq(PurchaseReturn::getTxId, context.getXid());
    purchaseReturnService.update(updateWrapper);
    return true;
  }

  @Override
  public Integer setPartSettle(String id) {
    PurchaseReturn record = purchaseReturnService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getTxId, RootContext.getXID()).eq(PurchaseReturn::getId, id);
    purchaseReturnService.update(updateWrapper);
    return purchaseReturnService.setPartSettle(id);
  }

  @Override
  public boolean commitSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getOriSettleStatus, null).set(PurchaseReturn::getTxId, null)
        .eq(PurchaseReturn::getId, id).eq(PurchaseReturn::getTxId, context.getXid())
        .in(PurchaseReturn::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    purchaseReturnService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    purchaseReturnMapper.rollbackSetPartSettle(id, context.getXid());

    return true;
  }

  @Override
  public Integer setSettled(String id) {
    PurchaseReturn record = purchaseReturnService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getTxId, RootContext.getXID()).eq(PurchaseReturn::getId, id);
    purchaseReturnService.update(updateWrapper);
    return purchaseReturnService.setSettled(id);
  }

  @Override
  public boolean commitSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<PurchaseReturn> updateWrapper = Wrappers.lambdaUpdate(PurchaseReturn.class)
        .set(PurchaseReturn::getOriSettleStatus, null).set(PurchaseReturn::getTxId, null)
        .eq(PurchaseReturn::getId, id).eq(PurchaseReturn::getTxId, context.getXid())
        .in(PurchaseReturn::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    purchaseReturnService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    purchaseReturnMapper.rollbackSetSettled(id, context.getXid());
    return true;
  }
}
