package com.lframework.xingyun.api.controller.sw;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.xingyun.api.bo.sw.excel.GetOnlineExcelBo;
import com.lframework.xingyun.api.bo.sw.excel.QueryOnlineExcelBo;
import com.lframework.xingyun.core.entity.OnlineExcel;
import com.lframework.xingyun.core.service.IOnlineExcelService;
import com.lframework.xingyun.core.vo.sw.excel.BatchSendOnlineExcelVo;
import com.lframework.xingyun.core.vo.sw.excel.CreateOnlineExcelVo;
import com.lframework.xingyun.core.vo.sw.excel.QueryOnlineExcelVo;
import com.lframework.xingyun.core.vo.sw.excel.SendOnlineExcelVo;
import com.lframework.xingyun.core.vo.sw.excel.UpdateOnlineExcelContentVo;
import com.lframework.xingyun.core.vo.sw.excel.UpdateOnlineExcelVo;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在线Excel Controller
 */
@Api(tags = "在线Excel")
@Validated
@RestController
@RequestMapping("/sw/excel")
public class OnlineExcelController extends DefaultBaseController {

  @Autowired
  private IOnlineExcelService onlineExcelService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryOnlineExcelBo>> query(@Valid QueryOnlineExcelVo vo) {

    PageResult<OnlineExcel> pageResult = onlineExcelService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<OnlineExcel> datas = pageResult.getDatas();
    List<QueryOnlineExcelBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryOnlineExcelBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetOnlineExcelBo> get(@NotBlank(message = "id不能为空！") String id) {

    OnlineExcel data = onlineExcelService.findById(id);
    if (data == null) {
      throw new DefaultClientException("在线Excel不存在！");
    }

    GetOnlineExcelBo result = new GetOnlineExcelBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateOnlineExcelVo vo) {

    onlineExcelService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateOnlineExcelVo vo) {

    onlineExcelService.update(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("查询内容")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping("/content")
  public InvokeResult<String> getContent(@NotBlank(message = "id不能为空！") String id) {

    OnlineExcel data = onlineExcelService.findById(id);
    if (data == null) {
      throw new DefaultClientException("文件不存在！");
    }

    return InvokeResultBuilder.success(data.getContent());
  }

  @ApiOperation("修改内容")
  @PutMapping("/content")
  public InvokeResult<Void> updateContent(@Valid UpdateOnlineExcelContentVo vo) {

    onlineExcelService.updateContent(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("发送文件给他人")
  @PostMapping("/send")
  public InvokeResult<Void> send(@Valid SendOnlineExcelVo vo) {

    if (vo.getUserId().equals(SecurityUtil.getCurrentUser().getId())) {
      throw new DefaultClientException("文件不能发送给自己！");
    }

    onlineExcelService.send(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量发送文件给他人")
  @PostMapping("/send/batch")
  public InvokeResult<Void> batchSend(@Valid @RequestBody BatchSendOnlineExcelVo vo) {

    if (vo.getUserId().equals(SecurityUtil.getCurrentUser().getId())) {
      throw new DefaultClientException("文件不能发送给自己！");
    }

    onlineExcelService.batchSend(vo);

    return InvokeResultBuilder.success();
  }
}
