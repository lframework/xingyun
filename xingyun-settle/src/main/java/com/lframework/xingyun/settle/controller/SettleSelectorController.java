package com.lframework.xingyun.settle.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.xingyun.settle.bo.item.in.SettleInItemSelectorBo;
import com.lframework.xingyun.settle.bo.item.out.SettleOutItemSelectorBo;
import com.lframework.xingyun.settle.entity.SettleInItem;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.service.SettleInItemService;
import com.lframework.xingyun.settle.service.SettleOutItemService;
import com.lframework.xingyun.settle.vo.item.in.SettleInItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.out.SettleOutItemSelectorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据选择器
 *
 * @author zmj
 */
@Api(tags = "数据选择器")
@Validated
@RestController
@RequestMapping("/selector")
public class SettleSelectorController extends DefaultBaseController {

  @Autowired
  private SettleInItemService settleInItemService;

  @Autowired
  private SettleOutItemService settleOutItemService;

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
   * 加载收入项目
   */
  @ApiOperation("加载收入项目")
  @PostMapping("/settle/item/in/load")
  public InvokeResult<List<SettleInItemSelectorBo>> loadSettleInItem(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SettleInItem> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> settleInItemService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SettleInItemSelectorBo> results = datas.stream().map(SettleInItemSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
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

  /**
   * 加载支出项目
   */
  @ApiOperation("加载支出项目")
  @PostMapping("/settle/item/out/load")
  public InvokeResult<List<SettleOutItemSelectorBo>> loadSettleOutItem(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SettleOutItem> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> settleOutItemService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<SettleOutItemSelectorBo> results = datas.stream().map(SettleOutItemSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }
}
