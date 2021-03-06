package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.FileUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.UploadUtil;
import com.lframework.xingyun.api.bo.basedata.product.brand.GetProductBrandBo;
import com.lframework.xingyun.api.bo.basedata.product.brand.QueryProductBrandBo;
import com.lframework.xingyun.api.excel.basedata.product.brand.ProductBrandImportListener;
import com.lframework.xingyun.api.excel.basedata.product.brand.ProductBrandImportModel;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import com.lframework.xingyun.basedata.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.vo.product.brand.UpdateProductBrandVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * ????????????
 *
 * @author zmj
 */
@Api(tags = "????????????")
@Validated
@RestController
@RequestMapping("/basedata/product/brand")
public class ProductBrandController extends DefaultBaseController {

    @Autowired
    private IProductBrandService productBrandService;

    /**
     * ??????logo
     */
    @ApiOperation("??????logo")
    @ApiImplicitParam(value = "??????", name = "file", paramType = "form", required = true)
    @PostMapping("/upload/logo")
    public InvokeResult<String> uploadLogo(MultipartFile file) {

        if (!FileUtil.IMG_SUFFIX.contains(FileUtil.getSuffix(file.getOriginalFilename()))) {
            throw new DefaultClientException(
                    "Logo??????????????????" + CollectionUtil.join(FileUtil.IMG_SUFFIX, StringPool.STR_SPLIT_CN) + "????????????");
        }

        if (file.getSize() > 1 << 20) {
            throw new DefaultClientException("Logo???????????????????????????1MB");

        }
        String url = UploadUtil.upload(file);

        return InvokeResultBuilder.success(url);
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('base-data:product:brand:query','base-data:product:brand:add','base-data:product:brand:modify')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QueryProductBrandBo>> query(@Valid QueryProductBrandVo vo) {

        PageResult<ProductBrand> pageResult = productBrandService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<ProductBrand> datas = pageResult.getDatas();
        List<QueryProductBrandBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {

            results = datas.stream().map(QueryProductBrandBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }


    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('base-data:product:brand:query','base-data:product:brand:add','base-data:product:brand:modify')")
    @GetMapping
    public InvokeResult<GetProductBrandBo> get(@NotBlank(message = "ID???????????????") String id) {

        ProductBrand data = productBrandService.findById(id);
        if (data == null) {
            throw new DefaultClientException("??????????????????");
        }

        GetProductBrandBo result = new GetProductBrandBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult<Void> batchUnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "?????????????????????????????????") @RequestBody List<String> ids) {

        productBrandService.batchUnable(ids);

        for (String id : ids) {
            productBrandService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult<Void> batchEnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "?????????????????????????????????") @RequestBody List<String> ids) {

        productBrandService.batchEnable(ids);

        for (String id : ids) {
            productBrandService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('base-data:product:brand:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateProductBrandVo vo) {

        productBrandService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * ????????????
     */
    @ApiOperation("????????????")
    @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateProductBrandVo vo) {

        productBrandService.update(vo);

        productBrandService.cleanCacheByKey(vo.getId());

        return InvokeResultBuilder.success();
    }

    @ApiOperation("??????????????????")
    @PreAuthorize("@permission.valid('base-data:product:brand:import')")
    @GetMapping("/import/template")
    public void downloadImportTemplate() {
        ExcelUtil.exportXls("??????????????????", ProductBrandImportModel.class);
    }

    @ApiOperation("??????")
    @PreAuthorize("@permission.valid('base-data:product:brand:import')")
    @PostMapping("/import")
    public InvokeResult<Void> importExcel(@NotBlank(message = "ID????????????") String id,
        @NotNull(message = "???????????????") MultipartFile file) {

        ProductBrandImportListener listener = new ProductBrandImportListener();
        listener.setTaskId(id);
        ExcelUtil.read(file, ProductBrandImportModel.class, listener).sheet().doRead();

        return InvokeResultBuilder.success();
    }
}
