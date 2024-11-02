package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.core.annotations.OpLog;
import com.lframework.xingyun.template.core.entity.SysUser;
import com.lframework.xingyun.template.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.core.enums.Gender;
import com.lframework.xingyun.template.core.service.GenerateCodeService;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.xingyun.template.core.utils.OpLogUtil;
import com.lframework.xingyun.template.inner.dto.UserInfoDto;
import com.lframework.xingyun.template.inner.events.UpdateUserEvent;
import com.lframework.xingyun.template.inner.mappers.system.SysUserMapper;
import com.lframework.xingyun.template.inner.service.system.SysUserDeptService;
import com.lframework.xingyun.template.inner.service.system.SysUserRoleService;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import com.lframework.xingyun.template.inner.vo.system.dept.SysUserDeptSettingVo;
import com.lframework.xingyun.template.inner.vo.system.user.CreateSysUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.QuerySysUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.RegistUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.SysUserRoleSettingVo;
import com.lframework.xingyun.template.inner.vo.system.user.SysUserSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.user.UpdateSysUserVo;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl extends BaseMpServiceImpl<SysUserMapper, SysUser> implements
    SysUserService, ApplicationListener<UpdateUserEvent> {

  @Autowired
  private PasswordEncoderWrapper encoderWrapper;

  @Autowired
  private SysUserDeptService sysUserDeptService;

  @Autowired
  private SysUserRoleService sysUserRoleService;

  @Autowired
  private GenerateCodeService generateCodeService;

  @Autowired
  private UserService userService;

  private static final Integer CODE_KEY = 1;

  @Override
  public PageResult<SysUser> query(Integer pageIndex, Integer pageSize,
      QuerySysUserVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SysUser> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysUser> query(QuerySysUserVo vo) {

    return this.doQuery(vo);
  }

  @Cacheable(value = SysUser.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysUser findById(String id) {

    return this.doGetById(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "启用用户，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchEnable(List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchEnable(ids);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "停用用户，ID：{}", params = "#ids", loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void batchUnable(List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchUnable(ids);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "新增用户，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysUserVo vo) {

    SysUser record = this.doCreate(vo);

    SysUserDeptSettingVo deptSettingVo = new SysUserDeptSettingVo();
    deptSettingVo.setUserId(record.getId());
    deptSettingVo.setDeptIds(vo.getDeptIds());
    sysUserDeptService.setting(deptSettingVo);

    SysUserRoleSettingVo roleSettingVo = new SysUserRoleSettingVo();
    roleSettingVo.setUserIds(Collections.singletonList(record.getId()));
    roleSettingVo.setRoleIds(vo.getRoleIds());
    sysUserRoleService.setting(roleSettingVo);

    OpLogUtil.setVariable("id", record.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return record.getId();
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "修改用户，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysUserVo vo) {

    SysUser data = this.findById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("用户不存在！");
    }

    if (!StringUtil.isBlank(vo.getPassword())) {
      if (!RegUtil.isMatch(PatternPool.PATTERN_PASSWORD, vo.getPassword())) {
        throw new DefaultClientException("密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线！");
      }
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, vo.getTelephone())) {
        throw new DefaultClientException("联系电话格式不正确！");
      }
    }

    this.doUpdate(vo);

    SysUserDeptSettingVo deptSettingVo = new SysUserDeptSettingVo();
    deptSettingVo.setUserId(vo.getId());
    deptSettingVo.setDeptIds(vo.getDeptIds());
    sysUserDeptService.setting(deptSettingVo);

    SysUserRoleSettingVo roleSettingVo = new SysUserRoleSettingVo();
    roleSettingVo.setUserIds(Collections.singletonList(vo.getId()));
    roleSettingVo.setRoleIds(vo.getRoleIds());
    sysUserRoleService.setting(roleSettingVo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public PageResult<SysUser> selector(Integer pageIndex, Integer pageSize,
      SysUserSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SysUser> datas = this.doSelector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void regist(RegistUserVo vo) {

    this.doRegist(vo);
  }

  protected List<SysUser> doQuery(QuerySysUserVo vo) {

    return getBaseMapper().query(vo);
  }

  protected SysUser doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected void doBatchEnable(List<String> ids) {

    Wrapper<SysUser> updateWrapper = Wrappers.lambdaUpdate(SysUser.class)
        .set(SysUser::getAvailable, Boolean.TRUE).in(SysUser::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected void doBatchUnable(List<String> ids) {

    Wrapper<SysUser> updateWrapper = Wrappers.lambdaUpdate(SysUser.class)
        .set(SysUser::getAvailable, Boolean.FALSE).in(SysUser::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected SysUser doCreate(CreateSysUserVo vo) {

    Wrapper<SysUser> checkCodeWrapper = Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<SysUser> checkUsernameWrapper = Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getUsername, vo.getUsername());
    if (getBaseMapper().selectCount(checkUsernameWrapper) > 0) {
      throw new DefaultClientException("用户名重复，请重新输入！");
    }

    SysUser record = new SysUser();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());
    record.setUsername(vo.getUsername());
    record.setPassword(encoderWrapper.getEncoder().encode(vo.getPassword()));
    if (!StringUtil.isBlank(vo.getEmail())) {
      record.setEmail(vo.getEmail());
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      record.setTelephone(vo.getTelephone());
    }

    record.setGender(EnumUtil.getByCode(Gender.class, vo.getGender()));
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(record);

    return record;
  }

  protected void doUpdate(UpdateSysUserVo vo) {

    Wrapper<SysUser> checkCodeWrapper = Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getCode, vo.getCode()).ne(SysUser::getId, vo.getId());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<SysUser> checkUsernameWrapper = Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getUsername, vo.getUsername()).ne(SysUser::getId, vo.getId());
    if (getBaseMapper().selectCount(checkUsernameWrapper) > 0) {
      throw new DefaultClientException("用户名重复，请重新输入！");
    }

    LambdaUpdateWrapper<SysUser> updateWrapper = Wrappers.lambdaUpdate(SysUser.class)
        .eq(SysUser::getId, vo.getId()).set(SysUser::getCode, vo.getCode())
        .set(SysUser::getUsername, vo.getUsername())
        .set(SysUser::getName, vo.getName())
        .set(SysUser::getEmail, null).set(SysUser::getTelephone, null)
        .set(SysUser::getGender, EnumUtil.getByCode(Gender.class, vo.getGender()))
        .set(SysUser::getAvailable, vo.getAvailable()).set(SysUser::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    if (!StringUtil.isBlank(vo.getPassword())) {
      updateWrapper.set(SysUser::getPassword,
          encoderWrapper.getEncoder().encode(vo.getPassword()));
    }

    if (!StringUtil.isBlank(vo.getEmail())) {
      updateWrapper.set(SysUser::getEmail, vo.getEmail());
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      updateWrapper.set(SysUser::getTelephone, vo.getTelephone());
    }

    getBaseMapper().update(updateWrapper);
  }

  protected List<SysUser> doSelector(SysUserSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected void doRegist(RegistUserVo vo) {

    Wrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
        .eq(SysUser::getUsername, vo.getUsername());
    if (getBaseMapper().selectCount(queryWrapper) > 0) {
      throw new DefaultClientException("用户名重复，请重新输入！");
    }

    SysUser record = new SysUser();
    record.setId(IdUtil.getId());
    record.setCode(generateCodeService.generate(CODE_KEY));
    record.setName(vo.getName());
    record.setUsername(vo.getUsername());
    record.setPassword(encoderWrapper.getEncoder().encode(vo.getPassword()));
    if (!StringUtil.isBlank(vo.getEmail())) {
      record.setEmail(vo.getEmail());
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      record.setTelephone(vo.getTelephone());
    }

    record.setGender(Gender.UNKNOWN);
    record.setAvailable(Boolean.TRUE);
    record.setDescription(StringPool.EMPTY_STR);

    getBaseMapper().insert(record);
  }

  @Cacheable(value = UserInfoDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #userId", unless = "#result == null")
  @Override
  public UserInfoDto getInfo(@NonNull String userId) {

    return this.doGetInfo(userId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updatePassword(@NonNull String userId, @NonNull String password) {

    this.doUpdatePassword(userId, this.encodePassword(password));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateEmail(@NonNull String userId, @NonNull String email) {

    this.doUpdateEmail(userId, email);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateTelephone(@NonNull String userId, @NonNull String telephone) {

    this.doUpdateTelephone(userId, telephone);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void lockById(String id) {

    getBaseMapper().lockById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unlockById(String id) {

    getBaseMapper().unlockById(id);
  }

  protected UserInfoDto doGetInfo(@NonNull String userId) {

    return getBaseMapper().getInfo(userId);
  }

  protected void doUpdatePassword(@NonNull String userId, @NonNull String password) {

    getBaseMapper().updatePassword(userId, password);
  }

  protected void doUpdateEmail(@NonNull String userId, @NonNull String email) {

    getBaseMapper().updateEmail(userId, email);
  }

  protected void doUpdateTelephone(@NonNull String userId, @NonNull String telephone) {

    getBaseMapper().updateTelephone(userId, telephone);
  }

  protected String encodePassword(String password) {

    return encoderWrapper.getEncoder().encode(password);
  }

  @CacheEvict(value = {UserInfoDto.CACHE_NAME,
      SysUser.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

    userService.cleanCacheByKey(key);
  }

  @Override
  public void onApplicationEvent(UpdateUserEvent event) {

    SysUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(event.getId());
  }
}
