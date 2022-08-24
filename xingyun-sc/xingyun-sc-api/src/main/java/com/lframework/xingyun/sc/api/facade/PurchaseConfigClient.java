package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.facade.PurchaseConfigFeignClient;
import com.lframework.xingyun.sc.facade.entity.PurchaseConfig;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采购参数设置
 *
 * @author zmj
 */
@Api(tags = "采购参数设置")
@Validated
@RestController
public class PurchaseConfigClient extends DefaultClient implements
    PurchaseConfigFeignClient {

  @Autowired
  private IPurchaseConfigService purchaseConfigService;

  @Override
  public ApiInvokeResult<PurchaseConfig> get() {
    return ApiInvokeResultBuilder.success(purchaseConfigService.get());
  }
}
