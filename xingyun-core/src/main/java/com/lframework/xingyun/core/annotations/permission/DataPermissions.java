package com.lframework.xingyun.core.annotations.permission;

import com.lframework.xingyun.core.components.permission.SysDataPermissionDataPermissionType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermissions {

  SysDataPermissionDataPermissionType type();

  DataPermission[] value();
}
