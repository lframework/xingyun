package com.lframework.xingyun.sc.service.permission;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.entity.SysDataPermissionData;
import com.lframework.starter.web.inner.entity.SysRole;
import com.lframework.starter.web.inner.enums.system.SysDataPermissionDataBizType;
import com.lframework.starter.web.inner.service.system.SysDataPermissionDataService;
import com.lframework.starter.web.inner.service.system.SysRoleService;
import com.lframework.starter.web.inner.service.system.SysRoleMenuService;
import com.lframework.xingyun.sc.components.permission.OrderViewScope;
import com.lframework.xingyun.sc.mappers.OrderViewScopeMapper;
import com.lframework.xingyun.sc.vo.permission.AuthorizeOrderRoleVo;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 复用数据权限表保存角色查看范围，不改变框架既有商品/单据条件。 */
@Service
public class OrderViewScopeService {

  // 应用专用类型，避开框架的商品(1)、单据(2)及登录时的条件编译。
  public static final int PERMISSION_TYPE = 100;
  public static final int PURCHASE_PERMISSION_TYPE = 101;
  public static final int SALE_PERMISSION_TYPE = 102;

  @Autowired
  private SysDataPermissionDataService permissionService;

  @Autowired
  private SysRoleService roleService;

  @Autowired
  private OrderViewScopeMapper mapper;

  @Autowired
  private SysRoleMenuService roleMenuService;

  public Map<String, Integer> getScopes(String roleId) {
    requireRole(roleId);
    return Map.of("purchaseScope", getScope(roleId, PURCHASE_PERMISSION_TYPE),
        "saleScope", getScope(roleId, SALE_PERMISSION_TYPE));
  }

  private int getScope(String roleId, int permissionType) {
    SysDataPermissionData data = permissionService.getByBizId(roleId,
        SysDataPermissionDataBizType.ROLE.getCode(), permissionType);
    return data == null ? get(roleId) : parseScope(data.getPermission());
  }

  /** 复用菜单授权的校验和缓存处理，在同一个事务中保存。 */
  @Transactional(rollbackFor = Exception.class)
  public void authorize(AuthorizeOrderRoleVo vo) {
    if (vo.getRoleIds() == null || vo.getRoleIds().isEmpty() || vo.getMenuIds() == null) {
      throw new DefaultClientException("请选择角色及授权菜单！");
    }
    validateScope(vo.getPurchaseScope());
    validateScope(vo.getSaleScope());
    for (String roleId : vo.getRoleIds()) {
      if ("admin".equals(requireRole(roleId).getPermission())) {
        throw new DefaultClientException("系统管理员无需授权！");
      }
    }
    roleMenuService.setting(vo);
    for (String roleId : vo.getRoleIds()) {
      saveScope(roleId, PURCHASE_PERMISSION_TYPE, vo.getPurchaseScope());
      saveScope(roleId, SALE_PERMISSION_TYPE, vo.getSaleScope());
    }
  }

  private void validateScope(Integer scope) {
    if (scope != null && (scope < 0 || scope > 3)) {
      throw new DefaultClientException("订单查看范围无效！");
    }
  }

  private void saveScope(String roleId, int permissionType, Integer scope) {
    if (scope == null) return;
    SysDataPermissionData data = permissionService.getByBizId(roleId,
        SysDataPermissionDataBizType.ROLE.getCode(), permissionType);
    if (data == null) {
      data = new SysDataPermissionData();
      data.setId(IdUtil.getId());
      data.setBizId(roleId);
      data.setBizType(SysDataPermissionDataBizType.ROLE);
      data.setPermissionType(permissionType);
    }
    // 显式保存0恢复默认范围；缺少记录才回退到旧配置。
    data.setPermission(Integer.toString(scope));
    permissionService.saveOrUpdate(data);
  }

  public int get(String roleId) {
    requireRole(roleId);
    SysDataPermissionData data = permissionService.getByBizId(roleId,
        SysDataPermissionDataBizType.ROLE.getCode(), PERMISSION_TYPE);
    return data == null ? 0 : parseScope(data.getPermission());
  }

