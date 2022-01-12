package com.lframework.xingyun.api.controller.retail;

import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.retail.config.GetRetailConfigBo;
import com.lframework.xingyun.sc.dto.retail.config.RetailConfigDto;
import com.lframework.xingyun.sc.service.retail.IRetailConfigService;
import com.lframework.xingyun.sc.vo.retail.config.UpdateRetailConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 零售参数设置
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/retail/config")
public class RetailConfigController extends DefaultBaseController {

    @Autowired
    private IRetailConfigService retailConfigService;

    @GetMapping
    public InvokeResult get() {

        RetailConfigDto config = retailConfigService.get();
        GetRetailConfigBo result = new GetRetailConfigBo(config);

        return InvokeResultBuilder.success(result);
    }

    @PutMapping
    public InvokeResult update(@Valid UpdateRetailConfigVo vo) {

        retailConfigService.update(vo);

        return InvokeResultBuilder.success();
    }
}
