package com.lframework.xingyun.sc.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.sc.biz.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.facade.RetailConfigFeignClient;
import com.lframework.xingyun.sc.facade.entity.RetailConfig;
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
public class RetailConfigClient extends DefaultClient implements RetailConfigFeignClient {

  @Autowired
  private IRetailConfigService retailConfigService;

  @Override
  public ApiInvokeResult<RetailConfig> get() {
    return ApiInvokeResultBuilder.success(retailConfigService.get());
  }
}
