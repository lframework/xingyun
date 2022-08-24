package com.lframework.xingyun.sc.biz.impl.sale;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.utils.StringUtil;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.biz.mappers.SaleOutSheetMapper;
import com.lframework.xingyun.sc.biz.service.sale.ISaleOutSheetService;
import com.lframework.xingyun.sc.biz.service.sale.ISaleOutSheetTccService;
import com.lframework.xingyun.sc.facade.entity.SaleOutSheet;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleOutSheetTccServiceImpl implements ISaleOutSheetTccService {

  @Autowired
  private ISaleOutSheetService saleOutSheetService;

  @Autowired
  private SaleOutSheetMapper saleOutSheetMapper;

  @Override
  public Integer setUnSettle(String id) {
    SaleOutSheet record = saleOutSheetService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }

    Wrapper<SaleOutSheet> updateWrapper = Wrappers.lambdaUpdate(SaleOutSheet.class)
        .set(SaleOutSheet::getTxId, RootContext.getXID()).eq(SaleOutSheet::getId, id);
    saleOutSheetService.update(updateWrapper);

    return saleOutSheetService.setUnSettle(id);
  }

  @Override
  public boolean commitSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleOutSheet> updateWrapper = Wrappers.lambdaUpdate(SaleOutSheet.class)
        .set(SaleOutSheet::getOriSettleStatus, null).set(SaleOutSheet::getTxId, null)
        .eq(SaleOutSheet::getId, id).eq(SaleOutSheet::getTxId, context.getXid())
        .eq(SaleOutSheet::getOriSettleStatus, SettleStatus.PART_SETTLE);
    saleOutSheetService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetUnSettle(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleOutSheet> updateWrapper = Wrappers.lambdaUpdate(SaleOutSheet.class)
        .set(SaleOutSheet::getSettleStatus, SettleStatus.PART_SETTLE)
        .set(SaleOutSheet::getOriSettleStatus, null).set(SaleOutSheet::getTxId, null)
        .eq(SaleOutSheet::getId, id).eq(SaleOutSheet::getOriSettleStatus, SettleStatus.PART_SETTLE)
        .eq(SaleOutSheet::getTxId, context.getXid());
    saleOutSheetService.update(updateWrapper);
    return true;
  }

  @Override
  public Integer setPartSettle(String id) {
    SaleOutSheet record = saleOutSheetService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<SaleOutSheet> updateWrapper = Wrappers.lambdaUpdate(SaleOutSheet.class)
        .set(SaleOutSheet::getTxId, RootContext.getXID()).eq(SaleOutSheet::getId, id);
    saleOutSheetService.update(updateWrapper);
    return saleOutSheetService.setPartSettle(id);
  }

  @Override
  public boolean commitSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleOutSheet> updateWrapper = Wrappers.lambdaUpdate(SaleOutSheet.class)
        .set(SaleOutSheet::getOriSettleStatus, null).set(SaleOutSheet::getTxId, null)
        .eq(SaleOutSheet::getId, id).eq(SaleOutSheet::getTxId, context.getXid())
        .in(SaleOutSheet::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    saleOutSheetService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetPartSettle(BusinessActionContext context) {

    String id = (String) context.getActionContext().get("id");
    saleOutSheetMapper.rollbackSetPartSettle(id, context.getXid());

    return true;
  }

  @Override
  public Integer setSettled(String id) {
    SaleOutSheet record = saleOutSheetService.getById(id);
    if (record == null || !StringUtil.isEmpty(record.getTxId())) {
      return 0;
    }
    Wrapper<SaleOutSheet> updateWrapper = Wrappers.lambdaUpdate(SaleOutSheet.class)
        .set(SaleOutSheet::getTxId, RootContext.getXID()).eq(SaleOutSheet::getId, id);
    saleOutSheetService.update(updateWrapper);
    return saleOutSheetService.setSettled(id);
  }

  @Override
  public boolean commitSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    Wrapper<SaleOutSheet> updateWrapper = Wrappers.lambdaUpdate(SaleOutSheet.class)
        .set(SaleOutSheet::getOriSettleStatus, null).set(SaleOutSheet::getTxId, null)
        .eq(SaleOutSheet::getId, id).eq(SaleOutSheet::getTxId, context.getXid())
        .in(SaleOutSheet::getOriSettleStatus, SettleStatus.UN_SETTLE, SettleStatus.PART_SETTLE);
    saleOutSheetService.update(updateWrapper);

    return true;
  }

  @Override
  public boolean rollbackSetSettled(BusinessActionContext context) {
    String id = (String) context.getActionContext().get("id");
    saleOutSheetMapper.rollbackSetSettled(id, context.getXid());
    return true;
  }
}
