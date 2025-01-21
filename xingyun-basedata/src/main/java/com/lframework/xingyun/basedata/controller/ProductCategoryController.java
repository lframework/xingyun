package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.xingyun.core.service.RecursionMappingService;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.basedata.bo.product.category.GetProductCategoryBo;
import com.lframework.xingyun.basedata.bo.product.category.ProductCategoryTreeBo;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.excel.product.category.ProductCategoryImportListener;
import com.lframework.xingyun.basedata.excel.product.category.ProductCategoryImportModel;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.vo.product.category.CreateProductCategoryVo;
import com.lframework.xingyun.basedata.vo.product.category.UpdateProductCategoryVo;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 分类管理
 *
 * @author zmj
 */
@Api(tags = "分类管理")
@Validated
@RestController
@RequestMapping("/basedata/product/category")
public class ProductCategoryController extends DefaultBaseController {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Autowired
  private RecursionMappingService recursionMappingService;

  /**
   * 分类列表
   */
  @ApiOperation("分类列表")
  @HasPermission({"base-data:product:category:query", "base-data:product:category:add",
      "base-data:product:category:modify"})
  @GetMapping("/query")
  public InvokeResult<List<ProductCategoryTreeBo>> query() {

    List<ProductCategory> datas = productCategoryService.getAllProductCategories();
    if (CollectionUtil.isEmpty(datas)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<ProductCategoryTreeBo> results = datas.stream().map(ProductCategoryTreeBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 查询分类
   */
  @ApiOperation("查询分类")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:product:category:query", "base-data:product:category:add",
      "base-data:product:category:modify"})
  @GetMapping
  public InvokeResult<GetProductCategoryBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductCategory data = productCategoryService.findById(id);
    if (data == null) {
      throw new DefaultClientException("分类不存在！");
    }

    GetProductCategoryBo result = new GetProductCategoryBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 停用分类
   */
  @ApiOperation("停用分类")
  @HasPermission({"base-data:product:category:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "分类ID不能为空！") String id) {

    productCategoryService.unable(id);
    productCategoryService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用分类
   */
  @ApiOperation("启用分类")
  @HasPermission({"base-data:product:category:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "分类ID不能为空！") String id) {

    productCategoryService.enable(id);

    productCategoryService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增分类
   */
  @ApiOperation("新增分类")
  @HasPermission({"base-data:product:category:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductCategoryVo vo) {

    productCategoryService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改分类
   */
  @ApiOperation("修改分类")
  @HasPermission({"base-data:product:category:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductCategoryVo vo) {

    productCategoryService.update(vo);

    productCategoryService.cleanCacheByKey(vo.getId());

    ProductCategory data = productCategoryService.findById(vo.getId());
    if (!vo.getAvailable()) {
      if (data.getAvailable()) {
        //如果是停用 子节点全部停用
        List<String> childrenIds = recursionMappingService.getNodeChildIds(data.getId(),
            ApplicationUtil.getBean(ProductCategoryNodeType.class));
        if (!CollectionUtil.isEmpty(childrenIds)) {
          for (String childrenId : childrenIds) {
            productCategoryService.cleanCacheByKey(childrenId);
          }
        }
      }
    } else {
      if (!data.getAvailable()) {
        //如果是启用 父节点全部启用
        List<String> parentIds = recursionMappingService.getNodeParentIds(data.getId(),
            ApplicationUtil.getBean(ProductCategoryNodeType.class));
        if (!CollectionUtil.isEmpty(parentIds)) {
          for (String parentId : parentIds) {
            productCategoryService.cleanCacheByKey(parentId);
          }
        }
      }
    }

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:product:category:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("分类导入模板", ProductCategoryImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"base-data:product:category:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ProductCategoryImportListener listener = new ProductCategoryImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ProductCategoryImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
