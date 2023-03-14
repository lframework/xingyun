package com.lframework.xingyun.sc.controller.retail;

import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.xingyun.sc.service.retail.RetailOutSheetService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 零售订单管理
 *
 * @author zmj
 */
@Api(tags = "零售订单管理")
@Validated
@RestController
@RequestMapping("/retail/order")
public class RetailOrderController extends DefaultBaseController {

  @Autowired
  private RetailOutSheetService retailOutSheetService;

}
