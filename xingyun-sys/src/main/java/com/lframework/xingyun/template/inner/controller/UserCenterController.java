package com.lframework.xingyun.template.inner.controller;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.entity.OpLogs;
import com.lframework.xingyun.core.enums.DefaultOpLogType;
import com.lframework.xingyun.core.service.OpLogsService;
import com.lframework.xingyun.core.vo.QueryOpLogsVo;
import com.lframework.xingyun.template.inner.bo.oplog.OpLogInUserCenterBo;
import com.lframework.xingyun.template.inner.bo.usercenter.UserInfoBo;
import com.lframework.xingyun.template.inner.dto.UserInfoDto;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户中心
 *
 * @author zmj
 */
@Api(tags = "个人中心")
@Validated
@RestController
@RequestMapping("/center")
public class UserCenterController extends DefaultBaseController {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private OpLogsService opLogsService;

  @Autowired
  private PasswordEncoderWrapper encoderWrapper;

  /**
   * 获取用户信息
   */
  @ApiOperation(value = "获取用户信息")
  @GetMapping("/info")
  public InvokeResult<UserInfoBo> getInfo() {

    String userId = getCurrentUser().getId();
    UserInfoDto info = sysUserService.getInfo(userId);

    return InvokeResultBuilder.success(new UserInfoBo(info));
  }

  /**
   * 修改密码
   */
  @ApiOperation("修改密码")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "旧密码", name = "oldPsw", paramType = "query", required = true),
      @ApiImplicitParam(value = "新密码", name = "newPsw", paramType = "query", required = true),
      @ApiImplicitParam(value = "确认密码", name = "confirmPsw", paramType = "query", required = true)})
  @OpLog(type = DefaultOpLogType.AUTH, name = "修改密码，原密码：{}，新密码：{}", params = {"#oldPsw", "#newPsw"})
  @PatchMapping("/password")
  public InvokeResult<Void> updatePassword(@NotBlank(message = "旧密码不能为空！") String oldPsw,
      @NotBlank(message = "新密码不能为空！") String newPsw,
      @NotBlank(message = "确认密码不能为空！") String confirmPsw) {

    AbstractUserDetails user = getCurrentUser();
    if (!encoderWrapper.getEncoder().matches(oldPsw, user.getPassword())) {
      throw new InputErrorException("旧密码不正确，请重新输入！");
    }

    if (!StringUtil.equals(newPsw, confirmPsw)) {
      throw new InputErrorException("两次密码输入不一致，请检查！");
    }

    if (!RegUtil.isMatch(PatternPool.PATTERN_PASSWORD, newPsw)) {
      throw new InputErrorException("密码格式不正确，请检查！");
    }

    sysUserService.updatePassword(user.getId(), newPsw);

    //修改密码后，退出登录状态
    SecurityUtil.logout();

    return InvokeResultBuilder.success();
  }

  /**
   * 修改邮箱
   */
  @ApiOperation("修改邮箱")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "新邮箱地址", name = "newEmail", paramType = "query", required = true),
      @ApiImplicitParam(value = "确认邮箱地址", name = "confirmEmail", paramType = "query", required = true)})
  @OpLog(type = DefaultOpLogType.AUTH, name = "修改邮箱，新邮箱：{}", params = "#newEmail")
  @PatchMapping("/email")
  public InvokeResult<Void> updateEmail(@NotBlank(message = "新邮箱地址不能为空！") String newEmail,
      @NotBlank(message = "确认邮箱地址不能为空！") String confirmEmail) {

    AbstractUserDetails user = getCurrentUser();

    if (!StringUtil.equals(newEmail, confirmEmail)) {
      throw new InputErrorException("两次邮箱地址输入不一致，请检查！");
    }

    if (!RegUtil.isMatch(PatternPool.EMAIL, newEmail)) {
      throw new InputErrorException("邮箱地址格式不正确，请检查！");
    }

    sysUserService.updateEmail(user.getId(), newEmail);

    sysUserService.cleanCacheByKey(user.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 修改联系电话
   */
  @ApiOperation("修改联系电话")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "新联系电话", name = "newTelephone", paramType = "query", required = true),
      @ApiImplicitParam(value = "确认联系电话", name = "confirmTelephone", paramType = "query", required = true)})
  @OpLog(type = DefaultOpLogType.AUTH, name = "修改联系电话，新联系电话：{}", params = "#newTelephone")
  @PatchMapping("/telephone")
  public InvokeResult<Void> updateTelephone(@NotBlank(message = "新联系电话不能为空！") String newTelephone,
      @NotBlank(message = "确认联系电话不能为空！") String confirmTelephone) {

    AbstractUserDetails user = getCurrentUser();

    if (!StringUtil.equals(newTelephone, confirmTelephone)) {
      throw new InputErrorException("两次联系电话输入不一致，请检查！");
    }

    if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, newTelephone)) {
      throw new InputErrorException("联系电话格式不正确，请检查！");
    }

    sysUserService.updateTelephone(user.getId(), newTelephone);

    sysUserService.cleanCacheByKey(user.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 查询操作日志
   */
  @ApiOperation("查询操作日志")
  @GetMapping("/oplog")
  public InvokeResult<PageResult<OpLogInUserCenterBo>> oplog(@Valid QueryOpLogsVo vo) {

    vo.setCreateBy(SecurityUtil.getCurrentUser().getId());

    PageResult<OpLogs> pageResult = opLogsService.query(getPageIndex(vo), getPageSize(vo),
        vo);
    List<OpLogs> datas = pageResult.getDatas();
    List<OpLogInUserCenterBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(OpLogInUserCenterBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
