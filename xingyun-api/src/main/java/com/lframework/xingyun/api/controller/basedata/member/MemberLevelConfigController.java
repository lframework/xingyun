package com.lframework.xingyun.api.controller.basedata.member;

import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.member.level.config.GetMemberLevelConfigBo;
import com.lframework.xingyun.crm.entity.MemberLevelConfig;
import com.lframework.xingyun.crm.service.member.IMemberLevelConfigService;
import com.lframework.xingyun.crm.vo.member.level.config.UpdateMemberLevelConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员等级规则 Controller
 *
 * @author zmj
 */
@Api(tags = "会员等级规则")
@Validated
@RestController
@RequestMapping("/member/level/config")
public class MemberLevelConfigController extends DefaultBaseController {

  @Autowired
  private IMemberLevelConfigService memberLevelConfigService;

  /**
   * 查询详情
   */
  @ApiOperation("查询详情")
  @PreAuthorize("@permission.valid('member:level:config')")
  @GetMapping
  public InvokeResult<GetMemberLevelConfigBo> get() {

    MemberLevelConfig config = memberLevelConfigService.get();
    GetMemberLevelConfigBo result = new GetMemberLevelConfigBo(config);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PreAuthorize("@permission.valid('member:level:config')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateMemberLevelConfigVo vo) {

    memberLevelConfigService.update(vo);

    memberLevelConfigService.cleanCacheByKey(null);

    return InvokeResultBuilder.success();
  }
}
