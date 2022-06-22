package com.lframework.xingyun.api.controller.basedata.member;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.api.bo.basedata.member.GetMemberBo;
import com.lframework.xingyun.api.bo.basedata.member.QueryMemberBo;
import com.lframework.xingyun.basedata.entity.Member;
import com.lframework.xingyun.basedata.service.member.IMemberService;
import com.lframework.xingyun.basedata.vo.member.CreateMemberVo;
import com.lframework.xingyun.basedata.vo.member.QueryMemberVo;
import com.lframework.xingyun.basedata.vo.member.UpdateMemberVo;
import com.lframework.xingyun.core.events.member.CreateMemberEvent;
import com.lframework.xingyun.core.events.member.UpdateMemberEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "会员管理")
@Validated
@RestController
@RequestMapping("/basedata/member")
public class MemberController extends DefaultBaseController {

    @Autowired
    private IMemberService memberService;

    /**
     * 会员列表
     */
    @ApiOperation("会员列表")
    @PreAuthorize("@permission.valid('base-data:member:query','base-data:member:add','base-data:member:modify')")
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
    @PreAuthorize("@permission.valid('base-data:member:query','base-data:member:add','base-data:member:modify')")
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
     * 批量停用会员
     */
    @ApiOperation("批量停用会员")
    @PreAuthorize("@permission.valid('base-data:member:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult<Void> batchUnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的会员！") @RequestBody List<String> ids) {

        memberService.batchUnable(ids);

        for (String id : ids) {
            memberService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用会员
     */
    @ApiOperation("批量启用会员")
    @PreAuthorize("@permission.valid('base-data:member:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult<Void> batchEnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的会员！") @RequestBody List<String> ids) {

        memberService.batchEnable(ids);

        for (String id : ids) {
            memberService.cleanCacheByKey(id);
        }

        return InvokeResultBuilder.success();
    }

    /**
     * 新增会员
     */
    @ApiOperation("新增会员")
    @PreAuthorize("@permission.valid('base-data:member:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateMemberVo vo) {

        String id = memberService.create(vo);

        CreateMemberEvent event = new CreateMemberEvent(this);
        event.setId(id);
        ApplicationUtil.publishEvent(event);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改会员
     */
    @ApiOperation("修改会员")
    @PreAuthorize("@permission.valid('base-data:member:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateMemberVo vo) {

        memberService.update(vo);

        memberService.cleanCacheByKey(vo.getId());

        UpdateMemberEvent event = new UpdateMemberEvent(this);
        event.setId(vo.getId());
        ApplicationUtil.publishEvent(event);

        return InvokeResultBuilder.success();
    }
}
