package com.lframework.xingyun.common.controller.components;

import cn.hutool.crypto.SecureUtil;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.web.bo.ExcelImportBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.service.SysParameterService;
import com.lframework.starter.web.utils.ExcelImportUtil;
import com.lframework.starter.web.utils.HttpUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.UploadUtil;
import com.lframework.xingyun.common.bo.components.MapLocationBo;
import com.lframework.xingyun.common.bo.components.OrderTimeLineBo;
import com.lframework.xingyun.core.entity.OrderTimeLine;
import com.lframework.xingyun.core.service.OrderTimeLineService;
import com.lframework.xingyun.template.core.service.GenerateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
  private SysParameterService sysParameterService;

  @Autowired
  private OrderTimeLineService orderTimeLineService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @ApiOperation("查询导入Excel任务")
  @GetMapping("/import/task")
  public InvokeResult<ExcelImportBo> getExcelImportTask(@NotBlank(message = "ID不能为空！") String id) {

    return InvokeResultBuilder.success(ExcelImportUtil.getTask(id));
  }

  @ApiOperation("获取地图Key")
  @GetMapping("/map/key")
  public InvokeResult<String> getMapKey() {
    String key = sysParameterService.findRequiredByKey("tx-map.key");

    return InvokeResultBuilder.success(key);
  }

  @ApiOperation("根据地址查询经纬度")
  @GetMapping("/map/location")
  public InvokeResult<MapLocationBo> getMapLocation(@NotEmpty(message = "地址不能为空！") String address) {

    String key = sysParameterService.findRequiredByKey("tx-map.key");
    String secret = sysParameterService.findRequiredByKey("tx-map.secret");

    // 请求腾讯地图WebService Api
    // Api文档地址：https://lbs.qq.com/service/webService/webServiceGuide/webServiceGeocoder
    String baseUrl = "https://apis.map.qq.com/";
    String uri = "/ws/geocoder/v1/";
    String reqParams = "?address=" + address + "&key=" + key;

    // Api使用签名方式
    // 签名文档：https://lbs.qq.com/faq/serverFaq/webServiceKey
    String sign = SecureUtil.md5(uri + reqParams + secret);
    Map<String, Object> reqMap = new HashMap<>();
    reqMap.put("address", address);
    reqMap.put("key", key);
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

  @ApiOperation("单据时间轴")
  @ApiImplicitParam(value = "单据ID", name = "orderId", paramType = "query", required = true)
  @GetMapping("/timeline/order")
  public InvokeResult<List<OrderTimeLineBo>> getOrderTimeLine(
      @NotBlank(message = "单据ID不能为空！") String orderId) {

    List<OrderTimeLine> datas = orderTimeLineService.getByOrder(orderId);
    List<OrderTimeLineBo> results = datas.stream().map(OrderTimeLineBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("通用上传图片")
  @PostMapping("/upload/image")
  public InvokeResult<String> uploadImage(MultipartFile file) {
    if (!FileUtil.IMG_SUFFIX.contains(FileUtil.getSuffix(file.getOriginalFilename()).toLowerCase())) {
      throw new DefaultClientException(
          "上传图片仅支持【" + CollectionUtil.join(FileUtil.IMG_SUFFIX, StringPool.STR_SPLIT_CN) + "】格式！");
    }

    String url = UploadUtil.upload(file);

    return InvokeResultBuilder.success(url);
  }

  @ApiOperation("通用上传视频")
  @PostMapping("/upload/video")
  public InvokeResult<String> uploadVideo(MultipartFile file) {
    if (!FileUtil.VIDEO_SUFFIX.contains(FileUtil.getSuffix(file.getOriginalFilename()).toLowerCase())) {
      throw new DefaultClientException(
          "上传视频仅支持【" + CollectionUtil.join(FileUtil.VIDEO_SUFFIX, StringPool.STR_SPLIT_CN) + "】格式！");
    }

    String url = UploadUtil.upload(file);

    return InvokeResultBuilder.success(url);
  }

  @ApiOperation("获取编号")
  @GetMapping("/generate/code")
  public InvokeResult<String> generateCode(@NotNull(message = "编号类型不能为空！") Integer type) {
    return InvokeResultBuilder.success(generateCodeService.generate(type));
  }
}
