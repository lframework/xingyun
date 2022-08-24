package com.lframework.xingyun.sc.api.controller.sale;

import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.sc.api.bo.sale.config.GetSaleConfigBo;
import com.lframework.xingyun.sc.biz.service.sale.ISaleConfigService;
import com.lframework.xingyun.sc.facade.entity.SaleConfig;
import com.lframework.xingyun.sc.facade.vo.sale.config.UpdateSaleConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 销售参数设置
 *
 * @author zmj
 */
@Api(tags = "销售参数设置")
@Validated
@RestController
@RequestMapping("/sale/config")
public class SaleConfigController extends DefaultBaseController {

  @Autowired
  private ISaleConfigService saleConfigService;

  /**
   * 查询详情
   */
  @ApiOperation("查询详情")
  @GetMapping
  public InvokeResult<GetSaleConfigBo> get() {

    SaleConfig config = saleConfigService.get();
    GetSaleConfigBo result = new GetSaleConfigBo(config);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSaleConfigVo vo) {

    saleConfigService.update(vo);

    saleConfigService.cleanCacheByKey(null);

    return InvokeResultBuilder.success();
  }
}
