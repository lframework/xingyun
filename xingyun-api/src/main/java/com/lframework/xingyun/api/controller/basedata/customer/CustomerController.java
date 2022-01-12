package com.lframework.xingyun.api.controller.basedata.customer;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.customer.GetCustomerBo;
import com.lframework.xingyun.api.bo.basedata.customer.QueryCustomerBo;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.vo.customer.CreateCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.QueryCustomerVo;
import com.lframework.xingyun.basedata.vo.customer.UpdateCustomerVo;
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
 * 客户管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/customer")
public class CustomerController extends DefaultBaseController {

    @Autowired
    private ICustomerService customerService;

    /**
     * 客户列表
     */
    @PreAuthorize("@permission.valid('base-data:customer:query','base-data:customer:add','base-data:customer:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryCustomerVo vo) {

        PageResult<CustomerDto> pageResult = customerService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<CustomerDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryCustomerBo> results = datas.stream().map(QueryCustomerBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 查询客户
     */
    @PreAuthorize("@permission.valid('base-data:customer:query','base-data:customer:add','base-data:customer:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        CustomerDto data = customerService.getById(id);
        if (data == null) {
            throw new DefaultClientException("客户不存在！");
        }

        GetCustomerBo result = new GetCustomerBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用客户
     */
    @PreAuthorize("@permission.valid('base-data:customer:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的客户！") @RequestBody List<String> ids) {

        customerService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用客户
     */
    @PreAuthorize("@permission.valid('base-data:customer:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的客户！") @RequestBody List<String> ids) {

        customerService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@permission.valid('base-data:customer:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateCustomerVo vo) {

        customerService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@permission.valid('base-data:customer:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateCustomerVo vo) {

        customerService.update(vo);

        return InvokeResultBuilder.success();
    }
}
