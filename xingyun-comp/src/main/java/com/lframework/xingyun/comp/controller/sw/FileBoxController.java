package com.lframework.xingyun.comp.controller.sw;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.comp.bo.sw.filebox.GetFileBoxBo;
import com.lframework.xingyun.comp.bo.sw.filebox.QueryFileBoxBo;
import com.lframework.xingyun.comp.entity.FileBox;
import com.lframework.xingyun.comp.service.FileBoxService;
import com.lframework.xingyun.comp.vo.sw.filebox.CreateFileBoxDirVo;
import com.lframework.xingyun.comp.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UpdateFileBoxVo;
import com.lframework.xingyun.comp.vo.sw.filebox.UploadFileBoxVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@Tag(name = "文件收纳箱")
@Validated
@RestController
@RequestMapping("/sw/filebox")
public class FileBoxController extends DefaultBaseController {

  @Autowired
  private FileBoxService fileBoxService;

  @Operation(summary = "创建文件夹")
  @PostMapping("/dir")
  public InvokeResult<Void> createDir(@Valid CreateFileBoxDirVo vo) {

    fileBoxService.createDir(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 上传文件
   */
  @Operation(summary = "上传文件")
  @PostMapping("/upload")
  public InvokeResult<Void> upload(@Valid UploadFileBoxVo vo) {

    fileBoxService.upload(vo, getCurrentUser().getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 查询列表
   */
  @Operation(summary = "查询列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryFileBoxBo>> query(@Valid QueryFileBoxVo vo) {

    PageResult<FileBox> pageResult = fileBoxService.query(getPageIndex(vo), getPageSize(vo),
        vo, getCurrentUser().getId());

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
  @Operation(summary = "根据ID查询")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
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
  @Operation(summary = "修改")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateFileBoxVo vo) {

    fileBoxService.update(vo);

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "删除")
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotEmpty(message = "ID不能为空！") String id) {
    fileBoxService.deleteById(id, getCurrentUser().getId());
    return InvokeResultBuilder.success();
  }
}
