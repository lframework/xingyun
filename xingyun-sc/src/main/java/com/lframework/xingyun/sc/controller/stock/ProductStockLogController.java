package com.lframework.xingyun.sc.controller.stock;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.mq.core.utils.ExportTaskUtil;
import com.lframework.xingyun.sc.bo.stock.product.log.QueryProductStockLogBo;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import com.lframework.xingyun.sc.excel.stock.ProductStockLogExportTaskWorker;
import com.lframework.xingyun.sc.service.stock.ProductStockLogService;
import com.lframework.xingyun.sc.vo.stock.log.QueryProductStockLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品库存变动记录
 *
 * @author zmj
 */
@Api(tags = "商品库存变动记录")
@Validated
@RestController
@RequestMapping("/stock/product/log")
public class ProductStockLogController extends DefaultBaseController {

  @Autowired
  private ProductStockLogService productStockLogService;

  /**
   * 查询商品库存变动记录
   */
  @ApiOperation("查询商品库存变动记录")
  @HasPermission({"stock:product-log:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductStockLogBo>> query(@Valid QueryProductStockLogVo vo) {

    PageResult<ProductStockLog> pageResult = productStockLogService.query(getPageIndex(vo),
        getPageSize(vo), vo);
    List<QueryProductStockLogBo> results = null;
    List<ProductStockLog> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryProductStockLogBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出商品库存变动记录
   */
  @ApiOperation("导出商品库存变动记录")
  @HasPermission({"stock:product-log:export"})
  @GetMapping("/export")
  public InvokeResult<Void> export(@Valid QueryProductStockLogVo vo) {

    ExportTaskUtil.exportTask("商品库存变动记录信息", ProductStockLogExportTaskWorker.class, vo);

    return InvokeResultBuilder.success();
  }
}
