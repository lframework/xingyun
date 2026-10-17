package com.lframework.xingyun.sc.mappers;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderViewScopeMapper {

  List<String> getRoleScopes(@Param("userId") String userId);

  int countReadablePurchase(@Param("id") String id, @Param("isForm") boolean isForm);

  int countReadableSale(@Param("id") String id);
}
