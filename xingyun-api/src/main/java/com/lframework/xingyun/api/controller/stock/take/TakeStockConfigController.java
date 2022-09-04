package com.lframework.xingyun.api.controller.stock.take;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.stock.take.config.GetTakeStockConfigBo;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.service.stock.take.ITakeStockConfigService;
import com.lframework.xingyun.sc.vo.stock.take.config.UpdateTakeStockConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "盘点参数")
@Validated
@RestController
@RequestMapping("/stock/take/config")
public class TakeStockConfigController extends DefaultBaseController {

    @Autowired
    private ITakeStockConfigService takeStockConfigService;

    /**
     * 根据ID查询
     */
    @ApiOperation("根据ID查询")
    @PreAuthorize("@permission.valid('stock:take:config:modify', 'stock:take:plan:handle:diff')")
    @GetMapping
    public InvokeResult<GetTakeStockConfigBo> get() {

        TakeStockConfig data = takeStockConfigService.get();
        if (data == null) {
            throw new DefaultClientException("盘点参数不存在！");
        }

        GetTakeStockConfigBo result = new GetTakeStockConfigBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PreAuthorize("@permission.valid('stock:take:config:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateTakeStockConfigVo vo) {

        takeStockConfigService.update(vo);

        takeStockConfigService.cleanCacheByKey(null);

        return InvokeResultBuilder.success();
    }
}
