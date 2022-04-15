package com.lframework.xingyun.api.controller.basedata.product;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.product.info.GetProductBo;
import com.lframework.xingyun.api.bo.basedata.product.info.QueryProductBo;
import com.lframework.xingyun.basedata.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.vo.product.info.UpdateProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品管理
 *
 * @author zmj
 */
@Api(tags = "商品管理")
@Validated
@RestController
@RequestMapping("/basedata/product")
public class ProductController extends DefaultBaseController {

  @Autowired
  private IProductService productService;

  /**
   * 商品列表
   */
  @ApiOperation("商品列表")
  @PreAuthorize("@permission.valid('base-data:product:info:query','base-data:product:info:add','base-data:product:info:modify')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductBo>> query(@Valid QueryProductVo vo) {

    PageResult<ProductDto> pageResult = productService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<ProductDto> datas = pageResult.getDatas();
    List<QueryProductBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 商品详情
   */
  @ApiOperation("商品详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('base-data:product:info:query','base-data:product:info:add','base-data:product:info:modify')")
  @GetMapping
  public InvokeResult<GetProductBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GetProductDto data = productService.getDetailById(id);

    GetProductBo result = new GetProductBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 修改商品
   */
  @ApiOperation("修改商品")
  @PreAuthorize("@permission.valid('base-data:product:info:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductVo vo) {

    productService.update(vo);

    return InvokeResultBuilder.success();
  }
}
