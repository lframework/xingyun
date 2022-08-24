package com.lframework.xingyun.basedata.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-basedata-api", contextId = "SupplierFeignClient")
public interface SupplierFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/basedata/supplier/findById")
  ApiInvokeResult<Supplier> findById(@RequestParam String id);

  /**
   * 根据编号查询
   *
   * @param code
   * @return
   */
  @GetMapping("/facade/basedata/supplier/getByCode")
  ApiInvokeResult<Supplier> getByCode(@RequestParam String code);
}
