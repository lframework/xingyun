package com.lframework.xingyun.api.controller.basedata.member;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.member.GetMemberBo;
import com.lframework.xingyun.api.bo.basedata.member.QueryMemberBo;
import com.lframework.xingyun.basedata.dto.member.MemberDto;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.vo.member.CreateMemberVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberVo;
import com.lframework.xingyun.basedata.vo.member.UpdateMemberVo;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/basedata/member")
public class MemberController extends DefaultBaseController {

  @Autowired
  private IMemberService memberService;

  /**
   * 会员列表
   */
  @PreAuthorize("@permission.valid('base-data:member:query','base-data:member:add','base-data:member:modify')")
  @GetMapping("/query")
  public InvokeResult query(@Valid QueryMemberVo vo) {

    PageResult<MemberDto> pageResult = memberService.query(getPageIndex(vo), getPageSize(vo), vo);

    List<MemberDto> datas = pageResult.getDatas();

    if (!CollectionUtil.isEmpty(datas)) {
      List<QueryMemberBo> results = datas.stream().map(QueryMemberBo::new)
          .collect(Collectors.toList());

      PageResultUtil.rebuild(pageResult, results);
    }

    return InvokeResultBuilder.success(pageResult);
  }

  /**
   * 查询会员
   */
  @PreAuthorize("@permission.valid('base-data:member:query','base-data:member:add','base-data:member:modify')")
  @GetMapping
  public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

    MemberDto data = memberService.getById(id);
    if (data == null) {
      throw new DefaultClientException("会员不存在！");
    }

    GetMemberBo result = new GetMemberBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用会员
   */
  @PreAuthorize("@permission.valid('base-data:member:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult batchUnable(
      @NotEmpty(message = "请选择需要停用的会员！") @RequestBody List<String> ids) {

    memberService.batchUnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用会员
   */
  @PreAuthorize("@permission.valid('base-data:member:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult batchEnable(
      @NotEmpty(message = "请选择需要启用的会员！") @RequestBody List<String> ids) {

    memberService.batchEnable(ids);
    return InvokeResultBuilder.success();
  }

  /**
   * 新增会员
   */
  @PreAuthorize("@permission.valid('base-data:member:add')")
  @PostMapping
  public InvokeResult create(@Valid CreateMemberVo vo) {

    memberService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改会员
   */
  @PreAuthorize("@permission.valid('base-data:member:modify')")
  @PutMapping
  public InvokeResult update(@Valid UpdateMemberVo vo) {

    memberService.update(vo);

    return InvokeResultBuilder.success();
  }
}
