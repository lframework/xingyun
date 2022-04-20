package com.dstz.adaptor.org.xingyun.entity;

import com.dstz.base.core.model.BaseModel;
import com.dstz.org.api.model.system.ISubsystem;

/**
 * 描述：子系统定义 实体对象
 */
public class Subsystem extends BaseModel implements ISubsystem {

  /**
   * 主键
   */
  protected String id;

  /**
   * 系统名称
   */
  protected String name;

  /**
   * 系统别名
   */
  protected String alias;


  /**
   * 如果url不为空则 跳转对应系统的url
   */
  protected String url;

  protected String openType = "_top";

  /**
   * 是否可用 1 可用，0 ，不可用
   */
  protected Integer enabled = 1;

  /**
   * 描述
   */
  protected String desc;

  /**
   * 是否主系统
   */
  protected int isDefault = 0;

  protected String config;

  /**
   * 返回 主键
   *
   * @return
   */
  public String getId() {

    return this.id;
  }

  public void setId(String id) {

    this.id = id;
  }

  /**
   * 返回 系统名称
   *
   * @return
   */
  public String getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

  /**
   * 返回 系统别名
   *
   * @return
   */
  public String getAlias() {

    return this.alias;
  }

  public void setAlias(String alias) {

    this.alias = alias;
  }

  public String getUrl() {

    return url;
  }

  public void setUrl(String url) {

    this.url = url;
  }

  public String getOpenType() {

    return openType;
  }

  public void setOpenType(String openType) {

    this.openType = openType;
  }

  /**
   * 返回 是否可用 1 可用，0 ，不可用
   *
   * @return
   */
  public Integer getEnabled() {

    return this.enabled;
  }

  public void setEnabled(Integer enabled) {

    this.enabled = enabled;
  }

  public String getDesc() {

    return desc;
  }

  public void setDesc(String desc) {

    this.desc = desc;
  }

  public int getIsDefault() {

    return isDefault;
  }

  public void setIsDefault(int isDefault) {

    this.isDefault = isDefault;
  }

  @Override
  public String getConfig() {

    return config;
  }

  public void setConfig(String config) {

    this.config = config;
  }
}