package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.sale.ISaleConfigService;
import com.lframework.xingyun.sc.facade.SaleConfigFeignClient;
import com.lframework.xingyun.sc.facade.entity.SaleConfig;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售参数设置
 *
 * @author zmj
 */
@Api(tags = "销售参数设置")
@Validated
@RestController
public class SaleConfigClient extends DefaultClient implements SaleConfigFeignClient {

  @Autowired
  private ISaleConfigService saleConfigService;

  @Override
  public ApiInvokeResult<SaleConfig> get() {
    return ApiInvokeResultBuilder.success(saleConfigService.get());
  }
}
