package com.lframework.xingyun.api.controller.sw;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.UploadUtil;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.xingyun.api.bo.sw.filebox.GetFileBoxBo;
import com.lframework.xingyun.api.bo.sw.filebox.QueryFileBoxBo;
import com.lframework.xingyun.core.entity.FileBox;
import com.lframework.xingyun.core.service.FileBoxService;
import com.lframework.xingyun.core.vo.sw.filebox.BatchSendFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.CreateFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.QueryFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.SendFileBoxVo;
import com.lframework.xingyun.core.vo.sw.filebox.UpdateFileBoxVo;
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
import org.springframework.web.multipart.MultipartFile;

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

  /**
   * 上传文件
   */
  @ApiOperation("上传文件")
  @ApiImplicitParam(value = "文件", name = "file", paramType = "form", required = true)
  @PostMapping("/upload")
  public InvokeResult<String> uploadLogo(MultipartFile file) {

    String url = UploadUtil.upload(file);

    return InvokeResultBuilder.success(url);
  }

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryFileBoxBo>> query(@Valid QueryFileBoxVo vo) {

    PageResult<FileBox> pageResult = fileBoxService.query(getPageIndex(vo), getPageSize(vo), vo);

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
   * 新增
   */
  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateFileBoxVo vo) {

    fileBoxService.create(vo);

    return InvokeResultBuilder.success();
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

  @ApiOperation("发送文件给他人")
  @PostMapping("/send")
  public InvokeResult<Void> send(@Valid SendFileBoxVo vo) {

    if (vo.getUserId().equals(SecurityUtil.getCurrentUser().getId())) {
      throw new DefaultClientException("文件不能发送给自己！");
    }

    fileBoxService.send(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量发送文件给他人")
  @PostMapping("/send/batch")
  public InvokeResult<Void> batchSend(@Valid @RequestBody BatchSendFileBoxVo vo) {

    if (vo.getUserId().equals(SecurityUtil.getCurrentUser().getId())) {
      throw new DefaultClientException("文件不能发送给自己！");
    }

    fileBoxService.batchSend(vo);

    return InvokeResultBuilder.success();
  }
}
