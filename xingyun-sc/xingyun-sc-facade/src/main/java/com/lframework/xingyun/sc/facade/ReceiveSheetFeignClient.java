package com.lframework.xingyun.sc.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
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
@FeignClient(value = "xingyun-sc-api", contextId = "ReceiveSheetFeignClient")
public interface ReceiveSheetFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/purchase/receive/sheet/getById")
  ApiInvokeResult<ReceiveSheet> getById(@RequestParam String id);

  /**
   * 查询已审核列表
   *
   * @param supplierId
   * @param startTime
   * @param endTime
   * @return
   */
  @GetMapping("/facade/purchase/receive/sheet/getApprovedList")
  ApiInvokeResult<List<ReceiveSheet>> getApprovedList(@RequestParam String supplierId,
      @RequestParam(required = false) LocalDateTime startTime,
      @RequestParam(required = false) LocalDateTime endTime,
      @RequestParam SettleStatus settleStatus);

  /**
   * 设置成未结算
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/purchase/receive/sheet/setUnSettle")
  ApiInvokeResult<Integer> setUnSettle(@RequestParam String id);

  /**
   * 设置成结算中
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/purchase/receive/sheet/setPartSettle")
  ApiInvokeResult<Integer> setPartSettle(@RequestParam String id);

  /**
   * 设置成已结算
   *
   * @param id
   * @return
   */
  @PatchMapping("/facade/purchase/receive/sheet/setSettled")
  ApiInvokeResult<Integer> setSettled(@RequestParam String id);

  /**
   * 根据供应商ID查询默认付款日期
   *
   * @param supplierId
   */
  @PatchMapping("/facade/purchase/receive/sheet/getPaymentDate")
  ApiInvokeResult<GetPaymentDateDto> getPaymentDate(@RequestParam String supplierId);
}
