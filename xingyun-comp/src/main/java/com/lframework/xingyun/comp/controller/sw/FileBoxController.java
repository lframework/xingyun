package com.lframework.xingyun.comp.controller.sw;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.comp.bo.sw.filebox.GetFileBoxBo;
import com.lframework.xingyun.comp.bo.sw.filebox.QueryFileBoxBo;
import com.lframework.xingyun.comp.entity.FileBox;
import com.lframework.xingyun.comp.service.FileBoxService;
import com.lframework.xingyun.comp.vo.sw.filebox.CreateFileBoxDirVo;
import com.lframework.xingyun.comp.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UpdateFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UploadFileBoxVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件收纳箱 Controller
 */
@Api(tags = "文件收纳箱")
@Validated
@RestController
@RequestMapping("/sw/filebox")
public class FileBoxController extends DefaultBaseController {

  @Autowired
  private FileBoxService fileBoxService;

  @ApiOperation("创建文件夹")
  @PostMapping("/dir")
  public InvokeResult<Void> createDir(@Valid CreateFileBoxDirVo vo) {

    fileBoxService.createDir(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 上传文件
   */
  @ApiOperation("上传文件")
  @PostMapping("/upload")
  public InvokeResult<Void> upload(@Valid UploadFileBoxVo vo) {

    fileBoxService.upload(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryFileBoxBo>> query(@Valid QueryFileBoxVo vo) {

    PageResult<FileBox> pageResult = fileBoxService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<FileBox> datas = pageResult.getDatas();
    List<QueryFileBoxBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryFileBoxBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetFileBoxBo> get(@NotBlank(message = "id不能为空！") String id) {

    FileBox data = fileBoxService.findById(id);
    if (data == null) {
      throw new DefaultClientException("文件收纳箱不存在！");
    }

    GetFileBoxBo result = new GetFileBoxBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateFileBoxVo vo) {

    fileBoxService.update(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("删除")
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotEmpty(message = "ID不能为空！") String id) {
    fileBoxService.deleteById(id);
    return InvokeResultBuilder.success();
  }
}
