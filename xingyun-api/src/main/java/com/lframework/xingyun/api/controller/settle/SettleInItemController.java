package com.lframework.xingyun.api.controller.settle;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.settle.item.in.GetSettleInItemBo;
import com.lframework.xingyun.api.bo.settle.item.in.QuerySettleInItemBo;
import com.lframework.xingyun.api.excel.settle.item.in.SettleInItemExportModel;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.service.ISettleInItemService;
import com.lframework.xingyun.settle.vo.item.in.CreateSettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.UpdateSettleInItemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ????????????
 *
 * @author zmj
 */
@Api(tags = "????????????")
@Validated
@RestController
@RequestMapping("/settle/item/in")
public class SettleInItemController extends DefaultBaseController {

    @Autowired
    private ISettleInItemService settleInItemService;

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('settle:in-item:query','settle:in-item:add','settle:in-item:modify')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QuerySettleInItemBo>> query(@Valid QuerySettleInItemVo vo) {

        PageResult<SettleInItem> pageResult = settleInItemService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SettleInItem> datas = pageResult.getDatas();
        List<QuerySettleInItemBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QuerySettleInItemBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('settle:in-item:query','settle:in-item:add','settle:in-item:modify')")
    @GetMapping
    public InvokeResult<GetSettleInItemBo> get(@NotBlank(message = "ID???????????????") String id) {

        SettleInItem data = settleInItemService.findById(id);
        if (data == null) {
            throw new DefaultClientException("????????????????????????");
        }

        GetSettleInItemBo result = new GetSettleInItemBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('settle:in-item:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySettleInItemVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("??????????????????",
                SettleInItemExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SettleInItem> pageResult = settleInItemService.query(pageIndex, getExportSize(), vo);
                List<SettleInItem> datas = pageResult.getDatas();
                List<SettleInItemExportModel> models = datas.stream().map(SettleInItemExportModel::new)
                        .collect(Collectors.toList());
                builder.doWrite(models);

                if (!pageResult.isHasNext()) {
                    break;
                }
                pageIndex++;
            }
        } finally {
            builder.finish();
        }
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('settle:in-item:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult<Void> batchUnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "???????????????????????????????????????") @RequestBody List<String> ids) {

        settleInItemService.batchUnable(ids);

        for (String id : ids) {
            settleInItemService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@permission.valid('settle:in-item:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult<Void> batchEnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "???????????????????????????????????????") @RequestBody List<String> ids) {

        settleInItemService.batchEnable(ids);

        for (String id : ids) {
            settleInItemService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('settle:in-item:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateSettleInItemVo vo) {

        settleInItemService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('settle:in-item:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateSettleInItemVo vo) {

        settleInItemService.update(vo);

        settleInItemService.cleanCacheByKey(vo.getId());

        return InvokeResultBuilder.success();
    }
}
