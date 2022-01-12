package com.lframework.xingyun.api.controller.basedata.supplier;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.supplier.GetSupplierBo;
import com.lframework.xingyun.api.bo.basedata.supplier.QuerySupplierBo;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.basedata.vo.supplier.CreateSupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.UpdateSupplierVo;
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
 * 供应商管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/supplier")
public class SupplierController extends DefaultBaseController {

    @Autowired
    private ISupplierService supplierService;

    /**
     * 供应商列表
     */
    @PreAuthorize("@permission.valid('base-data:supplier:query','base-data:supplier:add','base-data:supplier:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySupplierVo vo) {

        PageResult<SupplierDto> pageResult = supplierService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SupplierDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySupplierBo> results = datas.stream().map(QuerySupplierBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 查询供应商
     */
    @PreAuthorize("@permission.valid('base-data:supplier:query','base-data:supplier:add','base-data:supplier:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        SupplierDto data = supplierService.getById(id);
        if (data == null) {
            throw new DefaultClientException("供应商不存在！");
        }

        GetSupplierBo result = new GetSupplierBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用供应商
     */
    @PreAuthorize("@permission.valid('base-data:supplier:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的供应商！") @RequestBody List<String> ids) {

        supplierService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用供应商
     */
    @PreAuthorize("@permission.valid('base-data:supplier:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的供应商！") @RequestBody List<String> ids) {

        supplierService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增供应商
     */
    @PreAuthorize("@permission.valid('base-data:supplier:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateSupplierVo vo) {

        supplierService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改供应商
     */
    @PreAuthorize("@permission.valid('base-data:supplier:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateSupplierVo vo) {

        supplierService.update(vo);

        return InvokeResultBuilder.success();
    }
}
