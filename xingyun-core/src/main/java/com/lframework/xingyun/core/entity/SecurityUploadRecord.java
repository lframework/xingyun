package com.lframework.xingyun.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.xingyun.core.enums.UploadType;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 文件收纳箱
 * </p>
 */
@Data
@TableName("tbl_security_upload_record")
public class SecurityUploadRecord extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 上传方式
   */
  private UploadType uploadType;

  /**
   * 文件路径
   */
  private String filePath;

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
}
