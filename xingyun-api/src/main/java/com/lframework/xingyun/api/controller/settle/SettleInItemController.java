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
import com.lframework.xingyun.api.model.settle.item.in.SettleInItemExportModel;
import com.lframework.xingyun.settle.dto.item.in.SettleInItemDto;
import com.lframework.xingyun.settle.service.ISettleInItemService;
import com.lframework.xingyun.settle.vo.item.in.CreateSettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.QuerySettleInItemVo;
import com.lframework.xingyun.settle.vo.item.in.UpdateSettleInItemVo;
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
 * 收入项目
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/settle/item/in")
public class SettleInItemController extends DefaultBaseController {

    @Autowired
    private ISettleInItemService settleInItemService;

    /**
     * 收入项目列表
     */
    @PreAuthorize("@permission.valid('settle:in-item:query','settle:in-item:add','settle:in-item:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySettleInItemVo vo) {

        PageResult<SettleInItemDto> pageResult = settleInItemService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<SettleInItemDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySettleInItemBo> results = datas.stream().map(QuerySettleInItemBo::new)
                    .collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 查询收入项目
     */
    @PreAuthorize("@permission.valid('settle:in-item:query','settle:in-item:add','settle:in-item:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        SettleInItemDto data = settleInItemService.getById(id);
        if (data == null) {
            throw new DefaultClientException("收入项目不存在！");
        }

        GetSettleInItemBo result = new GetSettleInItemBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 导出收入项目
     */
    @PreAuthorize("@permission.valid('settle:in-item:export')")
    @PostMapping("/export")
    public void export(@Valid QuerySettleInItemVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("收入项目信息", SettleInItemExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<SettleInItemDto> pageResult = settleInItemService.query(pageIndex, getExportSize(), vo);
                List<SettleInItemDto> datas = pageResult.getDatas();
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
     * 批量停用收入项目
     */
    @PreAuthorize("@permission.valid('settle:in-item:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的收入项目！") @RequestBody List<String> ids) {

        settleInItemService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用收入项目
     */
    @PreAuthorize("@permission.valid('settle:in-item:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的收入项目！") @RequestBody List<String> ids) {

        settleInItemService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增收入项目
     */
    @PreAuthorize("@permission.valid('settle:in-item:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateSettleInItemVo vo) {

        settleInItemService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改收入项目
     */
    @PreAuthorize("@permission.valid('settle:in-item:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateSettleInItemVo vo) {

        settleInItemService.update(vo);

        return InvokeResultBuilder.success();
    }
}
