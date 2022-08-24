package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.entity.SaleReturn;
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
@FeignClient(value = "xingyun-sc-api", contextId = "SaleReturnFeignClient")
public interface SaleReturnFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/sale/return/getById")
  ApiInvokeResult<SaleReturn> getById(@RequestParam String id);

  /**
   * 查询已审核列表
   *
   * @param customerId
   * @param startTime
   * @param endTime
   * @return
   */
  @GetMapping("/facade/sale/return/getApprovedList")
  ApiInvokeResult<List<SaleReturn>> getApprovedList(@RequestParam String customerId,
      @RequestParam(required = false) LocalDateTime startTime,
      @RequestParam(required = false) LocalDateTime endTime,
      @RequestParam SettleStatus settleStatus);

  /**
   * 设置成未结算
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/sale/return/setUnSettle")
  ApiInvokeResult<Integer> setUnSettle(@RequestParam String id);

  /**
   * 设置成结算中
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/sale/return/setPartSettle")
  ApiInvokeResult<Integer> setPartSettle(@RequestParam String id);

  /**
   * 设置成已结算
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/sale/return/setSettled")
  ApiInvokeResult<Integer> setSettled(@RequestParam String id);
}
