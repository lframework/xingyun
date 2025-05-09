package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2025-01-28
 */
@Data
@TableName("tbl_print_template_comp")
public class PrintTemplateComp extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "PrintTemplateComp";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 模板ID
   */
  private Integer templateId;

  /**
   * 组件配置
   */
  private String compJson;
}
