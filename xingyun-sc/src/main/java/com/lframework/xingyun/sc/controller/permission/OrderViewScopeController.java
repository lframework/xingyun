package com.lframework.xingyun.sc.controller.permission;

import com.lframework.starter.web.core.annotations.security.HasPermission;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.xingyun.sc.service.permission.OrderViewScopeService;
import com.lframework.xingyun.sc.vo.permission.SetOrderViewScopeVo;
import com.lframework.xingyun.sc.vo.permission.AuthorizeOrderRoleVo;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/order/view-scope/role")
public class OrderViewScopeController extends DefaultBaseController {

  @Autowired
  private OrderViewScopeService service;

  @HasPermission({"system:role:permission"})
  @GetMapping("/scopes")
  public InvokeResult<Map<String, Integer>> getScopes(
      @NotBlank(message = "角色ID不能为空！") String roleId) {
    return InvokeResultBuilder.success(service.getScopes(roleId));
  }

  @HasPermission({"system:role:permission"})
  @PostMapping("/authorize")
  public InvokeResult<Void> authorize(@RequestBody @Valid AuthorizeOrderRoleVo vo) {
    service.authorize(vo);
    return InvokeResultBuilder.success();
  }

  @HasPermission({"system:role:permission"})
  @GetMapping
  public InvokeResult<Integer> get(@NotBlank(message = "角色ID不能为空！") String roleId) {
    return InvokeResultBuilder.success(service.get(roleId));
  }

  @HasPermission({"system:role:permission"})
  @PutMapping
  public InvokeResult<Void> save(@RequestBody @Valid SetOrderViewScopeVo vo) {
    service.save(vo.getRoleId(), vo.getScope());
    return InvokeResultBuilder.success();
  }
}
