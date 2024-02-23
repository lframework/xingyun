package com.lframework.xingyun.template.inner.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.UserLoginException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.annotations.OpenApi;
import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityConstants;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.CaptchaValidator;
import com.lframework.starter.web.components.redis.RedisHandler;
import com.lframework.starter.web.components.security.IUserTokenResolver;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.components.security.UserDetailsService;
import com.lframework.starter.web.config.properties.KaptchaProperties;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.dto.GenerateCaptchaDto;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.TenantUtil;
import com.lframework.xingyun.template.core.components.permission.SysDataPermissionDataPermissionType;
import com.lframework.xingyun.template.core.entity.SysDataPermissionData;
import com.lframework.xingyun.template.core.enums.SysDataPermissionDataBizType;
import com.lframework.xingyun.template.core.service.SysDataPermissionModelDetailService;
import com.lframework.xingyun.template.core.vo.permission.SysDataPermissionModelDetailVo;
import com.lframework.xingyun.template.inner.bo.auth.CollectMenuBo;
import com.lframework.xingyun.template.inner.bo.auth.LoginBo;
import com.lframework.xingyun.template.inner.bo.auth.MenuBo;
import com.lframework.xingyun.template.inner.bo.auth.MenuBo.MetaBo;
import com.lframework.xingyun.template.inner.dto.LoginDto;
import com.lframework.xingyun.template.inner.dto.MenuDto;
import com.lframework.xingyun.template.inner.entity.SysUserDept;
import com.lframework.xingyun.template.inner.entity.SysUserRole;
import com.lframework.xingyun.template.inner.entity.Tenant;
import com.lframework.xingyun.template.inner.enums.system.SysMenuComponentType;
import com.lframework.xingyun.template.inner.enums.system.SysMenuDisplay;
import com.lframework.xingyun.template.inner.events.LoginEvent;
import com.lframework.xingyun.template.inner.events.LogoutEvent;
import com.lframework.xingyun.template.inner.service.SysModuleTenantService;
import com.lframework.xingyun.template.inner.service.TenantService;
import com.lframework.xingyun.template.inner.service.system.SysDataPermissionDataService;
import com.lframework.xingyun.template.inner.service.system.SysMenuService;
import com.lframework.xingyun.template.inner.service.system.SysUserDeptService;
import com.lframework.xingyun.template.inner.service.system.SysUserRoleService;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import com.lframework.xingyun.template.inner.vo.system.user.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认用户认证Controller
 *
 * @author zmj
 */
@Api(tags = "用户认证")
@Slf4j
@Validated
@RestController
public class AuthController extends DefaultBaseController {

  @Autowired
  private Producer producer;

  @Autowired
  private KaptchaProperties kaptchaProperties;

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private CaptchaValidator captchaValidator;

  @Autowired
  private PasswordEncoderWrapper passwordEncoderWrapper;

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private IUserTokenResolver userTokenResolver;

  @Autowired
  private TenantService tenantService;

  @Autowired
  private SysDataPermissionDataService sysDataPermissionDataService;

  @Autowired
  private SysDataPermissionModelDetailService sysDataPermissionModelDetailService;

  @Autowired
  private SysUserRoleService sysUserRoleService;

  @Autowired
  private SysUserDeptService sysUserDeptService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  /**
   * 获取登录验证码
   */
  @ApiOperation(value = "获取登录验证码")
  @OpenApi
  @GetMapping("/auth/captcha")
  public InvokeResult<GenerateCaptchaDto> generateCaptcha() {

    String code = producer.createText();
    BufferedImage image = producer.createImage(code);

    // 转换流信息写出
    FastByteArrayOutputStream os = new FastByteArrayOutputStream();
    try {
      ImageIO.write(image, "jpg", os);
    } catch (IOException e) {
      throw new DefaultClientException("验证码生成失败，请稍后重试！");
    }

    String sn = IdUtil.getUUID();
    //将验证码存至redis
    redisHandler.set(StringUtil.format(StringPool.LOGIN_CAPTCHA_KEY, sn), code,
        kaptchaProperties.getExpireTime() * 60 * 1000L);

    GenerateCaptchaDto resp = new GenerateCaptchaDto();
    resp.setSn(sn);
    resp.setImage("data:image/jpeg;base64," + Base64.encode(os.toByteArray()));

    log.debug("获取验证码成功, SN={}, code={}", sn, code);

    return InvokeResultBuilder.success(resp);
  }

