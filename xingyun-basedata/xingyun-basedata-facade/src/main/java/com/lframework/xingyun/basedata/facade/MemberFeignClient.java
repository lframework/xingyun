package com.lframework.xingyun.basedata.facade;

import com.lframework.starter.cloud.BaseFeignClient;
import com.lframework.starter.cloud.resp.ApiInvokeResult;
import com.lframework.xingyun.basedata.facade.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "xingyun-basedata-api", contextId = "MemberFeignClient")
public interface MemberFeignClient extends BaseFeignClient {

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @GetMapping("/facade/basedata/member/findById")
  ApiInvokeResult<Member> findById(@RequestParam String id);
}
