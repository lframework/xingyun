package com.lframework.xingyun.sc.biz.service.purchase;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface IReceiveSheetTccService {

  /**
   * 设置成未结算
   *
   * @param id
   * @return
   */
  @TwoPhaseBusinessAction(name = "receiveSheetTccServiceImpl", commitMethod = "commitSetUnSettle", rollbackMethod = "rollbackSetUnSettle")
  Integer setUnSettle(@BusinessActionContextParameter(paramName = "id") String id);

  /**
   * 设置成未结算（提交）
   *
   * @param context
   * @return
   */
  boolean commitSetUnSettle(BusinessActionContext context);

  /**
   * 设置成未结算（回滚）
   *
   * @param context
   * @return
   */
  boolean rollbackSetUnSettle(BusinessActionContext context);

  /**
   * 设置成结算中
   *
   * @param id
   * @return
   */
  @TwoPhaseBusinessAction(name = "receiveSheetTccServiceImpl", commitMethod = "commitSetPartSettle", rollbackMethod = "rollbackSetPartSettle")
  Integer setPartSettle(@BusinessActionContextParameter(paramName = "id") String id);

  /**
   * 设置成结算中（提交）
   *
   * @param context
   * @return
   */
  boolean commitSetPartSettle(BusinessActionContext context);

  /**
   * 设置成结算中（回滚）
   *
   * @param context
   * @return
   */
  boolean rollbackSetPartSettle(BusinessActionContext context);

  /**
   * 设置成已结算
   *
   * @param id
   * @return
   */
  @TwoPhaseBusinessAction(name = "receiveSheetTccServiceImpl", commitMethod = "commitSetSettled", rollbackMethod = "rollbackSetSettled")
  Integer setSettled(String id);

  /**
   * 设置成已结算（提交）
   *
   * @param context
   * @return
   */
  boolean commitSetSettled(BusinessActionContext context);

  /**
   * 设置成已结算（回滚）
   *
   * @param context
   * @return
   */
  boolean rollbackSetSettled(BusinessActionContext context);
}
