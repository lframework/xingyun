package com.lframework.xingyun.api.controller.sale;

import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.sale.config.GetSaleConfigBo;
import com.lframework.xingyun.sc.dto.sale.config.SaleConfigDto;
import com.lframework.xingyun.sc.service.sale.ISaleConfigService;
import com.lframework.xingyun.sc.vo.sale.config.UpdateSaleConfigVo;
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
@Validated
@RestController
@RequestMapping("/sale/config")
public class SaleConfigController extends DefaultBaseController {

  @Autowired
  private ISaleConfigService saleConfigService;

  @GetMapping
  public InvokeResult get() {

    SaleConfigDto config = saleConfigService.get();
    GetSaleConfigBo result = new GetSaleConfigBo(config);

    return InvokeResultBuilder.success(result);
  }

  @PutMapping
  public InvokeResult update(@Valid UpdateSaleConfigVo vo) {

    saleConfigService.update(vo);

    return InvokeResultBuilder.success();
  }
}
