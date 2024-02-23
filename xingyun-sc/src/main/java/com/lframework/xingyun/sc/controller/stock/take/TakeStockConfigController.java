package com.lframework.xingyun.sc.controller.stock.take;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.sc.bo.stock.take.config.GetTakeStockConfigBo;
import com.lframework.xingyun.sc.entity.TakeStockConfig;
import com.lframework.xingyun.sc.service.stock.take.TakeStockConfigService;
import com.lframework.xingyun.sc.vo.stock.take.config.UpdateTakeStockConfigVo;
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
    private TakeStockConfigService takeStockConfigService;

    /**
     * 根据ID查询
     */
    @ApiOperation("根据ID查询")
    @HasPermission({"stock:take:config:modify", "stock:take:plan:handle:diff"})
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
    @HasPermission({"stock:take:config:modify"})
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateTakeStockConfigVo vo) {

        takeStockConfigService.update(vo);

        takeStockConfigService.cleanCacheByKey(null);

        return InvokeResultBuilder.success();
    }
}
