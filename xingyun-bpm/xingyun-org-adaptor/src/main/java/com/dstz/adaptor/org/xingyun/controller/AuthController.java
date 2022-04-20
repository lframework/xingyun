package com.dstz.adaptor.org.xingyun.controller;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/org/auth")
public class AuthController {

  @Value("${login-url}")
  private String loginUrl;

  @GetMapping("/url")
  public @ResponseBody
  Map<String, String> getLoginUrl() {

    return Collections.singletonMap("url", loginUrl);
  }
}
