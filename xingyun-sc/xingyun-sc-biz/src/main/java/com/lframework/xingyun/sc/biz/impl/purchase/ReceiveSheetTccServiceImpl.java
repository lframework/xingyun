package com.lframework.xingyun.sc.biz.impl.purchase;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.StringUtil;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.mappers.ReceiveSheetMapper;
import com.lframework.xingyun.sc.biz.service.purchase.IReceiveSheetService;
import com.lframework.xingyun.sc.biz.service.purchase.IReceiveSheetTccService;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveSheetTccServiceImpl implements IReceiveSheetTccService {

  @Autowired
  private IReceiveSheetService receiveSheetService;

  @Autowired
  private ReceiveSheetMapper receiveSheetMapper;

  @Override
  public Integer setUnSettle(String id) {
    ReceiveSheet record = receiveSheetService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }

    Wrapper<ReceiveSheet> updateWrapper = Wrappers.lambdaUpdate(ReceiveSheet.class)
        .set(ReceiveSheet::getTxId, RootContext.getXID()).eq(ReceiveSheet::getId, id);
    receiveSheetService.update(updateWrapper);

    return receiveSheetService.setUnSettle(id);
  }

  @Override
  public boolean commitSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<ReceiveSheet> updateWrapper = Wrappers.lambdaUpdate(ReceiveSheet.class)
        .set(ReceiveSheet::getOriSettleStatus, null).set(ReceiveSheet::getTxId, null)
        .eq(ReceiveSheet::getId, id).eq(ReceiveSheet::getTxId, context.getXid())
        .eq(ReceiveSheet::getOriSettleStatus, SettleStatus.PART_SETTLE);
    receiveSheetService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<ReceiveSheet> updateWrapper = Wrappers.lambdaUpdate(ReceiveSheet.class)
        .set(ReceiveSheet::getSettleStatus, SettleStatus.PART_SETTLE)
        .set(ReceiveSheet::getOriSettleStatus, null).set(ReceiveSheet::getTxId, null)
        .eq(ReceiveSheet::getId, id).eq(ReceiveSheet::getOriSettleStatus, SettleStatus.PART_SETTLE)
        .eq(ReceiveSheet::getTxId, context.getXid());
    receiveSheetService.update(updateWrapper);
    return true;
  }

  @Override
  public Integer setPartSettle(String id) {
    ReceiveSheet record = receiveSheetService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<ReceiveSheet> updateWrapper = Wrappers.lambdaUpdate(ReceiveSheet.class)
        .set(ReceiveSheet::getTxId, RootContext.getXID()).eq(ReceiveSheet::getId, id);
    receiveSheetService.update(updateWrapper);

    return receiveSheetService.setPartSettle(id);
  }

  @Override
  public boolean commitSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    Wrapper<ReceiveSheet> updateWrapper = Wrappers.lambdaUpdate(ReceiveSheet.class)
        .set(ReceiveSheet::getOriSettleStatus, null).set(ReceiveSheet::getTxId, null)
        .eq(ReceiveSheet::getId, id).eq(ReceiveSheet::getTxId, context.getXid())
        .in(ReceiveSheet::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    receiveSheetService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    receiveSheetMapper.rollbackSetPartSettle(id, context.getXid());

    return true;
  }

  @Override
  public Integer setSettled(String id) {
    ReceiveSheet record = receiveSheetService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<ReceiveSheet> updateWrapper = Wrappers.lambdaUpdate(ReceiveSheet.class)
        .set(ReceiveSheet::getTxId, RootContext.getXID()).eq(ReceiveSheet::getId, id);
    receiveSheetService.update(updateWrapper);
    return receiveSheetService.setSettled(id);
  }

  @Override
  public boolean commitSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<ReceiveSheet> updateWrapper = Wrappers.lambdaUpdate(ReceiveSheet.class)
        .set(ReceiveSheet::getOriSettleStatus, null).set(ReceiveSheet::getTxId, null)
        .eq(ReceiveSheet::getId, id).eq(ReceiveSheet::getTxId, context.getXid())
        .in(ReceiveSheet::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    receiveSheetService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    receiveSheetMapper.rollbackSetSettled(id, context.getXid());
    return true;
  }
}
