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
import com.lframework.xingyun.api.bo.settle.item.out.GetSettleOutItemBo;
import com.lframework.xingyun.api.bo.settle.item.out.QuerySettleOutItemBo;
import com.lframework.xingyun.api.model.settle.item.out.SettleOutItemExportModel;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
import com.lframework.xingyun.settle.vo.item.out.CreateSettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.QuerySettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.UpdateSettleOutItemVo;
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
 * 支出项目
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/settle/item/out")
public class SettleOutItemController extends DefaultBaseController {

    @Autowired
    private ISettleOutItemService settleOutItemService;

    /**
     * 支出项目列表
     */
    @PreAuthorize("@permission.valid('settle:out-item:query','settle:out-item:add','settle:out-item:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySettleOutItemVo vo) {

        PageResult<SettleOutItemDto> pageResult = settleOutItemService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SettleOutItemDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySettleOutItemBo> results = datas.stream().map(QuerySettleOutItemBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 查询支出项目
     */
    @PreAuthorize("@permission.valid('settle:out-item:query','settle:out-item:add','settle:out-item:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        SettleOutItemDto data = settleOutItemService.getById(id);
        if (data == null) {
            throw new DefaultClientException("支出项目不存在！");
        }

        GetSettleOutItemBo result = new GetSettleOutItemBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 导出支出项目
     */
    @PreAuthorize("@permission.valid('settle:out-item:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySettleOutItemVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("支出项目信息", SettleOutItemExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SettleOutItemDto> pageResult = settleOutItemService.query(pageIndex, getExportSize(), vo);
                if (!pageResult.isHasNext()) {
                    break;
                }
                pageIndex++;
                List<SettleOutItemDto> datas = pageResult.getDatas();
                List<SettleOutItemExportModel> models = datas.stream().map(SettleOutItemExportModel::new)
                        .collect(Collectors.toList());
                builder.doWrite(models);
            }
        } finally {
            builder.finish();
        }
    }

    /**
     * 批量停用支出项目
     */
    @PreAuthorize("@permission.valid('settle:out-item:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的支出项目！") @RequestBody List<String> ids) {

        settleOutItemService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用支出项目
     */
    @PreAuthorize("@permission.valid('settle:out-item:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的支出项目！") @RequestBody List<String> ids) {

        settleOutItemService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增支出项目
     */
    @PreAuthorize("@permission.valid('settle:out-item:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateSettleOutItemVo vo) {

        settleOutItemService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改支出项目
     */
    @PreAuthorize("@permission.valid('settle:out-item:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateSettleOutItemVo vo) {

        settleOutItemService.update(vo);

        return InvokeResultBuilder.success();
    }
}
