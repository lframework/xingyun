package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.entity.PurchaseReturn;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zmj
 * @since 2022/8/29
 */
@FeignClient(value = "xingyun-sc-api", contextId = "PurchaseReturnFeignClient")
public interface PurchaseReturnFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/purchase/return/getById")
  ApiInvokeResult<PurchaseReturn> getById(@RequestParam String id);

  /**
   * 查询已审核列表
   *
   * @param supplierId
   * @param startTime
   * @param endTime
   * @return
   */
  @GetMapping("/facade/purchase/return/getApprovedList")
  ApiInvokeResult<List<PurchaseReturn>> getApprovedList(@RequestParam String supplierId,
      @RequestParam(required = false) LocalDateTime startTime,
      @RequestParam(required = false) LocalDateTime endTime,
      @RequestParam SettleStatus settleStatus);

  /**
   * 设置成未结算
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/purchase/return/setUnSettle")
  ApiInvokeResult<Integer> setUnSettle(@RequestParam String id);

  /**
   * 设置成结算中
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/purchase/return/setPartSettle")
  ApiInvokeResult<Integer> setPartSettle(@RequestParam String id);

  /**
   * 设置成已结算
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/purchase/return/setSettled")
  ApiInvokeResult<Integer> setSettled(@RequestParam String id);
}
