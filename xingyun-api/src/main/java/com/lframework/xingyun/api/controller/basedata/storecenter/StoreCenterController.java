package com.lframework.xingyun.api.controller.basedata.storecenter;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.storecenter.GetStoreCenterBo;
import com.lframework.xingyun.api.bo.basedata.storecenter.QueryStoreCenterBo;
import com.lframework.xingyun.basedata.entity.StoreCenter;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.vo.storecenter.CreateStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.QueryStoreCenterVo;
import com.lframework.xingyun.basedata.vo.storecenter.UpdateStoreCenterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 仓库管理
 *
 * @author zmj
 */
@Api(tags = "仓库管理")
@Validated
@RestController
@RequestMapping("/basedata/storecenter")
public class StoreCenterController extends DefaultBaseController {

    @Autowired
    private IStoreCenterService storeCenterService;

    /**
     * 仓库列表
     */
    @ApiOperation("仓库列表")
    @PreAuthorize("@permission.valid('base-data:store-center:query','base-data:store-center:add','base-data:store-center:modify')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryStoreCenterBo>> query(@Valid QueryStoreCenterVo vo) {

        PageResult<StoreCenter> pageResult = storeCenterService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<StoreCenter> datas = pageResult.getDatas();
        List<QueryStoreCenterBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(QueryStoreCenterBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 查询仓库
     */
    @ApiOperation("查询仓库")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('base-data:store-center:query','base-data:store-center:add','base-data:store-center:modify')")
    @GetMapping
    public InvokeResult<GetStoreCenterBo> get(@NotBlank(message = "ID不能为空！") String id) {

        StoreCenter data = storeCenterService.findById(id);
        if (data == null) {
            throw new DefaultClientException("仓库不存在！");
        }

        GetStoreCenterBo result = new GetStoreCenterBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用仓库
     */
    @ApiOperation("批量停用仓库")
    @PreAuthorize("@permission.valid('base-data:store-center:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult<Void> batchUnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的仓库！") @RequestBody List<String> ids) {

        storeCenterService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用仓库
     */
    @ApiOperation("批量启用仓库")
    @PreAuthorize("@permission.valid('base-data:store-center:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult<Void> batchEnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的仓库！") @RequestBody List<String> ids) {

        storeCenterService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增仓库
     */
    @ApiOperation("新增仓库")
    @PreAuthorize("@permission.valid('base-data:store-center:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateStoreCenterVo vo) {

        storeCenterService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改仓库
     */
    @ApiOperation("修改仓库")
    @PreAuthorize("@permission.valid('base-data:store-center:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateStoreCenterVo vo) {

        storeCenterService.update(vo);

        return InvokeResultBuilder.success();
    }
}
