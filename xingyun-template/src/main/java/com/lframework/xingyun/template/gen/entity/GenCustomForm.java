package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 自定义表单
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@TableName("gen_custom_form")
public class GenCustomForm extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "GenCustomForm";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 名称
   */
  private String name;

  /**
   * 分类ID
   */
  private String categoryId;

  /**
   * 是否对话框表单
   */
  private Boolean isDialog;

  /**
   * 对话框标题
   */
  private String dialogTittle;

  /**
   * 对话框宽度
   */
  private String dialogWidth;

  /**
   * 表单配置
   */
  private String formConfig;

  /**
   * 前置提交脚本
   */
  private String prefixSubmit;

  /**
   * 后置提交脚本
   */
  private String suffixSubmit;

  /**
   * 是否需要查询
   */
  private Boolean requireQuery;

  /**
   * 查询数据Bean名称
   */
  private String queryBean;

  /**
   * 操作数据Bean名称
   */
  private String handleBean;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改人 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /**
   * 修改时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
