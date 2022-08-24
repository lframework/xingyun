package com.lframework.xingyun.common.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.common.facade.dto.dic.city.DicCityDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-common-api", contextId = "DicCityFeignClient")
public interface DicCityFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询链路数据
   *
   * @param id 末级ID
   * @return 返回值顺序：省、市、区
   */
  @GetMapping("/facade/dic/city/getChainById")
  ApiInvokeResult<List<DicCityDto>> getChainById(@RequestParam String id);

  /**
   * 查询所有数据
   *
   * @return
   */
  @GetMapping("/facade/dic/city/getAll")
  ApiInvokeResult<List<DicCityDto>> getAll();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/dic/city/findById")
  ApiInvokeResult<DicCityDto> findById(@RequestParam String id);
}
