package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.basedata.bo.shop.GetShopBo;
import com.lframework.xingyun.basedata.bo.shop.QueryShopBo;
import com.lframework.xingyun.basedata.entity.Shop;
import com.lframework.xingyun.basedata.excel.shop.ShopImportListener;
import com.lframework.xingyun.basedata.excel.shop.ShopImportModel;
import com.lframework.xingyun.basedata.service.shop.ShopService;
import com.lframework.xingyun.basedata.vo.shop.CreateShopVo;
import com.lframework.xingyun.basedata.vo.shop.QueryShopVo;
import com.lframework.xingyun.basedata.vo.shop.UpdateShopVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 门店 Controller
 *
 * @author zmj
 */
@Api(tags = "门店")
@Validated
@RestController
@RequestMapping("/basedata/shop")
public class ShopController extends DefaultBaseController {

  @Autowired
  private ShopService shopService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @HasPermission({"base-data:shop:query"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryShopBo>> query(@Valid QueryShopVo vo) {

    PageResult<Shop> pageResult = shopService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<Shop> datas = pageResult.getDatas();
    List<QueryShopBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryShopBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:shop:query"})
  @GetMapping
  public InvokeResult<GetShopBo> get(@NotBlank(message = "id不能为空！") String id) {

    Shop data = shopService.findById(id);
    if (data == null) {
      throw new DefaultClientException("门店不存在！");
    }

    GetShopBo result = new GetShopBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @HasPermission({"base-data:shop:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateShopVo vo) {

    shopService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @HasPermission({"base-data:shop:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateShopVo vo) {

    shopService.update(vo);

    shopService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:shop:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("门店导入模板", ShopImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"base-data:shop:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ShopImportListener listener = new ShopImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ShopImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
