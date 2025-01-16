package com.lframework.xingyun.template.inner.vo.system.dept;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysUserDeptSettingVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @NotBlank(message = "用户ID不能为空！")
  private String userId;

  /**
   * 部门ID
   */
  private List<String> deptIds;
}
