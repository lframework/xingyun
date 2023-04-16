package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.basedata.bo.paytype.GetPayTypeBo;
import com.lframework.xingyun.basedata.bo.paytype.QueryPayTypeBo;
import com.lframework.xingyun.basedata.entity.PayType;
import com.lframework.xingyun.basedata.service.paytype.PayTypeService;
import com.lframework.xingyun.basedata.vo.paytype.CreatePayTypeVo;
import com.lframework.xingyun.basedata.vo.paytype.QueryPayTypeVo;
import com.lframework.xingyun.basedata.vo.paytype.UpdatePayTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付方式管理
 *
 * @author zmj
 */
@Api(tags = "支付方式管理")
@Validated
@RestController
@RequestMapping("/basedata/paytype")
public class PayTypeController extends DefaultBaseController {

  @Autowired
  private PayTypeService payTypeService;

  /**
   * 支付方式列表
   */
  @ApiOperation("支付方式列表")
  @HasPermission({"base-data:pay-type:query", "base-data:pay-type:add",
      "base-data:pay-type:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryPayTypeBo>> query(@Valid QueryPayTypeVo vo) {

    PageResult<PayType> pageResult = payTypeService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<PayType> datas = pageResult.getDatas();
    List<QueryPayTypeBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryPayTypeBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询支付方式
   */
  @ApiOperation("查询支付方式")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:pay-type:query", "base-data:pay-type:add",
      "base-data:pay-type:modify"})
  @GetMapping
  public InvokeResult<GetPayTypeBo> get(@NotBlank(message = "ID不能为空！") String id) {

    PayType data = payTypeService.findById(id);
    if (data == null) {
      throw new DefaultClientException("支付方式不存在！");
    }

    GetPayTypeBo result = new GetPayTypeBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增支付方式
   */
  @ApiOperation("新增支付方式")
  @HasPermission({"base-data:pay-type:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreatePayTypeVo vo) {

    payTypeService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改支付方式
   */
  @ApiOperation("修改支付方式")
  @HasPermission({"base-data:pay-type:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdatePayTypeVo vo) {

    payTypeService.update(vo);

    payTypeService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
