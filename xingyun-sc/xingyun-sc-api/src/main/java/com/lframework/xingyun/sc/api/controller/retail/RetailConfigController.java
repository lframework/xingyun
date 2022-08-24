package com.lframework.xingyun.sc.api.controller.retail;

import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.sc.api.bo.retail.config.GetRetailConfigBo;
import com.lframework.xingyun.sc.biz.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.facade.entity.RetailConfig;
import com.lframework.xingyun.sc.facade.vo.retail.config.UpdateRetailConfigVo;
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
 * 零售参数设置
 *
 * @author zmj
 */
@Api(tags = "零售参数设置")
@Validated
@RestController
@RequestMapping("/retail/config")
public class RetailConfigController extends DefaultBaseController {

    @Autowired
    private IRetailConfigService retailConfigService;

    /**
     * 查询详情
     */
    @ApiOperation("查询详情")
    @GetMapping
    public InvokeResult<GetRetailConfigBo> get() {

        RetailConfig config = retailConfigService.get();
        GetRetailConfigBo result = new GetRetailConfigBo(config);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateRetailConfigVo vo) {

        retailConfigService.update(vo);

        retailConfigService.cleanCacheByKey(null);

        return InvokeResultBuilder.success();
    }
}
