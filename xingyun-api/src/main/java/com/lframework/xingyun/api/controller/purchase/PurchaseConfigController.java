package com.lframework.xingyun.api.controller.purchase;

import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.purchase.config.GetPurchaseConfigBo;
import com.lframework.xingyun.sc.entity.PurchaseConfig;
import com.lframework.xingyun.sc.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.vo.purchase.config.UpdatePurchaseConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 采购参数设置
 *
 * @author zmj
 */
@Api(tags = "采购参数设置")
@Validated
@RestController
@RequestMapping("/purchase/config")
public class PurchaseConfigController extends DefaultBaseController {

    @Autowired
    private IPurchaseConfigService purchaseConfigService;

    /**
     * 查询详情
     */
    @ApiOperation("查询详情")
    @GetMapping
    public InvokeResult<GetPurchaseConfigBo> get() {

        PurchaseConfig config = purchaseConfigService.get();
        GetPurchaseConfigBo result = new GetPurchaseConfigBo(config);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdatePurchaseConfigVo vo) {

        purchaseConfigService.update(vo);

        return InvokeResultBuilder.success();
    }
}
