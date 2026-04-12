package com.lframework.xingyun.sc.controller.retail;

import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.xingyun.sc.bo.retail.config.GetRetailConfigBo;
import com.lframework.xingyun.sc.entity.RetailConfig;
import com.lframework.xingyun.sc.service.retail.RetailConfigService;
import com.lframework.xingyun.sc.vo.retail.config.UpdateRetailConfigVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
@Tag(name = "零售参数设置")
@Validated
@RestController
@RequestMapping("/retail/config")
public class RetailConfigController extends DefaultBaseController {

    @Autowired
    private RetailConfigService retailConfigService;

    /**
     * 查询详情
     */
    @Operation(summary = "查询详情")
    @GetMapping
    public InvokeResult<GetRetailConfigBo> get() {

        RetailConfig config = retailConfigService.get();
        GetRetailConfigBo result = new GetRetailConfigBo(config);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 修改
     */
    @Operation(summary = "修改")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateRetailConfigVo vo) {

        retailConfigService.update(vo);

        retailConfigService.cleanCacheByKey(null);

        return InvokeResultBuilder.success();
    }
}
