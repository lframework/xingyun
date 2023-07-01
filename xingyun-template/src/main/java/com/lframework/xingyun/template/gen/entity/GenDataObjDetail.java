package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.enums.GenRelaMode;
import com.lframework.xingyun.template.gen.enums.GenRelaType;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 * 数据对象明细
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@TableName("gen_data_obj_detail")
public class GenDataObjDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "GenDataObjDetail";

  /**
   * ID
   */
  private String id;

  /**
   * 数据对象ID
   */
  private String dataObjId;

  /**
   * 主表字段
   */
  private String mainTableDetailIds;

  /**
   * 关联类型
   */
  private GenRelaType relaType;

  /**
   * 关联方式
   */
  private GenRelaMode relaMode;

  /**
   * 子表ID
   */
  private String subTableId;

  /**
   * 子表别名
   */
  private String subTableAlias;

  /**
   * 主表字段
   */
  private String subTableDetailIds;

  /**
   * 排序
   */
  private Integer orderNo;
}