  @ApiOperation("登录")
  @OpenApi
  @PostMapping("/auth/login")
  public InvokeResult<LoginBo> login(@Valid LoginVo vo) {

    String username = vo.getUsername();
    String password = vo.getPassword();
    String tenantId = null;
    if (TenantUtil.enableTenant()) {
      String[] tmpArr = username.split("@");
      if (tmpArr.length <= 1) {
        throw new DefaultClientException("用户名或密码错误！");
      }

      tenantId = tmpArr[0];
      username = tmpArr[1];

      // 检查租户是否存在
      Tenant tenant = tenantService.getById(tenantId);
      if (tenant == null) {
        throw new DefaultClientException("用户名或密码错误！");
      }

      if (!tenant.getAvailable()) {
        throw new DefaultClientException("用户已停用，无法登录！");
      }

      TenantContextHolder.setTenantId(tenant.getId());
    }

    log.info("用户 {} {} 开始登录", tenantId, username);

    String sn = vo.getSn();
    String captcha = vo.getCaptcha();
    captchaValidator.validate(sn, captcha);

    this.checkUserLogin(tenantId == null ? null : Integer.valueOf(tenantId), username, password);

    AbstractUserDetails user = userDetailsService.loadUserByUsername(username);

    LoginDto dto = this.doLogin(user);

    this.addAttributesToSession(user);

    return InvokeResultBuilder.success(new LoginBo(dto));
  }

  @ApiOperation("退出登录")
  @OpenApi
  @PostMapping("/auth/logout")
  public InvokeResult<Void> logout() {

    AbstractUserDetails user = getCurrentUser();

    String token = null;
    if (user != null) {
      token = userTokenResolver.getToken();
    }

    StpUtil.logout();

    if (user != null) {
      ApplicationUtil.publishEvent(new LogoutEvent(this, user, token));
    }

    return InvokeResultBuilder.success();
  }

  @ApiOperation(value = "获取用户信息")
  @GetMapping("/auth/info")
  public InvokeResult<LoginBo> info() {

    AbstractUserDetails user = getCurrentUser();
    LoginDto info = new LoginDto(null, user.getName(), user.getPermissions());

    return InvokeResultBuilder.success(new LoginBo(info));
  }

  @ApiOperation("获取用户菜单")
  @GetMapping("/auth/menus")
  public InvokeResult<List<MenuBo>> menus() {

    AbstractUserDetails user = getCurrentUser();
    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(
          TenantContextHolder.getTenantId());
    }
    List<MenuDto> menus = sysMenuService.getMenuByUserId(user.getId(), user.isAdmin(), moduleIds);

    // 组装成树形菜单
    List<MenuDto> topMenus = menus.stream().filter(t -> StringUtil.isBlank(t.getParentId()))
        .collect(Collectors.toList());

