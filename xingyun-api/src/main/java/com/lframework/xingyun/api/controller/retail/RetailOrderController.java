package com.lframework.xingyun.api.controller.retail;

import com.lframework.starter.web.controller.DefaultBaseController;
import io.swagger.annotations.Api;
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

}
