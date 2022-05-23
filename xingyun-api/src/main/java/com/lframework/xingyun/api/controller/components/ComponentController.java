package com.lframework.xingyun.api.controller.components;

import cn.hutool.crypto.SecureUtil;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.mybatis.entity.SysParameter;
import com.lframework.starter.mybatis.service.system.ISysParameterService;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.HttpUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.api.bo.components.MapLocationBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共组件 Controller
 */
@Slf4j
@Api(tags = "公共组件")
@Validated
@RestController
@RequestMapping("/component")
public class ComponentController extends DefaultBaseController {

  @Autowired
  private ISysParameterService sysParameterService;

  @ApiOperation("根据地址查询经纬度")
  @GetMapping("/map/location")
  public InvokeResult<MapLocationBo> getMapLocation(@NotEmpty(message = "地址不能为空！") String address) {

    SysParameter key = sysParameterService.findRequiredByKey("tx-map.key");
    SysParameter secret = sysParameterService.findRequiredByKey("tx-map.secret");

    // 请求腾讯地图WebService Api
    // Api文档地址：https://lbs.qq.com/service/webService/webServiceGuide/webServiceGeocoder
    String baseUrl = "https://apis.map.qq.com/";
    String uri = "/ws/geocoder/v1/";
    String reqParams = "?address=" + address + "&key=" + key.getPmValue();

    // Api使用签名方式
    // 签名文档：https://lbs.qq.com/faq/serverFaq/webServiceKey
    String sign = SecureUtil.md5(uri + reqParams + secret.getPmValue());
    Map<String, Object> reqMap = new HashMap<>();
    reqMap.put("address", address);
    reqMap.put("key", key.getPmValue());
    reqMap.put("sig", sign);

    try {
      String resp = HttpUtil.doGet(baseUrl + uri, reqMap);
      Map<String, Object> respMap = JsonUtil.parseObject(resp, Map.class);
      if (!"0".equals(String.valueOf(respMap.get("status")))) {
        throw new DefaultClientException(String.valueOf(respMap.get("message")));
      }

      Map<String, Object> result = (Map<String, Object>) respMap.get("result");
      Map<String, Object> location = (Map<String, Object>) result.get("location");
      MapLocationBo bo = new MapLocationBo();
      bo.setLng(new BigDecimal(String.valueOf(location.get("lng"))));
      bo.setLat(new BigDecimal(String.valueOf(location.get("lat"))));

      return InvokeResultBuilder.success(bo);
    } catch (ClientException e) {
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new DefaultClientException("解析地址失败，请稍后再试！");
    }
  }
}
