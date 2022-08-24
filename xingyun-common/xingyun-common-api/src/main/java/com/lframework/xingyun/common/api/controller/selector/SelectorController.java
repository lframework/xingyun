package com.lframework.xingyun.common.api.controller.selector;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.common.api.bo.dic.CitySelectorBo;
import com.lframework.xingyun.common.biz.service.IDicCityService;
import com.lframework.xingyun.common.facade.dto.dic.city.DicCityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据选择器
 *
 * @author zmj
 */
@Api(tags = "数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class SelectorController extends DefaultBaseController {

  @Autowired
  private IDicCityService dicCityService;

  /**
   * 城市数据
   */
  @ApiOperation("城市数据")
  @GetMapping("/city")
  public InvokeResult<List<CitySelectorBo>> dicCity() {

    List<DicCityDto> datas = dicCityService.getAll();
    List<CitySelectorBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(CitySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }
}
