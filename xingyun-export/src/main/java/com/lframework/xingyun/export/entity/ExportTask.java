package com.lframework.xingyun.export.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.xingyun.export.enums.ExportTaskStatus;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 导出任务
 * </p>
 *
 * @author zmj
 * @since 2025-04-10
 */
@Data
@TableName("tbl_export_task")
public class ExportTask extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "ExportTask";

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
   * 上传记录ID
   */
  private String recordId;

  /**
   * 状态
   */
  private ExportTaskStatus status;

  /**
   * 错误信息
   */
  private String errorMsg;

  /**
   * 请求类名
   */
  private String reqClassName;


  /**
   * 请求类方法参数
   */
  private String reqParams;

  /**
   * 请求类方法参数签名
   */
  private String reqParamsSign;

  /**
   * 总数据条数
   */
  private Long totalCount;

  /**
   * 当前完成数据条数
   */
  private Long curCount;

  /**
   * 文件大小
   */
  private String fileSize;

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
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 结束时间
   */
  private LocalDateTime finishTime;
}
