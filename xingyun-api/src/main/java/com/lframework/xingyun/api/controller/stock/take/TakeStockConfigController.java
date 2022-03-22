package com.lframework.xingyun.api.controller.stock.take;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.stock.take.config.GetTakeStockConfigBo;
import com.lframework.xingyun.sc.dto.stock.take.config.TakeStockConfigDto;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.vo.stock.take.config.UpdateTakeStockConfigVo;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 盘点参数 Controller
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/stock/take/config")
public class TakeStockConfigController extends DefaultBaseController {

  @Autowired
  private ITakeStockConfigService takeStockConfigService;

  /**
   * 根据ID查询
   */
  @PreAuthorize("@permission.valid('stock:take:config:modify', 'stock:take:plan:handle:diff')")
  @GetMapping
  public InvokeResult get() {

    TakeStockConfigDto data = takeStockConfigService.get();
    if (data == null) {
      throw new DefaultClientException("盘点参数不存在！");
    }

    GetTakeStockConfigBo result = new GetTakeStockConfigBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 修改
   */
  @PreAuthorize("@permission.valid('stock:take:config:modify')")
  @PutMapping
  public InvokeResult update(@Valid UpdateTakeStockConfigVo vo) {

    takeStockConfigService.update(vo);

    return InvokeResultBuilder.success();
  }
}
