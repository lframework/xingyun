package com.lframework.xingyun.api.controller.basedata.member;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.member.level.GetMemberLevelBo;
import com.lframework.xingyun.api.bo.basedata.member.level.QueryMemberLevelBo;
import com.lframework.xingyun.crm.entity.MemberLevel;
import com.lframework.xingyun.crm.service.member.IMemberLevelService;
import com.lframework.xingyun.crm.vo.member.level.CreateMemberLevelVo;
import com.lframework.xingyun.crm.vo.member.level.QueryMemberLevelVo;
import com.lframework.xingyun.crm.vo.member.level.UpdateMemberLevelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员等级 Controller
 *
 * @author zmj
 */
@Api(tags = "会员等级")
@Validated
@RestController
@RequestMapping("/member/level")
public class MemberLevelController extends DefaultBaseController {

  @Autowired
  private IMemberLevelService memberLevelService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @PreAuthorize("@permission.valid('member:level:query')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryMemberLevelBo>> query(@Valid QueryMemberLevelVo vo) {

    PageResult<MemberLevel> pageResult = memberLevelService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<MemberLevel> datas = pageResult.getDatas();
    List<QueryMemberLevelBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryMemberLevelBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "id", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('member:level:query')")
  @GetMapping
  public InvokeResult<GetMemberLevelBo> get(@NotBlank(message = "id不能为空！") String id) {

    MemberLevel data = memberLevelService.findById(id);
    if (data == null) {
      throw new DefaultClientException("会员等级不存在！");
    }

    GetMemberLevelBo result = new GetMemberLevelBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @PreAuthorize("@permission.valid('member:level:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateMemberLevelVo vo) {

    memberLevelService.create(vo);

    if (vo.getIsDefault()) {
      Wrapper<MemberLevel> queryWrapper = Wrappers.lambdaQuery(MemberLevel.class);
      List<MemberLevel> memberLevels = memberLevelService.list(queryWrapper);
      for (MemberLevel memberLevel : memberLevels) {
        memberLevelService.cleanCacheByKey(memberLevel.getId());
      }
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PreAuthorize("@permission.valid('member:level:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateMemberLevelVo vo) {

    memberLevelService.update(vo);

    if (vo.getIsDefault()) {
      Wrapper<MemberLevel> queryWrapper = Wrappers.lambdaQuery(MemberLevel.class);
      List<MemberLevel> memberLevels = memberLevelService.list(queryWrapper);
      for (MemberLevel memberLevel : memberLevels) {
        memberLevelService.cleanCacheByKey(memberLevel.getId());
      }
    }

    memberLevelService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }
}
