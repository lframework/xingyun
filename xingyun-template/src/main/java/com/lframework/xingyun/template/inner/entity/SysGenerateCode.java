package com.lframework.xingyun.template.inner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 编号规则
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_generate_code")
public class SysGenerateCode extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "SysGenerateCode";

  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  @TableId(value = "id", type = IdType.INPUT)
  private Integer id;

  /**
   * 名称
   */
  private String name;

  /**
   * 配置信息（JSONArray）
   */
  private String configStr;
}