  @Transactional(rollbackFor = Exception.class)
  public void save(String roleId, int scope) {
    SysRole role = requireRole(roleId);
    if ("admin".equals(role.getPermission())) {
      throw new DefaultClientException("系统管理员无需设置订单查看范围！");
    }
    if (scope < 0 || scope > 2) {
      throw new DefaultClientException("订单查看范围无效！");
    }
    SysDataPermissionData data = permissionService.getByBizId(roleId,
        SysDataPermissionDataBizType.ROLE.getCode(), PERMISSION_TYPE);
    if (scope == 0) {
      permissionService.remove(Wrappers.lambdaQuery(SysDataPermissionData.class)
          .eq(SysDataPermissionData::getBizId, roleId)
          .eq(SysDataPermissionData::getBizType, SysDataPermissionDataBizType.ROLE)
          .eq(SysDataPermissionData::getPermissionType, PERMISSION_TYPE));
      return;
    }
    if (data == null) {
      data = new SysDataPermissionData();
      data.setId(IdUtil.getId());
      data.setBizId(roleId);
      data.setBizType(SysDataPermissionDataBizType.ROLE);
      data.setPermissionType(PERMISSION_TYPE);
    }
    data.setPermission(Integer.toString(scope));
    permissionService.saveOrUpdate(data);
  }

  /** 由读取SQL调用，实时取角色设置，无须重新登录。 */
  public static OrderViewScope current(int permissionType) {
    if (permissionType != PURCHASE_PERMISSION_TYPE && permissionType != SALE_PERMISSION_TYPE) {
      throw new DefaultClientException("订单范围类型无效！");
    }
    AbstractUserDetails user = SecurityUtil.getCurrentUser();
    if (user == null) {
      return new OrderViewScope(-1, null);
    }
    if (Boolean.TRUE.equals(user.getIsAdmin()) || user.hasAdminPermission()) {
      return new OrderViewScope(0, user.getId());
    }
    int scope = 0;
    boolean all = false;
    for (String value : ApplicationUtil.getBean(OrderViewScopeMapper.class)
        .getRoleScopes(user.getId(), permissionType)) {
      int configured = parseScope(value);
      if (configured == 3) all = true;
      else scope = Math.max(scope, configured);
    }
    if (all) return new OrderViewScope(0, user.getId());
    if (scope != 0) return new OrderViewScope(scope, user.getId());
    // 原框架在登录时编译角色、部门、用户数据权限；显式规则接管默认筛选。
    var session = user.getLoginId() == null ? null
        : SecurityUtil.getSessionByLoginId(user.getLoginId());
    Object rules = session == null ? null : session.get("__data_permission_sql_map");
    if (rules instanceof Map<?, ?> map && map.entrySet().stream().anyMatch(entry ->
        "2".equals(String.valueOf(entry.getKey())) && entry.getValue() != null
            && !String.valueOf(entry.getValue()).isBlank())) {
      return new OrderViewScope(0, user.getId());
    }
    return new OrderViewScope(3, user.getId());
  }

  public void checkPurchase(String id, Boolean isForm) {
    if (mapper.countReadablePurchase(id, Boolean.TRUE.equals(isForm)) == 0) {
      throw new DefaultClientException("订单不存在或不在可查看范围内！");
    }
  }

  public void checkSale(String id) {
    if (mapper.countReadableSale(id) == 0) {
      throw new DefaultClientException("订单不存在或不在可查看范围内！");
    }
  }

  public boolean canReadPurchase(String id) {
    return mapper.countReadablePurchase(id, false) > 0;
  }

  public boolean canReadSale(String id) {
    return mapper.countReadableSale(id) > 0;
  }

  private SysRole requireRole(String roleId) {
    SysRole role = roleService.findById(roleId);
    if (role == null) {
      throw new DefaultClientException("角色不存在！");
    }
    return role;
  }

  private static int parseScope(String value) {
    if ("3".equals(value)) {
      return 3;
    }
    if ("0".equals(value)) {
      return 0;
    }
    if ("1".equals(value)) {
      return 1;
    }
    if ("2".equals(value)) {
      return 2;
    }
    throw new DefaultClientException("订单查看范围配置无效，请联系管理员！");
  }
}
