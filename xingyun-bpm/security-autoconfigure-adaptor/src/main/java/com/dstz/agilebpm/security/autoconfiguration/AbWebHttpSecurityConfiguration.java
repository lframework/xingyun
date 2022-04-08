package com.dstz.agilebpm.security.autoconfiguration;

import com.dstz.base.core.jwt.JWTService;
import com.dstz.org.api.context.ICurrentContext;
import com.dstz.security.authentication.AccessDecisionManagerImpl;
import com.dstz.security.authentication.FilterInvocationSecurityMetadataSourceImpl;
import com.dstz.security.authentication.JWTAuthenticationFilter;
import com.dstz.security.authentication.SecurityInterceptor;
import com.dstz.security.filter.EncodingFilter;
import com.dstz.security.filter.RefererCsrfFilter;
import com.dstz.security.filter.RequestThreadFilter;
import com.dstz.security.filter.XssFilter;
import com.dstz.security.forbidden.DefaultAccessDeniedHandler;
import com.dstz.security.forbidden.DefualtAuthenticationEntryPoint;
import com.dstz.security.login.UserDetailsServiceImpl;
import com.dstz.security.login.context.LoginContext;
import com.dstz.security.login.logout.DefualtLogoutSuccessHandler;
import com.dstz.sys.util.ContextUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

/**
 * 鉴权配置
 *
 * @author jeff
 */
@EnableConfigurationProperties({AbSecurityProperties.class})
@Configuration
public class AbWebHttpSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AbSecurityProperties abSecurityProperties;

  @Bean
  public LoginContext loginContext() {
    return new LoginContext();
  }

  @Bean
  public ContextUtil contextUtil(ICurrentContext loginContext) {
    ContextUtil context = new ContextUtil();
    context.setCurrentContext(loginContext);
    return context;
  }

  /**
   * 允许HTML 等标签的提交的请求列表
   *
   * @return 实例
   */
  public XssFilter xssFilter() {
    XssFilter xssFilter = new XssFilter();
    List<String> ingores = new ArrayList<>();

    String ingroesConfig = abSecurityProperties.getXssIngores();
    if (StringUtils.isNotEmpty(ingroesConfig)) {
      ingores = Arrays.asList(ingroesConfig.split(","));
    }

    xssFilter.setIngores(ingores);
    return xssFilter;
  }

  /**
   * 允许跨域的请求列表
   *
   * @return 实例
   */
  public RefererCsrfFilter csrfFilter() {
    RefererCsrfFilter filter = new RefererCsrfFilter();
    List<String> ingores = new ArrayList<>();

    String ingroesConfig = abSecurityProperties.getCsrfIngores();
    if (StringUtils.isNotEmpty(ingroesConfig)) {
      ingores = Arrays.asList(ingroesConfig.split(","));
    }

    filter.setIngores(ingores);
    return filter;
  }

  /**
   * 退出登录反馈
   *
   * @return
   */
  public DefualtLogoutSuccessHandler logoutSuccessHandler() {
    return new DefualtLogoutSuccessHandler();
  }

  /**
   * 无权限处理器 返回resultMsg
   **/
  public DefaultAccessDeniedHandler accessDeniedHandler() {
    return new DefaultAccessDeniedHandler();
  }

  /**
   * 访问超时
   **/
  public DefualtAuthenticationEntryPoint authenticationLoginEntry() {
    return new DefualtAuthenticationEntryPoint();
  }

  /**
   * spring security 设置
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.exceptionHandling().authenticationEntryPoint(new DefualtAuthenticationEntryPoint());
    http.rememberMe().key("rememberPrivateKey");
    http.logout().logoutSuccessHandler(new DefualtLogoutSuccessHandler());

    http.addFilterAt(csrfFilter(), CsrfFilter.class);
    //http.addFilter(xssFilter());

    //鉴权主入口
    SecurityInterceptor securityInterceptor = abSecurityInterceptor();
    http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);

    http.addFilterBefore(new RequestThreadFilter(), CsrfFilter.class);
    http.addFilterBefore(new EncodingFilter(), CsrfFilter.class);
    http.addFilterBefore(JWTAuthenticationFilter(), LogoutFilter.class);

    http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

    http.headers().frameOptions().disable();
    http.csrf().disable();
  }


  @Bean("abJWTAuthenticationFilter")
  protected JWTAuthenticationFilter JWTAuthenticationFilter() {
    JWTAuthenticationFilter abJWTAuthenticationFilter = new JWTAuthenticationFilter();
    return abJWTAuthenticationFilter;
  }

  @Bean("abJWTService")
  protected JWTService abJWTService() {
    CustomJWTService jWTService = new CustomJWTService();
    return jWTService;
  }

  /**
   * 访问决策器
   ***/
  @Bean
  protected AccessDecisionManager accessDecisionManager() {
    AccessDecisionManager decisionManager = new AccessDecisionManagerImpl();
    return decisionManager;
  }

  //获取 URL 对应的角色
  @Bean
  protected FilterInvocationSecurityMetadataSource securityMetadataSource() {
    FilterInvocationSecurityMetadataSourceImpl securityMetadataSource = new FilterInvocationSecurityMetadataSourceImpl();

    List<String> ingores = new ArrayList<>();
    String ingroesConfig = abSecurityProperties.getAuthIngores();
    if (StringUtils.isNotEmpty(ingroesConfig)) {
      ingores = Arrays.asList(ingroesConfig.split(","));
    }

    securityMetadataSource.setIngores(ingores);
    return securityMetadataSource;
  }


  @Bean("userDetailsService")
  public UserDetailsService userDetailsService() {
    UserDetailsService userDetailsService = new UserDetailsServiceImpl();
    return userDetailsService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Bean("authenticationManager")
  public AuthenticationManager authenticationManagerBean() throws Exception {
    AuthenticationManager authenticationManager = super.authenticationManagerBean();
    return authenticationManager;
  }


  /**
   * 鉴权拦截器
   *
   * @return
   */
  protected SecurityInterceptor abSecurityInterceptor() {
    SecurityInterceptor intercept = new SecurityInterceptor();

//		intercept.setAuthenticationManager(authenticationManager);
    intercept.setAccessDecisionManager(new AccessDecisionManagerImpl());
    intercept.setSecurityMetadataSource(securityMetadataSource());

    return intercept;
  }

  @Bean("localeResolver")
  public CookieLocaleResolver cookieLocaleResolver() {
    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    cookieLocaleResolver.setDefaultLocale(Locale.CHINA);
    return cookieLocaleResolver;
  }

}
