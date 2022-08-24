package com.lframework.xingyun.common.api.facade;

import com.lframework.starter.cloud.controller.DefaultClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.starter.cloud.resp.ApiInvokeResultBuilder;
import com.lframework.xingyun.common.biz.service.IDicCityService;
import com.lframework.xingyun.common.facade.DicCityFeignClient;
import com.lframework.xingyun.common.facade.dto.dic.city.DicCityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "城市字典")
@Validated
@RestController
public class DicCityClient extends DefaultClient implements DicCityFeignClient {

  @Autowired
  private IDicCityService dicCityService;


  @ApiOperation("根据ID查询链路数据")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<List<DicCityDto>> getChainById(@RequestParam String id) {

    return ApiInvokeResultBuilder.success(dicCityService.getChainById(id));
  }

  @ApiOperation("查询所有数据")
  @Override
  public ApiInvokeResult<List<DicCityDto>> getAll() {
    return ApiInvokeResultBuilder.success(dicCityService.getAll());
  }

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @Override
  public ApiInvokeResult<DicCityDto> findById(@RequestParam String id) {
    return ApiInvokeResultBuilder.success(dicCityService.findById(id));
  }
}
