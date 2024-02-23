package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.template.core.annotations.sort.Sort;
import com.lframework.xingyun.template.core.annotations.sort.Sorts;
import com.lframework.xingyun.template.inner.dto.UserInfoDto;
import com.lframework.xingyun.template.core.entity.SysUser;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.vo.system.user.QuerySysUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.SysUserSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @Sorts({
      @Sort(value = "code", alias = "u", autoParse = true),
      @Sort(value = "username", alias = "u", autoParse = true),
      @Sort(value = "name", alias = "u", autoParse = true),
      @Sort(value = "createTime", alias = "u", autoParse = true),
      @Sort(value = "updateTime", alias = "u", autoParse = true),
  })
  List<SysUser> query(@Param("vo") QuerySysUserVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysUser findById(String id);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SysUser> selector(@Param("vo") SysUserSelectorVo vo);

  /**
   * 获取用户信息
   *
   * @param userId 用户ID
   * @return
   */
  UserInfoDto getInfo(String userId);

  /**
   * 修改用户密码
   *
   * @param userId   用户ID
   * @param password 新密码（密文）
   */
  void updatePassword(@Param("userId") String userId, @Param("password") String password);

  /**
   * 修改邮箱
   *
   * @param userId 用户ID
   * @param email  邮箱
   */
  void updateEmail(@Param("userId") String userId, @Param("email") String email);

  /**
   * 修改联系电话
   *
   * @param userId    用户ID
   * @param telephone 联系电话
   */
  void updateTelephone(@Param("userId") String userId, @Param("telephone") String telephone);

  /**
   * 根据ID锁定
   *
   * @param id
   */
  void lockById(String id);

  /**
   * 根据ID解锁
   *
   * @param id
   */
  void unlockById(String id);
}
