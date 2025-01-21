package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysUser;
import com.lframework.xingyun.template.inner.dto.UserInfoDto;
import com.lframework.xingyun.template.inner.vo.system.user.CreateSysUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.QuerySysUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.RegistUserVo;
import com.lframework.xingyun.template.inner.vo.system.user.SysUserSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.user.UpdateSysUserVo;
import java.util.List;

public interface SysUserService extends BaseMpService<SysUser> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysUser> query(Integer pageIndex, Integer pageSize, QuerySysUserVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysUser> query(QuerySysUserVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysUser findById(String id);

  /**
   * 根据编号查询
   *
   * @param code
   * @return
   */
  SysUser findByCode(String code);

  /**
   * 启用
   *
   * @param id
   */
  void enable(String id);

  /**
   * 停用
   *
   * @param id
   */
  void unable(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysUserVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysUserVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysUser> selector(Integer pageIndex, Integer pageSize, SysUserSelectorVo vo);

  /**
   * 注册
   *
   * @param vo
   */
  void regist(RegistUserVo vo);

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
   * @param password 新密码（明文）
   */
  void updatePassword(String userId, String password);

  /**
   * 修改邮箱
   *
   * @param userId 用户ID
   * @param email  邮箱
   */
  void updateEmail(String userId, String email);

  /**
   * 修改联系电话
   *
   * @param userId    用户ID
   * @param telephone 联系电话
   */
  void updateTelephone(String userId, String telephone);

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
