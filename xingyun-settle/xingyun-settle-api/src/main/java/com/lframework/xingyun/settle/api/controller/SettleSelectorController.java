package com.lframework.xingyun.settle.api.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.settle.api.bo.item.in.SettleInItemSelectorBo;
import com.lframework.xingyun.settle.api.bo.item.out.SettleOutItemSelectorBo;
import com.lframework.xingyun.settle.biz.service.ISettleInItemService;
import com.lframework.xingyun.settle.biz.service.ISettleOutItemService;
import com.lframework.xingyun.settle.facade.entity.SettleInItem;
import com.lframework.xingyun.settle.facade.entity.SettleOutItem;
import com.lframework.xingyun.settle.facade.vo.item.in.SettleInItemSelectorVo;
import com.lframework.xingyun.settle.facade.vo.item.out.SettleOutItemSelectorVo;
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
 * 结算数据选择器
 *
 * @author zmj
 */
@Api(tags = "结算数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class SettleSelectorController extends DefaultBaseController {

  @Autowired
  private ISettleInItemService settleInItemService;

  @Autowired
  private ISettleOutItemService settleOutItemService;

  /**
   * 收入项目
   */
  @ApiOperation("收入项目")
  @GetMapping("/settle/item/in")
  public InvokeResult<PageResult<SettleInItemSelectorBo>> selector(
      @Valid SettleInItemSelectorVo vo) {

    PageResult<SettleInItem> pageResult = settleInItemService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettleInItem> datas = pageResult.getDatas();
    List<SettleInItemSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(SettleInItemSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 支出项目
   */
  @ApiOperation("支出项目")
  @GetMapping("/settle/item/out")
  public InvokeResult<PageResult<SettleOutItemSelectorBo>> selector(
      @Valid SettleOutItemSelectorVo vo) {

    PageResult<SettleOutItem> pageResult = settleOutItemService.selector(getPageIndex(vo),
        getPageSize(vo), vo);

    List<SettleOutItem> datas = pageResult.getDatas();
    List<SettleOutItemSelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SettleOutItemSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