    List<MenuBo> results = new ArrayList<>();
    for (MenuDto topMenu : topMenus) {
      MenuBo menuBo = new MenuBo();
      menuBo.setName(topMenu.getName());
      menuBo.setComponent("LAYOUT");
      menuBo.setChildren(parseChildrenMenu(topMenu, menus));
      menuBo.setPath(topMenu.getPath());

      MenuBo.MetaBo meta = new MetaBo();
      meta.setId(topMenu.getId());
      meta.setTitle(topMenu.getMeta().getTitle());
      meta.setIcon(topMenu.getMeta().getIcon());
      meta.setHideMenu(topMenu.getHidden());
      meta.setIgnoreKeepAlive(topMenu.getMeta().getNoCache());
      meta.setIsCollect(topMenu.getIsCollect());

      menuBo.setMeta(meta);

      results.add(menuBo);
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("验证当前登录人的登录密码")
  @PostMapping("/auth/check/password")
  public InvokeResult<Boolean> checkPassword(
      @NotBlank(message = "登录密码不能为空！") String password) {
    return InvokeResultBuilder.success(passwordEncoderWrapper.getEncoder()
        .matches(password, SecurityUtil.getCurrentUser().getPassword()));
  }

  private List<MenuBo> parseChildrenMenu(MenuDto topMenu, List<MenuDto> menus) {
    List<MenuBo> children = menus.stream()
        .filter(t -> StringUtil.equals(t.getParentId(), topMenu.getId())).map(t -> {
          MenuBo menuBo = new MenuBo();
          menuBo.setName(t.getName());
          menuBo.setChildren(parseChildrenMenu(t, menus));

          menuBo.setComponent(t.getComponent());
          menuBo.setPath(
              StringUtil.startWith(t.getPath(), "/") ? t.getPath().substring(1) : t.getPath());
          MenuBo.MetaBo meta = new MetaBo();
          meta.setId(t.getId());
          meta.setTitle(t.getMeta().getTitle());
          meta.setIcon(t.getMeta().getIcon());
          meta.setHideMenu(t.getHidden());
          meta.setIgnoreKeepAlive(t.getMeta().getNoCache());
          meta.setIsCollect(t.getIsCollect());
          if (RegUtil.isMatch(PatternPool.PATTERN_HTTP_URL, menuBo.getPath())) {
            meta.setIsLink(Boolean.TRUE);
          }

          // 如果是功能
          if (SysMenuDisplay.FUNCTION.getCode().equals(t.getDisplay())) {
            // 普通
            if (SysMenuComponentType.NORMAL.getCode().equals(t.getComponentType())) {
              if ("/iframes/index".equalsIgnoreCase(t.getComponent())) {
                menuBo.setComponent(null);
                meta.setFrameSrc(menuBo.getPath().substring(menuBo.getPath().indexOf("?src=") + 5));
                menuBo.setPath(menuBo.getPath().substring(0, menuBo.getPath().indexOf("?src=")));
              }
            } else if (SysMenuComponentType.CUSTOM_LIST.getCode().equals(t.getComponentType())) {
              // 自定义列表
              menuBo.setComponent("CUSTOMLIST");
              meta.setCustomListId(t.getComponent());
            } else if (SysMenuComponentType.CUSTOM_PAGE.getCode().equals(t.getComponentType())) {
              // 自定义页面
              menuBo.setComponent("CUSTOMPAGE");
              meta.setCustomPageId(t.getComponent());
            }
          }

          menuBo.setMeta(meta);

          return menuBo;
        }).collect(Collectors.toList());

    return children;
  }

  @ApiOperation("收藏菜单")
  @ApiImplicitParam(value = "菜单ID", name = "menuId", paramType = "query")
  @PostMapping("/menu/collect")
  public InvokeResult<Void> collectMenu(String menuId) {

    AbstractUserDetails user = getCurrentUser();
    sysMenuService.collect(user.getId(), menuId);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("获取已收藏的菜单")
  @GetMapping("/menu/collect")
  public InvokeResult<List<CollectMenuBo>> getCollectMenus() {
    AbstractUserDetails user = getCurrentUser();
    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(
          TenantContextHolder.getTenantId());
    }
    List<MenuDto> menus = sysMenuService.getMenuByUserId(user.getId(), user.isAdmin(), moduleIds);

    List<MenuDto> collectMenus = menus.stream().filter(t -> t.getIsCollect()).collect(Collectors.toList());
    List<CollectMenuBo> results = collectMenus.stream().map(t -> {
      CollectMenuBo result = new CollectMenuBo();
      result.setId(t.getId());
      result.setTitle(t.getMeta().getTitle());
      result.setIcon(t.getMeta().getIcon());
      if (StringUtil.isBlank(result.getIcon())) {
        // 如果没有图标 那么就往上级找
        String icon = null;
        String parentId = t.getParentId();
        while (StringUtil.isNotEmpty(parentId)) {
          MenuDto parentMenu = null;
          for (MenuDto m : menus) {
            if (m.getId().equals(parentId)) {
              parentMenu = m;
            }
          }

          if (parentMenu == null) {
            break;
          }

          if (StringUtil.isNotBlank(parentMenu.getMeta().getIcon())) {
            icon = parentMenu.getMeta().getIcon();
            break;
          }

          parentId = parentMenu.getParentId();
        }
        result.setIcon(icon);
      }

      List<String> pathList = new ArrayList<>();
      pathList.add(t.getPath());
      String parentId = t.getParentId();
      while (StringUtil.isNotEmpty(parentId)) {
        MenuDto parentMenu = null;
        for (MenuDto m : menus) {
          if (m.getId().equals(parentId)) {
            parentMenu = m;
          }
        }

        if (parentMenu == null) {
          break;
        }

        if (StringUtil.isNotBlank(parentMenu.getPath())) {
          pathList.add(parentMenu.getPath());
        }

        parentId = parentMenu.getParentId();
      }

      pathList = CollectionUtil.reverse(pathList);
      result.setPath(CollectionUtil.join(pathList, ""));

      return result;
    }).collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("取消收藏菜单")
  @ApiImplicitParam(value = "菜单ID", name = "menuId", paramType = "query")
  @PostMapping("/menu/collect/cancel")
  public InvokeResult<Void> cancelCollectMenu(String menuId) {

    AbstractUserDetails user = getCurrentUser();
    sysMenuService.cancelCollect(user.getId(), menuId);

    return InvokeResultBuilder.success();
  }

  private LoginDto doLogin(AbstractUserDetails user) {

    if (!user.isAccountNonExpired()) {
      throw new UserLoginException("账户已过期，不允许登录！");
    }

    if (!user.isAccountNonLocked()) {
      throw new UserLoginException("账户已过期，不允许登录！");
    }

    if (!user.isAccountNonLocked()) {
      throw new UserLoginException("账户已锁定，不允许登录！");
    }

    if (!user.isEnabled()) {
      throw new UserLoginException("账户已停用，不允许登录！");
    }

    if (user.isNoPermission()) {
      throw new UserLoginException("账户未授权，不允许登录！");
    }
    // 登录
    StpUtil.login(
        (user.getTenantId() == null ? "" : user.getTenantId() + "@") + user.getUsername());

    StpUtil.getSession().set(SecurityConstants.USER_INFO_KEY, user);

    String token = userTokenResolver.getToken();
    ApplicationUtil.publishEvent(new LoginEvent(this, SecurityUtil.getCurrentUser(), token));

    LoginDto dto = new LoginDto(token, user.getName(), user.getPermissions());

    return dto;
  }

  private void checkUserLogin(Integer tenantId, String username, String password) {
    AbstractUserDetails user = userDetailsService.loadUserByUsername(username);
    String lockKey =
        (tenantId == null ? "noTenant" : tenantId) + "_" + username + "_" + DateUtil.formatDate(
            LocalDate.now()) + "_LOGIN_LOCK";
    if (!passwordEncoderWrapper.getEncoder().matches(password, user.getPassword())) {
      long loginErrorNum = redisHandler.incr(lockKey, 1);
      redisHandler.expire(lockKey, 86400000L);
      int failNum = 5;
      if (loginErrorNum < failNum) {
        throw new UserLoginException(
            "您已经登录失败" + loginErrorNum + "次，您还可以尝试" + (failNum - loginErrorNum)
                + "次！");
      } else {
        sysUserService.lockById(user.getId());

        sysUserService.cleanCacheByKey(user.getId());

        redisHandler.expire(lockKey, 1L);
        // 锁定用户
        throw new UserLoginException("用户已锁定，无法登录！");
      }
    } else {
      redisHandler.expire(lockKey, 1L);
    }
  }

  protected void addAttributesToSession(AbstractUserDetails user) {
    Map<String, String> dataPermissionMap = new HashMap<>();
    SysDataPermissionDataPermissionType[] permissionTypes = SysDataPermissionDataPermissionType.values();
    for (SysDataPermissionDataPermissionType permissionType : permissionTypes) {
      List<String> sqlTemplates = new ArrayList<>();

      List<SysUserRole> userRoles = sysUserRoleService.getByUserId(user.getId());
      if (CollectionUtil.isNotEmpty(userRoles)) {
        for (SysUserRole userRole : userRoles) {
          SysDataPermissionData permissionData = sysDataPermissionDataService.getByBizId(
              userRole.getRoleId(),
              SysDataPermissionDataBizType.ROLE.getCode(), permissionType.getCode());
          if (permissionData != null) {
            String sqlTemplate = sysDataPermissionModelDetailService.toSql(
                JsonUtil.parseList(permissionData.getPermission(),
                    SysDataPermissionModelDetailVo.class));
            if (StringUtil.isNotBlank(sqlTemplate)) {
              sqlTemplates.add(sqlTemplate);
            }
          }
        }
      }

      List<SysUserDept> userDepts = sysUserDeptService.getByUserId(user.getId());
      if (CollectionUtil.isNotEmpty(userDepts)) {
        for (SysUserDept userDept : userDepts) {
          SysDataPermissionData permissionData = sysDataPermissionDataService.getByBizId(
              userDept.getDeptId(),
              SysDataPermissionDataBizType.DEPT.getCode(), permissionType.getCode());
          if (permissionData != null) {
            String sqlTemplate = sysDataPermissionModelDetailService.toSql(
                JsonUtil.parseList(permissionData.getPermission(),
                    SysDataPermissionModelDetailVo.class));
            if (StringUtil.isNotBlank(sqlTemplate)) {
              sqlTemplates.add(sqlTemplate);
            }
          }
        }
      }

      SysDataPermissionData permissionData = sysDataPermissionDataService.getByBizId(
          user.getId(),
          SysDataPermissionDataBizType.USER.getCode(), permissionType.getCode());
      if (permissionData != null) {
        String sqlTemplate = sysDataPermissionModelDetailService.toSql(
            JsonUtil.parseList(permissionData.getPermission(),
                SysDataPermissionModelDetailVo.class));
        if (StringUtil.isNotBlank(sqlTemplate)) {
          sqlTemplates.add(sqlTemplate);
        }
      }

      if (CollectionUtil.isNotEmpty(sqlTemplates)) {
        dataPermissionMap.put(permissionType.getCode().toString(),
            "(" + CollectionUtil.join(sqlTemplates, " AND ") + ")");
      }
    }

    StpUtil.getSession().set(SecurityConstants.DATA_PERMISSION_SQL_MAP, dataPermissionMap);

    Map<String, String> dataPermissionVar = new HashMap<>();
    List<SysUserDept> userDepts = sysUserDeptService.getByUserId(user.getId());
    List<String> curDeptIds = userDepts.stream().map(SysUserDept::getDeptId)
        .map(t -> "'" + t + "'").collect(
            Collectors.toList());
    dataPermissionVar.put("curDeptIds",
        CollectionUtil.isEmpty(curDeptIds) ? IdUtil.getId() : CollectionUtil.join(curDeptIds, ","));

    StpUtil.getSession().set(SecurityConstants.DATA_PERMISSION_SQL_VAR, dataPermissionVar);
  }
}
