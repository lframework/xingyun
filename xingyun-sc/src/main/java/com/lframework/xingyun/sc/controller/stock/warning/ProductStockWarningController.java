package com.lframework.xingyun.sc.controller.stock.warning;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.sc.bo.stock.warning.GetProductStockWarningBo;
import com.lframework.xingyun.sc.bo.stock.warning.GetProductStockWarningNotifyBo;
import com.lframework.xingyun.sc.bo.stock.warning.QueryProductStockWarningBo;
import com.lframework.xingyun.sc.entity.ProductStockWarning;
import com.lframework.xingyun.sc.entity.ProductStockWarningNotify;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningNotifyService;
import com.lframework.xingyun.sc.service.stock.warning.ProductStockWarningService;
import com.lframework.xingyun.sc.vo.stock.warning.CreateProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.QueryProductStockWarningVo;
import com.lframework.xingyun.sc.vo.stock.warning.UpdateProductStockWarningVo;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存预警 Controller
 *
 * @author zmj
 */
@Api(tags = "库存预警")
@Validated
@RestController
@RequestMapping("/stock/warning")
public class ProductStockWarningController extends DefaultBaseController {

  @Autowired
  private ProductStockWarningService productStockWarningService;

  @Autowired
  private ProductStockWarningNotifyService productStockWarningNotifyService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"stock:warning:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductStockWarningBo>> query(
      @Valid QueryProductStockWarningVo vo) {

    PageResult<ProductStockWarning> pageResult = productStockWarningService.query(
        getPageIndex(vo),
        getPageSize(vo), vo);

    List<ProductStockWarning> datas = pageResult.getDatas();
    List<QueryProductStockWarningBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductStockWarningBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:warning:query"})
  @GetMapping("/detail")
  public InvokeResult<GetProductStockWarningBo> getDetail(
      @NotBlank(message = "id不能为空！") String id) {

    ProductStockWarning data = productStockWarningService.findById(id);
    if (data == null) {
      throw new DefaultClientException("库存预警不存在！");
    }

    GetProductStockWarningBo result = new GetProductStockWarningBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"stock:warning:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductStockWarningVo vo) {

    productStockWarningService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"stock:warning:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductStockWarningVo vo) {

    productStockWarningService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"stock:warning:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "id不能为空！") String id) {

    productStockWarningService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @ApiOperation("批量删除")
  @HasPermission({"stock:warning:delete"})
  @DeleteMapping("/batch")
  public InvokeResult<Void> deleteByIds(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "请选择需要删除的库存预警！") List<String> ids) {

    productStockWarningService.deleteByIds(ids);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询设置的消息通知组
   */
  @ApiOperation("查询设置的消息通知组")
  @HasPermission({"stock:warning:notify"})
  @GetMapping("/setting")
  public InvokeResult<List<GetProductStockWarningNotifyBo>> getSetting() {
    List<ProductStockWarningNotify> datas = productStockWarningNotifyService.list(
        Wrappers.lambdaQuery(
            ProductStockWarningNotify.class).orderByDesc(ProductStockWarningNotify::getId));

    return InvokeResultBuilder.success(
        datas.stream().map(GetProductStockWarningNotifyBo::new).collect(
            Collectors.toList()));
  }

  /**
   * 新增设置的消息通知组
   */
  @ApiOperation("保存设置的消息通知组")
  @HasPermission({"stock:warning:notify"})
  @PostMapping("/setting")
  public InvokeResult<Void> createSetting(
      @ApiParam(value = "消息通知组ID", required = true) @NotBlank(message = "消息通知组ID不能为空！") String id) {

    productStockWarningNotifyService.createSetting(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除设置的消息通知组
   */
  @ApiOperation("删除设置的消息通知组")
  @HasPermission({"stock:warning:notify"})
  @DeleteMapping("/setting")
  public InvokeResult<Void> deleteSetting(
      @ApiParam(value = "消息通知组ID", required = true) @NotBlank(message = "消息通知组ID不能为空！") String id) {

    productStockWarningNotifyService.deleteSetting(id);

    return InvokeResultBuilder.success();
  }
}
