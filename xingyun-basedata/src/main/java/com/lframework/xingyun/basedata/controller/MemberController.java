package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.annotations.security.HasPermission;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.member.GetMemberBo;
import com.lframework.xingyun.basedata.bo.member.QueryMemberBo;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.excel.member.MemberImportListener;
import com.lframework.xingyun.basedata.excel.member.MemberImportModel;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.vo.member.CreateMemberVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberVo;
import com.lframework.xingyun.basedata.vo.member.UpdateMemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 会员管理
 *
 * @author zmj
 */
@Api(tags = "会员管理")
@Validated
@RestController
@RequestMapping("/basedata/member")
public class MemberController extends DefaultBaseController {

  @Autowired
  private MemberService memberService;

  /**
   * 会员列表
   */
  @ApiOperation("会员列表")
  @HasPermission({"base-data:member:query", "base-data:member:add", "base-data:member:modify"})
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryMemberBo>> query(@Valid QueryMemberVo vo) {

    PageResult<Member> pageResult = memberService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<Member> datas = pageResult.getDatas();
    List<QueryMemberBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryMemberBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询会员
   */
  @ApiOperation("查询会员")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @HasPermission({"base-data:member:query", "base-data:member:add", "base-data:member:modify"})
  @GetMapping
  public InvokeResult<GetMemberBo> get(@NotBlank(message = "ID不能为空！") String id) {

    Member data = memberService.findById(id);
    if (data == null) {
      throw new DefaultClientException("会员不存在！");
    }

    GetMemberBo result = new GetMemberBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 停用会员
   */
  @ApiOperation("停用会员")
  @HasPermission({"base-data:member:modify"})
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "会员ID不能为空！") String id) {

    memberService.unable(id);

    memberService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 启用会员
   */
  @ApiOperation("启用会员")
  @HasPermission({"base-data:member:modify"})
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "会员ID不能为空！") String id) {

    memberService.enable(id);

    memberService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增会员
   */
  @ApiOperation("新增会员")
  @HasPermission({"base-data:member:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateMemberVo vo) {

    String id = memberService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改会员
   */
  @ApiOperation("修改会员")
  @HasPermission({"base-data:member:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateMemberVo vo) {

    memberService.update(vo);

    memberService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @HasPermission({"base-data:member:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("会员导入模板", MemberImportModel.class);
  }

  @ApiOperation("导入")
  @HasPermission({"base-data:member:import"})
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    MemberImportListener listener = new MemberImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, MemberImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
