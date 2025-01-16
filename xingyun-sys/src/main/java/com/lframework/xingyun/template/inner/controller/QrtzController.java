package com.lframework.xingyun.template.inner.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.bo.qrtz.GetQrtzBo;
import com.lframework.xingyun.template.inner.bo.qrtz.QueryQrtzBo;
import com.lframework.xingyun.template.inner.service.QrtzService;
import com.lframework.xingyun.template.inner.vo.qrtz.CreateQrtzVo;
import com.lframework.xingyun.template.inner.vo.qrtz.QueryQrtzVo;
import com.lframework.xingyun.template.inner.vo.qrtz.UpdateQrtzVo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.template.inner.dto.qrtz.QrtzDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import com.lframework.starter.web.annotations.security.HasPermission;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "定时器管理")
@RestController
@RequestMapping("/qrtz")
public class QrtzController extends DefaultBaseController {

  @Autowired
  private QrtzService qrtzService;

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @ApiOperation("查询列表")
  @HasPermission({"development:qrtz:manage"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryQrtzBo>> query(@Valid QueryQrtzVo vo) {

    PageResult<QrtzDto> pageResult = qrtzService.query(getPageIndex(vo), getPageSize(vo), vo);
    List<QrtzDto> datas = pageResult.getDatas();

    List<QueryQrtzBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryQrtzBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询
   *
   * @return
   */
  @ApiOperation("查询")
  @HasPermission({"development:qrtz:manage"})
  @GetMapping
  public InvokeResult<GetQrtzBo> get(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    QrtzDto data = qrtzService.findById(name, group);
    if (data == null) {
      throw new DefaultClientException("任务不存在！");
    }

    return InvokeResultBuilder.success(new GetQrtzBo(data));
  }

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  @ApiOperation("创建")
  @HasPermission({"development:qrtz:manage"})
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateQrtzVo vo) {
    qrtzService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   *
   * @param vo
   * @return
   */
  @ApiOperation("修改")
  @HasPermission({"development:qrtz:manage"})
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateQrtzVo vo) {
    qrtzService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 暂停
   *
   * @return
   */
  @ApiOperation("暂停")
  @HasPermission({"development:qrtz:manage"})
  @PutMapping("/pause")
  public InvokeResult<Void> pause(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.pause(name, group);

    return InvokeResultBuilder.success();
  }

  /**
   * 恢复
   *
   * @return
   */
  @ApiOperation("恢复")
  @HasPermission({"development:qrtz:manage"})
  @PutMapping("/resume")
  public InvokeResult<Void> resume(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.resume(name, group);

    return InvokeResultBuilder.success();
  }

  /**
   * 触发
   *
   * @return
   */
  @ApiOperation("触发")
  @HasPermission({"development:qrtz:manage"})
  @PutMapping("/trigger")
  public InvokeResult<Void> trigger(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.trigger(name, group);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   *
   * @return
   */
  @ApiOperation("删除")
  @HasPermission({"development:qrtz:manage"})
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.delete(name, group);

    return InvokeResultBuilder.success();
  }
}
