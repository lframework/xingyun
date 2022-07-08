package com.lframework.xingyun.api.controller.crm.member;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.crm.member.GetCrmMemberBo;
import com.lframework.xingyun.api.bo.crm.member.QueryCrmMemberBo;
import com.lframework.xingyun.crm.entity.CrmMember;
import com.lframework.xingyun.crm.service.member.ICrmMemberService;
import com.lframework.xingyun.crm.vo.member.QueryCrmMemberVo;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Crm会员管理
 *
 * @author zmj
 */
@Api(tags = "Crm会员管理")
@Validated
@RestController
@RequestMapping("/crm/member")
public class CrmMemberController extends DefaultBaseController {

  @Autowired
  private ICrmMemberService crmMemberService;

  /**
   * 会员列表
   */
  @ApiOperation("会员列表")
  @PreAuthorize("@permission.valid('crm:member:query')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryCrmMemberBo>> query(@Valid QueryCrmMemberVo vo) {

    PageResult<CrmMember> pageResult = crmMemberService.query(getPageIndex(vo), getPageSize(vo),
        vo);

    List<CrmMember> datas = pageResult.getDatas();
    List<QueryCrmMemberBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryCrmMemberBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询会员
   */
  @ApiOperation("查询会员")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('crm:member:query')")
  @GetMapping
  public InvokeResult<GetCrmMemberBo> get(@NotBlank(message = "ID不能为空！") String id) {

    CrmMember data = crmMemberService.findById(id);
    if (data == null) {
      throw new DefaultClientException("会员不存在！");
    }

    GetCrmMemberBo result = new GetCrmMemberBo(data);

    return InvokeResultBuilder.success(result);
  }
}
