package com.lframework.xingyun.basedata.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.ExcelUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.bo.member.GetMemberBo;
import com.lframework.xingyun.basedata.bo.member.QueryMemberBo;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.excel.member.MemberImportListener;
import com.lframework.xingyun.basedata.excel.member.MemberImportModel;
import com.lframework.xingyun.basedata.service.member.MemberService;
import com.lframework.xingyun.basedata.vo.member.CreateMemberVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberVo;
import com.lframework.xingyun.basedata.vo.member.UpdateMemberVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "会员管理")
@Validated
@RestController
@RequestMapping("/basedata/member")
public class MemberController extends DefaultBaseController {

  @Autowired
  private MemberService memberService;

  /**
   * 会员列表
   */
  @Operation(summary = "会员列表")
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
  @Operation(summary = "查询会员")
  @Parameter(name = "id", description = "ID", in = ParameterIn.QUERY, required = true)
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
   * 删除会员
   */
  @Operation(summary = "删除会员")
  @HasPermission({"base-data:member:delete"})
  @DeleteMapping
  public InvokeResult<Void> deleteById(
      @Parameter(description = "ID", required = true) @NotEmpty(message = "会员ID不能为空！") String id) {

    memberService.deleteById(id);

    memberService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 新增会员
   */
  @Operation(summary = "新增会员")
  @HasPermission({"base-data:member:add"})
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateMemberVo vo) {

    String id = memberService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改会员
   */
  @Operation(summary = "修改会员")
  @HasPermission({"base-data:member:modify"})
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateMemberVo vo) {

    memberService.update(vo);

    memberService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @Operation(summary = "下载导入模板")
  @HasPermission({"base-data:member:import"})
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("会员导入模板", MemberImportModel.class);
  }

  @Operation(summary = "导入")
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
